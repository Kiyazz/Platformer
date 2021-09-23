package aPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


/**
 * In order to create a level, extend this class, declare it final and make it singleton, then 
 * in the constructor, add in declarations for all screen objects, making sure to pass </code> this <code>
 * as a parameter. This will allow the internal lists and displays to show only this level's worth of screen objects.
 * 
 * @author Kiya.Zadeh22
 *
 */
@SuppressWarnings("serial")
public abstract class Level extends JFXPanel {
	/**
	 * After any level is created, its initialization with the level number as a parameter should be placed here
	 */
	public static void initialize() {
		
			IntroPanel.ip = new IntroPanel(1);
			Level1.L1 = new Level1(2);
			
		
	}
	
	
	public static ArrayList<Level> list = new ArrayList<Level>();
	/**
	 * This contains what level is currently active at the time
	 */
	
	public static int level;
	
	/**
	 * This number should contain what number level this is
	 */
	public int levelNumber;
	
	public ArrayList<Platform> platformList = new ArrayList<Platform>();
	public ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	public ArrayList<Spike> spikeList = new ArrayList<Spike>();
	
	
	public Level(int levelNumber) {
		super();
		this.levelNumber = levelNumber;
		list.add(levelNumber-1, this);
		
	}
	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 5000, 3000);
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, 30, 30));
		g.drawString("Level:", 20, 50);
		g.drawString(Integer.toString(level+1), 100, 50);
		g.drawString("Lives: ", 400, 50);
		g.drawString(Integer.toString(Driver.lives), 485, 50);
		
		
		// put graphical stuff here
		g.setColor(Color.RED);
		// Show player sprite
		g.drawImage(Constants.playerSprite1, Driver.xpos - ScrollThread.scrollFactor, Driver.ypos - ScrollThread.scrollFactorVertical, Constants.PLAYER_WIDTH, Constants.PLAYER_WIDTH, Color.WHITE, null);

		for (int i = 0; i < platformList.size(); i++) {
			Platform p = platformList.get(i);
			Rectangle r = p.getRect();
			g.setColor(p.getColor());
			g.fillRect(r.x - ScrollThread.scrollFactor, r.y - ScrollThread.scrollFactorVertical, r.width, r.height);
			
		}
		// Shows all created spikes
		g.setColor(Color.RED);
		for (int i = 0; i < spikeList.size(); i++) {
			Polygon p = new Polygon(spikeList.get(i).getPolygon().xpoints, spikeList.get(i).getPolygon().ypoints, spikeList.get(i).getPolygon().npoints);
			p.translate(-ScrollThread.scrollFactor, -ScrollThread.scrollFactorVertical);
			g.fillPolygon(p);
		}
		// show all created enemies
		for (int i = 0; i < enemyList.size(); i++) {
			Enemy e = enemyList.get(i);
			Rectangle r = e.getHitBox();
			if (!e.killed) {
				g.drawImage(e.sprite, r.x - ScrollThread.scrollFactor, r.y - ScrollThread.scrollFactorVertical, r.width, r.height, Color.WHITE, null);
			}
		}
		
		if (Driver.killed) Panels.showKilledScreen(g);
	}
	/**
	 * This method is used to set the player to this level
	 * @param x The x coordinate the player should start at
	 * @param y The y coordinate the player should start in
	 */
	public void setLevel (int x, int y) {
		int oldLevel = level;
		Driver.activePanel = this;
		level = this.levelNumber-1;
		Driver.xpos = x;
		Driver.ypos = y;
		ScrollThread.scrollFactor = 0;
		ScrollThread.scrollFactorVertical = 0;
		Driver.levelChanged = true;
		Driver.frame.remove(list.get(oldLevel));
		Driver.activePanel = Level.list.get(Level.level);
		Driver.frame.add(list.get(Level.level));
		Driver.frame.revalidate();
		
	}
	/**
	 * 
	 * @param x The x coordinate the player should be set to
	 * @param y The y coordinate the player should be set to
	 * @param levelID The level number to set
	 */
	public static void setLevel(int x, int y, int levelID) {
		int oldLevel = level;
		Driver.activePanel = list.get(levelID-1);
		level = levelID-1;
		Driver.xpos = x;
		Driver.ypos = y;
		ScrollThread.scrollFactor = 0;
		ScrollThread.scrollFactorVertical = 0;
		
		
		Driver.frame.remove(list.get(oldLevel));
		Driver.activePanel = Level.list.get(Level.level);
		Driver.frame.add(list.get(Level.level));
		Driver.frame.revalidate();
		
		
		
	}
	public abstract void levelLoop();
	public static Level getactiveLevel() {
		return list.get(level);
	}
	
	
	public void playSound(String path) {
		
			
			Media m = null;
			
			m = new Media(Driver.documentBase + "/src/audio/" + path);
			MediaPlayer mp = null;
			try {
			mp = new MediaPlayer(m);
			}
			catch (NullPointerException e) {
				
				System.err.println("failed to load");
				return;
			}
			
			MediaView mv = new MediaView(mp);
			mp.setAutoPlay(false);
			Group g = new Group();
			g.getChildren().add(mv);
			Scene s = new Scene(g);
			setScene1(s);
			mp.play();
			
		
	}
	
	public void playSound(File f) {
		
		
			
				Media m = new Media(f.getPath());
				MediaPlayer mp = new MediaPlayer(m);
				MediaView mv = new MediaView(mp);
				mp.setAutoPlay(false);
				Group g = new Group();
				g.getChildren().add(mv);
				Scene s = new Scene(g);
				setScene1(s);
				mp.play();
			
	}
	
	
	
	private void setScene1(Scene s) {
		javafx.application.Platform.runLater(new Runnable( ) {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				setScene(s);
				
			}
			
		});
		
	}
	
	
	
	
	
}
