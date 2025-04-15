package FileClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeData {

    private String tradeDataName; // declare trade data name as a private string
    private String country; // declare country as a private string
    private String category; // declare category as a private string
    private double price; // declare price as a private double

    // default constructor
    public TradeData(){

    }

    // parameterized constructor setter all values above to the ones in the parameters
    public TradeData(String tradeDataName, String country, String category, double price) {
        this.tradeDataName = tradeDataName;
        this.country = country;
        this.category = category;
        this.price = price;
        tariffAdjustments(); // calling the tariffAdjustments method
    }

    // copy constructor
    public TradeData(TradeData tradeData) {
        this.tradeDataName = tradeData.tradeDataName;
        this.country = tradeData.country;
        this.category = tradeData.category;
        this.price = tradeData.price;

        // Deep copy the list if needed
        this.products = new ArrayList<>();
        for (TradeData item : tradeData.products) {
            this.products.add(new TradeData(item)); // recursive copy
        }
    }

    //getters and setters
    public String getTradeDataName() {
        return tradeDataName;
    }

    public void setTradeDataName(String tradeDataName) {
        this.tradeDataName = tradeDataName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString
    @Override
    public String toString(){
        String roundedUpdatedPrice = String.format("%.2f", price);
        return ("Trade Data --> Product: " + tradeDataName + ", Country: " + country + ", Category: " + category
                + ", Initial Price: $" + price + ", Updated Price: $" + roundedUpdatedPrice);
    }

    //equals
    @Override
    public boolean equals(Object otherObject){
        if(otherObject == null || this.getClass()!=otherObject.getClass()){
            return false;
        }
        else{
            TradeData otherTradeData = (TradeData) otherObject;
            return (tradeDataName.equals(otherTradeData.tradeDataName) &&
                    country.equals(otherTradeData.country) &&
                    category.equals(otherTradeData.category) &&
                    price == (otherTradeData.price));
        }
    }

    // tariff adjustments
    public void tariffAdjustments(){ // trimming and lowercasing every word
        // if country is china set price adjustment
        if(getCountry().toLowerCase().trim().equals("china")){
            price = price *1.25;
        }
        // if country is usa, and category is electronics set price adjustment
        else if(getCountry().toLowerCase().trim().equals("usa")&&getCategory().toLowerCase().trim().equals("electronics")){
            price = price *1.10;
        }
        // if country is japan, and category is automobiles set price adjustment
        else if(getCountry().toLowerCase().trim().equals("japan")&&getCategory().toLowerCase().trim().equals("automobiles")){
            price = price *1.15;
        }
        // if country is india, and category is agriculture set price adjustment
        else if(getCountry().toLowerCase().trim().equals("india")&&getCategory().toLowerCase().trim().equals("agriculture")){
            price = price *1.05;
        }
        // if country is south korea, and category is electronics set price adjustment
        else if(getCountry().toLowerCase().trim().equals("south korea")&&getCategory().toLowerCase().trim().equals("electronics")){
            price = price *1.08;
        }
        // if country is saudi arabia, and category is energy set price adjustment
        else if(getCountry().toLowerCase().trim().equals("saudi arabia")&&getCategory().toLowerCase().trim().equals("energy")){
            price = price *1.12;
        }
        // if country is germany, and category is manufacturing set price adjustment
        else if(getCountry().toLowerCase().trim().equals("germany")&&getCategory().toLowerCase().trim().equals("manufacturing")){
            price = price *1.06;
        }
        // if country is bangladesh, and category is textile set price adjustment
        else if(getCountry().toLowerCase().trim().equals("bangladesh")&&getCategory().toLowerCase().trim().equals("textile")){
            price = price *1.04;
        }
        // if country is brazil, and category is agriculture set price adjustment
        else if(getCountry().toLowerCase().trim().equals("brazil")&&getCategory().toLowerCase().trim().equals("agriculture")){
            price = price *1.09;
        }
        price = Double.parseDouble(String.format("%.2f", price)); // set the price to fit the format
        setPrice(price); // set the price using the setter to the adjusted price
    }

    //File IO
    private Scanner reader = null; // declare private Scanner
    private PrintWriter writer = null; // declare private PrintWriter
    private String line = null; // declare private string line and initialize to null
    private static int LinesCounter = 0; // declare static private int linesCounter and initialize to 0
    private ArrayList<TradeData> products = new ArrayList<>(); // declare and initialize private ArrayList of TradeData object and call it products

    // getter for arrayList
    public ArrayList<TradeData> getProducts() {
        return products;
    }

    // read trade data
    public void readTradeData() throws FileNotFoundException, NullPointerException { // throws two exceptions
        reader = new Scanner(new FileInputStream("src/RequiredFiles/TradeData.txt")); // initialize the reader to read from TradeData.txt file
        while (reader.hasNextLine()) { // loop while reader has a next line
            line = reader.nextLine(); // set the string line to reader.nextLine to read the next line
            String[] splitString = line.split(","); // split each variable at a comma
            if(splitString.length == 4){ // check if length of string is 4, if so...
                String name = splitString[0]; // declare and initialize String name to the first value of splitString
                String country = splitString[1]; // declare and initialize String country to the second value of splitString
                String category = splitString[2]; // declare and initialize String category to the third value of splitString
                Double price = Double.parseDouble(splitString[3]); // declare and initialize Double price to the fourth value of splitString and parse it
                TradeData newTradeData = new TradeData(name, country, category, price); // create a new TradeData object with the captured values
                products.add(newTradeData); // store them in the arraylist
                System.out.println(newTradeData); // print the new value
                LinesCounter++; // increment the counter
            }
            else{ // otherwise, skip wrongly formatted line
                System.out.println("Skipping malformed line: " + line);
            }
        }
        // print lines read
        System.out.println("--------------------------------------------------> Lines read: " + LinesCounter + " <--------------------------------------------------");
        reader.close(); // close reader
    }

    // sort products by name
    public void sortProductsByName() {
        for (int i = 0; i < products.size() - 1; i++) { // for loop
            for (int j = i + 1; j < products.size(); j++) { // nested for loop
                String name1 = products.get(i).getTradeDataName();
                String name2 = products.get(j).getTradeDataName();
                if (name1.compareToIgnoreCase(name2) > 0) {
                    // Swap the objects
                    TradeData temp = products.get(i);
                    products.set(i, products.get(j));
                    products.set(j, temp);
                }
            }
        }
    }

    // write the data in a new file
    public void writeTradeData() throws FileNotFoundException { // throws one exception
        sortProductsByName(); // sort products by name
        writer = new PrintWriter(new FileOutputStream("src/RequiredFiles/UpdatedTradeData.txt")); // initialize writer to write in new UpdatedTradeData.txt file
        for(TradeData trade: products){ // for loop looping through all values of products arrayList
            // print each value
            System.out.println(trade);
            writer.println(trade.getTradeDataName() + "," + trade.getCountry() + "," + trade.getCategory() + "," + trade.getPrice());
        }
        // print a finishing statement
        System.out.println("------------------------------------> Successfully wrote to UpdatedTradeData.txt <------------------------------------");
        writer.close(); // close the reader
    }
}