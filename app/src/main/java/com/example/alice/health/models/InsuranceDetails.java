package com.example.alice.health.models;

/**
 * Created by alice on 1/1/18.
 */



public class InsuranceDetails {

    private String natId;
    private String policyNumber;
    private String medCover;
    private String prefHospital;

    public InsuranceDetails(){}

    public InsuranceDetails(  String natId,  String policyNumber,String medCover, String prefHospital){
        this.medCover= medCover;
        this.natId=natId;
        this.policyNumber=policyNumber;
        this.prefHospital=prefHospital;
    }

    public String getMedCover() {
        return medCover;
    }
    public String getNatId() {
        return natId;
    }
    public String getPolicyNumber() {
        return policyNumber;
    }
    public String getPrefHospital() {return prefHospital;}
}

