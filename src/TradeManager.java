
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
            TradeData data = new TradeData();
            System.out.println("RAW VERSION: TradeData.txt");
            data.readTradeData();
            System.out.println("\nALPHABETICALLY SORTED AND STORED VERSION:");
            data.writeTradeData();
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

        } catch (FileNotFoundException e) {
            System.err.println("Error: file not found!");
            return;
        } catch (Exception e) {
            System.err.println("Error!");
            return;
        }

        Scanner keyIn = new Scanner(System.in);
        char decisionChar;
        System.out.println("SEARCH FOR A TARIFF:");

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