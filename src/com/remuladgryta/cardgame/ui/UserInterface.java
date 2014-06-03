package com.remuladgryta.cardgame.ui;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import com.remuladgryta.cardgame.GameEngine;
import com.remuladgryta.cardgame.GameMap;
import com.remuladgryta.cardgame.event.MapRenderStateChangedEvent;
import com.remuladgryta.hex.PixelCoord;
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
