
import javax.swing.JFrame;
public class MineSweeperDriver
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("Final Project: Minesweeper!");
      frame.setSize(700, 700);
      frame.setLocation(200, 100);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setContentPane(new MineSweeper());
      frame.setVisible(true);
   }
}