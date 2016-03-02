package cmpe277.lab2minesweeping;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yunlongxu on 3/2/16.
 */
public class FieldData implements Parcelable {
    private boolean isInvisible; // has the field been clicked
    private boolean isBomb; // is the bomb in this field
    private boolean isClickable; // can the field be clicked
    private int numOfMinesAround; // number of mines around the spot

    public FieldData() {
        setDefault();
    }

    public FieldData(boolean isInvisible, boolean isBomb, boolean isClickable, int numOfMinesAround) {
        this.isInvisible = isInvisible;
        this.isBomb = isBomb;
        this.isClickable = isClickable;
        this.numOfMinesAround = numOfMinesAround;
    }

    public void setDefault() {
        isInvisible = true;
        isBomb = false;
        isClickable = true;
        numOfMinesAround = 0;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public void setIsInvisible(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setIsBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setIsClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    public int getNumOfMinesAround() {
        return numOfMinesAround;
    }

    public void setNumOfMinesAround(int numOfMinesAround) {
        this.numOfMinesAround = numOfMinesAround;
    }

    public FieldData(Parcel in) {
        isInvisible = in.readInt() == 1 ? true : false;
        isBomb = in.readInt() == 1 ? true : false;
        isClickable = in.readInt() == 1 ? true : false;
        numOfMinesAround = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(isInvisible ? 1 : 0);
        dest.writeInt(isBomb ? 1 : 0);
        dest.writeInt(isClickable ? 1 : 0);
        dest.writeInt(numOfMinesAround);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FieldData createFromParcel(Parcel in) {
            return new FieldData(in);
        }

        public FieldData[] newArray(int size) {
            return new FieldData[size];
        }
    };
}
