package com.remuladgryta.cardgame.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.GameEngine;
import com.remuladgryta.cardgame.Player;
import com.remuladgryta.cardgame.event.CardPlayedEvent;
import com.remuladgryta.cardgame.event.EventListener;
import com.remuladgryta.cardgame.event.PlayerStartTurnEvent;

public class StatusView extends JPanel implements ActionListener {
	private final int ROWS_VISIBLE = 6;

	private GameEngine engine;

	DefaultListModel<Card> listModel = new DefaultListModel<Card>();
	private JList<Card> cardLog = new JList<Card>(listModel);
	private DefaultListModel<Player> playerListModel = new DefaultListModel<Player>();
	private JList<Player> playerList = new JList<Player>(playerListModel);
	private CardView cardText = new CardView();
	JButton turnButton = new JButton("End turn");

	public StatusView() {
		super();
		this.setLayout(new MigLayout());

		cardLog.setVisibleRowCount(ROWS_VISIBLE);
		cardLog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(cardLog), "grow, pushy, wrap");

		cardText.setCardList(cardLog);
		add(new JScrollPane(cardText), "grow, pushy, wrap");
		
		playerList.setEnabled(false);
		add(new JScrollPane(playerList), "grow, pushy, wrap");
		
		turnButton.addActionListener(this);
		add(turnButton);
	}

	public void setEngine(GameEngine engine) {
		this.engine = engine;
		cardText.setEngine(engine);
		for(Player p : engine.getPlayers()){
			playerListModel.addElement(p);
		}
		engine.getEventDispatch().addListener(cardPlayedListener,
				CardPlayedEvent.class);
		engine.getEventDispatch().addListener(playerStartTurnListener,
				PlayerStartTurnEvent.class);
	}

	private EventListener<CardPlayedEvent> cardPlayedListener = new EventListener<CardPlayedEvent>() {
		@Override
		public void handleEvent(final CardPlayedEvent event) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if (listModel.getSize() >= ROWS_VISIBLE) {
						listModel.remove(0);
					}
					listModel.addElement(event.getCard());
				}
			});
		}
	};

	private EventListener<PlayerStartTurnEvent> playerStartTurnListener = new EventListener<PlayerStartTurnEvent>() {

		@Override
		public void handleEvent(PlayerStartTurnEvent event) {
			playerList.setSelectedValue(event.getPlayer(), true);
		}
	};

	@Override
	public void actionPerformed(ActionEvent arg0) {
		engine.nextPlayer();
	}
}
