package comp2450.ui;

import comp2450.output.GamePrinter;

import java.util.Scanner;

public class REPL {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        System.out.println("Welcome to Battleship++");

        while (running) {

            System.out.print("> ");
            String command = scanner.nextLine();

            switch (command.toUpperCase()) {

                case "HELP":
                    GamePrinter.printHelp();
                    break;

                case "SHOW GAME":
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
