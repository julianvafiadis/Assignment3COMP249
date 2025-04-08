package FileClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeData {

    private String tradeDataName;
    private String country;
    protected String category;
    private double price;

    public TradeData(){

    }

    public TradeData(String tradeDataName, String country, String category, double price) {
        this.tradeDataName = tradeDataName;
        this.country = country;
        this.category = category;
        this.price = price;
        tariffAdjustments();
    }

    public TradeData(TradeData tradeData){
        this.tradeDataName = tradeData.tradeDataName;
        this.country = tradeData.country;
        this.category = tradeData.category;
        this.price = tradeData.price;
        tariffAdjustments();
    }

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

    @Override
    public String toString(){
        String roundedUpdatedPrice = String.format("%.2f", price);
        return ("Trade Data --> Product: " + tradeDataName + ", Country: " + country + ", Category: " + category
                + ", Initial Price: $" + price + ", Updated Price: $" + roundedUpdatedPrice);
    }

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

    public void tariffAdjustments(){
        if(getCountry().toLowerCase().trim().equals("china")){
            price = price *1.25;
        }
        else if(getCountry().toLowerCase().trim().equals("usa")&&getCategory().toLowerCase().trim().equals("electronics")){
            price = price *1.10;
        }
        else if(getCountry().toLowerCase().trim().equals("japan")&&getCategory().toLowerCase().trim().equals("automobile")){
            price = price *1.15;
        }
        else if(getCountry().toLowerCase().trim().equals("india")&&getCategory().toLowerCase().trim().equals("agriculture")){
            price = price *1.05;
        }
        else if(getCountry().toLowerCase().trim().equals("south korea")&&getCategory().toLowerCase().trim().equals("electronics")){
            price = price *1.08;
        }
        else if(getCountry().toLowerCase().trim().equals("saudi arabia")&&getCategory().toLowerCase().trim().equals("energy")){
            price = price *1.12;
        }
        else if(getCountry().toLowerCase().trim().equals("germany")&&getCategory().toLowerCase().trim().equals("manufacturing")){
            price = price *1.06;
        }
        else if(getCountry().toLowerCase().trim().equals("bangladesh")&&getCategory().toLowerCase().trim().equals("textile")){
            price = price *1.04;
        }
        else if(getCountry().toLowerCase().trim().equals("brazil")&&getCategory().toLowerCase().trim().equals("agriculture")){
            price = price *1.09;
        }
        else{
            price = price;
        }
        price = Double.parseDouble(String.format("%.2f", price));
        setPrice(price);
    }

    //File I/O
    private Scanner reader = null;
    private PrintWriter writer = null;
    private String line = null;
    private static int LinesCounter = 0;
    private ArrayList<TradeData> products = new ArrayList<>();

    public void readTradeData() throws FileNotFoundException, NullPointerException {
        reader = new Scanner(new FileInputStream("src/RequiredFiles/TradeData.txt"));
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            String[] splitString = line.split(",");
            if(splitString.length == 4){
                String name = splitString[0];
                String country = splitString[1];
                String category = splitString[2];
                Double price = Double.parseDouble(splitString[3]);
                TradeData newTradeData = new TradeData(name, country, category, price);
                products.add(newTradeData);
                System.out.println(newTradeData);
                LinesCounter++;
            }
            else{
                System.out.println("Skipping malformed line: " + line);
            }
        }
        System.out.println("--------------------------------------------------> Lines read: " + LinesCounter + " <--------------------------------------------------");
        reader.close();
    }

    public void sortProductsByName() {
        for (int i = 0; i < products.size() - 1; i++) {
            for (int j = i + 1; j < products.size(); j++) {
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

    public void writeTradeData() throws FileNotFoundException {
        sortProductsByName();
        writer = new PrintWriter(new FileOutputStream("src/RequiredFiles/UpdatedTradeData.txt"));
        for(TradeData trade: products){
            System.out.println(trade);
            writer.println(trade.getTradeDataName() + "," + trade.getCountry() + "," + trade.getCategory() + "," + trade.getPrice());
        }
        System.out.println("------------------------------------> Successfully wrote to UpdatedTradeData.txt <------------------------------------");
        writer.close();
    }
}
