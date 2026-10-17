import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Snake Lab");
		frame.setSize(800, 800);
		
		Snake board = new Snake();
		
		frame.add(board);
		frame.setLocationRelativeTo(null);
		frame.pack();//to make frame fit an 800x800 panel
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
