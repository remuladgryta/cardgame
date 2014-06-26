package com.remuladgryta.cardgame.filter;

import java.util.HashSet;
import java.util.Set;

import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.hex.CubeCoord;

public class FilterCount extends TileFilter {
	boolean less;
	int count;

	public FilterCount(boolean less, int count) {
		this(null, less, count);
	}
	
	public FilterCount(TileFilter intersect, boolean less, int count){
		super(intersect);
		this.less = less;
		this.count = count;
	}

	@Override
	protected Set<CubeCoord> filter(CubeCoord origin,
			Set<CubeCoord> candidates, GameMap map) {
		Set<CubeCoord> result = new HashSet<CubeCoord>();
		for (CubeCoord coord : candidates) {
			if (less && map.entitiesAt(coord).size() < count) {
				result.add(coord);
			}else if(!less && map.entitiesAt(coord).size() > count){
				result.add(coord);
			}
		}

		return result;
	}

}
