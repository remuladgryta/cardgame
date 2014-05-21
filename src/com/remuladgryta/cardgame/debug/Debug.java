package com.remuladgryta.cardgame.debug;

import com.remuladgryta.cardgame.Player;
import com.remuladgryta.cardgame.event.EventDispatch;
import com.remuladgryta.cardgame.event.EventListener;
import com.remuladgryta.cardgame.event.PlayerEvent;
import com.remuladgryta.cardgame.event.PlayerStartTurnEvent;

public class Debug {
	private static class dbgevt{
		static EventDispatch d = new EventDispatch();
		static boolean fired1, fired2;
		static void fire1(){
			fired1=true;
			System.out.println("fired PlayerEvent");
		}
		static void fire2(){
			fired2=true;
			System.out.println("fired PlayerStartTurnEvent");
		}
		public static void run(){
			//Add listeners
			EventListener<PlayerEvent> listen1 = new EventListener<PlayerEvent>(){
				@Override
				public void handleEvent(PlayerEvent event) {
					fire1();
				}
			};
			d.addListener(listen1, PlayerEvent.class);
			EventListener<PlayerStartTurnEvent> listen2 = new EventListener<PlayerStartTurnEvent>(){

				@Override
				public void handleEvent(PlayerStartTurnEvent event) {
					fire2();
				}
				
			};
			d.addListener(listen2, PlayerStartTurnEvent.class);
			
			//Fire event
			d.dispatch(new PlayerStartTurnEvent(new Player(null)));
			
			//check results
			System.out.println(fired1 && fired2);
		}
	}
	
	public static void debugEvent(){
		dbgevt.run();
	}
}
