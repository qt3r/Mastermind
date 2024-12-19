/*
 Name: Angelo Yap   
 Student Number: 100421421
 Course: CPSC1150        Section 4
 Date: April 4 2024
 Instructor: Bryan Green
 Purpose: Play the game mastermind (but with numbers instead of colours) inside the terminal.
 Input:   A difficulty, then your guesses on what the mastermind sequence is.
 Output:  Whether your guess is incorrect or correct. Every 3 guesses offers to give a hint and every round until you accept and then after another 3 rounds.
*/

import java.util.Scanner;

/**
 * This program implements the board game mastermind into a terminal game.
 * <p> Mastermind is played by guessing a sequence of colours within a certain amount of turns.
 * Instead of using colours, this game uses a sequence of unique integers to be guessed. The length of this sequence
 * is dependent on the given difficulty (2 to 10 inclusive). You are given a total of 12 attempts to guess the number. After 3 guesses you are prompted
 * if you would like a hint. If you choose to take the hint, you will be given another hint in three rounds. This continues until you run out of guesses.
 * If you refuse the hint, you will be prompted if you would like a hint every round until you accept; if you choose to accept it will follow the same rules as mentioned and
 * prompt if you would like another hint in 3 guesses. </p>  
 * @author Angelo Yap
 * @version 1.1.2
 */
public class Mastermind 
{
    private static Scanner keyboard = new Scanner(System.in);
    private static boolean cheaterMode = false;

    public static void main(String[] args)
    {
        printIdentification();
        printInstructions();
        System.out.println("Please input a difficulty from 10 to 2!");
        int difficulty = getDifficulty();
        int[] masterKey = getRandomFilledArray(difficulty);
        playMastermind(masterKey, difficulty);
    }

    /**
     * Creates an array the size of the difficulty and fills it with random unique integers.
     * @param size as the size of the array to be created.
     * @return an array the size of the difficulty filled with random unique integers.
     */
    private static int[] getRandomFilledArray(int size)
    {
        int[] array = new int[size];
        int[] pickFrom = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

        for(int index = 0; index < array.length; index++)
        {
            int randomIndex = (int) (Math.random()*(10-index));
            array[index] = pickFrom[randomIndex];
            pickFrom[randomIndex] = pickFrom[9 - index];
        }

        return array;
    }

    /**
     * Prints the given array in a nice manner.
     * @param array the array to be printed.
     */
    private static void printSequence(int[] array)
    {
        System.out.print("[");
        for(int index = 0; index < array.length; index++)
        {
            System.out.print(array[index] + " ");
        }
        System.out.println("]");
    }
    /**
     * Gets an input from the user and checks if it is an integer and if it is within 2-10 (inclusive).
     * @return a valid integer value.
     */
    private static int getDifficulty()
    {
        int number = -1;
        boolean isGoodInput = false;
        while(!isGoodInput)
        {
            if(keyboard.hasNextInt())
            {
                number = keyboard.nextInt();
                if (number <= 10 && number >= 2)
                    isGoodInput = true;
                else
                {
                    System.out.println("Please input a difficulty from 10 to 2 (inclusive)!");
                    keyboard.nextLine();
                }
            }
            else
            {
                System.out.println("Please use a valid integer!");
                keyboard.nextLine();
            }
        }
        return number;
    }

    /**
     * Gets a guess from the user and checks if it is a valid guess. (An integer and the same length as the master key and does not repeat digits)
     * @param difficulty as the given difficulty of the game.
     * @return a number in the form of a string.
     */
    private static String getGuess(int difficulty)
    {
        String number = "";
        boolean isGoodInput = false;
        while(!isGoodInput)
        {
            if(keyboard.hasNextLong())
            {
                number = keyboard.next();
                if(Long.valueOf(number) > 0 && number.length() == difficulty)
                {
                    if (hasUniqueDigits(number))
                        isGoodInput = true;
                    else
                    {
                        System.out.println("Please make a guess that is not a negative number, is the same length as your difficulty, and CONTAINS UNIQUE DIGITS!");
                        keyboard.nextLine();
                    }
                }   
                else
                {
                    System.out.println("Please make a guess that is not a negative number, is the same length as your difficulty, and contains unique digits!");
                    keyboard.nextLine();
                }
            }
            else
            {
                System.out.println("Please use a valid integer!");
                keyboard.nextLine();
            }
        }
        return number;
    }

    /**
     * Checks if a given string number does not repeat digits.
     * @param number as the given string number to be checked
     * @return a boolean value depending if it repeats digits or not.
     */
    private static boolean hasUniqueDigits(String number)
    {
        int checkSum = 0;
        int zeroCount = 0;
        int[] checkSumRemainingIntegers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        for(int index = 0; index < number.length(); index++)
        {
            int digit = number.charAt(index) - '0';
            checkSum += digit;
            checkSumRemainingIntegers[digit] = 0;
            if(digit == 0)
                zeroCount++;
        }

        if(zeroCount > 1)
            return false;
        for(int index = 1; index < 10; index++)
        {
            checkSum += checkSumRemainingIntegers[index];
        }

        if(checkSum == 45)
            return true;
        return false;
    }
    /**
     * Turns a given number that is in string form into an array of individual one digit integers.
     * @param number as the string number to be converted
     * @param difficulty as the difficulty of the game
     * @return an integer array containing a single digit integer in each index.
     */
    private static int[] convertToArray(String number, int difficulty)
    {
        int[] convertedNumber = new int[difficulty];
        
        for(int index = 0; index < convertedNumber.length; index++)
        {
            convertedNumber[index] = number.charAt(index) - '0';
        }
        return convertedNumber;
    }

    /**
     * Checks if the two given arrays have the same integers in the same spots.
     * @param masterKey as the array to be compared against.
     * @param guess as the array to be compared.
     * @return
     */
    private static boolean checkIfSameArray(int[] masterKey, int[] guess)
    {
        for(int index = 0; index < masterKey.length; index++)
        {
            if(masterKey[index] != guess[index])
                return false;
        }
        return true;
    }
    
    /**
     * Counts how many digits are in the correct spot and how many digits are in the wrong spot and returns the two values in an array.
     * @param masterKey as the array to be compared against.
     * @param guess as the array to be compared.
     * @return an integer array of size two containing the stats for how many digits in the correct spot and how many digits are in the wrong spot but appear.
     */
    private static int[] checkIfDigitsMatchOrSameSpot(int[] masterKey, int[] guess)
    {
        int correctSpot = 0;
        int correctDigit = 0;
        int[] stats = new int[2];

        for(int index = 0; index < guess.length; index ++)
        {
            if(masterKey[index] == guess[index])
            {
                correctSpot++;
                correctDigit++;
            }
            else
                for(int checkIndex = 0; checkIndex < masterKey.length; checkIndex++)
                {
                    if(guess[index] == masterKey[checkIndex])
                    {
                        correctDigit++;
                        break;
                    }
                }   
        }
        System.out.println("You got " + (correctDigit - correctSpot) + " correct digits in the wrong spot and ");
        System.out.println(correctSpot + " correct digits in the correct spot!");

        stats[0] = correctSpot;
        stats[1] = correctDigit - correctSpot;
        return stats;
    }
    
    /**
     * Takes your guess and tells you if you win. If it isn't correct, it lets you guess again for a total of 12 guesses.
     * After 3 guesses it offers a hint, if you take the hint it will offer a hint after 3 guesses. If you don't, it will offer a hint each round until you accept it.
     * After each round of guessing, it prints out whether your guess is incorrect, and how many digits you guessed in the wrong spots and how many you guessed in the right spots.
     * If you succeed in guessing the sequence or run out of hits, it prints out the winner alongside the guessing stats.
     * @param masterKey as the master sequence to be guessed.
     * @param difficulty as the given difficulty.
     */
    private static void playMastermind(int[] masterKey, int difficulty)
    {
        int untilHint = 3;
        int numberOfGuesses = 12;   //change this value if you want to lower or raise the amount of guesses
        int hintType = 1;
        int digitsGuessedInCorrectSpot = 0;
        int digitsGuessedCorrectlyInWrongSpot = 0; 
        while(numberOfGuesses > 0)
        {
            if(cheaterMode)
                printSequence(masterKey);
            if(untilHint == 0)
            {
                System.out.println("Would you like a hint? Enter (Y)es for a hint or type anything to continue");
                if(askIfHint())
                {
                    giveHint(masterKey, hintType);
                    hintType++;
                    untilHint = 3;
                }
                else
                {
                    System.out.println("Skipping the hint! Please enter your guess!");
                }
            }
            else
            {
                System.out.println("Please enter your guess!");
                untilHint--;
            }
            int[] guess = convertToArray(getGuess(difficulty), difficulty);  //get guess in array form
            
            numberOfGuesses--;

            if(checkIfSameArray(masterKey, guess))
            {
                System.out.println("Your guess is correct!");
                digitsGuessedInCorrectSpot += masterKey.length;
                break;
            }
            else
            {
                System.out.println("Your guess was incorrect!");
                int[] stats = checkIfDigitsMatchOrSameSpot(masterKey, guess);
                digitsGuessedInCorrectSpot += stats[0];
                digitsGuessedCorrectlyInWrongSpot += stats[1];
            }
            
            
            System.out.println("You have " + numberOfGuesses + " guesses left!");
        
        }

        printWinner(numberOfGuesses, masterKey, digitsGuessedInCorrectSpot, digitsGuessedCorrectlyInWrongSpot);

    }
    /**
     * Prints out a hint depending on which hint type it is told to give.
     * @param masterKey as the master sequence to be guessed.
     * @param hintType as which hint to be chosen.
     */
    private static void giveHint(int[] masterKey, int hintType) //add more cases if you increase guess count and need more hints
    {
        switch(hintType)
        {
            case 1:
                sumHint(masterKey);
                break;
            case 2:
                availableDigitHint(masterKey);
                break;
            case 3:
                firstAndLastNumberHint(masterKey);
                break;
            default:
                System.out.println("ERROR: OUT OF HINTS");
        }
    }
    /**
     * Prints out a hint telling the sum of all digits in the sequence from least to greatest.
     * @param masterKey as the master sequence to be guessed
     */
    private static void availableDigitHint(int[] masterKey)
    {
        System.out.println("Your second hint is: ");
        System.out.println("The digits used in this sequence are ");
        int[] availableDigits = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}; //initialize all numbers to -1 so we can see if they are used or not
        for (int index = 0; index < masterKey.length; index++) {
            availableDigits[masterKey[index]] = masterKey[index];
        }

        for (int index = 0; index < availableDigits.length; index++) 
        {
            if(availableDigits[index] > -1)
                System.out.print(availableDigits[index] + " ");
        }
        System.out.print("\n");
    }

    /**
     * Prints out a hint telling the sum of all digits in the sequence
     * @param masterKey as the master sequence to be guessed
     */
    private static void sumHint(int[] masterKey)
    {
        int sum = 0;
        for(int index = 0; index < masterKey.length; index++)
        {
            sum += masterKey[index];
        }
        System.out.println("Your first hint is: ");
        System.out.println("The sum of the digits is " + sum);
    }

    /**
     * Prints out a hint telling the first and last number in the sequence
     * @param masterKey as the master sequence to be guessed
     */
    private static void firstAndLastNumberHint(int[] masterKey)
    {
        System.out.println("Your last hint is:");
        System.out.println("The first digit of the sequence is " + masterKey[0] + " and the last digit of the sequence is " + masterKey[masterKey.length-1]);
    }

    /**
     * Asks the user if they would like a hint and returns a boolean value depending on their response.
     * @return a boolean value.
     */
    private static boolean askIfHint()
    {
        String answer = keyboard.next();
        if(Character.toUpperCase(answer.charAt(0)) == 'Y')
            return true;
        return false;
    }

    /**
     * Prints the instructions for the program.
     */
    private static void printInstructions()
    {
        System.out.println("This program implements the game mastermind using a sequence of unique integers.");
        System.err.println("Mastermind is a game where the user tries to guess a randomly generated sequence of unique integers.");
        System.out.println("First you will enter a difficulty, which will be the length of the sequence you will be guessing.");
        System.out.println("You can attempt to guess the sequence up to 12 times until the computer wins.");
        System.out.println("Hints will be offered every 3 guesses. If refused, a hint will be prompted until you accept, and be offered again after 3 guesses.");
    } // printInstructions

    /**
     * Prints the name of the program, identification of the author, alongside the course and section for this assignment.
     */
    private static void printIdentification()
    {
        System.out.println("### Mastermind  Author: Angelo Yap  ###");
        System.out.println("### Course/Section - CPSC1150-4    St.# - 100421421 ###\n");
        System.out.println(new java.util.Date());
        System.out.println();
    }

    /**
     * Prints out who wins based on the number of guesses. Also prints out the average stats of the user.
     * @param numberOfGuesses as number of guesses.
     * @param masterKey as the master sequence (the one being guessed).
     * @param digitsGuessedInCorrectSpot as the number of digits guessed in the correct spot.
     * @param digitsGuessedCorrectlyInWrongSpot as the number of digits guessed but in the wrong spot.
     */
    private static void printWinner(int numberOfGuesses, int[] masterKey, int digitsGuessedInCorrectSpot, int digitsGuessedCorrectlyInWrongSpot)
    {
        double[] stats = new double[2];

        if(numberOfGuesses == 0)
        {
            System.out.println("You ran out of guesses! Computer Wins!");
            System.err.print("The correct sequence was: ");
            printSequence(masterKey);
            stats = getAverageGuesses(digitsGuessedInCorrectSpot, digitsGuessedCorrectlyInWrongSpot, numberOfGuesses);
            System.out.printf("The average digits you guessed in the wrong spot per round was %2.2f \n", stats[1]);
            System.out.printf("and the average digits you guessed in the correct spot per round was %2.2f \n", stats[0]);
        }
        else
        {
            System.out.println("You guessed the right sequence! You Win!");
            stats = getAverageGuesses(digitsGuessedInCorrectSpot, digitsGuessedCorrectlyInWrongSpot, numberOfGuesses);
            System.out.printf("The average digits you guessed in the wrong spot per round was %2.2f \n", stats[1]);
            System.out.printf("and the average digits you guessed in the correct spot per round was %2.2f \n", stats[0]);
        }
    }

    /**
     * Calculates the average stats of the user.
     * @param digitsGuessedInCorrectSpot as the number of digits guessed in the correct spot.
     * @param digitsGuessedCorrectly as the number of digits guessed but in the wrong spot.
     * @param numberOfGuesses as number of guesses.
     * @return
     */
    private static double[] getAverageGuesses(double digitsGuessedInCorrectSpot, double digitsGuessedCorrectlyInWrongSpot, double numberOfGuesses)
    {
        double averageCorrectSpot = digitsGuessedInCorrectSpot/(12 - numberOfGuesses); 
        double averageGuessedCorrectly = digitsGuessedCorrectlyInWrongSpot/(12 - numberOfGuesses);
        double[] stats = {averageCorrectSpot, averageGuessedCorrectly};

        return stats;
    }
}
