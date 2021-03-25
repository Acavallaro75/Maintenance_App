package giba.globals;

import java.io.File;
import java.time.LocalDate;

/**
 * The GlobalVariables class is used to transfer information screen to screen seamlessly.
 *
 * @author Andrew Cavallaro
 */
public class GlobalVariables {

  // Name of a task //
  public static String taskName;

  // Employee first name //
  public static String firstName;

  // Employee last name //
  public static String lastName;

  // Frequency in integer format //
  public static int numberOfDays;

  // Date object //
  public static LocalDate today = LocalDate.now();

  // Path to folder of reports //
  public static final String path = "C:/Users/kathy/OneDrive/Desktop/reports/";

  // File to be written to //
  public static final File file = new File(path + today.toString() + ".doc");
}
