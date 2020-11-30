package recommendation;

import utilities.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the Popular recommendation method
 */
public class PopularRec {
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> shows;

    public PopularRec(final List<UserInputData> users, final List<MovieInputData> movies,
                      final List<SerialInputData> shows) {
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    /**
     * finds the most unseen video from the most popular genre
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getPopular(final ActionInputData action) throws IOException {
        UserInputData user1 = Helper.findUser(users, action);
        assert user1 != null;
        if (user1.getSubscriptionType().equals("BASIC")) {
            Helper.writeToOutput(action, "PopularRecommendation cannot be applied!");
            return;
        }
        HashMap<String, Integer> genresPopularity = new HashMap<>();
            for (MovieInputData movie : movies) {
                ArrayList<String> genres = movie.getGenres();
                for (String genre : genres) {
                    if (!genresPopularity.containsKey(genre)) {
                        genresPopularity.put(genre, 0);
                    }
                }
            }
            for (SerialInputData show : shows) {
                ArrayList<String> genres = show.getGenres();
                for (String genre : genres) {
                    if (!genresPopularity.containsKey(genre)) {
                        genresPopularity.put(genre, 0);
                    }
                }
            }
        for (UserInputData user : users) {
            Map<String, Integer> history = user.getHistory();
            for (MovieInputData movie: movies) {
                if (history.containsKey(movie.getTitle())) {
                    ArrayList<String> genres = movie.getGenres();
                    for (String genre : genres) {
                        int value = genresPopularity.get(genre);
                        genresPopularity.replace(genre, value + 1);
                    }
                }
            }
            for (SerialInputData show: shows) {
                if (history.containsKey(show.getTitle())) {
                    ArrayList<String> genres = show.getGenres();
                    for (String genre : genres) {
                        int value = genresPopularity.get(genre);
                        genresPopularity.replace(genre, value + 1);
                    }
                }
            }
        }
        Map<String, Integer> genresPopularitySorted = new LinkedHashMap<>();
        genresPopularity.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .forEachOrdered(x -> genresPopularitySorted.put(x.getKey(), x.getValue()));
        var genresPopularitySortedList = new ArrayList<>(genresPopularitySorted.keySet());


        Map<String, Integer> history = user1.getHistory();
        for (String genre : genresPopularitySortedList) {
            for (MovieInputData movie : movies) {
                if (!history.containsKey(movie.getTitle())) {
                    ArrayList<String> genres = movie.getGenres();
                    if (genres.contains(genre)) {
                        String messsage = "PopularRecommendation result: " + movie.getTitle();
                        Helper.writeToOutput(action, messsage);
                        return;
                    }
                }
            }
            for (SerialInputData show : shows) {
                if (!history.containsKey(show.getTitle())) {
                    ArrayList<String> genres = show.getGenres();
                    if (genres.contains(genre)) {
                        String message = "PopularRecommendation result: " + show.getTitle();
                        Helper.writeToOutput(action, message);
                        return;
                    }
                }
            }
        }
        Helper.writeToOutput(action, "PopularRecommendation cannot be applied!");
    }
}
