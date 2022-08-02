import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataInitializer {
    Manager manager=new Manager();
    public  void insertAllInformation() throws SQLException {
        insertUsers(Main.connection);
        insertPosts(Main.connection);
        insertMessages(Main.connection);
        insertGroupMessage(Main.connection);
        insertGroup(Main.connection);
        insertBlocks(Main.connection);
        insertBusinessPost(Main.connection);
    }
    public void insertUsers(Connection connection) throws SQLException {
        int num = manager.users.size();
        ArrayList<User> users = manager.users;
        for (int i=0;i<num;i++){
            PreparedStatement preparedStatement = connection.prepareStatement("insert ignore into User values(" +
                    "?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, users.get(i).getId());
            preparedStatement.setString(2,users.get(i).getPassword());
            preparedStatement.setBoolean(3,users.get(i).isEntered());
            preparedStatement.setString(4,users.get(i).getNationalCode());
            preparedStatement.setString(5,users.get(i).getBusinessAccount());
            StringBuilder followerIds= new StringBuilder();
            for (int j=0;j<users.get(i).getFollowerIds().size();j++){
                followerIds.append(users.get(i).getFollowerIds().get(j));
                followerIds.append("-");
            }
            preparedStatement.setString(6, followerIds.toString());
            StringBuilder followingIds= new StringBuilder();
            for (int j=0;j<users.get(i).getFollowingIds().size();j++){
                followingIds.append(users.get(i).getFollowingIds().get(j));
                followingIds.append("-");
            }
            preparedStatement.setString(7, followingIds.toString());
            if (users.get(i).getBusinessAccount().equals("ordinary")) {
                StringBuilder postIds = new StringBuilder();
                for (int j = 0; j < users.get(i).getPostIds().size(); j++) {
                    postIds.append(users.get(i).getPostIds().get(j));
                    postIds.append("-");
                }
                preparedStatement.setString(8, postIds.toString());
            }
            else if (users.get(i).getBusinessAccount().equals("business")){
                StringBuilder businessPostIds = new StringBuilder();
                BusinessUser businessUser = (BusinessUser) users.get(i);
                for (int j = 0; j < businessUser.getPostIds().size(); j++) {
                    businessPostIds.append(businessUser.getPostIds().get(j));
                    businessPostIds.append("-");
                }
                preparedStatement.setString(8, businessPostIds.toString());
            }
            StringBuilder messageIds= new StringBuilder();
            for (int j=0;j<users.get(i).getMessageIds().size();j++){
                String str=Integer.toString(users.get(i).getMessageIds().get(j));
                messageIds.append(str);
                messageIds.append("-");
            }
            preparedStatement.setString(9, messageIds.toString());
            StringBuilder allFriends= new StringBuilder();
            for (int j=0;j<users.get(i).getAllFriendIds().size();j++){
                allFriends.append(users.get(i).getAllFriendIds().get(j));
                allFriends.append("-");
            }
            preparedStatement.setString(10, allFriends.toString());
            preparedStatement.setString(11,users.get(i).getImageAddress());
            preparedStatement.setString(12,users.get(i).getBackGround());
            preparedStatement.executeUpdate();
        }
    }
    public void insertPosts(Connection connection) throws SQLException
    {
        int num=manager.posts.size();
        ArrayList<Post> posts=manager.posts;
        for (int i=0;i<num;i++){
            if (!(posts.get(i) instanceof  BusinessPost) ) {
                PreparedStatement preparedStatement = connection.prepareStatement("insert ignore into Post values(?,?,?,?,?,?)");
                preparedStatement.setString(1, posts.get(i).getId());
                preparedStatement.setString(2, posts.get(i).userId);
                preparedStatement.setString(3, posts.get(i).getText());
                StringBuilder likeUsers = new StringBuilder();
                for (int j = 0; j < posts.get(i).getLikeUsersId().size(); j++) {
                    likeUsers.append(posts.get(i).getLikeUsersId().get(j));
                    likeUsers.append("-");
                }
                preparedStatement.setString(4, likeUsers.toString());
                StringBuilder commentIds = new StringBuilder();
                for (int j = 0; j < posts.get(i).getCommentsId().size(); j++) {
                    commentIds.append(posts.get(i).getCommentsId().get(j));
                    commentIds.append("-");
                }
                preparedStatement.setString(5, commentIds.toString());
                preparedStatement.setString(6,posts.get(i).getImage());
                preparedStatement.executeUpdate();
            }
        }
    }
    public void insertMessages(Connection connection) throws SQLException{
        int num=manager.messages.size();
        ArrayList<Message> messages=manager.messages;
        for (int i=0;i<num;i++){
            PreparedStatement preparedStatement = connection.prepareStatement("insert ignore into Message values(?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1,messages.get(i).getId());
            preparedStatement.setString(2,messages.get(i).getText());
            preparedStatement.setBoolean(3,messages.get(i).forwarded);
            preparedStatement.setString(4,messages.get(i).localDateToString);
            preparedStatement.setString(5,messages.get(i).getSender().getId());
            preparedStatement.setString(6,messages.get(i).getReceiver().getId());
            preparedStatement.setBoolean(7,messages.get(i).isSeen());
            preparedStatement.setString(8,messages.get(i).getTime());
            preparedStatement.executeUpdate();
        }
    }
    public void insertGroupMessage(Connection connection) throws SQLException{
        int num = manager.groupMessages.size();
        ArrayList<GroupMessage> groupMessages = manager.groupMessages;
        for (int i=0;i<num;i++){
            PreparedStatement preparedStatement = connection.prepareStatement("insert ignore into GroupMessage values" +
                    "(?,?,?,?,?,?,?)");
            preparedStatement.setString(1,groupMessages.get(i).getId());
            preparedStatement.setString(2,groupMessages.get(i).getText());
            preparedStatement.setString(3,groupMessages.get(i).localDateToString);
            preparedStatement.setString(4,groupMessages.get(i).groupId);
            preparedStatement.setString(5,groupMessages.get(i).getSender().getId());
            StringBuilder seen= new StringBuilder();
            for (int j=0;j<groupMessages.get(i).getSeen().size();j++){
                seen.append(groupMessages.get(i).getSeen().get(j).toString());
                seen.append("-");
            }
            preparedStatement.setString(6,seen.toString());
            preparedStatement.setString(7,groupMessages.get(i).getTime());
            preparedStatement.executeUpdate();
        }
    }
    public void insertGroup(Connection connection) throws SQLException {
        int num = manager.groups.size();
        ArrayList<Group> groups = manager.groups;
        for (int i=0;i<num;i++){
            PreparedStatement preparedStatement = connection.prepareStatement("insert ignore into grp values" +
                    "(?,?,?,?,?,?,?)");
            preparedStatement.setString(1,groups.get(i).getGroupId());
            preparedStatement.setString(2,groups.get(i).getGroupName());
            preparedStatement.setString(3,groups.get(i).getAdmin().getId());
            StringBuilder users= new StringBuilder();
            for (int j=0;j<groups.get(i).getUsers().size();j++){
                users.append(groups.get(i).getUsers().get(j).getId());
                users.append("-");
            }
            preparedStatement.setString(4,users.toString());
            StringBuilder banned= new StringBuilder();
            for (int j=0;j<groups.get(i).getBanned().size();j++){
                banned.append(groups.get(i).getBanned().get(j).toString());
                banned.append("-");
            }
            preparedStatement.setString(5,banned.toString());
            StringBuilder groupMessageIds= new StringBuilder();
            for (int j=0;j<groups.get(i).getGroupMessages().size();j++){
                groupMessageIds.append(groups.get(i).getGroupMessages().get(j).getId());
                groupMessageIds.append("-");
            }
            preparedStatement.setString(6,groupMessageIds.toString());
            preparedStatement.setString(7,groups.get(i).getImage());
            preparedStatement.executeUpdate();
        }
    }
    public void insertBlocks(Connection connection) throws SQLException {
        int num = manager.blocks.size();
        ArrayList<Block> blocks = manager.blocks;
        for (int i=0;i<num;i++){
            PreparedStatement preparedStatement = connection.prepareStatement("insert ignore into BlockUser values" +
                    "(?,?,?)");
            preparedStatement.setInt(1,i+1);
            preparedStatement.setString(2,blocks.get(i).getBlocker().getId());
            preparedStatement.setString(3,blocks.get(i).getBlocked().getId());
            preparedStatement.executeUpdate();
        }
    }
    public void insertBusinessPost(Connection connection) throws SQLException
    {
        int num = manager.businessPosts.size();
        ArrayList<BusinessPost> businessPosts = manager.businessPosts;
        for (int i=0;i<num;i++){
            PreparedStatement preparedStatement = connection.prepareStatement("insert ignore into BusinessPost values" +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,businessPosts.get(i).getId());
            preparedStatement.setString(2,businessPosts.get(i).userId);
            preparedStatement.setString(3,businessPosts.get(i).getText());
            StringBuilder likeUsers = new StringBuilder();
            for (int j=0;j<businessPosts.get(i).getLikeUsersId().size();j++){
                likeUsers.append(businessPosts.get(i).getLikeUsersId().get(j));
                likeUsers.append("-");
            }
            preparedStatement.setString(4,likeUsers.toString());
            StringBuilder comments = new StringBuilder();
            for (int j=0;j<businessPosts.get(i).getCommentsId().size();j++){
                comments.append(businessPosts.get(i).getCommentsId().get(j));
                comments.append("-");
            }
            preparedStatement.setString(5,comments.toString());
            StringBuilder viewers = new StringBuilder();
            for (int j=0;j<businessPosts.get(i).getViewers().size();j++){
                viewers.append(businessPosts.get(i).getViewers().get(j));
                viewers.append("-");
            }
            preparedStatement.setString(6,viewers.toString());
            preparedStatement.setString(7,businessPosts.get(i).releasedTime.toString());
            StringBuilder likesUsersForTable= new StringBuilder();
            for (int j=0;j<businessPosts.get(i).likesUsersForTable.size();j++){
                likesUsersForTable.append(businessPosts.get(i).likesUsersForTable.get(j));
                likesUsersForTable.append("-");
            }
            preparedStatement.setString(8,likesUsersForTable.toString());
            StringBuilder likesLocalDateForTable= new StringBuilder();
            for (int j=0;j<businessPosts.get(i).likesLocalDateForTable.size();j++){
                likesLocalDateForTable.append(businessPosts.get(i).likesLocalDateForTable.get(j));
                likesLocalDateForTable.append(",");
            }
            preparedStatement.setString(9,likesLocalDateForTable.toString());

            StringBuilder viewUsersForTable = new StringBuilder();
            for (int j=0;j<businessPosts.get(i).viewUsersForTable.size();j++){
                viewUsersForTable.append(businessPosts.get(i).viewUsersForTable.get(j));
                viewUsersForTable.append("-");
            }
            preparedStatement.setString(10,viewUsersForTable.toString());
            StringBuilder viewLocalDatesForTable = new StringBuilder();
            for (int j=0;j<businessPosts.get(i).viewLocalDatesForTable.size();j++){
                viewLocalDatesForTable.append(businessPosts.get(i).viewLocalDatesForTable.get(j));
                viewLocalDatesForTable.append(",");
            }
            preparedStatement.setString(11,viewLocalDatesForTable.toString());

            StringBuilder favoriteNumberUser = new StringBuilder();
            for (int j=0;j<businessPosts.get(i).favoriteNumberUser.size();j++){
                favoriteNumberUser.append(businessPosts.get(i).favoriteNumberUser.get(j));
                favoriteNumberUser.append("-");
            }
            preparedStatement.setString(12,favoriteNumberUser.toString());
            StringBuilder favoriteNumberDouble = new StringBuilder();
            for (int j=0;j<businessPosts.get(i).favoriteNumberDouble.size();j++){
                favoriteNumberDouble.append(businessPosts.get(i).favoriteNumberDouble.get(j));
                favoriteNumberDouble.append("-");
            }
            preparedStatement.setString(13,favoriteNumberDouble.toString());
            preparedStatement.setString(14,businessPosts.get(i).getImage());
            preparedStatement.executeUpdate();
        }
    }
}