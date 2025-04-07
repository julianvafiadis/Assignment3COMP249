import FileClasses.Tariff;
import FileClasses.TariffList;
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
            System.err.println("Error: file not found.");
            return;
        }

        
    }
}