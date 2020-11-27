package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class RatingShowQuery {
    List<SerialInputData> shows;
    List<UserInputData> users;

    public RatingShowQuery(List<SerialInputData> shows, List<UserInputData> users) {
        this.shows = shows;
        this.users = users;
    }

    public void getRatingShowQuery(ActionInputData action) throws IOException {
        HashMap<String, Double> filteredShows = Filters.filteredShows(shows,action,users);

        filteredShows.values().removeAll(Collections.singleton(0.0));
        Map<String, Double> filteredShows2 = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            filteredShows.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> filteredShows2.put(x.getKey(), x.getValue()));
        } else {
            filteredShows.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> filteredShows2.put(x.getKey(), x.getValue()));
        }
        ArrayList<String> Actors = new ArrayList<>(filteredShows2.keySet());
        if (action.getNumber() < Actors.size()) {
            Actors.subList(action.getNumber(),Actors.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + Actors);
    }
}
