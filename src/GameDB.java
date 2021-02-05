import java.sql.*;

public class GameDB {

    private static final String DB_NAME = "guess_the_number_db";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/guess_the_number_db";
    private static final String USER_NAME = "testuser";
    private static final String PASSWORD = "testuser";

    private static final String TABLE_NAME = "score_log";

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";

    private int counter = 1;

    public GameDB() {
        connectToAndQueryDB();
    }

    public GameDB(String name, int score) {
        connectToAndInsertIntoDB(name, score);
    }

    private void connectToAndInsertIntoDB(String name, int score) {
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement();) {

            insertDataIntoDB(statement, name, score);
            //System.out.println("Data added to " + DB_NAME + ".");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertDataIntoDB(Statement statement, String name, int score) throws SQLException {
        statement.execute("INSERT INTO " + TABLE_NAME +
                " (" + COLUMN_NAME + ", " + COLUMN_SCORE + ")" +
                " VALUES " + "('" + name + "', " + score + ");");
    }

    private void connectToAndQueryDB() {
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD);
             Statement statement = connection.createStatement();) {

            queryTopPlayers(statement);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void queryTopPlayers(Statement statement) throws SQLException {
        ResultSet results = statement.executeQuery("SELECT " + COLUMN_NAME + ", " + COLUMN_SCORE +
                " FROM " + TABLE_NAME +
                " ORDER BY " + COLUMN_SCORE +
                " LIMIT 5;");

        System.out.println("\nWorld leaderboard (fewest turns): ");
        while (results.next()) {
            System.out.println(counter + ": " + results.getString(COLUMN_NAME) + ", " +
                    results.getInt(COLUMN_SCORE));
            counter++;
        }
        results.close();
    }

}
