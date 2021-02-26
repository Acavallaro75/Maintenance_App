package giba.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import giba.model.Login;
import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The LoginController handles the operations that are performed on the landing screen. The user has
 * two options on this screen: attempt to login to the system or to create a new account. If the
 * user attempts to login to the system, there will be a verification of username and password
 * entry. If the username and password combination is invalid, the user will be denied access to the
 * system until proper credentials are entered. If the user would like to create a new account, they
 * are brought to the account registration screen.
 *
 * @author Andrew Cavallaro
 */
public class LoginController {

  // Error message when username and password are incorrect and/or empty //
  @FXML private Label badCredentials;

  // Text field to gather the user's entered password //
  @FXML private JFXPasswordField password;

  // Text field to gather the user's entered username //
  @FXML private JFXTextField username;

  /**
   * The addEmployee method is used to bring the user to the employee registration screen if the
   * "Create Account" button is clicked. It will display the add_employee.fxml layout file.
   *
   * @param event Mouse clicked on "Create Account"
   * @throws IOException yes, it does
   */
  @FXML
  public void addEmployee(MouseEvent event) throws IOException {
    Parent parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/add_employee.fxml")));
    Scene scene = new Scene(parent);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  /**
   * The signInPressed method is the process of determining if the username and password combination
   * is valid or invalid. The username and password fields are passed to a checkEmployee method in
   * the Login class which accesses the database and verifies username and password combination. If
   * the username and password combination are acceptable, the user will be brought to the
   * dashboard. Otherwise, the user will be displayed an error message. There are two error
   * messages: "Try Again" if username and/or password is empty and "Invalid Combination" if the
   * username and password combination are invalid.
   *
   * @param event Mouse clicked on "Sign In"
   * @throws IOException yes, it does
   */
  @FXML
  public void signInPressed(ActionEvent event) throws IOException {
    String user = username.getText();
    String pass = password.getText();

    // If username and password are not empty //
    if (!user.equals("") && !pass.equals("")) {
      Login login = new Login();

      // If true, login to the system //
      if (login.checkEmployee(user, pass)) {
        Parent parent =
            FXMLLoader.load(
                Objects.requireNonNull(
                    getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
      } else {
        badCredentials.setText("Invalid Combination");
      }
    } else {
      badCredentials.setText("Try Again");
    }
  }
}
