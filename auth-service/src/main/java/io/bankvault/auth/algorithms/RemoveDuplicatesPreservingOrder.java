package io.bankvault.auth.algorithms;

public class RemoveDuplicatesPreservingOrder {

//    public static void main(String[] args) {
//
//        RemoveDuplicatesPreservingOrder duplicatesPreservingOrder = new RemoveDuplicatesPreservingOrder();
//        List<String> words = new ArrayList<String>(Arrays.asList("apple", "banana", "apple", "cherry", "banana", "date"));
//        duplicatesPreservingOrder.removeDuplicates(words);
//    }

//    public List<String> removeDuplicates(List<String> words){
//
//        Set<String> wordsSet = new HashSet<>();
//      //  wordsSet.addAll(words);
//
//
//       return words;
//    }

    public static void main(String[] args) {


        RemoveDuplicatesPreservingOrder s = new RemoveDuplicatesPreservingOrder();

//            int[] a = {1, 2, 3, 4, 5};
//            r.reverse(a);
//            System.out.println(Arrays.toString(a));   // expect [5, 4, 3, 2, 1]
//
//            int[] b = {1, 2, 3, 4};
//            r.reverse(b);
//            System.out.println(Arrays.toString(b));   // expect [4, 3, 2, 1]
//
//            int[] c = {7};
//            r.reverse(c);
//            System.out.println(Arrays.toString(c));   // expect [7]
//
//            int[] d = {};
//            r.reverse(d);
//            System.out.println(Arrays.toString(d));   // expect []


        System.out.println(s.secondLargest(new int[]{3, 7, 1, 7, 5}));  // expect 5
        System.out.println(s.secondLargest(new int[]{4, 4, 4}));        // expect MIN
        System.out.println(s.secondLargest(new int[]{2, 1}));           // expect 1
        System.out.println(s.secondLargest(new int[]{5}));              // expect MIN
        System.out.println(s.secondLargest(new int[]{1, 2, 3, 4, 5}));  // expect 4
        System.out.println(s.secondLargest(new int[]{5, 4, 3, 2, 1}));  // expect 4

    }

//    public void reverse(int[] nums){
//
//        int len = nums.length;
//    //    int [] reverseInt = new int[len];
//        System.out.println("this is length " + len);
//        int count  = 0;
//        for(int i=len-1; i>=0; i--){
//
//            for(int j=0; i<len; j++) {
//
//                int temp = nums[i];
//                nums[i] = nums[j];
//                nums[j] = temp;
//
////            reverseInt[count] = nums[i];
////            count ++;
//            }
//        }
//
//    }

    public void reverse(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int len = nums.length;

        int temp=0;
        while (left < right)
        {

                 temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;


            left++;
            right--;
            }
        }

//    public int secondLargest(int[] nums) {
//
//        int len = nums.length;
//        int largestNum = 0;
//        int secondLargestNum = 0;
//
//        for(int i=0; i<len ; i++) {
//
//            for (int j = len-1; j >=0; j--) {
//
//                if (nums[i] < nums[j] || nums[i] == nums[j]) {
//                    largestNum = nums[j];
//                }
//            }
//
//            for (int k = 0; k < len; k++) {
//
//                if (nums[i] < nums[k] && nums[k]!=largestNum) {
//                    secondLargestNum = nums[k];
//                }
//            }
//
//
//        }
//        System.out.println("secondLargestNum  "+secondLargestNum);
//        System.out.println("largestNum  " +largestNum);
//
//        return secondLargestNum;
//    //    return Integer.MIN_VALUE;
//    }

    public int secondLargest(int[] nums) {
        int largest = Integer.MIN_VALUE;
        int second  = Integer.MIN_VALUE;

        for (int n : nums) {
            if (n> largest) {              // n beats the largest
                second = largest;   // old champion gets demoted
                largest = n;
            } else if (n > second && n != largest) {       // n sits between the two
                second = n;
            }
        }
        return second;
    }

}
