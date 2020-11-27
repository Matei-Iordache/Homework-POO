package Queries.Video;

import Commands.Helper;
import Utilities.Filters;
import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class FavoriteShowQuery {
    private final List<UserInputData> Users;
    private final List<SerialInputData> Shows;

    public FavoriteShowQuery(List<UserInputData> Users, List<SerialInputData> Shows) {
        this.Users = Users;
        this.Shows = Shows;
    }

    public void FilterFavorite(ActionInputData action) throws IOException {
        // movies that match the filters
        HashMap<String, Integer> filteredMovies = Filters.filteredShows(Shows,action);
        // get how many users have the movie on favorites
        for (UserInputData user : Users) {
            ArrayList<String> favorites = user.getFavoriteMovies();
            for (String favorite : favorites) {
                if (filteredMovies.get(favorite) != null) {
                    int value = filteredMovies.get(favorite);
                    filteredMovies.replace(favorite, ++value);
                }
            }
        }
        Helper.FavoriteQuerySort(filteredMovies, action);
        filteredMovies.values().removeAll(Collections.singleton(0));
        Helper.writeToOutput(action,"Query result: " + filteredMovies.keySet());
    }
}
