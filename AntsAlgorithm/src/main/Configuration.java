package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {
	
	public static int WORLD_HEIGHT;
	public static int WORLD_LENGTH;
	public static int NUMBER_OF_ANTS;
	
	public static String WALLS;
	
	public static void init() {
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			
			input = new FileInputStream("ants.properties");
			
		} catch (FileNotFoundException e) {
			
			OutputStream output = null;
			
			try {
				
				output = new FileOutputStream("ants.properties");
				
				prop.setProperty("WORLD_HEIGHT", "200");
				prop.setProperty("WORLD_LENGTH", "200");
				prop.setProperty("NUMBER_OF_ANTS", "10");
				
				prop.setProperty("WALLS", "");
				
				prop.store(output, null);
				
				input = new FileInputStream("ants.properties");
				
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				
				if (output != null) {
					try {
						output.close();
					} catch (IOException e2) {
						e.printStackTrace();
					}
				}
			}
			
		} finally {
			
			try {
				
				prop.load(input);
				
				Configuration.WORLD_HEIGHT = Integer.parseInt(prop.getProperty("WORLD_HEIGHT"));
				Configuration.WORLD_LENGTH = Integer.parseInt(prop.getProperty("WORLD_LENGTH"));
				Configuration.NUMBER_OF_ANTS = Integer.parseInt(prop.getProperty("NUMBER_OF_ANTS"));
				
				Configuration.WALLS = prop.getProperty("WALLS");
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				
				if (input != null) {
					try {
						input.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			}
		}
	}

}
