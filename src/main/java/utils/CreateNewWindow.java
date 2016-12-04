package utils;/**
 * Created by Rangel on 22/11/2016.
 */

import com.jfoenix.controls.JFXDecorator;
import controllers.AbstractController;
import controllers.ApplicationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewWindow {
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Scene scene;
    private JFXDecorator decorator;
    private AbstractController abstractController;
    private boolean btnMin;

    public CreateNewWindow(String view) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("/views/" + view + ".fxml"));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setBtnMin(boolean btnMin) {
        this.btnMin = btnMin;
    }

    public void setScene() throws IOException {
        decorator = new JFXDecorator(this.stage, fxmlLoader.load(), false, true, btnMin);
        scene = new Scene(decorator);
        scene.getStylesheets().add(getClass().getResource("/assets/stylesheets/decorator.css").toExternalForm());
        this.stage.setScene(scene);
        this.stage.setResizable(false);
    }

    public void setAbstractController(ApplicationController applicationController) {
        abstractController = fxmlLoader.getController();
        abstractController.init(applicationController);
    }

    public void show() {
        if (!btnMin) {
            decorator.setOnCloseButtonAction(() -> this.stage.close());
            this.stage.initModality(Modality.APPLICATION_MODAL);
        }
        this.stage.show();
    }

}
