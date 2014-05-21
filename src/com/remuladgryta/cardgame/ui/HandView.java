package com.remuladgryta.cardgame.ui;

import javax.swing.AbstractListModel;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import net.miginfocom.swing.MigLayout;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.GameEngine;
import com.remuladgryta.cardgame.Hand;
import com.remuladgryta.cardgame.event.EventListener;
import com.remuladgryta.cardgame.event.HandChangedEvent;
import com.remuladgryta.cardgame.event.PlayerStartTurnEvent;

public class HandView extends JPanel implements
		EventListener<PlayerStartTurnEvent> {
	private final int ROWS_VISIBLE = 6;
	private GameEngine engine;

	private class HandModel extends AbstractListModel<Card> implements
			EventListener<HandChangedEvent> {
		private Hand hand;
		private int oldSize = 0;

		public void setHand(Hand hand) {
			this.hand = hand;
			fireContentsChanged(this, 0, getSize() - 1);
			if (oldSize > getSize())
				fireIntervalRemoved(this, getSize(), oldSize - 1);
			oldSize = getSize();
		}

		@Override
		public Card getElementAt(int index) {
			if (hand == null) {
				return null;
			}
			return hand.getCard(index);
		}

		@Override
		public int getSize() {
			if (hand == null) {
				return 0;
			}
			return hand.getSize();
		}

		@Override
		public void handleEvent(final HandChangedEvent event) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setHand(event.getHand());
					handList.clearSelection();
				}
			});
		}
	};

	HandModel model = new HandModel();
	private JList<Card> handList = new JList<Card>(model);
	private CardView cardText = new CardView();

	public HandView() {
		super();
		this.setLayout(new MigLayout());

		handList.setVisibleRowCount(ROWS_VISIBLE);
		handList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(handList), "grow, push");

		cardText.setCardList(handList);
		cardText.setInteractive(true);
		// cardText.setText("Lorem ipsum dolor sit amet.");
		add(new JScrollPane(cardText), "grow, push");

	}

	public void setEngine(GameEngine engine) {
		this.engine = engine;
		engine.getEventDispatch().addListener(this, PlayerStartTurnEvent.class);
		engine.getEventDispatch().addListener(model, HandChangedEvent.class);
		cardText.setEngine(engine);
	}

	@Override
	public void handleEvent(final PlayerStartTurnEvent event) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				model.setHand(event.getPlayer().getHand());
			}
		});
	}
}
