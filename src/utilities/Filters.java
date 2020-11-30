package utilities;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.HashMap;
import java.util.List;


/**
 * Provides various methods that filter videos
 * based on the type of action requested
 */
public final class Filters {
    private Filters() {

    }
    /**
     * Method makes a hashmap of movies (and their ratings) that have the
     * specified genre and have been released in the specified year
     * @param movies List of movies in the database
     * @param action requested action
     * @param users list of users in database
     * @return Hashmap of filtered movies
     */
    public static HashMap<String, Double> filteredMovies(final List<MovieInputData> movies,
                                                         final ActionInputData action,
                                                         final List<UserInputData> users) {
        String filterYear = action.getFilters().get(0).get(0);
        String filterGenre = action.getFilters().get(1).get(0);
        HashMap<String, Double> filteredMovies = new HashMap<>();
        for (MovieInputData movie : movies) {
            int ok = 0;
            if (String.valueOf(movie.getYear()).equals(filterYear)) {
                ok++;
            }
            for (String gen : movie.getGenres()) {
                if (gen.equals(filterGenre)) {
                    ok++;
                }
            }
            if (filterYear == null) {
                ok++;
            }
            if (filterGenre == null) {
                ok++;
            }
            if (ok == 2) {
                filteredMovies.put(movie.getTitle(), Helper.getRatingMovie(users, movie));
            }
        }
        return filteredMovies;
    }

    /**
     * Method makes a hashmap of movies that have the
     * specified genre and have been released in the specified year
     * @param movies List of movies in the database
     * @param action requested action
     * @return Hashmap of filtered movies
     */
    public static HashMap<String, Integer> filteredMovies(final List<MovieInputData> movies,
                                                          final ActionInputData action) {
        String filterYear = action.getFilters().get(0).get(0);
        String filterGenre = action.getFilters().get(1).get(0);
        HashMap<String, Integer> filteredMovies = new HashMap<>();
        for (MovieInputData movie : movies) {
            int ok = 0;
            if (String.valueOf(movie.getYear()).equals(filterYear)) {
                ok++;
            }
            for (String gen : movie.getGenres()) {
                if (gen.equals(filterGenre)) {
                    ok++;
                }
            }
            if (filterYear == null) {
                ok++;
            }
            if (filterGenre == null) {
                ok++;
            }
            if (ok == 2) {
                filteredMovies.put(movie.getTitle(), 0);
            }
        }
        return filteredMovies;
    }


    /**
     * Method makes a hashmap of shows (and their ratings) that have the
     * specified genre and have been released in the specified year
     * @param shows List of shows in the database
     * @param action requested action
     * @param users list of users in database
     * @return Hashmap of filtered movies
     */
    public static HashMap<String, Double> filteredShows(final List<SerialInputData> shows,
                                                        final ActionInputData action,
                                                        final List<UserInputData> users) {
        String filterYear = action.getFilters().get(0).get(0);
        String filterGenre = action.getFilters().get(1).get(0);
        HashMap<String, Double> filteredShows = new HashMap<>();
        for (SerialInputData show : shows) {
            int ok = 0;
            if (String.valueOf(show.getYear()).equals(filterYear)) {
                ok++;
            }
            for (String gen : show.getGenres()) {
                if (gen.equals(filterGenre)) {
                    ok++;
                }
            }
            if (filterYear == null) {
                ok++;
            }
            if (filterGenre == null) {
                ok++;
            }
            if (ok == 2) {
                filteredShows.put(show.getTitle(), Helper.getRatingShow(users, show));
            }
        }
        return filteredShows;
    }


    /**
     * Method makes a hashmap of shows (and their ratings) that have the
     * specified genre and have been released in the specified year
     * @param shows List of shows in the database
     * @param action requested action
     * @return Hashmap of filtered movies
     */
    public static HashMap<String, Integer> filteredShows(final List<SerialInputData> shows,
                                                         final ActionInputData action) {
        String filterYear = action.getFilters().get(0).get(0);
        String filterGenre = action.getFilters().get(1).get(0);
        HashMap<String, Integer> filteredShows = new HashMap<>();
        for (SerialInputData show : shows) {
            int ok = 0;
            if (String.valueOf(show.getYear()).equals(filterYear)) {
                ok++;
            }
            for (String gen : show.getGenres()) {
                if (gen.equals(filterGenre)) {
                    ok++;
                }
            }
            if (filterYear == null) {
                ok++;
            }
            if (filterGenre == null) {
                ok++;
            }
            if (ok == 2) {
                filteredShows.put(show.getTitle(), 0);
            }
        }
        return filteredShows;
    }
}
