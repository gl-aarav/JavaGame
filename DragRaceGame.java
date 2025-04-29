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

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class DragRaceGame 
{
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Drag Race!");
		frame.setSize(933, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
		frame.setResizable(true);
		frame.getContentPane().add(new GameHolder());
		frame.setVisible(true);
	}
}

class GameHolder extends JPanel 
{
	CardLayout layout;

	public GameHolder() 
	{
		layout = new CardLayout();
		setLayout(layout);

		WelcomePagePanel welcomePanel = new WelcomePagePanel(this, layout);

		InstructionPanel instructionsPanel = new InstructionPanel(this, layout);

		HighScorePanel highScoresPanel = new HighScorePanel(this, layout);

		CarChoosePanel carChoose = new CarChoosePanel(this,layout);

		add(welcomePanel, "Welcome");
		add(instructionsPanel, "Instructions");
		add(highScoresPanel, "HighScores");
		add(carChoose, "ChooseCar");
	}
}


class WelcomePagePanel extends JPanel implements MouseListener, MouseMotionListener
{
	private JPanel parent;
	private CardLayout layout;
	private float alpha = 1.0f; 
	private Timer timer;
	private Image gifImage;
	private boolean gifOrNo;
	private Image carBackground;

	private boolean leftButtonPressed;
	private boolean rightButtonPressed;
	private boolean leftButtonHovered;
	private boolean rightButtonHovered;
	public WelcomePagePanel(JPanel gameHolder, CardLayout layout) 
	{
		leftButtonPressed = false;
		rightButtonPressed = false;
		leftButtonHovered = false;
		rightButtonHovered = false;
		repaint();
		this.parent = gameHolder;
		this.layout = layout;
		addMouseListener(this);
		addMouseMotionListener(this);
		startGIF();
		gifOrNo = true;
	}

	public void startGIF()
	{
		setLayout(null);
		setBackground(Color.BLACK);

		gifImage = new ImageIcon("Start.gif").getImage(); 
		try 
		{
			carBackground = ImageIO.read(new File("CarBackground.jpeg"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

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
				alpha -= 0.05f; 
				if (alpha <= 0) 
				{
					alpha = 0;
					timer.stop();
					gifOrNo = false;
				}
				repaint(); 
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
			int finalHeight = 772;
			int x  = (getWidth() - finalWidth) / 2;
			int y = (getHeight() - finalHeight) / 2;
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2d.drawImage(gifImage, x, y, finalWidth, finalHeight, this); 
			g2d.dispose();
		}
		else
		{
			g.drawImage(carBackground, 0, 0, 933, 772, this);

			Graphics2D g2d = (Graphics2D) g.create();



			if (leftButtonPressed) 
			{
				g2d.setColor(new Color(0, 0, 0, 180));
				g2d.fillRect(180, 684, 150, 33); 
			}

			else if (rightButtonPressed) 
			{
				g2d.setColor(new Color(0, 0, 0, 180)); 
				g2d.fillRect(469, 683, 302, 33); 
			}
			else if(leftButtonHovered)
			{
				g2d.setColor(new Color(0, 0, 0, 80));
				g2d.fillRect(180, 684, 150, 33); 
			}
			else if(rightButtonHovered)
			{
				g2d.setColor(new Color(0, 0, 0, 80)); 
				g2d.fillRect(469, 683, 302, 33); 
			}

		}
	}

	public void mousePressed(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();

		if (!gifOrNo)
		{
			if (y > 684 && y < 717 && x > 185 && x < 329) 
			{
				leftButtonPressed = true;
				repaint();
			} 
			else if (y > 685 && y < 716 && x > 469 && x < 771) 
			{
				rightButtonPressed = true;
				repaint();
			}
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();

		if (!gifOrNo)
		{
			if (leftButtonPressed && y > 670 && y < 717 && x > 181 && x < 329) 
			{
				layout.show(parent, "Instructions");
			} 
			else if (rightButtonPressed && y > 685 && y < 716 && x > 469 && x < 771) 
			{
				layout.show(parent, "HighScores");
			}
		}

		leftButtonPressed = false;
		rightButtonPressed = false;
		repaint();
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) 
	{
		leftButtonHovered = false;
		rightButtonHovered = false;	
		leftButtonPressed = false;
		rightButtonPressed = false;	
	}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();

		if (!gifOrNo)
		{
			if (y > 684 && y < 717 && x > 185 && x < 329) 
			{
				leftButtonHovered = true;
			} 
			else if (y > 685 && y < 716 && x > 469 && x < 771) 
			{
				rightButtonHovered = true;	
			}
			else
			{
				leftButtonHovered = false;
				rightButtonHovered = false;	
			}
			repaint();
		}
	}
}

class InstructionPanel extends JPanel 
{
	private JPanel parent;
	private CardLayout layout;

	public InstructionPanel(GameHolder gameHolder, CardLayout layout) 
	{
		this.parent = gameHolder;
		this.layout = layout;
		setLayout(new BorderLayout());	
		setBackground(Color.BLUE); 
		showObjects();
	}

	public void showObjects() 
	{
		JTextField instructions = new JTextField("Hello");
		instructions.setForeground(Color.WHITE);
		instructions.setBackground(Color.BLACK);
		instructions.setEditable(false);
		instructions.setBackground(Color.BLACK);
		add(instructions, BorderLayout.CENTER);

		JPanel showButtons = new JPanel (new GridLayout(1,1));
		showButtons.setBackground(Color.BLUE);

		JButton back = new JButton("   Back   ");
		back.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				layout.show(parent, "Welcome");
			}
		});

		back.setBackground(Color.BLACK);

		back.setForeground(Color.WHITE);
		back.setOpaque(true);
		back.setBorderPainted(false);
		add(back, BorderLayout.WEST);
		JButton next = new JButton("   Next   ");
		next.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				layout.show(parent, "ChooseCar");
			}
		});	
		next.setBackground(Color.BLACK);
		next.setBorderPainted(false);
		next.setForeground(Color.WHITE);
		next.setOpaque(true);
		showButtons.add(next);

		ImageIcon icon = new ImageIcon("Instructions.png");
		JLabel label = new JLabel(icon);
		add(label, BorderLayout.NORTH);

		add(showButtons, BorderLayout.EAST);
	}

}

class CarChoosePanel extends JPanel implements MouseListener, MouseMotionListener
{
    private Image carOptions;
    int x, y, xHover, yHover, xClick, yClick;
    private JPanel parent;
    private CardLayout layout;

    public CarChoosePanel(GameHolder gameHolder, CardLayout layout) 
    {
        this.parent = gameHolder;
        this.layout = layout;
        addMouseListener(this);
        addMouseMotionListener(this);
        setLayout(new BorderLayout());
        addComponents();
    }

    public void addComponents()
    {
        try 
        {
            carOptions = ImageIO.read(new File("CarOptions.png"));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                layout.show(parent, "Instructions");
            }
        });
        add(back, BorderLayout.EAST);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(carOptions, 0, 0, 500 , 775, this);
        Graphics2D g2d = (Graphics2D) g;

        // Draw gray box if clicked
        if (xClick != 0)
        {
            g.setColor(new Color (0, 0, 0, 80));
            g.fillRect(xClick, yClick, 97, 190);
        }

        // Draw blue hover rectangle if hovering
        if (xHover != 0)
        {
            g.setColor(Color.BLUE);
            java.awt.Stroke oldStroke = g2d.getStroke();
            g2d.setStroke(new BasicStroke(5));
            g.drawRect(xHover, yHover, 97, 190);
            g2d.setStroke(oldStroke);
        }
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) 
    {
    	 x = e.getX();
         y = e.getY();
         boolean clicked = false; // Flag to check if clicked box is toggled

         // Toggle logic for each box region
         if (x > 4 && x < 103 && y > 0 && y < 195) 
         {
             if (xClick == 4 && yClick == 6) 
             {
                 xClick = 0;
                 yClick = 0; // Reset if already clicked
             } 
             else 
             {
                 xClick = 4;
                 yClick = 6;
             }
             clicked = true;
         } 
         else if (x > 102 && x < 203 && y > 0 && y < 195) 
         {
             if (xClick == 102 && yClick == 6) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 102;
                 yClick = 6;
             }
             clicked = true;
         } 
         else if (x > 203 && x < 302 && y > 0 && y < 195) 
         {
             if (xClick == 203 && yClick == 6) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 203;
                 yClick = 6;
             }
             clicked = true;
         } 
         else if (x > 302 && x < 400 && y > 0 && y < 195) 
         {
             if (xClick == 302 && yClick == 6) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 302;
                 yClick = 6;
             }
             clicked = true;
         } 
         else if (x > 400 && x < 500 && y > 0 && y < 195) 
         {
             if (xClick == 400 && yClick == 6) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 400;
                 yClick = 6;
             }
             clicked = true;
         } 
         else if (x > 4 && x < 103 && y > 195 && y < 387) 
         {
             if (xClick == 4 && yClick == 195) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 4;
                 yClick = 195;
             }
             clicked = true;
         } 
         else if (x > 102 && x < 203 && y > 195 && y < 387) 
         {
             if (xClick == 102 && yClick == 195) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 102;
                 yClick = 195;
             }
             clicked = true;
         } 
         else if (x > 203 && x < 302 && y > 195 && y < 387) 
         {
             if (xClick == 203 && yClick == 195) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 203;
                 yClick = 195;
             }
             clicked = true;
         } 
         else if (x > 302 && x < 400 && y > 195 && y < 387) 
         {
             if (xClick == 302 && yClick == 195) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 302;
                 yClick = 195;
             }
             clicked = true;
         } 
         else if (x > 400 && x < 500 && y > 195 && y < 387) 
         {
             if (xClick == 400 && yClick == 195) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 400;
                 yClick = 195;
             }
             clicked = true;
         } 
         else if (x > 4 && x < 103 && y > 386 && y < 580) 
         {
             if (xClick == 4 && yClick == 386) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 4;
                 yClick = 386;
             }
             clicked = true;
         } 
         else if (x > 103 && x < 203 && y > 386 && y < 580) 
         {
             if (xClick == 103 && yClick == 386) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 103;
                 yClick = 386;
             }
             clicked = true;
         } 
         else if (x > 203 && x < 302 && y > 386 && y < 580) 
         {
             if (xClick == 203 && yClick == 386) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 203;
                 yClick = 386;
             }
             clicked = true;
         } 
         else if (x > 302 && x < 400 && y > 386 && y < 580) 
         {
             if (xClick == 302 && yClick == 386) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 302;
                 yClick = 386;
             }
             clicked = true;
         } 
         else if (x > 400 && x < 500 && y > 386 && y < 580) 
         {
             if (xClick == 400 && yClick == 386) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 400;
                 yClick = 386;
             }
             clicked = true;
         } 
         else if (x > 4 && x < 103 && y > 580 && y < 772) 
         {
             if (xClick == 4 && yClick == 580) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 4;
                 yClick = 580;
             }
             clicked = true;
         } 
         else if (x > 103 && x < 203 && y > 580 && y < 772) 
         {
             if (xClick == 103 && yClick == 580) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 103;
                 yClick = 580;
             }
             clicked = true;
         } 
         else if (x > 203 && x < 302 && y > 580 && y < 772) 
         {
             if (xClick == 203 && yClick == 580) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 203;
                 yClick = 580;
             }
             clicked = true;
         } 
         else if (x > 302 && x < 400 && y > 580 && y < 772) 
         {
             if (xClick == 302 && yClick == 580) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 302;
                 yClick = 580;
             }
             clicked = true;
         } 
         else if (x > 400 && x < 500 && y > 580 && y < 772) 
         {
             if (xClick == 400 && yClick == 580) 
             {
                 xClick = 0;
                 yClick = 0;
             } 
             else 
             {
                 xClick = 400;
                 yClick = 580;
             }
             clicked = true;
         } 
         
         // Reset if clicked outside any region
         if (!clicked) 
         {
             xClick = 0;
             yClick = 0;
         }

         repaint();
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) 
    {
        xHover = 0;
        yHover = 0;
        xClick = 0;
        xHover = 0;
        repaint();
    }

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) 
    {
        x = e.getX();
        y = e.getY();

        // Update hover position for each region
        if (x > 4 && x < 103 && y > 0 && y < 195)
		{
			xHover = 4;
			yHover = 6;
		}
		else if (x > 102 && x < 203 && y > 0 && y < 195)
		{
			xHover = 102;
			yHover = 6;
		}
		else if (x > 203 && x < 302 && y > 0 && y < 195)
		{
			xHover = 203;
			yHover = 6;
		}
		else if (x > 302 && x < 400 && y > 0 && y < 195)
		{
			xHover = 302;
			yHover = 6;
		}
		else if (x > 400 && x < 500 && y > 0 && y < 195)
		{
			xHover = 400;
			yHover = 6;
		}

		else if (x > 4 && x < 103 && y > 195 && y < 387)
		{
			xHover = 4;
			yHover = 195;
		}
		else if (x > 102 && x < 203 && y > 195 && y < 387)
		{
			xHover = 102;
			yHover = 195;
		}
		else if (x > 203 && x < 302 && y > 195 && y < 387)
		{
			xHover = 203;
			yHover = 195;
		}
		else if (x > 302 && x < 400 && y > 195 && y < 387)
		{
			xHover = 302;
			yHover = 195;
		}
		else if (x > 400 && x < 500 && y > 195 && y < 387)
		{
			xHover = 400;
			yHover = 195;
		}

		else if (x > 4 && x < 103 && y > 386 && y < 580)
		{
			xHover = 4;
			yHover = 386;
		}
		else if (x > 103 && x < 203 && y > 386 && y < 580)
		{
			xHover = 103;
			yHover = 386;
		}
		else if (x > 203 && x < 302 && y > 386 && y < 580)
		{
			xHover = 203;
			yHover = 386;
		}
		else if (x > 302 && x < 400 && y > 386 && y < 580)
		{
			xHover = 302;
			yHover = 386;
		}
		else if (x > 400 && x < 500 && y > 386 && y < 580)
		{
			xHover = 400;
			yHover = 386;
		}
		else if (x > 4 && x < 103 && y > 580 && y < 772)
		{
			xHover = 4;
			yHover = 580;
		}
		else if (x > 103 && x < 203 && y > 580 && y < 772)
		{
			xHover = 103;
			yHover = 580;
		}
		else if (x > 203 && x < 302 && y > 580 && y < 772)
		{
			xHover = 203;
			yHover = 580;
		}
		else if (x > 302 && x < 400 && y > 580 && y < 772)
		{
			xHover = 302;
			yHover = 580;
		}
		else if (x > 400 && x < 500 && y > 580 && y < 772)
		{
			xHover = 400;
			yHover = 580;
		}
		else 
		{
			xHover = 0;
			yHover = 0;
		}
        // Repeat for all other regions (same logic as in mouseClicked)

        repaint();
    }
}

class HighScorePanel extends JPanel
{
	public HighScorePanel(GameHolder gameHolder, CardLayout layout) 
	{

	}
}
