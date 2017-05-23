package main.datatype;

public class IValue {
    
    private Double d;
    private Integer i;
    
    public IValue(Double d, Integer i) {
        this.d = d;
        this.i = i;
    }
    
    // exception x < 1 or d < 0
    public IValue div(double x) {  
        
    	this.d = d/x;
        while(d < 1 && d > 0) {
            this.d *= 10;
            i++;
        }
        return this;
    }
    
    //works with negative integers?
    public IValue add(IValue v) {
    	IValue max;
    	IValue min;
    	
    	if(this.i >= v.i) {
    		max = this;
    		min = v;
    	}else{
    		max = v;
    		min = this;
    	}
    	
    	/* alternative?
    	if(this.isHigher(v)){
    		max = this;
    		min = v;
    	}else{
    		max = v;
    		min = this;
    	}*/
    	
    	int dif = max.i - min.i;
    	
    	if(max.i > 0) {
    	   	for(int i = 0; i < dif; i++) {
	    	max.d /= 10;
    	   	}
    	} else {
    		for(int i = 0; i > dif; i--) {
    	    	max.i *= 10;
        	   	}
    	}
    	this.d = max.d + min.d;
    	this.i = min.i;
    	
    	return this;
    }
    
    public IValue sub(IValue v){
    	
    	int dif = Math.abs(this.i-v.i);
    	
    	if(this.i>v.i) {
    		for(int i = 0; i < dif; i++){
        		this.d /= 10;
        	}
    	} else {
    		for(int i = 0; i < dif; i++){
        		this.d *= 10;
        	}
    	}
    	
    	this.d = this.d - v.d;
    	this.i = v.i;
    	this.assimilate();
    	
    	if(this.d < 0) {
    		this.d = 0.0;
    		this.i = 0;
    	}
    	
    	return this;
    }
    
    public IValue frac(IValue v) {
    	
    	this.i = this.i - v.i;
    	this.d = this.d / v.d;
    	
    	return this;
    }
    
    public IValue mult(IValue v) {
    	
    	this.i = this.i + v.i;
    	this.d = this.d * v.d;
    	
    	return this;
    }
    
    public void assimilate() {
    	
    	while((this.d >= 10.0) || (this.d <= -10.0) || ((this.d < 1.0) && (this.d > -1.0)) && this.d != 0.0) {
    		
    		if(this.i == Integer.MAX_VALUE || this.i == Integer.MIN_VALUE) {
    			break;
    		}
    		
    		if(this.d >= 10.0 || this.d <= -10.0) {
    			this.d /= 10;
    			this.i--;
    		} else {
    			if(this.i < 0) {
    				this.d *= 10;
    				this.i--;
    			} else {
    				this.d *= 10;
    				this.i++;
    			}
    		}
    	}
    }

    public boolean isHigher(IValue c) {
    	
    	this.assimilate();
    	c.assimilate();
    	
        if(this.i < c.i) {
            return true;
        } else if(this.i > c.i) {
            return false;
        } else {
            return (this.d > c.d);
        }
    }
        
    public Double getDouble() {
        return this.d;
    }

    public Integer getInteger() {
        return this.i;
    }
    
    public void set(double d, int i) {
    	this.d = d;
    	this.i = i;
    }
    
    public double parseToDouble() {
    	
    	while(this.i != 0) {
    		
    		if(i > 0) {
    			this.d /= 10;
    			this.i--;
    		} else {
    			this.d *= 10;
    			this.i++;
    		}
    	}
    	
    	return this.d; 
    }
    
    //exception x < 1 or d < 0
    public static IValue div(IValue ix, int x) {  
        
    	IValue iv = new IValue(ix.getDouble(), ix.getInteger());
    	
    	iv.d = iv.d/x;
        while(iv.d < 1 && iv.d > 0.0) {
            iv.d *= 10;
            iv.i++;
        }
        return iv;
    }
    
    public static IValue add(IValue ix1, IValue ix2) {
    	
    	IValue v1 = new IValue(ix1.getDouble(), ix1.getInteger());
    	IValue v2 = new IValue(ix2.getDouble(), ix2.getInteger());
    	
    	IValue max;
    	IValue min;
    	
    	if(v1.i >= v2.i){
    		max = v1;
    		min = v2;
    	}else{
    		max = v2;
    		min = v1;
    	}
    	
    	int dif = max.i - min.i;
    	
    	for(int i = 0; i < dif; i++){
    		max.d /= 10;
    	}
    	v1.d = max.d + min.d;
    	v1.i = min.i;
    	
    	return v1;
    }
    
    public static IValue sub(IValue ix1, IValue ix2){
    	
    	IValue v1 = new IValue(ix1.getDouble(), ix1.getInteger());
    	IValue v2 = new IValue(ix2.getDouble(), ix2.getInteger());

    	
    	int dif = Math.abs(v1.i-v2.i);
    	
    	if(v1.i>v2.i) {
    		for(int i = 0; i < dif; i++){
        		v1.d /= 10;
        	}
    	} else {
    		for(int i = 0; i < dif; i++){
        		v1.d *= 10;
        	}
    	}
    	
    	v1.d = v1.d - v2.d;
    	v1.i = v2.i;
    	v1 = IValue.assimilate(v1);
    	
    	if(v1.d < 0) {
    		v1.d = 0.0;
    		v1.i = 0;
    	}
    	return v1;
    }
    
    public static IValue mult(IValue ix1, IValue ix2) {
    	
    	IValue v1 = new IValue(ix1.getDouble(), ix1.getInteger());
    	IValue v2 = new IValue(ix2.getDouble(), ix2.getInteger());
    	
    	v1.i = v1.i + v2.i;
    	v1.d = v1.d * v2.d;
    	
    	return v1;
    }
    
    public static IValue assimilate(IValue v) {

    	while((v.d >= 10.0) || (v.d <= -10.0) || ((v.d < 1.0) && (v.d > -1.0)) && v.d != 0.0) {
    		
    		if(v.i == Integer.MAX_VALUE || v.i == Integer.MIN_VALUE) {
    			break;
    		}
    		
    		if(v.d >= 10.0 || v.d <= -10.0) {
    			v.d /= 10;
    			v.i--;
    		} else {
    			v.d *= 10;
    			v.i++;
    		}
    	}
    	
    	return v;
    }
    
    public String toString() {
    	
    	return this.d + "/" + this.i;
    	
    }

}
