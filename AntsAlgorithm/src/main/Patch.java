package main;

import main.enums.Mode;

public class Patch {
    
    private IValue pFeed;
    private IValue pNest;
    private Boolean wall;
    private Integer feed;
    private Boolean nest;
    private Integer ant;
    
    public Patch(){
        this.pFeed = new IValue(0.0, 0);  
        this.wall = false;  
        this.feed = 0;  
        this.nest = false;  
        this.pNest = new IValue(0.0, 0);   
        this.ant = 0; 
    }
    
    public void addAnt() {
        this.ant += 1;
    }
    
    public void removeAnt() {
    	this.ant -= 1;
    }
    
    public void setWall(boolean b) {
    	this.wall = b;
    }
    
    public void setFeed() {
    	this.feed = 5;
    }
    
    public void setFeed(int i) {
    	this.feed = i;
    }
    public int getFeed() {
        return this.feed;
    }
    
    public void setNest(boolean b) {
    	this.nest = b;
    }
    
    public boolean isWall() {
        return this.wall;
    }
    
    public void setNestIntensity(IValue intensity) {
        this.pNest = intensity;
    }
    
    public void setFeedIntensity(IValue intensity) {
        this.pFeed = intensity; 
    }
    
    public IValue getFeedIntensity() {
        return this.pFeed;
    }
    
    public IValue getNestIntensity() {
        return this.pNest;
    }
    
    public boolean isFeed() {
        return this.feed > 0;
    }
    
    public boolean isNest() {
        return this.nest;
    }
    
    public boolean isAnt() {
        return this.ant > 0;
    }
    
    public main.enums.Mode getMode() {
        if(this.ant > 0) {
            return Mode.ANT;
        } else if(this.wall) {
            return Mode.WALL;
        } else if(this.nest) {
            return Mode.NEST;
        } else if(this.feed>0) {
            return Mode.FEED;
        } else if(this.pFeed.isHigher(new IValue(0.0, 0))) {
            return Mode.FEED_PHEROMON;
        } else if(this.pNest.isHigher(new IValue(0.0, 0))) {
            return Mode.NEST_PHEROMON;
        } else {
            return Mode.NOTHING;
        }
    }

}
