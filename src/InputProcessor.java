import com.sun.nio.sctp.MessageInfo;

import java.sql.SQLException;
import java.util.Scanner;

public class InputProcessor {
    private Scanner sc=new Scanner(System.in);
    boolean invalidCommand=true;
    public void start() throws SQLException {
        Manager manager=new Manager();
        while(true)
        {
            String input=sc.nextLine();
            if(input.contains("register"))
            {
                String[] splitInput=input.split("\\s");
                manager.register(splitInput);
            }
            else if (input.contains("login"))
            {
                if (manager.checkLogin()==null) {
                    String[] splitInput = input.split("\\s");
                    manager.login(splitInput);
                }
            }
            else if(input.contains("show")&&input.contains("profile"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput=input.split("\\s");
                    manager.showProfile(splitInput);
                }
            }
            else if (input.contains("create post"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    invalidCommand=false;
                    manager.createPost();
                }
            }
            else if(input.contains("add comment"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    invalidCommand=false;
                    String[] splitInput=input.split("\\s");
                    manager.addComment(splitInput);

                }
            }
            else if(input.contains("LIKE"))
            {
                if (manager.checkLogin()==null){
                    System.out.println("no one logged in...");
                }
                else
                {
                    String[] splitInput=input.split("\\s");
                    manager.like(splitInput);
                }
            }
            else if (input.contains("show comments")) {
                if (manager.checkLogin() == null) {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.showComments(splitInput);
                }
            }
            else if(input.contains("show likes")) {
                if (manager.checkLogin() == null) {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.showLikes(splitInput);
                }
            }
            else if(input.contains("show main page"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    manager.showMainPage();
                }
            }
            else if (input.contains("show stats"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.showStats(splitInput);
                }
            }
            else if(input.contains("start private message"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    invalidCommand=false;
                    String[] splitInput = input.split("\\s");
                    manager.startPrivateMessage(splitInput);

                }
            }
            else if (input.contains("forward message"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.forwardMessage(splitInput);
                }
            }
            else if (input.contains("reply message"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    invalidCommand=false;
                    String[] splitInput = input.split("\\s");
                    manager.replyMessage(splitInput);

                }
            }
            else if (input.contains("block"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.block(splitInput);
                }
            }
            else if (input.contains("edit message"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    invalidCommand=false;
                    String[] splitInput = input.split("\\s");
                    manager.editMessage(splitInput);

                }
            }
            else if (input.contains("show chats"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.showChats(splitInput);
                }
            }
            else if (input.contains("create group")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.createGroup(splitInput);
                }
            }
            else if (input.contains("add user")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.addUser(splitInput);
                }
            }
            else if (input.contains("change groupName")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    invalidCommand=false;
                    String[] splitInput = input.split("\\s");
                    manager.changeGroupName(splitInput);
                }
            }
            else if (input.contains("change groupId")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    invalidCommand=false;
                    String[] splitInput = input.split("\\s");
                    manager.changeGroupId(splitInput);
                }
            }
            else if (input.contains("remove user")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.removeUser(splitInput);
                }
            }
            else if (input.contains("ban user")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.banUser(splitInput);
                }
            }
            else if(input.contains("send message to group"))
            {
                if(manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    invalidCommand=false;
                    String[] splitInput = input.split("\\s");
                    manager.sendGroupMessage(splitInput);
                }
            }
            else if(input.contains("edit groupMessage"))
            {
                if(manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    invalidCommand=false;
                    String[] splitInput = input.split("\\s");
                    manager.editGroupMessage(splitInput);
                }
            }
            else if(input.contains("reply groupMessage"))
            {
                if(manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    invalidCommand=false;
                    String[] splitInput = input.split("\\s");
                    manager.replyGroupMessage(splitInput);
                }

            }
            else if(input.contains("forward groupMessage to group"))
            {
                if(manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    String[] splitInput = input.split("\\s");
                    manager.forwardGroupMessage(splitInput);
                }
            }
            else if(input.contains("show group chats"))
            {
                if(manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    String[] splitInput = input.split("\\s");
                    manager.showGroupMessages(splitInput);
                }
            }
            else if (input.contains("forward groupMessage to pv"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    String[] splitInput = input.split("\\s");
                    manager.forwardGroupToPv(splitInput);
                }
            }
            else if (input.contains("forward ordinaryMessage to group"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    String[] splitInput = input.split("\\s");
                    manager.forwardPvToGroup(splitInput);
                }
            }
            else if (input.contains("search text message"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    invalidCommand=false;
                    manager.searchTextMessage();
                }
            }
            else if(input.contains("show")&&input.contains("posts"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else
                {
                    String[] splitInput = input.split("\\s");
                    manager.showPosts(splitInput);
                }

            }
            else if(input.contains("suggest friend")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else{
                    manager.suggestFriend();
                }
            }
            else if(input.contains("suggest advertisement"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else{
                    manager.suggestBusinessPost();
                }
            }
            else if(input.contains("log out"))
            {
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    manager.logout();
                }
            }
            else if (input.contains("delete groupMessage")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.deleteGroupMessage(splitInput);
                }
            }
            else if (input.contains("delete message")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.deleteMessage(splitInput);
                }
            }
            else if (input.contains("leaveGroup")){
                if (manager.checkLogin()==null)
                {
                    System.out.println("no one logged in...");
                }
                else {
                    String[] splitInput = input.split("\\s");
                    manager.leaveGroup(splitInput);
                }
            }
            else if(input.equals("end"))
            {
                DataInitializer dataInitializer = new DataInitializer();
                dataInitializer.insertUsers(Main.connection);
                dataInitializer.insertPosts(Main.connection);
                dataInitializer.insertMessages(Main.connection);
                dataInitializer.insertGroupMessage(Main.connection);
                dataInitializer.insertGroup(Main.connection);
                dataInitializer.insertBlocks(Main.connection);
                dataInitializer.insertBusinessPost(Main.connection);
                break;
            }
            else if (input.equals("pain")){
                for(int i=0;i<manager.posts.size();i++){
                    System.out.println(manager.posts.get(i).getCommentsId().size());
                }
            }
            else
            {
                if(invalidCommand)
                {
                    System.out.println("Invalid command...");
                }
            }
        }
    }
}