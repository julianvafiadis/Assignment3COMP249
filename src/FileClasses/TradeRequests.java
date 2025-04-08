package FileClasses;

public class TradeRequests {

    final private String REQ = "REQ";
    private static int tradeRequestCounter = 1;
    private String tradeRequestCounterFormatted = String.format("%03d", tradeRequestCounter);
    private String tradeRequestNumber = REQ + tradeRequestCounterFormatted;
    private String tradeFromCountry;
    private String tradeToCountry;
    private String category;
    public static int tradeValue; // PRIVACY LEAK
    private int proposedTariff;

    public TradeRequests(){

    }

    public TradeRequests(String tradeFromCountry, String tradeToCountry, String category, int tradeValue, int proposedTariff) {
        this.tradeRequestNumber = String.format("REQ%03d", tradeRequestCounter++);
        this.tradeFromCountry = tradeFromCountry;
        this.tradeToCountry = tradeToCountry;
        this.category = category;
        this.proposedTariff = proposedTariff;
        this.tradeValue = tradeValue;
    }

    public TradeRequests(TradeRequests otherTradeRequests){
        this.tradeFromCountry = otherTradeRequests.tradeFromCountry;
        this.tradeToCountry = otherTradeRequests.tradeToCountry;
        this.category = otherTradeRequests.category;
        this.proposedTariff = otherTradeRequests.proposedTariff;
        this.tradeValue = otherTradeRequests.tradeValue;
    }

    public String getTradeRequestNumber() {
        return tradeRequestNumber;
    }
    public String getTradeFromCountry() {
        return tradeFromCountry;
    }
    public String getTradeToCountry() {
        return tradeToCountry;
    }
    public String getCategory() {
        return category;
    }
    public int getTradeValue() {
        return tradeValue;
    }
    public int getProposedTariff() {
        return proposedTariff;
    }

    public void setTradeFromCountry(String tradeFromCountry) {
        this.tradeFromCountry = tradeFromCountry;
    }
    public void setTradeToCountry(String tradeToCountry) {
        this.tradeToCountry = tradeToCountry;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setTradeValue(int tradeValue) {
        this.tradeValue = tradeValue;
    }
    public void setProposedTariff(int proposedTariff) {
        this.proposedTariff = proposedTariff;
    }

    @Override
    public String toString() {
        return "Trade Request --> Request ID: " + tradeRequestNumber +
                ", From: " + tradeFromCountry +
                ", To: " + tradeToCountry +
                ", Category: " + category +
                ", Trade Value: $" + tradeValue +
                ", Proposed Tariff: " + proposedTariff + "%";
    }

    @Override
    public boolean equals(Object otherObject){
        if(otherObject == null || !getClass().equals(otherObject.getClass())){
            return false;
        }
        else{
            TradeRequests otherTradeRequests = (TradeRequests) otherObject;
            return tradeFromCountry.equals(otherTradeRequests.tradeFromCountry) &&
                    tradeToCountry.equals(otherTradeRequests.tradeToCountry) &&
                    category.equals(otherTradeRequests.category) &&
                    proposedTariff == otherTradeRequests.proposedTariff &&
                    tradeValue == otherTradeRequests.tradeValue;
        }
    }

    @Override
    public TradeRequests clone(){
        return new TradeRequests(this);
    }
}
