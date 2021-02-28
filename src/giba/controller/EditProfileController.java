package giba.controller;

import com.jfoenix.controls.JFXTextField;
import giba.globals.GlobalVariables;
import giba.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * The EditProfileController is responsible for making changed to an employee's profile. The
 * employee can change their first name, last name, username, and password. The user can also upload
 * a photo if they would like.
 */
public class EditProfileController {

  // TextField to gather the user's first name //
  @FXML private JFXTextField firstName;

  // TextField to gather the user's last name //
  @FXML private JFXTextField lastName;

  // TextField to gather the user's username //
  @FXML private JFXTextField username;

  // TextField to gather the user's password //
  @FXML private JFXTextField password;

  // Parent object //
  private Parent parent;

  // Stage object //
  private Stage stage;

  // Scene object //
  private Scene scene;

  /**
   * The initialize method populates the firstName, lastName, username, and password fields with the
   * currently signed in employee's information.
   *
   * @throws SQLException yes, it does
   * @throws ClassNotFoundException yes, it does
   */
  public void initialize() throws SQLException, ClassNotFoundException {
    firstName.setText(GlobalVariables.firstName);
    lastName.setText(GlobalVariables.lastName);

    Employee employee = new Employee();
    employee.getFromDatabase(GlobalVariables.firstName, GlobalVariables.lastName);

    username.setText(employee.getUsername());
    password.setText(employee.getPassword());
  }

  /**
   * The saveProfile method updates the database with the entered information on the screen.
   *
   * @param event Mouse click on "Save" button
   */
  @FXML
  private void saveProfile(ActionEvent event)
      throws IOException, SQLException, ClassNotFoundException {

    Employee employee = new Employee();

    Alert alert = new Alert(AlertType.ERROR);
    DialogPane dialogPane;

    String first = firstName.getText();
    String last = lastName.getText();
    String user = username.getText();
    String pass = password.getText();

    if (!first.equals("") && !last.equals("") && !user.equals("") && !pass.equals("")) {
      if (pass.length() < 8) {
        alert.setContentText("Password must be at least 8 characters long");
        dialogPane = alert.getDialogPane();
        dialogPane
            .getStylesheets()
            .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
        alert.show();
      } else {
        employee.updateDatabase(first, last, user, pass);

        parent =
            FXMLLoader.load(
                Objects.requireNonNull(
                    getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
        scene = new Scene(parent);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
      }
    } else {
      alert.setContentText("All fields must be filled out properly");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
    }
  }

  /**
   * The back method returns the user to the dashboard.
   *
   * @param event Mouse click on "Back"
   */
  @FXML
  private void back(ActionEvent event) throws IOException {
    parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
    scene = new Scene(parent);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
