package com.remuladgryta.cardgame;

import com.remuladgryta.cardgame.entity.ComponentHealth;
import com.remuladgryta.cardgame.entity.ComponentPlayer;
import com.remuladgryta.cardgame.entity.ComponentRenderable;
import com.remuladgryta.cardgame.entity.Entity;
import com.remuladgryta.cardgame.event.EventListener;
import com.remuladgryta.cardgame.event.PlayerStartTurnEvent;
import com.remuladgryta.util.Config;

public class Player implements EventListener<PlayerStartTurnEvent> {
	Hand hand;
	Deck deck;
	Entity playerEntity;
	GameEngine engine;
	String name;
	
	@Override
	public String toString(){
		return name + ": " + ((ComponentHealth)playerEntity.getComponent("health")).getHealth();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	boolean alive = true;

	public Player(GameEngine engine) {
		this.engine = engine;
		hand = new Hand(this);
		playerEntity = new Entity(engine)
				.addComponent(new ComponentHealth().setMaxHealth(20).setHealth(20))
				.addComponent(new ComponentRenderable())
				.addComponent(new ComponentPlayer(this));
		engine.getEventDispatch().addListener(this, PlayerStartTurnEvent.class);
	}

	public Entity getEntity() {
		return playerEntity;
	}

	public GameEngine getEngine() {
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
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}
	
	public boolean getAlive(){
		return alive;
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

	@Override
	public void handleEvent(PlayerStartTurnEvent event) {
		if (event.getPlayer() == this && hand.getSize() < Config.maxHandSize) {
			draw(1);
		}
	}

}
