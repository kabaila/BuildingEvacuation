package com.utbm.info.vi51.buildingEvac.utils;

import java.util.Random;

public class RandomUtils {
	public static float randomNumber() {
	    Random generator = new Random();
	    float num = generator.nextFloat();

	    return num;
	}
	
	public static float randomNumber(float minimum, float maximum) {
		return minimum + randomNumber() * maximum;
	}
	
	public static float randomBinomial(float value) {
		return randomNumber(0,value)-randomNumber(0,value);
	}
        
        
        public static int getRand(int max, int min) {
            return min + (int) (Math.random() * ((max - min) + 1));
        }

        public static int getRand(int max) {
            return getRand(max, 0);
        }
}
