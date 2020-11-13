package Diff;

import java.util.ArrayList;
import java.util.Collections;

public class MyersDiff {
	
    private final char oldString[];
    private final char newString[];

    public MyersDiff(String a, String b) {
    	// Fill oldString
    	this.oldString = new char[a.length()]; 
    	for (int i = 0; i < a.length(); i++) { 
            this.oldString[i] = a.charAt(i); 
        }
    	// Fill newString
    	this.newString = new char[b.length()]; 
    	for (int i = 0; i < b.length(); i++) { 
            this.getNewString()[i] = b.charAt(i); 
        }
    }

    public ArrayList<int[]> shortestEditScript() {
    	
    	// Max length
        int max = oldString.length + newString.length;
        // 
        int[] v = new int[(2 * max) + 1];
        
        ArrayList<int[]> trace = new ArrayList<int[]>();
        
        // We iterate d from 0 to max in the outer loop
        for (int depth = 0; depth <= max; depth++) {
        	
        	trace.add(v.clone());

        	// We iterate k from -d to d in steps of 2 in the inner loop
        	// Since Java doesn't use negative array indices replace k with ((k)+v.length)%v.length
        	for (int k = -depth; k <= +depth; k+=2) {
        		int x;
        		if (k == -depth || (k != depth && v[((k-1)+v.length)%v.length] < v[((k+1)+v.length)%v.length])) {
        			x = v[((k+1)+v.length)%v.length];
        		} else {
        		    x = v[((k-1)+v.length)%v.length] + 1;
        		}
        		
        		int y = x - k;

        		while (x < oldString.length && y < getNewString().length && oldString[x] == getNewString()[y]) {
        			x = x+1;
        			y = y+1;
        		}
        		
        		v[((k)+v.length)%v.length] = x;

        		if (x >= oldString.length && y >= getNewString().length) {
        			return trace;
        		}
        	}
        }
        return null;
    }
    
    
    public Snake backtrack(ArrayList<int[]> trace) {
    	
    	Snake snake = new Snake(new ArrayList<Path>());
    	
    	int depth = trace.size()-1;
    	int x = oldString.length;
		int y = getNewString().length;
		
    	// Iterate through reverse of array list
    	Collections.reverse(trace);
    	for (int[] v : trace) {
    		
    		int k = x - y;
    		int previousK;
    		
    		if (k == -depth || (k != depth && v[((k-1)+v.length)%v.length] < v[((k+1)+v.length)%v.length])) {
    			previousK = k+1;
    		} else {
    			previousK = k-1;
    		}
    		
    	    int previousX = v[(previousK+v.length)%v.length];
    	    int previousY = previousX - previousK;
    	    
    	    while (x > previousX && y > previousY) {
    	    	Coord previous = new Coord(x-1, y-1);
    	    	Coord current = new Coord(x, y);
    	    	Path path = new Path(previous, current);
    	    	snake.AddPath(path);
    	    	x-=1;
        	    y-=1;
    	    }
    	    if (depth > 0) {
    	    	Coord previous = new Coord(previousX, previousY);
    	    	Coord current = new Coord(x, y);
    	    	Path path = new Path(previous, current);
    	    	snake.AddPath(path);
    	    }

    	    x = previousX;
    	    y = previousY;

    	    depth-=1;
    	}
    	return snake;
    }
    
    public void PrintDiff(Snake snake) {
		int oldCounter = 0;
		int newCounter = 0;
    	for (int i = snake.getSnake().size()-1; i > -1; i--)  {
    		boolean added = false;
    		boolean deleted = false;
    		if (snake.getSnake().get(i).getPrevious().getX() == snake.getSnake().get(i).getCurrent().getX()-1) {
    			deleted = true;
    		}
    		if (snake.getSnake().get(i).getPrevious().getY() == snake.getSnake().get(i).getCurrent().getY()-1) {
    			added = true;
    		}
    		if (added & deleted) {
    			System.out.println("  "+this.oldString[oldCounter]);
    			oldCounter++;
    			newCounter++;
    		} else if (added) {
    			System.out.println("+ "+this.getNewString()[newCounter]);
    			newCounter++;
    		} else if (deleted) {
    			System.out.println("- "+this.oldString[oldCounter]);
    			oldCounter++;
    		} else {
    			System.out.println("  "+this.oldString[oldCounter]);
    			oldCounter++;
    			newCounter++;
    		}
    	}
    }

	public char[] getNewString() {
		return newString;
	}
}