package com.remuladgryta.cardgame;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.remuladgryta.cardgame.debug.Debug;
import com.remuladgryta.cardgame.ui.UserInterface;
import com.remuladgryta.util.Config;

public class launcher {
	public static void main(String[] args) {
		if (Config.debugEvent) {
			Debug.debugEvent();
		}

		final GameEngine engine = new GameEngine(2);

		Runnable r = new Runnable() {
			public void run() {
				synchronized (this) {
					try {
						UIManager.setLookAndFeel(UIManager
								.getSystemLookAndFeelClassName());
						UserInterface window = new UserInterface(engine);
						this.notify();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		EventQueue.invokeLater(r);
		synchronized (r) {
			try {
				r.wait(50);
				engine.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
}
