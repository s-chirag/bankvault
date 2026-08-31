package io.bankvault.auth.algorithms;

public class BinarySearch {

    public static void main(String[] args) {

        int[] arrays = {1,22,43,54,65,76,97};

        System.out.println("this is binary search 76>>"+ binarySearch(arrays, 76));
        System.out.println("this is binary search 22>>"+ binarySearch(arrays, 22));
        System.out.println("this is binary search 97>>"+ binarySearch(arrays, 97));


    }
    public static int binarySearch(int[] numbers, int element){

        int low =0;
        int high = numbers.length-1;

        while(low<=high){
            int middlePosition = (low+high)/2;
            int middleNumber = numbers[middlePosition];

            if(middleNumber==element){
                return middlePosition;
            }

            if(element<middleNumber){
                high = middlePosition-1;
            }
            else{
                low = middlePosition+1;
            }
        }

        // ifno element found
        return -1;

    }
}
