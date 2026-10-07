package SnakeLab;

import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Snake Lab");
		frame.setSize(800, 800);
		
		GamePanel board = new GamePanel();
		
		frame.add(board);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
