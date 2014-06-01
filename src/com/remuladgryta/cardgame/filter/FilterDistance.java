package com.remuladgryta.cardgame.filter;

import java.util.List;
import java.util.Set;

import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.hex.CubeCoord;
import com.remuladgryta.hex.HexMath;

public class FilterDistance extends TileFilter{
	private final int distance;
	public FilterDistance(int distance, TileFilter sup){
		super(sup);
		this.distance = distance;
	}
	
	public FilterDistance(int distance) {
		super();
		this.distance = distance;
	}

	@Override
	protected Set<CubeCoord> filter(CubeCoord origin, Set<CubeCoord> candidates, GameMap map) {
		List<CubeCoord> inRange = HexMath.inRange(origin, distance);
		candidates.retainAll(inRange);
		return candidates;
	}

}
