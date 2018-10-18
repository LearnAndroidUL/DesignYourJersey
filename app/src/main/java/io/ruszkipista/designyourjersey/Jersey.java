package io.ruszkipista.designyourjersey;

import android.graphics.Color;

import java.text.DateFormat;
import java.util.GregorianCalendar;

/**
 * Created by Istvan Orosz on 18 Oct 2018
 */
public class Jersey {
    public enum JerseyColor {GREEN,PURPLE};
    private String mName;
    private int mNumber;
    private JerseyColor mColor;

    public Jersey() {
        mName = "Android";
        mNumber = 17;
        mColor = JerseyColor.GREEN;
    }

    public Jersey(String name, int quantity, JerseyColor color) {
        mName = name;
        mNumber = quantity;
        mColor = color;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) { mNumber = number;}

    public String getColorString() {return mColor.toString(); }

    public void setColor(JerseyColor color) {
        mColor = color;
    }
}