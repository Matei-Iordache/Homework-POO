package Recomandations;

import Commands.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class BestUnseenRec {
    List<UserInputData> users;
    List<MovieInputData> movies;
    List<SerialInputData> shows;

    public BestUnseenRec(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> shows) {
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    public void getBestUnseenRec(ActionInputData action) throws IOException {
        UserInputData user = Helper.findUser(users, action);
        HashMap<String, Double> ratedMovies = new HashMap<>();
        for (MovieInputData movie: movies) {
            ratedMovies.put(movie.getTitle(), Helper.getRatingMovie(users,movie));
        }
        ratedMovies.values().removeAll(Collections.singleton(0.0));

        HashMap<String, Double> ratedShows = new HashMap<>();
        for (SerialInputData show : shows) {
            ratedShows.put(show.getTitle(), Helper.getRatingShow(users, show));
        }
        ratedShows.values().removeAll(Collections.singleton(0.0));

        Map<String, Double> SortedMovies = new LinkedHashMap<>();
        ratedMovies.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> SortedMovies.put(x.getKey(), x.getValue()));
        ArrayList<String> sortedMoviesList = new ArrayList<>(SortedMovies.keySet());

        Map<String, Double> SortedShows = new LinkedHashMap<>();
        ratedShows.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                .forEachOrdered(x -> SortedShows.put(x.getKey(), x.getValue()));
        ArrayList<String> sortedShowsList = new ArrayList<>(SortedShows.keySet());


        assert user != null;
        Map<String, Integer> history = user.getHistory();

        for (String movie: sortedMoviesList) {
            if (!history.containsKey(movie)) {
                Helper.writeToOutput(action, "BestRatedUnseenRecommendation result: " + movie);
                return;
            }
        }
        for (String show: sortedShowsList) {
            if (!history.containsKey(show)) {
                Helper.writeToOutput(action, "BestRatedUnseenRecommendation result: " + show);
                return;
            }
        }
        for (MovieInputData movie : movies) {
            if (!history.containsKey(movie.getTitle())) {
                Helper.writeToOutput(action, "BestRatedUnseenRecommendation result: " + movie.getTitle());
                return;
            }
        }
        for (SerialInputData show: shows) {
            if (!history.containsKey(show.getTitle())) {
                Helper.writeToOutput(action, "BestRatedUnseenRecommendation result: " + show.getTitle());
                return;
            }
        }
        Helper.writeToOutput(action,"BestRatedUnseenRecommendation cannot be applied!");
    }
}
