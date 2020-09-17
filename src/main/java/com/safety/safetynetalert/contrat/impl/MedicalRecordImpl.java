package com.safety.safetynetalert.contrat.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.safetynetalert.contrat.dao.MedicalRecordDao;
import com.safety.safetynetalert.model.DB;
import com.safety.safetynetalert.model.MedicalRecords;
import com.safety.safetynetalert.model.Person;
import com.safety.safetynetalert.model.PersonMedicalRecords;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalRecordImpl implements MedicalRecordDao {


    public static List<MedicalRecords>medicalRecords = new ArrayList<>();

    static {
        File file = new File("safetynetalert.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
          DB db = mapper.readValue(file,DB.class);
          db.getMedicalrecords();
            medicalRecords.addAll(db.getMedicalrecords());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public MedicalRecords findMedicalRecordByFistAndLastName(String firstName, String lastName) {
        for (MedicalRecords medicalRecord : medicalRecords)
            if(medicalRecord.getFirstName().equals(firstName)&& medicalRecord.getLastName().equals(lastName)){

                return medicalRecord;
            }
        return null;
    }

    @Override
    public List<MedicalRecords> findAllMedicalrecords() {
        return medicalRecords;
    }

    @Override
    public MedicalRecords updateInfoMedicalRecord(String firstName, String lasName, MedicalRecords medicalInfo) {
       MedicalRecords medicalRecordUpdate = findMedicalRecordByFistAndLastName(firstName,lasName);
        if(medicalRecordUpdate!= null){
            medicalRecordUpdate.setBirthdate(medicalInfo.getBirthdate());
            medicalRecordUpdate.setMedications(medicalInfo.getMedications());
            medicalRecordUpdate.setAllergies(medicalInfo.getAllergies());

            return medicalRecordUpdate;
        }
        return null;
    }

    @Override
    public void deleteMedicalRecordAnPerson(String firstName, String lastName) {
       MedicalRecords medicalRecordDelete = findMedicalRecordByFistAndLastName(firstName,lastName);
       medicalRecords.remove(medicalRecordDelete);
    }

    @Override
    public MedicalRecords save(MedicalRecords medicalRecord) {
        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    @Override
    public List<PersonMedicalRecords> getMedicalRecord() {


        return null;

    }
}
