package com.keyin.http.cli;

import com.keyin.domain.Aircraft;
import com.keyin.domain.Airport;
import com.keyin.domain.City;
import com.keyin.domain.Passenger;
import com.keyin.http.client.RESTClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class HTTPRestCLIApplication {


    private final RESTClient client;
    private final Scanner scanner;

    public HTTPRestCLIApplication(RESTClient client, Scanner scanner) {
        this.client = client;
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        RESTClient client = new RESTClient();
        Scanner scanner = new Scanner(System.in);
        new HTTPRestCLIApplication(client, scanner).run();
    }

    public void run() {
        while (true) {
            printMenu();
            System.out.print("\nChoose an option (1-5): ");
            String input = scanner.nextLine();

            if (!input.matches("[1-5]")) {
                System.out.println("Invalid input. Please enter a number from 1 to 5.\n");
                continue;
            }

            int option = Integer.parseInt(input);

            switch (option) {
                case 1:
                    listAirportsInCity();
                    break;
                case 2:
                    listAircraftsByPassenger();
                    break;
                case 3:
                    listAirportsByAircraft();
                    break;
                case 4:
                    listAirportsByPassenger();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Unexpected input.");
            }
        }
    }

    private void listAirportsInCity() {
        while (true) {
            try {
                System.out.print("Enter City ID: ");
                long cityId = Long.parseLong(scanner.nextLine());
                City city = client.getCityById(cityId);
                List<Airport> airports = client.getAirportsByCity(cityId);

                System.out.println("\nAirports in " + city.getName() + ", " + city.getProvince() + ":");
                System.out.println("------------------------------------");
                if (airports.isEmpty()) {
                    System.out.println("• No airports found.");
                } else {
                    for (Airport a : airports) {
                        System.out.println("• " + a.getName() + " (Code: " + a.getCode() + ")");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nCheck another city? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) break;
        }
    }

    private void listAircraftsByPassenger() {
        while (true) {
            try {
                System.out.print("Enter Passenger ID: ");
                long passengerId = Long.parseLong(scanner.nextLine());
                Passenger passenger = client.getPassengerById(passengerId);
                List<Aircraft> aircrafts = client.getAircraftsByPassenger(passengerId);

                System.out.println("\nAircraft flown by " + passenger.getFirstName() + " " + passenger.getLastName() + ":");
                System.out.println("-------------------------------------");
                if (aircrafts.isEmpty()) {
                    System.out.println("• No aircrafts found.");
                } else {
                    for (Aircraft a : aircrafts) {
                        System.out.println("• " + a.getModel() + " (Airline: " + a.getAirlineName() + ")");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nCheck another passenger? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) break;
        }
    }

    private void listAirportsByAircraft() {
        while (true) {
            try {
                System.out.print("Enter Aircraft ID: ");
                long aircraftId = Long.parseLong(scanner.nextLine());
                List<Airport> airports = client.getAirportsByAircraft(aircraftId);

                System.out.println("\nAirports used by Aircraft ID " + aircraftId + ":");
                System.out.println("----------------------------------");
                if (airports.isEmpty()) {
                    System.out.println("• No airports found.");
                } else {
                    for (Airport a : airports) {
                        System.out.println("• " + a.getName() + " (Code: " + a.getCode() + ")");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nCheck another aircraft? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) break;
        }
    }

    private void listAirportsByPassenger() {
        while (true) {
            try {
                System.out.print("Enter Passenger ID: ");
                long passengerId = Long.parseLong(scanner.nextLine());
                Passenger passenger = client.getPassengerById(passengerId);
                List<Airport> airports = client.getAirportsByPassenger(passengerId);

                System.out.println("\nAirports used by " + passenger.getFirstName() + " " + passenger.getLastName() + ":");
                System.out.println("------------------------------------");
                if (airports.isEmpty()) {
                    System.out.println("• No airports found.");
                } else {
                    for (Airport a : airports) {
                        System.out.println("• " + a.getName() + " (Code: " + a.getCode() + ")");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nCheck another passenger? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) break;
        }
    }

    private static void printMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║        Travel Info CLI             ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1. List airports in a city");
        System.out.println("2. List aircraft flown by a passenger");
        System.out.println("3. List airports used by an aircraft");
        System.out.println("4. List airports used by a passenger");
        System.out.println("5. Exit");
    }
}
