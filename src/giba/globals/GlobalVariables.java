package giba.globals;

import java.io.File;

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

  // File to be written to //
  public static final File file = new File("maintenance_history.txt");
}
