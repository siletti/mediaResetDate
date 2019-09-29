package it.siletti.alberto;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;


public class App extends Application {

    private Text actionResult;
    private static final String titleTxt = " -  demo";

    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle(titleTxt);

        // Window label
        Label label = new Label("Change File: name and modified date ");
        label.setTextFill(Color.DARKBLUE);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, 18));
        HBox labelHb = new HBox();
        labelHb.setAlignment(Pos.CENTER);
        labelHb.getChildren().add(label);

        // Buttons
        Button btn1 = new Button(" jpg/mov files ");
        //Button btn2 = new Button(" ------ ");
        btn1.setOnAction(new ButtonListener1());
        // btn2.setOnAction(new ButtonListener2());
        HBox buttonHb1 = new HBox(10);
        buttonHb1.setAlignment(Pos.CENTER);
        //buttonHb1.getChildren().addAll(btn1, btn2);
        buttonHb1.getChildren().addAll(btn1);

        // Status message text
        actionResult = new Text();
        actionResult.setFont(Font.font("Calibri", FontWeight.NORMAL, 20));
        actionResult.setFill(Color.FIREBRICK);

        // Vbox
        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(25, 10, 10, 10));
        vbox.getChildren().addAll(labelHb, buttonHb1, actionResult);

        // Scene
        Scene scene = new Scene(vbox, 600, 400); // w x h
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ButtonListener1 implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            actionResult.setText("");
            List<File> selectedFiles = showFileChooser();
//            Optional<File> myFile = showFileChooser();

            if (selectedFiles != null) {
                selectedFiles.forEach(myFile -> {
                    try {
                        FileType fileType = FileTypeDetector.detectFileType(
                                new BufferedInputStream(new FileInputStream(myFile)));

                        if (fileType == FileType.Jpeg) {
                            MediaResetDate m = new MediaResetDateJpg(myFile);
                            m.changeDate();
                        }

                        if (fileType == FileType.Mov) {
                            MediaResetDate m = new MediaResetDateMov(myFile);
                            m.changeDate();
                        }
                    } catch (Exception ex) {
                        actionResult.setText("Sorry cannot read file: " + myFile.getName());
                        ex.printStackTrace();
                    }
                });
            }

            //////////////////////
/*
            if (myFile.isPresent()) {
                try {
                    FileType fileType = FileTypeDetector.detectFileType(
                            new BufferedInputStream(new FileInputStream(myFile.get())));

                    if (fileType == FileType.Jpeg) {
                        MediaResetDate m = new MediaResetDateJpg(myFile.get());
                        m.changeDate();
                    }

                    if (fileType == FileType.Mov) {
                        MediaResetDate m = new MediaResetDateMov(myFile.get());
                        m.changeDate();
                    }


                } catch (Exception ex) {
                    actionResult.setText("Sorry cannot read the file");
                    ex.printStackTrace();
                }
            }
*/
        }
    }


    private List<File> showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory(new File("C:\\Users\\Alby\\Downloads\\iCloud Photos"));
        fileChooser.getExtensionFilters().addAll(
                //        new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                //      new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("My Files", "*.jpg", "*.mov"));
        //    new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
        //  new FileChooser.ExtensionFilter("All Files", "*.*"));

        fileChooser.setTitle("Select files");

//        File selectedFile = fileChooser.showOpenDialog(null);
//        if (selectedFile != null) {
//            return Optional.of(selectedFile);
//        } else {
//            return Optional.empty();
//        }

        return fileChooser.showOpenMultipleDialog(null);

    }

}

