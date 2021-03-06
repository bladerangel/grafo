package controllers;


import com.jfoenix.controls.JFXDialog;
import javafx.embed.swing.SwingNode;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Event;
import models.State;
import services.CreateWindowService;
import views.LayoutGraph;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Rangel on 20/11/2016.
 */
public class ApplicationController implements Initializable {

    @FXML
    private BorderPane border;

    @FXML
    private Pane pane1;

    @FXML
    private Pane pane2;

    @FXML
    private Label xo1;

    @FXML
    private Label x1;

    @FXML
    private Label e1;

    @FXML
    private Label xm1;

    @FXML
    private Label xo2;

    @FXML
    private Label x2;

    @FXML
    private Label e2;

    @FXML
    private Label xm2;

    @FXML
    private RadioButton active1;

    @FXML
    private RadioButton active2;

    private SwingNode swingNode1;

    private SwingNode swingNode2;

    private LayoutGraph layoutGraph;

    private LayoutGraph layoutGraph1;

    private LayoutGraph layoutGraph2;

    private CreateWindowService createWindowService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        layoutGraph = new LayoutGraph();
        layoutGraph1 = new LayoutGraph();
        swingNode1 = new SwingNode();
        swingNode1.setOnMouseClicked(event -> click1());
        pane1.getChildren().add(swingNode1);
        active1.setSelected(true);
        layoutGraph = layoutGraph1;

        layoutGraph2 = new LayoutGraph();
        swingNode2 = new SwingNode();
        swingNode2.setOnMouseClicked(event -> click2());
        pane2.getChildren().add(swingNode2);
    }

    @FXML
    public void click1() {
        layoutGraph = layoutGraph1;
        active1.setSelected(true);
    }

    @FXML
    public void click2() {
        layoutGraph = layoutGraph2;
        active2.setSelected(true);
    }

    @FXML
    public void addState() throws IOException {
        newWindow("AddState", false);
    }

    @FXML
    public void addEvent() throws IOException {
        newWindow("AddEvent", false);
    }

    @FXML
    public void removeState() throws IOException {
        newWindow("RemoveState", false);
    }

    @FXML
    public void removeEvent() throws IOException {
        newWindow("RemoveEvent", false);
    }

    @FXML
    public void table() throws IOException {
        newWindow("TableView", false);
    }

    @FXML
    public void operations() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("What type of automatons operations do you want to perform?");
        alert.setContentText("Choose your option!");
        ButtonType buttonTypeNormal = new ButtonType("Normal");
        ButtonType buttonTypeComposition = new ButtonType("Composition");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeNormal, buttonTypeComposition, buttonTypeCancel);
        alert.setOnCloseRequest(event -> alert.close());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeNormal) {
            newWindow("Operations", false);
        } else if (result.get() == buttonTypeComposition) {
            newWindow("CompositionOperations", true);
        }
    }

    public void setLabels() {
        if (active1.isSelected()) {
            setXo(xo1);
            setX(x1);
            setXm(xm1);
            setE(e1);
        } else {
            setXo(xo2);
            setX(x2);
            setXm(xm2);
            setE(e2);
        }
    }

    @FXML
    public void newProject() {

        layoutGraph.removeAllGraph();
        setLabels();
        createAndSetSwingContent();
    }

    @FXML
    public void testCase() {

        layoutGraph.removeAllGraph();
        State state0 = new State("0");
        state0.setStart(true);
        State state1 = new State("1");
        State state2 = new State("2");
        state2.setMarked(true);
        State state3 = new State("3");
        State state4 = new State("4");
        State state5 = new State("5");
        State state6 = new State("6");
        layoutGraph.addState(state0);
        layoutGraph.addState(state1);
        layoutGraph.addState(state2);
        layoutGraph.addState(state3);
        layoutGraph.addState(state4);
        layoutGraph.addState(state5);
        layoutGraph.addState(state6);

        layoutGraph.addEvent(new Event("g", state1, state5), state1, state5);
        layoutGraph.addEvent(new Event("a", state6, state3), state6, state3);
        layoutGraph.addEvent(new Event("g", state2, state0), state2, state0);
        layoutGraph.addEvent(new Event("a", state1, state3), state1, state3);
        layoutGraph.addEvent(new Event("b", state3, state4), state3, state4);
        layoutGraph.addEvent(new Event("a", state0, state1), state0, state1);
        layoutGraph.addEvent(new Event("b", state1, state2), state1, state2);
        layoutGraph.addEvent(new Event("b", state6, state2), state6, state2);
        layoutGraph.addEvent(new Event("a", state4, state3), state4, state3);

        setLabels();
        createAndSetSwingContent();
    }

    @FXML
    public void importFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Import File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            Stage stage = (Stage) border.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                layoutGraph.removeAllGraph();
                FileReader file = new FileReader(selectedFile);
                BufferedReader bufferedReader = new BufferedReader(file);
                String line = bufferedReader.readLine();

                String states[] = line.split(",");
                for (String stateName : states) {
                    String attributes[] = stateName.split("!");
                    State state = new State(attributes[0]);
                    if (attributes.length > 1) {
                        if (attributes[1].equals("start")) {
                            state.setStart(true);
                        } else if (attributes[1].equals("marked")) {
                            state.setMarked(true);
                        }

                    }
                    if (attributes.length > 2 && attributes[2].equals("marked"))
                        state.setMarked(true);
                    addStateGraph(state);

                }
                line = bufferedReader.readLine();
                if (line != null) {
                    String events[] = line.split("],");
                    for (String event : events) {
                        String eventName = event.substring(0, event.indexOf("[") - 1);
                        String state1 = event.substring(event.indexOf("[") + 1, event.indexOf(","));
                        String state2 = event.substring(event.indexOf(",") + 2);
                        Event eventSave = new Event(eventName, layoutGraph.findStateByName(state1), layoutGraph.findStateByName(state2));
                        addEventGraph(eventSave, eventSave.getStateInit(), eventSave.getStateFinal());

                    }
                    createAndSetSwingContent();
                    bufferedReader.close();
                    file.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exportFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            Stage stage = (Stage) border.getScene().getWindow();
            File save = fileChooser.showSaveDialog(stage);
            if (save != null) {
                FileWriter file = new FileWriter(save);
                BufferedWriter bufferedWriter = new BufferedWriter(file);
                for (State state : layoutGraph.getAllStates()) {
                    bufferedWriter.write(state.getName());
                    if (state.isStart()) {
                        bufferedWriter.write("!start");
                    }
                    if (state.isMarked()) {
                        bufferedWriter.write("!marked");
                    }
                    bufferedWriter.write(",");
                }

                bufferedWriter.newLine();
                for (Event event : layoutGraph.getAllEvents()) {
                    bufferedWriter.write(event.getLinkName() + " ");
                    bufferedWriter
                            .write(layoutGraph
                                    .getAllStatesByEvent(event)
                                    .stream()
                                    .map(State::getName)
                                    .collect(Collectors.toList())
                                    + ",");
                }
                bufferedWriter.close();
                file.close();
                alertMessage("File saved successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void about() {
        alertMessage("Este programa é destinada a manipulação de automatos.\n"
                + "Foi desenvolvido por Pedro Rangel.\n"
                + "Na disciplina de Sistemas Embarcados 2.");
    }

    public void createAndSetSwingContent() {
        if (active1.isSelected())
            swingNode1.setContent(layoutGraph.changeBasicVisualizationServer());
        else
            swingNode2.setContent(layoutGraph.changeBasicVisualizationServer());
    }

    public void append(Label label, String text) {
        label.setText(label.getText() + text);
    }

    public void clean(Label label) {
        label.setText("");
    }

    public void cleanFinal(Label label) {
        label.setText(label.getText().substring(0, label.getText().lastIndexOf(",")));
    }

    public void setXo(Label xo) {
        clean(xo);
        append(xo, "Xo=" + layoutGraph.getStateStart().getName());
    }

    public void setX(Label x) {
        clean(x);
        append(x, "X={");
        if (!layoutGraph.getAllStates().isEmpty()) {
            layoutGraph
                    .getAllStates()
                    .forEach(state -> append(x, state.getName() + ","));
            cleanFinal(x);
        }
        append(x, "}");
    }

    public void setE(Label e) {
        clean(e);
        append(e, "E={");
        if (!layoutGraph.getAllEvents().isEmpty()) {
            layoutGraph
                    .getAllEvents()
                    .stream()
                    .map(Event::getLinkName)
                    .distinct()
                    .forEach(eventName -> append(e, eventName + ","));
            cleanFinal(e);
        }
        append(e, "}");
    }

    public void setXm(Label xm) {
        clean(xm);
        append(xm, "Xm={");
        if (!layoutGraph.getAllStatesMarked().isEmpty()) {
            layoutGraph
                    .getAllStatesMarked()
                    .forEach(state -> append(xm, state.getName() + ","));
            if (!layoutGraph.getAllStatesMarked().isEmpty())
                cleanFinal(xm);
        }
        append(xm, "}");
    }

    public void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public void newWindow(String view, boolean layout) throws IOException {
        createWindowService = new CreateWindowService(view);
        createWindowService.setStage(new Stage());
        createWindowService.setBtnMin(false);
        createWindowService.setScene();
        if (!layout)
            createWindowService.setAbstractController(this, layoutGraph);
        else
            createWindowService.setAbstractController(this, layoutGraph1, layoutGraph2);
        createWindowService.show();
    }


    private boolean containsState(State state) {
        for (State s : layoutGraph.getAllStates()) {
            if (s.getName().equals(state.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean addStateGraph(State state) {
        if (!containsState(state)) {
            layoutGraph.addState(state);
            createAndSetSwingContent();
            setLabels();
            return true;
        }
        return false;
    }

    public boolean removeStateGraph(State state) {
        if (state != null) {
            layoutGraph.removeState(state);
            createAndSetSwingContent();
            setLabels();
            return true;
        }
        return false;
    }

    public boolean addEventGraph(Event event, State state1, State state2) {
        if (state1 != null && state2 != null) {
            layoutGraph.addEvent(event, state1, state2);
            createAndSetSwingContent();
            setLabels();
            return true;
        }
        return false;
    }

    public boolean removeEventGraph(Event event) {
        if (event != null) {
            layoutGraph.removeEvent(event);
            createAndSetSwingContent();
            setLabels();
            return true;
        }
        return false;
    }

}
