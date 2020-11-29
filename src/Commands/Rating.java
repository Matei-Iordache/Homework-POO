package Commands;

import fileio.ActionInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a method which allows a user to rate a video
 */
public class Rating {

    List<UserInputData> Users;

    public Rating(List<UserInputData> Users) {
        this.Users = Users;
    }

    /**
     * Rate a video.
     * The video can't be rated if it was not seen or already rated
     * The user can rate multiple seasons of a show.
     * @param action requested action
     * @param seasons the seasons of a show
     *                If it is a movie, seasons will be 0
     * @throws IOException in case of exceptions to reading / writing
     */
    public void addRating(final ActionInputData action, int seasons) throws IOException {
        UserInputData user = Helper.findUser(Users, action);
        assert user != null;
        Map<String, Integer> history = user.getHistory();

        // ratings a user gave to a show/movie
        HashMap<String, Double> rated = user.getRated();

        // check if a movie/show season was rated or not
        HashMap<String, Integer> check = user.getCheck();

        if (history.containsKey(action.getTitle())) {
            if (check.containsKey(action.getTitle()+action.getSeasonNumber())) {
                addToOutput("AlreadyRated", action);
                return;
            }
            addToOutput("RateMovie", action);
            user.setNumberOfRated(user.getNumberOfRated() + 1);
            if (action.getSeasonNumber() == 0) { // if it is a movie
                rated.put(action.getTitle(),action.getGrade());
                check.put(action.getTitle() + 0, 1);
            } else { // if it is a show
                if (rated.containsKey(action.getTitle())) { // if the show was rated before
                    Double value = rated.get(action.getTitle());
                    rated.replace(action.getTitle(), value + action.getGrade()/seasons);
                } else { // if the show was never rated
                    rated.put(action.getTitle(), action.getGrade()/seasons);
                    check.put(action.getTitle()+action.getSeasonNumber(), 1);
                }
            }
        } else {
            addToOutput("not seen", action);
        }
    }

    /**
     * Constructs the appropriate message and sends
     * it to output.
     * @param cases tells the switch of what case to go
     * @param action requested action
     * @throws IOException in case of exceptions to reading / writing
     */
    private void addToOutput(final String cases, final ActionInputData action)
                            throws IOException {

        String message = switch (cases) {
            case "RateMovie" -> "success -> " + action.getTitle() + " was rated with " +
                  action.getGrade() + " by " + action.getUsername();
            case "AlreadyRated" -> "error -> " + action.getTitle() + " has been already rated";
            case "not seen" -> "error -> " + action.getTitle() + " is not seen";
            default -> throw new IllegalStateException("Unexpected value: " + cases);
        };
        Helper.writeToOutput(action, message);
    }
}