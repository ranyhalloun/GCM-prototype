package application;

import java.io.Serializable;
import java.util.ArrayList;

public class arrayOfStrings implements Serializable{
    private ArrayList<String> arrayList;

    public arrayOfStrings() {
        arrayList = new ArrayList<String>();
    }
    public ArrayList<String> getArrayList() {
        return this.arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
}