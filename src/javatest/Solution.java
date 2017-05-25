package javatest;

import java.util.Arrays;

public class Solution {
	int[] mNums;
    int[] mOrigin;

    public Solution(int[] nums) {
        mOrigin=nums;
        mNums=mOrigin.clone();
        Arrays.sort(mNums);
    }
    
    public void show(int[] arr){
    	for(int i=0;i<arr.length;i++){
    		System.out.print(arr[i]+ " , ");
    	}
		System.out.println();
    }
    
    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return mOrigin;
    }
    
    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        int l = mNums.length;
        for(int i=0;i<l;i++){
            int p = (int) Math.random()*l;
            int tp = mNums[i];
            mNums[i]=mNums[p];
            mNums[p]=tp;
        }
        return mNums;
    }
	
	public static void main(String[] args){
		int[] te = {5,1,2,3,4};
		Solution s = new Solution(te);
		
		s.show(s.mNums);
		s.show(s.mOrigin);
		
		s.mNums[3] =98;
		s.show(s.mNums);
		s.show(s.mOrigin);
	}

}
