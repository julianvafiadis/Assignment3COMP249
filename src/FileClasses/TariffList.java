
package FileClasses;

import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy{ // TariffList implements the TariffPolicy interface and represents a linked list of Tariff nodes

    // Instance variables for the head and tail nodes and the size of the list
    private TariffNode head;
    private TariffNode tail;
    private int size;

    // Default constructor: initializes an empty list
    public TariffList(){
        this.head = null;
        this.size = 0;
    }

    // Copy constructor: creates a shallow copy of another TariffList
    public TariffList(TariffList otherTariffList){
        this.head = otherTariffList.head;
        this.size = otherTariffList.size;
    }

    // Adds a new Tariff node to the start of the list
    public void addToStart(Tariff data){
        TariffNode newNode = new TariffNode(data, head);
        head = newNode;
        size++;
        if(size == 1){
            tail = head;
        }
    }

    // Inserts a Tariff node to the indicated index of the list
    public void insertAtIndex(Tariff data, int index){
        if(index < 0 || index > size){ // if index isn't valid, throw exception
            throw new NoSuchElementException("Invalid Index");
        }
        if(index == 0){ // if index is zero, add to start
            addToStart(data);
        }
        else if(index == size){ // if index = size, create a newNode with the data and a null link, if the tail is not = null, set the link to the newNode, and set tail = newNode
            TariffNode newNode = new TariffNode(data, null);
            if(tail != null){
                tail.setLink(newNode);
                tail = newNode;
            }
            else{ // else, head = tail = newNode
                head = tail = newNode;
            }
            size++; // increment size of list
        }
        else{ // otherwise, create a newNode with the data and a null link
            TariffNode newNode = new TariffNode(data, null);
            TariffNode position = head; //set position equals to head
            for (int i = 0; i < index-1; i++){ // increment and set position to position.getLink until we reach the indicated index
                position = position.getLink();
            }
            newNode.setLink(position.getLink()); // set the link of the newNode to position.getLink
            position.setLink(newNode); //set the link of the position to newNode
            size++; // increment
        }
    }

    // delete from the indicated index
    public void deleteFromIndex(int index) {
        if (head == null) { // if head = null throw an exception
            throw new NoSuchElementException("The list is empty, cannot delete from index " + index);
        }
        if (index < 0 || index >= size) { // if invalid index throw an exception
            throw new NoSuchElementException("Invalid index: " + index);
        }

        if (index == 0) { // if index is zero, delete the first node
            head = head.getLink();
            size--;
            if (size == 0) { // if size is zero, tail is set to null
                tail = null;
            }
            return; // return
        }

        // Deleting a node from the middle or end
        TariffNode position = head; // set position to be equal to head
        for (int i = 0; i < index - 1; i++) { // increment and set position to position.getLink until we reach the indicated index
            position = position.getLink();
        }

        // Position now points to the node before the one we want to delete
        TariffNode nodeToDelete = position.getLink();
        position.setLink(nodeToDelete.getLink()); // set the link of position to the link of nodeToDelete

        // If we deleted the last node, update the tail
        if (nodeToDelete == tail) {
            tail = position;
        }

        size--; // decrement size
    }

    // delete from start
    public void deleteFromStart() {
        if (head == null) { // if head is null, throw an exception
            throw new NoSuchElementException("The list is empty, cannot delete from start.");
        }
        if (head == tail) { // if head is tail, or size is 1, set head = tail = null (both null)
            head = tail = null;
        } else { // otherwise, head = head.getLink
            head = head.getLink();
        }
        size--; //decrement size
    }

    // replace at index
    public void replaceAtIndex(Tariff newTariff, int index) { // replace the node at the indicated index with the Tariff objet you have inserted
        if (index < 0 || index >= size) { // if index is invalid, return
            return;
        }
        TariffNode position = head; // else, create a new TariffNode called position and set it to equal head
        for (int i = 0; i < index; i++) { // increment and set position to position.getLink until we reach the indicated index
            position = position.getLink();
        }
        position.setData(newTariff); // set the data of position to be that of the tariff you would like to replace the current one
    }

    // find
    public Tariff find(String originCountry, String destinationCountry,  String productCategory) { // takes origin, destination, and category and tries to find a match in the list
        originCountry = originCountry.trim().toUpperCase(); // trim and push to upper case
        destinationCountry = destinationCountry.trim().toUpperCase(); // trim and push to upper case
        productCategory = productCategory.trim().toUpperCase(); // trim and push to upper case
        TariffNode position = head; // create a new TariffNode position and set it equal to head
        int iterations = 0; // set iterations to 0
        while (position != null) { // loop while position is not null
            iterations++; // increment iterations
            Tariff data = position.getData(); // create a new Tariff data and set it equal to position's data

            if (data.getDestinationCountry().trim().toUpperCase().equals(destinationCountry) && //if all is equal print iterations and return data
                    data.getOriginCountry().trim().toUpperCase().equals(originCountry) &&
                    data.getProductCategory().trim().toUpperCase().equals(productCategory)) {
                System.out.println("Iterations: " + iterations);
                return data;
            }
            position = position.getLink(); // set position to position.getLink
        }
        System.out.println("Iterations: " + iterations); // print iterations
        return null; // return null if data is not equal
    }

    // contains
    public boolean contains(String originCountry, String destinationCountry, String productCategory) { // takes origin, destination, category to see if list contains values
        originCountry = originCountry.trim().toUpperCase(); // trim and set to upper case
        destinationCountry = destinationCountry.trim().toUpperCase(); // trim and set to upper case
        productCategory = productCategory.trim().toUpperCase(); // trim and set to upper case

        TariffNode position = head; // create a new TariffNode position and st it equal to head
        while (position != null) { // loop while position is not null
            Tariff data = position.getData(); // create a new Tariff data and set it to position's data
            if (data.getDestinationCountry().trim().toUpperCase().equals(destinationCountry) &&
                    data.getOriginCountry().trim().toUpperCase().equals(originCountry) &&
                    data.getProductCategory().trim().toUpperCase().equals(productCategory)) {
                return true; // return true if all equal their respective category
            }
            position = position.getLink(); // else set position to position.getLink
        }
        return false; // and return false
    }

    // equals method
    public boolean equals(TariffList otherList) {
        if (otherList == null || this.size != otherList.size) {
            return false; // returns false if otherList is null or if the sizes aren't the same
        }
        TariffNode current = this.head; // create new TariffNode current and set it to this.head
        TariffNode otherCurrent = otherList.head; // create new TariffNode otherCurrent and set it to otherList.head
        while (current != null) { // loop while current is not null
            if (!current.getData().equals(otherCurrent.getData())) { // if both datas are not equal, return false
                return false;
            }
            current = current.getLink(); // else set current to current.getLink and otherCurrent to otherCurrent.geLink
            otherCurrent = otherCurrent.getLink();
        }
        return true; // and return true, meaning they are equal
    }

    // display
    public void display() {
        if (head == null) { // if head is null the list has no elements, display that
            System.out.println("The list has no elements.");
        } else { // else create a new Node called position and set it equal to head
            TariffNode position = head;
            while (position != null) { // loop until position is null
                Tariff tariff = position.getData();  // Get the Tariff object from the node
                System.out.println("Destination: " + tariff.getDestinationCountry() +
                        ", Origin: " + tariff.getOriginCountry() +
                        ", Category: " + tariff.getProductCategory() +
                        ", Min Tariff: " + tariff.getMinimumTariff());
                position = position.getLink();  // Move to the next node
            }
        }
    }

    // evalute trade
    @Override
    public String evaluateTrade(double proposedTariff, double minimumTariff){ // take in proposed tariff and minimum tariff
        int prop = (int) proposedTariff; // set both to ints to follow project format
        int min = (int) minimumTariff;
        if(proposedTariff >= minimumTariff){ // if proposed trade is creater than minimum trade, accept
            return "ACCEPTED\nProposed tariff meets or exceeds the minimum requirement.";
        }
        else if(proposedTariff >= minimumTariff * 0.8){ // if condition acceptance is approved, print that. (approved if proposed trade is greater than or equal to minimum tariff x 0.8, meaning within 20%)
            double surcharge = TradeRequests.tradeValue * ((minimumTariff - proposedTariff) / 100);
            return "CONDITIONALLY ACCEPTED\nProposed tariff " + prop + "% is within 20% of the required minimum tariff " + min + "%.\nSurcharge: $" + surcharge;
        }
        else { // if none apply, reject the trade
            return "REJECTED\nProposed tariff " + prop + "% is more than 20% below the required minimum tariff " + min + "%.";
        }
    }

    // private inner class TariffNode
    private class TariffNode{

        private Tariff data; // declare private Tariff called data
        private TariffNode link; // declare private TariffNode called link

        // default constructor setting both data and link to null
        public TariffNode(){
            this.data = null;
            this.link = null;
        }

        // parameterized constructor setting this.data to data and this.link to link
        public TariffNode(Tariff data, TariffNode link){
            this.data = data;
            this.link = link;
        }

        // copy constructor
        public TariffNode(TariffNode otherTariffNode){
            this.data = otherTariffNode.data;
            this.link = otherTariffNode.link;
        }

        //getters
        public Tariff getData(){
            return data;
        }
        public TariffNode getLink(){
            return link;
        }

        //setters
        public void setData(Tariff data){
            this.data = data;
        }
        public void setLink(TariffNode link){
            this.link = link;
        }

        //clone
        @Override
        public TariffNode clone(){
            return new TariffNode(this);
        }
    }
}
