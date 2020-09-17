package com.safety.safetynetalert.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safety.safetynetalert.contrat.dao.FirestationDao;
import com.safety.safetynetalert.contrat.dao.MedicalRecordDao;
import com.safety.safetynetalert.contrat.dao.PersonDao;
import com.safety.safetynetalert.model.Firestations;
import com.safety.safetynetalert.model.MedicalRecords;
import com.safety.safetynetalert.model.Person;
import com.safety.safetynetalert.model.PersonMedicalRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ControllerUrls {


    @Autowired
    private PersonDao personDao;
    @Autowired
    private FirestationDao firestationDao;
    @Autowired
    private MedicalRecordDao medicalRecordDao;

    //methode pour renvoyer une liste des personnes couvertes par la caserne de pompiers
    @GetMapping(value = "/station")
    public MappingJacksonValue getStationNumber (@RequestParam("stationNumber") int station){
        List<Person> listPerson = new ArrayList<>();
        List<Person> personList = personDao.findAllPersons();
        int i = 0;

        SimpleBeanPropertyFilter monfiltre = SimpleBeanPropertyFilter.serializeAllExcept("zip","email","city");
        FilterProvider myList = new SimpleFilterProvider().addFilter("filterPerson",monfiltre);

        MappingJacksonValue personByStation = null;
        for ( Firestations fire: firestationDao.finbAllFirestations()) {
            if (fire.getStation()==station){
                for (Person person : personList){
                    if (fire.getAddress().contains(person.getAddress())){
                        listPerson.add(person);
                        personByStation = new MappingJacksonValue(listPerson);
                        personByStation.setFilters(myList);
                        i++;
                    }
                }
            }
        }
        System.out.println(i);
        return personByStation;
    }
    //cette methode retourne une liste d'enfants qui ont au moins 18 ans qui habite à cette address
    @GetMapping(value = "/childAlert")
    public List<Person> getListToChild(@RequestParam("address") String address) throws ParseException {
        List<Person> recordsList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();

        int nbr = 0;
        Firestations firestation = firestationDao.findStationByAdress(address);
        if(firestation.getAddress().equals(address)){
            for(Person person : personDao.findAllPersons()){
                if(person.getAddress().equals(address)) {
                    for(MedicalRecords records : medicalRecordDao.findAllMedicalrecords()) {
                        if (records.getFirstName().equals(person.getFirstName()) && records.getLastName().equals(person.getLastName())) {
                        String date = records.getBirthdate();
                        Date birdhdate = dateFormat.parse(date);
                        long age = today.getYear()-birdhdate.getYear();
                        if (age<=18){
                            person.setMedicalRecords(records);
                            recordsList.add(person);
                            nbr++;
                        }
                        }
                    }
                }
            }
        }
        System.out.println(nbr);
        return recordsList;
    }

  //methode pour renvoyer les numeros de telephone de foyers deservis par la caserne
    @GetMapping(value = "/phoneAlert")
    public MappingJacksonValue getPhoneAPerson(@RequestParam("firestation") int station){
        List<Person> listPhone = new ArrayList<>();
        List<Person> personList = personDao.findAllPersons();

        SimpleBeanPropertyFilter monfiltre = SimpleBeanPropertyFilter.serializeAllExcept("lastName","address","zip","email","city");
        FilterProvider myList = new SimpleFilterProvider().addFilter("filterPerson",monfiltre);
        for (Firestations fire : firestationDao.finbAllFirestations()) {
            if (fire.getStation() == station) {
                for (Person person : personList) {
                    if (fire.getAddress().equals(person.getAddress())) {
                        listPhone.add(person);
                    }
                }
            }
        }
        MappingJacksonValue emailFilter = new MappingJacksonValue(listPhone);
        emailFilter.setFilters(myList);
        return emailFilter;

    }

    //cette methode retourne la liste des habitants qui vivent à l'address donnée
    @GetMapping(value = "/fire")
    public List<Person> getHabitantWithAddress(@RequestParam("address") String address){
      List<Person> personMedicalRecordsList = new ArrayList<>();
      Firestations fire = firestationDao.findStationByAdress(address);

       if(fire.getAddress().equals(address)){
           for(Person person : personDao.findAllPersons()){
               if(person.getAddress().equals(address)) {
                   for(MedicalRecords records : medicalRecordDao.findAllMedicalrecords()) {
                       if (records.getFirstName().equals(person.getFirstName()) && records.getLastName().equals(person.getLastName())) {
                           person.setMedicalRecords(records);
                           personMedicalRecordsList.add(person);
                       }
                   }
               }
           }
       }
        return personMedicalRecordsList;

    }

    //Cette methode retourner une liste de tous les foyers desservis par la caserne
    @GetMapping(value = "/flood")
    public List<Person> getFloodByHome(@RequestParam("stations") int stations)throws IOException {
        List<Person> listPersonFlood = new ArrayList<>();
        List<Firestations> firestationsList = firestationDao.finbAllFirestations();
        List<Person> personList = personDao.findAllPersons();
        for (Firestations firestation : firestationDao.finbAllFirestations()) {
            if (firestation.getStation() == stations) {
                for (Person person : personDao.findAllPersons()) {
                    if (person.getAddress().equals(firestation.getAddress())) {
                        for (MedicalRecords medicalRecord : medicalRecordDao.findAllMedicalrecords()){
                            if(person.getFirstName().equals(medicalRecord.getFirstName())&&person.getLastName().equals(medicalRecord.getLastName())){
                               person.setMedicalRecords(medicalRecord);
                               listPersonFlood.add(person);
                            }
                        }
                    }
                }
            }
        }
        return listPersonFlood;
    }

    //cette methode retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux
    @GetMapping(value = "/personInfo")
    public List<Person> getPersonMedicalRecords(@RequestParam(value ="firstName") String firstName, @RequestParam("lastName") String lastName){
        List<Person> recordsList = new ArrayList<>();
        for(MedicalRecords records : medicalRecordDao.findAllMedicalrecords()){
            if((records.getFirstName().equals(firstName)&&records.getLastName().equals(lastName))){
                for (Person person : personDao.findAllPersons()){
                    if((person.getFirstName().equals(records.getFirstName())&&person.getLastName().equals(records.getLastName()))){
                      person.setMedicalRecords(records);
                     recordsList.add(person);
                        return recordsList;
                    }
                }
            }
        }
        return null;
    }

    //cette methode retourne les Emails des habitants de la ville
    @GetMapping(value = "/communityEmail")
    public MappingJacksonValue listEmailByciti(@RequestParam("city") String city) throws IOException {

        List<Person> listEmail = new ArrayList<>();
        SimpleBeanPropertyFilter monfiltre = SimpleBeanPropertyFilter.serializeAllExcept("firstName","lastName","address","zip","phone","medicalRecords");
        FilterProvider myList = new SimpleFilterProvider().addFilter("filterPerson",monfiltre);
        for(Person person : personDao.findAllPersons()){
            if(person.getCity().equals(city)){
                listEmail.add(person);
            }
        }
        MappingJacksonValue emailFilter = new MappingJacksonValue(listEmail);
        emailFilter.setFilters(myList);
        return emailFilter;
    }
}
