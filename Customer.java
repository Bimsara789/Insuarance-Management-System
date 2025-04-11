/**
 * Name:Ravindu Bimsara Weerasekara
 * Student Number: 105693146
 * File Name: Customer.java
 * 
 * Purpose:
 * The Customer class represents a customer in an insurance management system. 
 * Each customer can have up to 5 policies, and the class includes functionality for 
 * adding, deactivating, and retrieving policies, as well as calculating the total cover 
 * value and installment amounts for a customer.
 * 
 * Key Variables:
 * customerId  : A unique ID for each customer, automatically generated when the customer is created.
 * name        : The customer's name.
 * address     : The customer's address.
 * policies    : An array to store up to 5 policies for the customer.
 * policyCount : A counter to track how many policies the customer currently holds.
 * 
 * 
 */

package com.assignment.code1;

import java.util.Scanner;

public class Customer {
	// Static counter to generate unique customer IDs
    private static int idCounter = 1;
    
    // Instance variables representing a customer's attributes
    private String customerId;
    private String name;
    private String address;
    private Policy[] policies; // Fixed-size array for policies
    private int policyCount;

    
    /**
     * Constructor for creating a new customer with a name and address.
     * The customer ID is automatically generated.
     */
    public Customer(String name, String address) {
        this.customerId = "C" + idCounter++; // Generate unique ID
        this.name = name;
        this.address = address;
        this.policies = new Policy[5]; // A customer can have a maximum of 5 policies
        this.policyCount = 0; // Initially no policies
    }

    
 // Getter methods for retrieving customer details
    public String getCustomerId() { 
    	return customerId; 
    }
    public String getName() { 
    	return name; 
    }
    public String getAddress() { 
    	return address; 
    }
    public Policy[] getPolicies() { 
    	return policies; 
    }
    public int getPolicyCount() { 
    	return policyCount; 
    }
    

    //Adds a new policy to the customer.
    public void addPolicyFromInput(Scanner scanner) {
        if (getPolicyCount() < 5) {
            // Get cover type
            System.out.print("Enter Cover Type (Vehicle/Health/Property/Pet/Travel): ");
            String coverType = scanner.nextLine();
            
            while (!Policy.isValidCoverType(coverType)) {
                System.out.print("Invalid cover type. Enter Cover Type (Vehicle/Health/Property/Pet/Travel): ");
                coverType = scanner.nextLine();
            }
            
            // Get cover value
            System.out.print("Enter Cover Value: ");
            double coverValue = scanner.nextDouble();
            
            while (coverValue < 0) {
                System.out.print("Cover value must be positive. Enter Cover Value: ");
                coverValue = scanner.nextDouble();
            }
            
            // Get duration
            System.out.print("Enter Duration (months): ");
            int duration = scanner.nextInt();
            
            while (duration <= 0 || duration > 120) {
                System.out.print("Duration must be between 1 and 120 months. Enter Duration: ");
                duration = scanner.nextInt();
            }
            
            // Get installment amount
            System.out.print("Enter Installment Amount: ");
            double installmentAmount = scanner.nextDouble();
            scanner.nextLine();  // Consume newline
            
            while (installmentAmount < 0) {
                System.out.print("Installment amount must be positive. Enter Installment Amount: ");
                installmentAmount = scanner.nextDouble();
                scanner.nextLine();  // Consume newline
            }
            
            // Get payment plan
            System.out.print("Enter Payment Plan (Monthly/Fortnight/Yearly): ");
            String paymentPlan = scanner.nextLine();
            
            while (!Policy.isValidPaymentPlan(paymentPlan)) {
                System.out.print("Invalid payment plan. Enter Payment Plan (Monthly/Fortnight/Yearly): ");
                paymentPlan = scanner.nextLine();
            }
            
            // Get start date
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine();
            
            while (!Policy.isValidDateFormat(startDate)) {
                System.out.print("Invalid date format. Enter Start Date (YYYY-MM-DD): ");
                startDate = scanner.nextLine();
            }
            
            // Create and add the policy to the customer's list
            Policy policy = new Policy(getCustomerId(), coverType, coverValue, duration, installmentAmount, paymentPlan, startDate);
            addPolicy(policy);
            System.out.println("Policy added successfully!");
        } else {
            System.out.println("Customer already has 5 policies.");
        }
    }
    
    

     //Updates an existing policy for the customer.
    public void updatePolicyFromInput(Scanner scanner) {
        System.out.print("Enter Policy ID to update: ");
        String policyId = scanner.nextLine();
        Policy policy = Customer.findPolicyById(this, policyId);
        
        if (policy != null) {
            if (!policy.isActive()) {
                System.out.println("Cannot update inactive policy.");
                return;
            }
            
            // Get new cover value
            System.out.print("Enter new Cover Value: ");
            double coverValue = scanner.nextDouble();
            
            while (coverValue < 0) {
                System.out.print("Cover value must be positive. Enter new Cover Value: ");
                coverValue = scanner.nextDouble();
            }
            
            // Get new duration
            System.out.print("Enter new Duration (months): ");
            int duration = scanner.nextInt();
            
            while (duration <= 0 || duration > 120) {
                System.out.print("Duration must be between 1 and 120 months. Enter new Duration: ");
                duration = scanner.nextInt();
            }
            
            // Get new installment amount
            System.out.print("Enter new Installment Amount: ");
            double installmentAmount = scanner.nextDouble();
            scanner.nextLine();  // Consume newline
            
            while (installmentAmount < 0) {
                System.out.print("Installment amount must be positive. Enter new Installment Amount: ");
                installmentAmount = scanner.nextDouble();
                scanner.nextLine();  // Consume newline
            }
            
            // Get new payment plan
            System.out.print("Enter new Payment Plan (Monthly/Fortnight/Yearly): ");
            String paymentPlan = scanner.nextLine();
            
            while (!Policy.isValidPaymentPlan(paymentPlan)) {
                System.out.print("Invalid payment plan. Enter new Payment Plan (Monthly/Fortnight/Yearly): ");
                paymentPlan = scanner.nextLine();
            }

            // Use the updatePolicy method
            policy.updatePolicy(coverValue, duration, installmentAmount, paymentPlan);
            System.out.println("Policy updated successfully!");
        } else {
            System.out.println("Policy not found.");
        }
    }
    
  //Method to find a policy by its ID from the customer's policies.
    public static Policy findPolicyById(Customer customer, String policyId) {
        for (int j = 0; j < customer.getPolicyCount(); j++) {
            Policy p = customer.getPolicies()[j];
            if (p != null && p.getPolicyId().equals(policyId)) {
                return p;
            }
        }
        return null;
    }
    
    //Method to add a policy to the customer's policies list.
    public void addPolicy(Policy policy) {
    	// Check if there's space for more policies
        if (policyCount < 5) {
            policies[policyCount++] = policy;// Add the policy and increment the count
        } else {
            System.out.println("Customer already has 5 policies.");
        }
    }

    //Method to deactivate a policy based on the given policy ID.
    public void deactivatePolicy(String policyId) {
        for (int i = 0; i < policyCount; i++) {
        	// If policy with matching ID is found, deactivate it
            if (policies[i].getPolicyId().equals(policyId)) {
                policies[i].deactivate(); // Call the deactivate method of the Policy class
                System.out.println("Policy " + policyId + " has been deactivated.");
                return;
            }
        }
        System.out.println("Policy not found.");
    }

  //Method to search and displays a customer by their ID and lists all their policies.
    public static void searchCustomerById(String searchCustomerId, Customer[] customers, int customerCount) {
        Customer customer = findCustomerById(searchCustomerId, customers, customerCount);
        if (customer != null) {
            System.out.println("\n===== Customer Details =====");
            System.out.println(String.format("%-15s %-25s %-30s", "Customer ID", "Name", "Address"));
            System.out.println("-----------------------------------------------------------");
            System.out.println(String.format("%-15s %-25s %-30s", 
                              customer.getCustomerId(), customer.getName(), customer.getAddress()));

            if (customer.getPolicyCount() > 0) {
                System.out.println("\nPolicies:");
                System.out.println(String.format("%-12s %-15s %-12s %-12s %-12s %-15s %-10s", 
                              "Policy ID", "Cover Type", "Cover Value", "Duration", "Installment", "Payment Plan", "Status"));
                System.out.println("--------------------------------------------------------------------------------------------");
                
                for (int j = 0; j < customer.getPolicyCount(); j++) {
                    Policy p = customer.getPolicies()[j];
                    if (p != null) {
                        System.out.println(String.format("%-12s %-15s $%-11.2f %-12d $%-11.2f %-15s %-10s", 
                                      p.getPolicyId(), p.getCoverType(), p.getCoverValue(), 
                                      p.getDuration(), p.getInstallmentAmount(), p.getPaymentPlan(), 
                                      (p.isActive() ? "Active" : "Inactive")));
                    }
                }
            } else {
                System.out.println("\nNo policies found for this customer.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }
    
 // Method to find a customer by ID
    public static Customer findCustomerById(String id, Customer[] customers, int customerCount) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getCustomerId().equals(id)) {
                return customers[i]; // Return the customer if ID matches
            }
        }
        return null; // Return null if no matching customer is found
    }

    
    //Searches customers by name
    public static void searchCustomerByName(String name, Customer[] customers, int customerCount) {
        boolean found = false;
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getName().toLowerCase().contains(name.toLowerCase())) {
                if (!found) {
                    System.out.println("\n===== Matching Customers =====");
                    System.out.println(String.format("%-15s %-25s %-30s", "Customer ID", "Name", "Address"));
                    System.out.println("-----------------------------------------------------------");
                    found = true;
                }
                
                System.out.println(String.format("%-15s %-25s %-30s", 
                              customers[i].getCustomerId(), customers[i].getName(), customers[i].getAddress()));
            }
        }
        
        if (!found) {
            System.out.println("No customers found with that name.");
        }
    }
    
    
    //Calculates the total cover value of all active policies.
    public double getTotalCoverValue() {
        double total = 0; // Variable to store the total cover value
        
     // Loop through the policies array and sum cover values
        for (int i = 0; i < policyCount; i++) {
            if (policies[i] != null) total += policies[i].getCoverValue();
        }
        return total;
    }
    
    //Calculates the total installment amount of all active policies.
    public double getTotalInstallmentAmount() {
        double total = 0;
        
     // Loop through the policies array and sum installment amounts
        for (int i = 0; i < policyCount; i++) {
            if (policies[i] != null) total += policies[i].getInstallmentAmount();
        }
        return total;
    }
    
   
    
}


