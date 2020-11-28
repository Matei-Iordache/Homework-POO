package Queries.Actors;

import Commands.Helper;
import fileio.ActionInputData;
import fileio.ActorInputData;

import java.io.IOException;
import java.util.*;

public class FilterDescription {
    List<ActorInputData> actors;

    public FilterDescription(List<ActorInputData> actors) {
        this.actors = actors;
    }

    public void getFilterQuery(ActionInputData action) throws IOException {
        List<String> filters = action.getFilters().get(2);
        ArrayList<String> actorsWithFilters = new ArrayList<>();
        for(ActorInputData actor : actors) {
            String carrerDescription = actor.getCareerDescription().toLowerCase();
            String[] split = carrerDescription.split("[ -.,]");
            List<String> carrerDesc = Arrays.asList(split);
            int counter = 0;
            for (String filter : filters) {
                if (carrerDesc.contains(filter)) {
                    counter++;
                }
            }
            if (counter == filters.size()) {
                actorsWithFilters.add(actor.getName());
            }
        }
        switch (action.getSortType()) {
            case "asc" -> Collections.sort(actorsWithFilters);
            case "desc" -> actorsWithFilters.sort(Comparator.reverseOrder());
            default -> throw new IllegalStateException("Unexpected value: " + action.getSortType());
        }
        Helper.writeToOutput(action, "Query result: " + actorsWithFilters);
    }
}
