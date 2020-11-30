package utilities;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public final class Helper {
    private Helper() {

    }
    private static Writer fileWriter;
    private static JSONArray arrayResult;

    /**
     *
     * @param action requested action
     * @param message to be written to output
     * @throws IOException in case of exceptions to reading / writing
     */
    @SuppressWarnings("unchecked")
    public static void writeToOutput(final ActionInputData action,
                                     final String message) throws IOException {
        JSONObject obj = fileWriter.writeFile(action.getActionId(), null, message);
        arrayResult.add(obj);
    }

    /**
     * Method looks in the user database for a specific user
     * @param users list of user in the database
     * @param action requested action
     * @return the user that requested the action
     */
    public static UserInputData findUser(final List<UserInputData> users,
                                         final ActionInputData action) {
        for (UserInputData user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                return user;
            }
        }
        // this should not ever return null
        return null;
    }

    /**
     * Method calculates the average rating of a movie
     * based on the ratings of all users in the database
     * @param movie that needs to get the average rating
     * @return average rating
     */
    public static double getRatingMovie(final List<UserInputData> users,
                                        final MovieInputData movie) {
        double totalRating = 0;
        double nrRating = 0;
        for (UserInputData user: users) {
            HashMap<String, Double> ratings = user.getRated();
            if (ratings.containsKey(movie.getTitle())) {
                totalRating += ratings.get(movie.getTitle());
                nrRating++;
            }
        }
        if (nrRating == 0) {
            return 0;
        }
        return totalRating / nrRating;
    }

    /**
     * Method calculates the average rating of a show
     * based on the ratings of all users in the database
     * @param show that needs to get the average rating
     * @return average rating
     */
    public static double getRatingShow(final List<UserInputData> users,
                                       final SerialInputData show) {
        double totalRating = 0;
        double nrRating = 0;
        for (UserInputData user: users) {
            HashMap<String, Double> ratings = user.getRated();
            if (ratings.containsKey(show.getTitle())) {
                totalRating += ratings.get(show.getTitle());
                nrRating++;
            }
        }
        if (nrRating == 0) {
            return 0;
        }
        return totalRating / nrRating;
    }

    public static void setFileWriter(final Writer fileWriter) {
        Helper.fileWriter = fileWriter;
    }

    public static void setArrayResult(final JSONArray arrayResult) {
        Helper.arrayResult = arrayResult;
    }
}
