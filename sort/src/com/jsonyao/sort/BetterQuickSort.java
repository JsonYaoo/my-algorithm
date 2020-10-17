package com.jsonyao.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * On：Java Version 1.7
 * 快速排序基本思想:
 *      a. 快速排序使用分治的思想, 通过一趟排序将待排序序列分割成两部分, 其中一部分记录的关键字均比另一部分
 *         记录的关键字小, 之后分别对这两部分记录继续进行排序, 以达到整个序列有序的目的
 * 快速排序的三个步骤:
 *      a. 在待排序序列中, 按照某种方式挑选出一个元素, 作为基准pivot, 进行一趟快速排序
 *      b. 以pivot在序列中的实际位置, 把序列分为两个子序列. 此时, 在pivot左边的元素都比该基准值小, 在pivot
 *         右边的元素都比基准值大
 *      c. 递归地对左右两个子序列进行快速排序, 直到序列为空或者只有一个元素
 * Relation:
 *      a. https://blog.csdn.net/insistgogo/article/details/7785038
 */
public class BetterQuickSort {

    InsertionSort insertionSort = new InsertionSort();

    public int[] baseQuickSort(int[] arr, int left, int right){
        /**
         * Test4 三数取中 + 待排序数组长度小于等于10时, 调用简单插入排序减少递归深度
         * A. 插入排序核心思想:
         *      a. 从第一个元素开始, 认为该元素p已经被排序
         *      b. 取出下一个元素q(当前轮用于比较的元素), 在已经排序的元素序列中从后向前扫描(即扫描有序序列)
         *      c. 如果比较元素q小于已排序元素r1, 需要将元素r1向后移动一个位置(预留位置给元素q)
         *      d. 重复c操作, 直到找到比较元素q大于等于已排序元素rn时，记录rn的下一个位置 或者 根本没找到时记录位置0
         *      e. 将新元素插入到该位置
         *      f. 重复b c d e操作, 直到数组最后一个元素
         * B. 测试结果:
         *          基本数组测试时间: 0ms
         *          基本数组测试: 2 4 5 5 7 8 9 10
         *
         *          随机数组测试时间: 66ms
         *          随机数组测试: -2147237162 -2146773255 -2146535093 -2145636385 -2144516349 -2144098656 -2144040985 -2143281430 -2143082322 -2142962672
         *
         *          升序数组测试时间: 20ms
         *          升序数组测试: -2147237162 -2146773255 -2146535093 -2145636385 -2144516349 -2144098656 -2144040985 -2143281430 -2143082322 -2142962672
         *
         *          降序数组测试时间: 24ms
         *          降序数组测试: -2147237162 -2146773255 -2146535093 -2145636385 -2144516349 -2144098656 -2144040985 -2143281430 -2143082322 -2142962672
         *
         *          重复数组测试时间: 377ms
         *          重复数组测试: 91431429 91431429 91431429 91431429 91431429 91431429 91431429 91431429 91431429 91431429
         * C. 测试数据分析:
         *      c.1. 可以看到再数据量不大的情况下, 如果在长度为10以下时, 通过调用简单插入排序, 理论上是会减少递归深度的, 但事实上却是带来了更高的时间复杂度, 估计是测试的数据量太小
         */
//        if(right - left + 1 < 10){// 长度为10以下时, 使用插入排序提高效率
//            insertionSort.insertionSort(arr);
//            return arr;
//        }

        /**
         * 左右指针法2(推荐, 虽然由于等于pivot时会执行交换所以交换次数比较多, 但是无强顺序性, pivot可以说随便更改, 便于测试, 所以pivot不固定时可以使用):
         * A. 核心思想:
         *      a. 设要排序的数组是A[0]...A[N-1], 首先任意选取一个数据(通常选用数组的第一个数)作为关键数据, 然后将所有比它小的数都放到
         *      它左边, 所有比它大的数都放到它右边, 这个过程成为一趟快速排序.
         *      b. 设置两个变量i、j, 排序开始的时候: i=0, j=N-1
         *      c. 以第一个数组元素作为关键数据, 赋值给key, 即key=A[0]
         *      d. 从j开始向前搜索, 即由后开始向前搜索(j--), 找到第一个小于key的值A[j], 将A[j]和A[i]的值交换(i、j交换的原理是比pivot大的或者等于的放在右边, 小的或者等于的放在左边)
         *      e. 从i开始向后搜索, 即由前开始向后搜索(i++), 找到第一个大于key的值A[i], 将A[i]和A[j]的值交换(i、j交换的原理是比pivot大的或者等于的放在右边, 小的或者等于的放在左边)
         *      f. 重复d和e, 直到i=j:
         *            1) 如果没有找到符合条件的值, 即d中A[j]都大于key, e中A[i]都小于key, 这时只需要j--,i++即可
         *            2) 如果有找到符合条件的值, 进行交换的时候, 值进行改变, i和j的指针位置不变
         *            3) 而当i=j时, 一定正好时i++或者j--完成的时候, 此时循环结束
         *         或者arr[i] = arr[j] = pivot:
         *            1) 这时只需要i继续向后搜索即可
         *      g. 经过n趟的快速排序：
         *            1）左数组只存在一个元素或者没有元素时, 则左数组排序完成
         *            2）右数组只存在一个元素或者没有元素时, 则右数组排序完成
         *            3) 此时, 整个数组排序完成
         *      h. 对比第一种, 由于等于pivot时会打断j或者i的循环, 导致等于pivot的值会进行多次交换(每次交换必定有一个等于pivot可能是i也可能是j), 这增加了不必要的交换次数
         */
        int pivotIndex = getPivotIndex(arr, left, right);
        int pivot = arr[pivotIndex];
        int i = left;
        int j = right;
        int temp;

        while (i < j){
            while (i < j && arr[j] > pivot){
                j--;
            }
            while (i < j && arr[i] < pivot){
                i++;
            }
            if(i < j){
                if(arr[i] == arr[j]){
                    i++;
                }else {
                    temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }

        /**
         * Test5(推荐) 三数取中 + pivot相同元素聚集:
         *      a. 在一次分割结束后, 把与key相等的元素聚在一起, 继续下次分割时, 不用再对与key相等元素进行分割
         *      b. 测试结果:
         *          基本数组测试时间: 0ms
         *          基本数组测试: 2 4 5 5 7 8 9 10
         *
         *          随机数组测试时间: 8ms
         *          随机数组测试: -2147397575 -2147233889 -2146488465 -2146364577 -2145824078 -2145471927 -2145449986 -2145250279 -2144738959 -2144026148
         *
         *          升序数组测试时间: 3ms
         *          升序数组测试: -2147397575 -2147233889 -2146488465 -2146364577 -2145824078 -2145471927 -2145449986 -2145250279 -2144738959 -2144026148
         *
         *          降序数组测试时间: 4ms
         *          降序数组测试: -2147397575 -2147233889 -2146488465 -2146364577 -2145824078 -2145471927 -2145449986 -2145250279 -2144738959 -2144026148
         *
         *          重复数组测试时间: 1ms
         *          重复数组测试: -475070472 -475070472 -475070472 -475070472 -475070472 -475070472 -475070472 -475070472 -475070472 -475070472
         *      c. 测试数据分析:
         *          c.1. 可以看到, 在数组中, 如果有相等的元素, 经过使用相同元素聚集, 可以减少不少冗余的部分, 这点在重复数组中体现特别明显
         */
        int finalPivotIndex = i;// 备份最后基准值的位置
        if(left < right){
            // 聚集左数组相同元素到左数组最右边
            for(int z = left; z < i; z++){
                if(i-1 >= left && arr[z] == pivot){
                    i--;
                    temp = arr[i];
                    arr[i] = arr[z];
                    arr[z] = temp;
                }
            }
            // 聚集右数组相同元素到右数组最左边
            for(int z = right; z > j; z--){
                if(j+1 <= right && arr[z] == pivot){
                    j++;
                    temp = arr[j];
                    arr[j] = arr[z];
                    arr[z] = temp;
                }
            }
        }

        if(i-1 > left){
            arr = baseQuickSort(arr, left, i-1);
        }
        if(j+1 < right){
            arr = baseQuickSort(arr, j+1, right);
        }

        return arr;
    }

    private int getPivotIndex(int[] arr, int left, int right){
        /**
         * Test1 pivot取固定位置:
         *      a. 取最左边的值, 即第一个元素的值作为基准值
         *      b. 测试结果:
         *          基本数组测试时间: 0ms
         *          基本数组测试: 2 4 5 5 7 8 9 10
         *
         *          随机数组测试时间: 6ms
         *          随机数组测试: -2147334390 -2147188304 -2146655504 -2146578971 -2145246934 -2145236440 -2145194193 -2144062332 -2143021100 -2142966168
         *
         *          升序数组测试时间: 149ms
         *          升序数组测试: -2147334390 -2147188304 -2146655504 -2146578971 -2145246934 -2145236440 -2145194193 -2144062332 -2143021100 -2142966168
         *
         *          降序数组测试时间: 39ms
         *          降序数组测试: -2147334390 -2147188304 -2146655504 -2146578971 -2145246934 -2145236440 -2145194193 -2144062332 -2143021100 -2142966168
         *
         *          重复数组测试时间: 143ms
         *          重复数组测试: -1912081884 -1912081884 -1912081884 -1912081884 -1912081884 -1912081884 -1912081884 -1912081884 -1912081884 -1912081884
         *      c. 测试数据分析:
         *          c.1. 基本的快速排序可以选取第一个或最后一个元素作为基准, 但不是一种好方法
         *          c.2. 如果输入序列时随机的, 处理时间还是可以接受的
         *          c.3. 如果数组已经有序时, 此时的分割就是一个非常不好的分割, 因为每次划分只能使待排序序列减一, 此时为最坏情况, 快速排序沦为起泡排序, 时间复杂度为O(n^2)
         *          c.4. 然而输入的数据是有序或部分有序的情况是相当常见的, 因此, 使用第一个元素或者最后一个元素作为基准值是非常糟糕的. 所以为了避免这个情况, 就引入了随机获取基准的方法
         */
//        return left;

        /**
         * Test2 pivot随机选取:
         *      a. 在待排序是部分有序时, 固定选取基准值会使快排效率低下, 要缓解这种情况, 就引入了随机选取基准值, 取待排序序列中任意一个元素作为基准
         *      b. 测试结果:
         *          基本数组测试时间: 1ms
         *          基本数组测试: 4 5 2 5 7 8 9 10
         *
         *          随机数组测试时间: 11ms
         *          随机数组测试: -2144674404 -2145415860 -2145724096 -2146621187 -2146225309 -2145210643 -2144507379 -2146652017 -2143483824 -2109818702
         *
         *          升序数组测试时间: 8ms
         *          升序数组测试: -2144674404 -2145415860 -2145724096 -2146621187 -2146225309 -2145210643 -2144507379 -2146652017 -2143483824 -2140228942
         *
         *          降序数组测试时间: 13ms
         *          降序数组测试: -2146652017 -2146621187 -2146225309 -2145724096 -2145415860 -2145210643 -2144674404 -2144507379 -2143483824 -2142330557
         *
         *          重复数组测试时间: 142ms
         *          重复数组测试: 165912628 165912628 165912628 165912628 165912628 165912628 165912628 165912628 165912628 165912628
         *      c. 测试数据分析:
         *          c.1. 可以看到, 经过多次测试发现, 随机选取基准值会大幅度减少升序、降序数组的时间
         *          c.2. 虽然是随机选取基准值, 只是减少出现不好分割的几率, 但最坏情况时间复杂度还是O(n^2), 所以为了缓解这种情况, 就引入三数取中的方法
         */
//        return (int) (Math.random() * arr.length);

        /**
         * Test3(推荐) pivot三数取基准数:
         *      a. 在选取中轴数时, 可以从由左中右3个中选取, 或者扩大到5个元素甚至更多元素中选取, 一般的会有(2t+1)平均分区法median-of-(2t+1), 三平均分区法为meidan-of-three
         *      b. 这里三数取中可以对待排序序列中low、mid、high三个位置上数据进行排序, 取它们中间的那个数作为基准值, 并把它交换作为数组第一个元素返回
         *      c. 测试结果:
         *          基本数组测试时间: 0ms
         *          基本数组测试: 2 4 5 5 7 8 9 10
         *
         *          随机数组测试时间: 5ms
         *          随机数组测试: -2147297722 -2147265369 -2146922649 -2146278024 -2146192003 -2145899283 -2145293114 -2145025322 -2143122033 -2143091968
         *
         *          升序数组测试时间: 1ms
         *          升序数组测试: -2147297722 -2147265369 -2146922649 -2146278024 -2146192003 -2145899283 -2145293114 -2145025322 -2143122033 -2143091968
         *
         *          降序数组测试时间: 2ms
         *          降序数组测试: -2147297722 -2147265369 -2146922649 -2146278024 -2146192003 -2145899283 -2145293114 -2145025322 -2143122033 -2143091968
         *
         *          重复数组测试时间: 170ms
         *          重复数组测试: -2125292467 -2125292467 -2125292467 -2125292467 -2125292467 -2125292467 -2125292467 -2125292467 -2125292467 -2125292467
         *      d. 测试数据分析:
         *          d.1. 显然, 使用三数取中选取基准值可以同时降低了升序、降序数组的时间, 但仍然处理不了重复数组
         */
        int mid = left + ((right - left) >> 1);
        int temp;

        // 由于是交换到数组第一个元素, 所以从右到左进行判断/交换
        if(arr[right] < arr[left]){// right => left
            temp = arr[right];
            arr[right] = arr[left];
            arr[left] = temp;
        }

        if(arr[right] < arr[mid]){// right => mid
            temp = arr[right];
            arr[right] = arr[mid];
            arr[mid] = temp;
        }

        if(arr[mid] > arr[left]){// mid => left
            temp = arr[mid];
            arr[mid] = arr[left];
            arr[left] = temp;
        }

        return left;
    }

    public static void main(String[] args) {
        BetterQuickSort betterQuickSort = new BetterQuickSort();
        long startTime;
        long endTime;

        /**
         * 基本数组测试
         */
        int[] baseArr = new int[]{
                9, 8, 10, 4, 7, 2, 5, 5
        };

        // 时间打印
        startTime = System.currentTimeMillis();
        baseArr = betterQuickSort.baseQuickSort(baseArr, 0, baseArr.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("*          基本数组测试时间: "+ (endTime - startTime) + "ms");

        // 打印
        System.out.print("*          基本数组测试: ");
        for(int i = 0; i < baseArr.length; i++){
            if(i != baseArr.length - 1){
                System.out.print(baseArr[i] + " ");
            }else {
                System.out.println(baseArr[i]);
            }
        }
        System.out.println("*          ");

        /**
         * 随机数组(1w数据)
         */
        int totalCount = 10000;
        int[] randomArr = new int[totalCount];
        Random random = new Random();
        for(int i = 0; i < randomArr.length; i++){
            randomArr[i] = random.nextInt();
        }

        // 时间打印
        startTime = System.currentTimeMillis();
        randomArr = betterQuickSort.baseQuickSort(randomArr, 0, randomArr.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("*          随机数组测试时间: "+ (endTime - startTime) + "ms");

        // 打印前10个数字
        System.out.print("*          随机数组测试: ");
        for(int i = 0; i < 10; i++){
            if(i != 9){
                System.out.print(randomArr[i] + " ");
            }else {
                System.out.println(randomArr[i]);
            }
        }
        System.out.println("*          ");

        /**
         * 升序数组
         */
        // 时间打印
        startTime = System.currentTimeMillis();
        randomArr = betterQuickSort.baseQuickSort(randomArr, 0, randomArr.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("*          升序数组测试时间: "+ (endTime - startTime) + "ms");

        // 打印前10个数字
        System.out.print("*          升序数组测试: ");
        for(int i = 0; i < 10; i++){
            if(i != 9){
                System.out.print(randomArr[i] + " ");
            }else {
                System.out.println(randomArr[i]);
            }
        }
        System.out.println("*          ");


        /**
         * 降序数组
         */
        Integer[] sortArr = new Integer[totalCount];
        for(int i = 0; i < randomArr.length; i++){
            sortArr[i] = randomArr[i];
        }
        Arrays.sort(sortArr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(o1 > o2){// 默认是o1 < o2时返回-1， 一下同理
                    return -1;
                }
                if(o1 < o2){
                    return 1;
                }
                return 0;
            }
        });
        for(int i = 0; i < sortArr.length; i++){
            randomArr[i] = sortArr[i];
        }

        // 时间打印
        startTime = System.currentTimeMillis();
        randomArr = betterQuickSort.baseQuickSort(randomArr, 0, randomArr.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("*          降序数组测试时间: "+ (endTime - startTime) + "ms");

        // 打印前10个数字
        System.out.print("*          降序数组测试: ");
        for(int i = 0; i < 10; i++){
            if(i != 9){
                System.out.print(randomArr[i] + " ");
            }else {
                System.out.println(randomArr[i]);
            }
        }
        System.out.println("*          ");

        /**
         * 重复数组
         */
        int sameCount = 0;
        int randomNum = random.nextInt();
        for(int i = 0; i < randomArr.length; i++){
            if(sameCount == totalCount / 10){// 长度为totalCount / 10时重新设置重复数组
                randomNum = random.nextInt();
            }
            randomArr[i] = randomNum;
            sameCount++;
        }

        // 时间打印
        startTime = System.currentTimeMillis();
        randomArr = betterQuickSort.baseQuickSort(randomArr, 0, randomArr.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("*          重复数组测试时间: "+ (endTime - startTime) + "ms");

        // 打印前10个数字
        System.out.print("*          重复数组测试: ");
        for(int i = 0; i < 10; i++){
            if(i != 9){
                System.out.print(randomArr[i] + " ");
            }else {
                System.out.println(randomArr[i]);
            }
        }
    }
}
