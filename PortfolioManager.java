/*
Author: Alisa Palson
Date created: 11/25/2024
Purpose: Final Course Project
*/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PortfolioManager {
    private static Scanner scnr = new Scanner(System.in);
    private static ArrayList<TransactionHistory> portfolioList = new ArrayList<TransactionHistory>();
    private static double cash = 0.0;

    private static void depositCash(){
        // get date and amount of cash
        System.out.print("Enter deposit date: ");
        String date = scnr.next();
        System.out.print("Enter amount of deposit: ");
        double amount = scnr.nextDouble();
        // inc cash and make cash transaction
        cash += amount;
        TransactionHistory lineItem = new TransactionHistory("CASH", date, "DEPOSIT", amount, 1.0);
        portfolioList.add(lineItem);
        // blank line
        System.out.println();
    }

    private static void withdrawCash(){
        // get date and amount of cash
        System.out.print("Enter withdraw date: ");
        String date = scnr.next();
        System.out.print("Enter amount of withdraw: ");
        double amount = scnr.nextDouble();

        // check that there is enough to withdraw
        if(cash < amount){
            System.out.println("\nError! You do not have enough cash to withdraw. \n");
        }
        else{
            // dec cash and make cash transaction
            cash -= amount;
            TransactionHistory lineItem = new TransactionHistory("CASH", date, "WITHDRAW", amount * -1, 1.0);
            portfolioList.add(lineItem);
        }
        
        // blank line
        System.out.println();
    }

    private static void buyStocks(){
        // get stock info
        System.out.print("Enter stock purchase date: ");
        String date = scnr.next();
        System.out.print("Enter stock ticker: ");
        String ticker = scnr.next();
        ticker = ticker.toUpperCase();
        System.out.print("Enter stock quantity: ");
        double quantity = scnr.nextDouble();
        System.out.print("Enter stock cost (basis): ");
        double cost = scnr.nextDouble();

        double totalCost = quantity * cost;
        // check to see if we have money
        if(totalCost > cash){
            System.out.println("\nError. You do not have the cash for that purchase. \n");
        }
        else {
            // add stock purchase to portfolio
            portfolioList.add(new TransactionHistory(ticker, date, "BUY", quantity, cost));
            // remove the purchase cost from cash
            cash -= totalCost;
            // add cash withdraw to buy stock to portfolio list
            portfolioList.add(new TransactionHistory("CASH", date, "WITHDRAW", totalCost * -1, 1.0));
        }
        // blank line
        System.out.println();
    }

    private static void sellStocks(){
        // get stock info
        System.out.print("Enter stock sell date: ");
        String date = scnr.next();

        System.out.print("Enter ticker of stock to sell: ");
        String ticker = scnr.next();
        ticker = ticker.toUpperCase(); // ensure ticker name is uppercase

        System.out.print("Enter amount to sell: ");
        double qty = scnr.nextDouble();

        System.out.print("Enter stock cost (basis): ");
        double cost = scnr.nextDouble();

        double total = 0.0;
        boolean hasStock = false;
        // ensure stock is available to sell
        for (TransactionHistory record : portfolioList) {
            if(record.getTicker().equals(ticker) && record.getTransType().equals("BUY")){
                hasStock = true;
                total += record.getQty();
            }
        }
        // check if the stock is available to sell
        if(!hasStock){
            System.out.println("\nError! No record of " + ticker + " in transaction history");
        }
        // check if amount to sell is more than the amount owned
        else if(qty > total){
            System.out.println("\nError! You do not have that many stocks to sell.");
        }
        else {
            // add buy stock transaction history record
            portfolioList.add(new TransactionHistory(ticker, date, "SELL", qty, cost));
            // update cash amount
            cash += (qty * cost);
            // add deposit cash history record
            portfolioList.add(new TransactionHistory("CASH", date, "DEPOSIT", (qty * cost), 1.0));
        }
        // blank line
        System.out.println();

    }

    private static void displayTransactionHistory(){
        System.out.println("\t\tAlisa Palson Brokerage Account");
        System.out.println("\t\t===========================");
        System.out.println();
        System.out.printf("%-16s%-10s%8s%15s     %s%n", "Date", "Ticker", "Quantity", "Cost Basis", "Trans Type");
        System.out.println("==================================================================");
        // loop through every transaction in the portfolio and list the information
        for (TransactionHistory record : portfolioList) {
            String costBasis = String.format("$%.2f", record.getCostBasis());
            System.out.printf("%-16s%-10s%8.0f%15s     %s%n", record.getTransDate(), record.getTicker(), record.getQty(), costBasis, record.getTransType());
        }
        // blank line
        System.out.println();
    }

    private static void displayPortfolio(){
        // date time formatter used to display current date and time of accessing the portfolio
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");


        System.out.println("\nPortfolio as of: " + dtf.format(LocalDateTime.now()));
        System.out.println("====================================\n");
        System.out.printf("%-8s%s\n", "Ticker", "Quantity");
        System.out.println("================");

        System.out.printf("%-8s%.2f\n", "CASH", cash);

        // create an array to store the stocks we have
        ArrayList<String> stocks = new ArrayList<String>();
        for (TransactionHistory record : portfolioList) {
            if(record.getTicker().equals("CASH")){
                continue;
            }
            if(stocks.contains(record.getTicker()) == false){
                stocks.add(record.getTicker());
            }
        }
        // loop through the stocks and portfolio list to determine the amount owned
        for (String stock : stocks) {
            double total = 0.0;
            for (TransactionHistory record : portfolioList) {
                // check if we have the stock
                if(record.getTicker().equals(stock)){
                    // check that the record is for the stock being purchased, not sold
                    if(record.getTransType().equals("BUY")){
                        // add the amount to the total
                        total += record.getQty();
                    }
                    else{
                        // if it was a SELL record, decrease the amount from the total
                        total -= record.getQty();
                    }
                }
            }
            // only show stocks with shares
            if(total > 0.0){
                System.out.printf("%-8s%.0f\n", stock, total);
            }
        }
        // blank line
        System.out.println();
    }

    public static void main(String[] args) {
        int choice = 0;

        do { 
            System.out.println("Alisa Palson Brokerage Account");
            System.out.println("0 - Exit");
            System.out.println("1 - Deposit Cash");
            System.out.println("2 - Withdraw Cash");
            System.out.println("3 - Buy Stock");
            System.out.println("4 - Sell Stock");
            System.out.println("5 - Display Transaction History");
            System.out.println("6 - Display Portfolio");
            
            try {
                System.out.println("\nEnter option (0 to 6):");
                choice = scnr.nextInt();

            } catch (InputMismatchException e) {
                // clear the input buffer
                scnr.nextLine();
                System.out.println("\nError! Please use numbers only. Do not use symbols or text");
                System.out.print("Press enter to continue...");
                scnr.nextLine();
                System.out.println();
                choice = -1; // set choice to -1 to continue with while loop
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("\nGoodbye!");
                    break;
                case 1:
                    depositCash();
                    break;
                case 2:
                    withdrawCash();
                    break;
                case 3:
                    buyStocks();
                    break;
                case 4:
                    sellStocks();
                    break;
                case 5:
                    displayTransactionHistory();
                    break;
                case 6:
                    displayPortfolio();
                    break;
                default:
                    System.out.println("\nError. Please select from the menu.\n");
            }
        } while (choice != 0);
    }
}