package Commands;

import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

public class Helper {
    private static Writer fileWriter;
    private static JSONArray arrayResult;

    public static void writeToOutput(ActionInputData action, String message) throws IOException {
        JSONObject obj = fileWriter.writeFile(action.getActionId(), null, message);
        arrayResult.add(obj);
    }

    /**
     * Method looks in the user database for a specific user
     * @param users list of user in the database
     * @param action requested action
     * @return the user that requested the action
     */
    public static UserInputData findUser(List<UserInputData> users, ActionInputData action) {
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
     * @param Users list of user in the database
     * @param movie that needs to get the average rating
     * @return average rating
     */
    public static double getRatingMovie(List<UserInputData> Users, MovieInputData movie) {
        double TotalRating = 0;
        double nrRating = 0;
        for (UserInputData user: Users) {
            HashMap<String, Double> ratings = user.getRated();
            if (ratings.containsKey(movie.getTitle())) {
                TotalRating += ratings.get(movie.getTitle());
                nrRating++;
            }
        }
        if (nrRating == 0) {
            return 0;
        }
        return TotalRating/nrRating;
    }

    /**
     * Method calculates the average rating of a show
     * based on the ratings of all users in the database
     * @param Users list of user in the database
     * @param show that needs to get the average rating
     * @return average rating
     */
    public static double getRatingShow(List<UserInputData> Users, SerialInputData show) {
        double TotalRating = 0;
        double nrRating = 0;
        for (UserInputData user: Users) {
            HashMap<String, Double> ratings = user.getRated();
            if (ratings.containsKey(show.getTitle())) {
                TotalRating += ratings.get(show.getTitle());
                nrRating++;
            }
        }
        if (nrRating == 0) {
            return 0;
        }
        return TotalRating/nrRating;
    }

    public static void setFileWriter(Writer fileWriter) {
        Helper.fileWriter = fileWriter;
    }

    public static void setArrayResult(JSONArray arrayResult) {
        Helper.arrayResult = arrayResult;
    }
}
