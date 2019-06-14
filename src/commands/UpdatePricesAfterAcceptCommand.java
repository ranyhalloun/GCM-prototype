package commands;

import java.io.Serializable;

public class UpdatePricesAfterAcceptCommand implements Serializable{

    //input
    private String newSubsPrice;
    private String newOnePrice;

    public UpdatePricesAfterAcceptCommand(String newSubsPrice, String newOnePrice) {
        this.newSubsPrice = newSubsPrice;
        this.newOnePrice = newOnePrice;
    }

    public String getNewSubsPrice() {
        return newSubsPrice;
    }

    public void setNewSubsPrice(String newSubsPrice) {
        this.newSubsPrice = newSubsPrice;
    }

    public String getNewOnePrice() {
        return newOnePrice;
    }

    public void setNewOnePrice(String newOnePrice) {
        this.newOnePrice = newOnePrice;
    }
}