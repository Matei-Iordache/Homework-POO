package queries.Actors;

import utilities.Helper;
import utilities.Sort;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Provides a method to obtain the actors
 * with the highest or the lowest average ratings
 */
public class Average {
    private final List<ActorInputData> actors;
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> shows;

    public Average(final List<ActorInputData> actors, final List<UserInputData> users,
                   final List<MovieInputData> movies, final List<SerialInputData> shows) {
        this.actors = actors;
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    /**
     * Method provides a sorted list of the actors that have
     * the highest or the lowest rated movies.
     * Method obtains the average rating of an actor and stores it
     * in a hashtable.
     * Then sort the hashtable in ascending or descending order.
     * And finally concert the keys of the hashtable into a list
     * and pass it to output.
     * @param action requested action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getAverage(final ActionInputData action) throws IOException {
        HashMap<String, Double> rates = new HashMap<>();
        for (ActorInputData actor : actors) {
            ArrayList<String> playedFilms = actor.getFilmography();
            double rating = 0; // sum of the ratings of an actor
            int counter = 0; // no_times a rating is not 0

            // get rating for the movies an actor played in
            for (MovieInputData movie: movies) {
                if (playedFilms.contains(movie.getTitle())) {
                    rating += Helper.getRatingMovie(users, movie);
                    if (Helper.getRatingMovie(users, movie) != 0) {
                        counter++;
                    }
                }
            }
            // get rating for the shows an actor played in
            for (SerialInputData show : shows) {
                if (playedFilms.contains(show.getTitle())) {
                    rating += Helper.getRatingShow(users, show);
                    if (Helper.getRatingShow(users, show) != 0) {
                        counter++;
                    }
                }
            }

            if (counter != 0) {
                rating /= counter;
                rates.put(actor.getName(), rating);
            }
        }

        LinkedHashMap<String, Double> ratesSorted = Sort.sortDoubleMap(rates, action);

        // sorted ratings in form of a list
        ArrayList<String> ratesList = new ArrayList<>(ratesSorted.keySet());
        if (action.getNumber() < ratesList.size()) {
            ratesList.subList(action.getNumber(), ratesList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + ratesList);
    }
}
