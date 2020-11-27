package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class RatingMovieQuery {
    List<MovieInputData> movies;
    List<UserInputData> users;

    public RatingMovieQuery(List<MovieInputData> movies, List<UserInputData> users) {
        this.movies = movies;
        this.users = users;
    }

    public void getRatingMovieQuery(ActionInputData action) throws IOException {
        HashMap<String, Double> filteredMovies = Filters.filteredMovies(movies, action, users);

        filteredMovies.values().removeAll(Collections.singleton(0.0));
        Map<String, Double> filteredMovies2 = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            filteredMovies.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> filteredMovies2.put(x.getKey(), x.getValue()));
        } else {
            filteredMovies.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> filteredMovies2.put(x.getKey(), x.getValue()));
        }
        ArrayList<String> Actors = new ArrayList<>(filteredMovies2.keySet());
        if (action.getNumber() < Actors.size()) {
            Actors.subList(action.getNumber(),Actors.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + Actors);
    }
}
