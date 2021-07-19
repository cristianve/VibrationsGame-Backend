package com.vibration.backend.domain.services.score;

import com.vibration.backend.domain.model.PlayUserData;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CalculateScore {

    private static final Integer MAX_POINTS = 100;

    public Integer execute(PlayUserData playUserData) {
        Duration figureCompletionTime = playUserData.getFigureCompletionTime();
        long durationInMillis = figureCompletionTime.toMillis();

        //First two seconds will obtain max points
        if (durationInMillis < 2000) {
            return MAX_POINTS;
        }
        else {
            durationInMillis = durationInMillis - 2000;
        }

        //Reduce points from 2 until 12 seconds
        var pointsToReduce = Math.toIntExact(durationInMillis / 100);
        var pointsObtained = MAX_POINTS - pointsToReduce;

        //More than 12 seconds will get you 1 point
        if (pointsObtained <= 0) {
            pointsObtained = 1;
        }

        return pointsObtained;
    }
}
