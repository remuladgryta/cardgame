package com.remuladgryta.cardgame.content;

import java.util.Arrays;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.ICardEffect;

public class Cards {
	public static Card
	 move = new Card("Move", "Move one step", Arrays.asList(new ICardEffect[]{CardEffects.MOVE, CardEffects.DISCARD}), TileFilters.FILTER_ADJACENT),
	 attack = new Card("Attack", "Attack a hex adjacent to you", Arrays.asList(new ICardEffect[]{CardEffects.DAMAGE(2), CardEffects.DISCARD}), TileFilters.FILTER_ADJACENT);
}
