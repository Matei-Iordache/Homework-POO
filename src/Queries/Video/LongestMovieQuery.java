package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import fileio.ActionInputData;
import fileio.MovieInputData;

import java.io.IOException;
import java.util.*;

public class LongestMovieQuery {
    List<MovieInputData> movies;

    public LongestMovieQuery(List<MovieInputData> movies) {
        this.movies = movies;
    }

    public void getLongestMovieQuery(ActionInputData action) throws IOException {

        HashMap<String, Integer> Filtered = Filters.filteredMovies(movies,action);

        for (MovieInputData movie : movies) {
            if (Filtered.containsKey(movie.getTitle())) {
                Filtered.replace(movie.getTitle(),movie.getDuration());
            }
        }
        Filtered.values().removeAll(Collections.singleton(0));
        Map<String, Integer> FilteredSorted = new LinkedHashMap<>();

        if (action.getSortType().equals("asc")) {
            Filtered.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        } else {
            Filtered.entrySet()
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
