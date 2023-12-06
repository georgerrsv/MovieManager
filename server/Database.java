import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Database {
    private Connection connection;
    private PreparedStatement query;

    public Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/moviemanager", "admin", "admin");
    }

    public String addMovie(Movie movie) throws SQLException {
        query = connection.prepareStatement("SELECT movieId FROM movie WHERE title = ?");
        query.setString(1, movie.getTitle());

        ResultSet resultSet = query.executeQuery();

        if (resultSet.next()) {
            return "Error";
        } else {
            query = connection.prepareStatement("INSERT INTO movie (title, director, year, duration, genre, classification, rating, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            query.setString(1, movie.getTitle());
            query.setString(2, movie.getDirector());
            query.setInt(3, movie.getYear());
            query.setInt(4, movie.getDuration());
            query.setString(5, movie.getGenre());
            query.setInt(6, movie.getClassification());
            query.setInt(7, movie.getRating());
            query.setString(8, movie.getDescription());

            int rowsAffected = query.executeUpdate();

            if (rowsAffected == 1) {
                return "Success";
            } else {
                return "Error";
            }
        }
    }

    public String removeMovie(int id) throws SQLException {
        query = connection.prepareStatement("DELETE FROM movie WHERE movieId = ?");
        query.setInt(1, id);

        int rowsAffected = query.executeUpdate();

        if (rowsAffected == 1) {
            return "Success";
        } else {
            return "Error";
        }
    }

    public String showDetails(int id) throws SQLException {
        query = connection.prepareStatement("SELECT title, director, year, duration, genre, classification, rating, description FROM movie WHERE movieId = ?");
        query.setInt(1, id);

        ResultSet resultSet = query.executeQuery();

        if (resultSet.next()) {
            String title = resultSet.getString("title");
            String director = resultSet.getString("director");
            int year = resultSet.getInt("year");
            int duration = resultSet.getInt("duration");
            String genre = resultSet.getString("genre");
            int classification = resultSet.getInt("classification");
            int rating = resultSet.getInt("rating");
            String description = resultSet.getString("description");

            Movie movie = new Movie(title, director, year, duration, genre, classification, rating, description);
            return movie.toJson();
        } else {
            return "Error";
        }
    }

    public String showCatalog() throws SQLException {
        query = connection.prepareStatement("SELECT movieId, title FROM movie");
        ResultSet resultSet = query.executeQuery();

        List<JSONObject> catalog = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("movieId");
            String title = resultSet.getString("title");

            JSONObject movie = new JSONObject();
            movie.put("movieId", id);
            movie.put("title", title);

            catalog.add(movie);
        }

        if (catalog.isEmpty()) {
            return "Error";
        }

        String catalogoJSON = new JSONArray(catalog).toString();

        return catalogoJSON;
    }

    public String addUser(User user) throws SQLException {
        query = connection.prepareStatement("SELECT userId FROM userInfo WHERE fname = ? AND lname = ? AND email = ?");
        query.setString(1, user.getFname());
        query.setString(2, user.getLname());
        query.setString(3, user.getEmail());

        ResultSet resultSet = query.executeQuery();

        if (resultSet.next()) {
            return "Error";
        } else {
            query = connection.prepareStatement("INSERT INTO userInfo (fname, lname, email, password) VALUES (?, ?, ?, ?)");
            query.setString(1, user.getFname());
            query.setString(2, user.getLname());
            query.setString(3, user.getEmail());
            query.setString(4, user.getPassword());

            int rowsAffected = query.executeUpdate();

            if (rowsAffected == 1) {
                return "Success";
            } else {
                return "Error";
            }
        }
    }

    public String removeUser(int id) throws SQLException {
        query = connection.prepareStatement("DELETE FROM userInfo WHERE userId = ?");
        query.setInt(1, id);

        int rowsAffected = query.executeUpdate();

        if (rowsAffected == 1) {
            return "Success";
        } else {
            return "Error";
        }
    }

    public String showUserDetails(int id) throws SQLException {
        query = connection.prepareStatement("SELECT * FROM userInfo WHERE userId = ?");
        query.setInt(1, id);

        ResultSet resultSet = query.executeQuery();

        if (resultSet.next()) {
            String fname = resultSet.getString("fname");
            String lname = resultSet.getString("lname");
            String email = resultSet.getString("email");

            User user = new User(fname, lname, email, "********");
            return user.toJson();
        } else {
            return "Error";
        }
    }

    public String login(String email, String password) throws SQLException {
        query = connection.prepareStatement("SELECT * FROM userInfo WHERE email = ? AND password = ?");
        query.setString(1, email);
        query.setString(2, password);

        ResultSet resultSet = query.executeQuery();

        if (resultSet.next()) {
            return "Success";
        } else {
            return "Error";
        }
    }
}
