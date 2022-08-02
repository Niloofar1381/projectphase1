import java.util.ArrayList;

public class Group {
    User admin;
    String groupName;
    String groupId;
    ArrayList<User>users=new ArrayList<>();
    ArrayList<Boolean>banned=new ArrayList<>();
    ArrayList<GroupMessage>groupMessages=new ArrayList<>();
    String image;


    public Group(User admin, String groupName, String groupId, ArrayList<User> users, ArrayList<Boolean> banned, ArrayList<GroupMessage> groupMessages, String image) {
        this.admin = admin;
        this.groupName = groupName;
        this.groupId = groupId;
        this.users = users;
        this.banned = banned;
        this.groupMessages = groupMessages;
        this.image = image;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Boolean> getBanned() {
        return banned;
    }

    public void setBanned(ArrayList<Boolean> banned) {
        this.banned = banned;
    }

    public ArrayList<GroupMessage> getGroupMessages() {
        return groupMessages;
    }

    public void setGroupMessages(ArrayList<GroupMessage> groupMessages) {
        this.groupMessages = groupMessages;
    }

    public Group(User admin, String groupName, String groupId) {
        this.admin = admin;
        this.groupName = groupName;
        this.groupId = groupId;
    }

}
