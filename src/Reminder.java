public class Reminder{
    private String title, date, message;

    public Reminder(){}
    public Reminder(String title, String date, String message){
        this.title = title;
        this.date = date;
        this.message = message;
    }

    //Setters and getters
    public String getTitle(){return title;}
    public String getDate(){return date;}
    public String getMessage(){return message;}
    public void setTitle(String title){this.title = title;}
    public void setDate(String date){this.date = date;}
    public void setMessage(String message){this.message = message;}
}