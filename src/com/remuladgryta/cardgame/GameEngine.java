package com.remuladgryta.cardgame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import com.remuladgryta.cardgame.content.Decks;
import com.remuladgryta.cardgame.debug.Debug;
import com.remuladgryta.cardgame.entity.ComponentRenderable;
import com.remuladgryta.cardgame.event.CardReadyEvent;
import com.remuladgryta.cardgame.event.EventDispatch;
import com.remuladgryta.cardgame.event.EventListener;
import com.remuladgryta.cardgame.event.MapRenderStateChangedEvent;
import com.remuladgryta.cardgame.event.PlayerStartTurnEvent;
import com.remuladgryta.hex.CubeCoord;
import com.remuladgryta.util.Config;

public class GameEngine {
	private GameMap map;
	private int currentPlayer;
	private ArrayList<Player> players;
	private EventDispatch eventDispatch;
	private Card toPlay = null;
	private List<CubeCoord> possibleTargets = null;
	private final Random rand = new Random();

	public Random getRand() {
		return rand;
	}

	public GameEngine(int numPlayers) {
		eventDispatch = new EventDispatch();
		players = new ArrayList<Player>(numPlayers);
		for (int i = 0; i < numPlayers; i++) {
			Player p = new Player(this);
			p.setDeck(Decks.get(Decks.DEFAULT));
			ComponentRenderable renderComponent = (ComponentRenderable) (p.getEntity().getComponent("renderable"));
			try {
				renderComponent.setImage(ImageIO.read(getClass().getResource("/img/cardback.png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			players.add(p);
		}
		map = new GameMap(this, Config.mapSize);
		registerListeners();
	}

	private void registerListeners() {
		EventListener<CardReadyEvent> cardReadyListener = new EventListener<CardReadyEvent>() {
			@Override
			public void handleEvent(CardReadyEvent event) {
				toPlay = event.getCard();
				possibleTargets = new ArrayList<CubeCoord>(toPlay.getFilter().eligibleTargets(
						event.getPlayer().getEntity().getLocation(), map.tiles.keySet()));
				eventDispatch.dispatch(new MapRenderStateChangedEvent());
			}
		};
		eventDispatch.addListener(cardReadyListener, CardReadyEvent.class);
	}

	public EventDispatch getEventDispatch() {
		return eventDispatch;
	}

	public List<CubeCoord> getValidTargets() {
		if (possibleTargets == null) {
			return new ArrayList<CubeCoord>();
		}
		return possibleTargets;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
		eventDispatch.dispatch(new MapRenderStateChangedEvent());
	}

	public Player getCurrentPlayer() {
		return players.get(currentPlayer);
	}

	public void nextPlayer() {
		currentPlayer++;
		if (currentPlayer >= players.size()) {
			currentPlayer = 0;
		}
		eventDispatch.dispatch(new PlayerStartTurnEvent(getCurrentPlayer()));
	}

	public void tileInteracted(CubeCoord coord) {
		if (possibleTargets != null && possibleTargets.contains(coord)) {
			toPlay.play(getCurrentPlayer(), coord);
		}
		possibleTargets = null;
		toPlay = null;
		eventDispatch.dispatch(new MapRenderStateChangedEvent());
	}

	public void start() {
		for (Player player : players) {
			player.getDeck().shuffle();
			player.draw(Config.startHandSize);

			map.addEntityAtRandom(player.playerEntity);

		}
		eventDispatch.dispatch(new PlayerStartTurnEvent(getCurrentPlayer()));
	}

}
