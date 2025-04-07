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
        try (Scanner tariffReader = new Scanner(new FileInputStream("src/RequiredFiles/Tariffs.txt"))) {
            while (tariffReader.hasNextLine()) {
                String line = tariffReader.nextLine();
                String[] parts = line.split(" ");

                String destination = parts[0];
                String origin = parts[1];
                String category = parts[2];
                int minTariff = Integer.parseInt(parts[3]);

                Tariff tariff = new Tariff(destination, origin, category, minTariff);

                if (!list1.contains(origin, destination, category)) {
                    list1.addToStart(tariff);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Tariffs.txt not found.");
            return;
        }
    }
}