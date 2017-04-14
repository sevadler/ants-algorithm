package main;

import java.util.ArrayList;
import java.util.List;

import main.ants.Ant;
import main.enums.Mode;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.Color;

import javax.swing.*;

public class World extends JFrame {
    
	private static final long serialVersionUID = 766277020594621205L;
	private final int l;
    private final int h;
    
    public Patch[][] field;
    
    private Nest nest;
    private Feed feed;
    
    public List<Ant> list = new ArrayList<Ant>();
    
    BufferedImage img;
    WritableRaster raster;
    ColorModel model;
    Icon icon;
    JButton button;
  
    public World(int l, int h){
        
        this.h = h;
        this.l = l;
        
        img = new BufferedImage(l*5, h*5, BufferedImage.TYPE_INT_ARGB);
        raster = img.getRaster();
        model = img.getColorModel();
        Color c0 = Color.WHITE;
        int argb = c0.getRGB();
        
        Object data0 = model.getDataElements(argb, null);
        
        for (int i = 0; i < l*5; i++) {
        	for (int j = 0; j < h*5; j++) {
        		raster.setDataElements(i, j, data0);
            }
        }
        
        icon = new ImageIcon(img);
        button = new JButton(icon);
        add(button);
         
        //Frame setting
        setSize(l*5+40,h*5+60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.field = new Patch[l][h];
        for(int x=0; x<l; x++){
            for(int y=0; y<h; y++){
                this.field[x][y]= new Patch();
            }
        }
    }
    
    public void initalize(){
        createWall(0,0,0,this.h-1);
        createWall(0,0,this.l-1,0);
        createWall(this.l-1,0,this.l-1,this.h-1);
        createWall(0,this.h-1,this.l-1,this.h-1);
        
        this.interpretWallConfiguration(Configuration.WALLS);
        //test wall
        //createWall(0,101,150,101);
        //createWall(0,99,150,99);
       // createWall(0,110,150,110);
        //createWall(0,90,150,90);
        
        createNest(Main.NEST_X, Main.NEST_Y);
        //createFeed(Main.FEED_X1, Main.FEED_Y1, Main.FEED_X2, Main.FEED_Y2);
        Thread tNest = new Thread(new Spawner());
        tNest.start();

    }
    
        
    protected void createObject(int x1, int y1, int x2, int y2, Mode m){
        
        int xLinks;
        int xRechts;
        int yUnten;
        int yOben; 
        
        if(x1>x2){
           xLinks = x2; 
           xRechts = x1;
        }else{
           xLinks = x1; 
           xRechts = x2; 
        }
        
        if(y1>y2){
           yUnten = y2; 
           yOben = y1;
        }else{
           yUnten = y1; 
           yOben = y2; 
        }
           
        for(int i = xLinks; i<=xRechts;i++){
           for(int o = yUnten; o<=yOben;o++){
               switch(m) {
                   case WALL:
                       this.field[i][o].setWall(true);
                       break;
                   case NEST:
                       this.field[i][o].setNest(true);
                       break;
                   case FEED:
                       this.field[i][o].setFeed(1); //test 1
                       break;
			default:
				break;
               }
           } 
        }
    } 
    
    public void interpretWallConfiguration(String walls) {
    	
    	if(walls.equals("")) {
    		return;
    	}
    	
    	String[] parts = walls.split("-");
    	int x1; int y1; int x2; int y2;
    	
    	for(String s : parts) {
    		
    		String[] coords = s.split(",");
    		
    		x1 = Integer.parseInt(coords[0]);
    		y1 = Integer.parseInt(coords[1]);
    		x2 = Integer.parseInt(coords[2]);
    		y2 = Integer.parseInt(coords[3]);
    		
    		this.createWall(x1, y1, x2, y2);
    	}
    	
    }
   
    public void createNest(int x1, int y1){
        this.nest= new Nest(x1, y1, x1, y1);
    }
   
    public void createWall(int x1, int y1, int x2, int y2){
       this.createObject(x1, y1, x2, y2, Mode.WALL); 
    }
    
    public void createFeed(int x1, int y1, int x2, int y2){
       this.feed = new Feed(x1, y1, x2, y2); 
    }
    
    public Nest getNest(){
        return this.nest;
    }
    
    public Feed getFeed() {
        return this.feed;
    }
    
    public Integer getL(){
        return this.l;
    }
    
    public Integer getH(){
        return this.h;
    }
   
}
