package aPackage;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * This class contains all the static methods involved in saving and loading the game to a file. 
 * All its methods are static and it cannot be instantized.
 * @author Kiya.Zadeh22
 *
 */
@SuppressWarnings("unused")
public final class Saving {
	public static PrintWriter writer;
	public static BufferedReader reader;
	/**
	 * Private constructor to stop instances of the class being created
	 */
	private Saving () {}
	/**
	 * Initialize the writer for output
	 * @throws IOException
	 */
	public static void initializeWriter() throws IOException {
		
		writer = new PrintWriter(new FileWriter(new File("Platformer Save Data.txt")));
	}
	/**
	 * Initializes the reader to receive save data
	 * @throws NullPointerException
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	public static void initializeReader() throws URISyntaxException, IOException, NullPointerException {
		reader = new BufferedReader(new FileReader(new File("Platformer Save data.txt")));
	}
	/**
	 * Shortcut method to write a string to the file
	 * @param s The string to attach to the 
	 */
	public static void writeToFIle(String s) {
		writer.print(s); 
	}
	/**
	 * Shortcut method to add a empty line to the file
	 */
	public static void writeLine() {
		writer.print("\n");
	}
	/**
	 * Reads a line from the save file and returns it
	 * @return The next unread line from the file
	 * @throws IOException
	 */
	public static String readNextLineFromFile() throws IOException {
		return reader.readLine();
		
	}
	/**
	 * Call this method to save all data to the save file
	 * @throws IOException If the writer is unable to work
	 */
	public static void save() throws IOException {
		initializeWriter();
		
		writer.print(Level.level);
		writeLine();
		writer.print(Driver.isCutsceneActive());
		writeLine();
		writer.print(Driver.getPlayerhitBox().getLocation().x);
		writeLine();
		writer.print(Driver.getPlayerhitBox().getLocation().y);
		writeLine();
		writer.print(Driver.lives);
		writeLine();
		writer.print(ScrollThread.scrollFactor);
		writeLine();
		writer.print(ScrollThread.scrollFactorVertical);
		writeLine();
		System.out.println(Level.list.size());
		for (int i = 0; i < Level.list.size(); i++) {
			System.out.println(Level.list.get(i).enemyList.size());
			for (int j = 0; j < Level.list.get(i).enemyList.size(); j++) {
				
				writer.print(Level.list.get(i).enemyList.get(j).killed);
				writeLine();
			}
		}
		
		
		writer.close();
	}
	/**
	 * Loads the save file to the active game
	 * @throws IOException If an IO exception happens
	 * @throws URISyntaxException if a URI syntax exception happens
	 */
	public static void load() throws IOException, URISyntaxException {
		initializeReader();
		int tempLevel = Integer.parseInt(reader.readLine());
		Driver.setCutscene(Boolean.parseBoolean(reader.readLine()));
		Driver.xpos = Integer.parseInt(reader.readLine());
		Driver.ypos = Integer.parseInt(reader.readLine());
		Driver.lives = Integer.parseInt(reader.readLine());
		ScrollThread.scrollFactor = Integer.parseInt(reader.readLine());
		ScrollThread.scrollFactorVertical = Integer.parseInt(reader.readLine());
		for (int i = 0; i < Level.list.size(); i++) {
			for (int j = 0; j < Level.list.get(i).enemyList.size(); j++) {
				Level.list.get(i).enemyList.get(j).killed = Boolean.parseBoolean(reader.readLine());
			}
		}
		Level.setLevel(Driver.xpos, Driver.ypos, tempLevel+1);
		reader.close();
	}
	/**
	 * Method used to read a file at a path. Currently unused
	 * @param path A string representing a file path
	 * @return A File object containing the information at the path
	 * @throws IOException If an exception occurs in file reading 
	 */
	public static File readFile(String path) throws IOException {
		@SuppressWarnings("static-access")
		InputStream in = ClassLoader.getSystemClassLoader().getSystemResourceAsStream(path);
		if (in == null) return null;
		File f = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
		f.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(f)) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			
		}
		return f;
	}
	
	
	
}
