package Queries.Actors;

import Commands.Helper;
import Utilities.Sort;
import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;

import java.io.IOException;
import java.util.*;

/**
 * Provides a method to get a list of the actors
 * with the highest or lowes number of awards.
 */
public class Awards {
    private final List<ActorInputData> Actors;

    public Awards(List<ActorInputData> Actors) {
        this.Actors = Actors;
    }

    /**
     * Makes a list with the actors that have the highest or lowest
     * amount of awards
     * @param action requested action
     * @throws IOException in case of exceptions to reading / writing
     */
    public void FilterAwards(ActionInputData action) throws IOException {
        List<String> filtersAwards = action.getFilters().get(3);
        HashMap<String, Integer> actorAwards = new HashMap<>();
        for (ActorInputData actor : Actors) {
            Map<ActorsAwards, Integer> ActorAwards = actor.getAwards();
            int counter = 0;
            int TotalAwards = 0;
            for (String award: filtersAwards) {
                if (ActorAwards.get(ActorsAwards.valueOf(award)) != null) {
                    counter++;
                }
            }
            ArrayList<Integer> awards = new ArrayList<>(ActorAwards.values());
            for (Integer i : awards) {
                TotalAwards += i;
            }
            if (counter == filtersAwards.size()) {
                actorAwards.put(actor.getName(), TotalAwards);
            }
        }

        var sortedActorAwards = Sort.sortIntegerMap(actorAwards, action);
        var actorAwardsList = new ArrayList<>(sortedActorAwards.keySet());
        Helper.writeToOutput(action, "Query result: " + actorAwardsList);
    }
}

