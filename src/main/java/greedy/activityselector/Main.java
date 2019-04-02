package greedy.activityselector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 活动选择，每个活动要求以独占的方式使用资源，给出每个活动的开始和结束时间，找出最大的相互兼容的集合
 * @author ellin
 * @since 2019/03/25
 */
public class Main {

    /**
     * 输入：活动i开始时间Si
     */
    static List<Integer> s = Stream
            .of(new Integer[]{1, 3, 0, 5, 3, 5, 6 , 8 , 8 , 2 , 12})
            .collect(Collectors.toList());

    /**
     * 输入：活动i结束时间Fi
     */
    static List<Integer> f = Stream
            .of(new Integer[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14})
            .collect(Collectors.toList());

    /**
     * 辅助输出活动i时间区间Ai
     */
    static List<String> a = Stream
            .of(new String[]{"1,4", "3,5", "0,6", "5,7", "3,8", "5,9", "6,10", "8,11", "8,12", "2,13", "12,14"})
            .collect(Collectors.toList());

    /**
     * 总活动数
     */
    static Integer n = s.size();

    public static void main(String[] args) {

        // 为了能够进行贪心运算，对输入数据进行一些处理，先将活动按结束时间单调递增排序

        List<Integer> activitySelector = new ArrayList<>();
        // 由于已按结束时间单调递增排序，第一个活动定是最大兼容集合的第一个元素
        activitySelector.add(0);

        // 尾递归方式
        //activitySelector.addAll(recursiveActivitySelector(0, n));

        // 非递归方式，尾递归转为迭代
        activitySelector.addAll(greedyActivitySelector());

        for (Integer as : activitySelector) {
            System.out.print("{" + a.get(as) + "}");
        }
        System.out.println();
        System.out.println(activitySelector.toString());
    }

    /**
     * 递归方式
     * @param i
     * @param j
     * @return
     */
    public static List<Integer> recursiveActivitySelector(int i, int j) {
        int m = i + 1;
        // 上一个节点的结束时间大于当前节点的开始时间，则继续寻找下一个当前节点
        while (m < j && s.get(m) < f.get(i)) {
            m++;
        }
        List<Integer> collector = new ArrayList<>();
        if (m < j) {
            collector.add(m);
            collector.addAll(recursiveActivitySelector(m, j));
        }
        return collector;
    }

    /**
     * 迭代贪心算法
     * @return
     */
    public static List<Integer> greedyActivitySelector() {
        int i = 0, m = i + 1;
        List<Integer> collector = new ArrayList<>();
        while (m < n) {
            if (s.get(m) > f.get(i)) {
                i = m;
                collector.add(m);
            }
            m++;
        }
        return collector;
    }
}
