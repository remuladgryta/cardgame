package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.Player;

public class PlayerEvent implements IEvent{

	private boolean canceled=false;
	private Player player;
	
	public PlayerEvent(Player p){
		player = p;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void cancel() {
		canceled=true;
	}
}
