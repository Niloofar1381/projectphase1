import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<BusinessUser> businessUsers = new ArrayList<>();
    public static ArrayList<Post> posts = new ArrayList<>();
    public static ArrayList<BusinessPost> businessPosts = new ArrayList<>();
    public static ArrayList<Message> messages = new ArrayList<>();
    public static ArrayList<Block> blocks = new ArrayList<>();
    public static ArrayList<Group> groups = new ArrayList<>();
    public static ArrayList<GroupMessage> groupMessages = new ArrayList<>();
    public Scanner sc = new Scanner(System.in);

    public boolean searchIds(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public User matchIdAndPassword(String id, String password) {
        for (User user : users) {
            if (user.getId().equals(id) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User checkNationalCode(String Id, String nationalCode) {
        for (User user : users) {
            if (user.getId().equals(Id) && user.getNationalCode().equals(nationalCode)) {
                return user;
            }
        }
        return null;
    }

    public User findId(String id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                return users.get(i);
            }
        }
        return null;
    }

    public Post searchPostById(String Id) {
        for (Post post : posts) {
            if (post.getId().equals(Id)) {
                return post;
            }
        }
        return null;
    }

    public BusinessPost searchBusinessPostById(String Id) {
        for (BusinessPost businessPost : businessPosts) {
            if (businessPost.getId().equals(Id)) {
                return businessPost;
            }
        }
        return null;
    }

    public User checkLogin() {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isEntered()) {
                return users.get(i);
            }
        }

        return null;
    }

    public Message searchMessage(int messageId) {
        for (Message message : messages) {
            if (message.getId() == messageId) {
                return message;
            }
        }
        return null;
    }

    public User postsUser(String postId) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).Id.equals(postId)) {
                User user = findId(posts.get(i).userId);
                return user;
            }
        }
        return null;
    }

    public int getDistance(LocalDate first, LocalDate second) {
        if (first.getYear() == second.getYear()) {
            return second.getDayOfYear() - first.getDayOfYear();
        } else {
            int x = first.getDayOfYear();
            int y = second.getDayOfYear();
            return 365 - x + y;
        }
    }

    public Group searchGroup(String groupId) {
        for (Group group : groups) {
            if (group.getGroupId().equals(groupId)) {
                return group;
            }
        }
        return null;
    }

    public GroupMessage findGroupMessage(String id) {
        for (int i = 0; i < groupMessages.size(); i++) {
            if (groupMessages.get(i).id.equals(id)) {
                return groupMessages.get(i);
            }
        }
        return null;
    }

    //main functions
    public void register(String[] splitInput) {
        if (searchIds(splitInput[1])) {
            System.out.println("this id has been selected choose another id...");
        } else {
            if (splitInput[2].length() < 8) {
                System.out.println("choose another password...");
            } else {
                if (!splitInput[3].equals(splitInput[2])) {
                    System.out.println("password is not valid");
                } else if (splitInput[5].equals("ordinary")) {
                    if (!searchIds(splitInput[1])) {
                        User user = new User(splitInput[1], splitInput[2], splitInput[4], splitInput[5]);
                        users.add(user);
                        System.out.println("registered successfully");
                    } else {
                        System.out.println("this id had been chosen before...");
                    }
                } else if (splitInput[5].equals("business")) {
                    if (!searchIds(splitInput[1])) {
                        BusinessUser businessUser = new BusinessUser(splitInput[1], splitInput[2], splitInput[4], splitInput[5]);
                        businessUsers.add(businessUser);
                        users.add(businessUser);
                        System.out.println("registered successfully");
                    } else {
                        System.out.println("this id had been chosen before...");
                    }
                }
            }
        }
    }

    public void login(String[] splitInput) {
        if (searchIds(splitInput[1])) {
            if (splitInput[2].equals("forgot")) {
                if (checkNationalCode(splitInput[1], splitInput[3]) != null) {
                    System.out.println("logged in successfully");
                    checkNationalCode(splitInput[1], splitInput[3]).setEntered(true);
                } else {
                    System.out.println("invalid national code...");
                }
            } else {
                if (matchIdAndPassword(splitInput[1], splitInput[2]) != null) {
                    matchIdAndPassword(splitInput[1], splitInput[2]).setEntered(true);
                    System.out.println("logged in successfully");
                } else {
                    System.out.println("Id and password not matched");
                }
            }
        } else {
            System.out.println("invalid Id");
        }
    }

    public void showProfile(String[] splitInput) {
        if (findId(splitInput[1]) == null) {
            System.out.println("Invalid id...");
        } else {
            User user = findId(splitInput[1]);
            System.out.println("Id: " + user.getId());
            System.out.println("followers numbers: " + user.getFollowerIds().size());
            System.out.println("following numbers: " + user.getFollowingIds().size());
            System.out.println("post numbers: " + user.getPostIds().size());
            if (!user.getFollowerIds().contains(checkLogin().getId())) {
                System.out.println("Do you want to follow this account?");
                String answer = sc.nextLine();
                if (answer.equals("Yes")) {
                    user.getFollowerIds().add(checkLogin().getId());
                    checkLogin().getFollowingIds().add(user.getId());
                    System.out.println("you are following " + user.getId());
                }
            }
        }
    }

    public void createPost() {
        boolean exist = true;
        if (checkLogin() == null) {
            System.out.println("no one logged in...");
            exist = false;
        }
        if (exist) {
            User user1 = checkLogin();
            if (user1.getBusinessAccount().equals("business")) {
                BusinessUser businessUser = (BusinessUser) checkLogin();
                Integer num = Integer.parseInt(String.valueOf(businessUser.getPostIds().size() + 1));
                String Id = businessUser.getId();
                Id += num.toString();
                BusinessPost businessPost = new BusinessPost(businessUser.getId(), Id);
                businessPost.setId(Id);
                String answer = sc.nextLine();
                if (answer.contains("add text")) {
                    String text = sc.nextLine();
                    businessPost.setText("ad   " + text);
                }
                LocalDate time = LocalDate.now();
                businessPost.releasedTime = time;
                businessUser.getPostIds().add(businessPost.getId());
                businessPosts.add(businessPost);
                posts.add(businessPost);
                System.out.println("post created successfully");
            } else {
                User user = checkLogin();
                Integer num = Integer.parseInt(String.valueOf(user.getPostIds().size() + 1));
                String Id = user.getId();
                Id += num.toString();
                Post post = new Post(user.getId(), Id);
                post.setId(Id);
                String answer = sc.nextLine();
                if (answer.contains("add text")) {
                    String text = sc.nextLine();
                    post.setText(text);
                }
                user.getPostIds().add(post.getId());
                posts.add(post);
                System.out.println("post created successfully");
            }
        }
    }

    public void addComment(String[] splitInput) {
        boolean ordinaryPost = false, businessPost = false;
        if (searchPostById(splitInput[2]) == null && searchBusinessPostById(splitInput[2]) == null) {
            System.out.println("This post doesn't exist...");
        } else if (searchPostById(splitInput[2]) != null && searchBusinessPostById(splitInput[2]) == null) {
            ordinaryPost = true;
        } else if (searchPostById(splitInput[2]) != null && searchBusinessPostById(splitInput[2]) != null) {
            businessPost = true;
        }
        if (ordinaryPost) {
            String comment = sc.nextLine();
            String id = searchPostById(splitInput[2]).getId() +
                    (searchPostById(splitInput[2]).commentsId.size() + 1);
            Post post = new Post(checkLogin().getId(), id);
            post.setText(comment);
            searchPostById(splitInput[2]).commentsId.add(post.getId());
            posts.add(post);
            System.out.println("comment added successfully");
        } else if (businessPost) {
            String comment = sc.nextLine();
            String id = searchBusinessPostById(splitInput[2]).getId() +
                    (searchPostById(splitInput[2]).commentsId.size() + 1);
            BusinessPost businessPost1 = new BusinessPost(checkLogin().getId(), id);
            businessPost1.setText(comment);
            searchBusinessPostById(splitInput[2]).commentsId.add(businessPost1.getId());
            businessPosts.add(businessPost1);
            posts.add(businessPost1);
            System.out.println("comment added successfully");
        }
    }

    public void like(String[] splitInput) {
        boolean ordinaryPost = false, businessPost = false;
        if (searchPostById(splitInput[1]) == null && searchBusinessPostById(splitInput[1]) == null) {
            System.out.println("this post doesn't exist...");
        } else if (searchPostById(splitInput[1]) != null && searchBusinessPostById(splitInput[1]) == null) {
            ordinaryPost = true;
        } else if (searchPostById(splitInput[1]) != null && searchBusinessPostById(splitInput[1]) != null) {
            businessPost = true;
        }
        if (ordinaryPost) {
            searchPostById(splitInput[1]).likeUsersId.add(checkLogin().getId());
            System.out.println("liked successfully");
        } else if (businessPost) {
            searchBusinessPostById(splitInput[1]).likeUsersId.add(checkLogin().getId());
            BusinessPost businessPost1 = searchBusinessPostById(splitInput[1]);
            LocalDate time = LocalDate.now();
            User user = checkLogin();
            businessPost1.likes.put(user, time);
            businessPost1.likesUsersForTable.add(user.getId());
            businessPost1.likesLocalDateForTable.add(time);
            System.out.println("liked successfully");
        }
    }

    public void showComments(String[] splitInput) {
        if (postsUser(splitInput[2]).getId().equals(checkLogin().getId())) {
            if (searchPostById(splitInput[2]) == null) {
                System.out.println("this post does not exist");
            } else {
                Post post = searchPostById(splitInput[2]);
                System.out.println("The number of comments: " + post.commentsId.size());
                for (int i = 0; i < post.commentsId.size(); i++) {
                    System.out.println(searchPostById(post.commentsId.get(i)).text);
                }
            }
        } else {
            System.out.println("This post doesn't exist for you...");
        }
    }

    public void showLikes(String[] splitInput) {
        if (postsUser(splitInput[2]).getId().equals(checkLogin().getId())) {
            if (searchPostById(splitInput[2]) == null) {
                System.out.println("this post does not exist");
            } else {
                Post post = searchPostById(splitInput[2]);
                System.out.println("The number of Likes: " + post.likeUsersId.size());
                for (int i = 0; i < post.likeUsersId.size(); i++) {
                    System.out.println(post.likeUsersId.get(i));
                }
            }
        } else {
            System.out.println("This post doesn't exist for you...");
        }
    }

    public void showMainPage() {
        if (checkLogin() == null) {
            System.out.println("no one logged in...");
        } else {
            User user = checkLogin();
            System.out.println("user's posts: ");
            user.printUserPosts();
            System.out.println("----------");
            System.out.println("follower's posts: ");
            user.printFollowersPosts();
            System.out.println("----------");
            System.out.println("following's posts: ");
            user.printFollowingsPosts();
            for (int i = 0; i < user.getFollowerIds().size(); i++) {
                User user1 = findId(user.getFollowerIds().get(i));
                if (user1 instanceof BusinessUser) {
                    int n = user1.getPostIds().size();
                    BusinessUser businessUser = (BusinessUser) user1;
                    if (n > 5) {
                        for (int i1 = n - 1; i1 > n - 6; i1--) {
                            Post post = searchPostById(businessUser.getPostIds().get(i1));
                            BusinessPost businessPost = (BusinessPost) post;
                            businessPost.getViewers().add(checkLogin().getId());
                            businessPost.views.put(checkLogin(), LocalDate.now());
                            businessPost.viewUsersForTable.add(checkLogin().getId());
                            businessPost.viewLocalDatesForTable.add(LocalDate.now());
                        }
                    } else {
                        for (int i1 = 0; i1 < n; i1++) {
                            Post post = searchPostById(businessUser.getPostIds().get(i1));
                            BusinessPost businessPost = (BusinessPost) post;
                            businessPost.getViewers().add(checkLogin().getId());
                            businessPost.views.put(checkLogin(), LocalDate.now());
                        }
                    }
                }
            }
            for (int i = 0; i < user.getFollowingIds().size(); i++) {
                User user1 = findId(user.getFollowingIds().get(i));
                if (user1 instanceof BusinessUser) {
                    int n = user1.getPostIds().size();
                    BusinessUser businessUser = (BusinessUser) user1;
                    if (n > 5) {
                        for (int i1 = n - 1; i1 > n - 6; i1--) {
                            Post post = searchPostById(businessUser.getPostIds().get(i1));
                            BusinessPost businessPost = (BusinessPost) post;
                            businessPost.getViewers().add(checkLogin().getId());
                            businessPost.views.put(checkLogin(), LocalDate.now());
                        }
                    } else {
                        for (int i1 = 0; i1 < n; i1++) {
                            Post post = searchPostById(businessUser.getPostIds().get(i1));
                            BusinessPost businessPost = (BusinessPost) post;
                            businessPost.getViewers().add(checkLogin().getId());
                            businessPost.views.put(checkLogin(), LocalDate.now());
                        }
                    }

                }
            }

        }
    }

    public void showStats(String[] splitInput)
    {
        LocalDate now = LocalDate.now();
        User user = checkLogin();
        if (user instanceof BusinessUser) {
            BusinessPost businessPost = searchBusinessPostById(splitInput[2]);
            int distance = getDistance(businessPost.releasedTime, now);
            for (int i = 0; i <= distance; i++) {
                int num = 0;
                for (LocalDate value : businessPost.likes.values()) {
                    if (getDistance(businessPost.releasedTime, value) == i) {
                        num++;
                    }
                }
                System.out.println("the number of likes in day" + i + ": " + num);
            }
            for (int i = 0; i <= distance; i++) {
                int num = 0;
                for (LocalDate value : businessPost.views.values()) {
                    if (getDistance(businessPost.releasedTime, value) == i) {
                        num++;
                    }
                }
                System.out.println("the number of views in day" + i + ": " + num);
            }
        } else {
            System.out.println("you are not a business user...");
        }
    }

    public void startPrivateMessage(String[] splitInput) {
        boolean blocked = false;
        if (findId(splitInput[3]) == null) {
            System.out.println("Invalid id...");
        } else {
            User user = findId(splitInput[3]);
            for (Block block : blocks) {
                if (block.getBlocked().equals(checkLogin()) && block.getBlocker().equals(user)) {
                    blocked = true;
                    break;
                }
            }
        }
        if (!blocked) {
            int num = 0;
            String text = sc.nextLine();
            User sender = checkLogin();
            User receiver = findId(splitInput[3]);
            for (int i = 0; i < messages.size(); i++) {
                num++;
            }
            Message message = new Message(sender, receiver, num + 1, text, LocalDate.now());
            message.localDateToString = LocalDate.now().toString();
            sender.getMessageIds().add(message.getId());
            messages.add(message);
            System.out.println(message.getText());
        } else {
            System.out.println(findId(splitInput[3]).getId() + " blocked you...");
        }
    }

    public void forwardMessage(String[] splitInput) {
        if (findId(splitInput[3]) == null) {
            System.out.println("user doesn't exist...");
        }
        if (searchMessage(Integer.parseInt(splitInput[2])) == null) {
            System.out.println("this messageId doesn't exist...");
        } else if (findId(splitInput[3]) != null && searchMessage(Integer.parseInt(splitInput[2])) != null) {
            boolean blocked = false;
            for (Block block : blocks) {
                if (block.getBlocked().equals(checkLogin()) && block.getBlocker().equals(findId(splitInput[3]))) {
                    blocked = true;
                    break;
                }
            }
            if (!blocked) {
                if (searchMessage(Integer.parseInt(splitInput[2])).getSender().equals(checkLogin()) ||
                        searchMessage(Integer.parseInt(splitInput[2])).getReceiver().equals(checkLogin())) {
                    int id = 0;
                    Message message = searchMessage(Integer.parseInt(splitInput[2]));
                    User user = findId(splitInput[3]);
                    for (int i = 0; i < messages.size(); i++) {
                        id++;
                    }
                    Message forwardedMessage = new Message(checkLogin(), user, id + 1, "forwarded\n" + message.getText(), LocalDate.now());
                    forwardedMessage.localDateToString = LocalDate.now().toString();
                    forwardedMessage.forwarded = true;
                    messages.add(forwardedMessage);
                    checkLogin().getMessageIds().add(forwardedMessage.getId());
                    System.out.println(forwardedMessage.getText());
                }
            } else {
                System.out.println(findId(splitInput[3]).getId() + " blocked you...");
            }
        }
    }

    public void replyMessage(String[] splitInput) {
        if (searchMessage(Integer.parseInt(splitInput[2])) == null) {
            System.out.println("this messageId doesn't exist...");
        } else {
            boolean blocked = false;
            for (Block block : blocks) {
                User user = searchMessage(Integer.parseInt(splitInput[2])).getSender();
                if (block.getBlocked().equals(checkLogin()) && block.getBlocker().equals(user)) {
                    blocked = true;
                    break;
                }
            }
            if (!blocked) {
                if (searchMessage(Integer.parseInt(splitInput[2])).getSender().equals(checkLogin()) ||
                        searchMessage(Integer.parseInt(splitInput[2])).getReceiver().equals(checkLogin())) {

                    Message message = searchMessage(Integer.parseInt(splitInput[2]));
                    String text = sc.nextLine();
                    String charsOfMessage = message.getText().substring(0, message.getText().length() - 3);
                    int id = 0;
                    User sender = checkLogin();
                    User receiver = message.getReceiver();
                    if (sender.equals(receiver)) {
                        receiver = message.getSender();
                    }
                    for (int i = 0; i < messages.size(); i++) {
                        id++;
                    }
                    Message repliedMessage = new Message(sender, receiver, id + 1, charsOfMessage + "\n" + text, LocalDate.now());
                    repliedMessage.localDateToString = LocalDate.now().toString();
                    messages.add(repliedMessage);
                    sender.getMessageIds().add(repliedMessage.getId());
                    System.out.println(repliedMessage.getText());
                }
            } else {
                User user = searchMessage(Integer.parseInt(splitInput[2])).getSender();
                System.out.println(user.getId() + " blocked you...");
            }
        }
    }

    public void block(String[] splitInput) {
        if (findId(splitInput[1]) == null) {
            System.out.println("user doesn't exist...");
        } else {
            User user = findId(splitInput[1]);
            Block block = new Block(checkLogin(), user);
            block.setBlocked(user);
            block.setBlocker(checkLogin());
            blocks.add(block);
            System.out.println(user.getId() + " is blocked successfully");
        }
    }

    public void editMessage(String[] splitInput) {
        if (searchMessage(Integer.parseInt(splitInput[2])) == null) {
            System.out.println("this messageId doesn't exist...");
        } else {
            Message message = searchMessage(Integer.parseInt(splitInput[2]));
            boolean blocked = false;
            for (Block block : blocks) {
                if (block.getBlocked().equals(checkLogin()) && block.getBlocker().equals(message.getReceiver())) {
                    blocked = true;
                    break;
                }
            }
            if (!blocked) {
                if (message.getSender().equals(checkLogin())) {
                    int index = checkLogin().indexOfMessage(message), all = checkLogin().getMessageIds().size();
                    if (index <= all && index > all - 10 && !message.forwarded) {
                        String text = sc.nextLine();
                        message.setText(text);
                        System.out.println("message edited successfully");
                    } else {
                        System.out.println("sth went wrong...");
                    }
                } else {
                    System.out.println("you cannot edit this message...");
                }
            } else {
                System.out.println("you are blocked...");
            }
        }
    }

    public void showChats(String[] splitInput) {
        ArrayList<Message> selectedMessages = new ArrayList<>();
        ArrayList<User> selectedUsers = new ArrayList<>();
        if (findId(splitInput[2]) == null) {
            System.out.println("this user doesn't exist...");
        } else {
            boolean exist = false;
            User user = findId(splitInput[2]);
            for (Message message : messages) {
                if ((message.getReceiver().equals(checkLogin()) && message.getSender().equals(user))) {
                    exist = true;
                    selectedMessages.add(message);
                    selectedUsers.add(user);
                } else if ((message.getSender().equals(checkLogin()) && message.getReceiver().equals(user))) {
                    exist = true;
                    selectedMessages.add(message);
                    selectedUsers.add(checkLogin());
                }
            }
            if (exist) {
                for (int i = 0; i < selectedMessages.size(); i++) {
                    System.out.println(selectedUsers.get(i).getId() + ": " + selectedMessages.get(i).getText());
                }
            } else {
                System.out.println("there isn't any chat...");
            }
        }
    }

    public void createGroup(String[] splitInput) {
        if (searchGroup(splitInput[2]) == null) {
            Group group = new Group(checkLogin(), splitInput[2], splitInput[3]);
            groups.add(group);
            group.setAdmin(checkLogin());
            System.out.println("new group created successfully");
        } else {
            System.out.println("this id had been selected before...");
        }
    }

    public void addUser(String[] splitInput) {
        if (findId(splitInput[2]) == null) {
            System.out.println("this user doesn't exist...");
        } else if (searchGroup((splitInput[3])) != null) {
            if (searchGroup((splitInput[3])).getAdmin().equals(checkLogin())) {
                User user = findId(splitInput[2]);
                Group group = searchGroup((splitInput[3]));
                if (!group.getUsers().contains(user)) {
                    group.getUsers().add(user);
                    group.getBanned().add(false);
                    System.out.println("added successfully");
                } else {
                    System.out.println("you were added to the group before...");
                }
            } else {
                System.out.println("you aren't admin...");
            }
        } else {
            System.out.println("this group doesn't exist...");
        }
    }

    public void changeGroupName(String[] splitInput) {
        if (searchGroup((splitInput[2])) == null) {
            System.out.println("this group doesn't exist...");
        } else {
            if (searchGroup((splitInput[2])).getAdmin().equals(checkLogin())) {
                Group group = searchGroup((splitInput[2]));
                group.setGroupName(splitInput[2]);
                System.out.println("group name is changed successfully");
            } else {
                System.out.println("you aren't admin...");
            }
        }
    }

    public void changeGroupId(String[] splitInput) {
        if (searchGroup((splitInput[2])) == null) {
            System.out.println("this group doesn't exist...");
        } else {
            if (searchGroup((splitInput[2])).getAdmin().equals(checkLogin())) {
                if (searchGroup(splitInput[3]) == null) {
                    Group group = searchGroup((splitInput[2]));
                    group.setGroupId(splitInput[3]);
                    System.out.println("group id is changed successfully");
                }
                else {
                    System.out.println("this group id had been chosen before...");
                }
            } else {
                System.out.println("you aren't admin...");
            }
        }
    }

    public void removeUser(String[] splitInput) {
        if (searchGroup((splitInput[2])) == null) {
            System.out.println("this group doesn't exist...");
        } else {
            if (searchGroup((splitInput[2])).getAdmin().equals(checkLogin())) {
                if (findId(splitInput[3]) == null) {
                    System.out.println("this user doesn't exist...");
                } else {
                    User user = findId(splitInput[3]);
                    boolean bool = false;
                    Group group = searchGroup((splitInput[2]));
                    for (User user1 : group.getUsers()) {
                        if (user1.equals(user)) {
                            group.getUsers().remove(user1);
                            System.out.println("user removed successfully");
                            bool = true;
                            break;
                        }
                    }
                    if (!bool) {
                        System.out.println("this user wasn't the member of this group...");
                    }
                }
            } else {
                System.out.println("you aren't admin...");
            }
        }

    }

    public void banUser(String[] splitInput) {
        if (searchGroup((splitInput[2])) == null) {
            System.out.println("this group doesn't exist...");
        } else {
            if (searchGroup((splitInput[2])).getAdmin().equals(checkLogin())) {
                if (findId(splitInput[3]) == null) {
                    System.out.println("this user doesn't exist...");
                } else {
                    User user = findId(splitInput[3]);
                    boolean bool = false;
                    Group group = searchGroup((splitInput[2]));
                    for (int i = 0; i < group.getUsers().size(); i++) {
                        if (group.getUsers().get(i).equals(user)) {
                            bool = true;
                            group.getBanned().set(i, true);
                            System.out.println("user banned successfully");
                            break;
                        }
                    }
                    if (!bool) {
                        System.out.println("this user wasn't the member of this group...");
                    }
                }
            } else {
                System.out.println("you aren't admin...");
            }
        }
    }

    public void sendGroupMessage(String[] splitInput) {
        boolean bool = false;
        if (searchGroup((splitInput[4])) == null) {
            System.out.println("this group doesn't exist...");
        } else {
            Group group = searchGroup(splitInput[4]);
            boolean admin = false;
            if (checkLogin().equals(group.getAdmin())) {
                admin = true;
                String text = sc.nextLine();
                int n = group.groupMessages.size() + 1;
                String str = Integer.toString(n);
                String id = group.groupId + str;
                GroupMessage groupMessage = new GroupMessage(checkLogin(), text, id, group.groupId, LocalDate.now());
                groupMessage.localDateToString = LocalDate.now().toString();
                groupMessages.add(groupMessage);
                group.getGroupMessages().add(groupMessage);
                System.out.println("message is sent to the group successfully");
            }
            if (!admin) {
                for (int i = 0; i < group.getUsers().size(); i++) {
                    if (group.getUsers().get(i).equals(checkLogin()) && group.getBanned().get(i).equals(false)) {
                        bool = true;
                        String text = sc.nextLine();
                        int n = group.groupMessages.size() + 1;
                        String str = Integer.toString(n);
                        String id = group.groupId + str;
                        GroupMessage groupMessage = new GroupMessage(checkLogin(), text, id, group.groupId, LocalDate.now());
                        groupMessage.localDateToString = LocalDate.now().toString();
                        groupMessages.add(groupMessage);
                        group.getGroupMessages().add(groupMessage);
                        System.out.println("message is sent to the group successfully");
                        break;
                    }
                }
            }
            if (!bool && !admin) {
                System.out.println("You can't send message to this group.");
            }
        }
    }

    public void editGroupMessage(String[] splitInput) {
        boolean bool = false;
        if (findGroupMessage(splitInput[2]) == null) {
            System.out.println("This message doesn't exist...");
        } else {
            boolean admin = false;
            GroupMessage groupMessage = findGroupMessage(splitInput[2]);
            String groupId = groupMessage.groupId;
            Group group = searchGroup(groupId);
            if (checkLogin().equals(group.getAdmin())) {
                admin = true;
                String newText = sc.nextLine();
                groupMessage.setText(newText);
                System.out.println("message is edited successfully");
            }
            if (!admin) {
                for (int i = 0; i < group.getUsers().size(); i++) {
                    if (group.getUsers().get(i).equals(checkLogin()) && group.getBanned().get(i).equals(false)) {
                        bool = true;
                        String newText = sc.nextLine();
                        groupMessage.setText(newText);
                        System.out.println("message is edited successfully");
                        break;
                    }
                }
            }
            if (!bool && !admin) {
                System.out.println("You can't edit message in this group.");
            }

        }
    }

    public void replyGroupMessage(String[] splitInput) {
        boolean bool = false;
        if (findGroupMessage(splitInput[2]) == null) {
            System.out.println("This message doesn't exist...");
        } else {
            boolean admin = false;
            GroupMessage groupMessage = findGroupMessage(splitInput[2]);
            String groupId = groupMessage.groupId;
            Group group = searchGroup(groupId);
            if (checkLogin().equals(group.getAdmin())) {
                admin = true;
                String text = sc.nextLine();
                String charsOfMessage = groupMessage.getText().substring(0, 4);
                int n = group.groupMessages.size() + 1;
                String str = Integer.toString(n);
                String id = group.groupId + str;
                GroupMessage groupMessage1 = new GroupMessage(checkLogin(), charsOfMessage + "\n" + text, id, groupId, LocalDate.now());
                groupMessage1.localDateToString = LocalDate.now().toString();
                groupMessages.add(groupMessage1);
                group.groupMessages.add(groupMessage1);
                System.out.println("message is replied successfully");
            }
            if (!admin) {
                for (int i = 0; i < group.getUsers().size(); i++) {
                    if (group.getUsers().get(i).equals(checkLogin()) && group.getBanned().get(i).equals(false)) {
                        bool = true;
                        String text = sc.nextLine();
                        String charsOfMessage = groupMessage.getText().substring(0, 4);
                        int n = group.groupMessages.size() + 1;
                        String str = Integer.toString(n);
                        String id = group.groupId + str;
                        GroupMessage groupMessage1 = new GroupMessage(checkLogin(), charsOfMessage + "\n" + text, id, group.groupId, LocalDate.now());
                        groupMessage1.localDateToString = LocalDate.now().toString();
                        groupMessages.add(groupMessage1);
                        group.groupMessages.add(groupMessage1);
                        System.out.println("message is replied successfully");
                        break;
                    }
                }
            }
            if (!bool && !admin) {
                System.out.println("You can't send message in this group.");
            }
        }
    }

    public void forwardGroupMessage(String[] splitInput) {
        boolean bool = false;

        if (searchGroup((splitInput[5])) == null) {
            System.out.println("this group doesn't exist...");
        } else {
            if (findGroupMessage(splitInput[4]) == null) {
                System.out.println("This message doesn't exist...");
            } else {
                boolean admin = false;
                Group group = searchGroup(splitInput[5]);
                if (group.getAdmin().equals(checkLogin())) {
                    admin = true;
                    GroupMessage groupMessage = findGroupMessage(splitInput[4]);
                    String id = group.getGroupId() + group.getGroupMessages().size() + 1;
                    String text = "Forwarded from " + groupMessage.getSender().getId() + "\n" + groupMessage.getText();
                    GroupMessage groupMessage1 = new GroupMessage(checkLogin(), text, id, group.groupId, LocalDate.now());
                    groupMessage1.localDateToString = LocalDate.now().toString();
                    groupMessages.add(groupMessage1);
                    group.getGroupMessages().add(groupMessage1);
                    System.out.println("groupMessage forwarded to the group successfully");
                }
                if (!admin) {
                    for (int i = 0; i < group.getUsers().size(); i++) {
                        if (group.getUsers().get(i).equals(checkLogin()) && group.getBanned().get(i).equals(false)) {
                            bool = true;
                            GroupMessage groupMessage = findGroupMessage(splitInput[4]);
                            String id = group.getGroupId() + group.getGroupMessages().size() + 1;
                            String text = "Forwarded from " + groupMessage.getSender().getId() + "\n" + groupMessage.getText();
                            GroupMessage groupMessage1 = new GroupMessage(checkLogin(), text, id, group.groupId, LocalDate.now());
                            groupMessage1.localDateToString = LocalDate.now().toString();
                            groupMessages.add(groupMessage1);
                            group.getGroupMessages().add(groupMessage1);
                            System.out.println("groupMessage forwarded to the group successfully");
                        }
                    }
                }
                if (!bool && !admin) {
                    System.out.println("You can't send message in this group.");
                }
            }
        }

    }

    public void showGroupMessages(String[] splitInput) {
        boolean bool = false;
        if (searchGroup((splitInput[3])) == null) {
            System.out.println("this group doesn't exist...");
        } else {
            Group group = searchGroup(splitInput[3]);
            if (group.getUsers().contains(checkLogin()) || group.getAdmin().equals(checkLogin())) {
                bool = true;
            }
            if (bool) {
                for (GroupMessage groupMessage : group.getGroupMessages()) {
                    System.out.print(groupMessage.getSender().getId() + " : ");
                    System.out.println(groupMessage.getText());
                }
            } else {
                System.out.println("you aren't member of this group...");
            }
        }
    }

    public void forwardGroupToPv(String[] splitInput) {
        if (searchGroup((splitInput[4])) == null) {
            System.out.println("this group doesn't exist...");
        } else if (findId(splitInput[5]) == null) {
            System.out.println("this user doesn't exist...");
        } else if (findGroupMessage(splitInput[6]) == null) {
            System.out.println("this groupMessage doesn't exist...");
        } else {
            boolean blocked = false;
            User receiver = findId(splitInput[5]);
            for (Block block : blocks) {
                if (block.getBlocker().equals(receiver) && block.getBlocked().equals(checkLogin())) {
                    blocked = true;
                    break;
                }
            }
            if (!blocked) {
                GroupMessage groupMessage = findGroupMessage(splitInput[6]);
                int num = messages.size() + 1;
                Message message = new Message(checkLogin(), receiver, num, "Forwarded from " + groupMessage.getSender().getId() + "\n" + groupMessage.getText(), LocalDate.now());
                message.localDateToString = LocalDate.now().toString();
                messages.add(message);
                System.out.println("groupMessage forwarded successfully");
            } else {
                System.out.println("you are blocked...");
            }
        }
    }

    public void forwardPvToGroup(String[] splitInput) {
        if (searchGroup((splitInput[4])) == null) {
            System.out.println("this group doesn't exist...");
        } else if (searchMessage(Integer.parseInt(splitInput[5])) == null) {
            System.out.println("this messageId doesn't exist...");
        } else {
            boolean banned = false;
            Group group = searchGroup((splitInput[4]));
            for (int i = 0; i < group.getUsers().size(); i++) {
                if (group.getBanned().get(i).equals(true) && group.getUsers().get(i).equals(checkLogin())) {
                    banned = true;
                    break;
                }
            }
            if (!banned) {
                Message message = searchMessage(Integer.parseInt(splitInput[5]));
                int num = group.groupMessages.size() + 1;
                String str = Integer.toString(num);
                String id = group.getGroupId() + str;
                GroupMessage groupMessage = new GroupMessage(checkLogin(), "Forwarded from " + message.getSender().getId() + "\n" + message.getText(), id, group.groupId, LocalDate.now());
                groupMessage.localDateToString = LocalDate.now().toString();
                group.groupMessages.add(groupMessage);
                System.out.println("message forwarded successfully to the group");
            } else {
                System.out.println("you are banned...");
            }
        }
    }

    public void searchTextMessage() {
        String text = sc.nextLine();
        ArrayList<Message> foundMessages = new ArrayList<>();
        ArrayList<GroupMessage> foundGroupMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getText().contains(text) && (message.getSender().equals(checkLogin()) || message.getReceiver().equals(checkLogin()))) {
                foundMessages.add(message);
            }
        }
        for (GroupMessage groupMessage : groupMessages) {
            if (groupMessage.getText().contains(text)) {
                foundGroupMessages.add(groupMessage);
            }
        }
        for (Message foundMessage : foundMessages) {
            System.out.println("Id: " + foundMessage.getId());
            System.out.println("date: " + foundMessage.getLocalDate());
            String str = foundMessage.getText().substring(0, 10);
            System.out.println("some characters of message: " + str);
        }
        for (GroupMessage foundGroupMessage : foundGroupMessages) {
            System.out.println("Id: " + foundGroupMessage.getId());
            System.out.println("date: " + foundGroupMessage.getLocalDate());
            String str = foundGroupMessage.getText().substring(0, 10);
            System.out.println("some characters of message: " + str);
        }
        String messageId = sc.nextLine();
        try {
            int id = Integer.parseInt(messageId);
            Message message = searchMessage(id);
            System.out.println(message.getText());
        } catch (Exception e) {
            GroupMessage groupMessage = findGroupMessage(messageId);
            System.out.println(groupMessage.getText());
        }
    }

    public void showPosts(String[] splitInput) {
        User user = findId(splitInput[1]);
        System.out.println(user.getId() + " posts:");
        for (int i = 0; i < user.getPostIds().size(); i++) {
            System.out.println(searchPostById(user.getPostIds().get(i)).getText());
            if (user instanceof BusinessUser) {
                BusinessPost businessPost = (BusinessPost) searchBusinessPostById(user.getPostIds().get(i));
                businessPost.getViewers().add(checkLogin().getId());
                LocalDate localDate = LocalDate.now();
                businessPost.views.put(checkLogin(), localDate);
            }
        }
    }
    public void setFriends()
    {
        for (User user : users) {
            user.allFriendIds.addAll(user.getFollowerIds());
            user.allFriendIds.addAll(user.getFollowingIds());
        }
    }
    public void suggestFriend(){
        User user=checkLogin();
        setFriends();
        ArrayList<User> userNewFriends=new ArrayList<>();
        for (int i = 0; i < user.getAllFriendIds().size(); i++) {
            ArrayList<User> newFriends=new ArrayList<>();
            for (int i1 = 0; i1 < findId(user.getAllFriendIds().get(i)).getAllFriendIds().size(); i1++) {
                if (!user.getAllFriendIds().contains(findId(user.getAllFriendIds().get(i)).getAllFriendIds().get(i1)) &&
                !findId(user.getAllFriendIds().get(i)).getAllFriendIds().get(i1).equals(user.getId())){
                    newFriends.add(findId(findId(user.getAllFriendIds().get(i)).getAllFriendIds().get(i1)));
                }
            }
            userNewFriends.addAll(newFriends);
        }
        ArrayList<User>notFriendsWithOurFriends=new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if(!user.getAllFriendIds().contains(users.get(i).getId())&&!users.get(i).equals(user)
                    &&!userNewFriends.contains(users.get(i)))
            {
                notFriendsWithOurFriends.add(users.get(i));
            }
        }
        ArrayList<Integer>gradeForNotFriends=new ArrayList<>();
        for (int i = 0; i < notFriendsWithOurFriends.size(); i++) {
            int num=0;
            for (int i1 = 0; i1 < notFriendsWithOurFriends.get(i).getPostIds().size(); i1++) {
                if( searchPostById(notFriendsWithOurFriends.get(i).getPostIds().get(i1)).likeUsersId.contains(user.getId()))
                {
                    num++;
                }
            }
            gradeForNotFriends.add(num);
        }
        int max1=gradeForNotFriends.get(0);
        int k1=0;
        for (int i = 0; i < gradeForNotFriends.size(); i++) {
            if(gradeForNotFriends.get(i)>max1)
            {
                max1=gradeForNotFriends.get(i);
                k1=i;
            }
        }
        System.out.println("Recommended friend for you\n"+notFriendsWithOurFriends.get(k1).getId());
        ArrayList<Integer>commonFriendsAndLikePosts=new ArrayList<>();
        for (int i = 0; i < userNewFriends.size(); i++) {
            int num=0;
            for (int i1 = 0; i1 <userNewFriends.get(i).getAllFriendIds().size(); i1++) {
                if(user.getAllFriendIds().contains(userNewFriends.get(i).getAllFriendIds().get(i1)))
                {
                    num++;
                }
            }
            for (int i1 = 0; i1 < userNewFriends.get(i).getPostIds().size(); i1++) {
                if( searchPostById(userNewFriends.get(i).getPostIds().get(i1)).likeUsersId.contains(user.getId()))
                {
                    num++;
                }
            }
            commonFriendsAndLikePosts.add(num);
        }
        int max2=commonFriendsAndLikePosts.get(0);
        int k2=0;
        for (int i = 0; i < commonFriendsAndLikePosts.size(); i++) {
            if(commonFriendsAndLikePosts.get(i)>max2)
            {
                max2=commonFriendsAndLikePosts.get(i);
                k2=i;
            }
        }
        ArrayList<User>suggestUsers=new ArrayList<>();
        suggestUsers.add(notFriendsWithOurFriends.get(k1));
        suggestUsers.add(userNewFriends.get(k2));
        System.out.println("Recommended friend for you\n"+userNewFriends.get(k2).getId());
    }

    public void setFavoriteNumber() {
        for (int i = 0; i < businessPosts.size(); i++) {
            for (int i1 = 0; i1 < users.size(); i1++) {
                if (businessPosts.get(i).getLikeUsersId().contains(users.get(i1).getId()) &&
                        businessPosts.get(i).getViewers().contains(users.get(i1).getId())) {
                    businessPosts.get(i).favoriteNumbers.put(users.get(i1), 1.0);
                    businessPosts.get(i).favoriteNumberUser.add(users.get(i1).getId());
                    businessPosts.get(i).favoriteNumberDouble.add(1.0);
                }
                if (businessPosts.get(i).getViewers().contains(users.get(i1).getId()) &&
                        !businessPosts.get(i).getLikeUsersId().contains(users.get(i).getId())) {
                    businessPosts.get(i).favoriteNumbers.put(users.get(i1), 0.0);
                    businessPosts.get(i).favoriteNumberUser.add(users.get(i1).getId());
                    businessPosts.get(i).favoriteNumberDouble.add(0.0);
                } else {
                    businessPosts.get(i).favoriteNumbers.put(users.get(i1), 0.5);
                    businessPosts.get(i).favoriteNumberUser.add(users.get(i1).getId());
                    businessPosts.get(i).favoriteNumberDouble.add(0.5);
                }
            }
        }
    }

    public void suggestBusinessPost() {
        setFavoriteNumber();
        ArrayList<BusinessPost> notViewedPosts = new ArrayList<>();
        ArrayList<BusinessPost> likedPosts = new ArrayList<>();
        ArrayList<Double> favoritism = new ArrayList<>();
        for (BusinessPost post : businessPosts) {
            if (!post.getViewers().contains(checkLogin().getId())) {
                notViewedPosts.add(post);
            }
        }
        for (BusinessPost businessPost : businessPosts) {
            if (businessPost.getLikeUsersId().contains(checkLogin().getId())) {
                likedPosts.add(businessPost);
            }
        }

        for (int j = 0; j < notViewedPosts.size(); j++) {
            double num = 0.0;
            for (int k = 0; k < likedPosts.size(); k++) {
                for (int i = 0; i < users.size(); i++) {
                    num += Math.abs(notViewedPosts.get(j).favoriteNumbers.get(users.get(i))
                            - likedPosts.get(k).favoriteNumbers.get(users.get(i)));
                }
            }
            favoritism.add(num);
        }
        if (favoritism.size() != 0) {
            double min = favoritism.get(0);
            int k = 0;
            for (int i = 0; i < favoritism.size(); i++) {
                if (favoritism.get(i) < min) {
                    min = favoritism.get(i);
                    k = i;
                }
            }
            System.out.println("Recommended advertisement:\n" + notViewedPosts.get(k).getText() + "\n" +
                    notViewedPosts.get(k).userId);

        }
    }
    public void logout()
    {
        checkLogin().setEntered(false);
        System.out.println("logged out successfully");
    }
}