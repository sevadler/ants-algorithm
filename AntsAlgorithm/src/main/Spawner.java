package main;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Spawner implements Runnable {
        
    @Override
    public void run() {
        while(Main.world.list.size() < 1) {
            Main.world.list.add(new main.ants.Ant(Main.world.getNest().x1, Main.world.getNest().y1, main.enums.Direction.parse((int)(Math.random() * 3))));
            try {
                Thread.sleep(500L + (long) (Math.random() * 1500L));
            } catch (InterruptedException ex) {
                Logger.getLogger(Spawner.class.getName()).log(Level.ALL, null, ex);
            }
        }
    }
    
}
