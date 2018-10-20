package io.ruszkipista.designyourjersey;

import android.graphics.Color;

import java.text.DateFormat;
import java.util.GregorianCalendar;

/**
 * Created by Istvan Orosz on 18 Oct 2018
 */
public class Jersey {
    private String mName;
    private int mNumber;
    private int mImageResourceId;

    public Jersey() {
        mName = "";
        mNumber = 0;
        mImageResourceId = 0;
    }

    public Jersey(String name, int quantity, int imageResourceId) {
        mName = name;
        mNumber = quantity;
        mImageResourceId = imageResourceId;
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

    public int getImageResourceId() {return mImageResourceId;}

    public void setPictureResourceId(int imageResourceId) {mImageResourceId = imageResourceId;}
}
