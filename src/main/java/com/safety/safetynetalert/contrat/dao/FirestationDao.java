package com.safety.safetynetalert.contrat.dao;

import com.safety.safetynetalert.model.Firestations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FirestationDao {

        Firestations findStationByAdress(String address);

       Firestations findStationById(int station);

        void deleteStation(String address);

        Firestations modifyNumberOfAStation(String address, Firestations firestationsInfo);

        List<Firestations> finbAllFirestations();


        Firestations save(Firestations firestation);
}
