package com.example.alice.health.models;

/**
 * Created by alice on 1/1/18.
 */

public class medicalDetails {

    private String name;
    private String age;
    private String gender;
    private String phoneNumber;
    private String bloodGroup;
    private String condition;
    private String medAllergies;
    private String userAllegies;

    public medicalDetails() {}

    public medicalDetails( String name, String age, String phoneNumber,String gender,String bloodGroup, String condition, String medAllergies, String userAllegies) {
        this.name = name;
        this.age = age;
        this.phoneNumber=phoneNumber;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.condition=condition;
        this.medAllergies= medAllergies;
        this.userAllegies= userAllegies;
    }
    public String getName() {
        return name;
    }
    public String getAge()
    {
        return age;
    }
    public String getGender() {
        return gender;
    }
    public  String getPhoneNumber(){
        return  phoneNumber;
    }
    public  String getBloodGroup(){
        return  bloodGroup;
    }
    public  String getCondition(){
        return  condition;
    }
    public  String getMedAllergies(){
        return  medAllergies;
    }
    public  String getUserAllergies(){
        return userAllegies;
    }

}
