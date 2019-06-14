package commands;

import java.io.Serializable;

public class GetOldPricesCommand implements Serializable{

    //output
    private String subscriptionPrice;
    private String oneTimePurchasePrice;

    public String getSubscriptionPrice() {
        return subscriptionPrice;
    }
    public void setSubscriptionPrice(String subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }
    public String getOneTimePurchasePrice() {
        return oneTimePurchasePrice;
    }
    public void setOneTimePurchasePrice(String oneTimePurchasePrice) {
        this.oneTimePurchasePrice = oneTimePurchasePrice;
    }
}