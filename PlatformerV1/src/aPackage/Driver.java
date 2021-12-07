package aPackage;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;




/**
 * This is currently the framework of a platformer video game. It is currently incomplete
 * <p>
 * 1036 lines of code
 * 
 * <p>
 * 
 * 
 * 
 * @version 1.0
 * @author Kiya Zadeh
 */
public class Driver {
	
	
	/**
	 * This frame is the window used throughout the program
	 * 
	 */
	public static final JFrame frame = new JFrame("New Platformer");
	
	
	// Declare important static variables
	/**
	 * This is a thread to take some pressure off the main thread
	 */
	public static Thread loops, screenUpdate;
	/**
	 * This panel contains all graphics used throughout the program
	 */
	public static Level activePanel;
	/**
	 * X position of the player
	 */
	public static volatile int xpos = 70;
	/**
	 * Y position of the player
	 */
	public static volatile int ypos = 450;
	/**
	 * X position of the last mouse click
	 */
	private static int mousexPos;
	/**
	 * Y position of the last mouse click
	 */
	private static int mouseyPos;
	/**
	 * Flag for if the mouse is being clicked
	 */
	protected static boolean mouseClicked;
	/**
	 * Should indicate if there is a cutscene active, 
	 * If yes, disables input and player death until turned false
	 */
	private static volatile boolean cutscene = false;

	// variables for if the given keyboard keys are being clicked
	protected static boolean spaceClicked, dClicked, aClicked;



	public static boolean isGrounded = true;
	public static boolean isMovingDown = false;
	public static boolean kill, killed;
	public static int lives = 4;
	public static boolean levelChanged = false;
	
	public static String documentBase;
	
	/**
	 * This constant should track how many levels have been created
	 * Update value as more levels are made
	 */
	public static final int CREATEDLEVELS = 3;
	
	
	
	
	// Commented out due to javaFX not working in jar
	/*
	static Application a = new Application() {

		@Override
		public void start(Stage primaryStage) throws Exception {
			primaryStage.close();
			
			
		}
		
	};
	*/
	
	
	
	
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) {
		// Set up an error log file so that error messages can be read when exported
		
		// Commented out due to javaFX not working in jar
		// documentBase = a.getHostServices().getDocumentBase();
		PrintStream ps = null;
		try {
			ps = new PrintStream(new File("Error log"));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.setErr(ps);
		System.setOut(ps);
		
		
		
		
		
		
		
		
		
		// initialize the window 
		frame.setBounds(0, 0, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowEvents());
		frame.setVisible(true);
		frame.setResizable(false);
		// add listeners to the windows
		frame.addMouseListener(new MouseListenerFrame());
		frame.addKeyListener(new KeyListenerFrame());
		
		// Set up a menu bar
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
		Level.initialize();
		
		
		IntroPanel.initialize();
		Level1.initialize();
		Level2.initialize();
		
		
		System.out.println(Level.list.size());
		
		// Start other execution threads
		
		// ScrollThread scroll = ScrollThread.getInstance();
		// scroll.start();
		
		
		// Set the level to level 1
		
		
		Level.setLevel(0, 500, 1);
		
		
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
		GravityThread.getInstance().start();
		// Main excecution loop
		while (true) {
			if (!NPC.speaking && !isCutsceneActive()) {
				// All things that need constant updating in regular loop
				updatePlayerPos(Level.list.get(Level.level));
				checkForDeathByFalling();
				checkForKill();
				ScrollThread.updateScrolling();
				
			
				// Stops excecution to show killed screen
				if (killed) {
					if (lives < 0) {
						Driver.setCutscene(true);
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						closeGame();
					}
					else {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						killed = false;
					}
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
	/**
	 * Get Coordinates of last mouse click
	 * @return A Point representing a location where the couse was clicked
	 */
	public static Point getMouseCoords() {
		return new Point(mousexPos, mouseyPos);
	}
	/**
	 * Gets if the space bar is pressed
	 * @return is space clicked
	 */
	public static boolean isSpaceClicked() {
		return spaceClicked;
	}
	/**
	 * Gets if the mouse left button is clicked
	 * @return true if the mouse left button is 
	 * clicked and the main window is enabled, false otherwise
	 */
	public static boolean isMousePressed() {
		return mouseClicked;
	}
	/** 
	 * Gets a Rectangle representing the players hitbox, the rectangle where the player interacts with game objects
	 * @return The hitbox
	 */
	public static Rectangle getPlayerhitBox() {
		return new Rectangle(xpos, ypos, Constants.PLAYER_WIDTH, Constants.PLAYER_WIDTH);
	}
	/**
	 * Updates the player x-coordinate and checks for wall and roof collision
	 * @param l The Level that should be collision and coordinate checked for
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
	 * This is used to check is the player has died, 
	 * sending the signals to show the death screen if it has
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
	 * This method is used to close the game, 
	 * by doing cleanup on all objects then calling <code> System.exit
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
	/** 
	 * Gets if a cutscene is active
	 * @return is a cutscene active
	 */
	public static boolean isCutsceneActive() {
		return cutscene;
	};
	/**
	 * Sets the programs cutscene state. In a cutscene, player movement, 
	 * enemy movement, and excecution checks are disabled, but screen updates are not.
	 * @param cutscene A boolean to set the cutscene mode
	 */
	public static void setCutscene(boolean cutscene) {
		Driver.cutscene = cutscene;
	}

	

}

/**
 * Keylistener implementation used for the main frame
 * @author Kiya.Zadeh22
 *
 */
class KeyListenerFrame implements KeyListener {
	KeyListenerFrame(){};
		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE: 
				Driver.spaceClicked = true;
				break;
			case KeyEvent.VK_D: 
				Driver.dClicked = true;
				break;
			case KeyEvent.VK_A: 
				Driver.aClicked = true;
				break;
			default:
				break;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE: 
				Driver.spaceClicked = false;
				break;
			case KeyEvent.VK_D: 
				Driver.dClicked = false;
				break;
			case KeyEvent.VK_A: 
				Driver.aClicked = false;
				break;
			default:
				break;
			}
		}
	
}
/**
 * MouseListener implementation used in the main frame
 * @author Kiya.Zadeh22
 *
 */
class MouseListenerFrame implements MouseListener {
	public MouseListenerFrame() {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Driver.mouseClicked = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		Driver.mouseClicked = false;
	}
}
