package com.voiture.gestion_voitures.Dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.voiture.gestion_voitures.Models.UserRole;
import com.voiture.gestion_voitures.Models.Users;
import com.voiture.gestion_voitures.Models.Voiture;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.nio.file.Files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class VoitureDaoImpl implements VoitureDao {

    private Connection connection = DataBaseDrivers.getConnection();


    @Override
    public Users getUser(String email) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Users user = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM Users WHERE email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new Users();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserRole.valueOf(rs.getString("role")));
            }

            return user;
        } catch (SQLException e) {
            System.err.println("Problème de requête pour récupérer un utilisateur");
            return null;
        } finally {
            DataBaseDrivers.closeResultSet(rs);
            DataBaseDrivers.closeStatement(ps);
        }
    }


    @Override
    public void insert(Voiture voiture) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO Voiture (marque, modele, matriculation, annee, prix, consommation, puissance ) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, voiture.getMarque());
            ps.setString(2, voiture.getModele());
            ps.setString(3, voiture.getMatriculation());
            ps.setInt(4, voiture.getAnnee());
            ps.setDouble(5, voiture.getPrix());
            ps.setDouble(6, voiture.getConsommation());
            ps.setInt(7, voiture.getPuissance());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    voiture.setId(id);
                }
                DataBaseDrivers.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'une voiture");
            ;
        } finally {
            DataBaseDrivers.closeStatement(ps);
        }

    }

    @Override
    public void update(Voiture voiture) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("UPDATE Voiture SET marque = ?, modele = ?, matriculation = ?, annee = ?, prix = ?, consommation = ?, puissance = ? WHERE id = ?");

            ps.setString(1, voiture.getMarque());
            ps.setString(2, voiture.getModele());
            ps.setString(3, voiture.getMatriculation());
            ps.setInt(4, voiture.getAnnee());
            ps.setDouble(5, voiture.getPrix());
            ps.setDouble(6, voiture.getConsommation());
            ps.setInt(7, voiture.getPuissance());
            ps.setInt(8, voiture.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'une voiture");
        } finally {
            DataBaseDrivers.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("DELETE FROM Voiture WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'une voiture");
        } finally {
            DataBaseDrivers.closeStatement(ps);
        }
    }

    @Override
    public Voiture findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM Voiture WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Voiture voiture = new Voiture();

                voiture.setId(rs.getInt("id"));
                voiture.setMarque(rs.getString("marque"));
                voiture.setModele(rs.getString("modele"));
                voiture.setMatriculation(rs.getString("matriculation"));
                voiture.setAnnee(rs.getInt("annee"));
                voiture.setPrix(rs.getDouble("prix"));
                voiture.setConsommation(rs.getDouble("consommation"));
                voiture.setPuissance(rs.getInt("puissance"));

                return voiture;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver une voiture");
            return null;
        } finally {
            DataBaseDrivers.closeResultSet(rs);
            DataBaseDrivers.closeStatement(ps);
        }
    }


    @Override
    public List<Voiture> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement("SELECT * FROM Voiture");
            rs = ps.executeQuery();

            List<Voiture> listVoiture = new ArrayList<>();

            while (rs.next()) {
                Voiture voiture = new Voiture();

                voiture.setId(rs.getInt("id"));
                voiture.setMarque(rs.getString("marque"));
                voiture.setModele(rs.getString("modele"));
                voiture.setMatriculation(rs.getString("matriculation"));
                voiture.setAnnee(rs.getInt("annee"));
                voiture.setPrix(rs.getDouble("prix"));
                voiture.setConsommation(rs.getDouble("consommation"));
                voiture.setPuissance(rs.getInt("puissance"));

                listVoiture.add(voiture);
            }

            return listVoiture;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner une voiture");
            ;
            return null;
        } finally {
            DataBaseDrivers.closeResultSet(rs);
            DataBaseDrivers.closeStatement(ps);
        }
    }


    //Read Files
    public List<Voiture> readExcelFile() {
        List<Voiture> voitures = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File("src/main/resources/Files/dataExcelFile.xls"))) {
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = spreadsheet.iterator();

            // Skip the first row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Voiture voiture = new Voiture(); // create a new Voiture object for each row
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    voiture.setId(cell.getRowIndex());

                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            voiture.setMarque(cell.getStringCellValue());
                            break;
                        case 1:
                            voiture.setModele(cell.getStringCellValue());
                            break;
                        case 2:
                            voiture.setMatriculation(cell.getStringCellValue());
                            break;
                        case 3:
                            if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                voiture.setAnnee((int) cell.getNumericCellValue());
                            } else {
                                // handle error, e.g. throw an exception or set a default value
                            }
                            break;
                        case 4:
                            if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                voiture.setPrix(cell.getNumericCellValue());
                            } else {
                                // handle error
                            }
                            break;
                        case 5:
                            if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                voiture.setConsommation(cell.getNumericCellValue());
                            } else {
                                // handle error
                            }
                            break;
                        case 6:
                            if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                voiture.setPuissance((int) cell.getNumericCellValue());
                            } else {
                                // handle error
                            }
                            break;
                        case 7:
                            if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                voiture.setId((int) cell.getNumericCellValue());
                            } else {
                                // handle error
                            }
                            break;
                        // add more cases for each column in the Excel file
                        default:
                            // do nothing
                            break;
                    }
                }
                voitures.add(voiture); // add the Voiture object to the List
            }
        } catch (FileNotFoundException e) {
            System.out.println("---FileNotFoundException---" + e);
            // TODO: handle exception
        } catch (IOException e) {
            System.out.println("---IOException---" + e);
            // TODO: handle exception
        }
        return voitures;
    }


    public List<Voiture> readJsonFile() {
        List<Voiture> voitures = null;
        try (FileReader reader = new FileReader("src/main/resources/Files/dataJsonFile.json")) {
            Gson gson = new Gson();
            Type voitureListType = new TypeToken<List<Voiture>>() {
            }.getType();
            voitures = gson.fromJson(reader, voitureListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voitures;
    }

    @Override
    public String readTxtFile() {
        try {
            Path filePath = Paths.get("src/main/resources/Files/dataTxtFile.txt");
            return Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Retourner une valeur par défaut en cas d'erreur de lecture du fichier
        }
    }


    //save Files
    @Override
    public void json_save(String json) {
        try (FileWriter fileWriter = new FileWriter("src/main/resources/Files/dataJsonFile.json")) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void txt_save(String txt) {
        try (PrintWriter writer = new PrintWriter("src/main/resources/Files/dataTxtFile.txt")) {
            writer.write(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateExcel(String newValue, int idLigne, int idColonne) {
        try (FileInputStream file = new FileInputStream("src/main/resources/Files/dataExcelFile.xls")) {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0); // Supposons que la voiture est enregistrée dans la première feuille du classeur

            // Parcourir les lignes pour trouver la voiture à mettre à jour
            for (Row row : sheet) {
                if (row.getRowNum() == idLigne) { // Ligne
                    Cell element = row.getCell(idColonne); // Colonne
                    if (element != null && element.getColumnIndex() == idColonne) {
                        switch (idColonne) {
                            case 3, 6, 7:
                                element.setCellValue(Integer.parseInt(newValue));
                                break;
                            case 4, 5:
                                element.setCellValue(Double.parseDouble(newValue));
                                break;
                            default:
                                if (element.getCellType() == CellType.NUMERIC || element.getCellType() == CellType.FORMULA) {
                                    element.setCellValue(Double.parseDouble(newValue));
                                } else {
                                    element.setCellValue(newValue);
                                }
                                break;
                        }
                    }

                }
            }

            try (FileOutputStream outFile = new FileOutputStream("src/main/resources/Files/dataExcelFile.xls")) {
                workbook.write(outFile);
            }

            System.out.println("Voiture mise à jour avec succès dans le fichier Excel.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveExcel(Voiture voiture) {
        try {
            FileInputStream file = new FileInputStream(new File("src/main/resources/Files/dataExcelFile.xls"));
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0); // Supposons que la voiture sera enregistrée dans la première feuille du classeur

            // Obtenir le numéro de la dernière ligne utilisée
            int lastUsedRow = sheet.getLastRowNum();

            // Créer une nouvelle ligne pour la nouvelle voiture
            Row newRow = sheet.createRow(lastUsedRow + 1);

            // Définir les valeurs des cellules de la nouvelle ligne
            Cell marqueCell = newRow.createCell(0); // Colonne MARQUE
            marqueCell.setCellValue(voiture.getMarque());

            Cell modeleCell = newRow.createCell(1); // Colonne MODEL
            modeleCell.setCellValue(voiture.getModele());

            Cell matriculationCell = newRow.createCell(2); // Colonne MATRICULE
            matriculationCell.setCellValue(voiture.getMatriculation());

            Cell anneeCell = newRow.createCell(3); // Colonne ANNEE
            anneeCell.setCellValue(voiture.getAnnee());

            Cell prixCell = newRow.createCell(4); // Colonne PRIX
            prixCell.setCellValue(voiture.getPrix());

            Cell consommationCell = newRow.createCell(5); // Colonne CONSOMMATION
            consommationCell.setCellValue(voiture.getConsommation());

            Cell puissanceCell = newRow.createCell(6); // Colonne PRUISSANCE
            puissanceCell.setCellValue(voiture.getPuissance());

            Cell idCell = newRow.createCell(7); // Colonne PRUISSANCE
            Integer id = voiture.getId();
            int voitureId = (id != null) ? id.intValue() : 0;
            idCell.setCellValue(voitureId);

            FileOutputStream outFile = new FileOutputStream(new File("src/main/resources/Files/dataExcelFile.xls"));
            workbook.write(outFile);
            outFile.close();

            System.out.println("Voiture enregistrée avec succès dans le fichier Excel.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Remove Into File
    @Override
    public void deleteIntoExcel(Voiture voiture) {
        List<Voiture> voitureList = readExcelFile(); // Lire les données actuelles du fichier Excel

        System.out.println("delete===============" + voiture);
        // Rechercher la voiture à supprimer dans la liste
        for (Iterator<Voiture> iterator = voitureList.iterator(); iterator.hasNext(); ) {
            Voiture currentVoiture = iterator.next();

            if (currentVoiture.equals(voiture)) {
                System.out.println("delete====currentVoiture==" + currentVoiture);
                iterator.remove(); // Supprimer la voiture de la liste
                break;
            }
        }

        // Écrire la liste mise à jour dans le fichier Excel
        writeDataToFileExcel(voitureList);
    }

    public static void writeDataToFileExcel(List<Voiture> voitureList) {
        // Création d'un objet de type fichier Excel
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Création d'un objet de type feuille Excel
        XSSFSheet spreadsheet = workbook.createSheet("VoitureInfos");

        // En-têtes
        String[] headers = {"MARQUE", "MODEL", "MATRICULE", "ANNEE", "PRIX", "CONSOMMATION", "PRUISSANCE", "ID"};

        // Créer la première ligne pour les en-têtes
        XSSFRow headerRow = spreadsheet.createRow(0);

        // Remplir les cellules avec les en-têtes
        for (int i = 0; i < headers.length; i++) {
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headers[i]);
        }

        // Les données à insérer
        AtomicInteger rowNum = new AtomicInteger(1);

        voitureList.forEach(voiture -> {
            XSSFRow row = spreadsheet.createRow(rowNum.getAndIncrement());

            // Créer les cellules et définir les valeurs
            Cell marqueCell = row.createCell(0);
            marqueCell.setCellValue(voiture.getMarque());

            Cell modeleCell = row.createCell(1);
            modeleCell.setCellValue(voiture.getModele());

            Cell matriculationCell = row.createCell(2);
            matriculationCell.setCellValue(voiture.getMatriculation());

            Cell anneeCell = row.createCell(3);
            anneeCell.setCellValue(voiture.getAnnee());

            Cell prixCell = row.createCell(4);
            prixCell.setCellValue(voiture.getPrix());

            Cell consommationCell = row.createCell(5);
            consommationCell.setCellValue(voiture.getConsommation());

            Cell puissanceCell = row.createCell(6);
            puissanceCell.setCellValue(voiture.getPuissance());

            Cell idCell = row.createCell(7);
            idCell.setCellValue(voiture.getId());
        });

        try {
            // Écrire les données dans le fichier Excel
            FileOutputStream out = new FileOutputStream(new File("src/main/resources/Files/dataExcelFile.xls"));
            workbook.write(out);
            out.close();
            System.out.println("Travail bien fait!!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //import - export
    @Override
    public void dataToExcel() {
        List<Voiture> voitures = findAll();
        writeDataToFileExcel(voitures);
    }

    @Override
    public void dataFromExcel(List<Voiture> voitures) {
        List<Voiture> existingVoitures = findAll();

        for (Voiture voiture : voitures) {
            boolean existsInDB = existingVoitures.stream()
                    .anyMatch(existingVoiture -> existingVoiture.getId().equals(voiture.getId()));

            if (!existsInDB) {
                insert(voiture);
            }
        }

        for (Voiture existingVoiture : existingVoitures) {
            boolean existsInList = voitures.stream()
                    .anyMatch(voiture -> existingVoiture.getId().equals(voiture.getId()));
            if (!existsInList) {
                deleteById(existingVoiture.getId());
            }
        }
        System.out.println("Données enregistrées");
    }

    @Override
    public void dataToJson() {
        List<Voiture> voitures = findAll();

        // Créez un objet Gson pour la conversion JSON
        Gson gson = new Gson();

        try {
            // Convertir la liste des voitures en JSON
            String jsonVoitures = gson.toJson(voitures);

            // Écrire le JSON dans un fichier
            FileWriter fichierJson = new FileWriter("src/main/resources/Files/dataJsonFile.json");
            fichierJson.write(jsonVoitures);
            fichierJson.close();

            System.out.println("Données enregistrées dans le fichier JSON avec succès !");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier JSON : " + e.getMessage());
        }
    }

    @Override
    public void dataFromJson() {
        List<Voiture> voitures = readJsonFile();
        dataFromExcel(voitures);
    }

    @Override
    public void dataToTxt() {
        List<Voiture> voitures = findAll();
        StringBuilder content = new StringBuilder();

        for (Voiture voiture : voitures) {
            content.append(voiture.getId()).append(",");
            content.append(voiture.getMarque()).append(",");
            content.append(voiture.getModele()).append(",");
            content.append(voiture.getMatriculation()).append(",");
            content.append(voiture.getAnnee()).append(",");
            content.append(voiture.getPrix()).append(",");
            content.append(voiture.getConsommation()).append(",");
            content.append(voiture.getPuissance()).append("\n");
        }
        System.out.println("dataToTxt---" + content);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/Files/dataTxtFile.txt"))) {
            writer.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dataFromTxt(String[] txt) {
        List<Voiture> voitureList = new ArrayList<>();
        System.out.println("Line---" + txt);
        for (String line : txt) {
            String[] values = line.split(",");

            Voiture voiture = new Voiture();
            voiture.setId(Integer.parseInt(values[0].trim()));
            voiture.setMarque(values[1].trim());
            voiture.setModele(values[2].trim());
            voiture.setMatriculation(values[3].trim());
            voiture.setAnnee(Integer.parseInt(values[4].trim()));
            voiture.setPrix(Double.parseDouble(values[5].trim()));
            voiture.setConsommation(Double.parseDouble(values[6].trim()));
            voiture.setPuissance(Integer.parseInt(values[7].trim()));

            voitureList.add(voiture);
        }
        dataFromExcel(voitureList);
    }




}
