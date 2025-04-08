package FileClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Tariff implements Cloneable{

    private String destinationCountry;
    private String originCountry;
    private String productCategory;
    private int minimumTariff;

    private Scanner reader = null;
    private PrintWriter writer = null;
    private String line = null;
    private int LinesCounter = 0;

    public Tariff(String destinationCountry, String originCountry, String productCategory, int minimumTariff) {
        this.destinationCountry = destinationCountry;
        this.originCountry = originCountry;
        this.productCategory = productCategory;
        this.minimumTariff = minimumTariff;
    }

    public Tariff(){

    }

    public Tariff(Tariff otherTariff){
        this.destinationCountry = otherTariff.destinationCountry;
        this.originCountry = otherTariff.originCountry;
        this.productCategory = otherTariff.productCategory;
        this.minimumTariff = otherTariff.minimumTariff;
    }

    public String getDestinationCountry() {

        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getMinimumTariff() {
        return minimumTariff;
    }

    public void setMinimumTariff(int minimumTariff) {
        this.minimumTariff = minimumTariff;
    }

    @Override
    public String toString(){
        return "Tariff --> Origin Country: " + originCountry +
                ", Destination Country: " + destinationCountry +
                ", Product Category: " + productCategory +
                ", Minimum Tariff: " + minimumTariff;
    }

    @Override
    public boolean equals(Object otherObject){
        if(otherObject == null || !getClass().equals(otherObject.getClass())){
            return false;
        }
        else{
            Tariff otherTariff = (Tariff) otherObject;
            return destinationCountry.equals(otherTariff.destinationCountry) &&
                    originCountry.equals(otherTariff.originCountry) &&
                    productCategory.equals(otherTariff.productCategory) &&
                    minimumTariff == otherTariff.minimumTariff;
        }
    }
    @Override
    public Tariff clone(){
        return new Tariff(this);
    }
}
