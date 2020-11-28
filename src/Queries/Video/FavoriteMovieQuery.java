package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class FavoriteMovieQuery {
    private final List<UserInputData> Users;
    private final List<MovieInputData> Movies;

    public FavoriteMovieQuery(List<UserInputData> Users, List<MovieInputData> Movies) {
        this.Users = Users;
        this.Movies = Movies;
    }

    public void FilterFavorite(ActionInputData action) throws IOException {
        HashMap<String, Integer> filteredMovies = Filters.filteredMovies(Movies,action);
        // get how many users have the movie on favorites
        for (UserInputData user : Users) {
            ArrayList<String> favorites = user.getFavoriteMovies();
            for (String favorite : favorites) {
                if (filteredMovies.get(favorite) != null) {
                    int value = filteredMovies.get(favorite);
                    filteredMovies.replace(favorite, ++value);
                }
            }
        }
        // Helper.FavoriteQuerySort(filteredMovies, action);
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
        filteredMovies.values().removeAll(Collections.singleton(0));
        Helper.writeToOutput(action,"Query result: " + movies);
    }
}
