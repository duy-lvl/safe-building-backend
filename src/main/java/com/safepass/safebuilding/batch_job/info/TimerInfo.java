package com.safepass.safebuilding.batch_job.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimerInfo {
    //how many times does the timer fire
    private int totalFireCount;
    //if true then the first value does not matter
    private boolean runForever;
    //the time in between starts, example set to 30s timer start every 30s
    private long repeatIntervalMs;
    //how long to start after start the timer
    private long initialOffsetMs;
    //pass some data
    private String callbackData;
    private int remainingFireCount;
}
