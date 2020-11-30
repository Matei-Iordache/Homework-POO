package recommendation;

import utilities.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the BestUnseenRecommendation method
 */
public class BestUnseenRec {
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> shows;

    public BestUnseenRec(final List<UserInputData> users, final List<MovieInputData> movies,
                         final List<SerialInputData> shows) {
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    /**
     * Adds to output the best rated unseen movie
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getBestUnseenRec(final ActionInputData action) throws IOException {
        UserInputData user = Helper.findUser(users, action);
        HashMap<String, Double> ratedMovies = new HashMap<>();
        for (MovieInputData movie: movies) {
            ratedMovies.put(movie.getTitle(), Helper.getRatingMovie(users, movie));
        }
        ratedMovies.values().removeAll(Collections.singleton(0.0));

        HashMap<String, Double> ratedShows = new HashMap<>();
        for (SerialInputData show : shows) {
            ratedShows.put(show.getTitle(), Helper.getRatingShow(users, show));
        }
        ratedShows.values().removeAll(Collections.singleton(0.0));

        // sort descendent
        Map<String, Double> sortedMovies = new LinkedHashMap<>();
        ratedMovies.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> sortedMovies.put(x.getKey(), x.getValue()));
        ArrayList<String> sortedMoviesList = new ArrayList<>(sortedMovies.keySet());

        // sort descendent
        Map<String, Double> sortedShows = new LinkedHashMap<>();
        ratedShows.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .forEachOrdered(x -> sortedShows.put(x.getKey(), x.getValue()));
        ArrayList<String> sortedShowsList = new ArrayList<>(sortedShows.keySet());


        assert user != null;
        Map<String, Integer> history = user.getHistory();

        for (String movie : sortedMoviesList) {
            if (!history.containsKey(movie)) {
                Helper.writeToOutput(action, "BestRatedUnseenRecommendation result: " + movie);
                return;
            }
        }
        for (String show : sortedShowsList) {
            if (!history.containsKey(show)) {
                Helper.writeToOutput(action, "BestRatedUnseenRecommendation result: " + show);
                return;
            }
        }
        for (MovieInputData movie : movies) {
            if (!history.containsKey(movie.getTitle())) {
                String message = "BestRatedUnseenRecommendation result: " + movie.getTitle();
                Helper.writeToOutput(action, message);
                return;
            }
        }
        for (SerialInputData show : shows) {
            if (!history.containsKey(show.getTitle())) {
                String message = "BestRatedUnseenRecommendation result: " + show.getTitle();
                Helper.writeToOutput(action, message);
                return;
            }
        }
        Helper.writeToOutput(action, "BestRatedUnseenRecommendation cannot be applied!");
    }
}
