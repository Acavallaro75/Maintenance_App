package giba.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import giba.model.Employee;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * The AddEmployeeController class is used to add a new employee to the database.
 *
 * @author Andrew Cavallaro
 */
public class AddEmployeeController {

  // Text field for the employee's first name //
  @FXML private JFXTextField firstName;

  // Text field for the employee's last name //
  @FXML private JFXTextField lastName;

  // Text field for the employee's username //
  @FXML private JFXTextField username;

  // Text field for the employee's password //
  @FXML private JFXPasswordField password;

  /**
   * The goBack method brings the user back to the landing screen.
   *
   * @param event Mouse click on "Back"
   * @throws IOException yes, it does
   */
  @FXML
  public void goBack(javafx.event.ActionEvent event) throws IOException {
    Parent parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/login.fxml")));
    Scene scene = new Scene(parent);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The createNewEmployee method is used to insert the employee into the employee database. It
   * verifies that all fields are not empty and that the password is at a minimum of 8 characters in
   * length. If any of the fields are empty or the password is too short, an error alert message
   * will be displayed to the user. If everything is correct, the employee's details will be entered
   * into the database using the addToDatabase method and a confirmation message will be displayed
   * to the user.
   *
   * @param event Mouse click on "Create Employee"
   * @throws SQLException yes, it does
   * @throws ClassNotFoundException yes, it does
   * @throws IOException yes, it does
   */
  @FXML
  public void createNewEmployee(ActionEvent event)
      throws SQLException, ClassNotFoundException, IOException {
    String first = firstName.getText();
    String last = lastName.getText();
    String user = username.getText();
    String pass = password.getText();

    // If the first name, last name, username, or password fields are empty //
    if (first.equals("") || last.equals("") || user.equals("") || pass.equals("")) {
      Alert error = new Alert(Alert.AlertType.ERROR);
      error.setContentText("Please ensure all fields are filled correctly");
      error.show();

      // If the length of the password is less than 8 characters //
    } else if (pass.length() < 8) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setContentText("Password must contain at least 8 characters");
      alert.show();

      // If all fields are entered properly //
    } else {
      Employee employee = new Employee(first, last, user, pass);
      employee.addToDatabase();

      Alert confirm = new Alert(AlertType.CONFIRMATION);
      confirm.setContentText(first + " " + last + " has been successfully registered");
      confirm.show();

      Parent parent =
          FXMLLoader.load(
              Objects.requireNonNull(
                  getClass().getClassLoader().getResource("giba/view/login.fxml")));
      Scene scene = new Scene(parent);
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    }
  }
}
