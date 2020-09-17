package com.safety.safetynetalert.model;

import java.util.List;

public class DB {

    private List<Person> persons;
    private List<Firestations> firestations;
    private List<MedicalRecords> medicalrecords;
    private List<PersonMedicalRecords> personMedicalRecords;

    public List<PersonMedicalRecords> getPersonMedicalRecords() {
        return personMedicalRecords;
    }

    public void setPersonMedicalRecords(List<PersonMedicalRecords> personMedicalRecords) {
        this.personMedicalRecords = personMedicalRecords;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Firestations> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestations> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecords> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecords> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }
}
