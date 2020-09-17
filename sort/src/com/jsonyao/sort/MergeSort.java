package com.jsonyao.sort;

import java.util.Arrays;

/**
 * On: Java Version 1.7
 * 归并排序概念:
 *      a. 归并排序是建立在归并操作上的一种有效稳定的排序算法
 *      b. 该算法采用分治法, 是通过将已有序的子序列合并, 得到完全有序的序列, 即先使每个子序列有序, 再使子序列间有序
 *      c. 若将两个有序表合并成一个有序表, 称为2-路归并
 * Relation:
 *      a. https://www.cnblogs.com/onepixel/articles/7674659.html
 *      b. https://baike.baidu.com/item/%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F/1639015?fr=aladdin
 *      c. https://blog.csdn.net/a1033025319/article/details/88650514
 */
public class MergeSort {

    public int[] mergeSort(int[] arr){
        /**
         * A. 核心思想:
         *      a. 把长度为n的输入序列分成两个长度为n/2的子序列
         *      b. 再对这两个子序列进行归并排序(所以子序列也会重复a,b,c操作)
         *      c. 将两个排序好的子序列合并成一个最终的排序序列
         * B. 时间复杂度:
         *      a. 由改程序可以看出, 时间复杂度通式为f(n) = 2f(n/2) + n
         *      b. 由通式推导出每次的时间复杂度为:
         *         第1次, n = n => f(n) = 2f(n/2) + n
         *         第2次, n = n/2 => f(n/2) = 2f(n/4) + n/2
         *         第3次, n = n/4 => f(n/4) = 2f(n/8) + n/4
         *         ...
         *         第k次, n = n/2^(k-1) => f(n/2^[k-1]) = 2f(n/2^[k]) + n/2^(k-1)
         *      c. 故总的时间复杂度为f(n) = 2f(n/2) + n                             <=  n = n
         *                              = 2(2f(n/4) + n/2) + n = 4f(n/4) + 2n     <=  n = n/2 = n/2^1
         *                              = 4(2f(n/8) + n/4) + 2n = 8f(n/8) + 3n    <=  n = n/4 = n/2^2
         *                              ...
         *                              = 2^k * f(n/2^k) + kn                       <= n = n/2^(k-1)
         *      d. 而对于长度n的序列来说, 第1次n剩n/2, 第2次n剩n/4, ..., 第k次时还剩下1个, 建立公式, n*(1/2)^k = 1 => k=logn
         *      e. 故当k=logn时, 由于是最后一次分割, 此时n/2^k = 1, 所以2路归并的平均时间复杂度为: f(n) = 2^(logn) * f(1) + nlogn = nlogn => O(nlogn)
         *      g. 最好情况和最坏情况都需要进行f(n) = 2f(n/2) + n的比较、合并操作, 所以这和选择排序一样, 性能不受输入数据的影响但性能要比选择排序好的多, 故时间复杂度还是O(nlogn)
         * C. 稳定性:
         *      a. 由于2-路归并排序在分割时是按照顺序分割的, 相等元素的相对位置不会发生变化
         *      b. 而在合并过程中, 对于相等的元素时, 会把左边的赋值给新数组, 这样相等元素的前后顺序依然不会发生变化
         *      c. 在分割和合并过程都不会破坏稳定性, 故2-路归并排序是稳定的
         * D. 算法分析:
         *      a. 归并排序的维持是要付出代价的, 代价就是需要额外的内存空间, 是个Out-Place添加了外址的排序
         *      b. 虽然归并排序比较占用内存, 但确实一种效率高且稳定的算法
         */
        if(arr.length < 2){// 递归出口, 只有一个元素时, 表示分割到了最里面一层的子序列了, 这时需要返回做左右子序列合并操作了
            return arr;
        }

        int middleIndex = arr.length / 2;// 取中部指针(向下取值)
        int[] leftArr = Arrays.copyOfRange(arr, 0, middleIndex);// copyOfRange: From取index, 截取的长度为to-From, 代表只截取到to之前的元素, 所以可以为arr.length
        int[] rightArr = Arrays.copyOfRange(arr, middleIndex, arr.length);// 同上

        return merge(mergeSort(leftArr), mergeSort(rightArr));// 对左右子序列递归做归并排序
    }

    private int[] merge(int[] leftArr, int[] rightArr){
        int[] result = new int[leftArr.length + rightArr.length];// 结果取两者长度之和

        // 如果左子序列和子序列长度同时大于0
        int m = 0, i = 0, j = 0;
        while (i < leftArr.length && j < rightArr.length){
            result[m++] = rightArr[j] < leftArr[i]? rightArr[j++] : leftArr[i++];// 取小值, 这里为了避免稳定性丢失, 需要比较的是右边小于左边时取右边, 大于等于时取左边, 保证相等元素相对顺序不变
        }

        // 如果还剩余左子序列
        while (i < leftArr.length){
            result[m++] = leftArr[i++];
        }

        // 如果还剩余右子序列
        while (j < rightArr.length){
            result[m++] = rightArr[j++];
        }

        return result;
    }

    public void betterMergeSort(){
        /**
         * A. 归并排序的优化
         *      a. 改进归并能够排序在归并时先判断前段序列的最大值与后段序列的做小值的关系, 再确定是否进行赋值比较
         *      b. 如果前段序列的最大值小于等于后段序列的最小值, 则说明序列可以直接形成一段有序序列不需要再归并, 反之则需要
         *      c. 这种优化在序列本身有序的情况下时(最好的情况), 时间复杂度可以将至O(n)
         *      d. TimSort可以说是归并排序的终极优化版本, 主要思想就是检测序列中的天然有序子段(若检测到严格降序子段则翻转序列为升序子段), 则在最好情况下无论是升序还是降序,
         *         都可以使时间复杂度降至O(n), 具有很强的自适应性
         * B. 核心思想:
         *      a.
         */
    }

    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();

        int[] arr = new int[]{
                9, 8, 7, 6, 5, 4, 3, 2, 10, 1, 11
        };

        arr = mergeSort.mergeSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }

}
