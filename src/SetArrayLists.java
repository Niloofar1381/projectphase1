import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class SetArrayLists {
    Manager manager = new Manager();

    public void setAllArrayLists() throws SQLException {
        setUserArrayList(Main.connection);
        setPostsArrayList(Main.connection);
        setBusinessPosts(Main.connection);
        setBlockArrayList(Main.connection);
        setGroupMessages(Main.connection);
        setGroupArrayList(Main.connection);
        setMessagesArrayList(Main.connection);
    }
    public void setUserArrayList(Connection connection) throws SQLException
    {
        boolean sameId=false;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from User");
        while (resultSet.next()) {
            ArrayList<String> followerIds = new ArrayList<>();
            ArrayList<String> followingIds = new ArrayList<>();
            ArrayList<String> postIds = new ArrayList<>();
            ArrayList<Integer> messageIds = new ArrayList<>();
            ArrayList<String> allFriendIds = new ArrayList<>();
            String[] splitInputFollowers = resultSet.getString("followerIds").split("-");
            String[] splitInputFollowings = resultSet.getString("followingIds").split("-");
            String[] splitInputPosts = resultSet.getString("postIds").split("-");
            String[] splitInputMessages = resultSet.getString("messageIds").split("-");
            String[] splitInputAllFriends = resultSet.getString("allFriendIds").split("-");
            String follower = resultSet.getString("followerIds");
            String following = resultSet.getString("followingIds");
            String post = resultSet.getString("postIds");
            String message = resultSet.getString("messageIds");
            String friend = resultSet.getString("allFriendIds");
            if (follower.contains("-")) {
                for (int i = 0; i < splitInputFollowers.length; i++) {
                    followerIds.add(splitInputFollowers[i]);
                }
            }
            if (following.contains("-")) {
                for (int i = 0; i < splitInputFollowings.length; i++) {
                    followingIds.add(splitInputFollowings[i]);
                }
            }
            if (message.contains("-")) {
                for (int i = 0; i < splitInputMessages.length; i++) {
                    messageIds.add(Integer.parseInt(splitInputMessages[i]));
                }
            }
            if (friend.contains("-")) {
                for (int i = 0; i < splitInputAllFriends.length; i++) {
                    allFriendIds.add(splitInputAllFriends[i]);
                }
            }
            if (resultSet.getString("businessAccount").equals("ordinary")){
                if (post.contains("-")) {
                    for (int i = 0; i < splitInputPosts.length; i++) {
                        postIds.add(splitInputPosts[i]);
                    }
                }
                User user = new User(
                        resultSet.getString("id"), resultSet.getString("password"),
                        resultSet.getBoolean("entered"),resultSet.getString("nationalCode"),
                        resultSet.getString("businessAccount"),postIds,followerIds,followingIds,messageIds,allFriendIds,
                        resultSet.getString("imageAddress"),resultSet.getString("backGround"));
                for (int i = 0; i < Manager.users.size(); i++) {
                    if(Manager.users.get(i).getId().equals(user.getId()))
                    {
                        sameId=true;
                        break;
                    }
                }
                if(!sameId)
                {
                    Manager.users.add(user);
                }

            }
            else if (resultSet.getString("businessAccount").equals("business")){
                if (post.contains("-")) {
                    for (int i = 0; i < splitInputPosts.length; i++) {
                        postIds.add(splitInputPosts[i]);
                    }
                }
                BusinessUser businessUser = new BusinessUser(resultSet.getString("id"), resultSet.getString("password"),
                        resultSet.getBoolean("entered"),resultSet.getString("nationalCode"),
                        resultSet.getString("businessAccount"),postIds,followerIds,followingIds,messageIds,allFriendIds,resultSet.getString("imageAddress"),
                        resultSet.getString("backGround"));
                for (int i = 0; i < Manager.businessUsers.size(); i++) {
                    if(Manager.businessUsers.get(i).getId().equals(businessUser.getId()))
                    {
                        sameId=true;
                        break;
                    }
                }
                if(!sameId)
                {
                    Manager.businessUsers.add(businessUser);
                    User user1=(User) businessUser;
                    Manager.users.add(user1);
                }

            }
        }
        statement.close();
    }
    public void setPostsArrayList(Connection connection) throws SQLException
    {
        boolean sameId=false;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from Post");
        while (resultSet.next()){
            ArrayList<String> likeUsersId = new ArrayList<>();
            ArrayList<String> commentsId = new ArrayList<>();
            String[] splitInputLikeUsers = resultSet.getString("likeUsers").split("-");
            String[] splitInputComments = resultSet.getString("comments").split("-");
            String likes = resultSet.getString("likeUsers");
            String comment = resultSet.getString("comments");
            if (likes.contains("-"))
            {
                for (int i=0;i< splitInputLikeUsers.length;i++){
                    likeUsersId.add(splitInputLikeUsers[i]);
                }
            }
            if (comment.contains("-")){
                for (int i=0;i< splitInputComments.length;i++){
                    commentsId.add(splitInputComments[i]);
                }
            }
            Post post = new Post(resultSet.getString("id"),resultSet.getString("userId"),
                    resultSet.getString("postText"),likeUsersId,commentsId, resultSet.getString("image"));
            for (int i = 0; i < Manager.posts.size(); i++) {
                if(Manager.posts.get(i).getId().equals(post.getId()))
                {
                    sameId=true;
                    break;
                }
            }
            if(!sameId)
            {
                Manager.posts.add(post);
            }
        }
        statement.close();
    }
    public void setMessagesArrayList(Connection connection) throws SQLException
    {
        boolean sameId=false;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from message");
        while (resultSet.next()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = resultSet.getString("localDate");
            LocalDate localDate = LocalDate.parse(date, formatter);
            Message message = new Message(manager.findId(resultSet.getString("sender")),resultSet.getString("messageText"),
                    manager.findId(resultSet.getString("receiver")),Integer.parseInt(resultSet.getString("id")),
                    resultSet.getBoolean("forwarded"), localDate,resultSet.getBoolean("seen"),resultSet.getString("time"));
            for (int i = 0; i < Manager.messages.size(); i++) {
                if(Manager.messages.get(i).getId()==message.getId())
                {
                    sameId=true;
                    break;
                }
            }
            if(!sameId)
            {
                manager.messages.add(message);
            }
        }
        statement.close();
    }
    public void setBlockArrayList(Connection connection) throws SQLException{
        boolean sameId=false;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from blockUser");
        while (resultSet.next()){
            Block block = new Block(manager.findId(resultSet.getString("blocker")),
                    manager.findId(resultSet.getString("blocked")));
            for (int i = 0; i < Manager.blocks.size(); i++) {
                if(Manager.blocks.get(i).blocker.equals(block.blocker)&&
                        Manager.blocks.get(i).blocked.equals(block.blocked))
                {
                    sameId=true;
                    break;
                }
            }
            if(!sameId)
            {
                Manager.blocks.add(block);
            }
        }
        statement.close();
    }
    public void setGroupArrayList(Connection connection) throws SQLException
    {
        boolean sameId=false;
        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from grp");
        while (resultSet.next())
        {
            ArrayList<User>users=new ArrayList<>();
            ArrayList<Boolean>banned=new ArrayList<>();
            ArrayList<GroupMessage>groupMessages=new ArrayList<>();
            String[] splitInputUsers=resultSet.getString("users").split("-");
            String[] splitInputBanned=resultSet.getString("banned").split("-");
            String[] splitInputGroupMessages=resultSet.getString("groupMessage").split("-");
            String user=resultSet.getString("users");
            String bann=resultSet.getString("banned");
            String groupMessage=resultSet.getString("groupMessage");
            if(user.contains("-")) {
                for (int i = 0; i < splitInputUsers.length; i++) {
                    users.add(manager.findId(splitInputUsers[i]));
                }
            }
            if(bann.contains("-"))
            {
                for (int i = 0; i < splitInputBanned.length; i++) {
                    banned.add(Boolean.parseBoolean(splitInputBanned[i]));
                }
            }
            if(groupMessage.contains("-"))
            {
                for (int i = 0; i < splitInputGroupMessages.length; i++) {
                    groupMessages.add(manager.findGroupMessage(splitInputGroupMessages[i]));
                }
            }
            Group group=new Group(manager.findId(resultSet.getString("admin")),resultSet.getString("name")
                    ,resultSet.getString("id"),users,banned,groupMessages, resultSet.getString("image"));
            for (int i = 0; i < Manager.groups.size(); i++) {
                if(Manager.groups.get(i).groupId.equals(group.groupId))
                {
                    sameId=true;
                    break;
                }
            }
            if(!sameId)
            {
                Manager.groups.add(group);
            }
        }
        statement.close();
    }
    public void setGroupMessages(Connection connection) throws SQLException
    {
        boolean sameId=false;
        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from groupMessage");
        while (resultSet.next())
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String date = resultSet.getString("localDate");
            LocalDate localDate = LocalDate.parse(date, formatter);
            ArrayList<Boolean>seen=new ArrayList<>();
            String[] splitInputSeen=resultSet.getString("seen").split("-");
            String Seen=resultSet.getString("seen");
            if(Seen.contains("-"))
            {
                for (int i = 0; i < splitInputSeen.length; i++) {
                    seen.add(Boolean.parseBoolean(splitInputSeen[i]));
                }
            }
            GroupMessage groupMessage=new GroupMessage(manager.findId(resultSet.getString("sender")),
                    resultSet.getString("groupText"),resultSet.getString("id"),
                    resultSet.getString("groupId"),localDate,seen,resultSet.getString("time"));
            for (int i = 0; i < Manager.groupMessages.size(); i++) {
                if(Manager.groupMessages.get(i).getId().equals(groupMessage.getId()))
                {
                    sameId=true;
                    break;
                }
            }
            if(!sameId)
            {
                Manager.groupMessages.add(groupMessage);
            }
        }
        statement.close();
    }
    public void setBusinessPosts(Connection connection) throws SQLException
    {
        boolean sameId=false;
        Statement statement = connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from businessPost");
        while (resultSet.next())
        {
            ArrayList<String>likeUsersId=new ArrayList<>();
            String[] splitInputLikeUsers=resultSet.getString("likeUsers").split("-");
            String LikeUsers=resultSet.getString("likeUsers");
            if(LikeUsers.contains("-"))
            {
                for (int i = 0; i < splitInputLikeUsers.length; i++) {
                    likeUsersId.add(splitInputLikeUsers[i]);
                }
            }
            ArrayList<String>commentsId=new ArrayList<>();
            String[] splitInputComments=resultSet.getString("comments").split("-");
            String Comments=resultSet.getString("comments");
            if(Comments.contains("-"))
            {
                for (int i = 0; i < splitInputComments.length; i++) {
                    commentsId.add(splitInputComments[i]);
                }
            }
            ArrayList<String>viewers=new ArrayList<>();
            String[] splitInputViewers=resultSet.getString("viewers").split("-");
            String Viewers=resultSet.getString("viewers");
            if(Viewers.contains("-"))
            {
                for (int i = 0; i < splitInputViewers.length; i++) {
                    viewers.add(manager.findId(splitInputViewers[i]).getId());
                }
            }
            ArrayList<String> favoriteNumberUser = new ArrayList<>();
            String[] splitInputFavoriteNumberUser=resultSet.getString("favoriteNumberUser").split("-");
            String FavoriteNumberUser=resultSet.getString("favoriteNumberUser");
            if (FavoriteNumberUser.contains("-")){
                for (int i=0;i<splitInputFavoriteNumberUser.length;i++) {
                    favoriteNumberUser.add(splitInputFavoriteNumberUser[i]);
                }
            }
            ArrayList<Double> favoriteNumberDouble = new ArrayList<>();
            String[] splitInputFavoriteNumberDouble=resultSet.getString("favoriteNumberDouble").split("-");
            String FavoriteNumberDouble = resultSet.getString("favoriteNumberDouble");
            if (FavoriteNumberDouble.contains("-")){
                for (int i=0;i<splitInputFavoriteNumberDouble.length;i++){
                    favoriteNumberDouble.add(Double.parseDouble(splitInputFavoriteNumberDouble[i]));
                }
            }
            ArrayList<String>likeUsersForTable=new ArrayList<>();
            String[] splitInputLikeUsersForTable=resultSet.getString("likesUsersForTable").split("-");
            String LikeUsersForTable=resultSet.getString("likesUsersForTable");
            if(LikeUsersForTable.contains("-"))
            {
                for (int i = 0; i < splitInputLikeUsersForTable.length; i++) {
                    likeUsersForTable.add(manager.findId(splitInputLikeUsersForTable[i]).getId());
                }
            }
            ArrayList<LocalDate>likesLocalDateForTable=new ArrayList<>();
            String[] splitInputLikesLocalDateForTable=resultSet.getString("likesLocalDateForTable").split(",");
            String LikesLocalDateForTable=resultSet.getString("likesLocalDateForTable");
            if(LikesLocalDateForTable.contains(","))
            {
                for (int i = 0; i < splitInputLikesLocalDateForTable.length; i++) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(splitInputLikesLocalDateForTable[i],formatter);
                    likesLocalDateForTable.add(localDate);
                }
            }
            ArrayList<String>viewUsersForTable=new ArrayList<>();
            String[] splitInputViewUsersForTable=resultSet.getString("viewUsersForTable").split("-");
            String ViewUsersForTable=resultSet.getString("viewUsersForTable");
            if(ViewUsersForTable.contains("-"))
            {
                for (int i = 0; i < splitInputViewUsersForTable.length; i++) {
                    viewUsersForTable.add(manager.findId(splitInputViewUsersForTable[i]).getId());
                }
            }
            ArrayList<LocalDate>viewLocalDatesForTable=new ArrayList<>();
            String[] splitInputViewLocalDatesForTable=resultSet.getString("viewLocalDatesForTable").split(",");
            String ViewLocalDatesForTable=resultSet.getString("viewLocalDatesForTable");
            if(ViewLocalDatesForTable.contains(","))
            {
                for (int i = 0; i < splitInputViewLocalDatesForTable.length; i++) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.parse(splitInputViewLocalDatesForTable[i],formatter);
                    viewLocalDatesForTable.add(localDate);
                }
            }
            LocalDate localDate = LocalDate.parse(resultSet.getString("releasedTime"));
            BusinessPost businessPost=new BusinessPost(
                    resultSet.getString("id"),resultSet.getString("userId"),resultSet.getString("postText"),likeUsersId,commentsId,
                    favoriteNumberUser,favoriteNumberDouble,viewers,likeUsersForTable
                    ,likesLocalDateForTable,viewUsersForTable,viewLocalDatesForTable,localDate, resultSet.getString("image"));
            for (int i = 0; i < Manager.businessPosts.size(); i++) {
                if(Manager.businessPosts.get(i).getId().equals(businessPost.getId()))
                {
                    sameId=true;
                    break;
                }
            }
            if(!sameId)
            {
                Manager.businessPosts.add(businessPost);
                Manager.posts.add(businessPost);
            }
        }
        statement.close();
    }
}