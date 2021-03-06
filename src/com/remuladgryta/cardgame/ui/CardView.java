package com.remuladgryta.cardgame.ui;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLEditorKit;

import com.remuladgryta.cardgame.Card;
import com.remuladgryta.cardgame.GameEngine;

public class CardView extends JEditorPane implements ListSelectionListener,
		HyperlinkListener, ListDataListener {

	private Card currentCard;
	private GameEngine engine;
	private boolean interactive = false;

	public boolean isInteractive() {
		return interactive;
	}

	public void setInteractive(boolean interactive) {
		this.interactive = interactive;
	}

	public GameEngine getEngine() {
		return engine;
	}

	public void setEngine(GameEngine engine) {
		this.engine = engine;
	}

	private JList<Card> cardList;

	public CardView() {
		super("text/html", "");
		((HTMLEditorKit) getEditorKit()).setAutoFormSubmission(false);
		addHyperlinkListener(this);
		setEditable(false);
	}

	public JList<Card> getCardList() {
		return cardList;
	}

	public void setCardList(JList<Card> cardList) {
		if (this.cardList != null) {
			this.cardList.removeListSelectionListener(this);
			this.cardList.getModel().removeListDataListener(this);
		}
		this.cardList = cardList;
		cardList.addListSelectionListener(this);
		cardList.getModel().addListDataListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent evt) {
		updateView();
	}

	private void updateView() {
		StringBuilder sb = new StringBuilder();
		currentCard = cardList.getSelectedValue();
		if (currentCard != null) {
			// Add card texts
			sb.append("<b>");
			sb.append(currentCard.getTitle());
			sb.append("</b> <br/>");
			sb.append(currentCard.getText());
			sb.append("<br/>");
			if (interactive) {
				// Add link to discard
				sb.append("<a href=\"http://discard\"> Discard </a>");
			}
		}
		setText(sb.toString());
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent event) {
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			if (event.getURL().getHost().equals("discard")) {
				engine.getCurrentPlayer().getHand().removeCard(currentCard);
			}
		}
	}

	@Override
	public void contentsChanged(ListDataEvent arg0) {
		updateView();
	}

	@Override
	public void intervalAdded(ListDataEvent arg0) {
		updateView();
	}

	@Override
	public void intervalRemoved(ListDataEvent arg0) {
		updateView();
	}
}
