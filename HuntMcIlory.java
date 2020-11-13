package Diff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;

public class HuntMcIlory {
    private final char oldString[];
    private final char newString[];

    public HuntMcIlory(String a, String b) {
    	// Fill oldString
    	this.oldString = new char[a.length()]; 
    	for (int i = 0; i < a.length(); i++) { 
            this.getOldString()[i] = a.charAt(i); 
        }
    	// Fill newString
    	this.newString = new char[b.length()]; 
    	for (int i = 0; i < b.length(); i++) { 
            this.newString[i] = b.charAt(i); 
        }
    }

    public void longestCommonSubsequence() {
    	
    	// TODO: This should all be correct
    	Hashtable<Character, ArrayList<Integer>> equivalencesTable = new Hashtable<Character, ArrayList<Integer>>();
    	
    	for(int column = 0; column < this.oldString.length; column++) {
    		if (equivalencesTable.get(this.oldString[column]) == null) {
        		ArrayList<Integer> equivalence = new ArrayList<Integer>();
            	for(int row = 0; row < this.newString.length; row++) {
            		if (this.oldString[column] == this.newString[row]) {
            			equivalence.add(row+1);
            		}
            	}
            	equivalencesTable.put(this.oldString[column], equivalence);
    		}
    	}

    	ArrayList<ArrayList <Coord>> equivalences = new ArrayList<ArrayList <Coord>>();
    	for (int column = 0; column < this.oldString.length; column++) {
    		ArrayList<Coord> row = new ArrayList<Coord>();
    		for (int y: equivalencesTable.get(this.oldString[column])) {
    			row.add(new Coord(column+1, y));
    		}
			equivalences.add(row);
    	}
    	
    	int size = Math.max(this.oldString.length, this.newString.length)+1;
    	Coord[] candidates = new Coord[size];
    	candidates[0] = new Coord(0, 0);
    	for (int i = 1; i < candidates.length; i++) {
    		candidates[i] = new Coord(this.oldString.length+1, this.newString.length+1);
    	}
    	
    	Hashtable<Integer, ArrayList<Path>> kTable = new Hashtable<Integer, ArrayList<Path>>();
    	
    	int k = 1;
		Coord[] newCandidates = candidates.clone();
		for (int column = 0; column < equivalences.size(); column++) {
			int startingK = k;
			for (int row = 0; row < equivalences.get(column).size(); row++) {
				if (k < candidates.length && equivalences.get(column).get(row).getY() > candidates[k-1].getY() && equivalences.get(column).get(row).getY() < candidates[k].getY()) {
					// Add to kTable
					ArrayList<Path> toAdd = new ArrayList<Path>();
					if (kTable.get(k) != null) { toAdd = kTable.get(k); kTable.remove(k); toAdd.add(new Path(candidates[k-1], equivalences.get(column).get(row))); } else { toAdd.add(new Path(candidates[k-1], equivalences.get(column).get(row))); }
					kTable.put(k, toAdd);
					
					newCandidates[k] = equivalences.get(column).get(row);
					k++;
					startingK = k;
				} else if (k < candidates.length-1) {
					k++;
					row--;
				} else {
					k = startingK;
				}
			}
			k=1;
			candidates = newCandidates.clone();
		}
		
		ArrayList<Coord> reversed = new ArrayList<Coord>();

		if (kTable.size() != 0) {
			Coord current = kTable.get(kTable.size()).get(kTable.get(kTable.size()).size()-1).getCurrent();
			Coord previous = kTable.get(kTable.size()).get(kTable.get(kTable.size()).size()-1).getPrevious();
			reversed.add(current);
			for (k = kTable.size(); k > 1; k--) {
				reversed.add(previous);
				current = previous;
				for (int i = 0; i < kTable.get(k-1).size(); i++) {
					if(previous.getX() == kTable.get(k-1).get(i).getCurrent().getX() && previous.getY() == kTable.get(k-1).get(i).getCurrent().getY()) {
						previous = kTable.get(k-1).get(i).getPrevious();
					}
				}
			}
		}

		reversed.add(new Coord(0, 0));
		ArrayList<Coord> longest = new ArrayList<Coord>();
		for  (int i = reversed.size()-1; i > -1; i--) {
			longest.add(reversed.get(i));
		}
		longest.add(new Coord(this.oldString.length+1, this.newString.length+1));

		// Prints correctly if array list of coords
		for (int i = 0; i < longest.size()-1; i++) {
			Coord last = longest.get(i);
			Coord next = longest.get(i+1);
    		for(int j = last.getX()+1; j < next.getX(); j++) {
    			System.out.println("- "+oldString[j-1]);
    		}
    		for(int j = last.getY()+1; j < next.getY(); j++) {
    			System.out.println("+ "+newString[j-1]);
    		}
    		if (i < longest.size()-2) {
        		System.out.println("  "+oldString[next.getX()-1]);
    		}
		}
    	return;
    }
    
	public char[] getOldString() {
		return oldString;
	}
	
	public char[] getNewString() {
		return newString;
	}
}
