package com.ps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private static Scanner scanner = new Scanner(System.in);

    public void display() {
        init();

        int mainMenuCommand;

        do {
            // Needs refactoring
            System.out.println("1- Find vehicles within a price range");
            System.out.println("2- Find vehicles by make / model");
            System.out.println("3- Find vehicles by year range");
            System.out.println("4- Find vehicles by color");
            System.out.println("5- Find vehicles by mileage range");
            System.out.println("6- Find vehicles by type (sedan, truck, SUV, van)");
            System.out.println("7- List ALL vehicles");
            System.out.println("8- Add a vehicle");
            System.out.println("9- Remove a vehicle");
            System.out.println("10- Buy/Lease a vehicle.");
            System.out.println("99- Quit");

            System.out.print("Please choose an option: ");

            mainMenuCommand = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (mainMenuCommand) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 10:
                    processSaleOrLease();
                    break;
                case 99:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (mainMenuCommand != 99);
    }

    private void init() {
        this.dealership = DealershipFileManager.getDealership();
    }

    private void displayVehicles(List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            System.out.printf("VIN: %d, Year: %d, Make: %s, Model: %s, Type: %s, Color: %s, Odometer: %d, Price: %.2f\n",
                    vehicle.getVin(),
                    vehicle.getYear(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getVehicleType(),
                    vehicle.getColor(),
                    vehicle.getOdometer(),
                    vehicle.getPrice()
            );
        }
    }

    public void processGetByPriceRequest() {
        System.out.print("Minimum price: ");
        int min = scanner.nextInt();
        System.out.print("Maximum price: ");
        int max = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        List<Vehicle> filteredVehicles = dealership.getVehiclesByPrice(min, max);
        displayVehicles(filteredVehicles);
    }

    public void processGetByMakeModelRequest() {
        System.out.print("Make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();

        List<Vehicle> filteredVehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(filteredVehicles);
    }

    public void processGetByYearRequest() {
        System.out.print("Minimum year: ");
        int min = scanner.nextInt();
        System.out.print("Maximum year: ");
        int max = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        List<Vehicle> filteredVehicles = dealership.getVehicleByYear(min, max);
        displayVehicles(filteredVehicles);
    }

    public void processGetByColorRequest() {
        System.out.print("Color: ");
        String color = scanner.nextLine();

        List<Vehicle> filteredVehicles = dealership.getVehicleByColor(color);
        displayVehicles(filteredVehicles);
    }

    public void processGetByMileageRequest() {
        System.out.print("Minimum Mileage: ");
        int min = scanner.nextInt();
        System.out.print("Maximum Mileage: ");
        int max = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        List<Vehicle> filteredVehicles = dealership.getVehiclesByMileage(min, max);
        displayVehicles(filteredVehicles);
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Vehicle type: ");
        String type = scanner.nextLine();

        List<Vehicle> filteredVehicles = dealership.getVehiclesByType(type);
        displayVehicles(filteredVehicles);
    }

    public void processGetAllVehiclesRequest() {
        List<Vehicle> allVehicles = dealership.getAllVehicles();
        System.out.println("------ All Vehicles ------");
        displayVehicles(allVehicles);
    }

    public void processAddVehicleRequest() {
        System.out.print("VIN: ");
        int vin = scanner.nextInt();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Color: ");
        String color = scanner.nextLine();
        System.out.print("Odometer: ");
        int odometer = scanner.nextInt();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline

        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, odometer, price);
        this.dealership.addVehicle(vehicle);

        DealershipFileManager.saveDealership(this.dealership);
    }

    public void processRemoveVehicleRequest() {
        List<Vehicle> allVehicles = this.dealership.getAllVehicles();
        displayVehicles(allVehicles);
        System.out.print("Which would you like to remove? VIN: ");
        int vin = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Vehicle vehicleToRemove = null;
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.getVin() == vin) {
                vehicleToRemove = vehicle;
                break;
            }
        }

        if (vehicleToRemove != null) {
            dealership.removeVehicle(vehicleToRemove);
            System.out.println("Vehicle found and removed");
            DealershipFileManager.saveDealership(this.dealership);
        } else {
            System.out.println("Vehicle not found");
        }
    }

    public void processSaleOrLease() {



                ContractDataManager contractDataManager = new ContractDataManager();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String dateOfContract = LocalDate.now().format(formatter);

                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.print("Enter your email: ");
                String customerEmail = scanner.nextLine();

                System.out.print("Enter the VIN of the vehicle you want to buy/lease: ");
                int vehicleVin = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                Vehicle vehicle = dealership.getVehicleByVin(vehicleVin);

                displayVehicle(vehicle);

                boolean isSaleOrLease = true;
                while (isSaleOrLease) {
                    System.out.println("Would you like to buy or lease?");
                    System.out.println("[1] Sale");
                    System.out.println("[2] Lease");
                    System.out.println("[3] Return to main menu.");

                    int saleOrLeaseChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    switch (saleOrLeaseChoice) {
                        case 1:
                            System.out.print("Would you like to finance the car (Y/N)?: ");
                            String wouldLikeToFinance = scanner.nextLine().toLowerCase();

                            boolean isFinanced = false;
                            if (wouldLikeToFinance.equals("y")) {
                                isFinanced = true;
                            } else if (!wouldLikeToFinance.equals("n")) {
                                System.out.println("This is not a valid choice.");
                                continue;
                            }

                            SalesContract salesContract = new SalesContract(dateOfContract,
                                    customerName,
                                    customerEmail,
                                    vehicle,
                                    isFinanced);
                            contractDataManager.saveContract(salesContract);
                            break;
                        case 2:
                            LeaseContract leaseContract = new LeaseContract(dateOfContract,
                                    customerName,
                                    customerEmail,
                                    vehicle);
                            contractDataManager.saveContract(leaseContract);
                            break;
                        case 3:
                            System.out.println("Returning to main menu...");
                            return;
                        default:
                            System.out.println("This is not a valid choice.");
                            continue;
                    }
                    isSaleOrLease = false;
                }

                dealership.removeVehicle(vehicle);
            }

            private void displayVehicle(Vehicle vehicle) {
                if (vehicle != null) {
                    System.out.printf("VIN: %d, Year: %d, Make: %s, Model: %s, Type: %s, Color: %s, Odometer: %d, Price: %.2f\n",
                            vehicle.getVin(),
                            vehicle.getYear(),
                            vehicle.getMake(),
                            vehicle.getModel(),
                            vehicle.getVehicleType(),
                            vehicle.getColor(),
                            vehicle.getOdometer(),
                            vehicle.getPrice());
                } else {
                    System.out.println("Vehicle not found.");
                }
            }


        }

