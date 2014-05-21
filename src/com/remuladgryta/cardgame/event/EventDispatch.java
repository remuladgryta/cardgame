package com.remuladgryta.cardgame.event;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//TODO this class is fugly, and in dire need of cleanup (especially with regards to generic types)
public class EventDispatch {
	private HashMap<Class<? extends IEvent>, List> listeners = new HashMap<Class<? extends IEvent>, List>();

	public <T extends IEvent> void addListener(EventListener<T> listener,
			Class<T> c) {
		List<EventListener<T>> listenersForC = listeners.get(c);
		if (listenersForC == null) {
			listenersForC = new LinkedList<EventListener<T>>();
			listeners.put(c, listenersForC);
		}
		listenersForC.add(listener);
	}
	
	public <T extends IEvent> void removeListener(EventListener <T> listener, Class<T> c){
		List<EventListener<T>> listenersForC = listeners.get(c);
		if (listenersForC != null) {
			listenersForC.remove(listener);
		}
	}

	public <T extends IEvent> boolean dispatch(T e) {
		Class<T> clazz = (Class<T>) e.getClass();
		return dispatchRecursive(e, clazz);
	}

	private <T extends IEvent> boolean dispatchRecursive(T e, Class<T> t) {
		List<EventListener<T>> listenersForE = listeners.get(t);
		if (listenersForE != null) {
			for (EventListener<T> listener : listenersForE) {
				listener.handleEvent(e);
				if (e.isCanceled()) {
					return false;
				}
			}
		}
		Class sup = t.getSuperclass();
		if (Arrays.asList(sup.getInterfaces()).contains(IEvent.class)) {
			// recursive case
			return dispatchRecursive(e, sup);
		} else {
			// base case
			return true;
		}

	}
}
