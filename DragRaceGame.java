/*
 * Aarav Goyal
 * 4/23/2025
 * DragRaceGame.java
 * 
 *  - Add Card Layout, Null Layout, etc...
 *  - Add all the tracks and the cars and make the welcome page, intstructions page, game page, and the answers/explanation page.
 */

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;

import javax.swing.JSlider;
import javax.imageio.ImageIO;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DragRaceGame
{
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Drag Race!");
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 50);
		frame.setResizable(true);
		frame.getContentPane().add(new GameHolder());
		frame.setVisible(true);
	}
}
class GameHolder extends JPanel 
{
	public GameHolder() 
	{
		setLayout(new CardLayout());
		FirstPagePanel first = new FirstPagePanel(this);
		add(first, "First");
	}
}

class FirstPagePanel extends JPanel 
{
	private Image image;
	private Image car1;
    public FirstPagePanel(JPanel Game) 
    {
        setLayout(null);
        try 
        {
        	image = ImageIO.read(new File ("track.jpg"));
        }
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
    }
}


