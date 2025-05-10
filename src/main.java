public class main {
    public static void main(String[] args) {
        DB database = new DB();
        Authenticator authenticator = new Authenticator(database);
        UI ui = new UI(authenticator);

        ui.start();
    }
}
