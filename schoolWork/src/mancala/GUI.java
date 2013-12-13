package mancala;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GUI {
	JButton[] buttons = new JButton[14];
	JFrame frame = new JFrame("Mancala");
	boolean player1turn = true;
	JLabel turn = new JLabel("Player 1's turn");

	public GUI() {
		// JFrame frame = new JFrame("Mancala");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addButtons(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	private void addButtons(Container pane) {

		pane.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton("5");
			// c.fill = GridBagConstraints.VERTICAL;
			buttons[i].addActionListener(new AL());
			c.insets = new Insets(5, 10, 5, 10);
			c.gridheight = 1;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.BOTH;
			c.ipady = 0;
			c.ipadx = 0;
			// c.
			if (i % 7 == 0) {
				buttons[i].setText("0");
				// c.weightx = 1;
				c.gridheight = 2;
				// c.fill = GridBagConstraints.VERTICAL;

				if (i == 0) {
					c.gridx = 0;
					c.gridy = 0;
				} else {
					c.gridx = 7;
					c.gridy = 0;
				}

			} else {
				c.ipady = 10;
				c.ipadx = 3;
				if (i < 7) {
					c.gridx = i;
					c.gridy = 1;
				}
				if (i > 7) {
					c.gridx = 14 - i;
					c.gridy = 0;
				}
			}

			try {
				pane.add(buttons[i], c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridwidth= 8;
		c2.gridx = 0;
		c2.gridy = 2;
		pane.add(turn, c2);

	}

	// 0 is no winner, 1 is player 1, 2 is player 2
	// 3 is a tie game
	public int checkForWinner() {
		for (int i = 0; i < buttons.length; i++) {
			if (i == 0 || i == 7)
				continue;
			if (!buttons[i].getText().equals("0")) {
				return 0;
			}
		}
		int player1 = Integer.parseInt(buttons[7].getText());//far right hole
		int player2 = Integer.parseInt(buttons[0].getText());
		
		if (player2 > player2) {
			System.out.println("Player 2 wins");
			return 2;
		} else if (player2 < player1) {
			System.out.println("Player 1 wins");
			return 1;
		} else {
			System.out.println("Tie Game");
			return 3;
			
		}
	}

	private class AL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton clicked = (JButton) e.getSource();
			int index = 0;
			System.out.println("check " + isBoardBlank(player1turn));
			System.out.println("bool " + player1turn);
			if(isBoardBlank(player1turn)){
				//player1turn = !player1turn;
			}
			for (int i = 0; i < buttons.length; i++) {
				if (clicked.equals(buttons[i])) {
					index = i;
				}

			}
			if (index == 0 || index == 7)
				return;
			System.out.println(index);
			if(player1turn& index>7){
				return;
			}else if(!player1turn & index<7)
				return;
			int beans = Integer.parseInt(clicked.getText());
			if(beans == 0)return;
			clicked.setText("0");
			int nextIndex = index;
			
			for (int i = 0; i < beans; i++) {
				nextIndex++;
				if (nextIndex >= 14)
					nextIndex = 0;

				int nextBeanCount = Integer.parseInt(buttons[nextIndex]
						.getText());
				buttons[nextIndex].setText(Integer.toString(nextBeanCount + 1));
			}
			checkSteal(nextIndex);
			player1turn = !player1turn;
			turn.setText(player1turn? "Player 1's Turn":"Player 2's Turn");
			int winner = checkForWinner();
			if (winner != 0) {
				String message;
				if (winner == 1) {
					message = "Player 1 won.";
				} else if (winner == 2)
					message = "Player 2 won.";
				else
					message = "The game was a tie.";
				message = message + " Do you want to play again?";
				String[] options = { "yes", "no" };
				int playAgain = JOptionPane
						.showOptionDialog(frame, message, "Play Again?",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				if (playAgain == 1) {
					System.out.println("End");
					System.exit(0);
				} else {
					buttons = new JButton[14];
					frame.dispose();
					frame = new JFrame("Manacals");
					addButtons(frame.getContentPane());
					frame.pack();
					frame.setVisible(true);
				}

			}
		}
	}
	public boolean isBoardBlank(boolean player1Turn){
		int shift = (player1Turn)?1:7;
		for(int i = 0; i < 6; i++){
			if(getBean(buttons[i+shift]) == 0)
				return true;
			}
		return false;
	}
	public void checkSteal(int index){
		Main: if(Integer.parseInt(buttons[index].getText()) == 1){
			int mancala1 = getBean(buttons[7]);
			int mancala2 = getBean(buttons[0]);
			if(index == 0 || index == 7)
				break Main;
			
			int capturedBeans = Integer.parseInt(buttons[14 - index].getText());
			buttons[14-index].setText("0");
			buttons[index].setText("0");
			capturedBeans++;
			if(player1turn){
				if(index < 7){
					buttons[7].setText(Integer.toString(capturedBeans + mancala1));
				}
			}else{
				if(index > 7){
					buttons[0].setText(Integer.toString(capturedBeans + mancala2));
				}
			}
			
		}
	}
	public int getBean(JButton b)
	{
		return Integer.parseInt(b.getText());
	}
	public void changeBean(int index, int value){
		//buttons[]
	}
}
