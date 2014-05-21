package com.remuladgryta.cardgame.event;

public interface EventListener<T extends IEvent> {
	void handleEvent(T event);
}
