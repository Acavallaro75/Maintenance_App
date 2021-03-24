package giba.model;

import giba.globals.GlobalVariables;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/** The MiscellaneousTasks class handles the writing of miscellaneous tasks to the history file. */
public class MiscellaneousTasks {

  /**
   * The addToHistory method appends the passed details string to the maintenance_history.txt file.
   *
   * @param details details about the miscellaneous task
   */
  public void addToHistory(String details) {
    try {
      FileWriter fileWriter = new FileWriter(GlobalVariables.file, true);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      bufferedWriter
          .append("Miscellaneous task completed on ")
          .append(LocalDate.now().toString())
          .append(" by ")
          .append(GlobalVariables.firstName)
          .append(" ")
          .append(GlobalVariables.lastName)
          .append(".")
          .append("\nDetails: ")
          .append(details)
          .append("\n\n");
      bufferedWriter.close();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
