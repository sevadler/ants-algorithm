/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

public class Main{
    
    public static World world;// = new World(200,200);
    
    public static final int NEST_X = 2;
    public static final int NEST_Y = 2;
    public static final int FEED_X1 = 30;
    public static final int FEED_Y1 = 91;
    public static final int FEED_X2 = 30;
    public static final int FEED_Y2 = 109;
   
    public static void main(String[] args) {
        world = new main.World(10, 10);
        world.initalize();
        Thread updating = new Thread(new Update());
        updating.start();
        world.setVisible(true);
    }
               
}

