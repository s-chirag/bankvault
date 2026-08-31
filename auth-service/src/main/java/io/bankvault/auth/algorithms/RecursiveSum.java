package io.bankvault.auth.algorithms;

public class RecursiveSum {

    public int sum(int[] nums, int index) {
        if (index == nums.length) {     // base case: walked off the end
            return 0;
        }
        return nums[index] + sum(nums, index + 1);
    }


    public int countChar(String s, char target) {

        int count = 0;
        if(s.length()==count){
            return 0;
        }

//return

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
