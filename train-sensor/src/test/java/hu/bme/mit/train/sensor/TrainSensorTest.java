package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TrainSensorTest {

    private TrainController controller;
    private TrainUser user;
    private TrainSensor sensor;

    @Before
    public void before() {
        controller = mock(TrainController.class);
        user = mock(TrainUser.class);
        sensor = new TrainSensorImpl(controller, user);
    }

    @Test
    public void OverridingSpeedlimit_AlarmIsNotSet() {
        when(controller.getReferenceSpeed()).thenReturn(200);
        sensor.overrideSpeedLimit(250);
        verify(user, never()).setAlarmState(true);
    }

    @Test
    public void OverridingSpeedlimit_UpperBoundaryTest_AlarmIsSetOnce() {
        sensor.overrideSpeedLimit(500);
        sensor.overrideSpeedLimit(501);
        verify(user, times(1)).setAlarmState(true);
    }

    @Test
    public void OverridingSpeedlimit_LowerBoundaryTest_AlarmIsSetOnce() {
        sensor.overrideSpeedLimit(0);
        sensor.overrideSpeedLimit(-1);
        verify(user, times(1)).setAlarmState(true);
    }

    @Test
    public void OverridingSpeedlimit_SpeedlimitIsMuchLowerThanReferenceSpeed_AlarmIsSetOnce() {
        when(controller.getReferenceSpeed()).thenReturn(150);
        sensor.overrideSpeedLimit(50);
        verify(user, times(1)).setAlarmState(true);
    }
}
