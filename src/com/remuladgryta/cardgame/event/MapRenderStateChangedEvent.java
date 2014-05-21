package com.remuladgryta.cardgame.event;


public class MapRenderStateChangedEvent implements IEvent{
	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void cancel() {
	}

}
