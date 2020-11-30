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
 * Provides a method to obtain the lowest or highest rated movies
 */
public class RatingShowQuery {
    private final List<SerialInputData> shows;
    private final List<UserInputData> users;

    public RatingShowQuery(final List<SerialInputData> shows,
                           final List<UserInputData> users) {
        this.shows = shows;
        this.users = users;
    }

    /**
     * Makes a list with the lowest or highest rated movies
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getRatingShowQuery(final ActionInputData action) throws IOException {
        HashMap<String, Double> filteredShows = Filters.filteredShows(shows, action, users);

        filteredShows.values().removeAll(Collections.singleton(0.0));
        var filteredShowsSorted = Sort.sortDoubleMap(filteredShows, action);
        ArrayList<String> actorsList = new ArrayList<>(filteredShowsSorted.keySet());
        if (action.getNumber() < actorsList.size()) {
            actorsList.subList(action.getNumber(), actorsList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + actorsList);
    }
}
