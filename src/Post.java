import java.util.ArrayList;

public class Post {
    String userId;
    String Id;
    String text="";
    ArrayList<String> likeUsersId = new ArrayList<>();
    ArrayList<String> commentsId = new ArrayList<>();
    String image="";

    public Post(String id, String userId , String text, ArrayList<String> likeUsersId, ArrayList<String> commentsId, String image) {

        Id = id;
        this.userId = userId;
        this.text = text;
        this.likeUsersId = likeUsersId;
        this.commentsId = commentsId;
        this.image = image;
    }

    public ArrayList<String> getLikeUsersId() {
        return likeUsersId;
    }

    public void setLikeUsersId(ArrayList<String> likeUsersId) {
        this.likeUsersId = likeUsersId;
    }

    public ArrayList<String> getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(ArrayList<String> commentsId) {
        this.commentsId = commentsId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Post(String userId, String id) {
        this.userId = userId;
        this.Id= id;
    }
}
