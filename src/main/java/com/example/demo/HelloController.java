package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onRunBtn(ActionEvent event) {
        try {
            welcomeText.setText("=== start run ===");
            String[] cmdline = new String[] { "cmd", "/c", "start", "start.bat" };
            Process process = Runtime.getRuntime().exec(cmdline);
            process.waitFor();
        } catch (Exception e) {
            welcomeText.setText("error : " +e.getMessage());
        }
    }
    @FXML
    public void onServerConfig() {
        welcomeText.setText("=== server config ===");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("server-config.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 630, 500);
            Stage stage = new Stage();
            stage.setTitle("server-config");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}