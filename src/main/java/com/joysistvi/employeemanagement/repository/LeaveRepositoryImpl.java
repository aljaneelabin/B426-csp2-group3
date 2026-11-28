package com.joysistvi.employeemanagement.repository;

import com.joysistvi.employeemanagement.config.DbConnection;
import com.joysistvi.employeemanagement.model.Leave;

import java.sql.Connection;
import java.sql.Date; // changed
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveRepositoryImpl implements LeaveRepository {
    private final DbConnection dbConnection;

    // Leave columns, the employee's name and the number of days (start to end, both counted).
    private static final String SELECT_LEAVES =
            "SELECT l.leave_id, l.employee_id, CONCAT(e.first_name, ' ', e.last_name) AS employee_name, " +
                    "l.leave_type, l.start_date, l.end_date, DATEDIFF(l.end_date, l.start_date) + 1 AS days, " +
                    "l.reason, l.status, l.date_requested, l.is_archived " +
                    "FROM leaves l " +
                    "JOIN employees e ON l.employee_id = e.employee_id ";

    public LeaveRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Leave> findAllActiveLeaves() {
        List<Leave> leaves = new ArrayList<>();
        String query = SELECT_LEAVES + "WHERE l.is_archived = 0 ORDER BY l.leave_id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                leaves.add(mapLeave(rs));
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findAllActiveLeaves): " + DbConnection.friendlyMessage(e));
        }
        return leaves;
    }

    @Override
    public List<Leave> findAllArchivedLeaves() {
        List<Leave> leaves = new ArrayList<>();
        String query = SELECT_LEAVES + "WHERE l.is_archived = 1 ORDER BY l.leave_id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                leaves.add(mapLeave(rs));
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findAllArchivedLeaves): " + DbConnection.friendlyMessage(e));
        }
        return leaves;
    }

    @Override
    public List<Leave> findPendingLeaves() {
        List<Leave> leaves = new ArrayList<>();
        String query = SELECT_LEAVES +
                "WHERE l.is_archived = 0 AND l.status = 'Pending' " +
                "ORDER BY l.date_requested ASC, l.leave_id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {

            while (rs.next()) {
                leaves.add(mapLeave(rs));
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findPendingLeaves): " + DbConnection.friendlyMessage(e));
        }
        return leaves;
    }

    @Override
    public List<Leave> findLeavesByEmployeeId(int employeeId) {
        List<Leave> leaves = new ArrayList<>();
        String query = SELECT_LEAVES + "WHERE l.is_archived = 0 AND l.employee_id = ? ORDER BY l.leave_id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, employeeId);

            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    leaves.add(mapLeave(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findLeavesByEmployeeId): " + DbConnection.friendlyMessage(e));
        }
        return leaves;
    }

    @Override
    public boolean addLeave(Leave leave) {
        // status defaults to 'Pending' and date_requested to the current time in the table.
        String query = "INSERT INTO leaves (employee_id, leave_type, start_date, end_date, reason) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, leave.getEmployeeId());
            prep.setString(2, leave.getLeaveType());
            prep.setDate(3, Date.valueOf(leave.getStartDate())); // changed
            prep.setDate(4, Date.valueOf(leave.getEndDate()));   // changed
            prep.setString(5, leave.getReason());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (addLeave): " + DbConnection.friendlyMessage(e));
            return false;
        }
    }

    @Override
    public boolean updateLeave(Leave leave) {
        // A request can only be edited while it is still Pending.
        String query = "UPDATE leaves SET leave_type = ?, start_date = ?, end_date = ?, reason = ? " +
                "WHERE leave_id = ? AND status = 'Pending'";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setString(1, leave.getLeaveType());
            prep.setDate(2, Date.valueOf(leave.getStartDate())); // changed
            prep.setDate(3, Date.valueOf(leave.getEndDate()));   // changed
            prep.setString(4, leave.getReason());
            prep.setInt(5, leave.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (updateLeave): " + DbConnection.friendlyMessage(e));
            return false;
        }
    }

    @Override
    public boolean approveLeave(Leave leave) {
        // Approve + Deduct Leave Credits: both updates run in one transaction,
        // so the status never changes without the credits being deducted.
        String statusQuery = "UPDATE leaves SET status = 'Approved' WHERE leave_id = ? AND status = 'Pending'";
        String deductQuery = "UPDATE employees e JOIN leaves l ON l.employee_id = e.employee_id " +
                "SET e.leave_credits = e.leave_credits - (DATEDIFF(l.end_date, l.start_date) + 1) " +
                "WHERE l.leave_id = ? AND e.leave_credits >= DATEDIFF(l.end_date, l.start_date) + 1";

        try (Connection conn = dbConnection.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement statusPrep = conn.prepareStatement(statusQuery);
                 PreparedStatement deductPrep = conn.prepareStatement(deductQuery)) {

                statusPrep.setInt(1, leave.getId());
                if (statusPrep.executeUpdate() == 0) {
                    conn.rollback();
                    System.out.println("Repository Error (approveLeave): leave request not found or already processed.");
                    return false;
                }

                deductPrep.setInt(1, leave.getId());
                if (deductPrep.executeUpdate() == 0) {
                    conn.rollback();
                    System.out.println("Repository Error (approveLeave): not enough leave credits to approve this request.");
                    return false;
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (approveLeave): " + DbConnection.friendlyMessage(e));
            return false;
        }
    }

    @Override
    public boolean rejectLeave(Leave leave) {
        // Rejecting never touches the employee's leave credits.
        String query = "UPDATE leaves SET status = 'Rejected' WHERE leave_id = ? AND status = 'Pending'";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, leave.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (rejectLeave): " + DbConnection.friendlyMessage(e));
            return false;
        }
    }

    @Override
    public boolean archiveLeave(Leave leave) {
        String query = "UPDATE leaves SET is_archived = 1 WHERE leave_id = ?";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, leave.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (archiveLeave): " + DbConnection.friendlyMessage(e));
            return false;
        }
    }

    @Override
    public boolean deleteArchivedLeave(Leave leave) {
        // Only archived leave requests can be deleted for good.
        String query = "DELETE FROM leaves WHERE leave_id = ? AND is_archived = 1";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            prep.setInt(1, leave.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (deleteArchivedLeave): " + DbConnection.friendlyMessage(e));
            return false;
        }
    }

    @Override
    public List<Leave> searchLeavesByEmployeeName(String name) {
        List<Leave> leaves = new ArrayList<>();
        String query = SELECT_LEAVES +
                "WHERE l.is_archived = 0 AND (e.first_name LIKE ? OR e.last_name LIKE ? " +
                "OR CONCAT(e.first_name, ' ', e.last_name) LIKE ?) " +
                "ORDER BY l.leave_id ASC";

        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {

            String keyword = "%" + name + "%";
            prep.setString(1, keyword);
            prep.setString(2, keyword);
            prep.setString(3, keyword);

            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    leaves.add(mapLeave(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (searchLeavesByEmployeeName): " + DbConnection.friendlyMessage(e));
        }
        return leaves;
    }

    private Leave mapLeave(ResultSet rs) throws SQLException {
        Leave leave = new Leave(
                rs.getInt("leave_id"),
                rs.getInt("employee_id"),
                rs.getString("employee_name"),
                rs.getString("leave_type"),
                rs.getDate("start_date").toLocalDate(), // changed
                rs.getDate("end_date").toLocalDate(),   // changed
                rs.getInt("days"),
                rs.getString("reason"),
                rs.getString("status"),
                rs.getString("date_requested")
        );
        leave.setArchived(rs.getBoolean("is_archived"));
        return leave;
    }
}