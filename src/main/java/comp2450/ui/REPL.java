package comp2450.ui;

import java.util.Scanner;

public class REPL {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            System.out.print("> ");
            String command = scanner.nextLine();

            switch (command.toUpperCase()) {

                case "HELP":
                    printHelp();
                    break;

                case "EXIT":
                    running = false;
                    break;
                    
                default:
                    System.out.println("Unknow command");
            }
        }
    }
}
