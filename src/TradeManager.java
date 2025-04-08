
import FileClasses.Tariff;
import FileClasses.TariffList;
import FileClasses.TradeData;
import FileClasses.TradeRequests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// ---------------------------------------------------------
// Assignment 3
// Written by: Julian Vafiadis 40310616
// ---------------------------------------------------------
public class TradeManager {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome To The Tariff Management System!\n");

        // (a) Create at least two empty lists
        TariffList list1 = new TariffList(); // Main list
        TariffList list2 = new TariffList(); // For copy constructor or other uses

        // (b) Read Tariffs.txt
        try {
            Scanner tradeData = new Scanner(new FileInputStream("src/RequiredFiles/TradeData.txt"));
            System.out.println("RAW VERSION: TradeData.txt");
            TradeData tradeData1 = new TradeData();
            tradeData1.readTradeData();
            System.out.println("\nALPHABETICALLY SORTED AND STORED VERSION:");
            tradeData1.writeTradeData();

            TradeData dataCopy = new TradeData(tradeData1.getProducts().getFirst());
            dataCopy.setCategory("Technology");
            dataCopy.setCountry("Italy");
            dataCopy.setTradeDataName("Computer");
            System.out.println("\nTRADE DATA --- COPY CONSTRUCTOR --- TESTING: " + dataCopy);

            System.out.println("\n'Tariff.txt' AND 'TradeRequests.txt' FILE ANALYSIS:\n");

            Scanner tariffReader = new Scanner(new FileInputStream("src/RequiredFiles/Tariffs.txt"));
            Scanner tradeRequestsReader = new Scanner(new FileInputStream("src/RequiredFiles/TradeRequests.txt"));

            while (tariffReader.hasNextLine() && tradeRequestsReader.hasNextLine()) {
                String tariffLine = tariffReader.nextLine();
                String[] tariffParts = tariffLine.split(" ");

                String tariffDestination = tariffParts[0];
                String tariffOrigin = tariffParts[1];
                String tariffCategory = tariffParts[2];
                int tariffMinTariff = Integer.parseInt(tariffParts[3]);

                Tariff tariff = new Tariff(tariffDestination, tariffOrigin, tariffCategory, tariffMinTariff);

                if (!list1.contains(tariffOrigin, tariffDestination, tariffCategory)) {
                    list1.addToStart(tariff);
                }

                String tradeLine = tradeRequestsReader.nextLine();
                String[] tradeParts = tradeLine.split(" ");

                String tradeRequestNumber = tradeParts[0];
                String tradeOrigin = tradeParts[1];
                String tradeDestination = tradeParts[2];
                String tradeCategory = tradeParts[3];
                int tradeValue = Integer.parseInt(tradeParts[4]);
                int tradeProposedTariff = Integer.parseInt(tradeParts[5]);

                TradeRequests tradeRequests = new TradeRequests(tradeOrigin, tradeDestination, tradeCategory, tradeValue, tradeProposedTariff);
                ArrayList<TradeRequests> tradeRequestsArrayList = new ArrayList<>();
                tradeRequestsArrayList.add(tradeRequests);

                String result = list1.evaluateTrade(tradeProposedTariff, tariffMinTariff);
                System.out.println(tradeRequestNumber + " - " + result + "\n");
            }

            tariffReader.close();
            tradeRequestsReader.close();

            TariffList list3 = new TariffList(list1);
            Tariff data1 = new Tariff("Greece", "Venezuela", "Clothing", 18);
            Tariff data2 = new Tariff("Norway", "Thailand", "Toys", 10);
            Tariff data3 = new Tariff("Argentina", "Morocco", "Food", 40);
            Tariff data4 = new Tariff("Canada", "Egypt", "Pharmaceuticals", 22);
            data2.setDestinationCountry("Brazil");
            data2.setOriginCountry("Finland");
            data2.setProductCategory("Books");
            data2.setMinimumTariff(25);
            System.out.println("TARIFF LIST --- COPY CONSTRUCTOR --- TESTING: ");
            list3.insertAtIndex(data1, 0);
            list3.insertAtIndex(data4, 1);
            list3.insertAtIndex(data2, 2);
            list3.deleteFromIndex(2);
            list3.deleteFromStart();
            list3.replaceAtIndex(data3, 3);
            System.out.println("The two lists are equal: " + list3.equals(list2));
            list3.display();

            System.out.println("\nTRADE REQUESTS --- GETTERS/SETTERS --- TESTING: ");
            TradeRequests tradeRequests = new TradeRequests();
            tradeRequests.setTradeFromCountry("Australia");
            tradeRequests.setTradeToCountry("Mexico");
            tradeRequests.setTradeValue(80000);
            tradeRequests.setCategory("Furniture");
            tradeRequests.setProposedTariff(20);
            System.out.println("Category: " + tradeRequests.getCategory());
            System.out.println("Trade Value: " + tradeRequests.getTradeValue());
            System.out.println("Destination: " + tradeRequests.getTradeToCountry());
            System.out.println("Origin: " + tradeRequests.getTradeFromCountry());
            System.out.println("Trade Request Number: " + tradeRequests.getTradeRequestNumber());
            System.out.println("Proposed Tariff: " + tradeRequests.getProposedTariff() + "\n");

        } catch (FileNotFoundException e) {
            System.err.println("Error: file not found!");
            return;
        } catch (Exception e) {
            System.err.println("Error!");
            return;
        }

        Scanner keyIn = new Scanner(System.in);
        char decisionChar;
        System.out.println("SEARCH FOR A TARIFF FROM 'Tariff.txt' AND 'TradeRequests.txt' FILES:");

        do {
            System.out.print("Enter the destination country --> ");
            String destination = keyIn.nextLine();

            System.out.print("Enter the origin country --> ");
            String origin = keyIn.nextLine();

            System.out.print("Enter the product category --> ");
            String category = keyIn.nextLine();

            System.out.println();

            Tariff result = list1.find(origin, destination, category);

            if (result != null) {
                System.out.println(" - Tariff found!");
                System.out.println(" - Origin: " + result.getOriginCountry());
                System.out.println(" - Destination: " + result.getDestinationCountry());
                System.out.println(" - Category: " + result.getProductCategory());
                System.out.println(" - Min Tariff: " + result.getMinimumTariff() + "%");
            } else {
                System.out.println("Tariff not found.");
            }

            System.out.print("\nWould you like to search for another tariff? (y or n) --> ");
            String input = keyIn.nextLine().trim().toLowerCase();
            if (input.length() > 0) {
                decisionChar = input.charAt(0);
            } else {
                decisionChar = 'n';
            }

        } while (decisionChar == 'y');
        keyIn.close();

        TradeData tradeData = new TradeData();
        System.out.println("TESTING REMAINING METHODS:");
        System.out.println("TRADE DATA: ");
        System.out.println("TRADE DATA --- COPY CONSTRUCTOR --- TESTING: ");
    }
}