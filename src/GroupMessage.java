import java.time.LocalDate;
import java.util.ArrayList;

public class GroupMessage {
    User sender;
    String text;
    String id;
    String groupId;
    LocalDate localDate;
    String time;

    public GroupMessage(User sender, String text, String id, String groupId, LocalDate localDate, ArrayList<Boolean> seen, String time) {
        this.sender = sender;
        this.text = text;
        this.id = id;
        this.groupId = groupId;
        this.localDate = localDate;
        this.seen = seen;
        this.time = time;
    }

    public ArrayList<Boolean> getSeen() {
        return seen;
    }

    public void setSeen(ArrayList<Boolean> seen) {
        this.seen = seen;
    }

    String localDateToString;
    ArrayList<Boolean> seen=new ArrayList<>();
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public GroupMessage(User sender, String text, String id, String groupId, LocalDate localDate) {
        this.sender = sender;
        this.text = text;
        this.id = id;
        this.groupId=groupId;
        this.localDate=localDate;
    }
}
