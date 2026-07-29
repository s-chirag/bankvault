package io.bankvault.auth.collections;

public class RecursiveSum {

    public int sum(int[] nums, int index) {


        if(nums.length==0){
            return 0;
        }
        if(nums.length==1){
            return nums[0];
        }

     //   return 1;//sum(nums[index], index+1);

//        if(index==nums.length){
//            return 1;
//        }

        return 0;
    }

    public static void main(String[] args) {
        RecursiveSum r = new RecursiveSum();

        System.out.println(r.sum(new int[]{1, 2, 3, 4}, 0));   // expect 10
        System.out.println(r.sum(new int[]{5}, 0));            // expect 5
        System.out.println(r.sum(new int[]{}, 0));             // expect 0
        System.out.println(r.sum(new int[]{-1, 1, -1, 1}, 0)); // expect 0
        System.out.println(r.sum(new int[]{10, 20, 30}, 0));   // expect 60
    }
}
