package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import Utilities.Sort;
import entertainment.Season;
import fileio.ActionInputData;
import fileio.SerialInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides a method to obtain a list with the longest or shortest shows
 */
public class LongestShowQuery {
    List<SerialInputData> shows;

    public LongestShowQuery(List<SerialInputData> shows) {
        this.shows = shows;
    }

    /**
     * Make a list with the longest or shortest shows in the database
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getLongestShowQuery(ActionInputData action) throws IOException {

        HashMap<String, Integer> filtered = Filters.filteredShows(shows,action);

        for (SerialInputData show: shows) {
            if (filtered.containsKey(show.getTitle())) {
                ArrayList<Season> seasons = show.getSeasons();
                int duration = 0;
                for (Season season: seasons) {
                    duration += season.getDuration();
                }
                filtered.replace(show.getTitle(), duration);
            }
        }

        var filteredSorted = Sort.sortIntegerMap(filtered, action);
        ArrayList<String> shows = new ArrayList<>(filteredSorted.keySet());
        if (action.getNumber() < shows.size()) {
            shows.subList(action.getNumber(),shows.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + shows);
    }
}
