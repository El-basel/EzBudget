public class NotificationService{
    private static volatile NotificationService instance;
    private Database database;

    private NotificationService(Database database) {
        this.database = database;
    }

    public static NotificationService getInstance(Database database) {
        if (instance == null) {
            synchronized (NotificationService.class) {
                if (instance == null) {
                    instance = new NotificationService(database);
                }
            }
        }
        return instance;
    }

    public void sendReminder(int reminderID){
    }
}