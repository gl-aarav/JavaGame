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
import javax.swing.border.LineBorder;
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


class WelcomePagePanel extends JPanel implements MouseListener
{
	private JPanel parent;
	private CardLayout layout;
	private float alpha = 1.0f; 
	private Timer timer;
	private Image gifImage;
	private boolean gifOrNo;
	private Image carBackground;

	private boolean leftButtonPressed = false;
	private boolean rightButtonPressed = false;

	public WelcomePagePanel(JPanel gameHolder, CardLayout layout) 
	{
		this.parent = gameHolder;
		this.layout = layout;
		addMouseListener(this);
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
			g2d.setColor(new Color(0, 0, 0, 80)); // Light semi-transparent black

			if (leftButtonPressed) 
			{
				g2d.fillRect(180, 684, 150, 33); // Left button highlight
			}

			if (rightButtonPressed) 
			{
				g2d.fillRect(469, 683, 302, 33); // Right button highlight
			}
			g2d.dispose();
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
	public void mouseExited(MouseEvent e) {}
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
		setBackground(new Color(0, 0, 70));
		showObjects();
	}

	public void showObjects() {
		JTextField instructions = new JTextField("Hello");
		instructions.setForeground(Color.WHITE);
		instructions.setBackground(Color.BLACK);
		instructions.setEditable(false);
		instructions.setBackground(Color.BLACK);
		add(instructions, BorderLayout.CENTER);

		JButton back = new JButton("   Back   ");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(parent, "Welcome");
			}
		});
		back.setBorder(new LineBorder(Color.BLUE, 1));
		back.setBackground(Color.BLACK);
		back.setForeground(Color.WHITE);
		back.setOpaque(true);
		add(back, BorderLayout.WEST);

		// Next Button
		JButton next = new JButton("   Next   ");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layout.show(parent, "ChooseCar");
			}
		});	
		next.setBorder(new LineBorder(Color.BLUE, 1));
		next.setBackground(Color.BLACK);
		next.setForeground(Color.WHITE);
		next.setOpaque(true);
		add(next, BorderLayout.EAST);

		// Image Label
		ImageIcon icon = new ImageIcon("Instructions.png");
		JLabel label = new JLabel(icon);
		add(label, BorderLayout.NORTH);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

}

class CarChoosePanel extends JPanel implements MouseListener, MouseMotionListener
{
	private Image carOptions;
	int x, y, xPrint, yPrint;
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
		g.drawImage(carOptions, 0, 0, 500 , 772, this);
		g.setColor(Color.BLUE);
		if (xPrint != 0)
		{
			Graphics2D g2d = (Graphics2D) g;
			java.awt.Stroke oldStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(5));
			g.drawRect(xPrint, yPrint, 97, 195);
			g2d.setStroke(oldStroke);
		}
	}


	public void mouseClicked(MouseEvent e) 
	{
		x = e.getX();
		y = e.getY();
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) 
	{
		x = e.getX();
		y = e.getY();
		if (x > 3 && x < 103 && y > 0 && y < 192)
		{
			xPrint = 3;
			yPrint = 3;
		}
		else if (x > 102 && x < 203 && y > 0 && y < 192)
		{
			xPrint = 102;
			yPrint = 3;
		}
		else if (x > 203 && x < 305 && y > 0 && y < 192)
		{
			xPrint = 203;
			yPrint = 3;
		}
		else if (x > 305 && x < 403 && y > 0 && y < 192)
		{
			xPrint = 305;
			yPrint = 3;
		}
		else if (x > 403 && x < 500 && y > 0 && y < 192)
		{
			xPrint = 403;
			yPrint = 3;
		}

		else if (x > 0 && x < 103 && y > 192 && y < 387)
		{
			xPrint = 3;
			yPrint = 192;
		}
		else if (x > 106 && x < 203 && y > 192 && y < 387)
		{
			xPrint = 106;
			yPrint = 192;
		}
		else if (x > 203 && x < 305 && y > 192 && y < 387)
		{
			xPrint = 203;
			yPrint = 192;
		}
		else if (x > 305 && x < 403 && y > 192 && y < 387)
		{
			xPrint = 305;
			yPrint = 192;
		}
		else if (x > 403 && x < 500 && y > 192 && y < 387)
		{
			xPrint = 403;
			yPrint = 192;
		}

		else if (x > 0 && x < 103 && y > 408 && y < 580)
		{
			xPrint = 3;
			yPrint = 408;
		}
		else if (x > 106 && x < 203 && y > 408 && y < 580)
		{
			xPrint = 106;
			yPrint = 408;
		}
		else if (x > 203 && x < 305 && y > 408 && y < 580)
		{
			xPrint = 203;
			yPrint = 408;
		}
		else if (x > 305 && x < 403 && y > 408 && y < 580)
		{
			xPrint = 305;
			yPrint = 408;
		}
		else if (x > 403 && x < 500 && y > 408 && y < 580)
		{
			xPrint = 403;
			yPrint = 408;
		}
		repaint();
	}
}

class HighScorePanel extends JPanel
{
	public HighScorePanel(GameHolder gameHolder, CardLayout layout) 
	{

	}
}
