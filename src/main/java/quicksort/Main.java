package main.java.quicksort;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        List<Integer> arr = new ArrayList<Integer>();
        arr.add(5);
        arr.add(7);
        arr.add(1);
        arr.add(3);
        arr.add(8);
        arr.add(4);
        arr.add(6);

        sort(0, arr.size() - 1, arr);

        System.out.println(arr.toString());
    }

    public static void sort(int left, int right, List<Integer> arr){

        //这里选择左端数字为基数
        int base = arr.get(left), initLeft = left, initRight = right;
        //所以这里从右端指针开始移动
        boolean isLeft = false;
        //左右指针相等，完成一次排序
        while (left < right){
            //从左指针开始移动
            if(isLeft){
                //左指针所在数大于base，将其赋值给右指针所在数
                if(arr.get(left) > base){
                    arr.set(right, arr.get(left));
                    isLeft = !isLeft;
                }else{
                    left++;
                }
            //从右指针开始移动
            }else{
                //右指针所在数小于base，将其赋值给左指针所在数
                if(arr.get(right) < base){
                    arr.set(left, arr.get(right));
                    isLeft = !isLeft;
                }else{
                    right--;
                }
            }
        }
        arr.set(left, base);
        if(left - initLeft > 1){
            sort(initLeft, left-1, arr);
        }
        if(initRight - left > 1){
            sort(left+1, initRight, arr);
        }
    }

}
