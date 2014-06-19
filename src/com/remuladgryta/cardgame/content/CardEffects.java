package com.remuladgryta.cardgame.content;

import java.util.List;
import java.util.Set;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.cardgame.ICardEffect;
import com.remuladgryta.cardgame.IEntityFactory;
import com.remuladgryta.cardgame.Player;
import com.remuladgryta.cardgame.entity.ComponentHealth;
import com.remuladgryta.cardgame.entity.ComponentRenderable;
import com.remuladgryta.cardgame.entity.Entity;
import com.remuladgryta.hex.CubeCoord;
import com.remuladgryta.hex.HexMath;
import com.remuladgryta.util.SpriteLibrary;

public class CardEffects {
	public static final ICardEffect DISCARD = new ICardEffect() {
		@Override
		public void onPlay(Card card, Player player, CubeCoord target) {
			player.getHand().removeCard(card);
		}
	};
	public static final ICardEffect MOVE = new ICardEffect() {
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

	public static ICardEffect ROTATE(final int steps) {
		return new ICardEffect() {

			@Override
			public void onPlay(Card card, Player player, CubeCoord target) {
				GameMap map = player.getEngine().getMap();
				Set<CubeCoord> tiles = map.getTiles();

				List<CubeCoord> toRotate = HexMath.inRange(target, 1);
				toRotate.remove(target);
				toRotate.retainAll(tiles);

				for (int step = 0; step < steps; step++) {
					List<Entity> tmp = map.entitiesAt(toRotate.get(toRotate
							.size() - 1));
					for (int t = toRotate.size() - 1; t > 0; t--) {
						List<Entity> migrators = map.entitiesAt(toRotate
								.get(t - 1));
						for (Entity e : migrators) {
							map.moveEntity(e, toRotate.get(t));
						}
					}
					for (Entity e : tmp) {
						map.moveEntity(e, toRotate.get(0));
					}
				}

			}

		};
	}

	public static ICardEffect SUMMON(final IEntityFactory factory,
			final int count) {
		return new ICardEffect() {
			@Override
			public void onPlay(Card card, Player player, CubeCoord target) {
				GameMap map = player.getEngine().getMap();
				for (int i = count - map.entitiesAt(target).size(); i > 0; i--) {
					// Don't summon more than can fit on the tile
					player.getEngine().getMap()
							.addEntity(target, factory.makeEntity());
				}
			}

		};
	}

	public static ICardEffect WALL() {
		return new ICardEffect(){
			@Override
			public void onPlay(Card card, final Player player, CubeCoord target) {
				IEntityFactory wallFactory = new IEntityFactory() {
					@Override
					public Entity makeEntity() {
						Entity e = new Entity(player.getEngine());
						e.addComponent(new ComponentHealth().setMaxHealth(5).setHealth(5));
						e.addComponent(new ComponentRenderable().setImage(SpriteLibrary.get("wall")));
						return e;
					}
				};
				
				SUMMON(wallFactory, 7).onPlay(card, player, target);
			}};
	}

}
