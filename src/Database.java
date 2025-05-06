import java.sql.*;
public class Database {
    Connection connection = null;
    static String[] tables_name = {"User", "Income", "Budget", "Expense", "Goal", "Plan", "Reminder"};
    public Database() {
        String url = "jdbc:sqlite:budget.db";
        try {
            connection = DriverManager.getConnection(url);
            if(connection == null) {
                throw new SQLException("Failed to connect to database");
            }
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String userTable = "CREATE TABLE IF NOT EXISTS User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "password TEXT NOT NULL);";
        String incomeTable = "CREATE TABLE IF NOT EXISTS Income (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "source TEXT NOT NULL," +
                "amount INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String expenseTable = "CREATE TABLE IF NOT EXISTS Expense (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "item TEXT NOT NULL," +
                "amount INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String budgetTable = "CREATE TABLE IF NOT EXISTS Budget (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "source TEXT NOT NULL," +
                "amount INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        // don't understand it fully
        String goalTable = "CREATE TABLE IF NOT EXISTS Goal (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "target TEXT NOT NULL," +
                "deadline TIMESTAMP NOT NULL," +
                "description TEXT NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String planTable = "CREATE TABLE IF NOT EXISTS Plan (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "target INTEGER NOT NULL," +
                "deadline TIMESTAMP NOT NULL," +
                "description TEXT NOT NULL," +
                "contribution INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String reminderTable = "CREATE TABLE IF NOT EXISTS Plan (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "date TIMESTAMP NOT NULL," +
                "message TEXT NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(userTable);
            statement.executeUpdate(incomeTable);
            statement.executeUpdate(expenseTable);
            statement.executeUpdate(budgetTable);
            statement.executeUpdate(goalTable);
            statement.executeUpdate(planTable);
            statement.execute(reminderTable);
        }
    }
}
