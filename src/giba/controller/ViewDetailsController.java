package giba.controller;

import giba.globals.GlobalVariables;
import giba.model.Details;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * The ViewDetailsController handles showing the details of a selectedTask from the dashboard.
 *
 * @author Andrew Cavallaro
 */
public class ViewDetailsController {

  // TextArea to display the task's details //
  @FXML private TextArea detailsView;

  /**
   * The initialize method populates the TextArea with the relevant information about the task
   * selected on the dashboard.
   *
   * @throws ClassNotFoundException yes, it does
   * @throws SQLException yes, it does
   */
  public void initialize() throws ClassNotFoundException, SQLException {

    Details details = new Details();
    details.retrieveDetails();

    String taskDetails = details.getDetails();
    Date completionDate = details.getCompletionDate();
    Date nextDate = details.getNextDate();

    // Appending to the TextArea //
    detailsView.appendText(
        "Task Name: "
            + GlobalVariables.taskName
            + "\n\nDetails: "
            + taskDetails
            + "\nLast completed on "
            + completionDate
            + "\nNeeds to be completed by "
            + nextDate);
  }

  /**
   * The done method returns the user to the dashboard.
   *
   * @param event Mouse click on "Done" button
   * @throws IOException yes, it does
   */
  @FXML
  private void done(ActionEvent event) throws IOException {
    Parent parent =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("giba/view/dashboard.fxml")));
    Scene scene = new Scene(parent);
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
