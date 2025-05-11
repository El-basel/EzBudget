public class Reminder{
    private int userID;
    private String title, date, message;

    public Reminder(){}
    public Reminder(int userID, String title, String date, String message){
        this.userID = userID;
        this.title = title;
        this.date = date;
        this.message = message;
    }

    //Setters and getters
    public int getUserID(){return userID;}
    public String getTitle(){return title;}
    public String getDate(){return date;}
    public String getMessage(){return message;}
    public void setUserID(int userID){this.userID = userID;}
    public void setTitle(String title){this.title = title;}
    public void setDate(String date){this.date = date;}
    public void setMessage(String message){this.message = message;}
}