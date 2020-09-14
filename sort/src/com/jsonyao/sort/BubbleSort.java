package com.jsonyao.sort;

/**
 * On: Java Version: 1.7
 * 冒泡排序算法概念:
 *      a. 由于经过该算法, 越小的元素会经由交换慢慢浮到数列的顶端, 就如同碳酸饮料中二氧化碳的气泡最终会上浮到顶端一样, 故名冒泡排序
 *      b. eg => https://baike.baidu.com/item/%E5%86%92%E6%B3%A1%E6%8E%92%E5%BA%8F/4602306?fr=aladdin
 *      c. eg => https://www.jianshu.com/p/1458abf81adf
 */
public class BubbleSort {

    private void bubbleSort(int[] arr){
        /**
         * A. 核心思想:
         *      a. 比较相邻的元素。如果第一个比第二个大, 就交换他们两个。
         *      b. 对每一对相邻元素做同样的工作, 从开始第一对到结尾的最后一对。经过这一点, 最后的元素应该会是最大的数目。
         *      c. 针对所有的元素重复以上的步骤, 除了最后一个。
         *      d. 持续每次对越来越少的元素重复上面的步骤, 直到没有任何一对数字比较。
         * B. 时间复杂度:
         *      a. 最好的情况为全部正序, 只需要跑 n - 1 趟, 每趟比较 1 个单元时间 => O(n)
         *          eg => https://www.cnblogs.com/melon-h/archive/2012/09/20/2694941.html
         *      b. 最坏的情况为全部逆序, 需要跑 n - 1 趟, 每趟比较 n - 1 - i 次, 共计:
         *          t = (n-1) + (n-2) + ... + 1 = n(n-1)/2 = n^2 - n/2 = O(n^2)
         * C. 稳定性:
         *      a. 冒泡总是把最小的前调或者最大的后调整, 相等的不调整, 所以不会打乱本来排好序的数组, 是稳定的
         */
        // 基础版
//        for(int i = 0; i < arr.length - 1; i++){// 减1是因为n长的数组, 最后一趟只剩下一个元素, 不用比较了, 所以n长的数组只需要比较 n - 1 趟就可以了
//            for(int j = 0; j < arr.length - 1 - i; j++){// 减1是为了防止比较最后一对时发生数组越界, 减i是为了减少每趟结果的再次比较
//                if(arr[j] > arr[j+1]){// 注意这里要维持稳定, 不能写 >=
//                    int temp = arr[j+1];
//                    arr[j+1] = arr[j];
//                    arr[j] = temp;
//                }
//            }
//        }

        /**
         * 优化版:
         *      a. 当还没到最后一趟已经全部排好序, 则无需再进行排序, 直接跳出程序
         *      b. 在基础版的基础上加入了标志位, 判断直到该轮结束还没有进行过交换, 如果没有则无需再次比较排序了, 直接跳出程序
         *      c. 时间复杂度最好最坏情况, 比较次数减少, 但整体O(n)和O(n^2)还是没变
         *      d. 稳定性没变
         */
        int temp;
        boolean swapFlag;
        for(int i = 0; i < arr.length - 1; i++){
            swapFlag = false;
            for(int j = 0; j < arr.length - 1 - i; j++){
                if(arr[j] > arr[j+1]){
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                    swapFlag = true;
                }
            }
            if(!swapFlag) break;// 如果
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{
                5, 4, 3, 2, 1
        };

        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
