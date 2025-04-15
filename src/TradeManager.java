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
        System.out.println("Welcome To The Tariff Management System!\n"); //welcome message

        TariffList list1 = new TariffList(); //create a TariffList object
        TariffList list2 = new TariffList(); //create a second one for copy constructor

        try { //try block
            Scanner tradeData = new Scanner(new FileInputStream("src/RequiredFiles/TradeData.txt")); //read TradeData.txt with scanner
            System.out.println("RAW VERSION: TradeData.txt"); //title unsorted read file
            TradeData tradeData1 = new TradeData(); //create TradeData object
            tradeData1.readTradeData(); //read trade data from txt file
            System.out.println("\nALPHABETICALLY SORTED AND STORED VERSION:");
            tradeData1.writeTradeData(); //write data alphabetically and store it that way in the UpdatedTradeData.txt file

            TradeData dataCopy = new TradeData(tradeData1.getProducts().getFirst()); //create a copy of the first object in the tradeData1 arrayList
            dataCopy.setCategory("Technology"); //overriding the values to test out setters
            dataCopy.setCountry("Italy"); //overriding the values to test out setters
            dataCopy.setTradeDataName("Computer"); //overriding the values to test out setters
            System.out.println("\nTRADE DATA --- COPY CONSTRUCTOR --- TESTING: " + dataCopy); // display the copy with toString

            System.out.println("\n'Tariff.txt' AND 'TradeRequests.txt' FILE ANALYSIS:\n"); //title for reading and writing Tariff.txt and TradeRequests.txt files

            Scanner tariffReader = new Scanner(new FileInputStream("Tariffs.txt")); //read Tariffs.txt with scanner
            Scanner tradeRequestsReader = new Scanner(new FileInputStream("TradeRequests.txt")); //read TradeRequests.txt with scanner
            int min = 0; //declares an int named min and assign its initial value to 0

            while (tariffReader.hasNextLine()) { //go through while loop while Tariff.txt has a next line
                String tariffLine = tariffReader.nextLine().trim();  // Read and trim the line

                if (tariffLine.isEmpty()) { // make sure the line isn't empty
                    continue; // simply continue
                }

                String[] tariffParts = tariffLine.split(" "); // split each line by an empty space

                if (tariffParts.length != 4) {// Check if there are exactly 4 parts (3 strings and 1 integer)
                    System.out.println("Invalid input format. Skipping line: " + tariffLine); //if line doesn't match valid form, skip the line
                    continue; // simply continue to the rest of the code
                }

                // Extract values from the parts
                String tariffDestination = tariffParts[0];
                String tariffOrigin = tariffParts[1];
                String tariffCategory = tariffParts[2];
                String tariffMinTariffString = tariffParts[3];

                int tariffMinTariff = 0; // Check if the last part is a valid integer
                try {
                    tariffMinTariff = Integer.parseInt(tariffMinTariffString);  // Attempt to parse the last part as an integer
                } catch (NumberFormatException e) {
                    // If it fails, print an error and skip this line
                    System.out.println("Invalid tariff value (not an integer). Skipping line: " + tariffLine);
                    continue; // simply continue
                }

                // Add to the list if everything is valid
                Tariff tariff = new Tariff(tariffDestination, tariffOrigin, tariffCategory, tariffMinTariff);
                if (!list1.contains(tariffOrigin, tariffDestination, tariffCategory)) { //check for duplicates and add if one isn't found
                    list1.addToStart(tariff); // add to start of LinkedList
                }
            } // end of while loop

            ArrayList<TradeRequests> tradeRequestsArrayList = new ArrayList<>(); // create TradeRequest arrayList to add values to later

            while (tradeRequestsReader.hasNextLine()) { //loop while TradeRequests has a next line
                String tradeLine = tradeRequestsReader.nextLine().trim();  // Read and trim the line

                if (tradeLine.isEmpty()) { // check for empty line (blank line)
                    continue; // simply continue
                }

                String[] tradeParts = tradeLine.split(" "); // split at blank space

                if (tradeParts.length != 6) { // Check if there are exactly 6 parts (1 string for tradeRequestNumber, 3 strings for origin, destination, and category, 2 integers for tradeValue and tradeProposedTariff)
                    System.out.println("Invalid input format. Skipping line: " + tradeLine);
                    continue;
                }

                // Extract values from the parts
                String tradeRequestNumber = tradeParts[0];
                String tradeOrigin = tradeParts[1];
                String tradeDestination = tradeParts[2];
                String tradeCategory = tradeParts[3];

                // Try parsing tradeValue and tradeProposedTariff as integers
                int tradeValue = 0;
                int tradeProposedTariff = 0;

                try {
                    tradeValue = Integer.parseInt(tradeParts[4]);  // Attempt to parse the trade value as an integer
                    tradeProposedTariff = Integer.parseInt(tradeParts[5]);  // Attempt to parse the proposed tariff as an integer
                } catch (NumberFormatException e) {
                    // If either parsing fails, print an error and skip this line
                    System.out.println("Invalid trade value or proposed tariff (not an integer). Skipping line: " + tradeLine);
                    continue;
                }

                // Create TradeRequest and add it to the list
                TradeRequests tradeRequest = new TradeRequests(tradeOrigin, tradeDestination, tradeCategory, tradeValue, tradeProposedTariff);
                tradeRequestsArrayList.add(tradeRequest); // add each value one at a time, while the loop goes on

                // Find the matching tariff and evaluate the trade
                String result; // declare result
                Tariff matchedTariff = list1.find(tradeOrigin, tradeDestination, tradeCategory); // declare the Tariff matchedTariff as the find method on list1
                if (matchedTariff != null) { // if matchedTariff is not null evaluate the trade
                    System.out.println("Matched Tariff: " + matchedTariff);
                    result = list1.evaluateTrade(tradeProposedTariff, matchedTariff.getMinimumTariff());
                } else { // else, deny the trade because there is no valid trade to evaluate
                    result = "Trade Denied - No Matching Tariff Found";
                }

                System.out.println(tradeRequestNumber + " - " + result + "\n"); // print the result of the evaluated trade
            } // end of while loop

            System.out.println("Tariffs List Data:");
            list1.display(); // display all the Tariffs in Tariff.txt file using the display method
            System.out.println(); // skip a line

            tariffReader.close(); // close the tariffReader
            tradeRequestsReader.close(); // close the tradeRequestsReader

            TariffList list3 = new TariffList(list1); // create a new LinkedList
            Tariff data1 = new Tariff("Greece", "Venezuela", "Clothing", 18); //create object values
            Tariff data2 = new Tariff("Norway", "Thailand", "Toys", 10); //create object values
            Tariff data3 = new Tariff("Argentina", "Morocco", "Food", 40); //create object values
            Tariff data4 = new Tariff("Canada", "Egypt", "Pharmaceuticals", 22); //create object values
            data2.setDestinationCountry("Brazil"); //use setter to set destination country
            data2.setOriginCountry("Finland"); //use setter to set origin country
            data2.setProductCategory("Books"); //use setter to set product category
            data2.setMinimumTariff(25); //use setter to set minimum tariff
            System.out.println("TARIFF LIST --- COPY CONSTRUCTOR --- TESTING: ");
            list3.insertAtIndex(data1, 0); // insert data1 at the index 0 of list3
            list3.insertAtIndex(data4, 1); // insert data4 at the index 1 of list3
            list3.insertAtIndex(data2, 2); // insert data2 at the index 2 of list3
            list3.deleteFromIndex(2); // delete the 2nd index of the list
            list3.deleteFromStart(); // delete the first element of the list
            list3.replaceAtIndex(data3, 3); // replace the 3rd index of the list with data3
            System.out.println("The two lists are equal: " + list3.equals(list2)); // display if list2 and list3 are equals with a true or false
            list3.display(); // display list3 with display method

            System.out.println("\nTRADE REQUESTS --- GETTERS/SETTERS --- TESTING: ");
            TradeRequests tradeRequests = new TradeRequests(); // create an empty trade request
            tradeRequests.setTradeFromCountry("Australia"); //set the origin with setter
            tradeRequests.setTradeToCountry("Mexico"); //set the destination with setter
            tradeRequests.setTradeValue(80000); //set the trade value with setter
            tradeRequests.setCategory("Furniture"); //set the category with setter
            tradeRequests.setProposedTariff(20); //set the proposed tariff with setter
            System.out.println(tradeRequests.getTradeRequestNumber()); // print out the trade request number with the getter (should be REQ004)
            System.out.println("Origin: " + tradeRequests.getTradeFromCountry()); // print origin with getter
            System.out.println("Destination: " + tradeRequests.getTradeToCountry()); // print destination with getter
            System.out.println("Category: " + tradeRequests.getCategory()); // print category with getter
            System.out.println("Trade Value: $" + tradeRequests.getTradeValue()); // print value with getter
            System.out.println("Proposed Tariff: " + tradeRequests.getProposedTariff() + "%\n"); // print proposed tariff with getter and skip line

        } catch (FileNotFoundException e) { // catch if file is not found and print an error message "Error: file not found!"
            System.err.println("Error: file not found!");
            return;
        } catch (Exception e) { // catch remaining exceptions and print an error message "Error!"
            System.err.println("Error!");
            return;
        }

        Scanner keyIn = new Scanner(System.in); // create a scanner for user inputs
        char decisionChar; // declare char decisionChar
        System.out.println("SEARCH FOR A TARIFF FROM THE 'Tariff.txt' FILE:");
        list1.display(); // display the possible tariffs to search for
        System.out.println(); // skip a line

        do {
            System.out.print("Enter the destination country --> ");
            String destination = keyIn.nextLine(); // takes destination country entered and stores as a String

            System.out.print("Enter the origin country --> ");
            String origin = keyIn.nextLine(); // takes origin country entered and stores as a String

            System.out.print("Enter the product category --> ");
            String category = keyIn.nextLine(); // takes product category entered and stores as a String

            System.out.println(); // skips a line

            Tariff result = list1.find(origin, destination, category); //instantiate Tariff result to find a Tariff in list1 that matches the origin, destination, and category the user inputted

            if (result != null) { // if statement to see if result is null. if not null print below
                System.out.println(" - Tariff found!"); // found the tariff
                System.out.println(" - Origin: " + result.getOriginCountry()); // print origin with getter
                System.out.println(" - Destination: " + result.getDestinationCountry()); // print destination with getter
                System.out.println(" - Category: " + result.getProductCategory()); // print category with getter
                System.out.println(" - Min Tariff: " + result.getMinimumTariff() + "%"); // print minimum tariff with getter and insert percent sign for nicer display
            } else { // if result is not not null, print that Tariff is not found
                System.out.println("Tariff not found.");
            }

            System.out.print("\nWould you like to search for another tariff? (y or n) --> "); // ask if user would like to continue searching for tariffs
            String input = keyIn.nextLine().trim().toLowerCase(); // take answer
            if (input.length() > 0) { // if answer is more than zero characters, assign decisionChar to the first letter of response. anything starting with y will work for loop
                decisionChar = input.charAt(0);
            } else { // if no answer set decisionChar to 'n'
                decisionChar = 'n';
            }

        } while (decisionChar == 'y'); // continue loop until decisionChar is not y
        keyIn.close(); // close the scanner
    } //close main
} // close TradeManager class