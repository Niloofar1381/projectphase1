import java.time.LocalDate;

public class Message {
    private User sender;
    private String text;
    private User receiver;
    private int id;
    boolean forwarded=false;
    private LocalDate localDate;
    public String localDateToString;
    private boolean seen = false;
    private String time;

    public Message(User sender, User receiver, int id, String text,LocalDate localDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.id = id;
        this.text=text;
        this.localDate=localDate;
    }
    public Message(User sender, String text, User receiver, int id, boolean forwarded, LocalDate localDate,boolean seen,String time) {
        this.sender = sender;
        this.text = text;
        this.receiver = receiver;
        this.id = id;
        this.forwarded = forwarded;
        this.localDate = localDate;
        this.seen = seen;
        this.time = time;
    }
    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

