package com.edc.stormbreaker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Core implements Initializable {

    @FXML
    public TextField Path, delimiter;

    @FXML
    public Button Start;

    @FXML
    public CheckBox Close;

    @FXML
    public ProgressBar Progress;

    @FXML
    public ToggleButton Windows,Unix;

    @FXML
    public ChoiceBox choicebox;

    @FXML
    AnchorPane ap;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ap.setOnMousePressed(e->{
            xOffset=StormbreakerApplication.getStage().getX()-e.getSceneX();
            yOffset=StormbreakerApplication.getStage().getY()-e.getSceneY();

        });

        ap.setOnMouseDragged(e->{
            StormbreakerApplication.getStage().setX(e.getSceneX()+xOffset);
            StormbreakerApplication.getStage().setY(e.getSceneY()+yOffset);
        });

        Close.fire();

        ObservableList<String> observableList = FXCollections.observableArrayList(
                "cp1250"
                ,"ASCII"
                ,"UTF8"
                ,"UTF-16"
        );

        choicebox.setItems(observableList);
        choicebox.setValue(observableList.get(0));

        Unix.setStyle("-fx-background-color: #828282");


    }

    @FXML
    private void draggingOver(DragEvent event){
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }

    }

    @FXML
    public void windowsClicked(){
        Windows.setSelected(true);
        Unix.setSelected(false);
        Unix.setStyle("-fx-background-color: #828282");
        Windows.setStyle("-fx-background-color: #427df4");


    }

    @FXML
    public void unixClicked(){
        Unix.setSelected(true);
        Windows.setSelected(false);
        Unix.setStyle("-fx-background-color: #427df4");
        Windows.setStyle("-fx-background-color: #828282");

    }

    @FXML
    private void start(){
        Progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);



        if(Utils.validfile(Path.getText())){
            System.out.println("ok");
            String lowerfilename = Path.getText().toLowerCase();
            if(lowerfilename.endsWith("csv") || lowerfilename.endsWith("txt")){

                CSVReading csvReading = new CSVReading();
                csvReading.saveGui(Path.getText(),delimiter.getText(),getEndofline(),choicebox.getValue().toString(),this);
            }
            else {
                ExcelReading excelReading = new ExcelReading();
                excelReading.saveGui(Path.getText(),delimiter.getText(),getEndofline(),choicebox.getValue().toString(),this);

            }

            if (Close.isSelected()){
                System.exit(0);
            }
            else{
                Path.setText("");
            }
        }
        else{
            showWarningWindow("Uwaga","Plik nie spełnia kryterium do przetworznia",Path.getText());
        }

        Progress.setProgress(0.0);





    }

    public String getEndofline(){
        if(Windows.isSelected()){
            return "\r\n";
        }
        else
            return "\n";
    }

    @FXML
    private void dropping(DragEvent event){

        Dragboard dragboard = event.getDragboard();
        List<File> list = dragboard.getFiles();
        Path.setText(list.get(0).getAbsolutePath());

    }

    @FXML
    public void minimalize(){
        StormbreakerApplication.getStage().setIconified(true);
    }

    @FXML
    public void close(){
        System.exit(0);
    }
    public void showInformationWindow(String title,String header,String context){
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle(title);
        alert1.setHeaderText(header);
        alert1.setContentText(context);
        alert1.showAndWait();

    }

    public void showWarningWindow(String title,String header,String context){
        Alert alert1 = new Alert(Alert.AlertType.WARNING);
        alert1.setTitle(title);
        alert1.setHeaderText(header);
        alert1.setContentText(context);
        alert1.showAndWait();

    }

}

