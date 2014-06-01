package com.remuladgryta.cardgame;

import java.util.List;

import com.remuladgryta.cardgame.event.CardPlayEvent;
import com.remuladgryta.cardgame.event.CardPlayedEvent;
import com.remuladgryta.cardgame.event.CardReadyEvent;
import com.remuladgryta.cardgame.filter.TileFilter;
import com.remuladgryta.hex.CubeCoord;

public class Card {
	private String cardText;
	private String title;
	private List<ICardEffect> effects;
	private TileFilter filter;

	public Card(String title, String cardText, List<ICardEffect> effects, TileFilter filter) {
		this.title = title;
		this.cardText = cardText;
		this.effects = effects;
		this.filter = filter;
	}

	public TileFilter getFilter() {
		return filter;
	}

	public String getText() {
		return cardText;
	}

	public String getTitle() {
		return title;
	}

	public void selectForPlay(Player player) {
		player.getEngine().getEventDispatch().dispatch(new CardReadyEvent(player, this));
	}

	public void play(Player player, CubeCoord target) {
		if(!player.getEngine().getEventDispatch().dispatch(new CardPlayEvent(this))){
			return;//play got canceled
			//TODO notify player that card was canceled?
		}
		if (effects != null) {
			for (ICardEffect effect : effects) {
				effect.onPlay(this, player, target);
			}
		}
		player.getEngine().getEventDispatch().dispatch(new CardPlayedEvent(this));//successfully played card.
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
