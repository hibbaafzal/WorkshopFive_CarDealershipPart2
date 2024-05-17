package com.ps;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContractDataManager {

    public void saveContract(DealershipContract contract) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("contracts.csv", true))) {
            StringBuilder output = new StringBuilder();
            String pricingInformation = "";

            if (contract instanceof LeaseContract) {
                output.append("LEASE|");
                LeaseContract leaseContract = (LeaseContract) contract;
                pricingInformation = String.format("|%.2f|%.2f|%.2f",
                        leaseContract.getLeaseFee(),
                        contract.getTotalPrice(),
                        contract.getMonthlyPayment());
            }

            if (contract instanceof SalesContract) {
                output.append("SALE|");
                SalesContract salesContract = (SalesContract) contract;

                String financed;
                if (salesContract.isFinanced()) {
                    financed = "YES";
                } else {
                    financed = "NO";
                }

                pricingInformation = String.format("|%.2f|%.2f|%.2f|%.2f|%s|%.2f",
                        salesContract.getSalesTaxPercentage(),
                        salesContract.getRecordingFee(),
                        salesContract.getProcessingFee(),
                        contract.getTotalPrice(),
                        financed,
                        contract.getMonthlyPayment());
            }

            String contractInformation = String.format("%s|%s|%s|",
                    contract.getDateOfContract(),
                    contract.getCustomerName(),
                    contract.getCustomerEmail());
            output.append(contractInformation);

            Vehicle soldVehicle = contract.getVehicleSold();
            String vehicleInformation = String.format("%d|%d|%s|%s|%s|%s|%d|%.2f",
                    soldVehicle.getVin(),
                    soldVehicle.getYear(),
                    soldVehicle.getMake(),
                    soldVehicle.getModel(),
                    soldVehicle.getColor(),
                    soldVehicle.getVehicleType(),
                    soldVehicle.getOdometer(),
                    soldVehicle.getPrice());

            output.append(vehicleInformation);

            output.append(pricingInformation);

            output.append("\n");
            bufferedWriter.write(output.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
