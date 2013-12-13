package checkers;

import layout.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Checkers {
	static Piece[] pieces;
	static JPanel main;
	static Board b;

	Checkers() {
		main = new JPanel();
		TableLayout l = new TableLayout();
		main.setLayout(new BorderLayout());

		main.add(b, BorderLayout.CENTER);
		main.setSize(500, 500);
		main.setMinimumSize(new Dimension(500,500));
		
	}

	static void createAndShowGui() {
		JFrame frame = new JFrame("Checkers");
		frame.setContentPane(main);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setSize(500, 500);
	}

	public static void main(String[] args) {
		b = new Board();
		pieces = new Piece[24];
		for (int i = 0; i < 24; i++) {
			boolean isRed = (i < 12);
			int x = i%4 * 2 + ((i/4 % 2 == 0)? 1:0);
			int y = i / 4 + ((i > 11)?2:0);
			pieces[i] = new Piece(isRed, x, y);
			pieces[i].king();
		}
		b.setCheckers(pieces);
		Checkers c = new Checkers();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
}
