import java.util.ArrayList;

public class BusinessUser extends User {
    public BusinessUser(String id, String password, String nationalCode, String businessAccount) {
        super(id, password, nationalCode, businessAccount);
    }

    public BusinessUser(String id, String password, boolean entered, String nationalCode, String businessAccount, ArrayList<String> postIds, ArrayList<String> followerIds, ArrayList<String> followingIds, ArrayList<Integer> messageIds, ArrayList<String> allFriendIds, String imageAddress, String backGround) {
        super(id, password, entered, nationalCode, businessAccount, postIds, followerIds, followingIds, messageIds, allFriendIds,imageAddress,backGround);
    }
}