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
import com.remuladgryta.util.Config;

public class HandView extends JPanel implements
		EventListener<PlayerStartTurnEvent> {
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
					handList.clearSelection();
					setHand(event.getHand());
				}
			});
		}
	};

	HandModel model = new HandModel();
	private JList<Card> handList = new JList<Card>(model);
	private CardView cardText = new CardView();

	public HandView() {
		super();
		
		handList.setVisibleRowCount(Config.maxHandSize);
		handList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		handList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				onCardSelected();
			}});
		
		cardText.setInteractive(true);
		cardText.setCardList(handList);
		myLayout();
	}
	
	private void myLayout(){
		this.setLayout(new MigLayout());
		add(new JScrollPane(handList), "grow, push");
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
				handList.clearSelection();
			}
		});
	}
	
private void onCardSelected(){
	Card c = handList.getSelectedValue();
	if(c != null){
		c.selectForPlay(engine.getCurrentPlayer());
	}else{
		engine.clearReadiedCard();
	}
}
}
