package com.example.lab4.Models;

import java.util.UUID;
import java.io.Serializable;

public class ContactInfo implements Serializable {
    public String Id;
    public String name;
    public String email;
    public String phoneNumber;
    public String socialLink;
    public String location;

    public ContactInfo() {
        this.Id = UUID.randomUUID().toString();
    }

    public String GetId() {
        return this.Id;
    }

    public String GetName() {
        return this.name;
    }

    public void SetName(String Name) {
        this.name = Name;
    }

    public String GetEmail() {
        return this.email;
    }

    public void SetEmail(String Email) {
        this.email = Email;
    }

    public String GetPhoneNumber() {
        return this.phoneNumber;
    }

    public void SetPhoneNumber(String PhoneNumber) {
        this.phoneNumber = PhoneNumber;
    }

    public String GetSocialLink() {
        return this.socialLink;
    }

    public void SetScoialLink(String SocialLink) {
        this.socialLink = SocialLink;
    }

    public String GetLocation() {
        return this.location;
    }

    public void SetLocation(String Location) {
        this.location = Location;
    }
}