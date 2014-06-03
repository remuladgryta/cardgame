package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.Player;

public class PlayerEndTurnEvent extends PlayerEvent {

	public PlayerEndTurnEvent(Player p) {
		super(p);
	}

}
