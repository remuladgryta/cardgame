package com.remuladgryta.cardgame.event;

import com.remuladgryta.cardgame.Hand;

public class HandChangedEvent implements IEvent {
	private Hand hand;
	public HandChangedEvent(Hand hand){
		this.hand = hand;
	}
	
	public Hand getHand(){
		return hand;
	}
	
	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void cancel() {
		//this event can't be cancelled.
	}

}
