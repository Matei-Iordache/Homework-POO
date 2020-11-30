package queries.Video;

import utilities.Helper;
import utilities.Filters;
import utilities.Sort;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a method to make a list of the most or least viewed
 * movies in the database
 */
public class MostViewedMovieQuery {
    private final List<MovieInputData> movies;
    private final List<UserInputData> users;

    public MostViewedMovieQuery(final List<MovieInputData> movies,
                                final List<UserInputData> users) {
        this.movies = movies;
        this.users = users;
    }

    /**
     * Makes a list of the most or least viewed movies in the database.
     * Put a movie and it's duration in a hashtable then sort the hashtable.
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getMostViewedMovie(final ActionInputData action) throws IOException {
        HashMap<String, Integer> filteredMovies = Filters.filteredMovies(movies, action);
        ArrayList<String> filteredMoviesList = new ArrayList<>(filteredMovies.keySet());

        for (UserInputData user : users) {
            Map<String, Integer> history =  user.getHistory();
            for (String movie : filteredMoviesList) {
                if (history.containsKey(movie)) {
                    int value = filteredMovies.get(movie);
                    filteredMovies.replace(movie, value + history.get(movie));
                }
            }
        }
        filteredMovies.values().removeAll(Collections.singleton(0));
        var filteredMoviesSorted = Sort.sortIntegerMap(filteredMovies, action);
        ArrayList<String> moviesList = new ArrayList<>(filteredMoviesSorted.keySet());
        if (action.getNumber() < moviesList.size()) {
            moviesList.subList(action.getNumber(), moviesList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + moviesList);
    }
}
