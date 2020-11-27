package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import entertainment.Season;
import fileio.ActionInputData;
import fileio.SerialInputData;

import java.io.IOException;
import java.util.*;

public class LongestShowQuery {
    List<SerialInputData> shows;

    public LongestShowQuery(List<SerialInputData> shows) {
        this.shows = shows;
    }

    public void getLongestShowQuery(ActionInputData action) throws IOException {

        HashMap<String, Integer> FilteredShows = Filters.filteredShows(shows,action);

        for (SerialInputData show: shows) {
            if (FilteredShows.containsKey(show.getTitle())) {
                ArrayList<Season> seasons = show.getSeasons();
                int duration = 0;
                for (Season season: seasons) {
                    duration += season.getDuration();
                }
                FilteredShows.replace(show.getTitle(), duration);
            }
        }

        Map<String, Integer> FilteredShowsSorted = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            FilteredShows.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue()
                    .thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> FilteredShowsSorted.put(x.getKey(), x.getValue()));
        } else {
            FilteredShows.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> FilteredShowsSorted.put(x.getKey(), x.getValue()));
        }
        ArrayList<String> shows = new ArrayList<>(FilteredShowsSorted.keySet());
        if (action.getNumber() < shows.size()) {
            shows.subList(action.getNumber(),shows.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + shows);
    }
}
