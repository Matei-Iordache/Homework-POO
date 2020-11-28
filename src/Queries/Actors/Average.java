package Queries.Actors;

import Commands.Helper;
import fileio.*;

import java.io.IOException;
import java.util.*;

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

    public void getAverage(ActionInputData action) throws IOException {
        HashMap<String, Double> rates = new HashMap<>();
        for (ActorInputData actor: Actors) {
            ArrayList<String> playedFilms = actor.getFilmography();
            double Rating = 0;
            int cock = 0;
            for (MovieInputData movie: Movies) {
                for (String film: playedFilms) {
                    if (film.equals(movie.getTitle())) {
                        Rating += Helper.getRatingMovie(Users, movie);
                        if (Helper.getRatingMovie(Users, movie) != 0) {
                            cock++;
                        }
                        break;
                    }
                }
            }
            for (SerialInputData show: Shows) {
                for (String film: playedFilms) {
                    if (film.equals(show.getTitle())) {
                        Rating += Helper.getRatingShow(Users, show);
                        if (Helper.getRatingShow(Users, show) != 0) {
                            cock++;
                        }
                        break;
                    }
                }
            }
            if (cock != 0) {
                //System.out.println(Rating);
                Rating = Rating / cock;
                rates.put(actor.getName(), Rating);
            }
        }
        rates.values().removeAll(Collections.singleton(0.0));
        Map<String, Double> rates2 = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            rates.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> rates2.put(x.getKey(), x.getValue()));
        } else {
            rates.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> rates2.put(x.getKey(), x.getValue()));
        }

        ArrayList<String> Actors = new ArrayList<>(rates2.keySet());
        if (action.getNumber() < Actors.size()) {
            Actors.subList(action.getNumber(),Actors.size()).clear();
        }
        System.out.println(Actors);
        Helper.writeToOutput(action, "Query result: " + Actors);
    }
}
