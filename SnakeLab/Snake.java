import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Snake extends JPanel implements KeyListener {

	private Body head;
	private boolean isGrowing, isRemoving;

	public Snake() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setSize(800, 800);
		//create the head for this body and give it's top left corner the coordinates (200,200)
		this.head = new Body(null, 200, 200);
		head.setX(200);
		head.setY(200);
		this.setFocusable(true);
		this.addKeyListener(this);

	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		draw(g2);
	}

	/**
	 * Draw every part of the Snake
	 * @param g
	 */
	private void draw(Graphics2D g) {
		if (head == null) {
			return;
		}
		Body runner = head;
		g.setColor(Color.blue);
		while (runner != null) {
			g.fillRect(runner.getX(), runner.getY(), runner.getWidth(), runner.getHeight());
			g.setColor(Color.green);
			runner = runner.getNext();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Moves the character based on the key pressed If the snake is growing/removing
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if (head == null) {
			return;
		} else {
			if (key == KeyEvent.VK_UP) {

				if (isGrowing) {// if snake will grow, add to the block behind the head
					Body temp = new Body(head.getNext(), head.getX(), head.getY());
					head.setY(head.getY() - head.getHeight());// move up one block
					temp.setNext(head.getNext());
					head.setNext(temp);
					isGrowing = false;
				} else {//we want to move every block, not just the head
					Body runner = head;
					Body t1 = new Body(null, runner.getX(), runner.getY());
					runner.setY(runner.getY() - runner.getHeight());// move the head up one block
					runner = runner.getNext();
					move(runner, t1);//move the rest of the snake
				}
			} else if (key == KeyEvent.VK_DOWN) {
				if (isGrowing) {// if snake will grow, add to the block behind the head
					Body temp = new Body(head.getNext(), head.getX(), head.getY());
					head.setY(head.getY() + head.getHeight());
					temp.setNext(head.getNext());
					head.setNext(temp);
					isGrowing = false;
				} else {//we want to move every block, not just the head
					Body runner = head;
					Body t1 = new Body(null, runner.getX(), runner.getY());
					runner.setY(runner.getY() + runner.getHeight());// move the head down one block
					runner = runner.getNext();
					move(runner, t1);//move the rest of the snake
				}
			} else if (key == KeyEvent.VK_RIGHT) {
				if (isGrowing) {// if snake will grow, add to the block behind the head
					Body temp = new Body(head.getNext(), head.getX(), head.getY());
					head.setX(head.getX() + head.getWidth());
					temp.setNext(head.getNext());
					head.setNext(temp);
					isGrowing = false;
				} else {//we want to move every block, not just the head
					Body runner = head;
					Body t1 = new Body(null, runner.getX(), runner.getY());
					runner.setX(runner.getX() + runner.getWidth());// move the head right one block
					runner = runner.getNext();
					move(runner, t1);//move the rest of the snake
				}
			} else if (key == KeyEvent.VK_LEFT) {
				if (isGrowing) {// if snake will grow, add to the block behind the head
					Body temp = new Body(head.getNext(), head.getX(), head.getY());
					head.setX(head.getX() - head.getWidth());
					temp.setNext(head.getNext());
					head.setNext(temp);
					isGrowing = false;
				} else {//we want to move every block, not just the head
					Body runner = head;
					Body t1 = new Body(null, runner.getX(), runner.getY());
					runner.setX(runner.getX() - runner.getWidth());// move the head left one block
					runner = runner.getNext();
					move(runner, t1);//move the rest of the snake
				}
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
			isRemoving = false;//Can't remove and add one after another; you have to choose
		}
		if (key == KeyEvent.VK_E) {// Make the snake lose one block (can we call it shedding skin--more like shedding limbs???)
			isRemoving = true;
			isGrowing = false;//Can't remove and add one after another; you have to choose
		}
	}

	/**
	 * Helper method to move the snake
	 * @param runner: The variable going through the snake's body
	 * @param temp1: The variable holding the space where the moving started
	 */
	private void move(Body runner, Body temp1) {
		if (isRemoving) {// if snake will remove, remove last block
			Body remove = head;//start at the top
			if (remove.getNext() != null) {
				while (remove.getNext().getNext() != null) {//while you are not the second to last body part
					remove = remove.getNext();
				}
				remove.setNext(null);//remove the last body part
				isRemoving = false;
			}
		}

		while (runner != null) {//for normal movement
			Body temp2 = new Body(null, runner.getX(), runner.getY());//temp where the block that is moving will be (like a saved gap)
			//update the body part's coordinates
			runner.setX(temp1.getX());
			runner.setY(temp1.getY());
			temp1 = temp2;
			runner = runner.getNext();
		}
	}

}
