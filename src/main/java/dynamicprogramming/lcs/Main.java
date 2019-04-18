package dynamicprogramming.lcs;


import java.util.ArrayList;
import java.util.List;

/**
 * 提供字符串s1和s2，计算它们的最长公共子序列
 * @author ellin
 * @since 2019/04/18
 */
public class Main {

    public static void main(String[] args) {
        String s1 = "ABCBDAB", s2 = "BDCABA";

        int len1 = s1.length() + 1, len2 = s2.length() + 1;
        //c[i,j] 字符串S0,i和S0,j最长公共子串长度
        int[][] c = new int[len1][len2];
        for (int i = 0; i < len1; i++) {
            c[i][0] = 0;
        }
        for (int j = 0; j < len2 ; j++) {
            c[0][j] = 0;
        }

        // 初始化最长公共子序列长度表
        initLcsLength(s1, s2, c);
        System.out.println("-----------");

        // 打印c[i,j]
        System.out.print(String.format("%5s", " "));
        for (int j = 0; j < len1 - 1; j++) {
            System.out.print(String.format("%2s", j));
        }
        System.out.println();
        System.out.print(String.format("%7s", " "));
        for (int j = 0; j < len2 - 1; j++) {
            System.out.print(String.format("%2s", s2.charAt(j)));
        }
        System.out.println();
        for (int i = 0; i < len1; i++) {
            if (i > 0) {
                System.out.print(String.format("%4s", i + " " + s1.charAt(i - 1)) + " ");
            } else {
                System.out.print(String.format("%5s", " "));
            }

            for (int j = 0; j < len2; j++) {
                System.out.print(String.format("%2s", c[i][j]));
            }
            System.out.println();
        }
        System.out.println();

        System.out.println(s1 + ", " + s2);

        // 打印最长公共子序列
        printLcs(s1, s2, c);
    }

    /**
     * 初始化最长公共子序列长度表
     * @param s1 序列1
     * @param s2 序列2
     * @param c 最长公共子序列长度表
     */
    public static void initLcsLength(String s1, String s2, int[][] c) {
        if (s1.isEmpty() || s2.isEmpty()) {
            return ;
        }
        for (int i = 1; i < s1.length() + 1; i++) {
            char c1 = s1.charAt(i - 1);
            for (int j = 1; j < s2.length() + 1; j++) {
                char c2 = s2.charAt(j - 1);
                if (c1 == c2) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else {
                    c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
                }
            }
        }

    }

    /**
     * 利用最长公共子序列长度的表格c[i,j]，打印出所有的最长公共子序列
     * @param s1 序列1
     * @param s2 序列2
     * @param c 最长公共子序列长度表
     */
    public static void printLcs(String s1, String s2, int[][] c) {
        int maxLen = c[c.length - 1][c[0].length - 1];
        List<StringBuilder> list = new ArrayList<StringBuilder>();

        List<Root> rootChars = new ArrayList<>();

        // 找出所有最长公共子串的末尾元素，即找出c[i,j]值最大且字符相等的位置
        int i = c.length - 1, j = c[0].length - 1;
        boolean end = false;
        while (i > 0 && j > 0 && !end) {

            while (j > 0) {
                if (c[i][j] < maxLen) {
                    if (j == c[0].length - 1) {
                        end = true;
                    }
                    break;
                }
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    Root myChar = new Root();
                    myChar.i = i;
                    myChar.j = j;
                    rootChars.add(myChar);
                    break;
                } else {
                    j--;
                }
            }
            i--;
            j = c[0].length - 1;
        }

        // 将末尾元素作为起点元素向下寻找
        for (Root root : rootChars) {
            StringBuilder sub = new StringBuilder();
            int _i = root.i, _j = root.j, last_j = _j, lastLen = c[_i][_j];

            // 寻找公共子序列长度差1的，并且c[i,j]处字符相等的元素，加入公共子序列中
            while (_i > 0) {
                while (_j > 0 && c[_i][_j] > 0 && c[_i][_j] >= lastLen - 1) {
                    if (s1.charAt(_i - 1) == s2.charAt(_j - 1)) {
                        sub.append(s1.charAt(_i - 1));
                        lastLen = c[_i][_j];
                        last_j = _j;
                        break;
                    }
                    _j--;
                }
                if (_j > 0 && c[_i][_j] < lastLen - 1) {
                    _j = last_j - 1;
                } else if (c[_i][_j] > 0){
                    _j--;
                }
                _i--;
            }

            list.add(sub);
        }
        for (StringBuilder sub : list) {
            System.out.println("z = " + sub.reverse().toString());
        }
    }

    static class Root {
        int i;
        int j;
    }
}
