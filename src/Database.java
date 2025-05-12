import java.sql.*;
import java.util.ArrayList;

/**
 * A class to handle database insertion and retrieval
 * @author Mahmoud
 */
public class Database {
    private static Database instance = null;
    Connection connection = null;
    int user_id = 0;

    /**
     * A private constructor for creating a singleton
     */
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

    /**
     * Returns an instance or creates a new one and returns it if one didn't exist
     * @return an instance of Database
     */
    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Creates the table if they don't exist, called in the constructor
     * @throws SQLException if the creation didn't success
     */
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
                "category TEXT NOT NULL," +
                "limit INTEGER NOT NULL," +
                "end_date TEXT NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "insertion_date TEXT DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES User(id));" ;

        // don't understand it fully
        String goalTable = "CREATE TABLE IF NOT EXISTS Goal (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "target INTEGER NOT NULL," +
                "amount INTEGER NOT NULL," +
                "description TEXT NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "insertion_date TEXT DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (user_id) REFERENCES User(id));";
        String reminderTable = "CREATE TABLE IF NOT EXISTS Reminder (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "reminder_date TEXT NOT NULL," +
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
            statement.execute(reminderTable);
        }
    }

    /**
     * Inserting user in
     * @param user contains user information required for the insertion
     * @return true if the insertion succeeded otherwise false
     */
    public boolean insertUser(User user) {
        String query = "INSERT INTO User (username, email, password) VALUES (?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
           statement.setString(1, user.getUsername());
           statement.setString(2, user.getEmail());
           statement.setString(3, user.getPassword());
           statement.executeUpdate();
           ResultSet generatedKeys = statement.getGeneratedKeys();
           if (generatedKeys.next()) {
               user_id = generatedKeys.getInt(1);
           }
           if(user_id == 0) {
               throw new SQLException("Insert failed");
           }
           return true;
        } catch (SQLException e) {
            System.out.println("Error Inserting User");
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    /**
     * Return a user that exists in the database
     * @param email
     * @param password
     * @return user with the provided email and password otherwise null is returned
     */
    public User getUser(String email, String password) {
        String query = "SELECT * FROM User WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            user_id = rs.getInt("id");
           return new User(rs.getString("username"), rs.getString("password"), rs.getString("email"));
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("User not found");
            return null;
        }
    }

    /**
     * Inserts income in the database with the provided information
     * @param income income's information to be inserted
     * @return true if the insertion succeeded otherwise false
     */
    public boolean insertIncome(Income income) {
        String query = "INSERT INTO Income (source, amount, user_id) VALUES (?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, income.getSource());
            statement.setInt(2, income.getAmount());
            statement.setInt(3, user_id);
            statement.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Error inserting income");
            return false;
        }
    }
    /**
     * Returns all incomes for the user currently registered
     * @return an array of type Income that corresponds to the registered user otherwise null is returned
     */
    public Income[] retrieveIncomes() {
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

    public Income retrieveIncome(String source, int amount) {
        String query = "SELECT * FROM Income WHERE source = ? AND amount = ? AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, source);
            statement.setInt(2, amount);
            statement.setInt(3, user_id);
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
    /**
     * Inserts an expense in the database with the provided information
     * @param expense expense's information to be inserted
     * @return true if the insertion succeeded otherwise false
     */
    public boolean insertExpense(Expense expense) {
        String query = "INSERT INTO Expense (item, amount, user_id) VALUES (?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, expense.getItem());
            statement.setInt(2, expense.getAmount());
            statement.setInt(3, user_id);
            statement.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Error Inserting Expense");
            return false;
        }

    }
    /**
     * Returns all expenses for the user currently registered
     * @return an array of type Expense that corresponds to the registered user otherwise null is returned
     */
    public Expense[] retrieveExpenses() {
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

    public Expense retrieveExpense(String item, int amount) {
        String query = "SELECT * FROM Expense WHERE item = ? AND amount = ? AND user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item);
            statement.setInt(2, amount);
            statement.setInt(3, user_id);
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
    /**
     * Inserts a budget in the database with the provided information
     * @param budget budget's information to be inserted
     * @return true if the insertion succeeded otherwise false
     */
    public boolean insertBudget(Budget budget) {
        String query;
        if(budget.getStart_date() != null) {
            query = "INSERT INTO Budget (category, limit, end_date, user_id, insertion_date) VALUES (?, ?, ?, ?, ?);";
        } else {
            query = "INSERT INTO Budget (category, limit, end_date, user_id) VALUES (?, ?, ?, ?);";
        }
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, budget.getCategory());
            statement.setInt(2, budget.getLimit());
            statement.setString(3, budget.getEnd_date());
            statement.setInt(4, user_id);
            if(budget.getStart_date() != null) {
                statement.setString(5, budget.getStart_date());
            }
            statement.executeQuery();
            return true;
        } catch (Exception e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("Error Inserting Expense");
            return false;
        }

    }
    /**
     * Returns all budgets for the user currently registered
     * @return an array of type Budget that corresponds to the registered user otherwise null is returned
     */
    public Budget[] retrieveBudgets() {
        String query = "SELECT * FROM Budget WHERE user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Budget> budgets = new ArrayList<>();
            while(rs.next()) {
                Budget budget = new Budget(rs.getString("category"), rs.getInt("limit"),
                       rs.getString("insertion_date"), rs.getString("end_date"));
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
    public Budget retrieveBudget(String category, int limit) {
        String query = "SELECT * FROM Budget WHERE limit = ? and category = ? AND user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, limit);
            statement.setString(2, category);
            statement.setInt(3, user_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                Budget budget = new Budget(rs.getString("category"), rs.getInt("limit"),
                        rs.getString("insertion_date"), rs.getString("end_date"));
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
    /**
     * Inserts a goal in the database with the provided information
     * @param goal goal's information to be inserted
     * @return true if the insertion succeeded otherwise false
     */
    public boolean insertGoal(Goal goal) {
        String query;
        query = "INSERT INTO Goal(target, amount, description, user_id) VALUES (?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(query) ) {
            statement.setInt(1, goal.getTarget());
            statement.setInt(2, goal.getSaving_amount());
            statement.setString(3, goal.getDescription());
            statement.setInt(4, user_id);
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting Goal");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Returns all goals for the user currently registered
     * @return an array of type Goal that corresponds to the registered user otherwise null is returned
     */
    public Goal[] retrieveGoals() {
        String query = "SELECT * FROM Goal WHERE user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Goal> goals = new ArrayList<>();
            while(rs.next()) {
                Goal goal = new Goal(rs.getInt("target"), rs.getInt("amount"), rs.getString("description"));
                goals.add(goal);
            }
            if(!goals.isEmpty()) {
                return  (Goal[]) goals.toArray();
            }
            return null;
        } catch(SQLException e) {
            System.out.println("Error retrieving Budgets");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Goal retrieveGoal(int target, int saving_amount, String description) {
        String query = "SELECT * FROM Goal WHERE target = ? AND amount = ? AND description = ? AND user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, target);
            statement.setInt(2, saving_amount);
            statement.setString(3, description);
            statement.setInt(4, user_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return new Goal(rs.getInt("target"), rs.getInt("amount"), rs.getString("description"));
            }
            return null;
        } catch(SQLException e) {
            System.out.println("Error retrieving Budgets");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Inserts a reminder in the database with the provided information
     * @param reminder reminder's information to be inserted
     * @return true if the insertion succeeded otherwise false
     */
    public boolean insertReminder(Reminder reminder) {
        String query = "INSERT INTO Reminder (title, reminder_date, message) VALUES (?, ?, ?);";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, reminder.getTitle());
            statement.setString(2, reminder.getDate());
            statement.setString(3, reminder.getMessage());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error Inserting User");
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
    /**
     * Returns all reminders for the user currently registered
     * @return an array of type Reminder that corresponds to the registered user otherwise null is returned
     */
    public Reminder[] retrieveReminders() {
        String query = "SELECT * FROM Reminder WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Reminder> reminders = new ArrayList<>();
            while(rs.next()) {
                Reminder reminder = new Reminder(rs.getString("title"), rs.getString("reminder_date")
                , rs.getString("message"));
                reminders.add(reminder);
            }
            return  (Reminder[]) reminders.toArray();
        } catch (Exception e) {
            System.out.println("Error retrieving Incomes");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sums user's incomes
     * @return The total sum of user's incomes
     */
    public int incomeSum() {
        String query = "SELECT SUM(amount) AS amount_sum FROM Income WHERE user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("amount_sum");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     * Sums user's expenses
     * @return The total sum of user's expenses
     */
    public int expenseSum() {
        String query = "SELECT SUM(amount) AS amount_sum FROM Expense WHERE user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("amount_sum");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
