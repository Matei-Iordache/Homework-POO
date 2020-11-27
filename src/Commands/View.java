package Commands;

import fileio.ActionInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class View {
    private final List<UserInputData> Users;

    public View(List<UserInputData> Users) {
        this.Users = Users;
    }

    public void addToViewed(final ActionInputData action) throws IOException {
        UserInputData user = Helper.findUser(Users, action);
        assert user != null: "user is null";
        Map<String, Integer> history = user.getHistory(); // history of viewed movies
        // if movie is already in history, just increment the view count
        // if not add it to history with a view count of 1
        if (history.get(action.getTitle()) != null) {
            int views = history.get(action.getTitle());
            history.replace(action.getTitle(), ++views);
            addToOutput("IncrementViewCount", action, views);
        } else {
            history.put(action.getTitle(), 1);
            addToOutput("AddToHistory", action, 1);
        }
    }

    private void addToOutput(final String cases, final ActionInputData action,
                             int views) throws IOException {
        String message = switch (cases) {
            case "AddToHistory" -> "success -> " + action.getTitle() + " was viewed with total views of 1";
            case "IncrementViewCount" -> "success -> " + action.getTitle() + " was viewed with total views of " + views;
            default -> throw new IllegalStateException("Unexpected value: " + cases);
        };
        Helper.writeToOutput(action, message);
    }
}