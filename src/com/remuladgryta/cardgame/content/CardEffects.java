package com.remuladgryta.cardgame.content;

import java.util.List;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.cardgame.ICardEffect;
import com.remuladgryta.cardgame.Player;
import com.remuladgryta.cardgame.entity.ComponentHealth;
import com.remuladgryta.cardgame.entity.Entity;
import com.remuladgryta.hex.CubeCoord;

public class CardEffects {
	public static final ICardEffect
	DISCARD = new ICardEffect() {
		@Override
		public void onPlay(Card card, Player player, CubeCoord target) {
			player.getHand().removeCard(card);
		}
	},
	MOVE =  new ICardEffect() {
		@Override
		public void onPlay(Card card, Player player, CubeCoord target) {
			Entity e = player.getEntity();
			GameMap map = player.getEngine().getMap();
			map.moveEntity(e, target);
		}
	};

	public static final ICardEffect DAMAGE_SELF(final int amount) {
		return new ICardEffect() {
			@Override
			public void onPlay(Card card, Player player, CubeCoord target) {
				ComponentHealth hp = (ComponentHealth) player.getEntity()
						.getComponent("health");
				hp.damage(amount);
			}
		};
	}

	public static ICardEffect DAMAGE(final int amount) {
		return new ICardEffect() {
			@Override
			public void onPlay(Card card, Player player, CubeCoord target) {
				List<Entity> targets = player.getEngine().getMap()
						.entitiesAt(target);
				for (Entity entity : targets) {
					ComponentHealth hp = (ComponentHealth) entity
							.getComponent("health");
					hp.damage(amount);
				}
			}
		};
	}

}
