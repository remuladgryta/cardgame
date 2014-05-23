package com.remuladgryta.cardgame.content;

import java.util.HashSet;
import java.util.Set;

import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.cardgame.TileFilter;
import com.remuladgryta.cardgame.entity.Entity;
import com.remuladgryta.hex.CubeCoord;

public class FilterPlayer extends TileFilter {

	@Override
	protected Set<CubeCoord> filter(CubeCoord origin, Set<CubeCoord> candidates, GameMap map) {
		HashSet<CubeCoord> result = new HashSet<CubeCoord>();
		for(CubeCoord candidate : candidates){
			for(Entity e : map.entitiesAt(candidate)){
				if(e.hasComponent("player")){
					result.add(candidate);
				}
			}
		}
		return result;
	}

}
