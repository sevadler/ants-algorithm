
package main;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.enums.Mode;

public class Update implements Runnable{
	
	/*private int ticks = 0;
	private int time1 = Update.getSeconds();
	private int time2;*/
	
	private double pct_nest = 70;
	private double pct_feed = 70;
	private IValue pFeed;
	private IValue pNest;
	
	private Color c_brown = new Color(198,130,12);
	private Color c_yellow = new Color(247,247,43);
	private Color c_purple = Color.BLACK; //new Color(198,12,185);
	private Color c_red = new Color(198,12,18);
	
	private final IValue LIMIT = new IValue(80 * Double.MAX_VALUE, Integer.MIN_VALUE + 2);

    @Override
    public void run(){
        while(true){
        	// Frame-Counter
        	/*time2 = Update.getSeconds();
        	if(time1 == time2) {
        		ticks++;
        	} else {
        		time1 = Update.getSeconds();
        		System.out.println(ticks);
        		ticks = 0;
        	}*/
            for(int y=0; y<Main.world.getH(); y++){
                for(int x=0; x<Main.world.getL(); x++){
                    Patch p = Main.world.field[x][y];
                    Mode m = p.getMode();
                    Color c = Color.GRAY;
                    
                    //zeitliche Abnahme
                    /*pFeed = p.getFeedIntensity();
                    if(pFeed.isHigher(new IValue(2.0, 0))){
                        p.setFeedIntensity(pFeed.sub(new IValue(0.0, 0)));
                    } else {
                    	pFeed.set(0.0, 0);
                    }
                    pNest = p.getNestIntensity();
                    if(pNest.isHigher(new IValue(2.0,0))){
                    	p.setNestIntensity(pNest.sub(new IValue(0.0, 0)));;
                    } else {
                    	pNest.set(0.0, 0);
                    }*/
                    
                    switch(m){
                        case ANT: 
                            c= c_brown;//braun
                            break;
                        case FEED:
                            c = c_yellow;//gelb
                            break;
                        case NEST:
                            c = c_purple;//lila
                            break;
                        case WALL:
                            c = c_red;//rot
                            break;
                        case FEED_PHEROMON:
                        	if(p.getNestIntensity().isHigher(LIMIT)) {
                        		pct_feed = 100.00;
                        	} else {
                        		pct_feed += IValue.mult(p.getFeedIntensity(), (new IValue((100-pct_feed), 0).frac(LIMIT))).parseToDouble();
                        	}
                        	c = Color.getHSBColor(1.80F, 1.00F, (float)(pct_feed/100));
                        	pct_feed = 70;
                        	break;
                        case NEST_PHEROMON:
                        	if(p.getNestIntensity().isHigher(LIMIT)) {
                        		pct_nest = 100.00;
                        	} else {
                        		pct_nest += IValue.mult(p.getNestIntensity(), (new IValue((100-pct_nest), 0).frac(LIMIT))).parseToDouble();
                        	}
                        	c = Color.getHSBColor(1.20F, 1.00F, (float)(pct_nest/100));
                        	pct_nest = 70;
                        	break;
                        default:
                        	
                    }
                    int rgb = c.getRGB();
                    Object data = Main.world.model.getDataElements(rgb, null);
                    for(int i=0; i<5; i++){
                        for(int j=0; j<5; j++){
                            Main.world.raster.setDataElements(x*5+i,y*5+j,data);
                        }
                    }                             
                }
            }
            Main.world.icon = new ImageIcon(Main.world.img);
            Main.world.button = new JButton(Main.world.icon);
            Main.world.add(Main.world.button);
            Main.world.validate();
        }
    
        
    }
    
    /*private static int getSeconds() {
		Date d = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("ss");
		return Integer.parseInt(ft.format(d).toString());
	}*/
}
