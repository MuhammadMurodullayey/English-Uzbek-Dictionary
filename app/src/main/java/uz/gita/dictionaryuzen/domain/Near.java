package uz.gita.dictionaryuzen.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Near {
    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    private static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];

        for (int i = 0; i <= lhs.length(); i++)
            distance[i][0] = i;
        for (int j = 1; j <= rhs.length(); j++)
            distance[0][j] = j;


        for (int i = 1; i <= lhs.length(); i++)
            for (int j = 1; j <= rhs.length(); j++)
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));

        return distance[lhs.length()][rhs.length()];
    }

    public static ArrayList<String> getMostNearest(String word, List<String> words) {
        ArrayList<String> result = new ArrayList<>();
        int i = 0;

        HashMap<String, Integer> map = new HashMap<>();
        for (String w : words) {
            map.put(w, computeLevenshteinDistance(word, w));
        }

        TreeMap<String, Integer> sortedMap = new TreeMap<>(new ValueComparator(map));
        sortedMap.putAll(map);

        Iterator<Map.Entry<String, Integer>> iterator = sortedMap.entrySet().iterator();
        Map.Entry<String, Integer> entry;
        while (iterator.hasNext() && i < 5) {
            entry = iterator.next();
            result.add(entry.getKey());
            i++;
        }

        return result;
    }


    static class ValueComparator implements Comparator<String> {
        Map<String, Integer> map;

        ValueComparator(Map<String, Integer> map) {
            this.map = map;
        }

        public int compare(String a, String b) {
            if (map.get(a) < map.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
