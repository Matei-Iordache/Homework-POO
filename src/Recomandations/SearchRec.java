package Recomandations;

import Commands.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides the Search Recommendation method
 */
public class SearchRec {
    List<UserInputData> users;
    List<MovieInputData> movies;
    List<SerialInputData> shows;

    public SearchRec(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> shows) {
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    /**
     * Makes a list of all the videos unseen from a genre
     * sorted in ascendent order by the rating
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getSearchRec(ActionInputData action) throws IOException {
        UserInputData user = Helper.findUser(users, action);
        assert user != null;
        if (user.getSubscriptionType().equals("BASIC")) {
            Helper.writeToOutput(action,"SearchRecommendation cannot be applied!");
            return;
        }
        String genre = action.getGenre();

        Map<String, Integer> history = user.getHistory();

        HashMap<String, Double> notSeen = new HashMap<>();

        for (MovieInputData movie : movies) {
            if (!history.containsKey(movie.getTitle())) {
                ArrayList<String> genres = movie.getGenres();
                if (genres.contains(genre)) {
                    notSeen.put(movie.getTitle(), Helper.getRatingMovie(users,movie));
                }
            }
        }

        for (SerialInputData show : shows) {
            if (!history.containsKey(show.getTitle())) {
                ArrayList<String> genres = show.getGenres();
                if (genres.contains(genre)) {
                    notSeen.put(show.getTitle(), Helper.getRatingShow(users,show));
                }
            }
        }
        if (notSeen.isEmpty()) {
            Helper.writeToOutput(action, "SearchRecommendation cannot be applied!");
            return;
        }
        Map<String, Double> notSeenSorted = new LinkedHashMap<>();

        notSeen.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue()
                .thenComparing(Map.Entry.comparingByKey()))
                .forEachOrdered(x -> notSeenSorted.put(x.getKey(), x.getValue()));

        ArrayList<String> notSeenSorterdList = new ArrayList<>(notSeenSorted.keySet());
        Helper.writeToOutput(action, "SearchRecommendation result: " + notSeenSorterdList);

    }

}
