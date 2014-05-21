package com.remuladgryta.cardgame;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.remuladgryta.cardgame.event.HandChangedEvent;

public class Hand {
	List<Card> cards = new LinkedList<Card>();
	Player player;
	private int maxSize = 8;
	public Hand(Player p){
		player = p;
	}
	
	public int getSize(){
		return cards.size();
	}
	
	public boolean putCard(Card card){
		if(cards.size() >= maxSize){
			return false;
		}else{
			cards.add(card);
			onChange();
			return true;
		}
	}
	public boolean removeCard(Card card){
		for(int i =0;i<cards.size();i++){
			if(cards.get(i) == card){
				cards.remove(i);
				onChange();
				return true;
			}
		}
		return false;
	}
	
	public Card getCard(int index){
		return cards.get(index);
	}
	
	private void onChange(){
		player.getEngine().getEventDispatch().dispatch(new HandChangedEvent(this));
	}
}
