import FinancialType.*;

import java.sql.*;
public class Database {
    private static Database instance = null;
    Connection connection = null;
    static String[] tables_name = {"User", "FinancialType.Income", "FinancialType.Budget", "FinancialType.Expense", "Goal", "Plan", "Reminder"};
    private Database() {
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
    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
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
    // TODO: add parameter User when the User class exists
    public void insertUser() {
        String query = "INSERT INTO User (username, email, password) VALUES (?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
           statement.setString(1, "TMP");
           statement.setString(2, "TMP");
           statement.setString(3, "TMP");
           statement.executeUpdate();
           System.out.println("Inserted Successfully");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    // TODO: change the return to User when the User class exists
    public void getUser(String email, String password) {
        String query = "SELECT * FROM User WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            System.out.println("\n-- Users --");
            while(rs.next()) {
                System.out.println(rs.getString("username") + " " + rs.getString("email") + " " + rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("User not found");
        }
    }
    public void insertIncome(Income income) {
        String query = "INSERT INTO Income (id, source, amount, user_id) VALUES (?, ?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, income.getTypeID());
            statement.setString(2, income.getSource());
            statement.setInt(1, income.getAmount());
            statement.setInt(1, income.getUserID());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Error inserting income");
        }
    }

}
