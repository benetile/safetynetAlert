package com.safety.safetynetalert.controller;

import com.safety.safetynetalert.contrat.dao.FirestationDao;
import com.safety.safetynetalert.contrat.dao.MedicalRecordDao;
import com.safety.safetynetalert.contrat.dao.PersonDao;
import com.safety.safetynetalert.model.Firestations;
import com.safety.safetynetalert.model.MedicalRecords;
import com.safety.safetynetalert.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class Controller {


    @Autowired
    private PersonDao personDao;
    @Autowired
    private FirestationDao firestationDao;
    @Autowired
    private MedicalRecordDao medicalRecordDao;

    /*************** Partie Controller service Person *********************/
    //Service Person
    @GetMapping(value = "/person")
    public List<Person> displayAllPersons(Person person) throws IOException {
        return personDao.findAllPersons();
    }
    @PostMapping(value = "/person")
    public void addPersonToTheList(@RequestBody Person person){
        personDao.save(person);
    }

    @PutMapping(value = "/person/{firstName}/{lastName}")
    public Person modifyInfoPerson( @PathVariable String firstName,@PathVariable  String lastName, @RequestBody Person person ) {
            return personDao.updateInfoPerson(firstName,lastName,person);
    }

    @DeleteMapping("/person/{firstName}/{lastName}")
    public void deletePersonToTheList(@PathVariable String firstName,@PathVariable String lastName)throws IOException{
       personDao.deleteInfoAPerson(firstName,lastName);
        System.out.print("Votre opération a été un succès ");
    }

    @GetMapping("/person/{firstName}/{lastName}")
    @ResponseBody
    public Person afficherUnePersonn(@PathVariable String firstName, @PathVariable String lastName) {
       Person person = personDao.findByFirstNameAndLastName(firstName,lastName);
        System.out.println(person);
        return person;
    }

    /*************** Partie Controller service Firestation *********************/

    //Service Firestation
    @GetMapping(value = "/firestation")
    public List<Firestations> displayAllFirestations() throws IOException{
        return firestationDao.finbAllFirestations();
    }

    @PostMapping(value = "/firestation")
    public void addFirestation(@RequestBody Firestations firestation){
        firestationDao.save(firestation);
    }


    @GetMapping(value = "/firestation/{station}")
    public List<Firestations> giveFirestationByStation(@PathVariable int station){

        return null;
        //return firestationDao.findStationByStation(station);
    }

    @PutMapping(value ="/firestation/{address}")
    public Firestations updateStation(@PathVariable String address, @RequestBody Firestations firestation){
        return firestationDao.modifyNumberOfAStation(address,firestation);
    }

    @DeleteMapping(value = "/firestation/{address}")
    public void deleteAStation(@PathVariable String address){
        firestationDao.deleteStation(address);
        System.out.println("la station a été supprimée avec succès");
    }

    /*************** Partie Controller service MedicalRecords *********************/
    //Service MedicalRecords
    @GetMapping(value = "/medicalRecord")
    public List<MedicalRecords> displayMedicalrecords()throws IOException{
        return medicalRecordDao.findAllMedicalrecords();
    }
    @PostMapping(value = "/medicalRecord")
    public void addNewMedicalRecord(@RequestBody MedicalRecords medicalRecord){
        medicalRecordDao.save(medicalRecord);
    }

    @GetMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public MedicalRecords takeMedicalRecordAnPerson(@PathVariable String firstName, @PathVariable String lastName){
       MedicalRecords medical = medicalRecordDao.findMedicalRecordByFistAndLastName(firstName,lastName);
        return medical;
    }

    @PutMapping(value = "/medicalRecord/{firstName}/{lastName}")
    public MedicalRecords modiifyMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @RequestBody MedicalRecords medicalInfo){
        MedicalRecords medical = medicalRecordDao.updateInfoMedicalRecord(firstName,lastName,medicalInfo);
        return medical;
    }

    @DeleteMapping(value = "/medicalRecord/{firstName}/{lastName}")
   public void deleteAMedicalRecord(@PathVariable String firstName, @PathVariable String lastName){
        medicalRecordDao.deleteMedicalRecordAnPerson(firstName,lastName);
        System.out.println("Votre demande a été éxecuté avec succès ");

   }

}