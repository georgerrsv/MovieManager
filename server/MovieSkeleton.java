package server;
import java.sql.SQLException;

public class MovieSkeleton {
    private Database db;


    public MovieSkeleton(Database db) {

        this.db = db;
    }

    public String addMovie(String arguments) {
        Movie movie = Movie.fromJson(arguments);
        try {
            return db.addMovie(movie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String removeMovie(String arguments) {
        int id = Integer.parseInt(arguments);
        try {
            return db.removeMovie(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String showDetails(String arguments) {
        int id = Integer.parseInt(arguments);
        try {
            return db.showDetails(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String showCatalog() {
        try {
            return db.showCatalog();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}