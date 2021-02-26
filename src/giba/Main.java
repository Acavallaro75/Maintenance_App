package giba;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class that runs first in any program.
 *
 * @author Andrew Cavallaro
 */
public class Main extends Application {

  /**
   * Starts the main landing screen.
   *
   * @param primaryStage Main screen
   * @throws Exception yes, it does
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
    primaryStage.setTitle("GIBA Maintenance Tracker");
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
  }

  /**
   * Main method inside the Main class.
   *
   * @param args command ling arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
