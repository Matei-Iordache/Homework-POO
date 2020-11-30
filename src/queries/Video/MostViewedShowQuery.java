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
import java.util.Map;

/**
 * Provides a method to make a list of the most or least viewed
 * shows in the database
 */
public class MostViewedShowQuery {
    private final List<SerialInputData> shows;
    private final List<UserInputData> users;

    public MostViewedShowQuery(final List<SerialInputData> shows,
                               final List<UserInputData> users) {
        this.shows = shows;
        this.users = users;
    }

    /**
     * Makes a list of the most or least viewed shows in the database.
     * Put a show and it's duration in a hashtable then sort the hashtable.
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getMostViewedShowQuery(final ActionInputData action) throws IOException {
        HashMap<String, Integer> filteredShows = Filters.filteredShows(shows, action);
        ArrayList<String> showsList = new ArrayList<>(filteredShows.keySet());

        for (UserInputData user : users) {
            Map<String, Integer> history =  user.getHistory();
            for (String show : showsList) {
                if (history.containsKey(show)) {
                    int value = filteredShows.get(show);
                    filteredShows.replace(show, value + history.get(show));
                }
            }
        }
        filteredShows.values().removeAll(Collections.singleton(0));
        var filteredShowsSorted = Sort.sortIntegerMap(filteredShows, action);
        ArrayList<String> showsSortedList = new ArrayList<>(filteredShowsSorted.keySet());
        if (action.getNumber() < showsSortedList.size()) {
            showsSortedList.subList(action.getNumber(), showsSortedList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + showsSortedList);
    }
}
