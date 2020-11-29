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
 * Provides a method to make a list of the most or least viewed
 * shows in the database
 */
public class MostViewedShowQuery {
    List<SerialInputData> shows;
    List<UserInputData> users;

    public MostViewedShowQuery(List<SerialInputData> shows, List<UserInputData> users) {
        this.shows = shows;
        this.users = users;
    }

    /**
     * Makes a list of the most or least viewed shows in the database.
     * Put a show and it's duration in a hashtable then sort the hashtable.
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getMostViewedShowQuery(ActionInputData action) throws IOException {
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
        ArrayList<String> shows = new ArrayList<>(filteredShowsSorted.keySet());
        if (action.getNumber() < shows.size()) {
            shows.subList(action.getNumber(),shows.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + shows);
    }
}
