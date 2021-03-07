package hu.bme.mit.train.tachograph;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.time.LocalTime;

public class TrainTachograph {

    private Table<LocalTime, Integer, Integer> tachograph;

    public TrainTachograph()
    {
        tachograph = HashBasedTable.create();
    }

    public void add(LocalTime currentTime, int joystickPos, int speed)
    {
        tachograph.put(currentTime, joystickPos, speed);
    }

    public int getSize()
    {
        return tachograph.size();
    }
}
