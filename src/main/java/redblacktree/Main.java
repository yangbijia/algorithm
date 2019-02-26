package main.java.redblacktree;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(new Integer[]{20, 15, 40, 10, 17, 32, 46, 5, 13, 37, 42});

        TreeNode root = null;
        for (Integer e : list) {
            root = TreeNodeUtil.insert(root, e);
        }

        int high = TreeNodeUtil.countHigh(root);
        System.out.println("high is " + high);
        System.out.println("RB-TREE show: ");
        TreeNodeUtil.showRBT(root, high, 4);

        System.out.println("\n\ninsert 35");
        root = TreeNodeUtil.insert(root, 35);
        high = TreeNodeUtil.countHigh(root);
        System.out.println("high is " + high);
        System.out.println("RB-TREE show: ");
        TreeNodeUtil.showRBT(root, high, 4);

        System.out.println("\n\ndelete 17");
        root = TreeNodeUtil.delete(root, TreeNodeUtil.search(root, 17));
        high = TreeNodeUtil.countHigh(root);
        System.out.println("high is " + high);
        System.out.println("RB-TREE show: ");
        TreeNodeUtil.showRBT(root, high, 4);

        System.out.println("\n\ninsert 17");
        root = TreeNodeUtil.insert(root, 17);
        high = TreeNodeUtil.countHigh(root);
        System.out.println("high is " + high);
        System.out.println("RB-TREE show: ");
        TreeNodeUtil.showRBT(root, high, 4);



    }

    public static void sort(){}



}
