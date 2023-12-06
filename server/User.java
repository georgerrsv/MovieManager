import org.json.JSONObject;

public class User {

    private String fname;
    private String lname;
    private String email;
    private String password;

    public User(String fname, String lname, String email, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
    }

    public String toJson() {
        JSONObject userData = new JSONObject();
        userData.put("fname", fname);
        userData.put("lname", lname);
        userData.put("email", email);
        userData.put("password", password);
        return userData.toString();
    }

    public static User fromJson(String jsonStr) {
        JSONObject userData = new JSONObject(jsonStr);
        return new User(
                userData.getString("fname"),
                userData.getString("lname"),
                userData.getString("email"),
                userData.getString("password")
        );
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
