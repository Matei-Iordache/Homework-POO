package main;

import Commands.Command;
import Commands.Helper;
import Queries.Actors.Average;
import Queries.Actors.Awards;
import Queries.Actors.FilterDescription;
import Queries.Users.NumberOfRatings;
import Queries.Video.*;
import Recomandations.*;
import checker.Checker;
import checker.Checkstyle;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        List<ActionInputData> Actions = input.getCommands();
        List<UserInputData> Users = input.getUsers();
        List<ActorInputData> Actors = input.getActors();
        List<MovieInputData> Movies = input.getMovies();
        List<SerialInputData> Shows = input.getSerials();
        Helper.setFileWriter(fileWriter);
        Helper.setArrayResult(arrayResult);
        for (ActionInputData action : Actions) {
            if (action.getActionType().equals("command")) {
                Command command = new Command(Users, Shows);
                command.execute(action);
            }
            if (action.getActionType().equals("query")) {
                if (action.getObjectType().equals("actors")) {
                    if (action.getCriteria().equals("awards")) {
                        Awards awards = new Awards(Actors);
                        awards.FilterAwards(action);
                    }
                    if (action.getCriteria().equals("average")) {
                        Average average = new Average(Actors,Users,Movies,Shows);
                        average.getAverage(action);
                    }
                    if (action.getCriteria().equals("filter_description")) {
                        FilterDescription filterDescription = new FilterDescription(Actors);
                        filterDescription.getFilterQuery(action);
                    }
                }
                if (action.getObjectType().equals("movies")) {
                    if (action.getCriteria().equals("favorite")) {
                        FavoriteMovieQuery favoriteMovieQuery = new FavoriteMovieQuery(Users, Movies);
                        favoriteMovieQuery.FilterFavorite(action);
                    }
                    if (action.getCriteria().equals("ratings")) {
                        RatingMovieQuery ratingMovieQuery = new RatingMovieQuery(Movies, Users);
                        ratingMovieQuery.getRatingMovieQuery(action);
                    }
                    if (action.getCriteria().equals("longest")) {
                        LongestMovieQuery longestMovieQuery = new LongestMovieQuery(Movies);
                        longestMovieQuery.getLongestMovieQuery(action);
                    }
                    if (action.getCriteria().equals("most_viewed")) {
                        MostViewedMovieQuery mostViewedMovieQuery = new MostViewedMovieQuery(Movies, Users);
                        mostViewedMovieQuery.getMostViewedMovie(action);
                    }
                }
                if (action.getObjectType().equals("shows")) {
                    if (action.getCriteria().equals("favorite")) {
                        FavoriteShowQuery favoriteShowQuery = new FavoriteShowQuery(Users, Shows);
                        favoriteShowQuery.FilterFavorite(action);
                    }
                    if (action.getCriteria().equals("ratings")) {
                        RatingShowQuery ratingMovieQuery = new RatingShowQuery(Shows, Users);
                        ratingMovieQuery.getRatingShowQuery(action);
                    }
                    if (action.getCriteria().equals("longest")) {
                        LongestShowQuery longestShowQuery = new LongestShowQuery(Shows);
                        longestShowQuery.getLongestShowQuery(action);
                    }
                    if (action.getCriteria().equals("most_viewed")) {
                        MostViewedShowQuery mostViewedShowQuery = new MostViewedShowQuery(Shows, Users);
                        mostViewedShowQuery.getMostViewedShowQuery(action);
                    }
                }
                if(action.getObjectType().equals("users")) {
                    if (action.getCriteria().equals("num_ratings")) {
                        NumberOfRatings numberOfRatings = new NumberOfRatings(Users);
                        numberOfRatings.getMostActiveUsers(action);
                    }
                }
            }
            if (action.getActionType().equals("recommendation")) {
                if (action.getType().equals("standard")) {
                    StandardRec standardRec = new StandardRec(Users,Movies,Shows);
                    standardRec.getStandardRec(action);
                }
                if (action.getType().equals("best_unseen")) {
                    BestUnseenRec bestUnseenRec = new BestUnseenRec(Users, Movies, Shows);
                    bestUnseenRec.getBestUnseenRec(action);
                }
                if (action.getType().equals("search")) {
                    SearchRec searchRec = new SearchRec(Users, Movies, Shows);
                    searchRec.getSearchRec(action);
                }
                if (action.getType().equals("popular")) {
                    PopularRec popularRec = new PopularRec(Users,Movies,Shows);
                    popularRec.getPopular(action);
                }
                if (action.getType().equals("favorite")) {
                    FavoriteRec favoriteRec = new FavoriteRec(Users,Movies,Shows);
                    favoriteRec.gerFavoriteRec(action);
                }
            }
        }
        fileWriter.closeJSON(arrayResult);
    }
}
