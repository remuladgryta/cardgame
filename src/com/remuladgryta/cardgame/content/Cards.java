package com.remuladgryta.cardgame.content;

import java.util.Arrays;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.ICardEffect;

public class Cards {
	public static Card
	 move = new Card("Move", "Move to an adjacent tile", Arrays.asList(new ICardEffect[]{CardEffects.MOVE, CardEffects.DISCARD}), TileFilters.FILTER_ADJACENT),
	 attack = new Card("Attack", "Deal 2 damage to an adjacent tile", Arrays.asList(new ICardEffect[]{CardEffects.DAMAGE(2), CardEffects.DISCARD}), TileFilters.FILTER_ADJACENT),
	 insult = new Card("Insult","Deal 1 damage to any tile <br/><br/><i>Words hurt too. Just not as much as sticks and stones.</i>",Arrays.asList(new ICardEffect[]{CardEffects.DAMAGE(1), CardEffects.DISCARD}),new FilterIdentity()),
	 egocentricity = new Card("Egocentricity", "Rotate tiles adjacent to you one step clockwise <br/><br/><i>The world revolves around me! </i>", Arrays.asList(new ICardEffect[]{CardEffects.ROTATE(1),CardEffects.DISCARD}),TileFilters.FILTER_SELF);
}
