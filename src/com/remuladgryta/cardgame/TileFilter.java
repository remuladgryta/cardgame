package com.remuladgryta.cardgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.remuladgryta.hex.CubeCoord;

public abstract class TileFilter {
	protected TileFilter sup;

	public TileFilter() {
		sup = null;
	}

	public TileFilter(TileFilter intersect) {
		sup = intersect;
	}

	public Set<CubeCoord> eligibleTargets(CubeCoord origin,
			Set<CubeCoord> candidates) {
		Set<CubeCoord> result;
		// recursive case
		if (sup != null) {
			result = sup.eligibleTargets(origin, candidates);
		}
		// base case
		else {
			result = new HashSet<CubeCoord>(candidates);
		}
		return filter(origin, result);
	}

	protected abstract Set<CubeCoord> filter(CubeCoord origin,
			Set<CubeCoord> candidates);
}
