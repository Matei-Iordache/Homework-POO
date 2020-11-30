package queries.Video;

import utilities.Helper;
import utilities.Filters;
import utilities.Sort;
import entertainment.Season;
import fileio.ActionInputData;
import fileio.SerialInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Provides a method to obtain a list with the longest or shortest shows
 */
public class LongestShowQuery {
    private final List<SerialInputData> shows;

    public LongestShowQuery(final List<SerialInputData> shows) {
        this.shows = shows;
    }

    /**
     * Make a list with the longest or shortest shows in the database
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getLongestShowQuery(final ActionInputData action) throws IOException {

        HashMap<String, Integer> filtered = Filters.filteredShows(shows, action);

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
        ArrayList<String> showsList = new ArrayList<>(filteredSorted.keySet());
        if (action.getNumber() < showsList.size()) {
            showsList.subList(action.getNumber(), showsList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + showsList);
    }
}
