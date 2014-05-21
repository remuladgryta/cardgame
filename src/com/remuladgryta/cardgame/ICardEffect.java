package com.remuladgryta.cardgame;

import com.remuladgryta.hex.CubeCoord;

public interface ICardEffect {
	public void onPlay(Card card, Player player, CubeCoord target);
}
