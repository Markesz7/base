package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.util.Timer;
import java.util.TimerTask;

public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;
	private boolean timerExists = false;
	private Timer timer;
	private boolean alarmState = false;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
		timer = new Timer();
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

		if(!timerExists)
		{
			TimerTask tt = new TimerTask() {
				@Override
				public void run() {
					controller.followSpeed();
				}
			};
			timer.scheduleAtFixedRate(tt, 0, 1000);
			timerExists = true;
		}
	}

	@Override
	public boolean getAlarmState() {
		return alarmState;
	}

	@Override
	public void setAlarmState(boolean alarmState) {
		this.alarmState = alarmState;
	}
}
