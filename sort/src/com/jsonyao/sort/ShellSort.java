package com.jsonyao.sort;

/**
 * On: Java Version 1.7
 * 希尔排序概念:
 *      a. 希尔排序是插入排序的一种, 又称缩小增量排序, 是直接插入排序算法的一种更高效的改进版本
 *      b. 希尔排序与直接插入排序不同之处在于, 它会优先比较距离较远的元素, 是第一个突破O(n^2)的算法
 *      c. 希尔排序是把记录按照下标的一定增量分组, 对每组使用直接插入排序算法排序, 而随着增量逐渐减少, 每组包含的关键词越来越多,
 *         当增量减至1时,  整个数组恰好被分成1组, 而这时大部分数据已经是排好序的, 经过这一轮排序后, 算法便终止
 *  Relation：
 *      a. https://www.cnblogs.com/onepixel/articles/7674659.html
 *      b. https://baike.baidu.com/item/%E5%B8%8C%E5%B0%94%E6%8E%92%E5%BA%8F/3229428?fr=aladdin
 */
public class ShellSort {

    private void shellSort(int[] arr){
        /**
         * A. 核心思想:
         *      a. 选择一个增量t1, t2, ..., tk, 其中ti > tj, tk = 1, 一般初次增量取序列的一半, 以后每次减半, 直到为1
         *      b. 设置增量后, 根据对应的增量ti, 将待排序序列分割成若干长度为m的子序列, 分别对各子序列进行直接插入排序
         *      c. 进行k趟增量分组直接插入后, 当增量为1时, 整个序列刚好作为子序列进行排序, 所以这趟排序过后就得到了最终的排序序列了
         * B. 时间复杂度:
         *      a.
         */
        int preIndex, compare;

        for(int gap = arr.length / 2; gap >= 1; gap = gap / 2){// 2. 擦除增量1, 增量根据该序列长度作动态获取; 初始增量默认取序列的一半(向下取整)
            for(int i = gap; i < arr.length; i++){// 1. 先写增量为1的简单插入排序; 2.1 i++代表分组序列交错执行
                preIndex = i - gap;// 前移一个单元, 取已排序序列的最后一个元素作为起始比较元素
                compare = arr[i];// 当前比较元素

                while (preIndex >= 0 && compare < arr[preIndex]){// 比较元素还小时, 则继续比较
                    arr[preIndex+gap] = arr[preIndex];// 覆盖式插入到已排序元素后面一个单元
                    preIndex = preIndex - gap;// 简单插入指针前移
                }

                // 当前移都到分组的1位 或者 找到比较元元素 大于等于 已排序元素后, 需要插入到该已排序元素后面一个单元
                arr[preIndex+gap] = compare;
            }
        }

    }

    public static void main(String[] args) {
        ShellSort shellSort = new ShellSort();

        int[] arr = new int[]{
            3, 5, 1, 2, 1, 4
        };

        shellSort.shellSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
