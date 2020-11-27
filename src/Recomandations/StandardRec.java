package Recomandations;

import Commands.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StandardRec {
    List<UserInputData> users;
    List<MovieInputData> movies;
    List<SerialInputData> shows;

    public StandardRec(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> shows) {
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    public void getStandardRec(ActionInputData action) throws IOException {
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
    }
}
