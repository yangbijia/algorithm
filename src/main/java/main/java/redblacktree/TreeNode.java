package main.java.redblacktree;


import java.util.HashMap;
import java.util.List;

/**
 * @author bijiayang
 */
public class TreeNode<T> {

    boolean red;

    TreeNode<T> left;

    TreeNode<T> right;

    TreeNode<T> parent;

    T value;

    int level;

    boolean nil;

    int width;

    public static TreeNode getInstance(int nilLevel){
        TreeNode treeNode = new TreeNode();
        treeNode.red = true;
        treeNode.nil = false;
        treeNode.left = TreeNode.getNilInstance(nilLevel, treeNode);
        treeNode.right = TreeNode.getNilInstance(nilLevel, treeNode);
        return treeNode;
    }

    public static TreeNode getNilInstance(int nilLevel, TreeNode parent){
        TreeNode nilInstance = new TreeNode();
        nilInstance.parent = parent;
        nilInstance.red = false;
        nilInstance.nil = true;
        nilInstance.level = nilLevel;
        nilInstance.value = "";
        return nilInstance;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "red=" + red +
                //"left=" + left.value +
                ", value=" + value +
                ", level=" + level +
                ", nil=" + nil +
                '}';
    }
}
