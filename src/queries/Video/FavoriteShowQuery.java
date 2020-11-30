package queries.Video;

import utilities.Helper;
import utilities.Filters;
import utilities.Sort;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Provides a method to obtain the shows that appear
 * in the favorite list of a user.
 */
public class FavoriteShowQuery {
    private final List<UserInputData> users;
    private final List<SerialInputData> shows;

    public FavoriteShowQuery(final List<UserInputData> users, final List<SerialInputData> shows) {
        this.users = users;
        this.shows = shows;
    }

    /**
     * Makes a list that contains shows that appear in the favorite lists of users.
     * The list is sorted in ascending or descending order based on the no_times
     * a show appears in a favorite list of the user.
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void filterFavorite(final ActionInputData action) throws IOException {
        // movies that match the filters
        HashMap<String, Integer> filteredShows = Filters.filteredShows(shows, action);
        // get how many users have the movie on favorites
        for (UserInputData user : users) {
            ArrayList<String> favorites = user.getFavoriteMovies();
            for (String favorite : favorites) {
                if (filteredShows.get(favorite) != null) {
                    int value = filteredShows.get(favorite);
                    filteredShows.replace(favorite, ++value);
                }
            }
        }
        filteredShows.values().removeAll(Collections.singleton(0));
        var filteredShowsSorted = Sort.sortIntegerMap(filteredShows, action);
        ArrayList<String> showsList = new ArrayList<>(filteredShowsSorted.keySet());
        if (action.getNumber() < showsList.size()) {
            showsList.subList(action.getNumber(), showsList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + showsList);
    }
}
