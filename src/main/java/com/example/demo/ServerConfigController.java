package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;

public class ServerConfigController {

    @FXML
    private TextField envCmdPath;
    @FXML
    private TextField wlstCmdPath;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField url;
    @FXML
    private Button saveBtn;
    @FXML
    private Label info;

    public void onSave() throws Exception {

        String envCmdPath = this.envCmdPath.getText();
        String wlstCmdPath = this.wlstCmdPath.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        String url = this.url.getText();

        String serverJson = readFile("/jdbc/server.json");
        serverJson = replaceServerJsonSrc(serverJson, "user", username);
        serverJson = replaceServerJsonSrc(serverJson, "password", password);
        serverJson = replaceServerJsonSrc(serverJson, "url", url);
        writeFile(serverJson, "bin/server.json");

        String jdbcBat = readFile("/jdbc/jdbc.bat");
        jdbcBat = replaceBatSrc(jdbcBat, "env", envCmdPath);
        jdbcBat = replaceBatSrc(jdbcBat, "wlst", wlstCmdPath);
        writeFile(jdbcBat, "bin/jdbc.bat");

        Stage stage = (Stage)saveBtn.getScene().getWindow();
        stage.close();
    }

    private String readFile(String filePath) throws Exception {
        try {
            FileReader fileReader = new FileReader(filePath.replaceFirst("/jdbc", "bin"));

            int ch;
            StringBuffer str = new StringBuffer();
            while((ch = fileReader.read())!=-1 ) {
                str.append((char)ch);
            }
            fileReader.close();
            return str.toString();
        } catch (Exception e) {
            URL resource = this.getClass().getResource(filePath);
            InputStream inputStream = resource.openStream();
            int ch;
            StringBuffer str = new StringBuffer();
            while((ch = inputStream.read())!=-1 ) {
                str.append((char)ch);
            }
            inputStream.close();
            return str.toString();
        }
    }

    private void writeFile(String value ,String filePath) throws Exception {
        File file = new File(filePath);
       if (!file.exists()) {
           file.createNewFile();
       }
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(value);
        fileWriter.close();
    }

    private String replaceServerJsonSrc(String originValue, String key, String value) {
        String user = originValue.split(",")[0].split(":")[1];
        String pwd = originValue.split(",")[1].split(":")[1];
        String url = originValue.split(",")[2].split(":",2)[1].replace("}", "");

        switch (key) {
            case "user":
                if (null != value && value.length() > 0) {;
                    return originValue.replace(user, "\"" + value + "\"");
                }
                return originValue;
            case "password":
                if (null != value && value.length() > 0) {
                    return originValue.replace(pwd, "\"" + value + "\"");
                }
                return originValue;
            case "url":
                if (null != value && value.length() > 0) {
                    return originValue.replace(url, "\"" + value + "\"" + "\n");
                }
                return originValue;
            default:
                return originValue;
        }
    }

    private String replaceBatSrc(String originValue, String key, String value) {
        String env = originValue.split("EnvCmdConfig")[1].split("rem")[0].split("=")[1];
        String wlst = originValue.split("WlstCmdConfig")[1].split("rem weblogic doCmd")[0].split("=")[1];

        switch (key) {
            case "env":
                if (null != value && value.length() > 0) {;
                    return originValue.replace(env, "\"" + value + "\""+ "\n");
                }
                return originValue;
            case "wlst":
                if (null != value && value.length() > 0) {
                    return originValue.replace(wlst, "\"" + value + "\""+ "\n");
                }
                return originValue;
            default:
                return originValue;
        }
    }
}
