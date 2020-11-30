package queries.Video;

import utilities.Helper;
import utilities.Filters;
import utilities.Sort;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Provides a method to obtain the lowest or highest rated movies
 */
public class RatingMovieQuery {
    private final List<MovieInputData> movies;
    private final List<UserInputData> users;

    public RatingMovieQuery(final List<MovieInputData> movies,
                            final List<UserInputData> users) {
        this.movies = movies;
        this.users = users;
    }

    /**
     * Makes a list with the lowest or highest rated movies
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getRatingMovieQuery(final ActionInputData action) throws IOException {
        HashMap<String, Double> filteredMovies = Filters.filteredMovies(movies, action, users);

        filteredMovies.values().removeAll(Collections.singleton(0.0));

        var filteredMoviesSorted = Sort.sortDoubleMap(filteredMovies, action);

        ArrayList<String> actorsList = new ArrayList<>(filteredMoviesSorted.keySet());
        if (action.getNumber() < actorsList.size()) {
            actorsList.subList(action.getNumber(), actorsList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + actorsList);
    }
}
