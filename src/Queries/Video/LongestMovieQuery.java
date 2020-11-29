package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import Utilities.Sort;
import fileio.ActionInputData;
import fileio.MovieInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides a method to obtain a list with the longest or shortest movies
 */
public class LongestMovieQuery {
    List<MovieInputData> movies;

    public LongestMovieQuery(List<MovieInputData> movies) {
        this.movies = movies;
    }

    /**
     * Make a list with the longest or shortest movies in the database
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getLongestMovieQuery(ActionInputData action) throws IOException {

        HashMap<String, Integer> filtered = Filters.filteredMovies(movies,action);

        for (MovieInputData movie : movies) {
            if (filtered.containsKey(movie.getTitle())) {
                filtered.replace(movie.getTitle(),movie.getDuration());
            }
        }
        filtered.values().removeAll(Collections.singleton(0));
        var filteredSorted = Sort.sortIntegerMap(filtered, action);
        ArrayList<String> movies = new ArrayList<>(filteredSorted.keySet());
        if (action.getNumber() < movies.size()) {
            movies.subList(action.getNumber(),movies.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + movies);
    }
}
