package Commands;

import fileio.ActionInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.List;

/**
 * Provides a method to execute any action that is a command
 */
public class Command {
    private final List<UserInputData> users;
    private final List<SerialInputData> shows;

    public Command(List<UserInputData> users, List<SerialInputData> shows) {
        this.users = users;
        this.shows = shows;
    }

    /**
     * Method which executes any command passed from the action parameter
     * @param action requested action
     * @throws IOException in case of in case of exceptions to reading / writing
     */
    public void execute(ActionInputData action) throws IOException {
        switch (action.getType()) {
            case "favorite" -> {
                Favorite favorite = new Favorite(users);
                favorite.addToFavorites(action);
            }
            case "view" -> {
                View view = new View(users);
                view.addToViewed(action);
            }
            case "rating" -> {
                int nrSeasons = numberOfSeasons(action.getTitle());
                Rating rating = new Rating(users);
                rating.addRating(action, nrSeasons);
            }
        }
    }

    /**
     * @param title of the show
     * @return number of seasons of that show
     */
    private int numberOfSeasons (String title) {
        int nrSeasons = 0;
        for (SerialInputData show : shows) {
            if (show.getTitle().equals(title)) {
                nrSeasons = show.getNumberSeason();
            }
        }
        return nrSeasons;
    }
}
