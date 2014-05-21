package com.remuladgryta.cardgame;

import com.remuladgryta.cardgame.entity.ComponentHealth;
import com.remuladgryta.cardgame.entity.ComponentRenderable;
import com.remuladgryta.cardgame.entity.Entity;

public class Player {
	Hand hand;
	Deck deck;
	Entity playerEntity;
	GameEngine engine;
	public Player(GameEngine engine){
		this.engine = engine;
		hand = new Hand(this);
		playerEntity = new Entity().addComponent(new ComponentHealth().setMaxHealth(20).setHealth(20)).addComponent(new ComponentRenderable());
	}
	
	public Entity getEntity(){
		return playerEntity;
	}
	
	public GameEngine getEngine(){
		return engine;
	}
	
	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public Deck getDeck() {
		return deck;
	}

	public void draw(int numCards) {
		for (int i = 0; i < numCards; i++) {
			Card c = deck.draw();
			if (c != null) {
				hand.putCard(c);
			}
		}

	}

}
