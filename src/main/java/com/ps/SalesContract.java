package com.ps;



public class SalesContract extends DealershipContract {
    private double salesTaxPercentage;
    private double recordingFee;
    private double processingFee;
    private boolean isFinanced;
    private int loanTermInMonths = 0;

    public SalesContract(String dateOfContract,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold,
                         boolean isFinanced) {

        super(dateOfContract, customerName, customerEmail, vehicleSold);
        this.salesTaxPercentage = vehicleSold.getPrice() * .05;
        this.recordingFee = 100;
        if (vehicleSold.getPrice() < 10_000) { // processing fee
            this.processingFee = 295;
        } else {
            this.processingFee = 495;
        }
        this.isFinanced = isFinanced;
    }


    //Methods
    @Override
    public double getTotalPrice() {
        double totalPrice = 0;
        totalPrice += getMonthlyPayment() * loanTermInMonths;
        totalPrice += recordingFee;
        totalPrice += processingFee;
        totalPrice *= salesTaxPercentage;
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        double monthlyInterestRate = 0; //as decimal

        if (this.isFinanced && this.getVehicleSold().getPrice() > 10_000) {
            monthlyInterestRate = 4.25 / 100 / 12;
            loanTermInMonths = 48;
        } else if (this.isFinanced) {
            monthlyInterestRate = 5.25 / 100 / 12;
            loanTermInMonths = 24;
        }
        return (monthlyInterestRate * this.getVehicleSold().getPrice()) / (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths));
    }

    //Getters and Setters

    public boolean isFinanced() {
        return isFinanced;
    }

    public void setFinanced(boolean financed) {
        this.isFinanced = financed;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public double getSalesTaxPercentage() {
        return salesTaxPercentage;
    }

    public double getRecordingFee() {
        return recordingFee;
    }
}