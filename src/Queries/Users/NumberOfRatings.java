package Queries.Users;

import Commands.Helper;
import Utilities.Sort;
import fileio.ActionInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides a method to obtain the most active users.
 * User activity consists of rating shows.
 */
public class NumberOfRatings {
    List<UserInputData> users;

    public NumberOfRatings(List<UserInputData> users) {
        this.users = users;
    }

    /**
     * Gives to output a list of the most active users,
     * sorted in ascending or descending order.
     * @param action requested action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void getMostActiveUsers(ActionInputData action) throws IOException {
        HashMap<String, Integer> usersWithRating = new HashMap<>();
        for (UserInputData user: users) {
            usersWithRating.put(user.getUsername(), user.getNumberOfRated());
        }

        usersWithRating.values().removeAll(Collections.singleton(0));
        LinkedHashMap<String, Integer> sortedUsers = Sort.sortIntegerMap(usersWithRating, action);

        ArrayList<String> users = new ArrayList<>(sortedUsers.keySet());
        if (action.getNumber() < users.size()) {
            users.subList(action.getNumber(),users.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + users);
    }
}
