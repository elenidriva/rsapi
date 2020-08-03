package gr.codehub.rsapi.utility;

public class Logger {
    public static void log(String text) {
        System.out.println("LOG: " + text);

        /**
         * if i would like to show log message in window
         * JOptionPane.showMessageDialog(null, text);
         */

    }

    public static void print(String text) {
        System.out.println("PRINT: " + text);
    }

}
