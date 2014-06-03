package com.remuladgryta.cardgame.entity;

import com.remuladgryta.cardgame.Player;
import com.remuladgryta.cardgame.event.EntityDeadEvent;
import com.remuladgryta.cardgame.event.EventListener;

public class ComponentPlayer extends EntityComponent implements EventListener<EntityDeadEvent>{

	private final Player player;
	
	public ComponentPlayer(Player p){
		super();
		player = p;
		p.getEngine().getEventDispatch().addListener(this, EntityDeadEvent.class);
	}
	
	@Override
	public String getName() {
		return "player";
	}
	
	public Player getPlayer(){
		return player;
	}

	@Override
	public void handleEvent(EntityDeadEvent event) {
		if(event.getEntity() == entity){
			player.setAlive(false);
		}
	}

}
