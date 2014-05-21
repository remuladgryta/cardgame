package com.remuladgryta.cardgame;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Deck {
	LinkedList<Card> cards;

	public Deck(){
		 cards = new LinkedList<Card>();
	}
	
	public Deck(Collection<Card> cards){
		this.cards = new LinkedList<Card>(cards);
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}

	public Card draw() {
		if (cards.isEmpty()) {
			return null;
		} else {
			return cards.removeFirst();
		}
	}

}
