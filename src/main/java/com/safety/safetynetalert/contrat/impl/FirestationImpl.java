package com.safety.safetynetalert.contrat.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.safetynetalert.contrat.dao.FirestationDao;
import com.safety.safetynetalert.model.DB;
import com.safety.safetynetalert.model.Firestations;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestationImpl implements FirestationDao {
    File file = new File("safetynetalert.json");
    public static List<Firestations> firestations = new ArrayList<>();
    DB db;

    static {
        File file = new File("safetynetalert.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
          DB db = mapper.readValue(file,DB.class);
          db.getFirestations();
          firestations.addAll(db.getFirestations());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Firestations findStationByAdress(String address) {
       for (Firestations adressFirestation : firestations) {
           if (adressFirestation.getAddress().equals(address)) {
               return adressFirestation;
           }
       }
        return null;
    }

    @Override
    public Firestations findStationById(int station) {
        for(Firestations fire : firestations) {
            if (fire.getStation() == station) {
                return fire;
            }
        }
        return null;
    }

    @Override
    public void deleteStation(String address) {
        Firestations firestationAddress = findStationByAdress(address);
        firestations.remove(firestationAddress);
    }

    @Override
    public Firestations modifyNumberOfAStation(String address, Firestations firestationsInfo) {
        Firestations firestationUpdate = findStationByAdress(address);
        if(firestationUpdate!=null){
            firestationUpdate.setStation(firestationsInfo.getStation());
            return firestationUpdate;
        }
        return null;
    }

    @Override
    public List<Firestations> finbAllFirestations() {
        return firestations;
    }

    @Override
    public  Firestations save(Firestations firestation) {
        firestations.add(firestation);
        return firestation;
    }
}
