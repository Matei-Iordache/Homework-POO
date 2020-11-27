package Queries.Users;

import Commands.Helper;
import fileio.ActionInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.*;

public class NumberOfRatings {
    List<UserInputData> users;

    public NumberOfRatings(List<UserInputData> users) {
        this.users = users;
    }

    public void getMostActiveUsers(ActionInputData action) throws IOException {
        HashMap<String, Integer> usersWithRating = new HashMap<>();
        for (UserInputData user: users) {
            usersWithRating.put(user.getUsername(), user.getNumberOfRated());
        }

        usersWithRating.values().removeAll(Collections.singleton(0));
        Map<String, Integer> FilteredSorted = new LinkedHashMap<>();

        if (action.getSortType().equals("asc")) {
            usersWithRating.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        } else {
            usersWithRating.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()).thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        }
        ArrayList<String> users = new ArrayList<>(FilteredSorted.keySet());
        if (action.getNumber() < users.size()) {
            users.subList(action.getNumber(),users.size()).clear();
        }
        Helper.writeToOutput(action, "Query result: " + users);
    }
}
