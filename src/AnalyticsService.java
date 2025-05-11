public class AnalyticsService {
    private static volatile AnalyticsService instance;
    private Database database;

    private AnalyticsService(Database database) {
        this.database = database;
    }

    public static AnalyticsService getInstance(Database database) {
        if (instance == null) {
            synchronized (AnalyticsService.class) {
                if (instance == null) {
                    instance = new AnalyticsService(database);
                }
            }
        }
        return instance;
    }

    public void analyzeSpending(int start, int end){

    }

    public void generateReport(int start, int end){

    }
}
