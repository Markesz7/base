package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.util.Timer;
import java.util.TimerTask;

public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;
	private boolean timer_exists = false;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
	}

	@Override
	public boolean getAlarmFlag()
	{
		if(controller.isSpeedNotCorrect())
		{
			System.out.println("The speed is higher than the speedlimit!");
			return true;
		}
		return false;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {

		this.joystickPosition = joystickPosition;
		controller.setJoystickPosition(joystickPosition);

		if(!timer_exists)
		{
			Timer timer = new Timer();
			TimerTask tt = new TimerTask() {
				@Override
				public void run() {
					controller.followSpeed();
				}
			};
			timer.scheduleAtFixedRate(tt, 0, 1000);
			timer_exists = true;
		}
	}
}
