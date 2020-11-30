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

/**
 * Provides a method to obtain the movies that appear
 * in the favorite list of a user.
 */
public class FavoriteMovieQuery {
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;

    public FavoriteMovieQuery(final List<UserInputData> users, final List<MovieInputData> movies) {
        this.users = users;
        this.movies = movies;
    }

    /**
     * Makes a list that contains movies that appear in the favorite lists of users.
     * The list is sorted in ascending or descending order based on the no_times
     * a movie appears in a favorite list of the user.
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void filterFavorite(final ActionInputData action) throws IOException {
        HashMap<String, Integer> filteredMovies = Filters.filteredMovies(movies, action);
        // get how many users have the movie on favorites
        for (UserInputData user : users) {
            ArrayList<String> favorites = user.getFavoriteMovies();
            for (String favorite : favorites) {
                if (filteredMovies.get(favorite) != null) {
                    int value = filteredMovies.get(favorite);
                    filteredMovies.replace(favorite, ++value);
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
