package aPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.JPanel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;




/**
 * In order to create a level, extend this class, declare it final and make it singleton, then 
 * in the constructor, add in declarations for all screen objects, making sure to pass </code> this <code>
 * as a parameter. This will allow the internal lists and displays to show only this level's worth of screen objects.
 * 
 * @author Kiya.Zadeh22
 *
 */
@SuppressWarnings("serial")
public abstract class Level extends JPanel {
	
	/**
	 * After any level is created, its initialization with the level number as a parameter should be placed here
	 * Though the initialize methods for all subclasses are empty, it is needed due to how Java class loading works
	 */
	public static void initialize() {
		
	}
	/**
	 * Threadpool tp manage all audio playing
	 */
	private static ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
	/**
	 * The list of all levels created in the program
	 */
	public static ArrayList<Level> list = new ArrayList<Level>();
	/**
	 * This contains what level is currently active at the time
	 */
	public static int level;
	private boolean isCutscene;
	
	/**
	 * This number should contain what number level this is
	 */
	public final int levelNumber;
	private Image background;
	// Lists that contain the elements of each level. 
	// Some are stored in multiple lists due to being shown and having a loop 
	public ArrayList<Platform> platformList = new ArrayList<Platform>();
	public ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	public ArrayList<Spike> spikeList = new ArrayList<Spike>();
	public ArrayList<Show> showableList = new ArrayList<Show>();
	
	
	public Level(int levelNumber, Image backGround) {
		super();
		background = backGround;
		this.levelNumber = levelNumber;
		
		
		list.add(levelNumber-1, this);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Show levels background image if there is one
		if (background != null) g.drawImage(background, 0, 0, getBackground(), null);
		else {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 5000, 3000);
		}
		if (!Driver.isCutsceneActive()) {
			// Show level and Lives counter
			g.setColor(Color.BLACK);
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
			g.drawString("Level:", 20, 50);
			g.drawString(Integer.toString(level), 110, 50);
			g.drawString("Lives: ", 200, 50);
			g.drawString(Integer.toString(Driver.lives), 285, 50);
		}
		// put graphical stuff here
		g.setColor(Color.RED);
		// Show player sprite
		g.drawImage(Constants.playerSprite1, Driver.xpos - ScrollThread.scrollFactor, 
				Driver.ypos - ScrollThread.scrollFactorVertical, 
				Constants.PLAYER_WIDTH, Constants.PLAYER_WIDTH, Color.WHITE, null);
		
		// Show all game elements
		for (Show s : showableList) {
			s.show(g);
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
	/**
	 * Same as setLevel but only to be used for initialization
	 */
	public static void setInitialLevel() {
		level = 1;
		Driver.activePanel = list.get(0);
		Driver.frame.add(list.get(0));
		Driver.frame.revalidate();
	}
	
	/**
	 * Should contain all level-specific checks to be looped every frame of program excecution
	 */
	public abstract void levelLoop();
	
	/**
	 * Gets the active level, I.E. The level currently showing on the screen
	 * @return The active Level
	 */
	public static Level getactiveLevel() {
		return list.get(level);
	}
	
	// Commented out due to javaFX not working in jar
	/*
	public void playSound(String path) {
		
			Media m = null;
			m = new Media(Driver.documentBase + "/src/audio/" + path);
			MediaPlayer mp;
			try {
			mp = new MediaPlayer(m);
			}
			catch (NullPointerException e) {
				
				System.err.println("failed to load");
				return;
			}
			javafx.application.Platform.runLater(new Runnable() {
				
				public void run() {
					MediaView mv = new MediaView(mp);
					mp.setAutoPlay(false);
					Group g = new Group();
					g.getChildren().add(mv);
					Scene s = new Scene(g);
					
					setScene(s);
					mp.play();
				}
			});
		
	}
	*/
	// commented out due to JMF being painful to work with
	/*
	public void playSoundJMF (String path) throws IOException, NoPlayerException, CannotRealizeException {
		InputStream is = this.getClass().getResourceAsStream(path);
        File temp=File.createTempFile("temp", ".mp3");
        FileOutputStream os = new FileOutputStream(temp);
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        final Player p=Manager.createRealizedPlayer(temp.toURI().toURL());

        p.start();
        while(true){
            if(p.getMediaTime().getSeconds()==p.getDuration().getSeconds()){
                p.stop();
                p.setMediaTime(new Time(0));
                p.start();
            }
        }
	}
	*/
	
	/** 
	 * Plays a audio file from an input stream
	 * @param path The InputStream derived from the path that represents a .mps or .wav file
	 * @throws JavaLayerException If some error occours during the audio playing or the 
	 * path does not contain a readable audio file
	 */
	public void playSoundJLayer(InputStream path) throws JavaLayerException {
		
		AdvancedPlayer AP = new AdvancedPlayer(path, FactoryRegistry.systemRegistry().createAudioDevice());
		AP.setPlayBackListener(new PlaybackListener() {
			public void playbackStarted(PlaybackEvent e) {
				
			}
		});
		Runnable r = new Runnable() {
			@Override public void run() {
				try {
					AP.play();
				} catch (JavaLayerException e) {
					
					e.printStackTrace();
				}
			}
		};
		pool.execute(r);
	}
	
	
	


	/**
	 * Sets if this screen should be a cutscene. 
	 * If yes, then this level will not show the level number or lives counter.
	 * The player will not appear on screen and gravity will not affect the player. 
	 * This should be used for intro screens, cutscenes, and other similar circumstances
	 * @return
	 */
	public boolean isCutscene() {
		return isCutscene;
	}
	
	public void setCutscene(boolean isCutscene) {
		this.isCutscene = isCutscene;
	}
	
	
	/* Commented out to javaFX not working in jar
	private void setScene1(Scene s) {
		javafx.application.Platform.runLater(new Runnable( ) {

			@Override
			public void run() {
				
				setScene(s);
				
			}
			
		});
		
	}
	*/
}
