package giba.globals;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.File;

/**
 * The GlobalVariables class is used to transfer information screen to screen seamlessly.
 *
 * @author Andrew Cavallaro
 */
public class GlobalVariables {

  // Name of a task //
  @SuppressFBWarnings("UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD")
  public static String taskName;

  // Employee first name //
  @SuppressFBWarnings("UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD")
  public static String firstName;

  // Employee last name //
  @SuppressFBWarnings("UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD")
  public static String lastName;

  // Frequency in integer format //
  @SuppressFBWarnings("UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD")
  public static int numberOfDays;

  // File to be written to //
  public static final File file = new File("maintenance_history.txt");
}
