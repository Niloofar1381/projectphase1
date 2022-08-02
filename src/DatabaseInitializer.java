import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public void createTables(Connection connection) throws SQLException{
        initUserTable(connection.createStatement());
        initPostTable(connection.createStatement());
        initMessageTable(connection.createStatement());
        initGroupMessageTable(connection.createStatement());
        initGroupTable(connection.createStatement());
        initBlock(connection.createStatement());
        initBusinessPost(connection.createStatement());
    }

    private void initUserTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + "User(id varchar(20) PRIMARY KEY," +
                "password varchar(20),entered boolean,nationalCode varchar(20),businessAccount varchar(20)," +
                "followerIds varchar(100),followingIds varchar(100),postIds varchar(100),messageIds varchar(100)," +
                "allFriendIds varchar(100),ImageAddress varchar(100),backGround varchar(100))");
    }

    private void initPostTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Post(id varchar(20) PRIMARY KEY,userId varchar(20),postText varchar(100)," +
                "likeUsers varchar(20),comments varchar(100),image varchar(100))");
    }

    private void initMessageTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Message(id int PRIMARY KEY,messageText varchar(100)," +
                "forwarded boolean,localDate varchar(20),sender varchar(20),receiver varchar(20),seen boolean,time varchar(20))");
    }
    private void initGroupMessageTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + "GroupMessage(id varchar(20) PRIMARY KEY,groupText varchar(100)," +
                "localDate varchar(20),groupId varchar(20),sender varchar(20),seen varchar(100),time varchar(20))");
    }
    private void initGroupTable(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + "Grp(id varchar(20) PRIMARY KEY,name varchar(20),admin varchar(20)," +
                "users varchar(100),banned varchar(100),groupMessage varchar(100),image varchar(100))");
    }
    private void initBlock(Statement statement) throws SQLException{
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + "BlockUser(id " +
                "int PRIMARY KEY,blocker varchar(20),blocked varchar(20))");
    }
    private void initBusinessPost(Statement statement) throws SQLException{
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + "businessPost(id varchar(20) PRIMARY KEY" +
                ",userId varchar(20),postText varchar(100)," +
                "likeUsers varchar(20),comments varchar(100),viewers varchar(100),releasedTime varchar(20)," +
                "likesUsersForTable varchar(100),likesLocalDateForTable varchar(100),viewUsersForTable varchar(100)" +
                ",viewLocalDatesForTable varchar(100),favoriteNumberUser varchar(100),favoriteNumberDouble varchar(100),image varchar(100))");
    }
}