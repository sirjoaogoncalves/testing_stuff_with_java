import java.util.Scanner;
import java.util.Random;


public class NumberGuesser {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        int number = random.nextInt(100) + 1;
        int guess = 0;
        int tries = 0;
        System.out.println("Guess a number between 1 and 100:");
        while (guess != number) {
            guess = input.nextInt();
            tries++;
            if (guess < number) {
                System.out.println("Higher");
            } else if (guess > number) {
                System.out.println("Lower");
            } else {
                System.out.println("You win!");
            }
        }
        System.out.println("Number of tries: " + tries);
    }
}


