import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Snake extends JPanel implements KeyListener {

	private Body head;
	private boolean isGrowing, isColliding;
	private int size;

	public Snake() {
		this.setBackground(new Color(234, 241, 248));
		this.setPreferredSize(new Dimension(800, 800));
		// create the head for this body and give it's top left corner the coordinates
		// (200,200)
		this.head = new Body(null, 200, 200);
		size = 1;
		head.setX(200);
		head.setY(200);
		this.setFocusable(true);
		this.addKeyListener(this);
		isColliding = false;
		isGrowing = false;

	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		drawGrid(g2);
		draw(g2);//draw snake
		//draw words on screen when necessary
		g2.setColor(new Color(30, 70, 170));
		g2.setFont(new Font("SansSerif", Font.BOLD, 20));
		g2.drawString("SIZE: " + size, 20, 30);
		if (isColliding) {
		    g2.setColor(new Color(255, 100, 100));
		    g2.setFont(new Font("SansSerif", Font.BOLD, 18));
		    g2.drawString("You can't move there!", 20, 60);
		}

	}
	/*
	 * Draw a grid for the map
	 */
	private void drawGrid(Graphics2D g) {
	    g.setColor(new Color(209, 223, 236));

	    int cellSize = 40;

	    for (int x = 0; x < getWidth(); x += cellSize) {
	        g.drawLine(x, 0, x, getHeight());
	    }

	    for (int y = 0; y < getHeight(); y += cellSize) {
	        g.drawLine(0, y, getWidth(), y);
	    }
	}
	
	/**
	 * Draw every part of the Snake
	 * 
	 * @param g
	 */
	private void draw(Graphics2D g) {
		if (head == null) {
			return;
		}
		// draw head as blue, then the body as green with blue outline
		Body runner = head;
		g.setColor(Color.blue);
		g.fillRoundRect(runner.getX(), runner.getY(), runner.getWidth(), runner.getHeight(), 10, 10);
		runner = runner.getNext();
		while (runner != null) {
			g.setColor(Color.green);
			g.fillRoundRect(runner.getX(), runner.getY(), runner.getWidth(), runner.getHeight(), 10, 10);
			g.setColor(Color.blue);
			g.drawRoundRect(runner.getX(), runner.getY(), runner.getWidth(), runner.getHeight(), 10, 10);
			runner = runner.getNext();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Moves the character based on the key pressed If the snake is growing/removing (WASD or Arrow Keys)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (head == null) {
			return;
		} else {
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				moveSnake(0, -40);
			} else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
				moveSnake(0, 40);
			} else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				moveSnake(40, 0);
			} else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				moveSnake(-40, 0);
			}

		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_F) {// "Feed" snake, causing it to grow
			isGrowing = true;
		}
		if (key == KeyEvent.VK_E) {// Make the snake lose one block (can we call it shedding skin--more like
									// shedding limbs???)
			shed();
		}
		if (key == KeyEvent.VK_R) {
			reverse();
		}
		repaint();
	}

	/**
	 * 
	 * @param dx
	 * @param dy
	 */
	private void moveSnake(int dx, int dy) {
		if(head==null)
			return;
		Body newHead = new Body(head, head.getX() + dx, head.getY() + dy);//creates new head where proposed move should go
		head = newHead;
		if (!isValidMove()) {
			head = head.getNext();//sets head back to original
			isColliding = true;
			isGrowing = false;
			return;
		} else {
			if (!isGrowing) {// removes the tail if it isn't growing
				popTail();
			} else {
				size++;// if it is growing, you keep the tail (so only the head is updated)
				isGrowing = false;
			}
		}
	}

	/*
	 * Checks if the user's proposed move is a valid move meaning the head will not
	 * collide with any part of the body or go out of bounds
	 */
	private boolean isValidMove() {
		if (head.getX()+head.getWidth() > this.getWidth() || head.getX() < 0) {
			return false;
		}
		if (head.getY() + head.getHeight()> this.getHeight() || head.getY() < 0) {
			return false;
		} else {
			Body runner = head;
			if (!isGrowing) {// if it isn't growing, don't check the tail
				while (runner.getNext().getNext() != null) {
					runner = runner.getNext();
					if (head.getX() == runner.getX() && head.getY() == runner.getY()) {// if the x or y of the head and
																						// another body part overlap at
																						// all
						return false;
					}
				}
			} else {// if it is growing
				while (runner.getNext() != null) { // check the tail as well
					runner = runner.getNext();
					if (head.getX() == runner.getX() && head.getY() == runner.getY()) {// if the x or y of the head and
																						// another body part overlap at
																						// all
						return false;
					}
				}
			}
		}
		isColliding = false;
		return true;
	}

	/**
	 * Removes the tail of the snake
	 */
	private void popTail() {
		if (head == null)
			return;
		if (head.getNext() == null)
			return;
		Body runner = head;
		while (runner.getNext().getNext() != null) {
			runner = runner.getNext();
		}
		runner.setNext(null);

	}

	/**
	 * Removes every other body part of the snake (starting at the body part after
	 * the head
	 */
	private void shed() {
		if(head==null)
			return;
		isGrowing = false;
		Body remove = head;// start at the top
		while (remove.getNext() != null && remove.getNext().getNext() != null) {// while you are not the
																				// second to last body part
			remove.setNext(remove.getNext().getNext());
			remove = remove.getNext();
			size--;
		}
		if (remove.getNext() != null) {// if the last one had to be removed (to keep track of the size correctly)
			remove.setNext(null);// remove the last body part
			size--;
		}

	}

	/**
	 * Reverses the orientation of the snake (the head becomes the tail, and all
	 * links to following body parts gets flipped
	 */
	private void reverse() {
		if (head == null)
			return;
		if (head.getNext() == null)
			return;
		Body runner = head.getNext();
		Body temp1 = head;// to store linked list's previous value
		Body temp2 = runner;// to store linked list's next value
		while (runner.getNext() != null) {
			temp2 = runner.getNext();
			runner.setNext(temp1);
			temp1 = runner;
			runner = temp2;
		}
		runner.setNext(temp1);
		head.setNext(null);
		head = runner;
	}

}
