package greedy.huffman;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ellin
 * @since 2019/03/27
 */
public class Main {
    //   		                |   a   |   b  |   c   |   d   |   e   |   f    |
    //            | 频度(千字)   |   45  |  13  |  12   |   16   |   9   |   5   |
    //            | 固定长编码   |  000  |  001  |  010  |  011  |  100  |   101  |
    //            | 变长编码 	    |    0  |  101  |  100  |  111  |  1101 |  1100 |

    public static void main(String[] args) {
        // 输入字符及其频度
        List<Node> frequencyInfos = new ArrayList<>();
        frequencyInfos.add(new Node("a", 45));
        frequencyInfos.add(new Node("b", 13));
        frequencyInfos.add(new Node("c", 12));
        frequencyInfos.add(new Node("d", 16));
        frequencyInfos.add(new Node("e", 9));
        frequencyInfos.add(new Node("f", 5));

        //按频度大小排序
        frequencyInfos =
                frequencyInfos.stream().sorted(Comparator.comparing(info -> info.frequency)).collect(Collectors.toList());
        huffmman(frequencyInfos);

    }

    /**
     * 构造HUFFMAN树
     * @param frequencyInfos 按频次排序好的字符列表
     */
    public static void huffmman(List<Node> frequencyInfos) {
        int n;
        Node root = null;
        while ((n = frequencyInfos.size()) > 1) {
            Node left = frequencyInfos.get(0);
            Node right = frequencyInfos.get(1);

            // 两个最低频次的节点作为子节点组合出一个新节点
            Node parent = new Node();
            parent.frequency = left.frequency + right.frequency;
            parent.left = left;
            parent.right = right;
            left.parent = parent;
            right.parent = parent;

            // 节点队列移除以上两个节点
            frequencyInfos.remove(0);
            frequencyInfos.remove(0);
            // 将新节点加入队列
            frequencyInfos.add(parent);
            // 对新队列重新排序，方便直接取出频度最低的两个节点
            frequencyInfos.sort(Comparator.comparing(info -> info.frequency));
            root = parent;
        }
        List<String> huffmanCodes = getHuffmanCode(root);
        for (String code : huffmanCodes) {
            System.out.println(code);
        }
        printHuffmanTree(root, countHigh(root), 4);
        System.out.println();
    }

    /**
     * 根据HUFFMAN树获取赫夫曼编码
     * 先序遍历（根左右）访问到每个叶子节点时，向上访问其到根节点的路径，存下路径后继续遍历
     * @param root
     * @return
     */
    public static List<String> getHuffmanCode(Node root) {
        Map<StringBuilder, StringBuilder> paths = new HashMap<>();

        Stack stack = new Stack();
        stack.push(root);
        while (!stack.empty()) {
            Node cur = (Node) stack.pop();
            // 访问到叶子节点
            if (cur.left == null) {
                StringBuilder leaf = new StringBuilder(cur.charactor);
                Node parent;
                StringBuilder path = paths.get(cur.charactor) == null ?
                        new StringBuilder() : paths.get(cur.charactor);
                // 找出该叶子节点的路径
                while ((parent = cur.parent) != null) {
                    // 左边路径标记为0
                    if (parent.left == cur) {
                        path.append(0);
                    // 右边路径标记为1
                    } else {
                        path.append(1);
                    }
                    cur = parent;
                }
                paths.put(leaf, path);

            } else {
                stack.push(cur.left);
                stack.push(cur.right);
            }
        }
        return paths.values().stream().map(path -> path.toString()).collect(Collectors.toList());
    }

    /**
     * 打印HUFFMAN树
     * @param root 根节点
     * @param high 树的高度
     * @param maxDataWidth 节点数据最大宽度
     */
    public static void printHuffmanTree(Node root, int high, int maxDataWidth) {
        if (root == null) {
            return;
        }
        //已知高度可计算树的最大宽度,即最后一行的宽度
        //数据宽度n*4+最后一行节点之间的宽度(n-1)*4
        int baseSpace = (int)((Math.pow(2, high - 1)) * maxDataWidth + ((Math.pow(2, high - 1)) - 1) * maxDataWidth);
        // 存放每行打印的数据
        StringBuilder print = new StringBuilder();
        Stack gstack = new Stack();
        //计算根节点在一行中的位置
        root.width = baseSpace/2;
        gstack.push(root);
        boolean end = true;
        int level = 0;
        while (end) {
            end = false;
            level++;
            // 要打印的当前行的拼接字符串的长度
            int curPrintPos = 0;
            print.delete(0, print.length());

            Stack stack = new Stack();
            // 打印一行内容
            while (!gstack.empty()) {

                Node current = (Node) gstack.pop();

                // 全二叉树时当前行第一个节点到起点的距离（即当前level行拥有共同父节点的左右子节点间距离的一半）
                int firstSpaceAmount = baseSpace / (int) Math.pow(2, level);
                int spaceAmount;
                if (current.parent == null) {
                    spaceAmount = current.width;
                } else {
                    // 当前节点的位置为父节点的位置加或减去firstSpaceAmount
                    if (current.parent.left == current) {
                        current.width = current.parent.width - firstSpaceAmount;
                    } else {
                        current.width = current.parent.width + firstSpaceAmount;
                    }
                    // 当前节点前需要拼接的空格数 = 当前节点位置 - 当前行的print已经拼接好的前面一部分
                    spaceAmount = current.width - curPrintPos;
                }

                // 减去数据部分宽度
                spaceAmount = spaceAmount - maxDataWidth;
                while ((spaceAmount--) > 0) {
                    print.append(" ");
                }

                // 拼接数据部分
                if (level == high && current.parent == null) {
                    print.append(String.format("%" + maxDataWidth + "s", " "));
                } else if (current.left == null) {
                    print.append(String.format("%" + maxDataWidth + "s", current.frequency + ":" + current.charactor));
                } else {
                    print.append(String.format("%" + maxDataWidth + "s", current.frequency));
                }
                curPrintPos=print.length();

                if (current.left != null) {
                    stack.push(current.left);
                    stack.push(current.right);
                    end = true;
                }
            }
            System.out.println(print);
            while (!stack.empty()) {
                gstack.push(stack.pop());
            }
        }
    }

    /**
     * 计算树的高度
     * 层序遍历计算树的层数
     * @param root
     * @return
     */
    public static int countHigh(Node root){
        if (root == null) {
            return 0;
        }
        Stack gstack = new Stack();
        gstack.push(root);
        int high = 0;
        while (true) {
            Stack stack = new Stack();
            high++;
            while (!gstack.empty()) {
                Node current = (Node) gstack.pop();

                if (current.left != null) {
                    stack.push(current.left);
                    stack.push(current.right);
                }
            }
            if (stack.empty()) {
                break;
            }
            while (!stack.empty()) {
                gstack.push(stack.pop());
            }
        }
        return high;
    }

}
