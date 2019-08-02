package model.events;

import java.util.EventListener;

import simulation.Rescuable;

public interface SOSListener extends EventListener {
	public void receiveSOSCall(Rescuable r);
}
