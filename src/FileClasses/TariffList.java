
package FileClasses;

import java.util.NoSuchElementException;

public class TariffList implements TariffPolicy{

    private TariffNode head;
    private TariffNode tail;
    private int size;

    public TariffList(){
        this.head = null;
        this.size = 0;
    }

    public TariffList(TariffList otherTariffList){
        this.head = otherTariffList.head;
        this.size = otherTariffList.size;
    }

    public void addToStart(Tariff data){
        TariffNode newNode = new TariffNode(data, head);
        head = newNode;
        size++;
        if(size == 1){
            tail = head;
        }
    }

    public void insertAtIndex(Tariff data, int index){
        if(index < 0 || index > size){
            throw new NoSuchElementException("Invalid Index");
        }
        if(index == 0){
            addToStart(data);
        }
        else if(index == size){
            TariffNode newNode = new TariffNode(data, null);
            if(tail != null){
                tail.setLink(newNode);
                tail = newNode;
            }
            else{
                head = tail = newNode;
            }
            size++;
        }
        else{
            TariffNode newNode = new TariffNode(data, null);
            TariffNode position = head;
            for (int i = 0; i < index-1; i++){
                position = position.getLink();
            }
            newNode.setLink(position.getLink());
            position.setLink(newNode);
            size++;
        }
    }

    public void deleteFromIndex(int index) {
        if (head == null) {
            throw new NoSuchElementException("The list is empty, cannot delete from index " + index);
        }
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Invalid index: " + index);
        }

        // Deleting the first node
        if (index == 0) {
            head = head.getLink();
            size--;
            if (size == 0) {
                tail = null;
            }
            return;
        }

        // Deleting a node from the middle or end
        TariffNode position = head;
        for (int i = 0; i < index - 1; i++) {
            position = position.getLink();
        }

        // Position now points to the node before the one we want to delete
        TariffNode nodeToDelete = position.getLink();
        position.setLink(nodeToDelete.getLink());

        // If we deleted the last node, update the tail
        if (nodeToDelete == tail) {
            tail = position;
        }

        size--;
    }

    public void deleteFromStart() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty, cannot delete from start.");
        }
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.getLink();
        }
        size--;
    }

    public void replaceAtIndex(Tariff newTariff, int index) {
        if (index < 0 || index >= size) {
            return;
        }
        TariffNode position = head;
        for (int i = 0; i < index; i++) {
            position = position.getLink();
        }
        position.setData(newTariff);
    }

    public Tariff find(String originCountry, String destinationCountry,  String productCategory) {
        originCountry = originCountry.trim().toUpperCase();
        destinationCountry = destinationCountry.trim().toUpperCase();
        productCategory = productCategory.trim().toUpperCase();
        TariffNode position = head;
        int iterations = 0;
        while (position != null) {
            iterations++;
            Tariff data = position.getData();
            if (data.getDestinationCountry().trim().toUpperCase().equals(destinationCountry) &&
                    data.getOriginCountry().trim().toUpperCase().equals(originCountry) &&
                    data.getProductCategory().trim().toUpperCase().equals(productCategory)) {
                System.out.println("Iterations: " + iterations);
                return data;
            }
            position = position.getLink();
        }
        System.out.println("Iterations: " + iterations);
        return null;
    }

    public boolean contains(String originCountry, String destinationCountry, String productCategory) {
        TariffNode position = head;
        while (position != null) {
            Tariff data = position.getData();
            if (data.getDestinationCountry().equals(destinationCountry) &&
                    data.getOriginCountry().equals(originCountry) &&
                    data.getProductCategory().equals(productCategory)) {
                return true;
            }
            position = position.getLink();
        }

        return false;
    }

    public boolean equals(TariffList otherList) {
        if (otherList == null || this.size != otherList.size) {
            return false;
        }
        TariffNode current = this.head;
        TariffNode otherCurrent = otherList.head;
        while (current != null) {
            if (!current.getData().equals(otherCurrent.getData())) {
                return false;
            }
            current = current.getLink();
            otherCurrent = otherCurrent.getLink();
        }
        return true;
    }

    public void display() {
        if (head == null) {
            System.out.println("The list has no elements.");
        } else {
            TariffNode position = head;
            while (position != null) {
                Tariff tariff = position.getData();  // Get the Tariff object from the node
                System.out.println("Destination: " + tariff.getDestinationCountry() +
                        ", Origin: " + tariff.getOriginCountry() +
                        ", Category: " + tariff.getProductCategory() +
                        ", Min Tariff: " + tariff.getMinimumTariff());
                position = position.getLink();  // Move to the next node
            }
        }
    }

    @Override
    public String evaluateTrade(double proposedTariff, double minimumTariff){
        int prop = (int) proposedTariff;
        int min = (int) minimumTariff;
        if(proposedTariff >= minimumTariff){
            return "ACCEPTED\nProposed tariff meets or exceeds the minimum requirement.";
        }
        else if(proposedTariff >= minimumTariff * 0.8){
            double surcharge = TradeRequests.tradeValue * ((minimumTariff - proposedTariff) / 100);
            return "CONDITIONALLY ACCEPTED\nProposed tariff " + prop + "% is within 20% of the required minimum tariff " + min + "%.\nSurcharge: $" + surcharge;
        }
        else {
            return "REJECTED\nProposed tariff " + prop + "% is more than 20% below the required minimum tariff " + min + "%.";
        }
    }

    private class TariffNode{

        private Tariff data;
        private TariffNode link;

        public TariffNode(){
            this.data = null;
            this.link = null;
        }

        public TariffNode(Tariff data, TariffNode link){
            this.data = data;
            this.link = link;
        }

        public TariffNode(TariffNode otherTariffNode){
            this.data = otherTariffNode.data;
            this.link = otherTariffNode.link;
        }

        public Tariff getData(){
            return data;
        }
        public TariffNode getLink(){
            return link;
        }

        public void setData(Tariff data){
            this.data = data;
        }
        public void setLink(TariffNode link){
            this.link = link;
        }

        @Override
        public TariffNode clone(){
            return new TariffNode(this);
        }
    }
}
