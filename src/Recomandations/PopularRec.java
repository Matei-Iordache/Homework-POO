package Recomandations;

import Commands.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class PopularRec {
    List<UserInputData> users;
    List<MovieInputData> movies;
    List<SerialInputData> shows;

    public PopularRec(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> shows) {
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    public void getPopular(ActionInputData action) throws IOException {
        UserInputData user1 = Helper.findUser(users, action);
        assert user1 != null;
        if (user1.getSubscriptionType().equals("BASIC")) {
            Helper.writeToOutput(action,"PopularRecommendation cannot be applied!");
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
        for(UserInputData user : users) {
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
        ArrayList<String> genresPopularitySortedList = new ArrayList<>(genresPopularitySorted.keySet());


        Map<String, Integer> history = user1.getHistory();
        for(String genre : genresPopularitySortedList) {
            for(MovieInputData movie: movies) {
                if (!history.containsKey(movie.getTitle())) {
                    ArrayList<String> genres = movie.getGenres();
                    if (genres.contains(genre)) {
                        Helper.writeToOutput(action, "PopularRecommendation result: " + movie.getTitle());
                        return;
                    }
                }
            }
            for(SerialInputData show: shows) {
                if (!history.containsKey(show.getTitle())) {
                    ArrayList<String> genres = show.getGenres();
                    if (genres.contains(genre)) {
                        Helper.writeToOutput(action, "PopularRecommendation result: " + show.getTitle());
                        return;
                    }
                }
            }
        }
    }
}
