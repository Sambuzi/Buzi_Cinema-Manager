package unibo.cinemamanager.Model;

public class Projection {
    private int id;
    private int movieId;
    private String movieTitle;  // Aggiungi questo campo
    private String projectionDate;
    private String projectionTime;
    private String hall;
    private int availableSeats;  // Aggiungi questo campo

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getProjectionDate() {
        return projectionDate;
    }

    public void setProjectionDate(String projectionDate) {
        this.projectionDate = projectionDate;
    }

    public String getProjectionTime() {
        return projectionTime;
    }

    public void setProjectionTime(String projectionTime) {
        this.projectionTime = projectionTime;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
