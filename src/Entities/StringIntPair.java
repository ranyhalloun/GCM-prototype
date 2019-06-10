package Entities;

import java.io.Serializable;


public class StringIntPair implements Serializable {
    private String string_field;
    private int int_field;
    
    public StringIntPair(String string_field, int int_field)
    {
        this.string_field = string_field;
        this.int_field = int_field;
    }
    
    //Getters
    public int getInt_field() {
        return int_field;
    }

    public String getString_field() {
        return string_field;
    }
    
    //Setters
    public void setString_field(String string_field) {
        this.string_field = string_field;
    }
    
    public void setInt_field(int int_field) {
        this.int_field = int_field;
    }
}
