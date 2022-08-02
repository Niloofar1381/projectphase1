import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
public class BusinessPost  extends Post{
    HashMap<User,Double> favoriteNumbers=new HashMap<>();
    ArrayList<String>viewers=new ArrayList<>();
    HashMap<User,LocalDate> likes=new HashMap<>();
    ArrayList <String>favoriteNumberUser=new ArrayList<>();
    ArrayList<Double>favoriteNumberDouble=new ArrayList<>();
    HashMap<User,LocalDate> views=new HashMap<>();
    LocalDate releasedTime = LocalDate.now();
    //for setting database:
    ArrayList<String> likesUsersForTable = new ArrayList<>();
    ArrayList<LocalDate> likesLocalDateForTable = new ArrayList<>();
    ArrayList<String> viewUsersForTable = new ArrayList<>();
    ArrayList<LocalDate> viewLocalDatesForTable = new ArrayList<>();

    public BusinessPost(String id,String userId,String text, ArrayList<String> likeUsersId, ArrayList<String> commentsId, ArrayList<String> favoriteNumberUser,ArrayList<Double> favoriteNumberDouble, ArrayList<String> viewers, ArrayList<String>likesUsersForTable,ArrayList<LocalDate>likesLocalDateForTable, ArrayList<String>viewUsersForTable,ArrayList<LocalDate>viewLocalDatesForTable, LocalDate releasedTime, String image) {
        super(id, userId,text, likeUsersId, commentsId,image);
        this.favoriteNumberUser = favoriteNumberUser;
        this.favoriteNumberDouble = favoriteNumberDouble;
        this.viewers = viewers;
        this.likesUsersForTable = likesUsersForTable;
        this.likesLocalDateForTable=likesLocalDateForTable;
        this.viewUsersForTable = viewUsersForTable;
        this.viewLocalDatesForTable=viewLocalDatesForTable;
        this.releasedTime = releasedTime;
    }

    public ArrayList<String> getViewers() {
        return viewers;
    }

    public void setViewers(ArrayList<String> viewers) {
        this.viewers = viewers;
    }

    public BusinessPost(String userId, String id) {
        super(userId, id);
    }
}