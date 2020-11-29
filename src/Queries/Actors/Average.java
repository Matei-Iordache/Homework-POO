package Queries.Actors;

import Commands.Helper;
import Utilities.Sort;
import fileio.*;

import java.io.IOException;
import java.util.*;

/**
 * Provides a method to obtain the actors
 * with the highest or the lowest average ratings
 */
public class Average {
    private final List<ActorInputData> Actors;
    private final List<UserInputData> Users;
    private final List<MovieInputData> Movies;
    private final List<SerialInputData> Shows;

    public Average(List<ActorInputData> Actors, List<UserInputData> Users,
                   List<MovieInputData> Movies, List<SerialInputData> Shows) {
        this.Actors = Actors;
        this.Users = Users;
        this.Movies = Movies;
        this.Shows = Shows;
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
    public void getAverage(ActionInputData action) throws IOException {
        HashMap<String, Double> rates = new HashMap<>();
        for (ActorInputData actor: Actors) {
            ArrayList<String> playedFilms = actor.getFilmography();
            double rating = 0; // sum of the ratings of an actor
            int counter = 0; // no_times a rating is not 0

            // get rating for the movies an actor played in
            for (MovieInputData movie: Movies) {
                if (playedFilms.contains(movie.getTitle())) {
                    rating += Helper.getRatingMovie(Users, movie);
                    if (Helper.getRatingMovie(Users, movie) != 0) {
                        counter++;
                    }
                }
            }
            // get rating for the shows an actor played in
            for (SerialInputData show: Shows) {
                if (playedFilms.contains(show.getTitle())) {
                    rating += Helper.getRatingShow(Users, show);
                    if (Helper.getRatingShow(Users, show) != 0) {
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
            ratesList.subList(action.getNumber(),ratesList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + ratesList);
    }
}
