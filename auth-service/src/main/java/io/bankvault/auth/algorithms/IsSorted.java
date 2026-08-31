package io.bankvault.auth.algorithms;

public class IsSorted {

    public boolean isSorted(int[] nums) {

        int len = nums.length;

//        if (len==1) {
//            return true;
//        }
//        if (len==0) {
//            return true;
//        }
//        if (len==2) {
//            if(nums[0]<=nums[1]){
//            return true;
//            }
//        }

        for (int i = 0; i < len; i++) {

            if (i < len - 1) {

                if (nums[i] > nums[i + 1]) {
                    return false;
                }
            }

        }


        return true;
    }

    public static void main(String[] args) {
        IsSorted s = new IsSorted();

        System.out.println(s.isSorted(new int[]{1, 2, 3, 4, 5}));  // true
        System.out.println(s.isSorted(new int[]{1, 3, 2, 4}));     // false
        System.out.println(s.isSorted(new int[]{5, 4, 3}));        // false
        System.out.println(s.isSorted(new int[]{1, 1, 2}));        // true
        System.out.println(s.isSorted(new int[]{7}));              // true
        System.out.println(s.isSorted(new int[]{}));               // true
    }
}
