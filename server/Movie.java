import org.json.JSONObject;

public class Movie {
    private String title;
    private String director;
    private int year;
    private int duration;
    private String genre;
    private int classification;
    private int rating;
    private String description;

    public Movie(String title, String director, int year, int duration, String genre, int classification, int rating, String description) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.duration = duration;
        this.genre = genre;
        this.classification = classification;
        this.rating = rating;
        this.description = description;
    }

    public String toJson() {
        JSONObject movieData = new JSONObject();
        movieData.put("title", title);
        movieData.put("director", director);
        movieData.put("year", year);
        movieData.put("duration", duration);
        movieData.put("genre", genre);
        movieData.put("classification", classification);
        movieData.put("rating", rating);
        movieData.put("description", description);
        return movieData.toString();
    }

    public static Movie fromJson(String jsonStr) {
        JSONObject movieData = new JSONObject(jsonStr);
        return new Movie(
                movieData.getString("title"),
                movieData.getString("director"),
                movieData.getInt("year"),
                movieData.getInt("duration"),
                movieData.getString("genre"),
                movieData.getInt("classification"),
                movieData.getInt("rating"),
                movieData.getString("description")
        );
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public String getGenre() {
        return genre;
    }

    public int getClassification() {
        return classification;
    }

    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }
}
