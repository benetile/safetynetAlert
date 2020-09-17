package com.safety.safetynetalert.contrat.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.safetynetalert.contrat.dao.MedicalRecordDao;
import com.safety.safetynetalert.contrat.dao.PersonDao;
import com.safety.safetynetalert.model.DB;
import com.safety.safetynetalert.model.MedicalRecords;
import com.safety.safetynetalert.model.Person;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonImpl implements PersonDao {


    File file = new File("safetynetalert.json");
    public static List<Person> persons = new ArrayList<>();
    DB db;

    static {
        File file = new File("safetynetalert.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
           DB db = mapper.readValue(file,DB.class);
            db.getPersons();
            persons.addAll(db.getPersons());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public Person findByFirstNameAndLastName(String firstName, String lastName) {
        for (Person person : persons)
            if(person.getFirstName().equals(firstName)&& person.getLastName().equals(lastName)){
                return person;
            }
        return null;
        }

    @Override
    public Person updateInfoPerson(String firstName, String lastName, Person personInfo) {
        Person personUpdate = findByFirstNameAndLastName(firstName, lastName);
        if(personUpdate!=null) {
            personUpdate.setAddress(personInfo.getAddress());
            personUpdate.setZip(personInfo.getZip());
            personUpdate.setCity(personInfo.getCity());
            personUpdate.setEmail(personInfo.getEmail());
            personUpdate.setPhone(personInfo.getPhone());
            return personUpdate;
        }
        return null;
    }
    @Override
    public void deleteInfoAPerson(String firstName, String lastName){
       Person personDelete = findByFirstNameAndLastName(firstName,lastName);
       persons.remove(personDelete);
    }

    @Override
    public List<Person> findAllPersons() {
        return persons;
    }

    @Override
    public List<Person> findPersonByAdress(String address) {
      List<Person> personlist = new ArrayList<>();
       for (Person person : persons){
           if(person.getAddress().equals(address)){
              personlist.add(person);
           }
       }
       return personlist;
    }

    @Override
    public Person save(Person person) {
       persons.add(person);
        return person;
    }

}
