package SnakeLab;

import java.awt.Color;

public class Body {
	private int x, y, width, height;//location of body piece
	private Body next; 
	
	//Constructors
	public Body(Body next, int x, int y) {
		width = 40; height = 40;
		this.next = next;
		this.x = x;
		this.y = y;
	}
	
	//Getters and Setters
	
	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Body getNext() {
		return next;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setNext(Body next) {
		this.next = next;
	}
	
	
}
