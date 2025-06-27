package com.kodilla.csv_conventer_2;

import java.time.LocalDate;

public class PersonInput {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public PersonInput() {
    }

    public PersonInput(String firstNName, String lastNName, LocalDate birthDate) {
        this.firstName = firstNName;
        this.lastName = lastNName;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
