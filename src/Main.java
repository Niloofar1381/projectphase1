import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static Connection connection;
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oop_project",
                "root", "12345");
        DatabaseInitializer databaseInitializer=new DatabaseInitializer();
        InputProcessor inputProcessor=new InputProcessor();
        databaseInitializer.createTables(connection);
        SetArrayLists setArrayLists = new SetArrayLists();
        setArrayLists.setAllArrayLists();
        inputProcessor.start();
    }
}
