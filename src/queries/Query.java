package queries;

import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import queries.Actors.Average;
import queries.Actors.Awards;
import queries.Actors.FilterDescription;
import queries.Users.NumberOfRatings;
import queries.Video.FavoriteMovieQuery;
import queries.Video.FavoriteShowQuery;
import queries.Video.LongestMovieQuery;
import queries.Video.LongestShowQuery;
import queries.Video.MostViewedMovieQuery;
import queries.Video.MostViewedShowQuery;
import queries.Video.RatingMovieQuery;
import queries.Video.RatingShowQuery;

import java.io.IOException;
import java.util.List;

/**
 * Provides a method to execute any action that is a query
 */
public class Query {
    private final List<UserInputData> users;
    private final List<SerialInputData> shows;
    private final List<MovieInputData> movies;
    private final List<ActorInputData> actors;

    public Query(final List<UserInputData> users, final List<SerialInputData> shows,
                 final List<MovieInputData> movies, final List<ActorInputData> actors) {
        this.users = users;
        this.shows = shows;
        this.movies = movies;
        this.actors = actors;
    }

    /**
     * Method which executes any query passed from the action parameter
     * @param action requested action
     * @throws IOException in case of in case of exceptions to reading / writing
     */
    public void execute(final ActionInputData action) throws IOException {
        if (action.getObjectType().equals("actors")) {
            switch (action.getCriteria()) {
                case "awards" -> {
                    Awards awards = new Awards(actors);
                    awards.filterAwards(action);
                }
                case "average" -> {
                    Average average = new Average(actors, users, movies, shows);
                    average.getAverage(action);
                }
                case "filter_description" -> {
                    var filterDescription = new FilterDescription(actors);
                    filterDescription.getFilterQuery(action);
                }
                default -> System.out.println("Invalid Criteria");
            }
        }
        if (action.getObjectType().equals("movies")) {
            switch (action.getCriteria()) {
                case "favorite" -> {
                    var favoriteMovieQuery = new FavoriteMovieQuery(users, movies);
                    favoriteMovieQuery.filterFavorite(action);
                }
                case "ratings" -> {
                    var ratingMovieQuery = new RatingMovieQuery(movies, users);
                    ratingMovieQuery.getRatingMovieQuery(action);
                }
                case "longest" -> {
                    var longestMovieQuery = new LongestMovieQuery(movies);
                    longestMovieQuery.getLongestMovieQuery(action);
                }
                case "most_viewed" -> {
                    var mostViewedMovieQuery = new MostViewedMovieQuery(movies, users);
                    mostViewedMovieQuery.getMostViewedMovie(action);
                }
                default -> System.out.println("Invalid Criteria");
            }
        }
        if (action.getObjectType().equals("shows")) {
            switch (action.getCriteria()) {
                case "favorite" -> {
                    var favoriteShowQuery = new FavoriteShowQuery(users, shows);
                    favoriteShowQuery.filterFavorite(action);
                }
                case "ratings" -> {
                    var ratingShowQueryQuery = new RatingShowQuery(shows, users);
                    ratingShowQueryQuery.getRatingShowQuery(action);
                }
                case "longest" -> {
                    var longestShowQuery = new LongestShowQuery(shows);
                    longestShowQuery.getLongestShowQuery(action);
                }
                case "most_viewed" -> {
                    var mostViewedShowQuery = new MostViewedShowQuery(shows, users);
                    mostViewedShowQuery.getMostViewedShowQuery(action);
                }
                default -> System.out.println("Invalid Criteria");
            }
        }
        if (action.getObjectType().equals("users")) {
                var numberOfRatings = new NumberOfRatings(users);
                numberOfRatings.getMostActiveUsers(action);
        }
    }
}
