package com.voiture.gestion_voitures.Dao;

import com.voiture.gestion_voitures.Models.Users;
import com.voiture.gestion_voitures.Models.Voiture;

import java.io.IOException;
import java.util.List;

public interface VoitureDao {

    //login
    Users getUser(String email);



    void insert(Voiture voiture);
    void update(Voiture voiture);

    void deleteById(Integer id);

    Voiture findById(Integer id);

    List<Voiture> findAll();

    //read Files
    List<Voiture> readExcelFile();
    List<Voiture> readJsonFile();
    String readTxtFile();


    //save File
    void json_save(String json);

    void txt_save(String txt);
    void updateExcel(String newValue, int idLigne, int idColonne);
    void saveExcel(Voiture voiture);

    //remove file
    void deleteIntoExcel(Voiture voiture);

    //import - export
    void dataToExcel();
    void dataFromExcel(List<Voiture> voitures);

    void dataToJson();
    void dataFromJson();
    void dataToTxt();
    void dataFromTxt(String[] txt);
}
