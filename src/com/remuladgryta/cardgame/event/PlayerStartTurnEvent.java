package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.Player;

public class PlayerStartTurnEvent extends PlayerEvent implements IEvent {
	public PlayerStartTurnEvent(Player p) {
		super(p);
	}
}
