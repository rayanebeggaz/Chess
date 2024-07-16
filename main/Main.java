package main;
import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame frame =new JFrame();
        frame.setLayout(new GridBagLayout());  
        frame.setMinimumSize(new Dimension(750,750));
        
        Board board = new Board();
        frame.add(board);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
    
}
