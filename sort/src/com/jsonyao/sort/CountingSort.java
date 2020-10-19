package com.jsonyao.sort;

/**
 * On: Java Version 1.7
 * 计数排序概念:
 *      a. 计数排序是一个非基于比较的排序算法
 *      b. 它的优势在于对一定范围内的整数排序时, 它的复杂度为O(n+k)(其中k是整数的范围), 快于任何比较排序算法.
 *      c. 这是一种牺牲空间换取时间的做法, 而且当O(k)>O(nlogn)的时候, 其效率反而不如基于比较的排序, 因为基于比较的排序时间复杂度在理论上的
 *         下限为O(nlogn), 比如归并排序、堆排序
 * Relation:
 *      a. https://baike.baidu.com/item/%E8%AE%A1%E6%95%B0%E6%8E%92%E5%BA%8F/8518144?fr=aladdin
 *      b. https://www.cnblogs.com/onepixel/articles/7674659.html
 *      c. https://zhuanlan.zhihu.com/p/125126086
 *      d. http://zhangchen915.com/index.php/archives/658/
 */
public class CountingSort {

    public int[] countingSort(int[] arr){
        /**
         * A. 核心思想:
         *      a. 对于给定的输入序列中的每一个元素x, 通过对元素值的计数和累加来确定小于x元素的个数, 这样便可以将x直接存放到最终的输出序列上的正确位置
         *      b. 具体步骤:
         *          b.1. 找出待排序序列A中最大的元素max, 创建max+1长度的序列C, 用于存放序列A的计数结果
         *          b.2. 统计序列中每个值为i的元素出现的次数, 存入序列C的第i项, 即: 扫描序列A, 以A中每个元素的值为索引, 将C中对应位置的次数统计加1,
         *               C[i]为A中i出现的次数
         *          b.3. 然后对所有的计数累加, 即从C中的第一个元素开始, 每一项和前一项相加, 以确定值为i的元素在排序后的序列B中出现的位置
         *          b.4. 反向填充目标序列B: 反向遍历序列A, 得到的值i作为索引, 求出C(i), 将每个元素i放在新序列B的第C(i)-1项, 其中C(i)为新序列的第几项,
         *               然后每放入一个元素就将C(i)减去1, 直到序列A遍历完毕后, 得到排好序的序列B, 排序完毕
         *      c. 优化思路:
         *          c.1. 在b.1.中可以看到, 当数组个数远小于最大元素max值时, 开辟的C序列就会过大, 极大浪费空间
         *          c.2. 所以, 针对c.1.的情况, 可以先同时取最大值和最小值, 开辟两者的差值+1的序列C减少空间浪费
         *          c.3. 接着遍历序列A统计元素个数以及计算在序列B中的位置, 但这里不同的是, 由于C序列长度减去了多余的min部分, 所以计算C索引时也需要用A[i]值
         *               减去min的值(这里同样兼容了负值排序)
         *          c.4. 最后反向遍历A序列, 在求序列C索引时, 同样也需要A[i]减去最小值min才能得到正确的C索引, 再把元素A[i]放到正确的B序列上, 返回B序列,
         *               排序完毕
         * B. 时间复杂度:
         *      a. 可以看到, 数组长度n, 最大值k, 初始化C数组遍历一次A数组用了O(n), 对C数组进行累加用了O(k+1), 反向遍历数组A用了O(n), 总花费了O(n+k),
         *         因此, 计数排序的时间复杂度为O(n+k)
         *      b. 之所以计数排序能取得线性级别的时间复杂度, 是因为对元素的取值范围做了一定的限制, 也就是k=O(n). 如果k=n^2, n^3... 那就得不到线性的时间了,
         *         一般地, 在实际工作中, 当k=O(n)时, 一般会采用计数排序, 此时的时间复杂度为O(n)
         *      c. 因此, 计算排序的时间复杂度与k相关, 这样通常在区间端点比较接近时, 复杂度较低. 对于区间较大的, 而待排序数据分布比较均匀时, 可以考虑桶排序
         * C. 空间复杂度:
         *      a. 这里开辟了数组A、数组B长度为n的数组, 用了O(n). 开辟了索引数组C, 长度为k+1的数组, 用了O(k). 因此, 计数排序的空间复杂度为O(n+k)
         * D. 稳定性:
         *      a. 由于最后是反向遍历数组A, 这就能保证了靠后的相等的元素输出到数组B时还是保持靠后的位置, 没有打乱相等元素的相对位置, 因此, 计数排序是稳定的
         *
         */
//        // 遍历数组, 求出最大元素
//        int max = 0;
//        for(int i = 0; i < arr.length; i++){
//            if(arr[i] > max){
//                max = arr[i];
//            }
//        }
//
//        // 创建max+1长度的C数组
//        int[] crr = new int[max+1];
//
//        // 遍历数组, 统计每元素出现的次数
//        for(int i = 0; i < arr.length; i++){
//            int value = arr[i];
//            crr[value] += 1;
//        }
//
//        // 对C数组进行计数累加, 得到B数组的正确索引位置
//        for(int i = 1; i < crr.length; i++){
//            crr[i] = crr[i-1] + crr[i];
//        }
//
//        // 创建n长的最终结果数组B
//        int[] brr = new int[arr.length];
//
//        // 反向遍历数组A, 根据数组C对应的索引存放到数组B对应的位置上
//        for(int i = arr.length-1; i >= 0; i--){
//            int value = arr[i];
//            brr[crr[value]-1] = value;
//            crr[value]--;
//        }
//
//        // 排序完毕, 结果返回
//        return brr;

        // 优化方案: 遍历数组, 求出最大元素以及最小元素
        int max = 0, min = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }
            if(arr[i] < min){
                min = arr[i];
            }
        }

        // 创建max-min+1长度的C数组
        int[] crr = new int[max-min+1];

        // 遍历数组, 统计每元素出现的次数
        for(int i = 0; i < arr.length; i++){
            int value = arr[i];
            crr[value-min] += 1;
        }

        // 对C数组进行计数累加, 得到B数组的正确索引位置
        for(int i = 1; i < crr.length; i++){
            crr[i] = crr[i-1] + crr[i];
        }

        // 创建n长的最终结果数组B
        int[] brr = new int[arr.length];

        // 反向遍历数组A, 根据数组C对应的索引存放到数组B对应的位置上
        for(int i = arr.length-1; i >= 0; i--){
            int value = arr[i];
            brr[crr[value-min]-1] = value;
            crr[value-min]--;
        }

        // 排序完毕, 结果返回
        return brr;
    }

    public static void main(String[] args) {
        int[] arr = {
                9, 8, 10, 4, 7, 2, 5, 5, -1
        };

        CountingSort countingSort = new CountingSort();
        arr = countingSort.countingSort(arr);

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
