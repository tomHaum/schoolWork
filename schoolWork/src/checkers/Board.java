package checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Board extends JPanel {
	private boolean redTurn = true;
	private boolean chaining = false;

	private Piece[] checkers;
	// private Point highlight = new Point(-1, -1);
	private Piece selected;
	private Piece chainer = null;

	Board() {
		this.setSize(100, 100);
		this.setBackground(Color.YELLOW);
		this.setOpaque(true);
		addMouseListener(new MListener());
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (i % 2 == 0) {
					if (j % 2 == 0)
						g2.setColor(Color.BLUE);
					else
						g2.setColor(Color.GREEN);
				} else {
					if (j % 2 == 0)
						g2.setColor(Color.GREEN);
					else
						g2.setColor(Color.BLUE);
				}
				g2.fillRect(i * 50, j * 50, 50, 50);
			}
		}
		for (Piece p : checkers) {
			if (p != null) {
				if (p.isRed)
					g.setColor(Color.RED);
				else
					g2.setColor(Color.BLACK);
				int x = p.getX() * 50 + 13;
				int y = p.getY() * 50 + 13;
				g2.fillOval(x, y, 25, 25);
				if(p.isKing){
					g2.setColor(Color.BLUE);
					g2.drawString("k", x + 5, y + 5);
				}
			}
			// System.out.println(p.cord.getX() + " " + p.cord.getY());
		}
		if (selected != null) {
			g2.setColor(Color.RED);
			g2.drawRect((int) selected.getX() * 50, (int) selected.getY() * 50,
					50, 50);
		}
		g2.setColor(Color.MAGENTA);
		g2.fillRect(400, 0, 50, 400);
		g2.fillRect(0, 400, 450, 50);
		String turn = (redTurn) ? "Red's Turn" : "Black's Turn";
		g2.setColor((redTurn) ? Color.RED : Color.BLACK);
		g2.setFont(new Font("Serif", Font.BOLD, 20));
		g2.drawString(turn, 150, 420);

	}

	void drawPiece(Piece p, int x, int y, Graphics2D g) {

	}

	void setCheckers(Piece[] p) {
		this.checkers = p;
	}

	private class MListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			repaint();
			int x = e.getX() / 50;
			int y = e.getY() / 50;
			// moving/jumping

			movePiece(selected, x, y);

			// selecting a piece
			if (hasPiece(x, y)) {
				if (pieceAt(x, y).isRed == redTurn) {

					selectPiece(x, y);
				}
			} else {
				deselect();
			}

			System.out.println(x + " " + y);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	public void movePiece(Piece p, int x, int y) {
		boolean validMove = false;
		boolean jumped = false;

		if (chainer != null) {
			if (p != null) {
				if (!p.equalz(chainer)) {
					return;
				}
			}
		}
		if (p == null)
			return;
		if (!hasPiece(x, y)) {
			// for a move
			if (!canJump(p)) {
				if (Math.abs(p.getX() - x) == 1) {
					if (p.isRed || p.isKing) {
						if (p.getY() - y == -1) {
							validMove = true;
						}
					} else {
						if (p.getY() - y == 1) {
							validMove = true;
						}
					}

				}
			}

			if (canJump(redTurn))
				validMove = false;
			// for a jump
			if (p.isRed || p.isKing) {
				Piece temp = pieceAt(p.getX() - 1, p.getY() + 1);
				if (temp != null) {
					if (temp.isRed != p.isRed) {
						if (p.getX() - 2 > 7 || p.getY() + 2 > 7)
							return;
						temp = pieceAt(p.getX() - 2, p.getY() + 2);
						if (temp == null && p.getX() - 2 == x
								&& p.getY() + 2 == y) {
							validMove = true;
							jumped = true;
							removePiece(p.getX() - 1, p.getY() + 1);
						}
					}
				}
				temp = pieceAt(p.getX() + 1, p.getY() + 1);
				if (temp != null) {
					if (temp.isRed != p.isRed) {
						temp = pieceAt(p.getX() + 2, p.getY() + 2);
						if (p.getX() + 2 > 7 || p.getY() + 2 > 7)
							return;
						if (temp == null && p.getX() + 2 == x
								&& p.getY() + 2 == y) {
							validMove = true;
							jumped = true;
							removePiece(p.getX() + 1, p.getY() + 1);
						}
					}
				}
			} else {
				Piece temp = pieceAt(p.getX() - 1, p.getY() - 1);
				if (temp != null) {
					if (temp.isRed != p.isRed) {
						temp = pieceAt(p.getX() - 2, p.getY() - 2);
						if (p.getX() - 2 > 7 || p.getY() - 2 > 7)
							return;
						if (temp == null && p.getX() - 2 == x
								&& p.getY() - 2 == y) {
							validMove = true;
							jumped = true;
							removePiece(p.getX() - 1, p.getY() - 1);
						}
					}
				}
				temp = pieceAt(p.getX() + 1, p.getY() - 1);
				if (temp != null) {
					if (temp.isRed != p.isRed) {
						temp = pieceAt(p.getX() + 2, p.getY() - 2);
						if (p.getX() + 2 > 7 || p.getY() - 2 > 7)
							return;
						if (temp == null && p.getX() + 2 == x
								&& p.getY() - 2 == y) {
							validMove = true;
							jumped = true;
							removePiece(p.getX() + 1, p.getY() - 1);
						}
					}
				}

			}
		}

		if (x > 7 || x < 0 || y < 0 || y > 7) {
			validMove = false;
		}
		System.out.println(validMove);
		//
		if (validMove) {
			for (int i = 0; i < checkers.length; i++) {
				// System.out.println("Looking for piece");
				if (p != null && checkers[i] != null) {
					if (checkers[i].equalz(p)) {
						// System.out.println("Found Piece");
						checkers[i] = p.move(x, y);
						if(checkers[i].isRed){
							if(checkers[i].getY() == 7)checkers[i].king();
						
						}else{
							if(checkers[i].getY() == 0)checkers[i].king();
						}
						if (canJump(checkers[i])) {
							if (jumped) {
								chainer = checkers[i];
							}
						} else {
							deselect();
							redTurn = !redTurn;
						}

					}
				}
			}
		}
	}

	private void removePiece(int x, int y) {
		for (int i = 0; i < checkers.length; i++) {
			if (checkers[i] != null) {
				Piece temp = pieceAt(x, y);
				if (temp != null) {
					if (checkers[i].equalz(temp)) {
						checkers[i] = null;
					}
				}
			}
		}

	}

	boolean canJump(Piece p) {
		int x = p.getX();
		int y = p.getY();
		if (p.isRed || p.isKing) {
			Piece temp = pieceAt(x - 1, y + 1);
			if (temp != null) {
				if (temp.isRed != p.isRed) {
					if (temp.getX() - 1 > 7 || temp.getY() + 1 > 7 || temp.getX() - 1 < 0 || temp.getY() + 1 < 0)
						return false;
					temp = pieceAt(x - 2, y + 2);

					if (temp == null) {

						return true;
					}
				}
			}
			temp = pieceAt(x + 1, y + 1);
			if (temp != null) {
				if (temp.isRed != p.isRed) {
					if (temp.getX() + 1 > 7 || temp.getY() + 1 > 7 || temp.getX() + 1 < 0 || temp.getY() + 1 < 0)
						return false;

					temp = pieceAt(x + 2, y + 2);

					if (temp == null) {
						return true;
					}
				}

			}
		} else {
			Piece temp = pieceAt(x - 1, y - 1);
			if (temp != null) {
				if (temp.isRed != p.isRed) {
					if (temp.getX() - 1 > 7 || temp.getY() - 1 > 7 || temp.getX() - 1 < 0 || temp.getY() - 1 < 0)
						return false;
					temp = pieceAt(x - 2, y - 2);
					if (temp == null) {
						return true;
					}
				}
			}
			temp = pieceAt(x + 1, y - 1);
			if (temp != null) {
				if (temp.isRed != p.isRed) {
					if (temp.getX() + 1 > 7 || temp.getY() - 1 > 7 || temp.getX() + 1 < 0 || temp.getY() - 1 < 0)
						return false;
					temp = pieceAt(x + 2, y - 2);
					if (temp == null) {
						return true;
					}
				}
			}
		}

		return false;
	}

	boolean canJump(boolean isRed) {
		for (Piece p : checkers) {
			if (p != null) {
				if (p.isRed == isRed) {
					if (canJump(p)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	boolean canMove(Piece p) {
		int x = p.getX();
		int y = p.getY();
		if (p.isRed || p.isKing) {
			if (pieceAt(x - 1, y + 1) == null || pieceAt(x + 1, y + 1) == null) {
				return false;
			}
		} else {
			if (pieceAt(x - 1, y - 1) == null || pieceAt(x + 1, y - 1) == null) {
				return false;
			}
		}
		return true;
	}

	public void deselect() {
		selected = null;
		repaint();
	}

	public Piece pieceAt(int x, int y) {
		for (Piece p : checkers) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y) {
					return p;
				}
			}
		}
		return null;
	}

	public void selectPiece(int x, int y) {
		// highlight = new Point(x, y);
		if (chaining)
			return;
		selected = pieceAt(x, y);
		System.out.println("this move can jump = " + canJump(selected));
		repaint();
	}

	public boolean hasPiece(int x, int y) {
		return pieceAt(x, y) != null;
	}
}
