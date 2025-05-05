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
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
	private SoundPlayer buttonClickSound;
	private SoundPlayer carAccelerationSound;
	private JButton muteButton;
	private boolean isMuted = false;

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
		buttonClickSound = new SoundPlayer("buttonClick.wav");
		carAccelerationSound = new SoundPlayer("accelerate.wav");
		// Inside the WelcomePagePanel constructor
		Timer soundDelay = new Timer(1000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				carAccelerationSound.play(); // Play the sound after 1 second
			}
		});
		soundDelay.setRepeats(false); // Ensure the timer only runs once
		soundDelay.start(); // Start the timer

		// Add mute button
		muteButton = new JButton(new ImageIcon("unmute.jpg"));
		muteButton.setVisible(false);
		muteButton.setBounds(10, 10, 50, 50); // Position in the top-left corner
		muteButton.setBorderPainted(false);
		muteButton.setContentAreaFilled(false);
		muteButton.setFocusPainted(false);
		muteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				isMuted = !isMuted;
				if (isMuted)
				{
					SoundPlayer.setMuted(isMuted);
					muteButton.setIcon(new ImageIcon("mute.jpg"));
				}
				else
				{
					SoundPlayer.setMuted(isMuted);
					muteButton.setIcon(new ImageIcon("unmute.jpg"));

				}
			}
		});
		add(muteButton);
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

		Timer delay = new Timer(7500, new ActionListener()
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
		//play the car acceleration sound
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
					muteButton.setVisible(true); // Show the mute button
					carAccelerationSound.stop(); // Stop the car acceleration sound
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
				buttonClickSound.play(); // Play button click sound
			}
			else if (y > 685 && y < 716 && x > 469 && x < 771)
			{
				rightButtonPressed = true;
				repaint();
				buttonClickSound.play(); // Play button click sound
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
	private SoundPlayer buttonClickSound, NotificationSound;
	private JPanel parent;
	private CardLayout layout;
	private boolean agreementChecked;
	JCheckBox agreementCheckBox = new JCheckBox("I agree to the terms and conditions");

	public InstructionPanel(GameHolder gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		showObjects();
		buttonClickSound = new SoundPlayer("buttonClick.wav");
		NotificationSound = new SoundPlayer("Notification.wav");
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
				buttonClickSound.play(); // Play button click sound
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
					NotificationSound.play(); // Play notification sound
					// Show a message dialog if the agreement is not checked
					javax.swing.JOptionPane.showMessageDialog(parent, "Please agree to the terms and conditions to proceed.", "Agreement Required", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					buttonClickSound.play(); // Play button click sound
				}
				else if (agreementChecked)
				{
					buttonClickSound.play(); // Play button click sound
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
				buttonClickSound.play(); // Play button click sound
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
	private Image imageForOpponent;
	int x, y, xHover, yHover, xClick, yClick, opponentX, opponentY;
	private JPanel parent;
	private CardLayout layout;
	private String name = "";
	private boolean carSelected = false;
	private boolean nameEntered = false;
	private SoundPlayer carSelectSound;
	private SoundPlayer buttonClickSound;
	private JLabel carStatsLabel;
	private String carStats = "No car selected"; // Placeholder for car stats

	public CarChoosePanel(GameHolder gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;
		addMouseListener(this);
		addMouseMotionListener(this);
		setLayout(null);
		addComponents();
		try
		{
			imageForOpponent = ImageIO.read(new File("CarNormal.png")); // Default to easy mode
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		carSelectSound = new SoundPlayer("carSelect.wav");
		buttonClickSound = new SoundPlayer("buttonClick.wav");

		try
		{
			imageForOpponent = ImageIO.read(new File("Bicycle.png")); // Default to easy mode
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void addComponents()
	{
		try
		{
			carOptions = ImageIO.read(new File("CarOptions.png"));
			EmptyCar = ImageIO.read(new File("EmptyCar.jpg")); // Load the empty car image
			carOptionsNoBackgroundOriginal = ImageIO.read(new File("CarOptionsClearBackground.png")); // Load the image without background
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
				buttonClickSound.play(); // Play button click sound
				layout.show(parent, "Instructions");
			}
		});
		back.setBounds(730, 720, 80, 30);
		add(back);

		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				buttonClickSound.play(); // Play button click sound
				layout.show(parent, "Game");
			}
		});
		next.setBounds(830, 720, 80, 30);
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

		nameField.addFocusListener(new java.awt.event.FocusListener()
		{
			@Override
			public void focusGained(java.awt.event.FocusEvent e)
			{
				buttonClickSound.play(); // Play button click sound
				if (nameField.getText().equals("Enter Your Name"))
				{
					nameField.setText(""); // Clear the placeholder text
				}
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent e)
			{
				if (nameField.getText().isEmpty())
				{
					nameField.setText("Enter Your Name"); // Reset placeholder text if empty
				}
			}
		});

		nameField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (nameField.getText().isEmpty() || nameField.getText().equals("Enter Your Name"))
				{
					// Reset the name field if empty or default text
					nameField.setText("Enter Your Name");
					nameEntered = false;
					repaint();
				}
				else if (nameField.getText().length() > 13)
				{
					// Limit the name to 16 characters
					nameField.setText(nameField.getText().substring(0,13));
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

		//Add Easy medium and hard labels
		JSlider difficultySlider = new JSlider(0, 100, 50);
		difficultySlider.setMajorTickSpacing(20);
		difficultySlider.setMinorTickSpacing(5);
		difficultySlider.setPaintTicks(true);
		difficultySlider.setPaintLabels(true);
		difficultySlider.setValue(50); // Default to medium difficulty
		difficultySlider.setBounds(515, 650, 400, 50);

		difficultySlider.addChangeListener(e ->
		{
			int value = difficultySlider.getValue();
			try
			{
				if (value < 20)
				{
					imageForOpponent = ImageIO.read(new File("Bicycle.png")); // Easy mode
				}
				else if (value < 40)
				{
					imageForOpponent = ImageIO.read(new File("Motorcycle.png"));
				}
				else if (value < 60)
				{
					imageForOpponent = ImageIO.read(new File("CarNormal.png"));
				}
				else if (value < 80)
				{
					imageForOpponent = ImageIO.read(new File("CarSport.png"));
				} else
				{
					imageForOpponent = ImageIO.read(new File("Rocket.png"));
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
			repaint(); // Ensure the panel updates
		});

		add(difficultySlider);

		JLabel difficultyLabel = new JLabel("Difficulty Level");
		difficultyLabel.setBounds(500, 630, 400, 20);
		difficultyLabel.setForeground(Color.BLACK);
		difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		add(difficultyLabel);
		carStatsLabel = new JLabel(carStats);
		carStatsLabel.setBounds(540, 300, 300, 150); // Increase width and height
		carStatsLabel.setFont(new Font("Arial", Font.BOLD, 16));
		carStatsLabel.setForeground(Color.BLUE);
		carStatsLabel.setVerticalAlignment(JLabel.TOP); // Align text to the top
		carStatsLabel.setHorizontalAlignment(JLabel.LEFT); // Align text to the left
		add(carStatsLabel);

	}

	@Override
	public void paintComponent(Graphics g)
	{
		int carWidth = 97;
		int carHeight = 190;

		super.paintComponent(g);
		g.drawImage(carOptions, 0, 0, 500 , 775, this);
		Graphics2D g2d = (Graphics2D) g;

		g.drawImage(imageForOpponent, 755, 105, carWidth, carHeight, this);

		// Draw gray box if clicked
		if (xClick != 0)
		{
			carSelected = true; // Set carSelected to true if a car is clicked
			g.setColor(new Color (0, 0, 0, 80));
			g.fillRect(xClick, yClick, 97, 190);
			// Draw cropped section from carOptions into "Your Car" area
			g.drawImage(carOptionsNoBackgroundOriginal, 530, 105, 530 + carWidth, 105 + carHeight, xClick, yClick, xClick + carWidth, yClick + carHeight, this);
		}
		else
		{
			carStats = "No car selected"; // Reset the car stats
			carStatsLabel.setText(carStats); // Update the label text
			carSelected = false; // Reset carSelected if no car is clicked
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
		g.setColor(Color.BLUE);
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
		boolean clicked = true; // Flag to check if clicked box is toggled

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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
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
				carSelectSound.play(); // Play car select sound
			}
			clicked = true;
		}

		if (!clicked)
		{
			xClick = 0;
			yClick = 0;
			carSelected = false;
		}

		//THIS IS A TENTATIVE SOLUTION FOR CAR COLORS, TYPES, CONDITIONS AND ENGINE TYPES, WILL BE MODIFIED LATER
		if (carSelected)
		{
			Map<String, String> carColors = new HashMap<>();
			carColors.put("6,4", "Blue");
			carColors.put("6,102", "Blue");
			carColors.put("6,203", "Blue");
			carColors.put("6,302", "Blue");
			carColors.put("6,400", "Blue");
			carColors.put("195,4", "Green");
			carColors.put("195,102", "Green");
			carColors.put("195,203", "Green");
			carColors.put("195,302", "Green");
			carColors.put("195,400", "Green");
			carColors.put("386,4", "Red");
			carColors.put("386,102", "Red");
			carColors.put("386,203", "Red");
			carColors.put("386,302", "Red");
			carColors.put("386,400", "Red");
			carColors.put("580,4", "Yellow");
			carColors.put("580,102", "Yellow");
			carColors.put("580,203", "Yellow");
			carColors.put("580,302", "Yellow");
			carColors.put("580,400", "Yellow");

			Map<String, String> carTypes = new HashMap<>();
			carTypes.put("6,4", "Sedan");
			carTypes.put("6,102", "SUV");
			carTypes.put("6,203", "Truck");
			carTypes.put("6,302", "Coupe");
			carTypes.put("6,400", "Convertible");
			carTypes.put("195,4", "Hatchback");
			carTypes.put("195,102", "Van");
			carTypes.put("195,203", "Wagon");
			carTypes.put("195,302", "Sports");
			carTypes.put("195,400", "Luxury");
			carTypes.put("386,4", "Electric");
			carTypes.put("386,102", "Hybrid");
			carTypes.put("386,203", "Diesel");
			carTypes.put("386,302", "Petrol");
			carTypes.put("386,400", "CNG");
			carTypes.put("580,4", "Off-Road");
			carTypes.put("580,102", "Compact");
			carTypes.put("580,203", "Mini");
			carTypes.put("580,302", "Pickup");
			carTypes.put("580,400", "Muscle");

			Map<String, String> carConditions = new HashMap<>();
			carConditions.put("6,4", "New");
			carConditions.put("6,102", "Used");
			carConditions.put("6,203", "Certified");
			carConditions.put("6,302", "New");
			carConditions.put("6,400", "Used");
			carConditions.put("195,4", "Certified");
			carConditions.put("195,102", "New");
			carConditions.put("195,203", "Used");
			carConditions.put("195,302", "Certified");
			carConditions.put("195,400", "New");
			carConditions.put("386,4", "Used");
			carConditions.put("386,102", "Certified");
			carConditions.put("386,203", "New");
			carConditions.put("386,302", "Used");
			carConditions.put("386,400", "Certified");
			carConditions.put("580,4", "New");
			carConditions.put("580,102", "Used");
			carConditions.put("580,203", "Certified");
			carConditions.put("580,302", "New");
			carConditions.put("580,400", "Used");

			Map<String, String> engineTypes = new HashMap<>();
			engineTypes.put("6,4", "V12");
			engineTypes.put("6,102", "V8");
			engineTypes.put("6,203", "Inline-4");
			engineTypes.put("6,302", "Electric");
			engineTypes.put("6,400", "Hybrid");
			engineTypes.put("195,4", "Diesel");
			engineTypes.put("195,102", "Petrol");
			engineTypes.put("195,203", "CNG");
			engineTypes.put("195,302", "Electric");
			engineTypes.put("195,400", "Hybrid");
			engineTypes.put("386,4", "V6");
			engineTypes.put("386,102", "V8");
			engineTypes.put("386,203", "Inline-4");
			engineTypes.put("386,302", "Electric");
			engineTypes.put("386,400", "Hybrid");
			engineTypes.put("580,4", "Diesel");
			engineTypes.put("580,102", "Petrol");
			engineTypes.put("580,203", "CNG");
			engineTypes.put("580,302", "Electric");
			engineTypes.put("580,400", "Hybrid");

			String key = yClick + "," + xClick;
			if (carColors.containsKey(key) && carSelected)
			{
				carStats = "<html>Car Stats:<br>"
						+ "- Color: " + carColors.get(key) + "<br>"
						+ "- Type: " + carTypes.get(key) + "<br>"
						+ "- Condition: " + carConditions.get(key) + "<br>"
						+ "- Engine Type: " + engineTypes.get(key) + "</html>";
				carStatsLabel.setText(carStats); // Update the label text
				carStatsLabel.revalidate(); // Ensure the label is revalidated
				carStatsLabel.repaint(); // Repaint the label to reflect changes
			}

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

class SoundPlayer
{
	private Clip clip;
	private FloatControl volumeControl;
	private static boolean isMuted = false; // Global mute flag

	// Constructor to load the sound file
	public SoundPlayer(String filePath)
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);

			// Get the volume control
			volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	// Play the sound
	public void play()
	{
		if (clip != null && !isMuted)
		{ // Check if not muted
			clip.setFramePosition(0); // Rewind to the beginning
			clip.start();
		}
	}

	// Set volume (range: 0.0 to 1.0)
	public void setVolume(float volume)
	{
		if (volumeControl != null && !isMuted)
		{ // Check if not muted
			float min = volumeControl.getMinimum();
			float max = volumeControl.getMaximum();
			float gain = min + (max - min) * volume; // Scale volume to gain range
			volumeControl.setValue(gain);
		}
	}

	// Stop the sound
	public void stop()
	{
		if (clip != null)
		{
			clip.stop();
		}
	}

	// Static method to mute or unmute all sounds
	public static void setMuted(boolean muted)
	{
		isMuted = muted;
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