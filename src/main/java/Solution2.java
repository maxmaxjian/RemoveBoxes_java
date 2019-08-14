import javafx.util.Pair;

import java.util.*;

public class Solution2 {
    public int removeBoxes(int[] nums) {
        int result = 0;
        List<Pair> pairs = convertToPairs(nums);
        List<Integer> cands = new ArrayList<>();

        DFS(pairs, result, cands);
        System.out.println("All cands are: ");
        cands.forEach(i -> System.out.print(i + " "));
        System.out.println();

        return Collections.max(cands);
    }

    private void DFS(List<Pair> pairs, int result, List<Integer> cands) {
        System.out.println(pairs);
        if (pairs.isEmpty()) {
            cands.add(result);
            System.out.println("result = " + result);
        } else {
            Map<Integer,Pair<Integer,Integer>> freq = new HashMap<>();
            pairs.forEach(p -> {
                if (!freq.containsKey(p.getKey())) {
                    freq.put((int)p.getKey(), new Pair(1, p.getValue()));
                } else {
                    Pair<Integer,Integer> found = freq.get(p.getKey());
                    freq.put((int)p.getKey(), new Pair(found.getKey()+1, found.getValue()+(int)p.getValue()));
                }
            });

            List<Map.Entry<Integer,Pair<Integer,Integer>>> list = new ArrayList<>(freq.entrySet());
            Collections.sort(list, (o1, o2) -> {
                if (!o1.getValue().getKey().equals(o2.getValue().getKey())) {
                    return o1.getValue().getKey()-o2.getValue().getKey();
                } else {
                    return o1.getValue().getValue()-o2.getValue().getValue();
                }
            });
            List<Integer> smallest = new ArrayList<>();
            for (Map.Entry<Integer,Pair<Integer,Integer>> aList : list) {
                if (aList.getValue().getKey().equals(list.get(0).getValue().getKey()) || aList.getValue().getValue().equals(list.get(0).getValue().getValue())) {
                    smallest.add(aList.getKey());
                }
            }
            System.out.println("ToRemove = " + smallest);

            for (Integer rm : smallest) {
//                System.out.println("Remove " + rm);
                int result_copy = result;
                List<Integer> toremove = new ArrayList<>();
                for (int i = 0; i < pairs.size(); i++) {
                    if (pairs.get(i).getKey() == rm) {
                        toremove.add(i);
                        result_copy += Math.pow((int)pairs.get(i).getValue(), 2);
                    }
                }

                List<Pair> copy = new ArrayList<>(pairs);
                for (int i = toremove.size()-1; i >= 0; i--) {
                    copy.remove((int)toremove.get(i));
                }
                copy = merge(copy);
                DFS(copy, result_copy, cands);
            }
        }
    }

    private List<Pair> merge(List<Pair> pairs) {
        List<Pair> merged = new ArrayList<>();
        for (int i = 0; i < pairs.size(); i++) {
            if (merged.isEmpty() || pairs.get(i).getKey() != merged.get(merged.size()-1).getKey()) {
                merged.add(pairs.get(i));
            } else {
                Pair last = merged.get(merged.size()-1);
                merged.set(merged.size()-1, new Pair(last.getKey(), (int)last.getValue()+(int)pairs.get(i).getValue()));
            }
        }
        return merged;
    }

    private List<Pair> convertToPairs(int[] nums) {
        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (pairs.isEmpty() || !pairs.get(pairs.size()-1).getKey().equals(nums[i])) {
                pairs.add(new Pair(nums[i], 1));
            } else {
                Pair last = pairs.get(pairs.size()-1);
                pairs.set(pairs.size()-1, new Pair(last.getKey(), (int)last.getValue()+1));
            }
        }
        return pairs;
    }

    public static void main(String[] args) {
//        int[] boxes = {1, 3, 2, 2, 2, 3, 4, 3, 1}; // expect 23
//        int[] boxes = {6, 10, 1, 7, 1, 3, 10, 2, 1, 3};
        int[] boxes = {3, 8, 8, 5, 5, 3, 9, 2, 4, 4, 6, 5, 8, 4, 8, 6, 9, 6, 2, 8, 6, 4, 1, 9,
                5, 3, 10, 5, 3, 3, 9, 8, 8, 6, 5, 3, 7, 4, 9, 6, 3, 9, 4, 3, 5, 10, 7, 6, 10, 7}; // expect 136

        Solution2 soln = new Solution2();
        final int result = soln.removeBoxes(boxes);
        System.out.println("The largest sum is: " + result);
    }
}
