/*
 * Name:Ravindu Bimsara Weerasekara
 * Student Number: 105693146
 * File: InsuranceManagementSystem.java
 * 
 * Purpose:
 * This program is a menu-driven Insurance Management System for TakeCare Insurance (TCI).
 * It allows adding customers, assigning policies to them, updating/deactivating policies,
 * viewing records, searching for customers and policies, and generating summary reports.
 * 
 * Input:
 * - User inputs customer details (name, address)
 * - User inputs policy details (cover type, value, duration, etc.)
 * - Menu selections from 1 to 9
 * 
 * Output:
 * - Confirmation messages (e.g., customer/policy added or not found)
 * - Tabular display of customer or policy data
 * - Summary report of total cover and installment amounts
 * 
 * Variables:
 * - customers: array storing Customer objects (max 7).
 * - customerCount: current number of customers added.
 * - scanner: Scanner object to read user input.
 * - choice: user input for main menu selection.
 */

package com.assignment.code1;

import java.util.Scanner;

public class InsuranceManagementSystem {
	
	//array to store a maximum of 7 customers
    private static Customer[] customers = new Customer[7];  // Max 7 customers
    private static int customerCount = 0; //counter to track the numbers added

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

     //Infinite loop to keep displaying menu until user exits
        while(true){
            // Display menu
            System.out.println("\n======== Insurance Management System - TakeCare Insurance (TCI) ========");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Policy");
            System.out.println("3. Update Policy");
            System.out.println("4. Delete (Deactivate) Policy");
            System.out.println("5. View Customers");
            System.out.println("6. View Policies");
            System.out.println("7. Search Customer/Policy");
            System.out.println("8. Calculate Total Cover Value and Installment Amount");
            System.out.println("9. Exit");
            System.out.println("====================================================================");
            
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();
            
         // Input validation for menu options (1-6)
            boolean validChoice = false;
            while (!validChoice) {
                // Check if the input is a valid number between 1 and 6
                if (choice.length() == 1 && choice.charAt(0) >= '1' && choice.charAt(0) <= '9') {
                    validChoice = true;  // If valid, break the loop
                } else {
                    // Print invalid input message and re-prompt
                    System.out.println("Invalid input. Please select a valid option (1-9).");
                    choice = scanner.nextLine();  // Re-prompt for input
                }
            }

            // Convert valid input to integer
            int selectedOption = choice.charAt(0) - '0';  // Convert char to int

            switch (selectedOption) {
                case 1:  // Add Customer
                    if (customerCount < 7) {
                        System.out.print("Enter Customer Name: ");
                        String name = scanner.nextLine();
                        
                        while (name.isEmpty()) {
                            System.out.print("Name cannot be empty. Enter Customer Name: ");
                            name = scanner.nextLine();
                        }
                        
                        System.out.print("Enter Address: ");
                        String address = scanner.nextLine();
                        
                        while (address.isEmpty()) {
                            System.out.print("Address cannot be empty. Enter Address: ");
                            address = scanner.nextLine();
                        }
                        
                        //Create and store new customer
                        customers[customerCount++] = new Customer(name, address);
                        System.out.println("Customer added successfully...");
                    } else {
                        System.out.println("Maximum customer limit reached.");
                    }
                    break;
                    
                case 2://add policy
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    Customer customer = Customer.findCustomerById(customerId, customers, customerCount);
                    
                    if (customer != null) {
                        customer.addPolicyFromInput(scanner); // Call the method to add policy
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                    
                case 3://update policy
                    System.out.print("Enter Customer ID: ");
                    customerId = scanner.nextLine();
                    customer = Customer.findCustomerById(customerId, customers, customerCount);
                    
                    if (customer != null) {
                        customer.updatePolicyFromInput(scanner); // Call the method to update policy
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;

                    
                case 4:  // Delete (Deactivate) Policy
                    System.out.print("Enter Customer ID: ");
                    customerId = scanner.nextLine();
                    customer = Customer.findCustomerById(customerId, customers, customerCount);
                    
                    if (customer != null) {
                        System.out.print("Enter Policy ID to deactivate: ");
                        String policyId = scanner.nextLine();
                        customer.deactivatePolicy(policyId);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                    
                case 5:  // View Customers
                    if (customerCount == 0) {
                        System.out.println("No customers found.");
                        break;
                    }
                    
                    System.out.println("\n===== Customer List =====");
                    System.out.println(String.format("%-15s %-25s %-30s", "Customer ID", "Name", "Address"));
                    System.out.println("-----------------------------------------------------------");
                    
                    for (int i = 0; i < customerCount; i++) {
                        Customer c = customers[i];
                        System.out.println(String.format("%-15s %-25s %-30s", 
                                          c.getCustomerId(), c.getName(), c.getAddress()));
                    }
                    break;
                    
                case 6:  // View Policies
                    if (customerCount == 0) {
                        System.out.println("No customers or policies found.");
                        break;
                    }
                    
                    boolean foundPolicies = false;
                    
                 // Display policies for the customer
                    for (int i = 0; i < customerCount; i++) {
                        Customer c = customers[i];
                        if (c.getPolicyCount() > 0) {
                            foundPolicies = true;
                            System.out.println("\nCustomer: " + c.getName() + " (ID: " + c.getCustomerId() + ")");
                            System.out.println(String.format("%-12s %-15s %-12s %-12s %-12s %-15s %-10s", 
                                              "Policy ID", "Cover Type", "Cover Value", "Duration", "Installment", "Payment Plan", "Status"));
                            System.out.println("--------------------------------------------------------------------------------------------");
                            
                            for (int j = 0; j < c.getPolicyCount(); j++) {
                                Policy p = c.getPolicies()[j];
                                if (p != null) {
                                	// Print policy info
                                    System.out.println(String.format("%-12s %-15s $%-11.2f %-12d $%-11.2f %-15s %-10s", 
                                                      p.getPolicyId(), p.getCoverType(), p.getCoverValue(), 
                                                      p.getDuration(), p.getInstallmentAmount(), p.getPaymentPlan(), 
                                                      (p.isActive() ? "Active" : "Inactive")));
                                }
                            }
                            System.out.println();
                        }
                    }
                    
                    if (!foundPolicies) {
                        System.out.println("No policies found.");
                    }
                    break;
                    
                case 7:  // Search Customer/Policy
                    System.out.println("\nSearch by:");
                    System.out.println("1. Customer ID");
                    System.out.println("2. Customer Name");
                    System.out.println("3. Policy ID");
                    System.out.print("Enter your choice: ");
                    int searchChoice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    switch (searchChoice) {
                    case 1:  // Search by Customer ID
                        System.out.print("Enter Customer ID: ");
                        String searchCustomerId = scanner.nextLine();
                        Customer.searchCustomerById(searchCustomerId, customers, customerCount);
                        break;

                    case 2:  // Search by Customer Name
                        System.out.print("Enter Customer Name: ");
                        String name = scanner.nextLine();
                        Customer.searchCustomerByName(name, customers, customerCount);
                        break;

                    case 3:  // Search by Policy ID
                        System.out.print("Enter Policy ID: ");
                        String policyId = scanner.nextLine();
                        Policy.searchPolicyById(policyId, customers, customerCount);
                        break;

                        default:
                            System.out.println("Invalid choice.");
                            break;
                    }
                    break;
                    
                case 8:  // Calculate Total Cover Value and Installment Amount
                    Policy.calculateInsuranceSummary(customers, customerCount);
                    break;
                    
                case 9:
                    System.out.println("Exiting the system...");
                    scanner.close();  // Close the scanner to free up resources
                    return;
                    
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
            
        }
    }

}