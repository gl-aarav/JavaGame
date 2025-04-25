/*
 * Aarav Goyal
 * 4/23/2025
 * DragRaceGame.java
 * 
 * Week 1
 * 	- Add intro GIF and fade out animation
 * 	- Create welcome page
 * Week 2
 * 	- Create the instructions panel 
 *  - Create select car panel
 * Week 3
 *  - Start on working to import all the files 
 *  - Create basic functioning buttons
 * Week 4
 *  - Start the game panel
 *  - create the text file for the questions
 * Week 5
 *  - Finish the game panel
 *  - Create the learning panel
 * Week 6
 *  - Finishing touches
 */ 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    private float alpha = 1.0f; // Transparency value for fade
    private Timer timer;
    private Image gifImage;

    public FirstPagePanel(JPanel Game) 
    {
        setLayout(null);
        setBackground(Color.BLACK);

        gifImage = new ImageIcon("Start.gif").getImage(); // Load image

        // Start the fade after a delay (7 seconds)
        Timer delay = new Timer(7000, new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                startFadeOut();
            }
        });
        delay.setRepeats(false);
        delay.start();
    }

    private void startFadeOut() {
        timer = new Timer(100, new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                alpha -= 0.05f; // Decrease alpha
                if (alpha <= 0) 
                {
                    alpha = 0;
                    timer.stop();
                    showWelcomeScreen();
                }
                repaint(); // Redraw with updated transparency
            }
        });
        timer.start();
    }


    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (alpha > 0 && gifImage != null) 
        {
            int imgWidth = gifImage.getWidth(this);
            int imgHeight = gifImage.getHeight(this);
            int x = (getWidth() - imgWidth) / 2;
            int y = (getHeight() - imgHeight) / 2;

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(gifImage, x, y, this); // Draw centered at original size
            g2d.dispose();
        }
    }

    private void showWelcomeScreen()
    {
        removeAll(); // Remove everything from panel
        repaint();

        JButton start = new JButton ("Start");
        start.setBounds(700, 720, 80, 30);
        add(start);
        
        JLabel welcome = new JLabel("Welcome to Drag Race!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 36));
        welcome.setForeground(Color.BLUE);
        welcome.setBounds(100, 350, 600, 50);
        add(welcome);
        repaint();
    }
}
