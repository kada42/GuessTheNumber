import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private String playerName;
    private boolean flag = true;
    private int score = Integer.MAX_VALUE;
    private int guessedNumber;
    private int randomNumber;
    private int counter = 0;
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        createRandomNumber();
        setPlayerName();
        play();
    }

    private void createRandomNumber() {
        randomNumber = random.nextInt(10) + 1;
    }

    private void setPlayerName() {
        System.out.print("Please enter your name: ");
        this.playerName = scanner.nextLine();
        if (playerName.equalsIgnoreCase("")) {
            this.playerName = "player1";
        }
    }

    private void highScore() {
        if (counter < score) {
            score = counter;
            System.out.println("New highscore! Fewest turns: " + score);
        } else {
            System.out.println("Fewest turns: " + score);
        }
    }

    private void showHighScore() {
        new GameDB();
    }

    private void addDataToDB() {
        new GameDB(playerName, score);
    }

    private void playAgain() {
        System.out.println("\nDo you wish to play again? (y/n)");
        String playAgain = scanner.nextLine();
        if (!playAgain.equalsIgnoreCase("y")) {
            flag = false;
            thanksForPlaying();
        } else {
            createRandomNumber();
            counter = 0;
        }
    }

    private void thanksForPlaying() {
        System.out.println("Thanks for playing " + playerName + "!");
    }

    private void play() {
        while (flag) {
            try {
                while (true) {
                    System.out.println("Enter a number between 1-10: ");
                    guessedNumber = scanner.nextInt();
                    scanner.nextLine(); // flushes scanner

                    if (guessedNumber < 1 || guessedNumber > 10) {
                        System.out.println("You must enter a number between 1-10: ");
                        continue;
                    }
                    if (guessedNumber == randomNumber) {
                        System.out.println("Your guess is correct!");
                        break;
                    } else if (guessedNumber < randomNumber) {
                        System.out.println("Your guess is too low!");
                        counter++;
                    } else {
                        System.out.println("Your guess is to high!");
                        counter++;
                    }
                }

                highScore();
                addDataToDB();
                showHighScore();
                playAgain();

            } catch (InputMismatchException e) {
                System.out.println("Please enter a whole number.");
                scanner.nextLine(); // flushes scanner
            } catch (NoSuchElementException e) {
                thanksForPlaying();
                System.exit(0);
            }
        }
    }
}
