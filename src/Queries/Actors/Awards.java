package Queries.Actors;

import Commands.Helper;
import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Awards {
    private final List<ActorInputData> Actors;

    public Awards(List<ActorInputData> Actors) {
        this.Actors = Actors;
    }

    public void FilterAwards(ActionInputData action) throws IOException {
        ArrayList<String> actorsToQuery = new ArrayList<>();
        for (ActorInputData actor : Actors) {
            // the awards of the actor
            Map<ActorsAwards, Integer> ActorAwards = actor.getAwards();
            // Award filters
            List<String> filtersAwards = action.getFilters().get(3);
            int counter = 0;
            int TotalAwards = 0;
            // check if actor has every award in the filter
            for (String award: filtersAwards) {
                if (ActorAwards.get(ActorsAwards.valueOf(award)) != null) {
                    counter++;
                    TotalAwards += ActorAwards.get(ActorsAwards.valueOf(award));
                }
            }
            if (counter == filtersAwards.size()) {
                String concatenated = TotalAwards + actor.getName();
                actorsToQuery.add(concatenated);
            }
        }
        // sort the list and remove the TotalAwards that was concatenated
        if (action.getSortType().equals("asc")) {
            Collections.sort(actorsToQuery);
        } else {
            actorsToQuery.sort(Collections.reverseOrder());
        }

        ArrayList<String> SortedActorsToQuery = new ArrayList<>();
        for (String actor: actorsToQuery) {
            SortedActorsToQuery.add(actor.substring(1));
        }
        Helper.writeToOutput(action, "Query result: " + SortedActorsToQuery);
    }
}
