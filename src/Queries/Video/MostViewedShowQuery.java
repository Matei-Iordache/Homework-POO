package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class MostViewedShowQuery {
    List<SerialInputData> shows;
    List<UserInputData> users;

    public MostViewedShowQuery(List<SerialInputData> shows, List<UserInputData> users) {
        this.shows = shows;
        this.users = users;
    }

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
        Map<String, Integer> FilteredSorted = new LinkedHashMap<>();

        if (action.getSortType().equals("asc")) {
            filteredShows.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        } else {
            filteredShows.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        }
        ArrayList<String> shows = new ArrayList<>(FilteredSorted.keySet());
        if (action.getNumber() < shows.size()) {
            shows.subList(action.getNumber(),shows.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + shows);
    }
}
