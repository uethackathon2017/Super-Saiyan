package com.supersaiyan.englock.algorithm;

import com.supersaiyan.englock.model.Word;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by cuong on 3/12/2017.
 */
public class SRS {

    public static void main(String[] args) {
        Word w = new Word();
        w.setTitle("Fancy");
        w.setMean("Thich");
        w.setSample("I fancy Ly");

        for (int i = 1; i < 20; i++) {
            int q = new Random().nextInt(5);
            w = updateEFactor(w, q);

            System.out.println("_> " + i);
            System.out.println("Quality: " + q + "\n" + w);
        }

    }

    public static Word updateEFactor(Word word, int q) {
        // fresh word
        if (q < 3) {
            word.setEF(2.5f);
            word.setCurrentInterval(0);
            word.setNextDay(null);
        }

        float EF = word.getEF();
        int interval = word.getCurrentInterval();

        if (EF == 0.0f) {
            EF = 2.5f;
        }

        if (interval == 0) {
            interval = 1;
        } else if (interval == 1) {
            interval = 6;
        } else {
            EF = (float)(EF+(0.1-(5-q)*(0.08+(5-q)*0.02)));
            if (EF < 1.3f) {
                EF = 1.3f;
            }
            interval = (int)(interval * EF);
        }

        // update next date for showing
        Date nextDay = word.getNextDay();
        if (nextDay == null) {
            nextDay = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextDay);
        calendar.add(Calendar.DATE, interval);

        word.setCurrentInterval(interval);
        word.setEF(EF);
        word.setNextDay(calendar.getTime());

        return word;
    }

}
