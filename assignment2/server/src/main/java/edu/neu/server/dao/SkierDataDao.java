package edu.neu.server.dao;

import edu.neu.server.Util.ConnectionManager;
import edu.neu.server.model.SkierData;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SkierDataDao {
    protected ConnectionManager connectionManager;
    private static SkierDataDao instance = null;

    protected SkierDataDao() {
        connectionManager = new ConnectionManager();
    }

    public static SkierDataDao getInstance() {
        if (instance == null) {
            instance = new SkierDataDao();
        }
        return instance;
    }

    public SkierData insert(SkierData skierData, int verticalMetres) throws SQLException {
        String insertSkier = "INSERT INTO skierdata(resort_id, day_num, skier_id, lift_id, time, vertical) " + "values (?,?,?,?,?,?);";
        try (Connection connection = connectionManager.getConnection(); PreparedStatement insertStmt = connection.prepareStatement(insertSkier)) {
            insertStmt.setInt(1, skierData.getResortID());
            insertStmt.setInt(2, skierData.getDayNum());
            insertStmt.setInt(3, skierData.getSkierID());
            insertStmt.setInt(4, skierData.getLiftID());
            insertStmt.setInt(5, skierData.getTime());
            insertStmt.setInt(6, verticalMetres);
            insertStmt.executeUpdate();
            return skierData;
        } catch (SQLException ex) {
            Logger.getLogger(SkierDataDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public String getDataBySkierIdAndDay(int skierId, int dayNum) throws SQLException {
        String selectSkierData = "SELECT COUNT(*), SUM(vertical) FROM skierdata WHERE skier_id = ? AND day_num = ?;";
        String totalLifts = "Total Lifts = ";
        String totalVertical = " Total Vertical(in metres) = ";

        try(Connection connection = connectionManager.getConnection(); PreparedStatement selectStmt = connection.prepareStatement(selectSkierData); ResultSet results = selectStmt.executeQuery()) {
            selectStmt.setInt(1, skierId);
            selectStmt.setInt(2, dayNum);
            if (results.next()) {
                totalLifts += results.getInt(1);
                totalVertical += results.getInt(2);
                return totalLifts + totalVertical;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SkierDataDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return null;
    }

    public void delete() throws SQLException {
        String deleteSkierData = "DELETE FROM skierdata;";
        try(Connection connection = connectionManager.getConnection(); PreparedStatement deleteStmt = connection.prepareStatement(deleteSkierData)) {
            deleteStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SkierDataDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }
}
