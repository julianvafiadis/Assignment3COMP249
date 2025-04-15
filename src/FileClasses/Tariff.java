package FileClasses;

public class Tariff implements Cloneable{

    private String destinationCountry; //declare a private destinationCountry as a String
    private String originCountry; //declare a private originCountry as a String
    private String productCategory; //declare a private productCategory as a String
    private int minimumTariff; //declare a private minimumTariff as an int

    public Tariff(String destinationCountry, String originCountry, String productCategory, int minimumTariff) { // constructor with all the values above
        this.destinationCountry = destinationCountry; // set this destination country to the one entered as a parameter
        this.originCountry = originCountry; // set this origin country to the one entered as a parameter
        this.productCategory = productCategory; // set this product category to the one entered as a parameter
        this.minimumTariff = minimumTariff; // set this minimum tariff to the one entered as a parameter
    }

    public Tariff(Tariff otherTariff){ // copy constructor (deep copy)
        this.destinationCountry = otherTariff.destinationCountry;
        this.originCountry = otherTariff.originCountry;
        this.productCategory = otherTariff.productCategory;
        this.minimumTariff = otherTariff.minimumTariff;
    }

    //getters
    public String getDestinationCountry() {return destinationCountry;}
    public String getOriginCountry() {
        return originCountry;
    }
    public String getProductCategory() {
        return productCategory;
    }
    public int getMinimumTariff() {
        return minimumTariff;
    }

    //setters
    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
    public void setMinimumTariff(int minimumTariff) {
        this.minimumTariff = minimumTariff;
    }

    //toString
    @Override
    public String toString(){
        return "Tariff --> Origin Country: " + originCountry +
                ", Destination Country: " + destinationCountry +
                ", Product Category: " + productCategory +
                ", Minimum Tariff: " + minimumTariff;
    }

    //equals
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

    //clone
    @Override
    public Tariff clone(){
        return new Tariff(this);
    }
}
