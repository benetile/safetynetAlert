package com.safety.safetynetalert.contrat.dao;

import com.safety.safetynetalert.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonDao {

    Person findByFirstNameAndLastName(String firstName, String lastName) ;

    Person updateInfoPerson(String firstName, String lastName,Person personInfo );

    void deleteInfoAPerson(String firstName,String lastName);

    List<Person> findAllPersons() ;

    List<Person> findPersonByAdress(String address);

    Person save(Person person);


}
