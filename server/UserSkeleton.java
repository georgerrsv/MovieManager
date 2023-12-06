import java.sql.SQLException;

public class UserSkeleton {
    private Database db;


    public UserSkeleton(Database db) {

        this.db = db;
    }

    public String addUser(String arguments) {
        User user = User.fromJson(arguments);
        try {
            return db.addUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String removeUser(String arguments) {
        int id = Integer.parseInt(arguments);
        try {
            return db.removeUser(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String showUserDetails(String arguments) {
        int id = Integer.parseInt(arguments);
        try {
            return db.showUserDetails(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String login(String arguments) {
        String email = String.valueOf(arguments);
        String password = String.valueOf(arguments);
        try {
            return db.login(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}