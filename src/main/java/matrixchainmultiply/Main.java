package matrixchainmultiply;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 计算矩阵A1*A2*……*An乘积的最优乘法，即最优加全括号
 * @author ellin
 * @since 2019/03/19
 */
public class Main {

    static List<Integer> p = Stream.of(new Integer[]{30, 35, 15, 5, 10, 20, 25}).collect(Collectors.toList());
    static int len = p.size();
    /**
     * 存放最优代价结果
     */
    static int[][] m = new int[len][len];

    /**
     * 存放裂变位置
     */
    static int[][] s = new int[len][len];

    public static void main(String[] args) {
        // 初始化结果数组
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                m[i][j] = -1;
            }
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                s[i][j] = -1;
            }
        }

        //计算矩阵A1*A2*……*An乘积的最优代价，两个矩阵相乘 A1mxn * A2nxr 的代价为：m*n*r
        // 计算矩阵从i到j的最优代价
        int i = 1, j = len - 1;
        int min = m[i][j] == -1 ? optimalSolution(i, j, i) : m[i][j];
        int s_all = i;
        for (int pos = i + 1; pos < j; pos++) {
            int res = m[i][j] == -1 ? optimalSolution(i, j, pos) : m[i][j];
            if (res < min) {
                s_all = pos;
                min = res;
            }
        }
        if (s[i][j] == -1) {
            s[i][j] = s_all;
        }
        m[i][j] = min;
        System.out.println("min = " + min);

        for (i = 0; i < len; i++) {
            for (j = 0; j < len; j++) {
                System.out.print(String.format("%8s", m[i][j]));
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        for (i = 0; i < len; i++) {
            for (j = 0; j < len; j++) {
                System.out.print(String.format("%8s", s[i][j]));
            }
            System.out.println();
        }
        System.out.println();

        printOptimalParens(1, len - 1);
        System.out.println();
    }

    /**
     * 递归计算矩阵i-j从k处开始裂变的标量值
     * @param i 矩阵下标Ai
     * @param j 矩阵下标Aj
     * @param k 裂变位置
     * @return
     */
    public static Integer optimalSolution(int i, int j, int k) {
        if (i == j) {
            m[i][j] = 0;
            return 0;
        } else {
            int min_left = m[i][k] == -1 ? optimalSolution(i, k, i) : m[i][k],
                min_right = m[k + 1][j] == -1 ? optimalSolution(k + 1, j, k + 1) : m[k + 1][j];

            int s_left = i, s_right = k + 1;
            // 计算k裂变点及其左边矩阵乘积的最优代价
            for (int pos = i + 1; pos < k; pos++) {
                int res = m[i][k] == -1 ? optimalSolution(i, k, pos) : m[i][k];
                if (res < min_left) {
                    s_left = pos;
                    min_left = res;
                }
            }
            if (s[i][k] == -1){
                s[i][k] = s_left;
            }
            m[i][k] = min_left;
            // 计算k右边矩阵乘积的最优代价
            for (int pos = k + 2; pos < j; pos++) {
                int res = m[k + 1][j] == -1 ? optimalSolution(k + 1, j, pos) : m[k + 1][j];
                if (res < min_right) {
                    s_right = pos;
                    min_right = res;
                }
            }
            if (s[k + 1][j] == -1){
                s[k + 1][j] = s_right;
            }
            m[k + 1][j] = min_right;
            int m_ijk = min_left + min_right + p.get(i - 1) * p.get(j) * p.get(k);
            return m_ijk;
        }

    }


    /**
     * 打印出相乘矩阵的最优加全括号
     * @param i 开始矩阵下标
     * @param j 末尾矩阵下标
     */
    public static void printOptimalParens(int i, int j) {
        if (i == j) {
            System.out.print("A" + i);
            return;
        }
        else {
            System.out.print("(");
            printOptimalParens(i, s[i][j]);
            printOptimalParens(s[i][j] + 1, j);
            System.out.print(")");
        }
    }

}
