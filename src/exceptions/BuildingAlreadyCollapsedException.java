package exceptions;

import model.disasters.Disaster;

public class BuildingAlreadyCollapsedException extends DisasterException {

	public BuildingAlreadyCollapsedException(Disaster disaster) {
		super(disaster);
		// TODO Auto-generated constructor stub
	}

	public BuildingAlreadyCollapsedException(Disaster disaster, String message) {
		super(disaster, message);
		// TODO Auto-generated constructor stub
	}

}
