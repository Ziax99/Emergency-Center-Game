package exceptions;

import model.disasters.Disaster;

public class CitizenAlreadyDeadException extends DisasterException {

	public CitizenAlreadyDeadException(Disaster disaster) {
		super(disaster);
		// TODO Auto-generated constructor stub
	}

	public CitizenAlreadyDeadException(Disaster disaster, String message) {
		super(disaster, message);
		// TODO Auto-generated constructor stub
	}
}
