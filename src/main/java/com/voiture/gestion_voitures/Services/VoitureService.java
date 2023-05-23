package com.voiture.gestion_voitures.Services;

import com.voiture.gestion_voitures.Dao.VoitureDao;
import com.voiture.gestion_voitures.Dao.VoitureDaoImpl;
import com.voiture.gestion_voitures.Models.Users;
import com.voiture.gestion_voitures.Models.Voiture;

import java.util.List;

public class VoitureService {
    //login
    public Users getUser(String email){
        return voitureDao.getUser(email);
    }

    private static VoitureDao voitureDao = new VoitureDaoImpl();

    public static List<Voiture> findAll() {
        return voitureDao.findAll();
    }

    public void save(Voiture voiture) {

        voitureDao.insert(voiture);

    }
    public void update(Voiture voiture) {

        voitureDao.update(voiture);

    }
    public void remove(Voiture voiture) {
        voitureDao.deleteById(voiture.getId());
    }

    //===================================== Insert========================================================//


    //===================================== Update========================================================//


    //===================================== Delete========================================================//



    // Read Files
    public static List<Voiture> readExcel() {
        return voitureDao.readExcelFile();
    }
    public static List<Voiture> readJson() {
        return voitureDao.readJsonFile();
    }
    public String readTxt(){
        return voitureDao.readTxtFile();
    }




    //Save File
    public void json_save(String json) {
        voitureDao.json_save(json);
    }
    public void txt_save(String txt) {
        voitureDao.txt_save(txt);
    }
    public void updateExcel(String newValue, int idLigne, int idColonne){
        voitureDao.updateExcel(newValue, idLigne, idColonne);
    }
    public void saveExcel(Voiture voiture){
        voitureDao.saveExcel(voiture);
    }
    public void deleteIntoExcel(Voiture voiture){
        voitureDao.deleteIntoExcel(voiture);
    }


    //import - export
    public void dataToExcel(){
        voitureDao.dataToExcel();
    }
    public void dataFromExcel(List<Voiture> voitures){
        voitureDao.dataFromExcel(voitures);
    }

    public void dataToJson(){
        voitureDao.dataToJson();
    }
    public void dataFromJson(){
        voitureDao.dataFromJson();
    }

    public void dataToTxt(){
        voitureDao.dataToTxt();
    }
    public void dataFromTxt(String[] txt){
        voitureDao.dataFromTxt(txt);
    }



}
