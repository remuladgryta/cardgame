package com.remuladgryta.cardgame.event;

public interface IEvent {
	boolean isCanceled();
	public void cancel();
}
