package com.remuladgryta.cardgame.content;

import java.util.Arrays;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.ICardEffect;
import com.remuladgryta.cardgame.filter.FilterIdentity;
import com.remuladgryta.cardgame.filter.TileFilter;

public class Cards {
	public static Card move(){ return new Card("Move", "Move to an adjacent tile", Arrays.asList(new ICardEffect[]{CardEffects.MOVE, CardEffects.DISCARD}), TileFilters.FILTER_ADJACENT);}
	 public static Card attack(){ return new Card("Attack", "Deal 2 damage to an adjacent tile", Arrays.asList(new ICardEffect[]{CardEffects.DAMAGE(2), CardEffects.DISCARD}), TileFilters.FILTER_ADJACENT);}
	 public static Card insult(){ return new Card("Insult","Deal 1 damage to any tile <br/><br/><i>Words hurt too. Just not as much as sticks and stones.</i>",Arrays.asList(new ICardEffect[]{CardEffects.DAMAGE(1), CardEffects.DISCARD}),new FilterIdentity());}
	 public static Card egocentricity(){ return new Card("Egocentricity", "Rotate tiles adjacent to you one step clockwise <br/><br/><i>The world revolves around me! </i>", Arrays.asList(new ICardEffect[]{CardEffects.ROTATE(1),CardEffects.DISCARD}),TileFilters.FILTER_SELF);}
	 public static Card debug(){return new Card("dummy", "desc", Arrays.asList(new ICardEffect[]{CardEffects.WALL}), new FilterIdentity()); }
}
