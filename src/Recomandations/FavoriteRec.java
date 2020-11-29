package Recomandations;

import Commands.Helper;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides the favorite recommendation method
 */
public class FavoriteRec {
    List<UserInputData> users;
    List<MovieInputData> movies;
    List<SerialInputData> shows;

    public FavoriteRec(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> shows) {
        this.users = users;
        this.movies = movies;
        this.shows = shows;
    }

    /**
     * Recommends the video that was the most added in the
     * favorite list of the uses
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void gerFavoriteRec(ActionInputData action) throws IOException {
        UserInputData user1 = Helper.findUser(users, action);
        assert user1 != null;
        if (user1.getSubscriptionType().equals("BASIC")) {
            Helper.writeToOutput(action,"FavoriteRecommendation cannot be applied!");
            return;
        }
        HashMap<String, Integer> favoriteVideos = new HashMap<>();

        for (UserInputData user : users) {
            ArrayList<String> favorites = user.getFavoriteMovies();
            for (String favorite : favorites) {
                    favoriteVideos.merge(favorite, 1, Integer::sum);
            }
        }
        HashMap<String, Integer> favoriteVideosSorted = new LinkedHashMap<>();
        favoriteVideos.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> favoriteVideosSorted.put(x.getKey(), x.getValue()));
        ArrayList<String> favoriteVideosSortedList = new ArrayList<>(favoriteVideosSorted.keySet());

        Map<String, Integer> history = user1.getHistory();
        for (String favorite: favoriteVideosSortedList) {
            if (!history.containsKey(favorite)) {
                Helper.writeToOutput(action, "FavoriteRecommendation result: " + favorite);
                return;
            }
        }
        Helper.writeToOutput(action,"FavoriteRecommendation cannot be applied!");
    }
}
