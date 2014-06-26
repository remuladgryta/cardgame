package com.remuladgryta.cardgame.content;

import com.remuladgryta.cardgame.filter.FilterCount;
import com.remuladgryta.cardgame.filter.FilterDistance;
import com.remuladgryta.cardgame.filter.FilterInvert;
import com.remuladgryta.cardgame.filter.TileFilter;


public class TileFilters {
	public static TileFilter 
	FILTER_SELF = new FilterDistance(0),
	FILTER_ADJACENT = new FilterInvert(new FilterDistance(1),FILTER_SELF),
	FILTER_FULL = new FilterCount(false, 6),
	FILTER_EMPTY = new FilterCount(true, 1),
	FILTER_HAS_SPACE = new FilterInvert(FILTER_FULL),
	FILTER_HAS_ENTITIES = new FilterInvert(FILTER_EMPTY);
}
