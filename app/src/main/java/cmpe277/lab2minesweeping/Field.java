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

    private boolean isInvisible; // has the field been clicked
    private boolean isBomb; // is the bomb in this field
    private boolean isClickable; // can the field be clicked
    private int numOfMinesAround; // number of mines around the spot

    public Field(Context context) {
        super(context);
    }

    public void setDefault() {
        isInvisible = true;
        isBomb = false;
        isClickable = true;
        numOfMinesAround = 0;

        this.setBackgroundResource(R.drawable.square_blue);
        setBoldFont();
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
        if (!isInvisible) {
            return;
        }
        setField(false);
        isInvisible = false;
        if (isBomb()) {
            setBombField(false);
        } else {
            setNumOfMinesAroundCurrentSpot(numOfMinesAround);
        }
    }

    public boolean isBomb() {
        return isBomb;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setNumOfMinesAround(int num) {
        this.numOfMinesAround = num;
    }

    public int getNumOfMinesAround() {
        return numOfMinesAround;
    }

    public void plantBomb() {
        isBomb = true;
    }

    public void touchBomb() {
        setBombField(true);
        this.setTextColor(Color.RED);
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        this.isClickable = clickable;
    }

    public void setting(boolean isInvisible, boolean isBomb, boolean isClickable, int numOfMinesAround) {
        this.isInvisible = isInvisible;
        this.isBomb = isBomb;
        this.isClickable = isClickable;
        this.numOfMinesAround = numOfMinesAround;
        //this.setBackgroundResource(R.drawable.square_blue);
        setBoldFont();
    }
}
