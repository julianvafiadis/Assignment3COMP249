package FileClasses;

import java.util.NoSuchElementException;

public class TradeRequestsLinkedList {

    private TradeRequestsNode head;
    private TradeRequestsNode tail;
    private int size;

    public TradeRequestsLinkedList(){
        this.head = null;
        this.size = 0;
    }

    public TradeRequestsLinkedList(TradeRequestsLinkedList otherTradeRequestsLinkedList){
        this.head = otherTradeRequestsLinkedList.head;
        this.size = otherTradeRequestsLinkedList.size;
    }

    public void addToStart(TradeRequests data){
        TradeRequestsNode newNode = new TradeRequestsNode(data, head);
        head = newNode;
        size++;
        if(size == 1){
            tail = head;
        }
    }

    public void insertAtIndex(TradeRequests data, int index){
        if(index < 0 || index > size){
            throw new NoSuchElementException("Invalid Index");
        }
        if(index == 0){
            addToStart(data);
        }
        else if(index == size){
            TradeRequestsNode newNode = new TradeRequestsNode(data, null);
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
            TradeRequestsNode newNode = new TradeRequestsNode(data, null);
            TradeRequestsNode position = head;
            for (int i = 0; i < index-1; i++){
                position = position.getLink();
            }
            newNode.setLink(position.getLink());
            position.setLink(newNode);
            size++;
        }
    }

    public void deleteFromIndex(int index) {
        if (index < 0 || index >= size) {
            throw new NoSuchElementException("Invalid index: " + index);
        }
        if (index == 0) {
            head = head.getLink();
            size--;
            if (size == 0) {
                tail = null;
            }
            return;
        }

        TradeRequestsNode position = head;
        for (int i = 0; i < index - 1; i++) {
            position = position.getLink();
        }
        TradeRequestsNode nodeToDelete = position.getLink();
        position.setLink(nodeToDelete.getLink());
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

    public void replaceAtIndex(TradeRequests newTradeRequest, int index) {
        if (index < 0 || index >= size) {
            return;
        }
        TradeRequestsNode position = head;
        for (int i = 0; i < index; i++) {
            position = position.getLink();
        }
        position.setData(newTradeRequest);
    }

    public TradeRequestsNode find(String originCountry, String destinationCountry, String productCategory) {
        TradeRequestsNode position = head;
        int iterations = 0;
        while (position != null) {
            iterations++;
            TradeRequests data = position.getData();
            if (data.getTradeFromCountry().equals(originCountry) &&
                    data.getTradeToCountry().equals(destinationCountry) &&
                    data.getCategory().equals(productCategory)) {
                System.out.println("Iterations: " + iterations);
                return position;
            }
            position = position.getLink();
        }
        System.out.println("Iterations: " + iterations);
        return null;
    }

    public boolean contains(String originCountry, String destinationCountry, String productCategory) {
        TradeRequestsNode position = head;
        while (position != null) {
            TradeRequests data = position.getData();
            if (data.getTradeFromCountry().equals(originCountry) &&
                    data.getTradeToCountry().equals(destinationCountry) &&
                    data.getCategory().equals(productCategory)) {
                return true;
            }
            position = position.getLink();
        }

        return false;
    }

    public boolean equals(TradeRequestsLinkedList otherList) {
        if (otherList == null || this.size != otherList.size) {
            return false;
        }
        TradeRequestsNode current = this.head;
        TradeRequestsNode otherCurrent = otherList.head;
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
            TradeRequestsNode position = head;
            while (position != null) {
                TradeRequests tradeRequests = position.getData();  // Get the Tariff object from the node
                System.out.println("Trade Request Number: " + tradeRequests.getTradeRequestNumber() +
                        ", Destination: " + tradeRequests.getTradeToCountry() +
                        ", Origin: " + tradeRequests.getTradeFromCountry() +
                        ", Category: " + tradeRequests.getCategory() +
                        ", Min Tariff: " + tradeRequests.getProposedTariff() +
                        ", Trade Value: " + tradeRequests.getTradeValue());
                position = position.getLink();  // Move to the next node
            }
        }
    }

    private class TradeRequestsNode {

        private TradeRequests data;
        private TradeRequestsNode link;

        public TradeRequestsNode() {
            this.data = null;
            this.link = null;
        }

        public TradeRequestsNode(TradeRequests data, TradeRequestsNode link) {
            this.data = data;
            this.link = link;
        }

        public TradeRequestsNode(TradeRequestsNode otherTradeRequestsNode) {
            this.data = otherTradeRequestsNode.data;
            this.link = otherTradeRequestsNode.link;
        }

        public TradeRequests getData() {
            return data;
        }

        public void setData(TradeRequests data) {
            this.data = data;
        }

        public TradeRequestsNode getLink() {
            return link;
        }

        public void setLink(TradeRequestsNode link) {
            this.link = link;
        }

        @Override
        public TradeRequestsNode clone() {
            return new TradeRequestsNode(this);
        }
    }
}