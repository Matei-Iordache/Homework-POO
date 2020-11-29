package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import Utilities.Sort;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides a method to make a list of the most or least viewed
 * movies in the database
 */
public class MostViewedMovieQuery {
    List<MovieInputData> movies;
    List<UserInputData> users;

    public MostViewedMovieQuery(List<MovieInputData> movies, List<UserInputData> users) {
        this.movies = movies;
        this.users = users;
    }

    /**
     * Makes a list of the most or least viewed movies in the database.
     * Put a movie and it's duration in a hashtable then sort the hashtable.
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getMostViewedMovie(ActionInputData action) throws IOException {
        HashMap<String, Integer> filteredMovies = Filters.filteredMovies(movies,action);
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
        ArrayList<String> movies = new ArrayList<>(filteredMoviesSorted.keySet());
        if (action.getNumber() < movies.size()) {
            movies.subList(action.getNumber(),movies.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + movies);
    }
}
