import javafx.util.Pair;

import java.util.*;

class Solution {

    public int removeBoxes(int[] nums) {
        int result = 0;

        List<Pair> pairs = convertToPairs(nums);
        while (!pairs.isEmpty()) {
            pairs.forEach(p -> {
                System.out.print(String.format("(%d,%d)", p.getKey(), p.getValue()));
            });
            System.out.println();

            Map<Integer, Pair> freq = new HashMap<>();
            for (Pair p : pairs) {
                if (freq.containsKey(p.getKey())) {
                    Pair<Integer, Integer> found = freq.get(p.getKey());
                    freq.put((int) p.getKey(), new Pair(found.getKey() + 1, found.getValue() + (int) p.getValue()));
                } else {
                    freq.put((int) p.getKey(), new Pair(1, p.getValue()));
                }
            }
//        freq.forEach((k,v) -> {
//            System.out.println(String.format("[%d,%d]", k, v));
//        });
//        List<Map.Entry<Integer,Integer>> list = new ArrayList<>(freq.entrySet());
//        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
//        list.forEach(elm -> {
//            System.out.print(String.format("(%d,%d)", elm.getKey(), elm.getValue()));
//        });
//        System.out.println();

            List<Map.Entry<Integer, Pair>> list = new ArrayList<>(freq.entrySet());
            Collections.sort(list, (o1, o2) -> {
                if (o1.getValue().getKey() != o2.getValue().getKey()) {
                    return (int) o1.getValue().getKey() - (int) o2.getValue().getKey();
                } else {
                    return (int) o1.getValue().getValue() - (int) o2.getValue().getValue();
                }
            });
            list.forEach(e -> {
                System.out.print(String.format("[%d,%d,%d]", e.getKey(), e.getValue().getKey(), e.getValue().getValue()));
            });
            System.out.println();

            Map.Entry<Integer, Pair> smallest = Collections.min(freq.entrySet(), (o1, o2) -> {
                if (o1.getValue().getKey() != o2.getValue().getKey()) {
                    return (int) o1.getValue().getKey() - (int) o2.getValue().getKey();
                } else {
                    return (int) o1.getValue().getValue() - (int) o2.getValue().getValue();
                }
            });
            System.out.println("The smallest Pair is: [" + smallest.getKey() + "," + smallest.getValue().getKey() + "," + smallest.getValue().getValue() + "]");

            List<Integer> toRemove = new ArrayList<>();
            for (int i = 0; i < pairs.size(); i++) {
                if (pairs.get(i).getKey() == smallest.getKey()) {
                    toRemove.add(i);
                    result += Math.pow((int) pairs.get(i).getValue(), 2);
                }
            }
            System.out.println("result = " + result);
//            System.out.println("To remove: " + toRemove);
            for (int i = toRemove.size() - 1; i >= 0; i--) {
                pairs.remove((int) toRemove.get(i));
            }

            pairs = merge(pairs);
        }

//        Map<Integer,Integer> freq = new HashMap<>();
//        pairs.forEach(p -> {
//            if (freq.containsKey(p.getKey())) {
//                freq.put((int)p.getKey(), (int)p.getValue()+1);
//            } else {
//                freq.put((int)p.getKey(), (int)p.getValue());
//            }
//        });
////        freq.forEach((k,v) -> {
////            System.out.println(String.format("[%d,%d]", k, v));
////        });
//
//        PriorityQueue<Map.Entry<Integer,Integer>> pq = new PriorityQueue<>(pairs.size(), (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
//        freq.entrySet().forEach(pq::add);
////        while (!pq.isEmpty()) {
////            Map.Entry curr = pq.remove();
////            System.out.println(String.format("[%d,%d]", curr.getKey(), curr.getValue()));
////        }
//
//        while (!pq.isEmpty()) {
//            Map.Entry curr = pq.remove();
//            System.out.println(String.format("[%d,%d]", curr.getKey(), curr.getValue()));
//        }

        return result;
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
//        int[] boxes = {1, 3, 2, 2, 2, 3, 4, 3, 1};
//        int[] boxes = {6, 10, 1, 7, 1, 3, 10, 2, 1, 3};
        int[] boxes = {3, 8, 8, 5, 5, 3, 9, 2, 4, 4, 6, 5, 8, 4, 8, 6, 9, 6, 2, 8, 6, 4, 1, 9,
                5, 3, 10, 5, 3, 3, 9, 8, 8, 6, 5, 3, 7, 4, 9, 6, 3, 9, 4, 3, 5, 10, 7, 6, 10, 7};

        Solution soln = new Solution();
        final int result = soln.removeBoxes(boxes);
        System.out.println("The largest sum is: " + result);
    }
}
