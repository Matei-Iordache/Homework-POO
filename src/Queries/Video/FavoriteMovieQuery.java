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
 * Provides a method to obtain the movies that appear
 * in the favorite list of a user.
 */
public class FavoriteMovieQuery {
    private final List<UserInputData> Users;
    private final List<MovieInputData> Movies;

    public FavoriteMovieQuery(List<UserInputData> Users, List<MovieInputData> Movies) {
        this.Users = Users;
        this.Movies = Movies;
    }

    /**
     * Makes a list that contains movies that appear in the favorite lists of users.
     * The list is sorted in ascending or descending order based on the no_times
     * a movie appears in a favorite list of the user.
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void FilterFavorite(ActionInputData action) throws IOException {
        HashMap<String, Integer> filteredMovies = Filters.filteredMovies(Movies,action);
        // get how many users have the movie on favorites
        for (UserInputData user : Users) {
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
        ArrayList<String> movies = new ArrayList<>(filteredMoviesSorted.keySet());
        if (action.getNumber() < movies.size()) {
            movies.subList(action.getNumber(),movies.size()).clear();
        }
        Helper.writeToOutput(action,"Query result: " + movies);
    }
}
