package Commands;

import fileio.ActionInputData;
import fileio.UserInputData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rating {

    List<UserInputData> Users;

    public Rating(List<UserInputData> Users) {
        this.Users = Users;
    }

    public void addRating(final ActionInputData action, int seasons) throws IOException {
        UserInputData user = Helper.findUser(Users, action);
        // map with the ratings a user gave to a show/movie
        assert user != null : "user is null";
        HashMap<String, Double> rated = user.getRated();
        // map to check if a movie/show season was rated or not
        HashMap<String, Integer> check = user.getCheck();
        // history of the user
        Map<String, Integer> history = user.getHistory();
        if (history.get(action.getTitle()) != null) {
            if (check.containsKey(action.getTitle()+action.getSeasonNumber())) {
                addToOutput("AlreadyRated", action);
                return;
            }
            addToOutput("RateMovie", action);
            int x = user.getNumberOfRated();
            user.setNumberOfRated(x + 1);
            if (action.getSeasonNumber() == 0) { // if it is a movie
                rated.put(action.getTitle(),action.getGrade());
                check.put(action.getTitle() + 0, 1);
            } else { // if it is a show
                if (rated.containsKey(action.getTitle())) { // if the show was rated before
                    Double value = rated.get(action.getTitle()); // previous value of a show over all seasons
                    rated.replace(action.getTitle(), value + action.getGrade()/seasons); // new value
                } else { // if the show was never rated
                    rated.put(action.getTitle(), action.getGrade()/seasons);
                    check.put(action.getTitle()+action.getSeasonNumber(), 1);
                }
            }
        } else {
            addToOutput("not seen", action);
        }
    }
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