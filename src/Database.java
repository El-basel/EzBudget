import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static Database instance = null;
    Connection connection = null;
    int user_id = 0;
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
                "deadline TEXT NOT NULL," +
                "description TEXT NOT NULL," +
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
            statement.execute(reminderTable);
        }
    }

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
    public boolean insertGoal(Goal goal) {
        String query;
        if(goal.getStartDate() != null) {
            query = "INSERT INTO Goal(target, deadline, description, user_id, insertion_date) VALUES (?, ?, ?, ?, ?);";
        } else {
            query = "INSERT INTO Goal(target, deadline, description, user_id) VALUES (?, ?, ?, ?);";
        }
        try(PreparedStatement statement = connection.prepareStatement(query) ) {
            statement.setInt(1, goal.getTarget());
            statement.setString(2, goal.getEndDate());
            statement.setString(3, goal.getDiscription());
            statement.setInt(4, user_id);
            if(goal.getStartDate() != null) {
                statement.setString(5, goal.getStartDate());
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting Goal");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Goal[] retrieveGoals() {
        String query = "SELECT * FROM Goal WHERE user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user_id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Goal> goals = new ArrayList<>();
            while(rs.next()) {
                Goal goal = new Goal(rs.getInt("target"), rs.getString("insertion_date"),
                        rs.getString("end_date"), rs.getString("description"));
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

    public Goal retrieveGoal(int target, String description) {
        String query = "SELECT * FROM Goal WHERE target = ? AND description = ? AND user_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, target);
            statement.setString(2, description);
            statement.setInt(3, user_id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                return new Goal(rs.getInt("target"), rs.getString("insertion_date"),
                        rs.getString("end_date"), rs.getString("description"));
            }
            return null;
        } catch(SQLException e) {
            System.out.println("Error retrieving Budgets");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
