package commands;

import fileio.ActionInputData;
import fileio.UserInputData;
import utilities.Helper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *  Provides the method to add a video
 *  that was seen to the history of a user
 */
public class View  {

    private final List<UserInputData> users;

    public View(final List<UserInputData> users) {
        this.users = users;
    }

    /**
     * Add a video to the history of the user after it was seen
     * If it was already seen, update no_views of the user history
     * @param action requested action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void addToViewed(final ActionInputData action) throws IOException {
        UserInputData user = Helper.findUser(users, action);
        assert user != null : "user is null";
        Map<String, Integer> history = user.getHistory(); // history of viewed movies

        // if movie is already in history, just increment the view count
        // if not add it to history with a view count of 1
        if (history.containsKey(action.getTitle())) {
            int views = history.get(action.getTitle());
            history.replace(action.getTitle(), ++views);
            addToOutput("IncrementViewCount", action, views);
        } else {
            history.put(action.getTitle(), 1);
            addToOutput("AddToHistory", action, 1);
        }
    }

    /**
     * Constructs the appropriate message and sends it to output.
     * @param cases tells the switch of what case to go
     * @param action requested action
     * @param views number of times the user has seen the video
     * @throws IOException in case of exceptions to reading / writing
     */
    private void addToOutput(final String cases, final ActionInputData action,
                             final int views) throws IOException {
        String message = switch (cases) {
            case "AddToHistory" -> "success -> " + action.getTitle()
                                    + " was viewed with total views of 1";
            case "IncrementViewCount" -> "success -> " + action.getTitle()
                                    + " was viewed with total views of " + views;
            default -> throw new IllegalStateException("Unexpected value: " + cases);
        };
        Helper.writeToOutput(action, message);
    }
}
