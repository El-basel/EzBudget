import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Database instance = null;
    Connection connection = null;
    static String[] tables_name = {"User", "Income", "Budget", "Expense", "Goal", "Plan", "Reminder", "Category"};
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
                "email TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL);";
        String incomeTable = "CREATE TABLE IF NOT EXISTS Income (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "source TEXT NOT NULL," +
                "amount INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "insertion_date TEXT DEFAULT CURRENT_DATE," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String expenseTable = "CREATE TABLE IF NOT EXISTS Expense (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "item TEXT NOT NULL," +
                "amount INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "insertion_date TEXT DEFAULT CURRENT_DATE," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String budgetTable = "CREATE TABLE IF NOT EXISTS Budget (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "source TEXT NOT NULL," +
                "category TEXT NOT NULL," +
                "amount INTEGER NOT NULL," +
                "end_date TEXT NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "insertion_date TEXT DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES User(id))," +
                "FOREIGN KEY (category) REFERENCES Category(id));";
        String categoryTable = "CREATE TABLE IF NOT EXISTS Category(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT NOT NULL);";
        // don't understand it fully
        String goalTable = "CREATE TABLE IF NOT EXISTS Goal (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "target TEXT NOT NULL," +
                "deadline TIMESTAMP NOT NULL," +
                "description TEXT NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "insertion_date TEXT DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String planTable = "CREATE TABLE IF NOT EXISTS Plan (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "target INTEGER NOT NULL," +
                "deadline TIMESTAMP NOT NULL," +
                "description TEXT NOT NULL," +
                "contribution INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "insertion_date TEXT DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String reminderTable = "CREATE TABLE IF NOT EXISTS Plan (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "message TEXT NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "insertion_date TEXT DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(userTable);
            statement.executeUpdate(incomeTable);
            statement.executeUpdate(expenseTable);
            statement.executeUpdate(budgetTable);
            statement.executeUpdate(goalTable);
            statement.executeUpdate(planTable);
            statement.execute(reminderTable);
            statement.execute(categoryTable);
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
            System.out.println("\n-- User --");
            if(rs.next()) {
                System.out.println(rs.getString("username") + " " + rs.getString("email") + " " + rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("User not found");
        }
    }
    public void insertIncome(Income income) {
        String query = "INSERT INTO Income (source, amount, user_id) VALUES (?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, income.getSource());
            statement.setInt(2, income.getAmount());
            statement.setInt(3, income.getUserID());
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Error inserting income");
        }
    }

    public Income[] retrieveIncomes(int user_id) {
        String query = "SELECT * FROM Income WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Income> incomes = new ArrayList<>();
            while(rs.next()) {
                Income income = new Income(rs.getString("source"), rs.getInt("amount"));
                incomes.add(income);
            }
            return  (Income[]) incomes.toArray();
        } catch (Exception e) {
            System.out.println("Error retrieving Incomes");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Income retrieveIncome(int user_id, String source, int amount) {
        String query = "SELECT * FROM Income WHERE user_id = ? AND source = ? AND amount = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            statement.setString(2, source);
            statement.setInt(3, amount);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return new Income(rs.getString("source"), rs.getInt("amount"));
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error retrieving Incomes");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void insertExpense(Expense expense) {
        String query = "INSERT INTO Expense (item, amount, user_id) VALUES (?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, expense.getItem());
            statement.setInt(2, expense.getAmount());
            statement.setInt(3, expense.getUserID());
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Error Inserting Expense");
        }

    }

    public Expense[] retrieveExpenses(int user_id) {
        String query = "SELECT * FROM Expense WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Income> expenses = new ArrayList<>();
            while(rs.next()) {
                Income income = new Income(rs.getString("item"), rs.getInt("amount"));
                expenses.add(income);
            }
            return  (Expense[]) expenses.toArray();
        } catch (Exception e) {
            System.out.println("Error retrieving Incomes");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Expense retrieveExpense(int user_id, String item, int amount) {
        String query = "SELECT * FROM Expense WHERE user_id = ? AND item = ? AND amount = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            statement.setString(2, item);
            statement.setInt(3, amount);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return new Expense(rs.getString("item"), rs.getInt("amount"));
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error retrieving Incomes");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void insertBudget(Budget budget) {
        String categoryQuery = "SELECT * FROM Category WHERE id = ?";
        String category = null;
        try (PreparedStatement statement = connection.prepareStatement(categoryQuery)) {
            statement.setString(1,budget.getCategory());
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                category = rs.getString("type");
                return;
            }
        } catch (SQLException e) {

        }
        String query = "INSERT INTO Budget (source, category, amount, user_id) VALUES (?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, budget.getSource());
            statement.setString(2, category);
            statement.setInt(3, budget.getAmount());
            statement.setInt(4, budget.getUserID());
            statement.executeQuery();
        } catch (Exception e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Error Inserting Expense");
        }

    }

    public Budget[] retrieveBudgets(int user_id) {
        String query = "SELECT * FROM Budget WHERE user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Budget> budgets = new ArrayList<>();
            while(rs.next()) {
                Budget budget = new Budget(rs.getString("source"), rs.getInt("amount"),
                        rs.getString("category"), rs.getString("insertion_date"), rs.getString("end_date"));
                budgets.add(budget);
            }
            if(!budgets.isEmpty()) {
                return  (Budget[]) budgets.toArray();
            }
            return null;
        } catch(SQLException e) {
            System.out.println("Error retrieving Budgets");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public Budget retrieveBudget(int user_id, String source, String amount, String category) {
        String query = "SELECT * FROM Budget WHERE user_id = ? AND source = ? AND amount = ? and category = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            statement.setString(2, source);
            statement.setString(3, amount);
            statement.setString(4, category);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                Budget budget = new Budget(rs.getString("source"), rs.getInt("amount"),
                        rs.getString("category"), rs.getString("insertion_date"), rs.getString("end_date"));
                return budget;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error retrieving Budget");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
