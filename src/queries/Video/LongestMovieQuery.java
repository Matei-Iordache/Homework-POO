package queries.Video;

import utilities.Helper;
import utilities.Filters;
import utilities.Sort;
import fileio.ActionInputData;
import fileio.MovieInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Provides a method to obtain a list with the longest or shortest movies
 */
public class LongestMovieQuery {
    private final List<MovieInputData> movies;

    public LongestMovieQuery(final List<MovieInputData> movies) {
        this.movies = movies;
    }

    /**
     * Make a list with the longest or shortest movies in the database
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getLongestMovieQuery(final ActionInputData action) throws IOException {

        HashMap<String, Integer> filtered = Filters.filteredMovies(movies, action);

        for (MovieInputData movie : movies) {
            if (filtered.containsKey(movie.getTitle())) {
                filtered.replace(movie.getTitle(), movie.getDuration());
            }
        }
        filtered.values().removeAll(Collections.singleton(0));
        var filteredSorted = Sort.sortIntegerMap(filtered, action);
        ArrayList<String> moviesList = new ArrayList<>(filteredSorted.keySet());
        if (action.getNumber() < moviesList.size()) {
            moviesList.subList(action.getNumber(), moviesList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + moviesList);
    }
}
