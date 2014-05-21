package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.Card;

public class CardPlayedEvent extends CardEvent {

	public CardPlayedEvent(Card card) {
		super(card);
	}
	
	@Override
	public void cancel(){
		
	}
}
