package com.remuladgryta.cardgame.entity;

import com.remuladgryta.cardgame.Player;

public class ComponentPlayer extends EntityComponent {

	private final Player player;
	
	public ComponentPlayer(Player p){
		super();
		player = p;
	}
	
	@Override
	public String getName() {
		return "player";
	}
	
	public Player getPlayer(){
		return player;
	}

}
