package com.jsonyao.sort;

import java.util.Arrays;

/**
 * On: Java Version 1.7
 * 归并排序优化概念:
 *      a. 归并排序有两种实现方式: 基于递归的归并排序和基于循环的归并排序(分别叫 自顶向下 和 自底向上 的归并排序)
 *      b. 这两种归并算法虽然实现方式不同, 但还是有共同之处的:
 *          b.1. 无论是基于递归还是循环的归并排序, 它们调用的核心方法都是相同的(都调用相同的合并逻辑), 即完成一趟合并的算法, 使两个已经有
 *               序的数组序列合并成一个更大的有序数组序列
 *          b.2. 从排序轨迹上看, 合并序列的长度都是从小(一个元素)到大(整个数组)增大的
 *      c. 总结来说, 2路归并排序其实就是 二分+合并 操作, 其重点应该是合并操作的实现, 二分只需要取对下标即可, 同时由于循环实现提高了时间
 *         复杂度, 所以推荐使用 递归且返回原址方式 betterMergeSort 来实现归并排序
 * Relation:
 *      a. https://www.cnblogs.com/penghuwan/p/7940440.html
 */
public class BetterMergeSort {

    int[] aux;// 辅助序列(用于备份待排序序列, 然后对序列进行比较和覆盖式地对待排序序列赋值)

    /**
     * 归并排序优化-合并逻辑
     * @param arr       待合并序列(一定是左半边有序, 右半边也有序的)
     * @param low       左序列游标起始索引
     * @param mid       左序列游标结束索引
     * @param high      右序列游标结束索引
     */
    private void betterMerge(int[] arr, int low, int mid, int high){
        for(int k = low; k < high+1; k++){
            aux[k] = arr[k];// 深复制, 备份待排序序列
        }

        int i = low;// i代表左序列游标起始索引
        int j = mid + 1;// j代表右序列游标起始索引

        for(int k = low; k < high+1; k++){
            if(i > mid){// 左序列游标 大于 左序列游标结束索引
                arr[k] = aux[j++];// 左序列耗尽, 把右序列值全部赋给待排序序列
            }else if(j > high){// 右序列游标 大于 右序列游标结束索引
                arr[k] = aux[i++];// 右序列耗尽, 把左序列值全部赋给待排序序列
            }else if(aux[j] < aux[i]){
                arr[k] = aux[j++];// 如果右序列值 小于 左序列值, 那么赋值右序列值给待排序序列
            }else {
                arr[k] = aux[i++];// 如果都不是, 只是左序列值 小于等于 右序列值(保证稳定性), 那么赋值左序列值给待排序序列
            }
        }
    }

    /**
     * 归并排序优化-返回本身数组的引用, 但仍然要开辟外部空间
     * @param arr   待排序序列
     */
    public void betterMergeSort(int[] arr){
//        aux = new int[arr.length];
        aux = arr.clone();// 归并排序优化三: 去除原数组序列到辅助数组的拷贝

        baseSort(arr, 0, arr.length-1);// 初始归并排序优化
//        betterBaseSort1(arr, 0, arr.length-1);// 归并排序优化一: 对小规模子数组使用插入排序
//        betterBaseSort2(arr, 0, arr.length-1);// 归并排序优化二: 测试待排序序列中左右半边是否已有序
//        betterBaseSort3(arr, aux,0, arr.length-1);// 归并排序优化三: 去除原数组序列到辅助数组的拷贝
    }

    /**
     * 归并排序优化-核心排序逻辑(实现递归二分再合并)
     *      A. 核心思想:
     *          a. 分解: 将当前待排序的序列A[low],...,A[high]一分为二, 即求分裂点 mid = (low + high) / 2, 而对于子序列来说,
     *             中间索引还需要加上low起始索引
     *          b. 求解：递归地对序列A[low],...,A[mid] 和 A[mid+1],...,A[high]进行归并排序
     *          c. 组合: 将两个已排序的子序列归并为一个有序序列
     *          d. 递归终止条件: 当子序列长度为1时, 因为一个元素自然就代表终极有序了
     *      B. 时间复杂度:
     *          a. 由程序 baseSort可以看出, 时间复杂度通式仍然满足方程 f(n) = 2f(n/2) + n的
     *          b. 其中n代表待排序序列的长度, f(n)的时间复杂度代表序列归并的时间复杂度, n的复杂度代表合并的复杂度
     *          c. 因此同 MergeSort.mergeSort 推导, 故该程序的时间复杂度仍然是O(nlogn)
     *
     * @param arr    待排序序列
     * @param low    序列游标起始索引
     * @param high   序列游标结束索引
     */
    private void baseSort(int[] arr, int low, int high){
        if(low >= high){
            return;// 递归出口: 当待排序数组只剩一个元素时, 则无序继续归并, 直接返回
        }

        int mid = low + (high - low) / 2;// 中间索引取起始索引+长度的一半

        baseSort(arr, low, mid);// 归并左序列
        baseSort(arr, mid+1, high);// 归并右序列
        betterMerge(arr, low, mid, high);// 递归出来时合并归并好的左右序列成一个大的有序序列返回

        return;
    }

    /**
     * 根据算法要求改造直接插入排序
     * @param arr    待排序序列
     * @param low    序列游标起始索引
     * @param high   序列游标结束索引
     */
    private void inSertionSort(int[] arr, int low, int high){
        int preIndex, compare;

        for(int i = low+1; i < high+1; i++){
            preIndex = i - 1;// 前一指针r1
            compare = arr[i];// 比较元素q

            while (preIndex >= low && compare < arr[preIndex]){
                arr[preIndex+1] = arr[preIndex];// 前移指针数值往后面赋值
                preIndex--;// 前移指针前移
            }

            // 直到数组的第low位依然没比较到结果 或者 找到比较元素q大于等于了已排序元素rn时, 插入q到该位置的下一个位置
            arr[preIndex+1] = compare;
        }
    }

    /**
     * 归并排序优化再优化-优化点一
     *      A. 核心思想:
     *          a. 对小规模子数组使用插入排序(使用稳定算法不影响归并的稳定性, 而插入排序性能又比冒泡好)
     *          b. 用不同的方法处理小规模问题能改进大多数递归算法的性能, 因为递归会使小规模问题中方法调用太过频繁
     *          c. 由于插入排序非常简单, 因此, 一般来说在小数组上用插入排序更快, 这种优化能使归并排序的运行时间缩短10%~15%
     * @param arr    待排序序列
     * @param low    序列游标起始索引
     * @param high   序列游标结束索引
     */
    private void betterBaseSort1(int[] arr, int low, int high){
        /**
         * 递归出口逻辑更改
         *      a. 由原来的一个元素 => M个元素
         *      b. 由原来的直接返回 => 调用直接插入排序
         */
        if(low + 5 >= high){// 注意这里是要用>=high, 如果仅仅只有==high的话, 可能会导致找不到递归出口而出现堆栈异常
            inSertionSort(arr, low, high);
            return;
        }

        int mid = low + (high - low) / 2;// 中间索引取起始索引+长度的一半

        betterBaseSort1(arr, low, mid);// 归并左序列
        betterBaseSort1(arr, mid+1, high);// 归并右序列
        betterMerge(arr, low, mid, high);// 递归出来时合并归并好的左右序列成一个大的有序序列返回

        return;
    }

    /**
     * 归并排序优化再优化-优化点二
     *      A. 核心思想:
     *          a. 在一趟合并前, 如果判断出arr[mid] <= arr[mid+1]的话, 就证明arr[low...high]本身就是有序的, 从而不用再进行合并了
     *          b. 这时, 仅仅需要对大于的情况进行合并就好
     * @param arr    待排序序列
     * @param low    序列游标起始索引
     * @param high   序列游标结束索引
     */
    private void betterBaseSort2(int[] arr, int low, int high){
        /**
         * 递归出口逻辑更改
         *      a. 由原来的一个元素 => M个元素
         *      b. 由原来的直接返回 => 调用直接插入排序
         */
        if(low + 5 >= high){// 注意这里是要用>=high, 如果仅仅只有==high的话, 可能会导致找不到递归出口而出现堆栈异常
            inSertionSort(arr, low, high);
            return;
        }

        int mid = low + (high - low) / 2;// 中间索引取起始索引+长度的一半

        betterBaseSort2(arr, low, mid);// 归并左序列
        betterBaseSort2(arr, mid+1, high);// 归并右序列

        // 如果arr[mid] <= arr[mid+1], 则说明arr本身就是有序的, 这时不需要进行合并了, 仅仅需要对大于的情况进行合并就好
        if(arr[mid] > arr[mid+1]) betterMerge(arr, low, mid, high);// 递归出来时合并归并好的左右序列成一个大的有序序列返回
        return;
    }

    /**
     * 归并排序优化再优化-优化点三
     *      A. 核心思想:
     *          a. 在上面介绍的基于递归的归并排序的代码中, 我们在每次调用merge方法时候, 我们都把arr对应的序列拷贝到辅助数组aux中来
     *          b. 实际上, 我们可以通过一种看起来比较逆天的方式把这个拷贝过程给去除掉
     *          c. 为了达到这一点, 由于合并操作里时用aux的值取判断arr该赋予什么新值, 所以合并过后必须交换一次输入和输出数组的角色, 以保证
     *             合并时aux对比数据用到的是排过序后的数据, 因此, 要在递归调用的每个层次交换输入数组和输出数组的角色, 从而不断地把输入数组
     *             排序到辅助数组, 再将数据从辅助数组排序到输入数组
     *          d. 而由于最后一次调用的Merge而言, 最终被排位有序的都是原数组, 而不是辅助数组, Sort参数里的角色交换只是起到了把排序后的原
     *             数组备份到辅助数组的作用, 也就是相当于以前Merge方法里的赋值操作, 这就是该点优化的原理
     * @param arr    待排序序列
     * @param aux    辅助序列
     * @param low    序列游标起始索引
     * @param high   序列游标结束索引
     */
    private void betterBaseSort3(int[] arr, int[] aux, int low, int high){
        if(low >= high){
            return;
        }

        int mid = low + (high - low) / 2;// 中间索引取起始索引+长度的一半

        betterBaseSort3(aux, arr, low, mid);// 归并左序列
        betterBaseSort3(aux, arr, mid+1, high);// 归并右序列
        betterMerge2(arr, aux, low, mid, high);// 递归出来时合并归并好的左右序列成一个大的有序序列返回
        return;
    }

    /**
     * 归并排序优化-合并逻辑
     * @param arr       待合并序列(一定是左半边有序, 右半边也有序的)
     * @param aux       辅助数组
     * @param low       左序列游标起始索引
     * @param mid       左序列游标结束索引
     * @param high      右序列游标结束索引
     */
    private void betterMerge2(int[] arr, int[] aux, int low, int mid, int high){
        // 去除了每次合并操作里对辅助数组的赋值

        int i = low;// i代表左序列游标起始索引
        int j = mid + 1;// j代表右序列游标起始索引

        for(int k = low; k < high+1; k++){
            if(i > mid){// 左序列游标 大于 左序列游标结束索引
                arr[k] = aux[j++];// 左序列耗尽, 把右序列值全部赋给待排序序列
            }else if(j > high){// 右序列游标 大于 右序列游标结束索引
                arr[k] = aux[i++];// 右序列耗尽, 把左序列值全部赋给待排序序列
            }else if(aux[j] < aux[i]){
                arr[k] = aux[j++];// 如果右序列值 小于 左序列值, 那么赋值右序列值给待排序序列
            }else {
                arr[k] = aux[i++];// 如果都不是, 只是左序列值 小于等于 右序列值(保证稳定性), 那么赋值左序列值给待排序序列
            }
        }
    }

    /**
     * 基于一趟排序和循环方式的归并排序
     *      A. 核心思想:
     *          a. 将一个具有n个待排序记录的序列看成是n个长度为1的有序序列
     *          b. 然后进行两两归并, 得到[n/2]个长度为2的有序序列
     *          c. 再进行两两合并, 得到[n/4]个长度为4的有序序列
     *          ...
     *          d. 直至得到1个长度为n的有序序列为止, 排序结束
     *      B. 时间复杂度:
     *          a. t = n/2 + n/4 + ... + n/n
     *                 = n * ([n/2 - 1] / [1 - 1/2])
     *                 = n * (-n/4 + 1/2)
     *                 = -n^2/4 + n/2
     *                 = O(n^2)
     *          b. 时间复杂度增大, 故不推荐循环式归并写法
     * @param arr   待排序序列
     */
    public void otherBetterSort(int[] arr){
        aux = new int[arr.length];

        for(int size = 1; size < arr.length; size = size + size){// 初始时默认看成长度为1的有序序列, size为1, 步长为每个size
            for(int low = 0; low < arr.length - size; low = low + size + size){// 初始时, 默认初始游标为0, 步长为跳过每个size, 用于截取size长的数组, 最后的条件是low+2size >= N-size =》 low+3size>=N =》 N-low<=3size剩余的元素不够3倍size长的时候
                betterMerge(arr, low, low+size-1, Math.min(low+size+size-1, arr.length - 1));// 为了取到对的索引, 这里low+size后, 都需要-1才能得到索引
            }
        }
    }

    public static void main(String[] args) {
        BetterMergeSort betterMergeSort = new BetterMergeSort();

        int[] arr = new int[]{
//                3, 4, 1, 2// 测试 betterMerge
//                3, 4, 1, 2, 5, 1// 测试 betterMergeSort
                5, 3, 4, 1, 6, 3, 4 , 2, 5, 1// 测试 betterMergeSort
        };

//        betterMergeSort.aux = new int[arr.length];// 测试 betterMerge - 开辟辅助序列空间
//        betterMergeSort.betterMerge(arr, 0, 1, 3);// 测试 betterMerge

//        betterMergeSort.betterMergeSort(arr);// 测试递归实现的归并排序

        betterMergeSort.otherBetterSort(arr);// 测试循环实现的归并排序

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
