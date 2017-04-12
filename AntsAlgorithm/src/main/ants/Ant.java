//ACHTUNG: NORTH und SOUTH vertauscht! Muss evtl an anderen Stellen angepasst werden

package main.ants;

import main.IValue;
import main.Main;
import main.Patch;
import main.enums.Mode;
import main.enums.Direction;

public class Ant {
    protected Integer posx;
    protected Integer posy;
    protected IValue intensity;
    protected boolean back = false;
    protected boolean pheromon = false;
    protected Direction direction;
    protected int moves;
    
    private Patch[][] field = Main.world.field;
    
    public Ant(Integer x, Integer y, Direction d) {
        this.posx = x;
        this.posy = y;
        this.direction = d;
        this.intensity = new IValue(Double.MAX_VALUE/2, 0);
        this.moves = 0;
        new Thread(new Movement(this)).start();
    }
    
    protected void move(Direction d) {
        boolean success = false;
        //System.out.println(d.toString());
        switch(d) {
            case SOUTH:
                if(!field[this.posx][this.posy+1].isWall()) {
                    field[posx][posy].removeAnt();
                    this.posy++;
                    success = true;
                    
                    //this.intensity=1000*Math.exp((this.moves/100)*Math.log(0.99));
                    this.intensity.div(3);
                }
                break;
            case EAST:
                if(!field[this.posx+1][this.posy].isWall()) {
                    field[posx][posy].removeAnt();
                    this.posx++;
                    success = true;
                    
                    this.intensity.div(3);
                }
                break;
            case NORTH:
                if(!field[this.posx][this.posy-1].isWall()) {
                    field[posx][posy].removeAnt();
                    this.posy--;
                    success = true;
                    
                    this.intensity.div(3);
                }
                break;
            case WEST:
                if(!field[this.posx-1][this.posy].isWall()) {
                    field[posx][posy].removeAnt();
                    this.posx--;
                    success = true;
                    
                    this.intensity.div(3);
                }
                break;
        }
        
        if(success) {
            this.direction = d;
            field[posx][posy].addAnt();
            this.moves++;
        } else {
            this.direction = Direction.parse((d.ordinal()+2) % 4);
            return;
        }
        
        if(this.back) {
            this.handleIntensity(Mode.FEED);
        } else {
            this.handleIntensity(Mode.NEST);
        }
    }
    
    private void handleIntensity(Mode m) {
        switch(m) {
            case FEED:
                field[posx][posy].setFeedIntensity(field[posx][posy].getFeedIntensity().add(intensity)); 
                if(!field[posx+1][posy].isWall()) { field[posx+1][posy].setFeedIntensity(field[posx+1][posy].getFeedIntensity().add(intensity.div(2))); }
                if(!field[posx-1][posy].isWall()) { field[posx-1][posy].setFeedIntensity(field[posx-1][posy].getFeedIntensity().add(intensity.div(2))); }
                if(!field[posx][posy+1].isWall()) { field[posx][posy+1].setFeedIntensity(field[posx][posy+1].getFeedIntensity().add(intensity.div(2))); }
                if(!field[posx][posy-1].isWall()) { field[posx][posy-1].setFeedIntensity(field[posx][posy-1].getFeedIntensity().add(intensity.div(2))); }
                
                /*field[posx+1][posy+1].setFeedIntensity(field[posx+1][posy].getFeedIntensity() + 0.5 * intensity);
                field[posx-1][posy-1].setFeedIntensity(field[posx-1][posy].getFeedIntensity() + 0.5 * intensity);
                field[posx-1][posy+1].setFeedIntensity(field[posx][posy+1].getFeedIntensity() + 0.5 * intensity);
                field[posx+1][posy-1].setFeedIntensity(field[posx][posy-1].getFeedIntensity() + 0.5 * intensity);*/
            case NEST:
                field[posx][posy].setNestIntensity(field[posx][posy].getNestIntensity().add(intensity));
                if(!field[posx+1][posy].isWall()) { field[posx+1][posy].setNestIntensity(field[posx+1][posy].getNestIntensity().add(intensity.div(2))); } 
                if(!field[posx-1][posy].isWall()) { field[posx-1][posy].setNestIntensity(field[posx-1][posy].getNestIntensity().add(intensity.div(2))); }
                if(!field[posx][posy+1].isWall()) { field[posx][posy+1].setNestIntensity(field[posx][posy+1].getNestIntensity().add(intensity.div(2))); }
                if(!field[posx][posy-1].isWall()) { field[posx][posy-1].setNestIntensity(field[posx][posy-1].getNestIntensity().add(intensity.div(2))); }
                
                /*field[posx+1][posy+1].setNestIntensity(field[posx+1][posy].getNestIntensity() + 0.5 * intensity);
                field[posx-1][posy-1].setNestIntensity(field[posx-1][posy].getNestIntensity() + 0.5 * intensity);
                field[posx-1][posy+1].setNestIntensity(field[posx][posy+1].getNestIntensity() + 0.5 * intensity);
                field[posx+1][posy-1].setNestIntensity(field[posx][posy-1].getNestIntensity() + 0.5 * intensity);*/
		default:
			break;
        }
    }
    
    protected Direction checkObject(Mode m) {
        switch(m) {
            case FEED:
                if(field[this.posx+1][this.posy].isFeed()) {
                    return Direction.EAST;
                } else if(field[this.posx-1][this.posy].isFeed()) {
                    return Direction.WEST;
                } else if(field[this.posx][this.posy+1].isFeed()) {
                    return Direction.SOUTH;
                } else if(field[this.posx][this.posy-1].isFeed()) {
                    return Direction.NORTH;
                } else {
                    return null;
                }
            case NEST:
                if(field[this.posx+1][this.posy].isNest()) {
                    return Direction.EAST;
                } else if(field[this.posx-1][this.posy].isNest()) {
                    return Direction.WEST;
                } else if(field[this.posx][this.posy+1].isNest()) {
                    return Direction.SOUTH;
                } else if(field[this.posx][this.posy-1].isNest()) {
                    return Direction.NORTH;
                } else {
                    return null;
                }
            /*case NEST_PHEROMON:
                double a1 = field[this.posx+1][this.posy].getNestIntensity();
                double a3 = field[this.posx-1][this.posy].getNestIntensity();
                double a0 = field[this.posx][this.posy+1].getNestIntensity();
                double a2 = field[this.posx][this.posy-1].getNestIntensity();
                //Suche nach größtem Pheromonwert:
                Direction aDir = Direction.EAST;
                double a = a1;
                if(a < a3) {
                    a = a3;
                    aDir = Direction.WEST;
                }
                if(a < a0) {
                    a = a0;
                    aDir = Direction.SOUTH;
                }
                if(a < a2) {
                    aDir = Direction.NORTH;
                }
                if(a != 0) {
                	return aDir;
                } else {
                	return null;
                }
            case FEED_PHEROMON:
                double b1 = field[this.posx+1][this.posy].getFeedIntensity();
                double b3 = field[this.posx-1][this.posy].getFeedIntensity();
                double b0 = field[this.posx][this.posy+1].getFeedIntensity();
                double b2 = field[this.posx][this.posy-1].getFeedIntensity();
                //Suche nach größtem Pheromonwert:
                Direction bDir = Direction.EAST;
                double b = b1;
                if(b < b3) {
                    b = b3;
                    bDir = Direction.WEST;
                }
                if(b < b0) {
                    b = b0;
                    bDir = Direction.SOUTH;
                }
                if(b < b2) {
                    bDir = Direction.NORTH;
                }
                if(b!=0){
                    return bDir;
                }else{
                    return null;
                }*/
            default:
                return null;
        }
    }
    
    protected IValue getNearIntensity(Direction d, Mode m) {
        switch(m) {
            case FEED_PHEROMON:
                switch(d) {
                    case SOUTH:
                        return field[this.posx][this.posy+1].getFeedIntensity();
                    case EAST:
                        return field[this.posx+1][this.posy].getFeedIntensity();
                    case NORTH:
                        return field[this.posx][this.posy-1].getFeedIntensity();
                    case WEST:
                        return field[this.posx-1][this.posy].getFeedIntensity();
                }
            case NEST_PHEROMON:
                switch(d) {
                    case SOUTH:
                        return field[this.posx][this.posy+1].getNestIntensity();
                    case EAST:
                        return field[this.posx+1][this.posy].getNestIntensity();
                    case NORTH:
                        return field[this.posx][this.posy-1].getNestIntensity();
                    case WEST:
                        return field[this.posx-1][this.posy].getNestIntensity();
                }
            default:
               return new IValue(0.0,0); 
        }
    }
}