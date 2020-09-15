package com.jsonyao.sort;

/**
 * On: Java Version 1.7
 * 选择排序概念:
 *      a. 选择排序是一种简单直观的排序算法, 经过不断选择小的数加入到排序序列中, 直到排序完毕
 */
public class SelectionSort {

    private void selectionSort(int[] arr){
        /**
         * A. 核心思想:
         *      a. 首先在未排序序列中找到最小元素, 存放到排序序列的起始位置
         *      b. 然后, 在从剩余未排序元素中继续找到次小元素, 然后放到已排序序列的末尾
         *      c. 以此类推, 直到所有元素均排序完毕
         *      d. 即排序时分为 有序序列R1[1..i-1] 以及 待排序序列R2[i..n]
         * B. 时间复杂度:
         *      a. 比较次数: t = (n-1) + (n-2) +...+ 1 = n(n-1)/2 = O(n^2)
         *      b. 交换次数:
         *          b.1. 最好情况: 有序序列0次, 逆序序列n/2次
         *          b.2. 最坏情况: 交换n-1次
         *          b.3. 交换次数O(n), 由于冒泡排序最坏情况逆序时交换次数为n(n-1)/2次, O(n^2),
         *               而交换所需CPU时间比比较所需的CPU时间多, 选择排序比冒泡排序快
         * C. 稳定性:
         *      a. 稳定性定义：
         *          a.1. 稳定: 如果a原本在b前面, 而a=b, 排序之后a仍然在b的前面
         *          a.2. 不稳定: 如果a原本在b前面, 而a=b, 排序之后a可能会出现在b的后面
         *      b. 选择排序是给有序序列位置选择当前元素最小的, 比如给第一个位置选择最小的, 在剩余元素里面给第二个元素选择第二小的
         *      c. 以此类推, 直到第 n-1 个元素, 第n个元素不用比较, 因为只剩下它一个最大的元素了
         *      d. 如果在一趟比较中, 如果一个元素比当前元素小, 而该小的元素又出现在一个和当前元素相等的元素后面, 那么交换后稳定性就被破坏了
         *      e. 比如 5 8 5 2 9, 在第一次比较后5和2交换位置, 打乱了起始两个5的位置, 稳定性被破坏, 故选择排序是一个不稳定的排序算法
         *      f. eg => https://baike.baidu.com/item/%E9%80%89%E6%8B%A9%E6%8E%92%E5%BA%8F/9762418?fr=aladdin
         * D. 算法分析:
         *      a. 选择排序是"最稳定"的排序算法之一, 因为无论什么数据进去都是O(n^2)的时间复杂度
         *      b. 所以用它的时候, 数据规模越小越好
         *      c. 唯一的好处可能就是不占用额外的内存空间了吧
         *      d. 理论上讲，选择排序可能也是平时排序一般人想到的最多的排序方法了吧
         *      e. eg => https://www.cnblogs.com/onepixel/articles/7674659.html
         */
        int minIndex;// 记录最小值下标
        int temp;// 交换临时变量

        // 遍历有序序列, 取末尾项与待排序序列进行比较
        for(int i = 0; i < arr.length - 1; i++){// 这里减一是因为只需要比较 n-1 趟, 最后一个不用比较了, 因为倒数第二个已经比较过了
            minIndex = i;// 初始值为有序序列末尾

            // 遍历待排序序列
            for(int j = i+1; j < arr.length; j++){// 设定i为有序序列开头, i+1作为待排序序列开头, 直到数组末尾
                if(arr[j] < arr[minIndex]){// 当待排序序列元素小于有序序列时, 记录交换指针, 再继续寻找最小值
                    minIndex = j;
                }
            }

            // 当待排序序列遍历完时, 交换指针
            temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    public static void main(String[] args) {
        SelectionSort selectionSort = new SelectionSort();

        int[] arr = new int[]{
                2, 4, 1, 6, 5, 3
        };
        selectionSort.selectionSort(arr);

        for(int i =0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

}
