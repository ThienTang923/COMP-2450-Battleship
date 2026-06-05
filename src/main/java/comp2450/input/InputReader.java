package comp2450.input;

import comp2450.exceptions.InvalidInputException;
import comp2450.model.Coordinate;

import java.util.Locale;
import java.util.Scanner;

public class InputReader {

    private Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readString(String prompt) throws InvalidInputException {
        System.out.println(prompt);

        String input = scanner.nextLine();

        if (input.isBlank()) {
            throw new InvalidInputException("Input cannot be empty. Please enter a value");
        }

        return input;
    }

    public int readInt(String prompt) throws InvalidInputException {
        System.out.println(prompt);

        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            throw new InvalidInputException("Invalid input. Please enter a whole number");
        }

        int value = scanner.nextInt();
        scanner.nextLine();

        return value;
    }

    public int readPositiveInt(String prompt) throws InvalidInputException {
        int value = readInt(prompt);

        if (value <= 0) {
            throw new InvalidInputException("Invalid number. Please enter a positive number greater than 0.");
        }

        return value;
    }

    public Coordinate readCoordinate(String prompt) throws InvalidInputException {
        System.out.println(prompt);

        int x = readInt("Enter x: ");
        int y = readInt("Enter y: ");

        if (x < 0|| y<0) {
            throw new InvalidInputException("Invalid coordinate. x and y must be greater. ");
        }

        return new Coordinate(x, y);
    }

    public String readDirection(String prompt) throws InvalidInputException {
        String direction = readString(prompt).toUpperCase();

        if (!direction.equals("UP") && !direction.equals("DOWN")
                && !direction.equals("LEFT") && !direction.equals("RIGHT")) {
            throw new InvalidInputException(
                    "Invalid direction. Please enter UP, DOWN, LEFT, or RIGHT."
            );
        }

        return direction;
    }
}

