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
        frame.setSize(1500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
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
    private Image carBackground;

    public FirstPagePanel(JPanel Game) 
    {
    	
       startGIF();
    }
    
    public void startGIF()
    {
    	setLayout(null);
        setBackground(Color.WHITE);

        gifImage = new ImageIcon("Start.gif").getImage(); 
        
        carBackground = new ImageIcon("CarBackground.png").getImage();

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

    public void startFadeOut()
    {
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


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (alpha > 0 && gifImage != null) 
        {
        	int originalWidth = gifImage.getWidth(this);
        	int originalHeight = gifImage.getHeight(this);
        	double ratio = (double) originalWidth / originalHeight;
        	int finalWidth = (int) (700 * ratio);
        	int finalHeight = 700;
            int x  = (getWidth() - finalWidth)/2;
            int y = (getHeight() - finalHeight)/2;

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(gifImage, x, y, finalWidth, finalHeight, this); 
        }
        
        else
        {
        	int imgWidth = carBackground.getWidth(this);
            int imgHeight = carBackground.getHeight(this);
            int ratio = ((getWidth() - imgWidth)/2) / ((getHeight() - imgHeight)/2);
            int x  = (getWidth() - imgWidth)/2;
            int y = (getHeight() - imgHeight)/2;
            
        	Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.drawImage(gifImage, x, y, this); 
        }
    }

    public void showWelcomeScreen()
    {
        repaint();
        
        JLabel welcome = new JLabel("Welcome to Drag Race!");
        welcome.setFont(new Font("Arial", Font.BOLD, 36));
        welcome.setForeground(Color.BLUE);
        welcome.setBounds(100, 350, 600, 50);
        add(welcome, BorderLayout.NORTH);
        repaint();
        
        JButton startButton = new JButton ("Start");
        startButton.setBounds(330, 380, 80, 30);
        add(startButton);
        
        JButton highScores = new JButton ("High Scores");
        highScores.setBounds(310, 410, 120, 30);
        add(highScores);
        
        startButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
               System.out.println("start was clicked");
            }
        });
        
        highScores.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		System.out.println("highscores was clicked");
        	}
        });
    }
}
