package com.remuladgryta.cardgame.content;

import com.remuladgryta.cardgame.TileFilter;

public class TileFilters {
	public static TileFilter 
	FILTER_SELF = new FilterDistance(0),
	FILTER_ADJACENT = new FilterInvert(new FilterDistance(1),FILTER_SELF);
	
	
}
