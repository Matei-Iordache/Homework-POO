package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import Utilities.Sort;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides a method to obtain the lowest or highest rated movies
 */
public class RatingMovieQuery {
    List<MovieInputData> movies;
    List<UserInputData> users;

    public RatingMovieQuery(List<MovieInputData> movies, List<UserInputData> users) {
        this.movies = movies;
        this.users = users;
    }

    /**
     * Makes a list with the lowest or highest rated movies
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getRatingMovieQuery(ActionInputData action) throws IOException {
        HashMap<String, Double> filteredMovies = Filters.filteredMovies(movies, action, users);

        filteredMovies.values().removeAll(Collections.singleton(0.0));

        var filteredMoviesSorted = Sort.sortDoubleMap(filteredMovies, action);

        ArrayList<String> Actors = new ArrayList<>(filteredMoviesSorted.keySet());
        if (action.getNumber() < Actors.size()) {
            Actors.subList(action.getNumber(),Actors.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + Actors);
    }
}
