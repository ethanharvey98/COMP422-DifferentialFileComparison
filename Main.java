package Diff;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
    	// abcabba 
    	// cbabac

    	String a = "abcabba";
    	String b = "cbabac";
    	
    	System.out.println("Myers Diff");
    	long start = System.currentTimeMillis();
    	MyersDiff m = new MyersDiff(a, b);
    	m.PrintDiff(m.backtrack(m.shortestEditScript()));
    	long end = System.currentTimeMillis();
    	long elapsedTime1 = end - start;
    	System.out.println();
    	
    	System.out.println("Hunt McIlory");
    	start = System.currentTimeMillis();
    	HuntMcIlory h = new HuntMcIlory(a, b);
    	h.longestCommonSubsequence();
    	end = System.currentTimeMillis();
    	long elapsedTime2 = end - start;
    	System.out.println();
    	System.out.println("Myers Diff: "+elapsedTime1+"  Hunt McIlory: "+elapsedTime2);
    }
}
