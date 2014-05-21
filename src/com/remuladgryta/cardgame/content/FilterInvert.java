package com.remuladgryta.cardgame.content;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.remuladgryta.cardgame.TileFilter;
import com.remuladgryta.hex.CubeCoord;

public class FilterInvert extends TileFilter {

	TileFilter invert;
	
	public FilterInvert(TileFilter sup, TileFilter toInvert){
		super(sup);
		invert = toInvert;
	}
	
	@Override
	protected Set<CubeCoord> filter(CubeCoord origin,
			Set<CubeCoord> candidates) {
		Set<CubeCoord> toRemove = invert.eligibleTargets(origin, candidates);
		candidates.removeAll(toRemove);
		return candidates;
	}

}
