package giba.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import giba.globals.GlobalVariables;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/**
 * The ViewHistoryController is responsible for displaying the contents of the
 * maintenance_history.txt file to the JFXTextArea. That file is appended to every time a task is
 * marked as completed.
 *
 * @author Andrew Cavallaro
 */
public class ViewHistoryController {

  // Combobox that displays all available files to choose from //
  @FXML private JFXComboBox<String> allFiles;

  // JFXTextArea that will display the contents of the maintenance_history.txt file //
  @FXML private JFXTextArea historyView;

  /**
   * The initialize method populates the JFXTextArea with the contents of the
   * maintenance_history.txt file which is written to each time a task is marked as complete.
   */
  public void initialize() {

    historyView.setEditable(false);

    File folder = new File(GlobalVariables.path);
    File[] listOfFiles = folder.listFiles();
    assert listOfFiles != null;
    String[] fileNames = new String[listOfFiles.length];

    for (int i = 0; i < listOfFiles.length; i++) {
      fileNames[i] = listOfFiles[i].getName();
    }

    List<String> files = new ArrayList<>(Arrays.asList(fileNames));
    ObservableList<String> fileList = FXCollections.observableList(files);
    allFiles.getItems().clear();
    allFiles.setItems(fileList);
    allFiles.getSelectionModel().selectFirst();
  }

  /**
   * The done method returns the user to the dashboard.
   *
   * @param event Mouse click on "Done" button
   * @throws IOException yes, it does
   */
  @FXML
  private void back(ActionEvent event) throws IOException {
    Parent parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
    Scene scene = new Scene(parent);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The select method takes the selected file in the combobox and reads the file and displays the
   * contents to the text area.
   *
   * @throws FileNotFoundException yes, it does
   */
  public void select() throws FileNotFoundException {

    if (allFiles.getSelectionModel().getSelectedItem().equals("")
        || allFiles.getSelectionModel().getSelectedItem() == null) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Please choose a date.");
      DialogPane dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
    } else {
      File file = new File(GlobalVariables.path + allFiles.getSelectionModel().getSelectedItem());
      Scanner scanner = new Scanner(file);

      while (scanner.hasNext()) {
        historyView.appendText(scanner.nextLine() + "\n");
      }
    }
  }
}
