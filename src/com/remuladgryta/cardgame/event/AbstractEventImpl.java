package com.remuladgryta.cardgame.event;

public class AbstractEventImpl implements IEvent { //TODO make existing events extend this where it makes sense.

	protected boolean canceled=false;
	@Override
	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void cancel() {
		canceled = true;
	}

}
