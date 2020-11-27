package Commands;

import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {
    private static Writer fileWriter;
    private static JSONArray arrayResult;

    public static void writeToOutput(ActionInputData action, String message) throws IOException {
        JSONObject obj = fileWriter.writeFile(action.getActionId(), null, message);
        arrayResult.add(obj);
    }

    public static void setFileWriter(Writer fileWriter) {
        Helper.fileWriter = fileWriter;
    }

    public static void setArrayResult(JSONArray arrayResult) {
        Helper.arrayResult = arrayResult;
    }

    /**
     * Add javadoc later
     * @param filteredMovies Hashmap of filtered movies that needs
     *                       to be sorted
     */
    public static void FavoriteQuerySort(Map<String, Integer> filteredMovies, ActionInputData action) {
        if (action.getSortType().equals("asc")) {
            filteredMovies.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEachOrdered(x -> filteredMovies.put(x.getKey(), x.getValue()));
            // sort the list descending value
        } else {
            filteredMovies.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> filteredMovies.put(x.getKey(), x.getValue()));
        }
    }

    public static UserInputData findUser(List<UserInputData> users, ActionInputData action) {
        for (UserInputData user : users) {
            if (user.getUsername().equals(action.getUsername())) {
                return user;
            }
        }
        // this should not ever return null
        return null;
    }

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
}
