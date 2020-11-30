package commands;

import fileio.ActionInputData;
import fileio.UserInputData;
import utilities.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides a method which lets a user add a video
 * to his favorite list.
 */
public class Favorite {
    private final List<UserInputData> users;
    public Favorite(final List<UserInputData> users) {
        this.users = users;
    }

    /**
     * Adds a movie to the favorite list of a user, if the movie has been seen
     * and is not already in the favorites list
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void addToFavorites(final ActionInputData action)
                               throws IOException {
        UserInputData user = Helper.findUser(users, action);
        assert user != null : "user is null";
        ArrayList<String> favorites = user.getFavoriteMovies();
        Map<String, Integer> history = user.getHistory();

        // check if the movie is seen
        if (history.containsKey(action.getTitle())) {
            if (favorites.contains(action.getTitle())) {
                addToOutput("Already in favorites", action);
                return;
            }
            favorites.add(action.getTitle());
            addToOutput("Add to favorites", action);
        } else {
            addToOutput("Not seen", action);
        }
    }

    /**
     * Constructs the appropriate message and sends it to output.
     * @param cases tells the switch of what case to go
     * @param action requested action
     * @throws IOException in case of exceptions to reading / writing
     */
    private void addToOutput(final String cases, final ActionInputData action)
                            throws IOException {
        String message = switch (cases) {
            case "Already in favorites"
                    -> "error -> " + action.getTitle() + " is already in favourite list";
            case "Add to favorites"
                    -> "success -> " + action.getTitle() + " was added as favourite";
            case "Not seen"
                    -> "error -> " + action.getTitle() + " is not seen";
            default -> throw new IllegalStateException("Unexpected value: " + cases);
        };
        Helper.writeToOutput(action, message);
    }
}
