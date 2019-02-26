package redblacktree;

import java.util.Stack;

/**
 * redblack tree
 *
 * @author yangbijia
 * @date 2019/1/2
 */
public class TreeNodeUtil {

    /**
     * 左旋
     * @param root
     * @param z
     * @return
     */
    public static TreeNode leftRotate(TreeNode root, TreeNode z){
        TreeNode x = z.right;
        z.right = x.left;
        x.left.parent = z;
        if(z.parent == null){
            root = x;
        }else if(z.parent.left == z){
            z.parent.left = x;
        }else{
            z.parent.right = x;
        }
        x.parent = z.parent;
        z.parent = x;
        x.left = z;

        z.level++;
        x.level--;
        traverseLevelOp(z.parent.right, false);
        traverseLevelOp(z.left, true);
        return root;
    }

    /**
     * 右旋
     * @param root
     * @param z
     * @return
     */
    public static TreeNode rightRotate(TreeNode root, TreeNode z){
        TreeNode x = z.left;
        z.left = x.right;
        x.right.parent = z;
        if(z.parent == null){
            root = x;
        }else if(z.parent.right == z){
            z.parent.right = x;
        }else{
            z.parent.left = x;
        }
        x.parent = z.parent;
        z.parent = x;
        x.right = z;

        z.level++;
        x.level--;
        traverseLevelOp(z.parent.left, false);
        traverseLevelOp(z.right, true);
        return root;
    }

    /**
     * 遍历树上所有节点level加一或减一
     * @param root
     * @param addOrDown true：level++，false：level--
     */
    public static void traverseLevelOp (TreeNode root, boolean addOrDown) {
        Stack stack = new Stack();
        stack.push(root);
        while (true) {
            TreeNode current = (TreeNode) stack.pop();
            if (addOrDown) {
                current.level++;
            } else {
                current.level--;
            }
            if (!current.nil && current.left != null) {
                stack.push(current.left);
            }
            if (!current.nil && current.right != null) {
                stack.push(current.right);
            }
            if (stack.empty()) {
                return;
            }
        }
    }


    /**
     * 插入节点
     * @param root
     * @param value
     * @return
     */
    public static TreeNode insert (TreeNode root, Integer value) {
        if (value == null) {
            return null;
        }
        TreeNode<Integer> current = root;
        int level = 1;
        while (true) {
            if (current == null) {
                current = TreeNode.getInstance(level + 1);
                current.value = value;
                current.level = level;
                root = insertFixup(root, current);
                return root;
            } else {
                if ((int)current.value > value) {
                    if (current.left != null && current.left.nil) {
                        TreeNode treeNode = TreeNode.getInstance(level + 2);
                        treeNode.value = value;
                        treeNode.parent = current;
                        treeNode.level = level + 1;
                        current.left = treeNode;
                        root = insertFixup(root, treeNode);
                        return root;
                    }else{
                        current = current.left;
                        level++;
                    }
                } else if ((int)current.value < value) {
                    if (current.right != null && current.right.nil) {
                        TreeNode treeNode = TreeNode.getInstance(level + 2);
                        treeNode.value = value;
                        treeNode.parent = current;
                        treeNode.level = level + 1;
                        current.right = treeNode;
                        root = insertFixup(root, treeNode);
                        return root;
                    } else {
                        current = current.right;
                        level++;
                    }
                } else {
                    return root;
                }
            }
        }
    }

    /**
     * 插入后修正使其恢复红黑树性质
     * @param root
     * @param z
     */
    public static TreeNode insertFixup(TreeNode root, TreeNode z) {
        TreeNode y, rotateNode = null;

        if (root == null) {
            z.red = false;
            return z;
        }

        while (z.parent != null && z.parent.red) {
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if (y.red) {
                    z.parent.red = false;
                    y.red = false;
                    z.parent.parent.red = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        rotateNode = leftRotate(root, z);
                    }
                    z.parent.red = false;
                    z.parent.parent.red = true;
                    rotateNode = rightRotate(root, z.parent.parent);
                }
            } else {
                y = z.parent.parent.left;
                if (y.red) {
                    z.parent.red = false;
                    y.red = false;
                    z.parent.parent.red = true;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateNode = rightRotate(root, z);
                    }

                    z.parent.red = false;
                    z.parent.parent.red = true;
                    rotateNode = leftRotate(root, z.parent.parent);
                }
            }
            if (z.parent == null) {
                z.red = false;
                root = z;
            }
        }
        return (rotateNode != null && rotateNode.parent == null) ? rotateNode : root;
    }

    /**
     * 删除节点
     * @param root
     * @param z
     * @return
     */
    public static TreeNode delete(TreeNode root, TreeNode z) {
        TreeNode x,y;
        if (z == null) {
            return root;
        } else {
            if (z.left.nil || z.right.nil) {
                y = z;
            } else {
                y = successor(z);
            }
            if (!y.left.nil) {
                x = y.left;
            } else {
                x = y.right;
                traverseLevelOp(x, false);
            }
            x.parent = y.parent;
            if (y.parent.nil) {
                root = x;
            } else if (y == y.parent.left) {
                y.parent.left = x;
            } else {
                y.parent.right = x;
            }
            if (y != z) {
                z.value = y.value;
            } else {
            }
            if (!y.red) {
                root = deleteFixup(root, x);
            }
        }

        return root;
    }

    /**
     * 删除后修正使其恢复红黑树性质
     * @param root
     * @param x
     */
    public static TreeNode deleteFixup(TreeNode root, TreeNode x) {

        while (x != root && !x.red) {
            TreeNode w;
            if (x == x.parent.left) {
                w = x.parent.right;
                if (w.red) {
                    w.red = false;
                    x.parent.red = true;
                    root = leftRotate(root, x.parent);
                    w = x.parent.right;
                }
                if (!w.left.red && !w.right.red) {
                    w.red = true;
                    x = x.parent;
                } else {
                    if (!w.right.red) {
                        w.left.red = true;
                        root = rightRotate(root, w);
                        w = x.parent.right;
                    }
                    w.red = x.parent.red;
                    x.parent.red = false;
                    w.right.red = false;
                    root = leftRotate(root, x.parent);
                    x = root;
                }
            } else {
                w = x.parent.left;
                if (w.red) {
                    w.red = false;
                    x.parent.red = true;
                    root = rightRotate(root, x.parent);
                    w = x.parent.left;
                }
                if (!w.left.red && !w.right.red) {
                    w.red = true;
                    x = x.parent;
                } else {
                    if (!w.left.red) {
                        w.right.red = true;
                        root = leftRotate(root, w);
                        w = x.parent.left;
                    }
                    w.red = x.parent.red;
                    x.parent.red = false;
                    w.left.red = false;
                    root = rightRotate(root, x.parent);
                    x = root;
                }
            }
        }
        x.red = false;
        return root;
    }

    /**
     * 获取节点x的后继
     * @param x
     * @return
     */
    public static TreeNode successor(TreeNode x) {
        TreeNode y;
        if (x.right.nil) {
            y = x.parent;
            while (y != null && x == y.right) {
                x = y;
                y = y.parent;
            }
        } else {
            y = minimum(x.right);
        }
        return y;
    }

    /**
     * 获取x所在子树的最小节点，即最左节点
     * @param x
     * @return
     */
    public static TreeNode minimum(TreeNode x) {
        while (!x.left.nil) {
            x = x.left;
        }
        return x;
    }


    /**
     * 树形打印
     * @param root
     * @param high
     * @param maxDataWidth
     */
    public static void showRBT(TreeNode root, int high, int maxDataWidth) {
        if (root == null) {
            return;
        }
        //树的最大宽度,即最后一行的宽度
        //数据宽度n*4+最后一行节点之间的宽度(n-1)*4
        int baseSpace = (int)((Math.pow(2, high - 1)) * maxDataWidth + ((Math.pow(2, high - 1)) - 1) * maxDataWidth);
        StringBuilder print = new StringBuilder();
        Stack gstack = new Stack();
        root.width = baseSpace/2;
        gstack.push(root);
        boolean end = true;

        while (end) {
            end = false;
            Stack stack = new Stack();

            System.out.println();
            boolean isFirst = true;
            while (!gstack.empty()) {

                TreeNode current = (TreeNode) gstack.pop();

                print.delete(0, print.length());

                int firstSpaceAmount = baseSpace / (int) Math.pow(2, current.level);
                int spaceAmount;
                if (current.parent == null) {
                    spaceAmount = current.width;
                } else {
                    spaceAmount = current.parent.width - firstSpaceAmount;
                }
                current.width = firstSpaceAmount;

                if (isFirst) {

                    while ((firstSpaceAmount--) > 0) {
                        print.append(" ");
                    }
                    isFirst = ! isFirst;
                } else {
                    spaceAmount = spaceAmount * 2 - maxDataWidth;
                    while ((spaceAmount--) > 0) {
                        print.append(" ");
                    }
                }
                if (current.level == high && current.parent.nil) {
                    print.append(String.format("%" + maxDataWidth + "s", " "));
                }else if (current.red) {
                    print.append(String.format("%" + maxDataWidth + "s", "[" + current.value + "]"));
                } else if (current.nil) {
                    print.append(String.format("%" + maxDataWidth + "s", "nil"));
                } else {
                    print.append(String.format("%" + maxDataWidth + "s", current.value));
                }
                System.out.print(print);


                if (!current.nil) {
                    end = true;
                    stack.push(current.left);
                    stack.push(current.right);
                } else if (current.level < high) {
                    stack.push(TreeNode.getNilInstance(high, current));
                    stack.push(TreeNode.getNilInstance(high, current));
                }

            }
            while (!stack.empty()) {
                gstack.push(stack.pop());
            }
        }
    }

    /**
     * 计算树的高度
     * @param root
     * @return
     */
    public static int countHigh(TreeNode root){
        if (root == null) {
            return 0;
        }
        Stack gstack = new Stack();
        gstack.push(root);
        boolean end = true;
        int high = 0;
        while (end) {
            end = false;
            Stack stack = new Stack();
            high++;
            while (!gstack.empty()) {
                TreeNode current = (TreeNode) gstack.pop();

                if (!current.nil) {
                    stack.push(current.left);
                    stack.push(current.right);
                    end = true;
                }
            }

            while (!stack.empty()) {
                gstack.push(stack.pop());
            }
        }
        return high;

    }

    /**
     * 树中搜索某一节点
     * @param root
     * @param value
     * @return
     */
    public static TreeNode search(TreeNode root, Integer value) {
        TreeNode current = root;
        while (true) {
            if ((int)current.value == value) {
                return current;
            } else {
                if ((int)current.value > value) {
                    current = current.left;
                } else {
                    current = current.right;
                }
                if (current.nil) {
                    return null;
                }
            }
        }
    }
}
