//Name______________________________ Date_____________
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*******************************
This class is used for 
the classic game Minesweeper. 
*******************************/
public class MineSweeper extends JPanel
{
/***The gameboard of buttons.*/
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
   
   /**Creates the board, layout,
         and all the buttons as well as a 
         matrix where the info is stored. */
   public MineSweeper()
   {
      ////SET THE NUMBER OF BOMBS- changes with level
      bombs=60;
      
      //SET BOARD SIZE- changes with level in setBoard()
      numitems = 20;
      
      gameover = false;
      colorOn = true;
      
      String message = "Select a level.";
      message = message + "\n1. Easy";
      message = message + "\n2. Medium";
      message = message + "\n3. Hard";
      int level = Integer.parseInt(JOptionPane.showInputDialog(message));
      switch(level) {
         case 1: bombs = 10; numitems = 10; 
            break;
         case 2: bombs = 35; numitems = 15; 
            break;
         case 3: bombs = 60; numitems = 20; 
            break; }
      
      setLayout(new BorderLayout());
   
      JPanel north = new JPanel();
      north.setLayout(new FlowLayout());
      add(north, BorderLayout.NORTH);
      label = new JLabel("Welcome to Minesweeper. Avoid the "+bombs+" bombs!");
      north.add(label);
   
      center = new JPanel();
      center.setLayout(new GridLayout(numitems,numitems));
      add(center, BorderLayout.CENTER);
   
      JPanel bottom = new JPanel();
      bottom.setLayout(new GridLayout(1,3));
      add(bottom, BorderLayout.SOUTH);
      
      JPanel center = new JPanel();
      center.setLayout(new GridLayout(numitems,numitems));
      add(center, BorderLayout.CENTER);
      board = new JButton[numitems][numitems];
      matrix = new int[numitems][numitems];
   
      for(int r = 0; r < numitems; r++){
         for(int c = 0; c < numitems; c++)
         {
            board[r][c] = new JButton();
            board[r][c].setOpaque(true);
            board[r][c].setForeground(Color.red);
            board[r][c].setBackground(new Color(173, 173, 173));
            board[r][c].addActionListener( new buttonHandler(r, c) );
            center.add(board[r][c]);
            matrix[r][c] = 0;
               
            board[r][c].addMouseListener(new MouseAdapter(r, c));  //creates the right-click checker                         
         }
      }   
   
   
      reset = new JButton("Reset");
      reset.addActionListener( new resetHandler() );
      bottom.add(reset, BorderLayout.SOUTH);
      
      help = new JButton("How to play");
      help.addActionListener( new helpHandler() );
      bottom.add(help, BorderLayout.SOUTH);
   
      color = new JButton("Color on/off");
      color.addActionListener( new colorHandler() );
      bottom.add(color, BorderLayout.SOUTH);
      
      placeBombs(bombs);
      setNumbers();
      colorNumbers(colorOn,0,0);
   }

/*******************************
Allows right-clicks to flag squares. 
*******************************/
   private class MouseAdapter implements MouseListener
   {
      private int r, c;
            
      public MouseAdapter(int row, int col)
      {
         r = row;
         c = col;
      }
   
      public void mouseClicked(MouseEvent e)
      {
         if(SwingUtilities.isRightMouseButton(e)){//if the right button was pressed       
            if(board[r][c].getText().equals("X")){
               board[r][c].setLabel("");
            }  
            else{
               if(board[r][c].isEnabled()==true)
                  board[r][c].setLabel("X");
               if(board[r][c].isEnabled()==false){
               }
            }
         
         }
      }
      public void mouseExited(MouseEvent e){}//these are all necessary to implement mouseClicked
      public void mouseEntered(MouseEvent e){}//because it is abstract
      public void mouseReleased(MouseEvent e){}
      public void mousePressed(MouseEvent e){}
   
   }
    
                  
   /*******************************
Randomly places bombs- number specified by the private int bombs.
*******************************/
   private void placeBombs(int bombs)
   {
      int count = 0;
      boolean used;
      int row=0;
      int col=0;
      for (int k = 0; k < bombs; k++)
      {
         do{
            row = (int)(Math.random()*(numitems-1));
            col = (int)(Math.random()*(numitems-1));
            used = false;
            if(matrix[row][col] == 9)
               used = true;
               
         }while(used == true);
         matrix[row][col]=9;
         count++;
      }
   
      
         
   }
   
   /*******************************
Each bomb calls surroundBomb().
*******************************/
   private void setNumbers()
   {
      int r=0;
      int c=0;
      for (r = 0; r < numitems; r++)
         for (c = 0; c < numitems; c++)
            if (matrix[r][c]>=9){
               surroundBomb(r, c);
            
            }
     /* for (r = 0; r < numitems; r++)
         for (c = 0; c < numitems; c++)
            board[r][c].setText(""+matrix[r][c]);  */ 
   }
   /*******************************
Each square records the number of bombs it is touching in a matrix. 
*******************************/
   private void surroundBomb(int r, int c)
   {
      if (r > 0)
      {
         matrix[r-1][c]++;
         if (c < numitems)
            matrix[r-1][c+1]++;
         if (c > 0)
            matrix[r-1][c-1]++;
      }
      if (r < numitems)
      {  
         matrix[r+1][c]++;
         if (c < numitems)
            matrix[r+1][c+1]++;
         if (c > 0)
            matrix[r+1][c-1]++;
      }
      if (c < numitems)
         matrix[r][c+1]++;
      if (c > 0)
         matrix[r][c-1]++;
   }
   /*******************************
Changes the color of each numbered square if colorOn is true.
*******************************/
   private void colorNumbers(boolean colorOn, int a, int b){
      for (int r = 0; r < numitems; r++){
         for (int c = 0; c < numitems; c++){
            if(gameover==false){
               if(colorOn == true){
                  board[r][c].setForeground(Color.red);
                  if(board[r][c].isEnabled()==false){
                     int value = matrix[r][c];
                     switch(value){
                        case 1: board[r][c].setBackground(new Color(163,178,201));
                           break;
                        case 2: board[r][c].setBackground(new Color(176,199,173));
                           break;
                        case 3: board[r][c].setBackground(new Color(200,164,164));
                           break;
                        case 4: board[r][c].setBackground(new Color(187,177,201));
                           break;
                        case 5: board[r][c].setBackground(new Color(161,129,145));
                           break;
                        case 6: board[r][c].setBackground(new Color(199,199,153));
                           break;
                        case 7: board[r][c].setBackground(new Color(203,177,204));
                           break;
                        case 8: board[r][c].setBackground(new Color(167,201,199));
                           break;
                        default: board[r][c].setBackground(new Color(173,173,173));
                     }
                  }
               }
            }
            if(colorOn == false){
               board[r][c].setBackground(new Color(173,173,173));
               board[r][c].setForeground(Color.black);
            
            }
            if(gameover==true){
               //board[r][c].setBackground(new Color(173,173,173));
               board[r][c].setEnabled(false);
               board[r][c].setBackground(new Color(173,173,173));
               if(board[r][c].getText().equals("X") && matrix[r][c] < 9){
                  board[r][c].setBackground(new Color(255,0,255));
               
               }
               if(matrix[r][c]>=9){
                  board[r][c].setLabel("X");
                  board[r][c].setOpaque(true);
                  board[r][c].setBackground(Color.red);
               }
               board[a][b].setOpaque(true);
               board[a][b].setForeground(Color.white);
               board[a][b].setBackground(Color.black);
               label.setText("Game over. Play again?");
            }
         }
      }
   }
   /*******************************
Decides what to do based on the value of the square that is clicked.
Determines if the player has won the game yet.
*******************************/
   private class buttonHandler implements ActionListener
   {
      
      int piecesLeft =0;
      private int myRow, myCol;
      public buttonHandler(int r, int c)
      {
         myRow = r;
         myCol = c;
      }
      public void actionPerformed(ActionEvent e)
      {
         if(board[myRow][myCol].getText().equals("X"))//you can't click it if its flagged
         {
         
         }
         else{//otherwise you can
            int piecesLeft = 0;
         
            switch(matrix[myRow][myCol])
            {
               case 0: safe(myRow, myCol); 
               
                  break;
               case 1:
                     case 2:
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                     case 7:
                     case 8: number(myRow, myCol);
                  colorNumbers(colorOn,0,0); 
                  break;
               default: bomb(myRow, myCol); 
                  
            }
            for (int r = 0; r < numitems; r++){
               for (int c = 0; c < numitems; c++){
                  if(board[r][c].isEnabled()==false){
                     piecesLeft++;
                  }
               }
            }
            if((numitems*numitems)-piecesLeft == bombs){
               win();
            } 
         } 
      } 
   }
   /*******************************
   Allows safe squares to reveal all other safe squares touching the one that was clicked, along with
   numbers surrounding them.
*******************************/
   private void safe(int r, int c)
   {
      matrix[r][c] = -1;
      board[r][c].setLabel("");
      board[r][c].setEnabled(false);
      
      if((r-1)>=0 && (c-1)>=0){
         if(board[r-1][c-1].getText().equals("X")){
         
         }
         else{
            if(matrix[r-1][c-1]==0){          
               safe(r-1,c-1);
            }
            else{
               board[r-1][c-1].setEnabled(false);
               board[r-1][c-1].setLabel(""+matrix[r-1][c-1]);
            
            }
         } 
      
      }
      if((r-1)>=0 && (c)>=0){
         if(board[r-1][c].getText().equals("X")){
         
         }
         else{
         
            if(matrix[r-1][c]==0){          
               safe(r-1,c);
                 
            }
            else{
               board[r-1][c].setEnabled(false);
               board[r-1][c].setLabel(""+matrix[r-1][c]);
            }
         } 
          
      }
      if((r-1)>=0 && (c+1)<numitems){
         if(board[r-1][c+1].getText().equals("X")){
         
         }
         else{
         
            if(matrix[r-1][c+1]==0){          
               safe(r-1,c+1);
              
            } 
            else{
               board[r-1][c+1].setEnabled(false);
               board[r-1][c+1].setLabel(""+matrix[r-1][c+1]);
            
            }    
         }        
      }
      if((r)>=0 && (c-1)>=0){
         if(board[r][c-1].getText().equals("X")){
         
         }
         else{
         
            if(matrix[r][c-1]==0){          
               safe(r,c-1);
            
            } 
            else{
               board[r][c-1].setEnabled(false);
               board[r][c-1].setLabel(""+matrix[r][c-1]);
            } 
         }   
                  
      }
      if((r)>=0 && (c+1)<numitems){
         if(board[r][c+1].getText().equals("X")){
         
         }
         else{
         
            if(matrix[r][c+1]==0){          
               safe(r,c+1); 
                      
            }  
            else{
               board[r][c+1].setEnabled(false);
               board[r][c+1].setLabel(""+matrix[r][c+1]);
             
            }  
         }       
      }
      if((r+1)<numitems && (c-1)>=0){
         if(board[r+1][c-1].getText().equals("X")){
         
         }
         else{
         
            if(matrix[r+1][c-1]==0){          
               safe(r+1,c-1); 
                  
            }
            else{
               board[r+1][c-1].setEnabled(false);
               board[r+1][c-1].setLabel(""+matrix[r+1][c-1]);
             
            }
         }
      }
         
      if((r+1)<numitems && (c+1)<numitems){
         if(board[r+1][c+1].getText().equals("X")){
         
         }
         else{
         
            if(matrix[r+1][c+1]==0){          
               safe(r+1,c+1); 
                     
            }   
            else{
               board[r+1][c+1].setEnabled(false);
               board[r+1][c+1].setLabel(""+matrix[r+1][c+1]);
             
            }  
         }     
      }
           
      if((r+1)<numitems && (c)>=0){
         if(board[r+1][c].getText().equals("X")){
         
         }
         else{
         
            if(matrix[r+1][c]==0){          
               safe(r+1,c);
                    
            }
            else{
               board[r+1][c].setEnabled(false);
               board[r+1][c].setLabel(""+matrix[r+1][c]);
            
            }
         }    
      }
      colorNumbers(colorOn,0,0);
      for (int a = 0; a < numitems; a++){
         for (int b = 0; b < numitems; b++){
            if(matrix[a][b]==-1){
               board[a][b].setLabel("");
            }
         }
      }
           
   }
   /*******************************
Handles the case when a number square is clicked.
*******************************/
   private void number(int r, int c)
   {
      board[r][c].setText("" + matrix[r][c]);
      board[r][c].setEnabled(false);
   }
   /*******************************
Handles the case where a bomb is clicked. Calls colorNumbers(). 
*******************************/
   private void bomb(int a, int b)
   {  
      gameover = true;
      colorNumbers(true, a, b);
   }
   /*******************************
The actions for when the player wins the game. 
*******************************/
   private void win()
   {
      for (int r = 0; r < numitems; r++){
         for (int c = 0; c < numitems; c++){
           
            board[r][c].setEnabled(false);
            if(matrix[r][c]>=9){
               board[r][c].setLabel("X");
               board[r][c].setOpaque(true);
               board[r][c].setBackground(Color.yellow);
            }
         }
      }
      label.setText("You won!. Play again?");
   
   }
   /*******************************
The reset button resets the gameboard.
*******************************/
   private class resetHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         for (int k = 0; k < numitems; k++)
            for (int i = 0; i < numitems; i++)
            {
               matrix[k][i] = 0;
               board[k][i].setLabel("");
               board[k][i].setBackground(new Color(173, 173, 173));
               board[k][i].setForeground(Color.black);
               board[k][i].setEnabled(true);
               label.setText("Welcome to Minesweeper. Avoid the "+bombs+" bombs!");
            }
         //piecesLeft = 400;
         placeBombs(bombs);
         setNumbers();
         gameover = false;
         colorNumbers(colorOn,0,0);
      }
   }
   /*******************************
The how-to-play button displays a help menu. 
*******************************/
   private class helpHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         JOptionPane.showMessageDialog(null,"Objective:"+"\n \t"+"Find the empty squares and avoid the mines." 
            +"\n"+"How to play:"+"\n \t"+"If you click a square with a mine, you lose."+"\n \t"+"If you click an empty square, all the empty"+"\n \t \t"
            +"squares touching it disappear." +"\n \t"+"If you click a number, it tells you how many"+"\n \t \t"+"bombs are touching that square."
            +"\n \t"+"Right-click a square to flag it.");
      }
   }
   /*******************************
The "color on/off" button toggles colors on and off.
*******************************/
   private class colorHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         if(gameover == true){
         }
         else{
            if(colorOn==true)
               colorOn = false;
            else
               colorOn = true;
            colorNumbers(colorOn,0,0);
         }
      }
   }
}