/*
 * Name           : Ravindu Bimsara Weerasekara
 * Student Number : 105693146
 * File Name      : Policy.java
 * 
 * Purpose        : 
 * This class represents an insurance policy in the TakeCare Insurance (TCI)
 * Management System. It defines attributes of a policy such as policy ID,
 * customer ID, cover type, cover value, duration, payment plan, start date,
 * installment amount and policy status (active/inactive).
 *
 * Variables:
 * idCounter             : Static counter used to generate unique policy IDs
 * policyId              : Unique policy identifier
 * customerId            : The ID of the customer owning the policy
 * coverType             : Type of insurance coverage (e.g., Health, Vehicle)
 * coverValue            : The value of the coverage
 * duration              : Duration in months
 * installmentAmount     : Monthly/fortnightly/yearly payment amount
 * paymentPlan           : Payment frequency ("Monthly", "Fortnight", "Yearly")
 * startDate             : Policy start date in "YYYY-MM-DD" format
 * isActive              : Policy status (active/inactive)
 */

package com.assignment.code1;

public class Policy {
    private static int idCounter = 1; //Auto-increment policy ID
    private String policyId;
    private String customerId;
    private String coverType;
    private double coverValue;
    private int duration;
    private double installmentAmount;
    private String paymentPlan;
    private String startDate;
    private boolean isActive;

 // Constructor to initialize a policy object
    public Policy(String customerId, String coverType, double coverValue, int duration, double installmentAmount, String paymentPlan, String startDate) {
        this.policyId = "P" + idCounter++; // Generate unique policy ID
        this.customerId = customerId;
        this.coverType = coverType;
        this.coverValue = coverValue > 0 ? coverValue : 0; // Ensure non-negative cover value
        this.duration = duration;
        this.installmentAmount = installmentAmount;
        this.paymentPlan = paymentPlan;
        this.startDate = startDate;
        this.isActive = true;
    }

    // Getter and Setter methods
    public String getPolicyId() {
        return policyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCoverType() {
        return coverType;
    }

    public double getCoverValue() {
        return coverValue;
    }

    public void setCoverValue(double coverValue) {
        this.coverValue = coverValue;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public String getStartDate() {
        return startDate;
    }
    public boolean isActive() {
        return isActive;
    }
    public void deactivate() {
        this.isActive = false;
    }



    // Method to update policy details
    public void updatePolicy(double newCoverValue, int newDuration, double newInstallmentAmount, String newPaymentPlan) {
        if (newCoverValue >= 0) {
            this.coverValue = newCoverValue; // Update cover value if it's non-negative
        }
        this.duration = newDuration;
        this.installmentAmount = newInstallmentAmount;
        this.paymentPlan = newPaymentPlan;
    }

    // String representation of a policy
    @Override
    public String toString() {
        return "Policy ID: " + policyId + ", Customer ID: " + customerId + ", Cover Type: " + coverType +
               ", Cover Value: $" + coverValue + ", Duration: " + duration + " months, Installment: $" +
               installmentAmount + ", Payment Plan: " + paymentPlan + ", Active: " + (isActive ? "Yes" : "No");
    }
    
 
    //method to validate cover type
    public static boolean isValidCoverType(String coverType) {
        String[] validTypes = {"Vehicle", "Health", "Property", "Pet", "Travel"};
        
        // Convert coverType to lowercase manually
        coverType = coverType.toLowerCase();  // Convert input to lowercase
        
        for (String type : validTypes) {
            // Convert each valid type to lowercase and compare
            String validType = type.toLowerCase();  // Convert valid type to lowercase
            if (validType.equals(coverType)) {
                return true;  // Return true if match is found
            }
        }
        
        return false;  // Return false if no match is found
    }

 //method to validate payment plan
    public static boolean isValidPaymentPlan(String paymentPlan) {
        String[] validPlans = {"Monthly", "Fortnight", "Yearly"};
        for (String plan : validPlans) {
            if (plan.equals(paymentPlan)) {
                return true;
            }
        }
        return false;
    }
    
    // Validate date format as YYYY-MM-DD
    public static boolean isValidDateFormat(String date) {
        // Simple check if the format matches YYYY-MM-DD pattern
        if (date.length() != 10) return false; // Must be in format of 10 characters

        // Check if the hyphens are at the correct positions
        if (date.charAt(4) != '-' || date.charAt(7) != '-') return false;

        // Manually check if characters are digits (ignoring the hyphens)
        for (int i = 0; i < date.length(); i++) {
            if (i == 4 || i == 7) continue; // Skip hyphen positions
            char ch = date.charAt(i);
            
            // Check if character is not a digit
            if (ch < '0' || ch > '9') {
                return false; // Return false if character is not a digit
            }
        }
        return true;
    }
    
 // Search for a policy by ID in all customers
    public static void searchPolicyById(String policyId, Customer[] customers, int customerCount) {
        boolean policyFound = false;
        for (int i = 0; i < customerCount; i++) {
            Customer c = customers[i];
            for (int j = 0; j < c.getPolicyCount(); j++) {
                Policy p = c.getPolicies()[j];
                if (p != null && p.getPolicyId().equals(policyId)) {
                    System.out.println("\n===== Policy Details =====");
                    System.out.println("Policy ID: " + p.getPolicyId());
                    System.out.println("Customer: " + c.getName() + " (ID: " + c.getCustomerId() + ")");
                    System.out.println("Cover Type: " + p.getCoverType());
                    System.out.println("Cover Value: $" + String.format("%.2f", p.getCoverValue()));
                    System.out.println("Duration: " + p.getDuration() + " months");
                    System.out.println("Installment Amount: $" + String.format("%.2f", p.getInstallmentAmount()));
                    System.out.println("Payment Plan: " + p.getPaymentPlan());
                    System.out.println("Start Date: " + p.getStartDate());
                    System.out.println("Status: " + (p.isActive() ? "Active" : "Inactive"));
                    policyFound = true;
                    break;
                }
            }
            if (policyFound) break;
        }

        if (!policyFound) {
            System.out.println("Policy not found.");
        }
    }
    
    // Calculate total cover and installment summaries across all customers
    public static void calculateInsuranceSummary(Customer[] customers, int customerCount) {
        if (customerCount == 0) {
            System.out.println("No customers or policies found.");
            return;
        }

        double totalCoverValue = 0;
        double totalInstallmentAmount = 0;
        int totalActivePolicies = 0;
        int totalInactivePolicies = 0;

        System.out.println("\n===== Insurance Summary =====");
        System.out.println(String.format("%-20s %-20s %-15s %-15s", "Customer", "Cover Value", "Installment", "# Policies"));
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < customerCount; i++) {
            Customer c = customers[i];
            double customerCoverValue = 0;
            double customerInstallmentAmount = 0;
            int activePolicies = 0;

            for (int j = 0; j < c.getPolicyCount(); j++) {
                Policy p = c.getPolicies()[j];
                if (p != null) {
                    if (p.isActive()) {
                        customerCoverValue += p.getCoverValue();
                        customerInstallmentAmount += p.getInstallmentAmount();
                        activePolicies++;
                        totalActivePolicies++;
                    } else {
                        totalInactivePolicies++;
                    }
                }
            }

            // Only show customers with policies
            if (c.getPolicyCount() > 0) {
                System.out.println(String.format("%-20s $%-19.2f $%-14.2f %-15d", 
                                                  c.getName(), customerCoverValue, customerInstallmentAmount, activePolicies));
            }

            totalCoverValue += customerCoverValue;
            totalInstallmentAmount += customerInstallmentAmount;
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println(String.format("%-20s $%-19.2f $%-14.2f", "TOTAL:", totalCoverValue, totalInstallmentAmount));
        System.out.println("\nActive Policies: " + totalActivePolicies);
        System.out.println("Inactive Policies: " + totalInactivePolicies);
        System.out.println("Total Policies: " + (totalActivePolicies + totalInactivePolicies));
    }
    
}


