package FileClasses;

public class TradeRequests {

    final private String REQ = "REQ"; // declare and initialize final private String REQ to "REQ"
    private static int tradeRequestCounter = 1; // declare and initialize private static int traderequestcoutner to 1
    private final String tradeRequestCounterFormatted = String.format("%03d", tradeRequestCounter); // declare and initialize private final string traderequestcounterformatted to traderequest counter, but formatted to match the instructions
    private String tradeRequestNumber = REQ + tradeRequestCounterFormatted; // store the two above values in the declared and initialized String traderequestnumber
    private String tradeFromCountry; // declare private string origin
    private String tradeToCountry; // declare private string destination
    private String category; // declare private string category
    protected static int tradeValue; // declare protected static int tradevalue // PRIVACY LEAK: ANYONE CAN CHANGE THIS VALUE AT ANY TIME WITHOUT THE NEED OF A GETTER
    private int proposedTariff; // declare private int proposed tariff

    // default constructor which adds a trade request number to the trade request
    public TradeRequests(){
        this.tradeRequestNumber = String.format("REQ%03d", tradeRequestCounter++);
    }

    // parameterized constructor
    public TradeRequests(String tradeFromCountry, String tradeToCountry, String category, int tradeValue, int proposedTariff) {
        this.tradeRequestNumber = String.format("REQ%03d", tradeRequestCounter++);
        this.tradeFromCountry = tradeFromCountry;
        this.tradeToCountry = tradeToCountry;
        this.category = category;
        this.proposedTariff = proposedTariff;
        this.tradeValue = tradeValue;
    }

    // copy constructor which copies everything but the request number
    public TradeRequests(TradeRequests otherTradeRequests){
        this.tradeRequestNumber = String.format("REQ%03d", tradeRequestCounter++);
        this.tradeFromCountry = otherTradeRequests.tradeFromCountry;
        this.tradeToCountry = otherTradeRequests.tradeToCountry;
        this.category = otherTradeRequests.category;
        this.proposedTariff = otherTradeRequests.proposedTariff;
        this.tradeValue = otherTradeRequests.tradeValue;
    }

    //getters
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

    //setters
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

    //toString
    @Override
    public String toString() {
        return "Trade Request --> Request ID: " + tradeRequestNumber +
                ", From: " + tradeFromCountry +
                ", To: " + tradeToCountry +
                ", Category: " + category +
                ", Trade Value: $" + tradeValue +
                ", Proposed Tariff: " + proposedTariff + "%";
    }

    //equals method
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

    //clone
    @Override
    public TradeRequests clone(){
        return new TradeRequests(this);
    }
}