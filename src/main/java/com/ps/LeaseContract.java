package com.ps;


// omitted expected ending value


public class LeaseContract extends DealershipContract {

    private double leaseFee;
    private int loanTermInMonths = 36;

    public LeaseContract(String dateOfContract,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicleSold) {
        super(dateOfContract, customerName, customerEmail, vehicleSold);


        this.leaseFee = vehicleSold.getPrice() * .07;
    }

    //Methods
    @Override
    public double getTotalPrice() {
        double totalPrice = 0;
        totalPrice += getMonthlyPayment() * loanTermInMonths;
        totalPrice += leaseFee;
        return totalPrice;
    }

    @Override
    public double getMonthlyPayment() {
        double monthlyInterestRate = 4.0 /100 /12; //as decimal

        return (monthlyInterestRate * this.getVehicleSold().getPrice()) / (1 - Math.pow(1 + monthlyInterestRate, - loanTermInMonths));
    }

    //Getters and Setters



    public double getLeaseFee() {
        return leaseFee;
    }
}