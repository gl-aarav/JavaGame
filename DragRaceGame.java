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
 *  - Add high scores panel
 */

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;

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
		GamePanel gamePanel = new GamePanel(this, layout);
		CarChoosePanel carChoose = new CarChoosePanel(this,layout);

		add(welcomePanel, "Welcome");
		add(instructionsPanel, "Instructions");
		add(highScoresPanel, "HighScores");
		add(carChoose, "ChooseCar");
		add(gamePanel, "Game");
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
			@Override
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
			@Override
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

	@Override
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
			int x = (getWidth() - finalWidth) / 2;
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

	@Override
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

	@Override
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

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e)
	{
		leftButtonHovered = false;
		rightButtonHovered = false;
		leftButtonPressed = false;
		rightButtonPressed = false;
	}
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
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
	private boolean agreementChecked;
	JCheckBox agreementCheckBox = new JCheckBox("I agree to the terms and conditions");;

	public InstructionPanel(GameHolder gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		showObjects();
	}

	public void showObjects()
	{
		JTextPane instructions = new JTextPane();
		instructions.setContentType("text/html");
		instructions.setText(
				"<html><div style='text-align: center; font-family: Arial; font-size: 10px; color: white;'>"
						+ "<h2> Game Setup</h2>"
						+ "<p>You control a race car that competes against a bot car.<br>"
						+ "Answer correctly for speed boosts; wrong answers slow you down.</p>"
						+ "<h2> How to Play</h2>"
						+ "<p>Enter your name, pick your car color, choose difficulty,<br>"
						+ "and press <b>Start Game</b> to begin.<br>"
						+ "Answer questions using the buttons that appear.<br>"
						+ "If the bot wins, you lose!</p>"
						+ "<h2> Learning Features</h2>"
						+ "<p>At the end, review your mistakes,<br>"
						+ "see correct answers, and get helpful explanations.</p>"
						+ "<h2> Winning the Game</h2>"
						+ "<p>Beat the bot to the finish line!<br>"
						+ "Track your progress with the progress bar,<br>"
						+ "and aim for a high score!</p>"
						+ "</div></html>"
				); // Set the text with HTML formatting
		instructions.setEditable(false);
		instructions.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent background
		instructions.setOpaque(true);

		instructions.setForeground(Color.WHITE);
		instructions.setBackground(Color.BLACK);
		instructions.setEditable(false);
		instructions.setBackground(Color.BLACK);
		add(instructions, BorderLayout.CENTER);

		JButton back = new JButton("   Back   ");
		back.addActionListener(new ActionListener()
		{
			@Override
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
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!agreementChecked)
				{
					// Show a message dialog if the agreement is not checked
					javax.swing.JOptionPane.showMessageDialog(parent, "Please agree to the terms and conditions to proceed.", "Agreement Required", javax.swing.JOptionPane.INFORMATION_MESSAGE);
				}
				else if (agreementChecked)
				{
					layout.show(parent, "ChooseCar");
					agreementChecked = false; // Reset for next time
				}
			}
		});

		next.setBackground(Color.BLACK);
		next.setBorderPainted(false);
		next.setForeground(Color.WHITE);
		next.setOpaque(true);
		add(next, BorderLayout.EAST);

		agreementCheckBox.setForeground(Color.WHITE);
		agreementCheckBox.setBackground(Color.BLACK);
		agreementCheckBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (agreementCheckBox.isSelected())
				{
					agreementChecked = true;
				}
				else
				{
					agreementChecked = false;
				}
			}
		});
		add(agreementCheckBox, BorderLayout.SOUTH);
		ImageIcon icon = new ImageIcon("Instructions.png");
		JLabel label = new JLabel(icon);
		add(label, BorderLayout.NORTH);
	}
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		if (visible)
		{
			agreementCheckBox.setSelected(false); // Reset the checkbox
			agreementChecked = false; // Reset the agreement flag
		}
	}

}

class CarChoosePanel extends JPanel implements MouseListener, MouseMotionListener
{
	// This class represents the car selection panel where players can choose their car and enter their name.
	private Image carOptions;
	private Image carOptionsNoBackgroundOriginal;
	private Image EmptyCar;
	int x, y, xHover, yHover, xClick, yClick, opponentX, opponentY;
	private JPanel parent;
	private CardLayout layout;
	private String name = "";
	private boolean carSelected = false;
	private boolean nameEntered = false;
	private boolean difficultySelected = false;

	public CarChoosePanel(GameHolder gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;
		addMouseListener(this);
		addMouseMotionListener(this);
		setLayout(null);
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

		try
		{
			carOptionsNoBackgroundOriginal = ImageIO.read(new File("CarOptionsClearBackground.png")); // Load the image without background
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			EmptyCar = ImageIO.read(new File("EmptyCar.jpg")); // Load the empty car image
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				layout.show(parent, "Instructions");
			}
		});
		back.setBounds(730, 720, 80, 30); // Positioned in bottom right corner with space for "Next"
		add(back);

		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				layout.show(parent, "Game"); // Replace with your actual next panel name
			}
		});
		next.setBounds(830, 720, 80, 30); // Positioned in bottom right corner with space for "Back"
		add(next);
		JTextField nameField = new JTextField("Enter Your Name");
		nameField.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (nameField.getText().equals("Enter Your Name"))
				{
					nameField.setText("");
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});

		nameField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (nameField.getText().isEmpty())
				{
					nameField.setText("Enter Your Name");
					nameEntered = false;
					repaint();
				}
				else
				{
					name = nameField.getText() + ", ";
					nameEntered = true;
					repaint();
				}
			}
		});

		add(nameField);
		nameField.setBounds(515, 590, 400, 30);
		nameField.setEditable(true);

		JSlider difficultySlider = new JSlider(0, 100, 50);
		difficultySlider.setMajorTickSpacing(20);
		difficultySlider.setMinorTickSpacing(5);
		difficultySlider.setPaintTicks(true);
		difficultySlider.setPaintLabels(true);
		difficultySlider.setBounds(515, 650, 400, 50);
		add(difficultySlider);
		JLabel difficultyLabel = new JLabel("Difficulty Level");
		difficultyLabel.setBounds(500, 630, 400, 20);
		difficultyLabel.setForeground(Color.BLACK);
		difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		add(difficultyLabel);

		createCoordinatesForOpponent(); // Create opponent coordinates
	}

	public void createCoordinatesForOpponent()
	{
		int carNumber = (int)(Math.random()*25)+1;
		if (carNumber == 1)
		{
			opponentX = 4;
			opponentY = 6;
		}
		else if (carNumber == 2)
		{
			opponentX = 102;
			opponentY = 6;
		}
		else if (carNumber == 3)
		{
			opponentX = 203;
			opponentY = 6;
		}
		else if (carNumber == 4)
		{
			opponentX = 302;
			opponentY = 6;
		}
		else if (carNumber == 5)
		{
			opponentX = 400;
			opponentY = 6;
		}
		else if (carNumber == 6)
		{
			opponentX = 4;
			opponentY = 195;
		}
		else if (carNumber == 7)
		{
			opponentX = 102;
			opponentY = 195;
		}
		else if (carNumber == 8)
		{
			opponentX = 203;
			opponentY = 195;
		}
		else if (carNumber == 9)
		{
			opponentX = 302;
			opponentY = 195;
		}
		else if (carNumber == 10)
		{
			opponentX = 400;
			opponentY = 195;
		}
		else if (carNumber == 11)
		{
			opponentX = 4;
			opponentY = 386;
		}
		else if (carNumber == 12)
		{
			opponentX = 102 ;
			opponentY = 386;
		}
		else if (carNumber == 13)
		{
			opponentX = 203;
			opponentY = 386;
		}
		else if (carNumber == 14)
		{
			opponentX = 302;
			opponentY = 386;
		}
		else if (carNumber == 15)
		{
			opponentX = 400;
			opponentY = 580;
		}
		else if (carNumber == 16)
		{
			opponentX = 4;
			opponentY = 580;
		}
		else if (carNumber == 17)
		{
			opponentX = 102;
			opponentY = 580;
		}
		else if (carNumber == 18)
		{
			opponentX = 203;
			opponentY = 580;
		}
		else if (carNumber == 19)
		{
			opponentX = 302;
			opponentY = 580;
		}
		else if (carNumber == 20)
		{
			opponentX = 400;
			opponentY = 580;
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		int carWidth = 97;
		int carHeight = 190;

		super.paintComponent(g);
		g.drawImage(carOptions, 0, 0, 500 , 775, this);
		Graphics2D g2d = (Graphics2D) g;

		g.drawImage(carOptionsNoBackgroundOriginal, 755, 105, 755 + carWidth, 105 + carHeight, opponentX, opponentY, opponentX + carWidth, opponentY + carHeight, this);

		// Draw gray box if clicked
		if (xClick != 0)
		{
			g.setColor(new Color (0, 0, 0, 80));
			g.fillRect(xClick, yClick, 97, 190);
			// Draw cropped section from carOptions into "Your Car" area
			g.drawImage(carOptionsNoBackgroundOriginal, 530, 105, 530 + carWidth, 105 + carHeight, xClick, yClick, xClick + carWidth, yClick + carHeight, this);
		}
		else
		{
			g.drawImage(EmptyCar, 540, 105, carWidth, carHeight, this);
			//add border to the empty car
			java.awt.Stroke oldStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(2));
			g.drawRect(540, 105, carWidth, carHeight);
			g2d.setStroke(oldStroke);
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

		g.setColor(new Color (31, 81, 255));
		g.setFont(new Font ("Amazone BT", Font.BOLD, 25));

		if (nameEntered && carSelected)
		{
			g.drawString(name + "Get Ready to Play!", 500, 25);
		}
		else if (nameEntered && !carSelected)
		{
			g.drawString(name + "Select a Car", 500, 25);
		}
		else if (!nameEntered && carSelected)
		{
			g.drawString("Enter Your Name", 500, 25);
		}
		else if (!nameEntered && !carSelected)
		{
			g.drawString("Enter Your Name and Select a Car", 500, 25);
		}

		g.drawLine(500, 35, 933, 35);
		g.setFont(new Font ("Amazone BT", Font.PLAIN, 15));
		g.drawLine(550, 93, 600, 93);
		g.drawString("Your Car", 550, 90);
		g.drawLine(760, 93, 845, 93);
		g.drawString("Opponents Car", 750, 90);
	}



	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e)
	{
		//Number of cars: 20
		x = e.getX();
		y = e.getY();
		boolean clicked = false; // Flag to check if clicked box is toggled

		// Toggle logic for each box region
		if (x > 4 && x < 102 && y > 0 && y < 195)
		{
			if (xClick == 4 && yClick == 6)
			{
				xClick = 0;
				yClick = 0; // Reset if already clicked
				carSelected = false;
			}
			else
			{
				xClick = 4;
				yClick = 6;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 102 && x < 203 && y > 0 && y < 195)
		{
			if (xClick == 102 && yClick == 6)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 102;
				yClick = 6;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 203 && x < 302 && y > 0 && y < 195)
		{
			if (xClick == 203 && yClick == 6)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 203;
				yClick = 6;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 302 && x < 400 && y > 0 && y < 195)
		{
			if (xClick == 302 && yClick == 6)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 302;
				yClick = 6;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 400 && x < 500 && y > 0 && y < 195)
		{
			if (xClick == 400 && yClick == 6)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 400;
				yClick = 6;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 4 && x < 102 && y > 195 && y < 387)
		{
			if (xClick == 4 && yClick == 195)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 4;
				yClick = 195;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 102 && x < 203 && y > 195 && y < 387)
		{
			if (xClick == 102 && yClick == 195)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 102;
				yClick = 195;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 203 && x < 302 && y > 195 && y < 387)
		{
			if (xClick == 203 && yClick == 195)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 203;
				yClick = 195;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 302 && x < 400 && y > 195 && y < 387)
		{
			if (xClick == 302 && yClick == 195)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;	
			}
			else
			{
				xClick = 302;
				yClick = 195;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 400 && x < 500 && y > 195 && y < 387)
		{
			if (xClick == 400 && yClick == 195)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 400;
				yClick = 195;
				carSelected = true;	
			}
			clicked = true;
		}
		else if (x > 4 && x < 102 && y > 386 && y < 580)
		{
			if (xClick == 4 && yClick == 386)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;	
			}
			else
			{
				xClick = 4;
				yClick = 386;
				carSelected = true;	
			}
			clicked = true;
		}
		else if (x > 102 && x < 203 && y > 386 && y < 580)
		{
			if (xClick == 102 && yClick == 386)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;	
			}
			else
			{
				xClick = 102 ;
				yClick = 386;
				carSelected = true;	
			}
			clicked = true;
		}
		else if (x > 203 && x < 302 && y > 386 && y < 580)
		{
			if (xClick == 203 && yClick == 386)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;		
			}
			else
			{
				xClick = 203;
				yClick = 386;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 302 && x < 400 && y > 386 && y < 580)
		{
			if (xClick == 302 && yClick == 386)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 302;
				yClick = 386;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 400 && x < 500 && y > 386 && y < 580)
		{
			if (xClick == 400 && yClick == 386)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 400;
				yClick = 386;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 4 && x < 102 && y > 580 && y < 772)
		{
			if (xClick == 4 && yClick == 580)
			{
				xClick = 0;
				yClick = 0;
					carSelected = false;
			}
			else
			{
				xClick = 4;
				yClick = 580;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 102 && x < 203 && y > 580 && y < 772)
		{
			if (xClick == 102 && yClick == 580)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 102 ;
				yClick = 580;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 203 && x < 302 && y > 580 && y < 772)
		{
			if (xClick == 203 && yClick == 580)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 203;
				yClick = 580;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 302 && x < 400 && y > 580 && y < 772)
		{
			if (xClick == 302 && yClick == 580)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;
			}
			else
			{
				xClick = 302;
				yClick = 580;
				carSelected = true;
			}
			clicked = true;
		}
		else if (x > 400 && x < 500 && y > 580 && y < 772)
		{
			if (xClick == 400 && yClick == 580)
			{
				xClick = 0;
				yClick = 0;
					carSelected = false;
					
			}
			else
			{
				xClick = 400;
				yClick = 580;
				carSelected = true;
			}
			clicked = true;
		}

		if (!clicked)
		{
			xClick = 0;
			yClick = 0;
			carSelected = false;
		}

		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();

		// Update hover position for each region
		if (x > 4 && x < 102 && y > 0 && y < 195)
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

		else if (x > 4 && x < 102 && y > 195 && y < 387)
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

		else if (x > 4 && x < 102 && y > 386 && y < 580)
		{
			xHover = 4;
			yHover = 386;
		}
		else if (x > 102 && x < 203 && y > 386 && y < 580)
		{
			xHover = 102 ;
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
		else if (x > 4 && x < 102 && y > 580 && y < 772)
		{
			xHover = 4;
			yHover = 580;
		}
		else if (x > 102 && x < 203 && y > 580 && y < 772)
		{
			xHover = 102 ;
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

		repaint();
	}
}


class GamePanel extends JPanel
{
	private JPanel parent;
	private CardLayout layout;

	public GamePanel(JPanel gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;
		setLayout(new BorderLayout());
		setBackground(Color.DARK_GRAY);

		JLabel label = new JLabel("Game Under Construction", JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.BOLD, 30));
		add(label, BorderLayout.CENTER);

		JButton back = new JButton("Back to Car Select");
		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				layout.show(parent, "ChooseCar");
			}
		});
		add(back, BorderLayout.SOUTH);
	}
}

class HighScorePanel extends JPanel
{
	private JPanel parent;
	private CardLayout layout;

	public HighScorePanel(JPanel gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);

		JLabel label = new JLabel("High Scores Coming Soon", JLabel.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Arial", Font.BOLD, 32));
		add(label, BorderLayout.CENTER);

		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				layout.show(parent, "Welcome");
			}
		});
		add(back, BorderLayout.SOUTH);
	}
}