package com.remuladgryta.hex;

import com.remuladgryta.util.HashCodeUtil;

public class PixelCoord implements Comparable, ICoordinate<PixelCoord>{
	private final double x, y;
	
	public PixelCoord(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	@Override
	public int compareTo(Object o) {
		if(this == o){
			return 0;
		}
		PixelCoord c = (PixelCoord)o;
		//sort on x, then y
		if(this.x != c.x) return  c.x - this.x < 0? -1 : +1;
		if(this.y != c.y) return  c.y - this.y < 0? -1 : +1;
		return 0;
	}
	
	@Override 
	public boolean equals(Object o){
		if(o == null)return false;
		return compareTo(o) == 0;
	}
	
	@Override
	public int hashCode(){
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, x);
		result = HashCodeUtil.hash(result, y);
		return result;
	}
	
	public PixelCoord add(PixelCoord coord){
		return new PixelCoord(this.getX()+coord.getX(),this.getY()+coord.getY());
	}

	@Override
	public PixelCoord invert() {
		return new PixelCoord(-getX(),-getY());
	}

	@Override
	public PixelCoord scale(double amount) {
		return new PixelCoord(getX()*amount, getY()*amount);
	}
}
