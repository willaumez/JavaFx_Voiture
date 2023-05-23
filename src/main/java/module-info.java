module com.voiture.gestion_voitures {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires mysql.connector.java;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires poi.ooxml;
    requires poi;
    requires com.google.gson;

    opens com.voiture.gestion_voitures to javafx.fxml;
    exports com.voiture.gestion_voitures;
    exports com.voiture.gestion_voitures.Controllers;
    exports com.voiture.gestion_voitures.Controllers.Admin;
    exports com.voiture.gestion_voitures.Controllers.User;
    exports com.voiture.gestion_voitures.Models;
    exports com.voiture.gestion_voitures.Views;
    exports com.voiture.gestion_voitures.Dao;


    opens com.voiture.gestion_voitures.Models;
    opens com.voiture.gestion_voitures.Controllers.Admin to javafx.fxml;
    opens com.voiture.gestion_voitures.Controllers.User to javafx.fxml;


}