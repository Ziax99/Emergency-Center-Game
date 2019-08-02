package model.events;

import java.util.EventListener;

import simulation.Simulatable;

public interface WorldListener extends EventListener{
	public void assignAddress(Simulatable sim, int x , int y);
}
