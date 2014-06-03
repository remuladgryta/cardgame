package com.remuladgryta.cardgame.ui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.remuladgryta.cardgame.GameEngine;
import com.remuladgryta.cardgame.event.EventListener;
import com.remuladgryta.cardgame.event.GameOverEvent;
import com.remuladgryta.util.Config;

public class UserInterface {
	GameEngine engine;
	
	JFrame frame;
	
	JPanel contentpane;
	MapView mapView;
	HandView handPane;
	JPanel commandPane = new JPanel();
	StatusView statusPane = new StatusView();
	
	String layoutArgs = "";
	JButton turnButton;
	
	public UserInterface(GameEngine engine) {
		initialize();
		setEngine(engine);
		frame.setVisible(true);
	}
	
	public void setEngine(GameEngine engine){
		this.engine = engine;
		bindEngine();
	}
	
	private void bindEngine() {
		mapView.setMap(engine.getMap());
		handPane.setEngine(engine);
		statusPane.setEngine(engine);
		
		EventListener<GameOverEvent> gameOverListener = new EventListener<GameOverEvent>(){
			@Override
			public void handleEvent(GameOverEvent event) {
				if(event.getWinner()!=null){
					JOptionPane.showMessageDialog(frame, event.getWinner().getName()+" wins!",  "Game Over", JOptionPane.PLAIN_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(frame, "It's a draw!",  "Game Over", JOptionPane.PLAIN_MESSAGE);
				}
			}
		};
		
		engine.getEventDispatch().addListener(gameOverListener, GameOverEvent.class);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Cardgame");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setPreferredSize(new Dimension(1200,600));
		
		MigLayout layout;
		if(Config.debugUI){
			layout = new MigLayout("debug");
		}else{
			layout = new MigLayout();
		}
		frame.setLayout(layout);
		
		mapView = new MapView();
		frame.add(mapView, "grow, push");
		
		frame.add(statusPane, "grow, pushy, spany 2, wrap");
		
		handPane = new HandView();
		frame.add(handPane, "grow, wrap");
		
		frame.add(commandPane,"span");
		
		frame.pack();
	}
}
