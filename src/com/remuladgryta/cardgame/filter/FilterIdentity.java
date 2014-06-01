package com.remuladgryta.cardgame.filter;

import java.util.Set;

import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.hex.CubeCoord;


public class FilterIdentity extends TileFilter {
	@Override
	protected Set<CubeCoord> filter(CubeCoord origin,
			Set<CubeCoord> candidates, GameMap map) {
		return candidates;
	}

}
