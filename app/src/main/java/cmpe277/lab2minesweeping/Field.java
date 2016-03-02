package cmpe277.lab2minesweeping;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;

/**
 * Created by yunlongxu on 3/1/16.
 */
public class Field extends Button {

    private FieldData fieldData;
    public Field(Context context) {
        super(context);
        this.setBackgroundResource(R.drawable.square_blue);
        setBoldFont();
    }

    public void setData(FieldData fieldData) {
        this.fieldData = fieldData;
    }

    public void init() {
        if (fieldData.isInvisible()) {
            return;
        }
        setField(false);
        fieldData.setIsInvisible(false);
        if (isBomb()) {
            setBombField(false);
        } else {
            setNumOfMinesAroundCurrentSpot(fieldData.getNumOfMinesAround());
        }
    }

    public void setBoldFont() {
        this.setTypeface(null, Typeface.BOLD);
    }

    public void setNumOfMinesAroundCurrentSpot(int num) {
        this.setBackgroundResource(R.drawable.square_grey);
        mineNumberUpdate(num);
    }

    public void mineNumberUpdate(int num) {
        if (num != 0) {
            this.setText(Integer.toString(num));
            switch (num) {
                case 1:
                    this.setTextColor(Color.BLUE);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
                case 2:
                    this.setTextColor(Color.WHITE);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
                case 3:
                    this.setTextColor(Color.RED);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
                case 4:
                    this.setTextColor(Color.GREEN);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
                case 5:
                    this.setTextColor(Color.YELLOW);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
                case 6:
                    this.setTextColor(Color.CYAN);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
                case 7:
                    this.setTextColor(Color.BLACK);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
                case 8:
                    this.setTextColor(Color.MAGENTA);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
                case 9:
                    this.setTextColor(Color.LTGRAY);
                    this.setTextSize(9);
                    this.setGravity(Gravity.CENTER);
                    break;
            }
        }
    }

    public void setBombField(boolean enabled) {
        this.setText("M");
        if (!enabled) {
            this.setBackgroundResource(R.drawable.square_grey);
            this.setTextColor(Color.RED);
            this.setTextSize(9);
            this.setGravity(Gravity.CENTER);
        } else {
            this.setTextColor(Color.BLACK);
            this.setTextSize(9);
            this.setGravity(Gravity.CENTER);
        }
    }

    public void setField(boolean enabled) {
        if (!enabled) {
            this.setBackgroundResource(R.drawable.square_grey);
            this.setGravity(Gravity.CENTER);
        } else {
            this.setBackgroundResource(R.drawable.square_blue);
            this.setGravity(Gravity.CENTER);
        }
    }

    public void clearAll() {
        this.setText("");
    }

    public void setVisiable() {
        if (!fieldData.isInvisible()) {
            return;
        }
        setField(false);
        fieldData.setIsInvisible(false);
        if (isBomb()) {
            setBombField(false);
        } else {
            setNumOfMinesAroundCurrentSpot(fieldData.getNumOfMinesAround());
        }
    }

    public boolean isBomb() {
        return fieldData.isBomb();
    }

    public boolean isInvisible() {
        return fieldData.isInvisible();
    }

    public void setNumOfMinesAround(int num) {
        fieldData.setNumOfMinesAround(num);
    }

    public int getNumOfMinesAround() {
        return fieldData.getNumOfMinesAround();
    }

    public void plantBomb() {
        fieldData.setIsBomb(true);
    }

    public void touchBomb() {
        setBombField(true);
        this.setTextColor(Color.RED);
    }

    public boolean isClickable() {
        return fieldData != null ? fieldData.isClickable() : false;
    }

    public void setClickable(boolean clickable) {
        if (fieldData != null) {
            fieldData.setIsClickable(clickable);
        }
    }
}
