package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.Player;

public class GameOverEvent extends AbstractEventImpl {
	Player winner;
	public GameOverEvent(Player winner){
		super();
		this.winner = winner;
	}
	
	public Player getWinner(){
		return winner;
	}
	
	@Override
	public void cancel(){
		
	}
}
