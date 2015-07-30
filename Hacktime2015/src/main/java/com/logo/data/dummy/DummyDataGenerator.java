package com.logo.data.dummy;

import java.util.Arrays;
import java.util.Collection;

import com.logo.domain.DashboardNotification;

public abstract class DummyDataGenerator {


    public static int[] randomSparklineValues(int howMany, int min, int max) {
        int[] values = new int[howMany];

        for (int i = 0; i < howMany; i++) {
            values[i] = (int) (min + (Math.random() * (max - min)));
        }

        return values;
    }

}