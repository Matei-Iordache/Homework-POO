package recommendation;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.List;

/**
 * Provides a method to execute any action that is a recommendation
 */
public class Recommendation {
    private final List<UserInputData> users;
    private final List<SerialInputData> shows;
    private final List<MovieInputData> movies;

    public Recommendation(final List<UserInputData> users, final List<SerialInputData> shows,
                          final List<MovieInputData> movies) {
        this.users = users;
        this.shows = shows;
        this.movies = movies;
    }

    /**
     * Method which executes any recommendation passed from the action parameter
     * @param action requested action
     * @throws IOException in case of in case of exceptions to reading / writing
     */
    public void execute(final ActionInputData action) throws IOException {
        switch (action.getType()) {
            case "standard" -> {
                var standardRec = new StandardRec(users, movies, shows);
                standardRec.getStandardRec(action);
            }
            case "best_unseen" -> {
                var bestUnseenRec = new BestUnseenRec(users, movies, shows);
                bestUnseenRec.getBestUnseenRec(action);
            }
            case "search" -> {
                var searchRec = new SearchRec(users, movies, shows);
                searchRec.getSearchRec(action);
            }
            case "popular" -> {
                var popularRec = new PopularRec(users, movies, shows);
                popularRec.getPopular(action);
            }
            case "favorite" -> {
                var favoriteRec = new FavoriteRec(users);
                favoriteRec.gerFavoriteRec(action);
            }
            default -> System.out.println("Invalid Type");
        }
    }
}
