package Queries.Actors;

import Commands.Helper;
import actor.ActorsAwards;
import fileio.ActionInputData;
import fileio.ActorInputData;

import java.io.IOException;
import java.util.*;

public class Awards {
    private final List<ActorInputData> Actors;

    public Awards(List<ActorInputData> Actors) {
        this.Actors = Actors;
    }

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
            ArrayList<Integer> sex = new ArrayList<>(ActorAwards.values());
            for (Integer i : sex) {
                TotalAwards += i;
            }
            if (counter == filtersAwards.size()) {
                actorAwards.put(actor.getName(), TotalAwards);
            }
        }

        //actorAwards.values().removeAll(Collections.singleton(0));
        //System.out.println(actorAwards);
        Map<String, Integer> FilteredSorted = new LinkedHashMap<>();

        if (action.getSortType().equals("asc")) {
            actorAwards.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        } else {
            actorAwards.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> FilteredSorted.put(x.getKey(), x.getValue()));
        }
        //System.out.println(FilteredSorted);
        ArrayList<String> movies = new ArrayList<>(FilteredSorted.keySet());
        Helper.writeToOutput(action, "Query result: " + movies);
    }
//        ArrayList<String> actorsToQuery = new ArrayList<>();
//        for (ActorInputData actor : Actors) {
//            // the awards of the actor
//            Map<ActorsAwards, Integer> ActorAwards = actor.getAwards();
//            // Award filters
//            List<String> filtersAwards = action.getFilters().get(3);
//            int counter = 0;
//            int TotalAwards = 0;
//            // check if actor has every award in the filter
//            for (String award: filtersAwards) {
//                if (ActorAwards.get(ActorsAwards.valueOf(award)) != null) {
//                    counter++;
//                    TotalAwards += ActorAwards.get(ActorsAwards.valueOf(award));
//                }
//            }
//            if (counter == filtersAwards.size()) {
//                String concatenated = TotalAwards + actor.getName();
//                actorsToQuery.add(concatenated);
//            }
//        }
        //Helper.writeToOutput(action, "Query result: " + Actors);
}

