package Diff;

public class Coord {
    private int x;
    private int y;
    
    public Coord(int x, int y) {
    	this.setX(x);
    	this.setY(y);
    }
    
    public String toString() {
    	return "(" + getX() + ", " + getY() + ")";
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
