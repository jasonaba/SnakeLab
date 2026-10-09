package SnakeLab;

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
		this.head = new Body(null, 200, 200);
		head.setX(200);
		head.setY(200);
		this.setFocusable(true);
		this.addKeyListener(this);
		
		
		
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		draw(g2);
		System.out.println(isGrowing);
	}
	
	protected void draw(Graphics2D g) {
		if(head== null) {
			return;
		}
		Body runner = head;
		g.setColor(Color.blue);
		while(runner!=null) {
			g.fillRect(runner.getX(), runner.getY(), runner.getWidth(), runner.getHeight());
			g.setColor(Color.green);
			runner = runner.getNext();
		}
	}
	
	private void add() {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Moves the character based on the key pressed
	 * If the snake is growing and going to hit itself
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) {
			if(isGrowing) {//if you are growing
				
				Body temp = new Body(head.getNext(), head.getX(), head.getY());
				head.setY(head.getY()-head.getHeight());
				temp.setNext(head.getNext());
				head.setNext(temp);
				isGrowing = false;
			}
		}
		if(key == KeyEvent.VK_DOWN) {
			if(isGrowing) {//if you are growing
				Body temp = new Body(head.getNext(), head.getX(),head.getY());
				head.setY(head.getY()+head.getHeight());
				temp.setNext(head.getNext());
				head.setNext(temp);
				isGrowing = false;
			}
		}
		if(key==KeyEvent.VK_RIGHT) {
			if(isGrowing) {//if you are growing
				Body temp = new Body(head.getNext(), head.getX(),head.getY());
				head.setX(head.getX()+head.getWidth());
				temp.setNext(head.getNext());
				head.setNext(temp);
				isGrowing = false;
			}
		}
		if(key==KeyEvent.VK_LEFT) {
			if(isGrowing) {//if you are growing
				Body temp = new Body(head.getNext(), head.getX(),head.getY());
				head.setX(head.getX()-head.getWidth());
				temp.setNext(head.getNext());
				isGrowing = false;
			}
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_F)//"Feed" snake --causing it to grow
			isGrowing = true;
		if(key == KeyEvent.VK_E)
			isRemoving = true;
	}
	
	
}
