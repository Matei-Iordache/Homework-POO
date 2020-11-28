package Utilities;

import Commands.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.HashMap;
import java.util.List;

public class Filters {

    public static HashMap<String, Double> filteredMovies(List<MovieInputData> movies, ActionInputData action,
                                                         List<UserInputData> users) {
        String filterYear = action.getFilters().get(0).get(0);
        String filterGenre = action.getFilters().get(1).get(0);
        HashMap<String, Double> filteredMovies = new HashMap<>();
        for (MovieInputData movie : movies) {
            int ok = 0;
            if (String.valueOf(movie.getYear()).equals(filterYear)) {
                ok++;
            }
            for (String gen : movie.getGenres()) {
                if (gen.equals(filterGenre)) {
                    ok++;
                }
            }
            if (filterYear == null) {
                ok ++;
            }
            if (filterGenre == null) {
                ok ++;
            }
            if (ok == 2) {
                filteredMovies.put(movie.getTitle(), Helper.getRatingMovie(users, movie));
            }
        }
        return filteredMovies;
    }

    public static HashMap<String, Integer> filteredMovies(List<MovieInputData> movies, ActionInputData action) {
        String filterYear = action.getFilters().get(0).get(0);
        String filterGenre = action.getFilters().get(1).get(0);
        HashMap<String, Integer> filteredMovies = new HashMap<>();
        for (MovieInputData movie : movies) {
            int ok = 0;
            if (String.valueOf(movie.getYear()).equals(filterYear)) {
                ok++;
            }
            for (String gen : movie.getGenres()) {
                if (gen.equals(filterGenre)) {
                    ok++;
                }
            }
            if (filterYear == null) {
                ok ++;
            }
            if (filterGenre == null) {
                ok ++;
            }
            if (ok == 2) {
                filteredMovies.put(movie.getTitle(), 0);
            }
        }
        return filteredMovies;
    }

    public static HashMap<String, Double> filteredShows(List<SerialInputData> shows, ActionInputData action,
                                                        List<UserInputData> users) {
        String filterYear = action.getFilters().get(0).get(0);
        String filterGenre = action.getFilters().get(1).get(0);
        HashMap<String, Double> filteredShows = new HashMap<>();
        for (SerialInputData show : shows) {
            int ok = 0;
            if (String.valueOf(show.getYear()).equals(filterYear)) {
                ok++;
            }
            for (String gen : show.getGenres()) {
                if (gen.equals(filterGenre)) {
                    ok++;
                }
            }
            if (filterYear == null) {
                ok ++;
            }
            if (filterGenre == null) {
                ok ++;
            }
            if (ok == 2) {
                filteredShows.put(show.getTitle(), Helper.getRatingShow(users, show));
            }
        }
        return filteredShows;
    }

    public static HashMap<String, Integer> filteredShows(List<SerialInputData> shows, ActionInputData action) {
        String filterYear = action.getFilters().get(0).get(0);
        String filterGenre = action.getFilters().get(1).get(0);
        HashMap<String, Integer> filteredShows = new HashMap<>();
        for (SerialInputData show : shows) {
            int ok = 0;
            if (String.valueOf(show.getYear()).equals(filterYear)) {
                ok++;
            }
            for (String gen : show.getGenres()) {
                if (gen.equals(filterGenre)) {
                    ok++;
                }
            }
            if (filterYear == null) {
                ok ++;
            }
            if (filterGenre == null) {
                ok ++;
            }
            if (ok == 2) {
                filteredShows.put(show.getTitle(), 0);
            }
        }
        return filteredShows;
    }
}
