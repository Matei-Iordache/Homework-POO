package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class MostViewedMovieQuery {
    List<MovieInputData> movies;
    List<UserInputData> users;

    public MostViewedMovieQuery(List<MovieInputData> movies, List<UserInputData> users) {
        this.movies = movies;
        this.users = users;
    }

    public void getMostViewedMovie(ActionInputData action) throws IOException {
        HashMap<String, Integer> filteredMovies = Filters.filteredMovies(movies,action);
        ArrayList<String> filteredMoviesList = new ArrayList<>(filteredMovies.keySet());

        for (UserInputData user : users) {
            Map<String, Integer> history =  user.getHistory();
            for (String movie : filteredMoviesList) {
                if (history.containsKey(movie)) {
                    int value = filteredMovies.get(movie);
                    filteredMovies.replace(movie, value + history.get(movie));
                }
            }
        }
        filteredMovies.values().removeAll(Collections.singleton(0));
        Map<String, Integer> FilteredSorted = new LinkedHashMap<>();

        if (action.getSortType().equals("asc")) {
            filteredMovies.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        } else {
            filteredMovies.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        }
        ArrayList<String> movies = new ArrayList<>(FilteredSorted.keySet());
        if (action.getNumber() < movies.size()) {
            movies.subList(action.getNumber(),movies.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + movies);
    }
}
