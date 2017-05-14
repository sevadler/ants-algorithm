package main;

import main.misc.Configuration;

public class Main{
    
    public static World world;
   
    public static void main(String[] args) throws InterruptedException {
    	Configuration.init();
    	Thread.sleep(500L);
        world = new main.World(Configuration.WORLD_HEIGHT, Configuration.WORLD_LENGTH);
        world.initalize();
        Thread updating = new Thread(new Update());
        updating.start();
        world.setVisible(true);
    }
               
}

