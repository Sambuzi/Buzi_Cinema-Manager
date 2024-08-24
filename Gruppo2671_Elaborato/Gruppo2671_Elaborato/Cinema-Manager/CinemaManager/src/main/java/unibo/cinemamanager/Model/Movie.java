package unibo.cinemamanager.Model;

/**
 * Represents a movie in the cinema management system.
 */
public final class Movie {

    private int id;
    private String title;
    private String description;
    private String releaseDate;
    private String genre;
    private int duration;

    /**
     * Gets the ID of the movie.
     *
     * @return the movie ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the movie.
     *
     * @param id the ID to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Gets the title of the movie.
     *
     * @return the movie title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title the title to set
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Gets the description of the movie.
     *
     * @return the movie description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the movie.
     *
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets the release date of the movie.
     *
     * @return the release date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Sets the release date of the movie.
     *
     * @param releaseDate the release date to set
     */
    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the genre of the movie.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the movie.
     *
     * @param genre the genre to set
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     * Gets the duration of the movie in minutes.
     *
     * @return the duration in minutes
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the movie in minutes.
     *
     * @param duration the duration to set in minutes
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
