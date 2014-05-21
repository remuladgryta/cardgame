package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.Player;


public class CardReadyEvent extends PlayerEvent {
	private final Card card;
	public Card getCard() {
		return card;
	}
	public CardReadyEvent(Player p, Card c) {
		super(p);
		card=c;
	}
}
