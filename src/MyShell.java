

class MyShell extends Thread {

  private static final String LINE_SEPARATOR = 
                              System.getProperty("line.separator");
  
  public void run() {
          
    // process a command line
    for (int cmdLine = 1; ; cmdLine++) {
      String str = "";
      // only process a command and increment counter if user types any
      // character before a new line character is received
      do {
        StringBuffer sb = new StringBuffer();
        // use error output file descriptor to print shell prompt
        SysLib.cerr("shell[" + cmdLine + "]% ");
        // receive input from console
        SysLib.cin(sb);
        str = sb.toString();
      } while (str.length() == 0);
      
      // create an array of all arguments
      String[] args = SysLib.stringToArgs(str);
      
      // split commands into array of commands where each command
      // is separated by a ";" or "&"
      int idxCmdStart = 0;
      
      // parse command line until a command separator "&" or ";" is encountered
      // pass arguments to helper function stripCommand
      // loop until the last index is equal to the number of arguments
      for (int idxCmdEnd = 0; idxCmdEnd < args.length; 
           idxCmdEnd++) {
        if ((!args[idxCmdEnd].equals(";")) && (!args[idxCmdEnd].
             equals("&")) && (idxCmdEnd != args.length - 1))
          continue;

        String[] command = stripCommand(args, idxCmdStart, 
                           idxCmdEnd == args.length - 1 ? 
                           idxCmdEnd + 1 : idxCmdEnd);

        if (command != null) {
          // print the command followed by new line
          SysLib.cerr(command[0] + LINE_SEPARATOR);
          if (command[0].equals("exit")) {
            SysLib.exit();
            return;
          }
          // Wait for execution to complete unless the command ends with the 
          // concurrency symbol "&". Otherwise a join will allow the process
          // to wait for *this* child thread to be terminated.
          int retCode = SysLib.exec(command);
          while ((retCode != -1) && 
            (!args[idxCmdEnd].equals("&")) && 
            (SysLib.join() != retCode));
        }
        idxCmdStart = idxCmdEnd + 1;
      }
    }
  }

  private String[] stripCommand(String[] args, int idxCmdStart, 
                                int idxCmdEnd) {
                                  
    // handle the case for no command (a space)
    boolean isEmpty = true;
    for (int i = idxCmdStart; i < idxCmdEnd; i++) {
      if (!args[i].isEmpty()) {
        isEmpty = false;
        break;
      }
    }
    if (isEmpty == true) return null;
    
    // handle the case where a command ends with a "&" or ";"
    // we will not process the "&" or ";" as an actual command
    if ((args[(idxCmdEnd - 1)].equals(";")) || 
        (args[(idxCmdEnd - 1)].equals("&"))) {
      idxCmdEnd--;
    }
    // if there is no command return null
    if (idxCmdEnd == idxCmdStart) return null;
    
    String[] command = new String[idxCmdEnd - idxCmdStart];
    for (int i = idxCmdStart; i < idxCmdEnd; i++) {
      command[(i - idxCmdStart)] = args[i];
    }
    return command;
  }
}