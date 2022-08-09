package com.example.domain;

/**
 * A POJO for an insurance policy.
 * Note there are no dependencies (imports) beyond core Java
 */
public class Policy {
    private String policyNumber;
    private int faceAmount;
    private String insured;
    private int age;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public int getFaceAmount() {
        return faceAmount;
    }

    public void setFaceAmount(int faceAmount) {
        this.faceAmount = faceAmount;
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
