package com.example.colocviu;

import Domain.Reteta;
import Repository.IRepository;
import Repository.SQLRetetaRepository;
import Repository.RepoMemory;
import Service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        IRepository<Reteta> repo = new SQLRetetaRepository();
        Service service = new Service(repo);

        // Setează service-ul în controller
        HelloController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.setTitle("Aplicație Reteta");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}