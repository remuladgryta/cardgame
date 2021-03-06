package com.remuladgryta.cardgame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import com.remuladgryta.cardgame.event.GameOverEvent;
import com.remuladgryta.cardgame.event.MapRenderStateChangedEvent;
import com.remuladgryta.cardgame.event.PlayerEndTurnEvent;
import com.remuladgryta.cardgame.event.PlayerStartTurnEvent;
import com.remuladgryta.hex.CubeCoord;
import com.remuladgryta.util.Config;
import com.remuladgryta.util.ImageUtil;
import com.remuladgryta.util.SpriteLibrary;

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
		loadImages();
		eventDispatch = new EventDispatch();
		players = new ArrayList<Player>(numPlayers);
		for (int i = 0; i < numPlayers; i++) {
			Player p = new Player(this);
			p.setName("Player "+(i+1));
			if(Config.debugCard){
				p.setDeck(Decks.get(Decks.DEBUG));
			}else{
				p.setDeck(Decks.get(Decks.DEFAULT));
			}
			ComponentRenderable renderComponent = (ComponentRenderable) (p
					.getEntity().getComponent("renderable"));
			renderComponent.setImage(SpriteLibrary.get("entityp" + (i + 1)));
			players.add(p);
		}
		map = new GameMap(this, Config.mapSize);
		registerListeners();
	}

	private void loadImages() {
		BufferedImage back = SpriteLibrary.get("cardface");
		for (int i = 1; i <= 6; i++) {
			BufferedImage fg = SpriteLibrary.get("p" + i);
			BufferedImage result = ImageUtil.composite(back, fg);
			SpriteLibrary.add(result, "entityp" + i);
		}
	}

	private void registerListeners() {
		EventListener<CardReadyEvent> cardReadyListener = new EventListener<CardReadyEvent>() {
			@Override
			public void handleEvent(CardReadyEvent event) {
				toPlay = event.getCard();
				possibleTargets = new ArrayList<CubeCoord>(toPlay.getFilter()
						.eligibleTargets(
								event.getPlayer().getEntity().getLocation(),
								map.getTiles(), map));
				eventDispatch.dispatch(new MapRenderStateChangedEvent());
			}
		};
		eventDispatch.addListener(cardReadyListener, CardReadyEvent.class);
		
		EventListener<PlayerEndTurnEvent> playerEndTurnListener = new EventListener<PlayerEndTurnEvent>(){

			@Override
			public void handleEvent(PlayerEndTurnEvent event) {
				ArrayList<Player> alivePlayers = new ArrayList<Player>();
				for(Player p : players){
					if(p.alive){
						alivePlayers.add(p);
					}
				}
				if(alivePlayers.size()<2){
					event.cancel();
					if(alivePlayers.size()<1){
						eventDispatch.dispatch(new GameOverEvent(null));
					}else{
						eventDispatch.dispatch(new GameOverEvent(alivePlayers.get(0)));
					}
				}
			}
			
		};
		eventDispatch.addListener(playerEndTurnListener, PlayerEndTurnEvent.class);
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
	
	public List<Player> getPlayers(){
		return Collections.unmodifiableList(players);
	}

	public void nextPlayer() {
		if (eventDispatch.dispatch(new PlayerEndTurnEvent(getCurrentPlayer()))) {
			currentPlayer++;
			if (currentPlayer >= players.size()) {
				currentPlayer = 0;
			}
			eventDispatch
					.dispatch(new PlayerStartTurnEvent(getCurrentPlayer()));
		}

	}

	public void tileInteracted(CubeCoord coord) {
		if (possibleTargets != null && possibleTargets.contains(coord)) {
			toPlay.play(getCurrentPlayer(), coord);
		}
		clearReadiedCard();
	}
	
	public void clearReadiedCard(){
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
