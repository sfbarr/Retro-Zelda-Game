package main;

//import javax.swing.*;
//import java.awt.*;
//import java.util.Arrays;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.setLocation(241, 0);

        window.add(gamePanel);
        window.pack();

        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        g.getCenterPoint();

//        window.setLocationRelativeTo(g);
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();

        System.out.println(Arrays.toString(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        System.out.println(window.getLocationOnScreen());
    }
}