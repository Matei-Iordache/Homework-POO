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
 * Provides a method to obtain the lowest or highest rated movies
 */
public class RatingShowQuery {
    List<SerialInputData> shows;
    List<UserInputData> users;

    public RatingShowQuery(List<SerialInputData> shows, List<UserInputData> users) {
        this.shows = shows;
        this.users = users;
    }

    /**
     * Makes a list with the lowest or highest rated movies
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getRatingShowQuery(ActionInputData action) throws IOException {
        HashMap<String, Double> filteredShows = Filters.filteredShows(shows,action,users);

        filteredShows.values().removeAll(Collections.singleton(0.0));
        var filteredShowsSorted = Sort.sortDoubleMap(filteredShows, action);
        ArrayList<String> Actors = new ArrayList<>(filteredShowsSorted.keySet());
        if (action.getNumber() < Actors.size()) {
            Actors.subList(action.getNumber(),Actors.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + Actors);
    }
}
