package giba.controller;

import com.jfoenix.controls.JFXTextField;
import giba.model.ForgotPassword;
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
 * The ForgotPasswordController handles retrieving the user's password from the database in the
 * occurrence that the user forgets their password.
 *
 * @author Andrew Cavallaro
 */
public class ForgotPasswordController {

  // Text field to gather the user's first name //
  @FXML private JFXTextField firstName;

  // Text field to gather the user's last name //
  @FXML private JFXTextField lastName;

  // Text field to gather the user's username //
  @FXML private JFXTextField username;

  // Parent object //
  private Parent parent;

  // Stage object //
  private Stage stage;

  // Scene object //
  private Scene scene;

  /**
   * The getPassword method returns the user's password if the user exists in the database.
   *
   * @param event Mouse click on "Get Password"
   * @throws SQLException yes, it does
   * @throws ClassNotFoundException yes, it does
   * @throws IOException yes, it does
   */
  @FXML
  private void getPassword(ActionEvent event)
      throws SQLException, ClassNotFoundException, IOException {

    String first = firstName.getText();
    String last = lastName.getText();
    String user = username.getText();
    String password;

    Alert alert;
    DialogPane dialogPane;

    if (first.isEmpty() || last.isEmpty() || user.isEmpty()) {
      alert = new Alert(AlertType.ERROR);
      alert.setContentText("Please enter all fields.");
      dialogPane = alert.getDialogPane();
      dialogPane
          .getStylesheets()
          .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
      alert.show();
    } else {
      ForgotPassword forgotPassword = new ForgotPassword(first, last, user);
      password = forgotPassword.getPassword();

      if (password.isEmpty()) {
        alert = new Alert(AlertType.ERROR);
        alert.setContentText("No matching record found. Please try again.");
        dialogPane = alert.getDialogPane();
        dialogPane
            .getStylesheets()
            .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
        alert.show();
      } else {
        alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Your password is " + password + ".");
        alert.setTitle("Success");
        alert.setHeaderText("Record Found");
        dialogPane = alert.getDialogPane();
        dialogPane
            .getStylesheets()
            .add(getClass().getResource("/giba/resources/styles.css").toExternalForm());
        alert.show();
        parent =
            FXMLLoader.load(
                Objects.requireNonNull(
                    getClass().getClassLoader().getResource("giba/view/login.fxml")));
        scene = new Scene(parent);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
      }
    }
  }

  /**
   * The back method returns the user to the login screen.
   *
   * @param event Mouse click on "Back"
   * @throws IOException yes, it does
   */
  @FXML
  private void back(ActionEvent event) throws IOException {
    parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/login.fxml")));
    scene = new Scene(parent);
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
