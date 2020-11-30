package queries.Users;

import utilities.Helper;
import utilities.Sort;
import fileio.ActionInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Provides a method to obtain the most active users.
 * User activity consists of rating shows.
 */
public class NumberOfRatings {
    private final List<UserInputData> users;

    public NumberOfRatings(final List<UserInputData> users) {
        this.users = users;
    }

    /**
     * Gives to output a list of the most active users,
     * sorted in ascending or descending order.
     * @param action requested action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getMostActiveUsers(final ActionInputData action) throws IOException {
        HashMap<String, Integer> usersWithRating = new HashMap<>();
        for (UserInputData user: users) {
            usersWithRating.put(user.getUsername(), user.getNumberOfRated());
        }

        usersWithRating.values().removeAll(Collections.singleton(0));
        LinkedHashMap<String, Integer> sortedUsers = Sort.sortIntegerMap(usersWithRating, action);

        ArrayList<String> usersList = new ArrayList<>(sortedUsers.keySet());
        if (action.getNumber() < usersList.size()) {
            usersList.subList(action.getNumber(), usersList.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + usersList);
    }
}
