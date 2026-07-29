package io.bankvault.auth.dsa;

import java.util.Arrays;
import java.util.HashMap;

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        // your code here

    //    int sum[] = {};
        for (int i = 0; i < nums.length; i++) {

            for (int j = nums.length-1; j >0; j--) {

               int sum = nums[i] + nums[j];

               if(sum==target){
                //   return new int[]{nums[i],nums[j]};
                   return new int[]{i,j};
               }
            }

            }
        return nums;

    }

    public int[] twoSumFast(int[] nums, int target) {
        // your code here
        HashMap inputMap = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            inputMap.put(nums[i],i);

        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if(inputMap.containsKey(complement)){
                return new int[]{i,(int)inputMap.get(complement)};
            }
        }


        throw new IllegalArgumentException();

        }

    public static void main(String[] args) {
        TwoSum solver = new TwoSum();
//        System.out.println(Arrays.toString(solver.twoSum(new int[]{2, 7, 11, 15}, 9)));
//        System.out.println(Arrays.toString(solver.twoSum(new int[]{3, 2, 4}, 6)));
//        System.out.println(Arrays.toString(solver.twoSum(new int[]{3, 3}, 6)));


        System.out.println(Arrays.toString(solver.twoSumFast(new int[]{2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(solver.twoSumFast(new int[]{3, 2, 4}, 6)));
        System.out.println(Arrays.toString(solver.twoSumFast(new int[]{3, 3}, 6)));
    }
}
