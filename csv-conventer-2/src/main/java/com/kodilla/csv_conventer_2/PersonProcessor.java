package com.kodilla.csv_conventer_2;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.Period;

public class PersonProcessor implements ItemProcessor<PersonInput, PersonOutput> {

    @Override
    public PersonOutput process(PersonInput item) throws Exception {

        int age = Period.between(item.getBirthDate(), LocalDate.now()).getYears();
        return new PersonOutput(item.getFirstName(), item.getLastName(), age);
    }
}
