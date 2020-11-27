package Commands;

import fileio.ActionInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Favorite {
    private final List<UserInputData> Users;
    public Favorite(List<UserInputData> Users) {
        this.Users = Users;
    }

    /**
     * Adds a movie to the favorite list of a user, if the movie has been seen
     * and is not already in the favorites list
     * @param action type of action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void addToFavorites(final ActionInputData action)
                               throws IOException {
        UserInputData user = Helper.findUser(Users, action);
        assert user != null: "user is null";
        ArrayList<String> favorites = user.getFavoriteMovies(); // list of favorite movies
        Map<String, Integer> history = user.getHistory(); // history of viewed movies
        // check if the movie is seen
        if (history.get(action.getTitle()) != null) {
            for (String movie: favorites) {
                if (action.getTitle().equals(movie)) {
                    addToOutput("Already in favorites", action);
                    return;
                }
            }
            favorites.add(action.getTitle());
            addToOutput("Add to favorites", action);
        } else {
            addToOutput("Not seen", action);
        }
    }
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
