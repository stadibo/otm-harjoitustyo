package habitrpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author peje
 */
public class DaysShownDao {

    private Database database;

    public DaysShownDao(Database database) {
        this.database = database;
        createTable();
    }

    public boolean create(boolean[] days, Integer key) {
        String sql = "INSERT INTO "
                + "DaysShown(monday, tuesday, wednesday, thursday, friday, saturday, sunday, daily_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            //set values
            stmt.setBoolean(1, days[1]);
            stmt.setBoolean(2, days[2]);
            stmt.setBoolean(3, days[3]);
            stmt.setBoolean(4, days[4]);
            stmt.setBoolean(5, days[5]);
            stmt.setBoolean(6, days[6]);
            stmt.setBoolean(7, days[7]);
            stmt.setInt(8, key);

            stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean[] getDays(Integer key) {
        boolean[] days = new boolean[8];
        String sql = "SELECT monday, tuesday, wednesday, thursday, friday, saturday, sunday "
                + "FROM DaysShown WHERE daily_id = ?";

        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, key);

            ResultSet res = stmt.executeQuery();
            days[1] = res.getBoolean("monday");
            days[2] = res.getBoolean("tuesday");
            days[3] = res.getBoolean("wednesday");
            days[4] = res.getBoolean("thursday");
            days[5] = res.getBoolean("friday");
            days[6] = res.getBoolean("saturday");
            days[7] = res.getBoolean("sunday");

        } catch (SQLException e) {
            return null;
        }

        return days;
    }

    public boolean delete(int key) {
        String sql = "DELETE FROM DaysShown WHERE daily_id = ?";
        int deleted = 0;
        try (Connection conn = database.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, key);
            deleted = stmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }

        if (deleted == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS DaysShown (\n"
                + "days INTEGER PRIMARY KEY,\n"
                + "daily_id INTEGER,\n"
                + "monday INTEGER NOT NULL,\n"
                + "tuesday INTEGER NOT NULL,\n"
                + "wednesday INTEGER NOT NULL,\n"
                + "thursday INTEGER NOT NULL,\n"
                + "friday INTEGER NOT NULL,\n"
                + "saturday INTEGER NOT NULL,\n"
                + "sunday INTEGER NOT NULL,\n"
                + "FOREIGN KEY (daily_id) REFERENCES Daily (id)\n"
                + ");";

        try (Connection conn = database.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {

        }
    }

}
