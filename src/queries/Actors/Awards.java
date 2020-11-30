package queries.Actors;

import utilities.Helper;
import utilities.Sort;
import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides a method to get a list of the actors
 * with the highest or lowes number of awards.
 */
public class Awards {
    private final List<ActorInputData> actors;

    public Awards(final List<ActorInputData> actors) {
        this.actors = actors;
    }

    /**
     * Makes a list with the actors that have the highest or lowest
     * amount of awards
     * @param action requested action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void filterAwards(final ActionInputData action) throws IOException {
        // 3 will be the index where you can find the filterAwards
        List<String> filtersAwards = action.getFilters().get(3);
        HashMap<String, Integer> actorAwardsMap = new HashMap<>();
        for (ActorInputData actor : actors) {
            Map<ActorsAwards, Integer> actorAwards = actor.getAwards();
            int counter = 0;
            int totalAwards = 0;
            for (String award: filtersAwards) {
                if (actorAwards.get(ActorsAwards.valueOf(award)) != null) {
                    counter++;
                }
            }
            ArrayList<Integer> awards = new ArrayList<>(actorAwards.values());
            for (Integer i : awards) {
                totalAwards += i;
            }
            if (counter == filtersAwards.size()) {
                actorAwardsMap.put(actor.getName(), totalAwards);
            }
        }

        var sortedActorAwards = Sort.sortIntegerMap(actorAwardsMap, action);
        var actorAwardsList = new ArrayList<>(sortedActorAwards.keySet());
        Helper.writeToOutput(action, "Query result: " + actorAwardsList);
    }
}

