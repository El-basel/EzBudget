public class AnalyticsService {
    private static volatile AnalyticsService instance;
    private Database database;

    private AnalyticsService() {
        this.database = Database.getInstance();
    }

    public static AnalyticsService getInstance() {
        if (instance == null) {
            synchronized (AnalyticsService.class) {
                if (instance == null) {
                    instance = new AnalyticsService();
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
