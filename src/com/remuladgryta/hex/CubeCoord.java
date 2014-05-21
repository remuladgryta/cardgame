package com.remuladgryta.hex;

import com.remuladgryta.util.HashCodeUtil;

public class CubeCoord implements Comparable<CubeCoord>, ICoordinate<CubeCoord> {
	private final double r, g, b;

	public CubeCoord(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public double getR() {
		return r;
	}

	public double getG() {
		return g;
	}

	public double getB() {
		return b;
	}

	@Override
	public int compareTo(CubeCoord c) {
		if (this == c) {
			return 0;
		}
		// sort on r, then g, then b
		if (this.r != c.r)
			return c.r - this.r < 0 ? -1 : +1;
		if (this.g != c.g)
			return c.g - this.g < 0 ? -1 : +1;
		if (this.b != c.b)
			return c.b - this.b < 0 ? -1 : +1;
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null){
			return false;
		}
		return compareTo((CubeCoord)o) == 0;
	}

	@Override
	public int hashCode() {
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, r);
		result = HashCodeUtil.hash(result, g);
		result = HashCodeUtil.hash(result, b);
		return result;
	}

	public CubeCoord scale(double amount) {
		return new CubeCoord(r * amount, g * amount, b * amount);
	}

	public CubeCoord neighbor(HexDirection direction){
		return add(direction.getOffset());
	}

	public CubeCoord add(CubeCoord coord) {
		return new CubeCoord(this.getR() + coord.getR(), this.getG()
				+ coord.getG(), this.getB() + coord.getB());
	}

	@Override
	public CubeCoord invert() {
		return new CubeCoord(-getR(),-getG(),-getB());
	}
	
	@Override public String toString(){
		return getR()+","+getG()+","+getB();
	}
}
