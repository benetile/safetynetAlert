package com.safety.safetynetalert.contrat.dao;

import com.safety.safetynetalert.model.MedicalRecords;
import com.safety.safetynetalert.model.PersonMedicalRecords;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordDao {

    MedicalRecords findMedicalRecordByFistAndLastName(String firstName, String lastName);

    List<MedicalRecords> findAllMedicalrecords();

    MedicalRecords updateInfoMedicalRecord(String firstName, String lasName, MedicalRecords medicalInfo);

    void deleteMedicalRecordAnPerson(String firstName, String lastName);

    MedicalRecords save(MedicalRecords medicalRecord);

    List<PersonMedicalRecords> getMedicalRecord();
}
