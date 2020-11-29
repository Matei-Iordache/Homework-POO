package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import Utilities.Sort;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides a method to obtain the shows that appear
 * in the favorite list of a user.
 */
public class FavoriteShowQuery {
    private final List<UserInputData> Users;
    private final List<SerialInputData> Shows;

    public FavoriteShowQuery(List<UserInputData> Users, List<SerialInputData> Shows) {
        this.Users = Users;
        this.Shows = Shows;
    }

    /**
     * Makes a list that contains shows that appear in the favorite lists of users.
     * The list is sorted in ascending or descending order based on the no_times
     * a show appears in a favorite list of the user.
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void FilterFavorite(ActionInputData action) throws IOException {
        // movies that match the filters
        HashMap<String, Integer> filteredShows = Filters.filteredShows(Shows,action);
        // get how many users have the movie on favorites
        for (UserInputData user : Users) {
            ArrayList<String> favorites = user.getFavoriteMovies();
            for (String favorite : favorites) {
                if (filteredShows.get(favorite) != null) {
                    int value = filteredShows.get(favorite);
                    filteredShows.replace(favorite, ++value);
                }
            }
        }
        filteredShows.values().removeAll(Collections.singleton(0));
        var filteredShowsSorted = Sort.sortIntegerMap(filteredShows, action) ;
        ArrayList<String> movies = new ArrayList<>(filteredShowsSorted.keySet());
        if (action.getNumber() < movies.size()) {
            movies.subList(action.getNumber(),movies.size()).clear();
        }
        Helper.writeToOutput(action,"Query result: " + movies);
    }
}
