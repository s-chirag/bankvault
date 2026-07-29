package io.bankvault.auth.collections;

import java.util.Arrays;

public class RemoveDuplicatesSorted {

//    public int removeDuplicates(int[] nums) {
//
//        int len = nums.length;
//
//        int temp;
//
//
//
//        for(int i=0; i<len; i++){
//            if(nums[i]==nums[i+1]){
//                for(int j=0; j<len; j++){
//                    temp = nums[i+1];
//                    nums[i+1] = nums[i+2];
//                    nums[len-1]=temp;
//
//                }
//                len--;
//            }
//        }
//
//        return 0;
//    }

//    public int removeDuplicates(int[] nums) {
//
//        int len = nums.length;
//
//        if(len==1){
//            return 1;
//        }
//        if(len==0){
//            return 0;
//        }
//        int temp = 0;
//
//        for(int i=0; i<len; i++) {
//
//            if(nums[i+1]<len) {
//                int left = nums[i];
//                int right = nums[i + 1];
//
//                if (left != right) {
//                    temp++;
//                }
//            }
//
//        }
//        System.out.println("temp>>"+ temp);
//        return temp;
//
//
//
//      //  return 0;
//    }


    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;      // genuinely needed here

        int writeIndex = 1;                   // nums[0] is always a keeper

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[writeIndex - 1]) {   // different from the last one we kept
                nums[writeIndex] = nums[i];
                writeIndex++;
            }
        }
        return writeIndex;
    }


    public static void main(String[] args) {
        RemoveDuplicatesSorted r = new RemoveDuplicatesSorted();

        int[] a = {1, 1, 2};
        int n = r.removeDuplicates(a);
        System.out.println(n + " -> " + Arrays.toString(Arrays.copyOf(a, n)));  // 2 -> [1, 2]

        int[] b = {0, 0, 1, 1, 2, 3};
        n = r.removeDuplicates(b);
        System.out.println(n + " -> " + Arrays.toString(Arrays.copyOf(b, n)));  // 4 -> [0, 1, 2, 3]

        int[] c = {1, 2, 3};
        n = r.removeDuplicates(c);
        System.out.println(n + " -> " + Arrays.toString(Arrays.copyOf(c, n)));  // 3 -> [1, 2, 3]

        int[] d = {5, 5, 5};
        n = r.removeDuplicates(d);
        System.out.println(n + " -> " + Arrays.toString(Arrays.copyOf(d, n)));  // 1 -> [5]

        int[] e = {};
        n = r.removeDuplicates(e);
        System.out.println(n + " -> " + Arrays.toString(Arrays.copyOf(e, n)));  // 0 -> []
    }
}
