package com.example.colocviu;

import Domain.Reteta;
import Repository.RepositoryException;
import Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class HelloController {
    private Service service;

    @FXML
    private Label welcomeText;

    public Label labelNume;
    public TextField textFieldNume;
    public Label labelMinute;
    public TextField textFieldMinute;
    public Label labelIngrediente;
    public TextField textFieldIngrediente;


    @FXML
    private ListView<String> retetaListView;
    @FXML
    private TextField textField;

    public void setService(Service service) {
        this.service = service;
        loadRetete();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to Iustinache`s Kitchen!");
    }

    @FXML
    private void loadRetete() {
        retetaListView.getItems().clear();
        List<Reteta> retete = service.getReteteDisponibile();
        for (Reteta r : retete) {
            String text = r.toString();
            retetaListView.getItems().add(text);
        }
    }

    public void onAddButtonClick(ActionEvent actionEvent) {

        try {
            var nume = textFieldNume.getText();
            var minute = Integer.parseInt(textFieldMinute.getText());
            var ingrediente = textFieldIngrediente.getText();
            try{
                service.addReteta(nume, minute, ingrediente);
                new Alert(Alert.AlertType.INFORMATION, "Reteta a fost adăugat cu succes!", ButtonType.OK).show();
            } catch (IllegalArgumentException e) {
                showAlert("Eroare", e.getMessage());
            }

            try {
                loadRetete();
            } catch (Exception e) {
                showAlert("Eroare", e.getMessage());
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        } catch (RepositoryException e) {
            Alert hopa = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            hopa.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            textFieldNume.clear();
            textFieldMinute.clear();
            textFieldIngrediente.clear();
        }
    }

    @FXML
    private void onFilterButtonClick() {
        try {
            String givenText = textField.getText();

            retetaListView.getItems().clear();
            List<Reteta> retete = service.filtreazaRetete(givenText);
            for (Reteta r : retete) {
                String text = r.toString();
                retetaListView.getItems().add(text);
            }

        } catch (RuntimeException e) {
            showAlert("Eroare", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



}