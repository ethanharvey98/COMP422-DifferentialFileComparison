package Diff;

import java.util.ArrayList;

public class Snake {
    private ArrayList<Path> snake;

    public Snake(ArrayList<Path> snake) {
    	this.snake = snake;
    }
    
    public void AddPath(Path path) {
    	this.snake.add(path);
    }

	public ArrayList<Path> getSnake() {
		return snake;
	}

	public void setSnake(ArrayList<Path> snake) {
		this.snake = snake;
	}

}
