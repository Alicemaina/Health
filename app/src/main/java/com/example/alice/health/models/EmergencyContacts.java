package com.example.alice.health.models;

/**
 * Created by alice on 1/1/18.
 */


public class EmergencyContacts {
    private String  EmergencyContactNameOne;
    private String  EmergencyContactNumberOne;
    private String  EmergencyContactNameTwo;
    private String  EmergencyContactNumberTwo;
    private String  EmergencyContactNameThree;
    private String  EmergencyContactNumberThree;

    public EmergencyContacts(){}

    public EmergencyContacts(String  EmergencyContactNameOne, String  EmergencyContactNumberOne,String  EmergencyContactNameTwo, String  EmergencyContactNumberTwo,String  EmergencyContactNameThree, String  EmergencyContactNumberThree) {
        this.EmergencyContactNameOne = EmergencyContactNameOne;
        this.EmergencyContactNumberOne = EmergencyContactNumberOne;
        this.EmergencyContactNameTwo = EmergencyContactNameTwo;
        this.EmergencyContactNumberTwo = EmergencyContactNumberTwo;
        this.EmergencyContactNameThree = EmergencyContactNameThree;
        this.EmergencyContactNumberThree = EmergencyContactNumberThree;
    }
    public  String getEmergencyContactNameOne(){
        return EmergencyContactNameOne;
    }
    public String getEmergencyContactNumberOne(){
        return EmergencyContactNumberOne;
    }
    public  String getEmergencyContactNameTwo(){return EmergencyContactNameTwo;}
    public String getEmergencyContactNumberTwo(){
        return EmergencyContactNumberTwo;
    }
    public  String getEmergencyContactNameThree(){
        return EmergencyContactNameThree;
    }
    public String getEmergencyContactNumberThree(){
        return EmergencyContactNumberThree;
    }
}
