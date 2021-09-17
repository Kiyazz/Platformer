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
 * This class contains all the methods involved in saving and loading the game to a file. 
 * All its methods are static and it cannot be instantized.
 * @author Kiya.Zadeh22
 *
 */
public final class Saving {
	public static PrintWriter writer;
	public static BufferedReader reader;
	private Saving () {}
	private static final Saving s = new Saving();
	
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
	public static void writeToFIle(String s) {
		writer.print(s); 
	}
	public static void writeLine() {
		writer.print("\n");
	}
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
		for (int i = 0; i < Level.list.size(); i++) {
			for (int j = 0; j < Level.list.get(i).enemyList.size(); j++) {
				writer.print(Level.list.get(i).enemyList.get(j).killed);
				writeLine();
			}
		}
		
		
		writer.close();
	}
	public static void load() throws IOException, URISyntaxException {
		initializeReader();
		Level.level = Integer.parseInt(reader.readLine());
		Driver.xpos = Integer.parseInt(reader.readLine());
		Driver.ypos = Integer.parseInt(reader.readLine());
		Driver.lives = Integer.parseInt(reader.readLine());
		ScrollThread.scrollFactor = Integer.parseInt(reader.readLine());
		ScrollThread.scrollFactorVertical = Integer.parseInt(reader.readLine());
		for (int i = 0; i < Enemy.enemyList.size(); i++) {
			Enemy.enemyList.get(i).killed = Boolean.parseBoolean(reader.readLine());
		}
		
		reader.close();
	}
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
