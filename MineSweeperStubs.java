//Name______________________________ Date_____________
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MineSweeperStubs extends JPanel
{/***The gameboard of buttons.*/
   private JButton[][] board; 
   /***The panel that holds the board.*/
   private JPanel center;
   /***Tells whether the game is over or not.*/
   private boolean gameover;
   /***Decides whether colors are on or off.*/
   private boolean colorOn;
   /***The number of bombs hidden on the board.*/
   private int bombs;
   /***The number of square for one side of the square board.*/
   private int numitems;
   /***The matrix that stores the numbers.*/
   private int[][] matrix;
   /***The welcome message.*/
   private JLabel label;
   /***The reset button.*/
   private JButton reset;
   /***The "how to play" button.*/
   private JButton help;
   /***The button that enables and disables color.*/
   private JButton color;  
   public MineSweeper()
   {
      /**constructor; creates the board, layout,
         and all the buttons as well as a 
         matrix where the info is stored. */
         
      placeBombs();
      setNumbers();
   }
   private void placeBombs()
   {
      //randomly places 20 bombs on the board, 
      //and changes those corresponding positions
      //in the matrix to "9"    
      
      //called by the constructor and resetHandler.
   }
   private void setNumbers()
   {
      //assigns numbers to all the positions in
      //the matrix by searching through it for bombs,
      //and calls surroundBonb to put numbers in 
      //the surrounding postions in the matrix.
      
      surroundBomb();
   }
   private void surroundBomb(int r, int c)
   {
      /**puts numbers in the positions surrounding 
         the given (r, c).*/
         
      //called by setNumbers.
   }
   private class buttonHandler implements ActionListener
   {
      /**the listener for the buttons created in the
         constructor*/
      safe();
      number();
      bomb();
   }
   private void safe(int r, int c)
   {
      /**disables the button because it is now considered
         safe. Calls "surroundSafe" to also reveal the safe squares
         touching it.*/
         
      //called by buttonHandler.
         
      surroundSafe();
   }
   private void surroundSafe(int r, int c)
   {
      /**reveals the safe squares touching the given square
         at (r, c)*/
         
      //called by safe();
   }
   private void number(int r, int c)
   {
      /**reveals the number at the location (r, c). This number
         indicates the number of bombs that position is touching.*/
         
      //called by buttonHandler.
   }
   private void bomb(int r, int c)
   {
      /**reveals the bomb at the location (r, c). This means that
         the player has lost the game, and so all the other bombs 
         are also revealed.*/
         
      //called by buttonHandler.
   } 
   private class resetHandler implements ActionListener
   {
      /**listener activated by the reset button. It resets the board
         and once again calls placeBombs to make it as if a new
         construction occurred.*/
      
      placebombs();
   }
}