package com.remuladgryta.cardgame.content;

import java.util.ArrayList;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.Deck;

public class Decks {
	public static final int DEBUG = -1, DEFAULT = 0, AGGRO = 1;

	public static Deck get(int deck) {
		ArrayList<Card> cards = new ArrayList<Card>();
		switch (deck) {
		case AGGRO:
			for(int i=0;i<7;i++){
				cards.add(Cards.attack());
				cards.add(Cards.attack());
				cards.add(Cards.move());
			}
			break;
		case DEBUG:
			cards.add(Cards.debug());
			break;
			
		case DEFAULT:
		default:
			for(int i=0;i<10;i++){
				cards.add(Cards.move());
				cards.add(Cards.attack());
				cards.add(Cards.egocentricity());
				cards.add(Cards.insult());
				cards.add(Cards.wall());
			}
		}
		return new Deck(cards);
	}
}
