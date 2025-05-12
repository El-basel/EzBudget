public class NotificationService{
    private static volatile NotificationService instance;
    private Database database;

    private NotificationService() {
        this.database = Database.getInstance();
    }

    public static NotificationService getInstance() {
        if (instance == null) {
            synchronized (NotificationService.class) {
                if (instance == null) {
                    instance = new NotificationService();
                }
            }
        }
        return instance;
    }

    public void sendReminder(int reminderID){
    }
}