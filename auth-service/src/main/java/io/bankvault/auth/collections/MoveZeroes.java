package io.bankvault.auth.collections;


import java.util.Arrays;

public class MoveZeroes {

    //    public void moveZeroes(int[] nums) {
//
//        int len = nums.length;
//
//        for(int i=0; i<len; i++){
//
//            if(nums[len-1]==0){
//                len--;
//            }
//            if(nums[i]==0){
//                int temp = nums[len-1];
//                nums[len-1]=nums[i];
//                nums[i]=temp;
//                len--;
//            }
//
//        }
//
//    }
    public void moveZeroes(int[] nums) {
        int insertPos = 0;

        for (int n : nums) {
            if (n != 0) {                    // n is not zero
                nums[insertPos] = n;
                insertPos++;
            }
        }

        while (insertPos < nums.length) {
            nums[insertPos] = 0;
            insertPos++;
        }
    }

    public static void main(String[] args) {
        MoveZeroes m = new MoveZeroes();

        int[] a = {0, 1, 0, 3, 12};
        m.moveZeroes(a);
        System.out.println(Arrays.toString(a));   // [1, 3, 12, 0, 0]

        int[] b = {0, 0, 1};
        m.moveZeroes(b);
        System.out.println(Arrays.toString(b));   // [1, 0, 0]

        int[] c = {1, 2, 3};
        m.moveZeroes(c);
        System.out.println(Arrays.toString(c));   // [1, 2, 3]

        int[] d = {0, 0};
        m.moveZeroes(d);
        System.out.println(Arrays.toString(d));   // [0, 0]
    }
}