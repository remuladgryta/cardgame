package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.Card;

public class CardEvent extends AbstractEventImpl {
	
	private final Card card;
	
	public CardEvent(Card card){
		super();
		this.card = card;
	}
	
	public Card getCard() {
		return card;
	}
}
