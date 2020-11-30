package recommendation;

import utilities.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Provides the standard recommendation method
 */
public class StandardRec {
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> shows;

    public StandardRec(final List<UserInputData> users, final List<MovieInputData> movies,
                       final List<SerialInputData> shows) {
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    /**
     * Method which returns the first unseen video
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getStandardRec(final ActionInputData action) throws IOException {
        UserInputData user = Helper.findUser(users, action);
        assert user != null;
        Map<String, Integer> history = user.getHistory();

        for (MovieInputData movie : movies) {
            if (!history.containsKey(movie.getTitle())) {
                Helper.writeToOutput(action, "StandardRecommendation result: " + movie.getTitle());
                return;
            }
        }

        for (SerialInputData show : shows) {
            if (!history.containsKey(show.getTitle())) {
                Helper.writeToOutput(action, "StandardRecommendation result: " + show.getTitle());
                return;
            }
        }
        Helper.writeToOutput(action, "StandardRecommendation cannot be applied!");
    }
}
