package Diff;

public class Path {
	private Coord previous;
	private Coord current;
	
	public Path(Coord previous, Coord current) {
		this.setPrevious(previous);
		this.setCurrent(current);
	}

	public Coord getPrevious() {
		return previous;
	}

	public void setPrevious(Coord previous) {
		this.previous = previous;
	}

	public Coord getCurrent() {
		return current;
	}

	public void setCurrent(Coord current) {
		this.current = current;
	}
	
    public String toString() {
    	return this.previous.toString()+"->"+this.current.toString();
    }
}
