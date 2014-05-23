package com.remuladgryta.cardgame.content;

import java.util.Arrays;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.ICardEffect;

public class Cards {
	public static Card
	 move = new Card("Move", "Move to an adjacent tile", Arrays.asList(new ICardEffect[]{CardEffects.MOVE, CardEffects.DISCARD}), TileFilters.FILTER_ADJACENT),
	 attack = new Card("Attack", "Attack a tile adjacent to you", Arrays.asList(new ICardEffect[]{CardEffects.DAMAGE(2), CardEffects.DISCARD}), TileFilters.FILTER_ADJACENT),
	 egocentricity = new Card("Egocentricity", "Rotate tiles adjacent to you one step clockwise <p><i>The world revolves around me! </i></p>", Arrays.asList(new ICardEffect[]{CardEffects.ROTATE(1),CardEffects.DISCARD}),TileFilters.FILTER_SELF);
}
