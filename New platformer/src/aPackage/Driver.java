package aPackage;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
/**
 * This is currently the framework of a platformer video game. It is currently incomplete
 * <p>
 * 1036 lines of code
 * 
 * @version 0.1
 * @author Kiya Zadeh
 */
public final class Driver {
	
	
	/**
	 * This frame is the window used throughout the program
	 * 
	 */
	public static JFrame frame = new JFrame("New Platformer");
	/**
	 * This panel contains all graphics used throughout the program
	 */
	
	// Declare important static variables
	public static Thread loops, screenUpdate;
	public static JPanel activePanel;
	public static volatile int xpos = 60;
	public static volatile int ypos = 500;
	private static int mousexPos;
	private static int mouseyPos;
	private static boolean mouseClicked, spaceClicked, dClicked, aClicked;
	public static boolean isGrounded = true;
	public static boolean isMovingDown = false;
	public static boolean kill, killed;
	public static int lives = 4;
	public static boolean levelChanged = false;
	public static void main(String[] args) {
		
		MouseListener panelMListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseClicked = true;
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				mouseClicked = false;
			}
		};
		
		KeyListener panelKListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE: 
					spaceClicked = true;
					break;
				case KeyEvent.VK_D: 
					dClicked = true;
					break;
				case KeyEvent.VK_A: 
					aClicked = true;
					break;
				default:
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE: 
					spaceClicked = false;
					break;
				case KeyEvent.VK_D: 
					dClicked = false;
					break;
				case KeyEvent.VK_A: 
					aClicked = false;
					break;
				default:
					break;
				}
				
			}
			
		};
		
		
		
		
		
		
		
		
		frame.addMouseListener(panelMListener);
		frame.addKeyListener(panelKListener);
		frame.setBounds(0, 0, 2000, 800);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowEvents());
		frame.setVisible(true);
		frame.setResizable(false);
		
		
		JMenuBar bar = new JMenuBar();
		JMenu saving = new JMenu("Save");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem load = new JMenuItem("Load");
		bar.add(saving);
		saving.add(save);
		saving.add(load);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Saving.save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		load.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Saving.load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.setJMenuBar(bar);
		// Main excecution loop
		
		GravityThread gravity = GravityThread.getInstance();
		gravity.start();
		// ScrollThread scroll = ScrollThread.getInstance();
		// scroll.start();
		Level.initialize();
		frame.add(Level1.L1);
		activePanel = Level.list.get(0);
		
		screenUpdate = new Thread(new Runnable() {
			
				@Override
				public void run() {
					activePanel.repaint();
					while (true) {
						
							Level.getactiveLevel().levelLoop();
							activePanel.repaint();
							activePanel.revalidate();
							
							try {
								Thread.sleep(16);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
					}
				}
		});
		screenUpdate.start();
		loops = new Thread(new Runnable() {
			public void run() {	
				while (true) {
					if (!NPC.speaking) {
						Spike.killConditionAll(Level.list.get(Level.level));
						Enemy.updateAllEnemies(Level.list.get(Level.level));
					}
						
					try {
						Thread.sleep(16);
					} 
					catch (InterruptedException e) {
					}
					
				}
			}
		});
		loops.start();
		while (true) {
			if (!NPC.speaking) {
				// All things that need constant updating in regular loop
				updatePlayerPos(Level.list.get(Level.level));
				checkForDeathByFalling();
				checkForKill();
				ScrollThread.updateScrolling();
			
				
				
				if (levelChanged) {
					frame.remove(Level.list.get(Level.level));
					switch (Level.level) {
					case 1:
						activePanel = Level.list.get(1);
						levelChanged = false;
						
						break;
					}
					frame.add(Level.list.get(Level.level));
					
				}
				
				// Stops excecution to show killed screen
				if (killed) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					killed = false;
				}
				if (lives < 0) {
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					closeGame();
				}
				ypos += GravityThread.playerDownSpeed;
				
				}
				// Set framerate to prevent wasted recorses
			try {
				Thread.sleep(16);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		
	}
	
	public static Point getMouseCoords() {
		return new Point(mousexPos, mouseyPos);
	}
	public static boolean isSpaceClicked() {
		return spaceClicked;
	}
	public static boolean isMousePressed() {
		return mouseClicked;
	}
	public static Rectangle getPlayerhitBox() {
		return new Rectangle(xpos, ypos, Constants.PLAYER_WIDTH, Constants.PLAYER_WIDTH);
	}
	/**
	 * Updates the player x-coordinate and checks for wall collision
	 */
	public static void updatePlayerPos(Level l) {
		boolean wallCollisionLeft = false;
		boolean wallCollisionRight = false;
		for (int i = 0; i < l.platformList.size(); i++) {
			Platform p = l.platformList.get(i);
			if (Math.abs(xpos + Constants.PLAYER_WIDTH - p.getRect().x) < Constants.SPEED && ypos + Constants.PLAYER_WIDTH > p.getRect().y && ypos < p.getRect().y+p.getRect().height && p.isSolid()) {
				wallCollisionLeft = true;
				break;
			}
			else wallCollisionLeft = false;
		}
		for (int i = 0; i < l.platformList.size(); i++) {
			Platform p = l.platformList.get(i);
			if (Math.abs(xpos - (p.getRect().x+p.getRect().width)) < Constants.SPEED && ypos + Constants.PLAYER_WIDTH > p.getRect().y && ypos < p.getRect().y+p.getRect().height && p.isSolid()) {
				wallCollisionRight = true;
				break;
			}
			else wallCollisionRight = false;
		}
		
		if (!wallCollisionRight) {
			if (aClicked) {
				xpos -= Constants.SPEED;
				
			}
			
		}
		if (!wallCollisionLeft) {
			if (dClicked) {
					xpos += Constants.SPEED;
			}
		}
	}
	/**
	 * Is used to kill the player by a call of a method
	 */
	public static void killPlayer() {
		kill = true;
	}
	/**
	 * This method checks if the player has fallen 
	 * out of the screen or into a bottomless pit, killing him if he has.
	 */
	public static void checkForDeathByFalling() {
		if (ypos - ScrollThread.scrollFactorVertical > frame.getBounds().height ) {
			killPlayer();
		}
	}
	/**
	 * This is used to check is the player has died, sending the signals to show the death screen if he has
	 */
	public static void checkForKill() {
		if (kill) {
			lives--;
			xpos = 0;
			ypos = 500;
			ScrollThread.scrollFactor = 0;
			ScrollThread.scrollFactorVertical = 0;
			kill = false;
			killed = true;
			
		}
	}
	/**
	 * This method is used to close the game, by doing cleanup on all objects then calling <code> System.exit
	 */
	public static void closeGame() {
		try {
			if (Saving.writer != null)
			Saving.writer.close();
			} catch (NullPointerException e3) {
				e3.printStackTrace();
			}
			try {
				if (Saving.reader != null)
				Saving.reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			catch (NullPointerException e4) {
				e4.printStackTrace();
			}
			System.exit(0);
	}

}
