package Utilities;


import fileio.ActionInputData;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides different sorting methods
 * used throughout the program
 */
public class Sort {

    /**
     * Method will sort a map ascendent or descendent based on the action
     * The hashmap returned will be of type <String, Double>
     * @param unsorted the unsorted hashmap
     * @param action requested action
     * @return sorted hashmap
     */
    public static LinkedHashMap<String, Integer> sortIntegerMap(HashMap<String, Integer> unsorted, ActionInputData action) {
        LinkedHashMap<String, Integer> sorted = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            unsorted.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue()
                    .thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        } else {
            unsorted.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        }
        return sorted;
    }

    /**
     * Method will sort a map ascendent or descendent based on the action
     * The hashmap returned will be of type <String, Integer>
     * @param unsorted the unsorted hashmap
     * @param action requested action
     * @return sorted hashmap
     */
    public static LinkedHashMap<String, Double> sortDoubleMap(HashMap<String, Double> unsorted, ActionInputData action) {
        LinkedHashMap<String, Double> sorted = new LinkedHashMap<>();
        if (action.getSortType().equals("asc")) {
            unsorted.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue()
                    .thenComparing(Map.Entry.comparingByKey()))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        } else {
            unsorted.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder())
                    .thenComparing(Map.Entry.comparingByKey(Comparator.reverseOrder())))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        }
        return sorted;
    }
}
