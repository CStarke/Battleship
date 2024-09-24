import java.util.*;

public class Battleship
{
    // Grids for user and enemy ship placements and guesses
    char[][] userShipGrid = new char[10][10];
    char[][] userGuessedGrid = new char[10][10];
    char[][] enemyShipGrid = new char[10][10];
    char[][] enemyGuessedGrid = new char[10][10];

    // Variables for tracking game state
    int row;
    int col;
    int dir;
    int lastHitRow;
    int lastHitCol;
    int lastHitDir;
    int orientation;
    int turnNum = 0;

    Random random = new Random();
    Scanner scanner = new Scanner(System.in);

    // Main game loop
    public void run()
    {
        // Clear console and initialize grids
        System.out.print("\033[H\033[2J");
        setStartGrid(userShipGrid);
        setStartGrid(userGuessedGrid);
        setStartGrid(enemyShipGrid);
        setStartGrid(enemyGuessedGrid);

        // Welcome message
        System.out.println("================================");
        System.out.println("     Welcome to Battleship      ");
        System.out.println("================================");
        System.out.println("Time to place your ships.");

        // Prompt user to place ships of varying lengths
        placeShipClick();
        placeShip(2);
        placeShipClick();
        placeShip(3);
        placeShipClick();
        placeShip(3);
        placeShipClick();
        placeShip(4);
        placeShipClick();
        placeShip(5);

        // Clear console and show user's current ship grid
        System.out.print("\033[H\033[2J");
        System.out.println("Your current grid of ships.");
        printGrid(userShipGrid);

        // Allow enemy to place their ships
        enemyChooseClick();
        enemyPlaceShips();
        System.out.println("The enemy has placed their ships.");

        // Start the guessing phase
        startGuessing();
        while(true)
        {
            // Check if the user has won
            if(userTurn() == true)
            {
                System.out.print("\033[H\033[2J");
                printGrid(enemyGuessedGrid);
                System.out.println("Congratulations! You Win!");
                System.out.println("      (_v_)");
                System.out.println("       _|_");
                System.out.println("       | |");
                System.out.println("  |-----+-----|");
                System.out.println("  |    YOU    |");
                System.out.println("  |    WIN    |");
                System.out.println("   '---------'");
                System.out.println("    |       /");
                System.out.println("     '.   .'");
                System.out.println("       | |");
                System.out.println("      .' '.");
                System.out.println("     _|___|_");
                System.out.println("    [#######]");
                System.out.println("   On turn " + turnNum);
                break;
            }

            // Check if the enemy has won
            if(enemyTurn() == true)
            {
                System.out.print("\033[H\033[2J");
                printGrid(userGuessedGrid);
                System.out.println("The enemy has defeated all of your ships.");
                System.out.println(" You lost on turn " + turnNum);
                break;
            }
        }
    }
    
    // Prompt user to hit enter to place the next ship
    public void placeShipClick()
    {
        System.out.println("Hit enter to place the next ship.");
        scanner.nextLine();
        System.out.print("\033[H\033[2J");
    }
    
    // Prompt user to take their turn
    public void takeYourTurn()
    {
        System.out.println("Hit enter for your turn.");
        scanner.nextLine();
        System.out.print("\033[H\033[2J");
    }
    
    // Prompt user to let the computer take its turn
    public void enemyTurnClick()
    {
        System.out.println("Hit enter for the computer turn.");
        scanner.nextLine();
        System.out.print("\033[H\033[2J");
    }

    public void startGuessing()
    {
        // Prompt the user to start guessing and wait for input
        System.out.println("Hit enter to start guessing.");
        scanner.nextLine();
    }

    public void enemyChooseClick()
    {
        // Prompt the user to allow the enemy to choose their ship locations
        System.out.println("Hit enter for the enemy to");
        System.out.println("choose their ship locations.");
        scanner.nextLine();
    }

    public void setStartGrid(char grid[][])
    {
        // Initialize the grid with '-' to indicate empty spaces
        for(int row = 0; row < 10; row++)
        {
            for(int col = 0; col < 10; col++)
            {
                grid[row][col] = '-';
            }
        }
    }

    public void printGrid(char grid[][])
    {
        // Print the grid to the console with row and column headers
        System.out.print(" ");
        for(int i = 0; i < 10; i++)
        {
            System.out.print(" " + (i + 1));
        }
        System.out.println();
        for(int row = 0; row < 10; row++)
        {
            System.out.print((char) (row + 65));
            for(int col = 0; col < 10; col++)
            {
                System.out.print(" " + grid[row][col]);
            }
            System.out.println();
        }
    }

    public void enemyPlaceShips()
    {
        // Define ship lengths for the enemy
        int[] shipPlaces = {2, 3, 3, 4, 5};
        int length;
        int row;
        int col;
        boolean dirBoolean;
        char dir;

        // Loop through each ship to place
        for(int j = 0; j < 5; j++)
        {
            while(true)
            {
                length = shipPlaces[j];
                row = random.nextInt(10);
                col = random.nextInt(10);
                dirBoolean = random.nextBoolean();

                // Set direction based on random boolean
                if(dirBoolean == true)
                {
                    dir = 'H';
                }
                else
                {
                    dir = 'V';
                }

                // Check if the ship can be placed
                if(checkShip(enemyShipGrid, length, row, col, dir) == true)
                {
                    break;
                }
            }

            // Place the ship on the grid based on the chosen direction
            if(dir == 'H') // Horizontal placement
            {
                for(int i = 0; i < length; i++)
                {
                    enemyShipGrid[row][col] = 'X';
                    col++;
                }
            }
            else // Vertical placement
            {
                for(int i = 0; i < length; i++)
                {
                    enemyShipGrid[row][col] = 'X';
                    row++;
                }
            }
        }
    }

    public void placeShip(int length)
    {
        // Display the user's current grid of ships
        System.out.println("Your current grid of ships.");
        printGrid(userShipGrid);
    
        // Prompt the user to place a ship of specified length
        System.out.println("Now you need to place a ship of length " + length);
        String rowInput;
        char rowChar;
        int rowAsNumber = -10;
    
        // Loop until a valid ship placement is made
        while(true)
        {
            // Input loop for the row selection
            while(true)
            {
                System.out.print("Which row? (A-J) ");
                rowInput = scanner.nextLine();
                if(rowInput.length() > 0) // Check if input is not empty
                {
                    rowChar = Character.toUpperCase(rowInput.charAt(0));
                    rowAsNumber = (int) rowChar - 65;
                }
                // Validate the row number
                if(rowAsNumber > -1 && rowAsNumber < 10)
                {
                    row = rowAsNumber;
                    break;
                }
                System.out.println("Invalid row. Please try again. ");
            }
    
            String colInput;
            int colAsNumber = -10;
    
            // Input loop for the column selection
            while(true)
            {
                System.out.print("Which column? (1-10) ");
                colInput = scanner.nextLine();
                if(colInput.length() > 0) // Check if input is not empty
                {
                    try
                    {
                        colAsNumber = Integer.parseInt(colInput) - 1;
                    }
                    catch (NumberFormatException e)
                    {
                        colAsNumber = -10;
                    }
                }
                // Validate the column number
                if(colAsNumber > -1 && colAsNumber < 10)
                {
                    col = colAsNumber;
                    break;
                }
                System.out.println("Invalid column. Please try again. ");
            }
    
            String dirInput;
            char dirChar;
    
            // Input loop for the direction selection
            while(true)
            {
                System.out.print("Horizontal or Vertical? ");
                dirInput = scanner.nextLine();
                dirChar = 'U';
                if(dirInput.length() > 0) // Check if input is not empty
                {
                    dirChar = Character.toUpperCase(dirInput.charAt(0));
                }
                // Validate the direction
                if(dirChar == 'H' || dirChar == 'V')
                {
                    dir = dirChar;
                    break;
                }
                System.out.println("Invalid direction. Please try again. ");
            }
    
            // Check if the ship can be placed at the selected location
            if(checkShip(userShipGrid, length, row, col, dirChar) == true)
            {
                break;
            }
            System.out.println("Invalid ship placement. Please try again.");
        }
    
        // Place the ship on the grid based on the chosen direction
        if(dir == 'H') // Horizontal placement
        {
            for(int i = 0; i < length; i++)
            {
                userShipGrid[row][col] = 'X';
                col++;
            }
        }
        else // Vertical placement
        {
            for(int i = 0; i < length; i++)
            {
                userShipGrid[row][col] = 'X';
                row++;
            }
        }
    }

    public boolean checkShip(char grid[][], int length, int row, int col, char dir)
    {
        // Check if the ship can be placed horizontally
        if(dir == 'H')
        {
            for(int i = 0; i < length; i++)
            {
                // Check if the column is out of bounds
                if(col > 9 || col < 0)
                {
                    return false;
                }
                // Check if the grid cell is already occupied
                if(grid[row][col] != '-')
                {
                    return false;
                }
                col++;
            }
        }
        else // Check if the ship can be placed vertically
        {
            for(int i = 0; i < length; i++)
            {
                // Check if the row is out of bounds
                if(row > 9 || row < 0)
                {
                    return false;
                }
                // Check if the grid cell is already occupied
                if(grid[row][col] != '-')
                {
                    return false;
                }
                row++;
            }
        }
        return true;
    }

    public boolean userTurn()
    {
        takeYourTurn();
        System.out.println("      Enemy Grid");
        printGrid(enemyGuessedGrid);
        System.out.println("It is your turn to guess.");
        String rowInput;
        char rowChar;
        int rowAsNumber = -10;

        // Input loop for the row selection
        while(true)
        {
            System.out.print("Which row? (A-J) ");
            rowInput = scanner.nextLine();
            if(rowInput.length() > 0) // Check if input is not empty
            {
                rowChar = Character.toUpperCase(rowInput.charAt(0));
                rowAsNumber = (int) rowChar - 65;
            }
            // Validate the row number
            if(rowAsNumber > -1 && rowAsNumber < 10)
            {
                row = rowAsNumber;
                break;
            }
            System.out.println("Invalid row. Please try again. ");
        }

        String colInput;
        int colAsNumber = -10;

        // Input loop for the column selection
        while(true)
        {
            System.out.print("Which column? (1-10) ");
            colInput = scanner.nextLine();
            if(colInput.length() > 0) // Check if input is not empty
            {
                try
                {
                    colAsNumber = Integer.parseInt(colInput) - 1;
                }
                catch (NumberFormatException e)
                {
                    colAsNumber = -10;
                }
            }
            // Validate the column number
            if(colAsNumber > -1 && colAsNumber < 10)
            {
                col = colAsNumber;
                break;
            }
            System.out.println("Invalid column. Please try again. ");
        }

        // Check if the guessed location hits an enemy ship
        if(enemyShipGrid[row][col] == 'X')
        {
            System.out.print("\033[H\033[2J");
            System.out.println("   ------Hit!------");
            enemyGuessedGrid[row][col] = 'X';
            printGrid(enemyGuessedGrid);
        }
        else
        {
            System.out.print("\033[H\033[2J");
            System.out.println("   ------Miss------");
            enemyGuessedGrid[row][col] = 'O';
            printGrid(enemyGuessedGrid);
        }

        // Count hits on the enemy's ships
        int num = 0;
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10;j++)
            {
                // Check for hits
                if(enemyGuessedGrid[i][j] == 'X')
                {
                    num++;
                }
                // Check if all enemy ships have been hit
                if(num == 17)
                {
                    turnNum += 1;
                    return true;
                }
            }
        }
        turnNum += 1;
        return false;
    }

    public boolean guess(int row, int col)
    {
        // Convert zero-based indices to one-based for display purposes
        char rowShot = ' ';
        switch(row) {
            case 0:
                rowShot = 'A';
                break;
            case 1:
                rowShot = 'B';
                break;
            case 2:
                rowShot = 'C';
                break;
            case 3:
                rowShot = 'D';
                break;
            case 4:
                rowShot = 'E';
                break;
            case 5:
                rowShot = 'F';
                break;
            case 6:
                rowShot = 'G';
                break;
            case 7:
                rowShot = 'H';
                break;
            case 8:
                rowShot = 'I';
                break;
            case 9:
                rowShot = 'J';
                break;
        }
        int colShot = col + 1;

        // Check if the guess hits a user's ship
        System.out.println("   Computer shot at " + rowShot + colShot);
        if(userShipGrid[row][col] == 'X')
        {
            System.out.println("     Computer hit");
            userGuessedGrid[row][col] = 'X';
            lastHitRow = row;
            lastHitCol = col;
            return true;
        }
        else
        {
            System.out.println("     Computer missed");
            userGuessedGrid[row][col] = 'O';
            lastHitDir = '\0';
            return false;
        }
    }

    public boolean enemyTurn()
    {
        enemyTurnClick();
        smartGuess();
        printGrid(userGuessedGrid);
        int bnum = 0;
        // Count the number of hits in the guessed grid
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(userGuessedGrid[i][j] == 'X')
                {
                    bnum++;
                }
                // Check if all ships have been hit
                if(bnum == 17)
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void randomGuess()
    {
        int row;
        int col;
        // Generate random guesses until an unguessed location is found
        while(true)
        {
            row = random.nextInt(10);
            col = random.nextInt(10);
            // Check if the guessed cell is unguessed
            if(userGuessedGrid[row][col] == '-')
            {
                guess(row, col);
                break;
            }
        }
    }

    public void smartGuess()
    {
        // Try to make a guess based on the last hit direction
        if(guessLastDir())
        {
            return;
        }
        // Try to guess based on the orientation of the last hit
        if(guessOrientation())
        {
            return;
        }
        lastHitDir = '\0';
        orientation = '\0';
        // Attempt to guess around the last hit
        if(guessAround())
        {
            return;
        }
        randomGuess();
    }

    public boolean guessAround()
    {
        // Ensure there was a valid last hit to guess around
        if(lastHitRow < 0 || lastHitCol < 0)
        {
            return false;
        }
        // Attempt to guess in all four directions around the last hit
        if(guessAbove())
        {
            return true;
        }
        if(guessBelow())
        {
            return true;
        }
        if(guessRight())
        {
            return true;
        }
        if(guessLeft())
        {
            return true;
        }
        return false;
    }

    public boolean guessLastDir()
    {
        // Check the last hit direction to determine the next guess
        if(lastHitDir == 1) // Last hit was above
        {
            orientation = 3;
            return guessAbove();
        }
        else if(lastHitDir == 2) // Last hit was to the right
        {
            orientation = 4;
            return guessRight();
        }
        else if(lastHitDir == 3) // Last hit was below
        {
            orientation = 1;
            return guessBelow();
        }
        else if(lastHitDir == 4) // Last hit was to the left
        {
            orientation = 2;
            return guessLeft();
        }
        return false;
    }

    public boolean guessOrientation()
    {
        int i;
        char check;
        boolean result = false;

        // Check orientation and make guesses based on previous hits
        if(orientation == 1) // Upwards
        {
            i = lastHitRow;
            while(true && i >= 0) // Continue while within bounds
            {
                check = userGuessedGrid[i][lastHitCol];
                if(check == 'X') // If hit already recorded
                {
                    i--;
                }
                else if(check == 'O') // If missed previously
                {
                    lastHitRow = -1;
                    lastHitCol = -1;
                    return false;
                }
                else if(check == '-') // If the cell is unguessed
                {
                    result = true;
                    // Check the user's ship grid to make a guess
                    if(guess(i, lastHitCol))
                    {
                        lastHitDir = 1;
                        break;
                    }
                    else
                    {
                        lastHitRow = -1;
                        lastHitCol = -1;
                        orientation = '\0';
                        lastHitDir = '\0';
                        break;
                    }
                }
            }
        }
        else if(orientation == 2) // Rightwards
        {
            i = lastHitCol;
            while(true && i < 10) // Continue while within bounds
            {
                check = userGuessedGrid[lastHitRow][i];
                if(check == 'X') // If hit already recorded
                {
                    i++;
                }
                else if(check == 'O') // If missed previously
                {
                    lastHitRow = -1;
                    lastHitCol = -1;
                    return false;
                }
                else if(check == '-') // If the cell is unguessed
                {
                    result = true;
                    // Check the user's ship grid to make a guess
                    if(guess(lastHitRow, i))
                    {
                        lastHitDir = 2;
                        break;
                    }
                    else
                    {
                        lastHitRow = -1;
                        lastHitCol = -1;
                        orientation = '\0';
                        lastHitDir = '\0';
                        break;
                    }
                }
            }
        }
        else if(orientation == 3) // Downwards
        {
            i = lastHitRow;
            while(true && i < 10) // Continue while within bounds
            {
                check = userGuessedGrid[i][lastHitCol];
                if(check == 'X') // If hit already recorded
                {
                    i++;
                }
                else if(check == 'O') // If missed previously
                {
                    lastHitRow = -1;
                    lastHitCol = -1;
                    return false;
                }
                else if(check == '-') // If the cell is unguessed
                {
                    result = true;
                    // Check the user's ship grid to make a guess
                    if(guess(i, lastHitCol))
                    {
                        lastHitDir = 3;
                        break;
                    }
                    else
                    {
                        lastHitRow = -1;
                        lastHitCol = -1;
                        orientation = '\0';
                        lastHitDir = '\0';
                        break;
                    }
                }
            }
        }
        else if(orientation == 4)// Leftwards
        {
            i = lastHitCol;
            while(true) // Continue until a guess is made
            {
                check = userGuessedGrid[lastHitRow][i];
                if(check == 'X') // If hit already recorded
                {
                    i--;
                }
                else if(check == 'O') // If missed previously
                {
                    lastHitRow = -1;
                    lastHitCol = -1;
                    return false;
                }
                else if(check == '-') // If the cell is unguessed
                {
                    result = true;
                    // Check the user's ship grid to make a guess
                    if(guess(lastHitRow, i))
                    {
                        lastHitDir = 4;
                        break;
                    }
                    else
                    {
                        lastHitRow = -1;
                        lastHitCol = -1;
                        orientation = '\0';
                        lastHitDir = '\0';
                        break;
                    }
                }
            }
        }
        return result;
    }

    public boolean guessAbove()
    {
        // Check if the last hit row is at the top edge of the grid
        if(lastHitRow == 0)
        {
            return false;
        }
        char targetCheck = userGuessedGrid[lastHitRow-1][lastHitCol];

        // If the cell above is already guessed (hit or miss), return false
        if(targetCheck == 'O' || targetCheck == 'X')
        {
            return false;
        }
        // Make a guess for the cell above
        if(guess(lastHitRow-1, lastHitCol))
        {
            lastHitDir = 1;
        }
        return true;
    }

    public boolean guessBelow()
    {
        // Check if the last hit row is at the bottom edge of the grid
        if(lastHitRow == 9)
        {
            return false;
        }
        char targetCheck = userGuessedGrid[lastHitRow+1][lastHitCol];

        // If the cell below is already guessed (hit or miss), return false
        if(targetCheck == 'O' || targetCheck == 'X')
        {
            return false;
        }
        // Make a guess for the cell below
        if(guess(lastHitRow+1, lastHitCol))
        {
            lastHitDir = 3;
        }
        return true;
    }

    public boolean guessRight()
    {
        // Check if the last hit column is at the right edge of the grid
        if(lastHitCol == 9)
        {
            return false;
        }
        char targetCheck = userGuessedGrid[lastHitRow][lastHitCol+1];

        // If the cell to the right is already guessed (hit or miss), return false
        if(targetCheck == 'O' || targetCheck == 'X')
        {
            return false;
        }
        // Make a guess for the cell to the right
        if(guess(lastHitRow, lastHitCol+1))
        {
            lastHitDir = 2;
        }
        return true;
    }

    public boolean guessLeft()
    {
        // Check if the last hit column is at the left edge of the grid
        if(lastHitCol == 0)
        {
            return false;
        }
        char targetCheck = userGuessedGrid[lastHitRow][lastHitCol-1];

        // If the cell to the left is already guessed (hit or miss), return false
        if(targetCheck == 'O' || targetCheck == 'X')
        {
            return false;
        }
        // Make a guess for the cell to the left
        if(guess(lastHitRow, lastHitCol-1))
        {
            lastHitDir = 4;
        }
        return true;
    }

    // Main method to run the Battleship game
    public static void main(String[] args) {
        new Battleship().run();
    }
}