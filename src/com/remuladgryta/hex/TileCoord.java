package com.remuladgryta.hex;

import com.remuladgryta.util.HashCodeUtil;

public class TileCoord implements Comparable{
	private final double p, q;
	
	/*      /\
	 *     /  \
	 *    /    \
	 *   p      q
	 * 
	 */
	
	public TileCoord(double p, double q){
		this.p = p;
		this.q = q;
	}
	
	public double getP(){
		return p;
	}
	
	public double getQ(){
		return q;
	}
	
	@Override
	public int compareTo(Object o) {
		if(this == o){
			return 0;
		}
		TileCoord c = (TileCoord)o;
		//sort on p, then q
		if(this.getP() != c.getP()) return  c.getP() - this.getP() < 0? -1 : +1;
		if(this.getQ() != c.getQ()) return  c.getQ() - this.getQ() < 0? -1 : +1;
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
		result = HashCodeUtil.hash(result, getP());
		result = HashCodeUtil.hash(result, getQ());
		return result;
	}
}
