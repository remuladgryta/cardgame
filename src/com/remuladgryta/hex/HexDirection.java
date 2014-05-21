package com.remuladgryta.hex;

public enum HexDirection {
	EAST(new CubeCoord(1, -1, 0)), NORTH_EAST(new CubeCoord(0, -1, 1)), NORTH_WEST(
			new CubeCoord(-1, 0, 1)), WEST(new CubeCoord(-1, 1, 0)), SOUTH_WEST(
			new CubeCoord(0, 1, -1)), SOUTH_EAST(new CubeCoord(1, 0, -1));
	final CubeCoord offset;

	HexDirection(CubeCoord direction) {
		offset = direction;
	}
	
	public CubeCoord getOffset(){
		return offset;
	}
}
