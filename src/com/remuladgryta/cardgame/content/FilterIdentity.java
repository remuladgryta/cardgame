package com.remuladgryta.cardgame.content;

import java.util.Set;

import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.cardgame.TileFilter;
import com.remuladgryta.hex.CubeCoord;


public class FilterIdentity extends TileFilter {
	@Override
	protected Set<CubeCoord> filter(CubeCoord origin,
			Set<CubeCoord> candidates, GameMap map) {
		return candidates;
	}

}
