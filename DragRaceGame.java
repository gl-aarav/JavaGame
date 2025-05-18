/*
 * Aarav Goyal
 * 4/23/2025
 * DragRaceGame.java
 *
 * Week 1
 * 	- Add intro GIF and fade out animation
 * 	- Create welcome page
 * 	- Add image to go to instructions and high scores
 * 	- Add sound
 * 	- Add coordinates to go to instructions and high scores
 *  - Make the pointer change when hovering over the coordinates
 *  - Create the instructions and high scores panels
 * Week 2
 * 	- Finish the instructions panel
 *  - Create select car select panel
 *  - Look for images for the cars
 *  - Clear the background of the car images
 *  - Create the car images
 *  - Create a 5x4 grid for the car images
 *  - Create the empty car image
 *  
 * Week 3
 *  - Start on working to import all the files
 *  - Add sound
 *  - Add the car images
 *  - Add the car selection
 *  - Make the pointer change when hovering over the buttons
 *  - Add the car statistics
 *  - Add the buttons for back and next
 *  - Add the text field for the name
 *  - Add the difficulty slider
 *  - Print the name and car on the top of the screen
 *  - Show the car selected
 *  - Show the empty car if no car is selected
 *  - Create basic functioning buttons
 * Week 4
 *  - Start the game panel
 *  - create the text file for the questions
 *  - Import the questions
 *  - Create the Answer buttons and the Question panel
 *  - Create the progress bar
 *  - Create the timer
 *  - Add the person's name to the game panel
 *  - Make the car move
 *  - Add the opponent car
 *  - Add another type of game (tug of war)
 * Week 5
 *  - Finish the game panel
 *  - Finish the question panel
 *  - Add Sound
 *  - create the answer text file
 *  - Add next button in game panel after game is over
 *  - Find sound for the game
 *  - Test the game
 *  - Integrate the learning panel with the answer text file
 *  - Create the learning panel
 *  - Finishing touches
 *  - Add the high scores text file
 *  - Finish the high scores panel
 *  - Add the sounds
 *  - Finish the learning panel
 *  - Finish the game panel
 *  - Finish the high scores panel
 *  - Add more graphics (if time allows)
 *  - Add another subject (if time allows)
 *  Week 6 
 *  - Finishing Touches
 */

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.util.Scanner;
import java.util.function.BiConsumer;

public class DragRaceGame 
{
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Drag Race!");
		frame.setSize(1166, 1003);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.getContentPane().add(new GameHolder());

		// Center the window on the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2 - 5;
		frame.setLocation(x, y);

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
		HighScorePanelAfter highScoresPanelAfter = new HighScorePanelAfter(this, layout);
		GamePanel gamePanel = new GamePanel(this, layout);
		CarChoosePanel carChoose = new CarChoosePanel(this,layout);
		TugOfWarPanel tow = new TugOfWarPanel(this, layout);
		LearningPanel lp = new LearningPanel(this, layout);
		ThankYouScreenPanel ty = new ThankYouScreenPanel();

		add(welcomePanel, "Welcome");
		add(instructionsPanel, "Instructions");
		add(highScoresPanel, "HighScores");
		add(carChoose, "ChooseCar");
		add(gamePanel, "Game");
		add(tow, "Tug");
		add(lp, "Learn");
		add(highScoresPanelAfter, "HighScoresAfter");
		add(ty, "thank you");
	}
}

class WelcomePagePanel extends JPanel implements MouseListener, MouseMotionListener
{
	private JPanel parent;
	private CardLayout layout;
	private float alpha = 1.0f;
	private Timer timer;
	private Image gifImage;
	private boolean gifOrNo = true;
	private Image carBackground;

	private boolean leftButtonPressed;
	private boolean rightButtonPressed;
	private boolean leftButtonHovered;
	private boolean rightButtonHovered;

	private SoundPlayer buttonClickSound;
	private SoundPlayer IntroSound;

	private JButton muteButton;
	private boolean isMuted = false;
	private JButton skipButton;
	private boolean fadeCompleted = false;

	public WelcomePagePanel(JPanel gameHolder, CardLayout layout)
	{
		setDoubleBuffered(true); // prevent flicker

		leftButtonPressed = false;
		rightButtonPressed = false;
		leftButtonHovered = false;
		rightButtonHovered = false;
		repaint();

		this.parent = gameHolder;
		this.layout = layout;

		addMouseListener(this);
		addMouseMotionListener(this);

		buttonClickSound = new SoundPlayer("buttonClick.wav");
		IntroSound = new SoundPlayer("Intro.wav");

		startGIF();

		muteButton = new JButton(new ImageIcon("unmute.jpg"));
		muteButton.setVisible(false);
		muteButton.setBounds(10, 10, 50, 50);
		muteButton.setBorderPainted(false);
		muteButton.setContentAreaFilled(false);
		muteButton.setFocusPainted(false);
		muteButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				isMuted = !isMuted; // Toggle the isMuted variable
				SoundPlayer.setMuted(isMuted); // Update the global mute state
				if (isMuted)
				{
					muteButton.setIcon(new ImageIcon("mute.jpg"));
				} 
				else 
				{
					muteButton.setIcon(new ImageIcon("unmute.jpg"));
				}
			}
		});

		setLayout(null);
		add(muteButton);

		skipButton = new JButton("Skip");
		skipButton.setBounds(1000, 10, 100, 50);
		skipButton.setBorderPainted(true);
		skipButton.setContentAreaFilled(false);
		skipButton.setFocusPainted(false);
		skipButton.setForeground(Color.BLACK);
		skipButton.setBackground(Color.WHITE);
		skipButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				fadeCompleted = true;
				if (timer != null && timer.isRunning())
				{
					timer.stop();
				}
				alpha = 0.0f;
				gifOrNo = false;
				startFadeIn(40);
				repaint();
			}
		});
		add(skipButton);

		Timer soundDelay = new Timer(1000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!fadeCompleted)
				{
					IntroSound.play();
				}
				else
				{
					fadeCompleted = false;
				}
			}
		});
		soundDelay.setRepeats(false);
		soundDelay.start();
	}

	public void startGIF()
	{
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
				startFadeOut(100);
			}
		});

		delay.setRepeats(false);
		delay.start();
	}

	public void startFadeOut(int time)
	{
		if (timer != null && timer.isRunning())
		{
			timer.stop();
		}
		if (!fadeCompleted)
		{
			timer = new Timer(time, new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					alpha = Math.max(0.0f, alpha - 0.05f);
					if (alpha <= 0.0f)
					{
						alpha = 0.0f;
						timer.stop();
						gifOrNo = false;
						startFadeIn(100);
					}
					repaint();
				}
			});
			timer.start();
		}
	}

	public void startFadeIn(int time)
	{
		skipButton.setVisible(false);
		if (timer != null && timer.isRunning())
		{
			timer.stop();
		}

		alpha = 0.0f;
		timer = new Timer(time, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				alpha = Math.min(1.0f, alpha + 0.05f);
				if (alpha >= 1.0f)
				{
					alpha = 1.0f;
					timer.stop();
					muteButton.setVisible(true);
					fadeCompleted = true;
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

		Graphics2D g2d = (Graphics2D) g.create();

		if (gifOrNo && gifImage != null)
		{
			int finalWidth = 1166;
			int finalHeight = 975;
			int x = (getWidth() - finalWidth) / 2;
			int y = (getHeight() - finalHeight) / 2;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2d.drawImage(gifImage, x, y, finalWidth, finalHeight, this);
		}
		else if (carBackground != null)
		{
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			g2d.drawImage(carBackground, 0, 0, 1166, 972, this);

			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

			if (leftButtonPressed)
			{
				g2d.setColor(new Color(0, 0, 0, 180));
				g2d.fillRect(225, 855, 189, 53);
			}
			else if (rightButtonPressed)
			{
				g2d.setColor(new Color(0, 0, 0, 180));
				g2d.fillRect(586, 853, 376, 53);
			}
			else if (leftButtonHovered)
			{
				g2d.setColor(new Color(0, 0, 0, 80));
				g2d.fillRect(224, 855, 188, 53);
			}
			else if (rightButtonHovered)
			{
				g2d.setColor(new Color(0, 0, 0, 80));
				g2d.fillRect(586, 853, 376, 53);
			}
		}

		g2d.dispose();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		//secret command to skip to car selection
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			layout.show(parent, "ChooseCar");
		}

		int x = e.getX();
		int y = e.getY();

		if (!gifOrNo)
		{
			if (y > 855 && y < 896 && x > 232 && x < 412)
			{
				leftButtonPressed = true;
				repaint();
				buttonClickSound.play();
			}
			else if (y > 855 && y < 896 && x > 587 && x < 964)
			{
				rightButtonPressed = true;
				repaint();
				buttonClickSound.play();
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
			if (leftButtonPressed && y > 855 && y < 896 && x > 232 && x < 412)
			{
				IntroSound.stop();
				layout.show(parent, "Instructions");
			}
			else if (rightButtonPressed && y > 855 && y < 896 && x > 587 && x < 964)
			{
				layout.show(parent, "HighScores");
			}
		}

		leftButtonPressed = false;
		rightButtonPressed = false;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e){}

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
			if (y > 855 && y < 896 && x > 232 && x < 412)
			{
				leftButtonHovered = true;
				rightButtonHovered = false;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			else if (y > 856 && y < 895 && x > 587 && x < 964)
			{
				rightButtonHovered = true;
				leftButtonHovered = false;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			else
			{
				leftButtonHovered = false;
				rightButtonHovered = false;
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
	private Image gifImage;
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
		gifImage = new ImageIcon("Background.gif").getImage();

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
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.drawImage(gifImage, 101, 312, 962, 626, this);
		g.setColor(Color.WHITE);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("Arial", Font.BOLD, 18));
		g2d.setColor(Color.WHITE);

		String[] lines = 
			{
					"Game Setup",
					"You control a race car that competes against a bot car.",
					"Answer correctly for speed boosts; wrong answers slow you down.",
					"",
					"How to Play",
					"Enter your name, pick your car color, choose difficulty,",
					"and press Start Game to begin.",
					"Answer questions using the buttons that appear.",
					"If the bot wins, you lose!",
					"",
					"Learning Features",
					"At the end, review your mistakes,",
					"see correct answers, and get helpful explanations.",
					"",
					"Winning the Game",
					"Beat the bot to the finish line!",
					"Track your progress with the progress bar, and aim for a high score!"
			};

		int x = 300;
		int y = 461;
		int lineHeight = g.getFontMetrics().getHeight();

		for (int i = 0; i < lines.length; i++)
		{
			g2d.drawString(lines[i], x, y + i * lineHeight);
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
	private SoundPlayer notificationSound;
	private SoundPlayer openingSound;
	private JLabel carStatsLabel;
	private String carStats = "No car selected"; // Placeholder for car stats
	private boolean opponentCarSelected = false;
	Storer Storer = new Storer();

	public CarChoosePanel(GameHolder gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;
		addMouseListener(this);
		addMouseMotionListener(this);
		setLayout(null);
		addComponents();

		openingSound = new SoundPlayer("Opening.wav");
		carSelectSound = new SoundPlayer("carSelect.wav");
		buttonClickSound = new SoundPlayer("buttonClick.wav");
		notificationSound = new SoundPlayer("Notification.wav");
		imageForOpponent = null;
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
		back.setBounds(912, 900, 100, 38);
		add(back);

		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				buttonClickSound.play(); // Play button click sound
				if (carSelected && opponentCarSelected && nameEntered)
				{
					openingSound.play();
					layout.show(parent, "Game");
				}

				else
				{
					notificationSound.play(); // Play notification sound
					javax.swing.JOptionPane.showMessageDialog(parent, "Please select enter the nessesary information to proceed.", "Information Required", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					buttonClickSound.play(); // Play button click sound
				}
			}
		});
		next.setBounds(1037, 900, 100, 38);
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
				Storer.setName(nameField.getText());
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
		nameField.setBounds(643, 737, 500, 38);
		nameField.setEditable(true);

		//Add Easy medium and hard labels
		JSlider difficultySlider = new JSlider(0, 100, 50);
		difficultySlider.setMajorTickSpacing(20);
		difficultySlider.setMinorTickSpacing(5);
		difficultySlider.setPaintTicks(true);
		difficultySlider.setPaintLabels(true);
		difficultySlider.setValue(50); // Default to medium difficulty
		difficultySlider.setBounds(643, 812, 500, 62);

		difficultySlider.addChangeListener(e ->
		{
			int value = difficultySlider.getValue();
			Storer.setDifficultyLevel(value); // Set the difficulty based on the slider value
			Storer Storer = new Storer();
			try
			{
				opponentCarSelected = true; // Set to true when the slider is moved
				if (value < 20)
				{
					imageForOpponent = ImageIO.read(new File("Bicycle.png")); // Easy mode
					Storer.setOpponentCarImage("Bicycle.png");
				}
				else if (value < 40)
				{
					imageForOpponent = ImageIO.read(new File("Motorcycle.png"));
					Storer.setOpponentCarImage("Motorcycle.png");
				}
				else if (value < 60)
				{
					imageForOpponent = ImageIO.read(new File("CarNormal.png"));
					Storer.setOpponentCarImage("CarNormal.png");
				}
				else if (value < 80)
				{
					imageForOpponent = ImageIO.read(new File("CarSport.png"));
					Storer.setOpponentCarImage("CarSport.png");
				}
				else
				{
					imageForOpponent = ImageIO.read(new File("Rocket.png"));
					Storer.setOpponentCarImage("Rocket.png");
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
		difficultyLabel.setBounds(625, 787, 500, 25);
		difficultyLabel.setForeground(Color.BLACK);
		difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		add(difficultyLabel);
		carStatsLabel = new JLabel(carStats);
		carStatsLabel.setBounds(675, 375, 375, 188); // Increase width and height
		carStatsLabel.setFont(new Font("Arial", Font.BOLD, 16));
		carStatsLabel.setForeground(Color.BLUE);
		carStatsLabel.setVerticalAlignment(JLabel.TOP); // Align text to the top
		carStatsLabel.setHorizontalAlignment(JLabel.LEFT); // Align text to the left
		add(carStatsLabel);

	}

	@Override
	public void paintComponent(Graphics g)
	{
		int carWidth = 121;
		int carHeight = 238;

		super.paintComponent(g);
		g.drawImage(carOptions, 0, 0, 625 , 980, this);
		Graphics2D g2d = (Graphics2D) g;

		if (!opponentCarSelected)
		{
			g.drawImage(EmptyCar, 944, 131, carWidth, carHeight, this);
			java.awt.Stroke oldStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(2));
			g.drawRect(944, 131, carWidth, carHeight);
			g2d.setStroke(oldStroke);           
		}
		else
		{
			g.drawImage(imageForOpponent, 944, 131, carWidth, carHeight, this);
		}

		// Draw gray box if clicked
		if (xClick != 0)
		{
			carSelected = true; // Set carSelected to true if a car is clicked
			g.setColor(new Color (0, 0, 0, 80));
			g.fillRect(xClick, yClick, carWidth, carHeight);
			// Draw cropped section from carOptions into "Your Car" area
			g.drawImage(carOptionsNoBackgroundOriginal, 662, 131, 662 + carWidth, 131 + carHeight, xClick, yClick, xClick + carWidth, yClick + carHeight, this);
		}
		else
		{
			carStats = "No car selected"; // Reset the car stats
			carStatsLabel.setText(carStats); // Update the label text
			carSelected = false; // Reset carSelected if no car is clicked
			g.drawImage(EmptyCar, 675, 131, carWidth, carHeight, this);
			//add border to the empty car
			java.awt.Stroke oldStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(2));
			g.drawRect(675, 131, carWidth, carHeight);
			g2d.setStroke(oldStroke);
		}

		// Draw blue hover rectangle if hovering
		if (xHover != 0)
		{
			g.setColor(Color.BLUE);
			java.awt.Stroke oldStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(5));
			g.drawRect(xHover, yHover, carWidth, carHeight);
			g2d.setStroke(oldStroke);
		}

		g.setColor(new Color (31, 81, 255));
		g.setFont(new Font ("Amazone BT", Font.BOLD, 31));

		if (nameEntered && carSelected)
		{
			g.drawString(name + "Get Ready to Play!", 625, 31);
		}
		else if (nameEntered && !carSelected)
		{
			g.drawString(name + "Select a Car", 625, 31);
		}
		else if (!nameEntered && carSelected)
		{
			g.drawString("Enter Your Name", 625, 31);
		}
		else if (!nameEntered && !carSelected)
		{
			g.drawString("Enter Your Name and Select a Car", 625, 31);
		}

		g.drawLine(625, 44, 1166, 44);
		g.setFont(new Font ("Amazone BT", Font.PLAIN, 19));
		g.drawLine(688, 117, 768, 117);
		g.drawString("Your Car", 688, 113);
		g.setColor(Color.BLUE);
		g.drawLine(939, 117, 1076, 117);
		g.drawString("Opponents Car", 937, 113);
	}



	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e)
	{
		boolean clicked = true; // Flag to check if clicked box is toggled

		if (x > 5 && x < 130 && y > 0 && y < 245)
		{
			if (xClick == 5 && yClick == 7)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 5;
				yClick = 7;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car1"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 130 && x < 254 && y > 0 && y < 245)
		{
			if (xClick == 130 && yClick == 7)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 130;
				yClick = 7;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car2"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 254 && x < 376 && y > 0 && y < 245)
		{
			if (xClick == 254 && yClick == 7)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 254;
				yClick = 7;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car3"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 376 && x < 500 && y > 0 && y < 245)
		{
			if (xClick == 376 && yClick == 7)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 376;
				yClick = 7;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car4"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 500 && x < 625 && y > 0 && y < 245)
		{
			if (xClick == 500 && yClick == 7)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 500;
				yClick = 7;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car5"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 5 && x < 130 && y > 245 && y < 484)
		{
			if (xClick == 5 && yClick == 245)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 5;
				yClick = 245;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car6"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 130 && x < 253 && y > 245 && y < 484)
		{
			if (xClick == 130 && yClick == 245)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 130;
				yClick = 245;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car7"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 254 && x < 376 && y > 245 && y < 484)
		{
			if (xClick == 254 && yClick == 245)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 254;
				yClick = 245;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car8"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 376 && x < 500 && y > 245 && y < 484)
		{
			if (xClick == 376 && yClick == 245)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 376;
				yClick = 245;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car9"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 500 && x < 625 && y > 245 && y < 484)
		{
			if (xClick == 500 && yClick == 245)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 500;
				yClick = 245;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car10"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 5 && x < 128 && y > 500 && y < 725)
		{
			if (xClick == 5 && yClick == 490)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 5;
				yClick = 490;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car11"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 128 && x < 253 && y > 500 && y < 725)
		{
			if (xClick == 128 && yClick == 490)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 128;
				yClick = 490;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car12"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 253 && x < 376 && y > 500 && y < 725)
		{
			if (xClick == 253 && yClick == 490)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 253;
				yClick = 490;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car13"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 376 && x < 500 && y > 500 && y < 725)
		{
			if (xClick == 376 && yClick == 490)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 376;
				yClick = 490;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car14"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 500 && x < 625 && y > 500 && y < 725)
		{
			if (xClick == 500 && yClick == 490)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 500;
				yClick = 490;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car15"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 5 && x < 128 && y > 725 && y < 965)
		{
			if (xClick == 5 && yClick == 735)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 5;
				yClick = 735;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car16"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 128 && x < 253 && y > 725 && y < 965)
		{
			if (xClick == 128 && yClick == 735)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 128;
				yClick = 735;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car17"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 253 && x < 376 && y > 725 && y < 965)
		{
			if (xClick == 253 && yClick == 735)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 253;
				yClick = 735;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car18"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 376 && x < 500 && y > 725 && y < 965)
		{
			if (xClick == 376 && yClick == 735)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 376;
				yClick = 735;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car19"); // Reset the image
			}
			clicked = true;
		}
		else if (x > 500 && x < 625 && y > 725 && y < 965)
		{
			if (xClick == 500 && yClick == 735)
			{
				xClick = 0;
				yClick = 0;
				carSelected = false;

			}
			else
			{
				xClick = 500;
				yClick = 735;
				carSelected = true;
				carSelectSound.play();
				Storer.setCarImage("Car20"); // Reset the image
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
		if (carSelected)
		{
			Map<String, String> carColors = new HashMap<>();
			carColors.put("7,5", "Blue");
			carColors.put("7,130", "Dark Blue");
			carColors.put("7,254", "Blue");
			carColors.put("7,376", "Blue");
			carColors.put("7,500", "Blue");

			carColors.put("245,5", "Green");
			carColors.put("245,130", "Dark Green");
			carColors.put("245,254", "Olive Green");
			carColors.put("245,376", "Dark Green");
			carColors.put("245,500", "Green");

			carColors.put("490,5", "Dark Red");
			carColors.put("490,128", "Red");
			carColors.put("490,253", "Maroon Red");
			carColors.put("490,376", "Red");
			carColors.put("490,500", "Dark Red");

			carColors.put("735,5", "Yellow");
			carColors.put("735,128", "Gold Yellow");
			carColors.put("735,253", "Gold Yellow");
			carColors.put("735,376", "Yellow");
			carColors.put("735,500", "Yellow");

			Map<String, String> carTypes = new HashMap<>();
			carTypes.put("7,5", "Sport");
			carTypes.put("7,130", "SuperSport");
			carTypes.put("7,254", "Coupe");
			carTypes.put("7,376", "Mid-Size SUV");
			carTypes.put("7,500", "Full-Size SUV");

			carTypes.put("245,5", "Sport");
			carTypes.put("245,130", "Hyper");
			carTypes.put("245,254", "Coupe");
			carTypes.put("245,376", "Luxury");
			carTypes.put("245,500", "Hatchback");

			carTypes.put("490,5", "SuperSport");
			carTypes.put("490,128", "Sport");
			carTypes.put("490,253", "Coupe");
			carTypes.put("490,376", "Crossover");
			carTypes.put("490,500", "Luxury");

			carTypes.put("735,5", "Hybrid");
			carTypes.put("735,128", "Hyper");
			carTypes.put("735,253", "Coupe");
			carTypes.put("735,376", "Compact");
			carTypes.put("735,500", "Mini-Van");

			Map<String, String> carConditions = new HashMap<>();
			carConditions.put("7,5", "New");
			carConditions.put("7,130", "New");
			carConditions.put("7,254", "1 Year Used");
			carConditions.put("7,376", "New");
			carConditions.put("7,500", "5 Years Used");

			carConditions.put("245,5", "New");
			carConditions.put("245,130", "New");
			carConditions.put("245,254", "2 Years Used");
			carConditions.put("245,376", "Certified");
			carConditions.put("245,500", "1/2 Years Used");

			carConditions.put("490,5", "New");
			carConditions.put("490,128", "Certified");
			carConditions.put("490,253", "New");
			carConditions.put("490,376", "6 Years Used");
			carConditions.put("490,500", "Refurbished");

			carConditions.put("735,5", "New");
			carConditions.put("735,128", "1 Year Used");
			carConditions.put("735,253", "Certified");
			carConditions.put("735,376", "Refurbished");
			carConditions.put("735,500", "New");

			String key = yClick + "," + xClick;
			if (carColors.containsKey(key) && carSelected)
			{
				carStats = "<html>Car Stats:<br>"
						+ "- Color: " + carColors.get(key) + "<br>"
						+ "- Type: " + carTypes.get(key) + "<br>"
						+ "- Condition: " + carConditions.get(key) + "</html>";
				carStatsLabel.setText(carStats);
				carStatsLabel.revalidate();
				carStatsLabel.repaint();
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

		// Update hover position for each car region
		if (x > 5 && x < 130 && y > 0 && y < 245) 
		{
			xHover = 5;
			yHover = 7;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 130 && x < 254 && y > 0 && y < 245)
		{
			xHover = 130;
			yHover = 7;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 254 && x < 376 && y > 0 && y < 245) 
		{
			xHover = 254;
			yHover = 7;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 376 && x < 500 && y > 0 && y < 245) 
		{
			xHover = 376;
			yHover = 7;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if (x > 500 && x < 625 && y > 0 && y < 245) 
		{
			xHover = 500;
			yHover = 7;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 5 && x < 130 && y > 245 && y < 484)
		{
			xHover = 5;
			yHover = 245;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if (x > 130 && x < 254 && y > 245 && y < 484)
		{
			xHover = 130;
			yHover = 245;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 254 && x < 376 && y > 245 && y < 484) 
		{
			xHover = 254;
			yHover = 245;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 376 && x < 500 && y > 245 && y < 484)
		{
			xHover = 376;
			yHover = 245;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 500 && x < 625 && y > 245 && y < 484) 
		{
			xHover = 500;
			yHover = 245;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 5 && x < 130 && y > 484 && y < 724)
		{
			xHover = 5;
			yHover = 490;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 130 && x < 254 && y > 484 && y < 724) 
		{
			xHover = 130;
			yHover = 490;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 254 && x < 376 && y > 484 && y < 724)
		{
			xHover = 254;
			yHover = 490;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 376 && x < 500 && y > 484 && y < 724) 
		{
			xHover = 376;
			yHover = 490;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 500 && x < 625 && y > 484 && y < 724)
		{
			xHover = 500;
			yHover = 490;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 5 && x < 130 && y > 724 && y < 964)
		{
			xHover = 5;
			yHover = 735;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 130 && x < 254 && y > 724 && y < 964)
		{
			xHover = 130;
			yHover = 735;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else if (x > 254 && x < 376 && y > 724 && y < 964) 
		{
			xHover = 254;
			yHover = 735;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 376 && x < 500 && y > 724 && y < 964) 
		{
			xHover = 376;
			yHover = 735;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else if (x > 500 && x < 625 && y > 724 && y < 964)
		{
			xHover = 500;
			yHover = 735;
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		} 
		else 
		{
			xHover = 0; // Reset hover position
			yHover = 0; // Reset hover position
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		repaint();
	}
}

class Storer 
{
	private static String carNumber; // Make this static
	private static String carOpponentString;
	private int difficultyLevel;
	private static int timeRace;
	private static int timeTug;
	private static String name;
	private static int[] wrongGame1;
	private static int[] wrongGame2;

	public void setCarImage(String number) 
	{
		carNumber = number; // Set the static field
	}

	public String getCarImage() 
	{
		return carNumber; // Return the static field
	}

	public void setOpponentCarImage(String carType) 
	{
		carOpponentString = carType; // Set the static field
	}

	public String getOpponentCarImage() 
	{
		return carOpponentString; // Return the static field
	}
	public void setDifficultyLevel(int difficultyLevel) 
	{
		this.difficultyLevel = difficultyLevel;
	}
	public int getDifficultyLevel() 
	{
		return difficultyLevel;
	}
	public void setRaceGameTime(int timeRaceIn)
	{
		timeRace = timeRaceIn;
	}
	public int getRaceTime()
	{
		return timeRace;
	}
	public void setTugTime(int tugTimeIn)
	{
		timeTug = tugTimeIn;
	}
	public int getTugTime()
	{
		return timeTug;
	}
	public void setName(String nameIn)
	{
		name = nameIn;
	}
	public String getName()
	{
		return name;
	}
	public void setWrongFromGame1(int[] wrongIn) 
	{
		wrongGame1 = wrongIn;
	}

	public void setWrongFromGame2(int[] wrongIn) 
	{
		wrongGame2 = wrongIn;
	}

	public int[] getWrongFromGame1()
	{
		return wrongGame1;
	}

	public int[] getWrongFromGame2() 
	{
		return wrongGame2;
	}
}

class GamePanel extends JPanel
{
	private JPanel parent;
	private CardLayout layout;
	private Image trackImage, carsImage, opponentCarImage;
	private String carNumber, carOpponentString;

	private final int USER_CAR_X = 300;
	private final int FINISH_LINE = 54000;
	private final int TRACK_END = -56000;

	private double car1LogicalPos = 0;
	private double car2LogicalPos = 0;
	private double trackPos = 0;

	private boolean timerStarted = false;
	private boolean gameEnded = false;

	private double userSpeedBoost = 0;
	private Timer gameTimer;
	private double opponentSpeed;
	private Storer Storer = new Storer();

	private int questionNumber = 0;
	private String question = "";
	private String answerExplanation = "";
	private String[] answerChoices = new String[4];
	private String answer = "";
	boolean showButtons = true;
	JTextArea questionArea = new JTextArea();

	private SoundPlayer correct;
	private SoundPlayer incorrect;
	private JProgressBar progressBar;

	private JButton start;
	private JButton next;

	private int arrayNumber = 0;
	private int[] arrayNum = new int[100];

	GamePanel(JPanel gameHolder, CardLayout layout) 
	{
		importTextfiles();
		correct = new SoundPlayer("Correct.wav");
		incorrect = new SoundPlayer("InCorrect.wav");
		this.parent = gameHolder;
		this.layout = layout;
		setLayout(new BorderLayout());

		// Panel backgrounds
		setBackground(new Color(240, 248, 255)); // light pastel blue
		JLabel label = new JLabel("Racing Challenge!", SwingConstants.CENTER);
		label.setForeground(new Color(25, 25, 112)); // midnight blue
		label.setFont(new Font("Verdana", Font.BOLD, 36));

		progressBar = new JProgressBar(0, 54000);
		progressBar.setValue((int)trackPos);

		JPanel header = new JPanel(new GridLayout(3,1));
		header.setBackground(new Color(240, 248, 255));

		JPanel controlPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		controlPanel.setBackground(new Color(230, 240, 255)); // slightly deeper blue

		// Buttons
		JButton questionOneButton = new JButton("");
		JButton questionTwoButton = new JButton("");
		JButton questionThreeButton = new JButton("");
		JButton questionFourButton = new JButton("");
		start = new JButton("  Start  ");
		next = new JButton("Next");
		next.setVisible(false);

		// Button colors
		Color startRestartColor = new Color(0, 122, 204);  // blue for both start & restart
		Color nextColor = new Color(0, 166, 126);  // green for next

		start.setPreferredSize(new Dimension(130, 35));
		next.setPreferredSize(new Dimension(130, 35));

		// Shared styling helper
		Font customFont = new Font("Segoe UI", Font.BOLD, 16);
		Color primaryColor     = new Color(0, 166, 126);
		Color secondaryColor   = new Color(220, 220, 220);
		Color hoverShadow      = new Color(0, 0, 0, 30);

		// Method to apply flat + hover‐shadow style
		final BiConsumer<JButton, Color> styleButton = (btn, bg) -> 
		{
			btn.setFont(customFont);
			btn.setBackground(bg);
			btn.setForeground(bg.equals(secondaryColor) ? Color.BLACK : Color.WHITE);
			btn.setFocusPainted(false);
			btn.setOpaque(true);
			btn.setContentAreaFilled(true);
			btn.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(0, 0, 4, 0, hoverShadow),
					BorderFactory.createEmptyBorder(6, 20, 6, 20)
					));

			btn.addMouseListener(new MouseAdapter() 
			{
				public void mousePressed(MouseEvent e) 
				{
					btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
				}
				public void mouseExited(MouseEvent e) 
				{
					btn.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createMatteBorder(0, 0, 4, 0, hoverShadow),
							BorderFactory.createEmptyBorder(6, 20, 6, 20)
							));
				}
				public void mouseReleased(MouseEvent e) 
				{
					btn.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createMatteBorder(0, 0, 4, 0, hoverShadow),
							BorderFactory.createEmptyBorder(6, 20, 6, 20)
							));
				}
			});
		};

		// Apply styles
		styleButton.accept(questionOneButton, primaryColor);
		styleButton.accept(questionTwoButton, primaryColor);
		styleButton.accept(questionThreeButton, primaryColor);
		styleButton.accept(questionFourButton, primaryColor);
		styleButton.accept(start, startRestartColor);
		styleButton.accept(next, nextColor);

		// Size
		Dimension buttonSize = new Dimension(190, 60);
		for (JButton b : new JButton[]
				{ 
						questionOneButton, questionTwoButton,questionThreeButton, questionFourButton
				})
		{
			b.setPreferredSize(buttonSize);
		}

		// Listeners & logic (unchanged)
		start.addActionListener(e ->
		{
			if (!timerStarted)
			{
				start.setEnabled(false);
				runCountdown(3, () ->
				{
					startTimer();
					timerStarted = true;
					start.setText("Restart");
					start.setEnabled(true);
					questionOneButton.setText(answerChoices[0]);
					questionTwoButton.setText(answerChoices[1]);
					questionThreeButton.setText(answerChoices[2]);
					questionFourButton.setText(answerChoices[3]);
					questionArea.setText(question);
					updateProgressBar();
				});
			}
			else
			{
				questionOneButton.setText("");
				questionTwoButton.setText("");
				questionThreeButton.setText("");
				questionFourButton.setText("");
				questionArea.setText("");
				if (gameTimer != null) gameTimer.stop();
				car1LogicalPos = car2LogicalPos = trackPos = userSpeedBoost = 0;
				gameEnded = timerStarted = false;
				start.setText("  Start  ");
				progressBar.setValue(0);
				repaint();
			}
		});

		next.addActionListener(e->
		{
			layout.show(parent, "Tug");
		});

		JPanel controlButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		controlButtonPanel.setBackground(new Color(240, 248, 255, 0));
		controlButtonPanel.add(start);
		controlButtonPanel.add(next);

		questionOneButton.addActionListener(e -> handleAnswer(0, questionOneButton, questionTwoButton, questionThreeButton, questionFourButton));
		questionTwoButton.addActionListener(e -> handleAnswer(1, questionOneButton, questionTwoButton, questionThreeButton, questionFourButton));
		questionThreeButton.addActionListener(e -> handleAnswer(2, questionOneButton, questionTwoButton, questionThreeButton, questionFourButton));
		questionFourButton.addActionListener(e -> handleAnswer(3, questionOneButton, questionTwoButton, questionThreeButton, questionFourButton));

		Font font = new Font("Arial", Font.CENTER_BASELINE, 15);
		questionOneButton.setFont(font);
		questionTwoButton.setFont(font);
		questionThreeButton.setFont(font);
		questionFourButton.setFont(font);

		// Text area styling
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setEditable(false);
		questionArea.setFont(new Font("Serif", Font.PLAIN, 20));
		questionArea.setBackground(new Color(245, 245, 245));
		questionArea.setForeground(Color.BLACK);

		// Assemble
		header.add(label);
		header.add(questionArea);
		header.add(progressBar);
		add(header, BorderLayout.NORTH);

		controlPanel.add(questionOneButton);
		controlPanel.add(questionTwoButton);
		controlPanel.add(questionThreeButton);
		controlPanel.add(questionFourButton);
		add(controlPanel, BorderLayout.SOUTH);

		add(controlButtonPanel, BorderLayout.CENTER);
	}

	private void updateProgressBar()
	{
		int progress = (int) car1LogicalPos;
		progressBar.setValue(Math.min(progress, FINISH_LINE));
	}


	private void runCountdown(int seconds, Runnable onComplete)
	{
		Timer countdownTimer = new Timer(1000, null);
		final int[] count = 
			{ 
					seconds
			};
		start.setText(String.valueOf(count[0]));

		countdownTimer.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				count[0]--;
				if (count[0] > 0)
				{
					start.setText(String.valueOf(count[0]));	
				}
				else
				{
					countdownTimer.stop();
					onComplete.run();
				}
			}
		}); 

		countdownTimer.start();
	}


	private void importTextfiles()
	{
		Scanner in1 = null;
		Scanner in2 = null;
		File trigAnswerFile = new File("trigAnswerExplanations.txt");
		File trigQuestionFile = new File("trigMultipleChoice.txt");

		try
		{
			in1 = new Scanner(trigAnswerFile);
			in2 = new Scanner(trigQuestionFile);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File not found: " + e.getMessage());
		}

		int temp = 0;
		do
		{
			temp = (int) (Math.random() * 50 + 1);
		} while (questionNumber == temp);

		questionNumber = temp;
		System.out.println("Question Number: " + questionNumber);

		while (in1.hasNextLine())
		{
			String line = in1.nextLine();
			if (line.startsWith(questionNumber + ")"))
			{
				answerExplanation = line;
			}
		}

		while (in2.hasNextLine())
		{
			String questionLine = in2.nextLine();
			if (questionLine.startsWith(questionNumber + ")"))
			{
				question = questionLine;
				for (int i = 0; i < 4; i++)
				{
					if (in2.hasNextLine())
					{
						answerChoices[i] = in2.nextLine();
					}
				}
				in2.next();
				if (in2.hasNext())
				{
					answer = in2.next();
				}
			}
		}

		System.out.println("Question: " + question);
		for (String choice : answerChoices)
		{
			System.out.println("Choice: " + choice);
		}
		System.out.println("Answer: " + answer);
		System.out.println("Explanation: " + answerExplanation);
	}

	public void getCar()
	{
		if (carsImage != null && opponentCarImage != null && trackImage != null)
			return;

		carNumber = Storer.getCarImage();
		carOpponentString = Storer.getOpponentCarImage();
		int level = Storer.getDifficultyLevel();

		switch (carOpponentString)
		{
		case "Bicycle.png":
			opponentSpeed = 15 + level * 2;
			break;
		case "Motorcycle.png":
			opponentSpeed = 25 + level * 5;
			break;
		case "CarNormal.png":
			opponentSpeed = 35 + level * 6;
			break;
		case "CarSport.png":
			opponentSpeed = 40 + level * 7;
			break;
		case "Rocket.png":
			opponentSpeed = 50 + level * 8;
			break;
		}

		try
		{
			BufferedImage originalOpponentImage = ImageIO.read(new File(carOpponentString));
			int width = originalOpponentImage.getHeight();
			int height = originalOpponentImage.getWidth();
			BufferedImage rotatedImage = new BufferedImage(width, height, originalOpponentImage.getType());
			Graphics2D g2d = rotatedImage.createGraphics();
			AffineTransform transform = new AffineTransform();
			transform.translate(width / 2.0, height / 2.0);
			transform.rotate(Math.toRadians(90));
			transform.translate(-originalOpponentImage.getWidth() / 2.0, -originalOpponentImage.getHeight() / 2.0);
			g2d.drawImage(originalOpponentImage, transform, null);
			g2d.dispose();
			opponentCarImage = rotatedImage;

			carsImage = ImageIO.read(new File(carNumber + ".png"));
			trackImage = ImageIO.read(new File("Track.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void startTimer()
	{
		long startTime = System.currentTimeMillis(); // Record the start time
		gameTimer = new Timer(16, e -> 
		{	
			if (gameEnded)
				return;

			double userSpeed = 20 + 2 * userSpeedBoost;

			car1LogicalPos += userSpeed;
			car2LogicalPos += opponentSpeed;

			if (trackPos > TRACK_END) 
			{
				trackPos -= userSpeed;
			}

			// Update the progress bar here every tick
			updateProgressBar();

			// Check win/lose conditions
			if (car1LogicalPos >= FINISH_LINE && car2LogicalPos < FINISH_LINE)
			{
				Storer.setWrongFromGame1(Arrays.copyOf(arrayNum, arrayNumber));
				long endTime = System.currentTimeMillis(); // Record the end time
				Storer.setRaceGameTime((int) (endTime/1000 - startTime/1000));
				gameEnded = true;
				gameTimer.stop();
				JOptionPane.showMessageDialog(GamePanel.this, "You Win!", "Race Result", JOptionPane.INFORMATION_MESSAGE);
				next.setVisible(true);
				start.setVisible(false);
			} 
			else if (car2LogicalPos >= FINISH_LINE && car1LogicalPos >= FINISH_LINE) 
			{
				Storer.setWrongFromGame1(Arrays.copyOf(arrayNum, arrayNumber));
				long endTime = System.currentTimeMillis(); // Record the end time
				Storer.setRaceGameTime((int) (endTime/1000 - startTime/1000));
				gameEnded = true;
				gameTimer.stop();
				JOptionPane.showMessageDialog(GamePanel.this, "You Lose!", "Race Result", JOptionPane.INFORMATION_MESSAGE);
				next.setVisible(true);
				start.setVisible(false);
			}

			repaint();
		});

		gameTimer.start();
	}


	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		getCar();

		int shake = (int)(Math.random() * Math.max(0, userSpeedBoost / 2));
		int trackY = 150 + shake;

		if (trackImage != null)
		{
			g.drawImage(trackImage, (int) trackPos, trackY, 1166 * 50, 750, this);
		}

		if (carsImage != null)
		{
			g.drawImage(carsImage, USER_CAR_X, 170 + shake, 476, 242, this);
		}

		int opponentScreenX = USER_CAR_X + (int)(car2LogicalPos - car1LogicalPos);
		if (opponentCarImage != null)
		{
			if (car2LogicalPos >= FINISH_LINE)
			{
				g.drawImage(opponentCarImage, USER_CAR_X + (int)(FINISH_LINE - car1LogicalPos), 630, 476, 242, this);
			}
			else if (opponentScreenX < getWidth() && opponentScreenX > -400)
			{
				g.drawImage(opponentCarImage, opponentScreenX, 630, 476, 242, this);
			}
		}
	}

	public void handleAnswer(int selectedIndex, JButton b1, JButton b2, JButton b3, JButton b4)
	{
		if (timerStarted)
		{
			boolean isCorrect = answerChoices[selectedIndex].substring(3, 4).equals(answer);
			System.out.println(isCorrect + "\n");
			System.out.println("Entered: " + answerChoices[selectedIndex].substring(3,4) + "\tRight: " + answer);

			if (!gameEnded)
			{
				if (isCorrect)
				{
					correct.stop();
					correct.play();
					userSpeedBoost += 10; // Apply the boost 
					importTextfiles();
					b1.setText(answerChoices[0]);
					b2.setText(answerChoices[1]);
					b3.setText(answerChoices[2]);
					b4.setText(answerChoices[3]);
					questionArea.setText(question);
					Timer boostTimer = new Timer(2000, new ActionListener() 
					{ // 2000ms = 2 seconds
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							userSpeedBoost -= 10; // Revert the boost
							((Timer) e.getSource()).stop(); // Stop the timer
						}
					});
					boostTimer.setRepeats(false); // Ensure the timer only runs once
					boostTimer.start(); // Start the timer
				}
				else
				{
					arrayNum[arrayNumber++] = questionNumber;
					incorrect.stop();
					incorrect.play();
					userSpeedBoost -= 10; // Apply the slowdown

					importTextfiles();
					b1.setText(answerChoices[0]);
					b2.setText(answerChoices[1]);
					b3.setText(answerChoices[2]);
					b4.setText(answerChoices[3]);

					b1.setVisible(false);
					b2.setVisible(false);
					b3.setVisible(false);
					b4.setVisible(false);
					questionArea.setText("");

					Timer slowdownTimer = new Timer(2000, new ActionListener()
					{ // 2000ms = 2 seconds
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							userSpeedBoost += 10; // Revert the slowdown
							((Timer) e.getSource()).stop(); // Stop the timer
							b1.setVisible(true);
							b2.setVisible(true);
							b3.setVisible(true);
							b4.setVisible(true);
							questionArea.setText(question);
						}
					});
					slowdownTimer.setRepeats(false); // Ensure the timer only runs once
					slowdownTimer.start(); // Start the timer
				}
			}
		}
	}
}
class TugOfWarPanel extends JPanel
{
	private JPanel parent;
	private CardLayout layout;

	private BufferedImage userCarImage;
	private BufferedImage botCarImage;
	private BufferedImage ropeImage;

	private final double PULL_STEP = 50;
	private final double BOT_PULL_STEP = 20;
	private double botPullSpeed = 0;
	private final double WIN_THRESHOLD = 300;

	private final int CAR_WIDTH = 238;
	private final int CAR_HEIGHT = 131;

	private double ropeOffset = 0;
	private boolean timerStarted = false;
	private boolean gameEnded = false;

	private Timer gameTimer;

	private int questionNumber = 0;
	private String question = "";
	private String[] answerChoices = new String[4];
	private String answer = "";
	private String answerExplanation = "";

	private SoundPlayer correctSound;
	private SoundPlayer incorrectSound;

	private JTextArea questionArea = new JTextArea();
	private JButton[] answerButtons = new JButton[4];
	private JButton startButton = new JButton("Start");
	private JButton next = new JButton("Next");
	private Storer store = new Storer();
	private long startTime;
	private long endTime;
	private int tugArrayNumber = 0;
	private int[] tugArrayNum = new int[100];


	public TugOfWarPanel(JPanel gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;

		setLayout(new BorderLayout());
		setBackground(new Color(70, 80, 90));

		initControls();
	}

	private void initControls()
	{
		correctSound = new SoundPlayer("Correct.wav");
		incorrectSound = new SoundPlayer("InCorrect.wav");

		BiConsumer<JButton, Color> styleButton = (btn, bg) ->
		{
			Font customFont = new Font("Segoe UI", Font.BOLD, 18);
			Color hoverShadow = new Color(0, 0, 0, 50);
			btn.setFont(customFont);
			btn.setBackground(bg);
			btn.setForeground(Color.WHITE);
			btn.setFocusPainted(false);
			btn.setOpaque(true);
			btn.setContentAreaFilled(true);
			btn.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(0, 0, 6, 0, hoverShadow),
					BorderFactory.createEmptyBorder(10, 25, 10, 25)
					));
			btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn.addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					btn.setBorder(BorderFactory.createEmptyBorder(16, 25, 4, 25));
				}
				public void mouseExited(MouseEvent e)
				{
					btn.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createMatteBorder(0, 0, 6, 0, hoverShadow),
							BorderFactory.createEmptyBorder(10, 25, 10, 25)
							));
				}
				public void mouseReleased(MouseEvent e)
				{
					btn.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createMatteBorder(0, 0, 6, 0, hoverShadow),
							BorderFactory.createEmptyBorder(10, 25, 10, 25)
							));
				}
			});
		};

		Color startRestartColor = new Color(0, 150, 255);
		styleButton.accept(startButton, startRestartColor);

		styleButton.accept(next, Color.GREEN);
		next.setVisible(false);

		next.addActionListener(e->
		{
			layout.show(parent, "Learn");
		});

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
		topPanel.setOpaque(false);
		topPanel.add(startButton);
		topPanel.add(next);
		add(topPanel, BorderLayout.NORTH);

		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setEditable(false);
		questionArea.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		questionArea.setForeground(Color.WHITE);
		questionArea.setOpaque(false);
		questionArea.setBorder(new EmptyBorder(20, 50, 20, 50));
		add(questionArea, BorderLayout.CENTER);

		JPanel answersPanel = new JPanel(new GridLayout(2, 2, 20, 20));
		answersPanel.setOpaque(false);
		answersPanel.setBorder(new EmptyBorder(0, 50, 50, 50));
		Color answerButtonColor = new Color(0, 180, 140);

		for (int i = 0; i < 4; i++)
		{
			answerButtons[i] = new JButton();
			styleButton.accept(answerButtons[i], answerButtonColor);
			final int idx = i;
			answerButtons[i].addActionListener(e -> handleAnswer(idx));
			answerButtons[i].setEnabled(false);
			answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
			answersPanel.add(answerButtons[i]);
		}

		add(answersPanel, BorderLayout.SOUTH);

		startButton.addActionListener(e ->
		{
			if (!timerStarted)
			{
				startButton.setEnabled(false);
				runCountdown(3, () ->
				{
					timerStarted = true;
					loadNextQuestion();
					startGameLoop();
					startButton.setVisible(false);
				});
			}
		});
	}

	private void getImages()
	{
		try
		{
			String carNumber = store.getCarImage();
			String carOpponentString = store.getOpponentCarImage();

			switch (carOpponentString)
			{
			case "Bicycle.png":
				botPullSpeed = 0.1;
				break;
			case "Motorcycle.png":
				botPullSpeed = 0.2;
				break;
			case "CarNormal.png":
				botPullSpeed = 0.3;
				break;
			case "CarSport.png":
				botPullSpeed = 0.4;
				break;
			case "Rocket.png":
				botPullSpeed = 0.5;
				break;
			}

			BufferedImage originalUser = ImageIO.read(new File(carNumber + ".png"));
			BufferedImage scaledUser = new BufferedImage(CAR_WIDTH, CAR_HEIGHT, originalUser.getType());
			Graphics2D g2dUser = scaledUser.createGraphics();
			g2dUser.drawImage(originalUser, 0, 0, CAR_WIDTH, CAR_HEIGHT, null);
			g2dUser.dispose();
			BufferedImage rotatedUser = new BufferedImage(CAR_WIDTH, CAR_HEIGHT, scaledUser.getType());
			Graphics2D g2dRotUser = rotatedUser.createGraphics();
			AffineTransform rtUser = AffineTransform.getRotateInstance(Math.toRadians(180), CAR_WIDTH / 2.0, CAR_HEIGHT / 2.0);
			g2dRotUser.drawImage(scaledUser, rtUser, null);
			g2dRotUser.dispose();
			userCarImage = rotatedUser;

			BufferedImage originalBot = ImageIO.read(new File(carOpponentString));
			int w = originalBot.getHeight();
			int h = originalBot.getWidth();
			BufferedImage rotatedBot = new BufferedImage(w, h, originalBot.getType());
			Graphics2D g2dBot = rotatedBot.createGraphics();
			AffineTransform rtBot = new AffineTransform();
			rtBot.translate(w / 2.0, h / 2.0);
			rtBot.rotate(Math.toRadians(90));
			rtBot.translate(-originalBot.getWidth() / 2.0, -originalBot.getHeight() / 2.0);
			g2dBot.drawImage(originalBot, rtBot, null);
			g2dBot.dispose();
			botCarImage = rotatedBot;

			BufferedImage originalRope = ImageIO.read(new File("rope.png"));
			int targetWidth = 400;
			int targetHeight = originalRope.getHeight() * targetWidth / originalRope.getWidth();
			BufferedImage scaledRope = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gRope = scaledRope.createGraphics();
			gRope.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			gRope.drawImage(originalRope, 0, 0, targetWidth, targetHeight, null);
			gRope.dispose();
			ropeImage = scaledRope;
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(this, "Error loading game images: " + e.getMessage(), "Image Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void runCountdown(int seconds, Runnable onComplete)
	{
		JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Get Ready", ModalityType.APPLICATION_MODAL);
		JLabel lbl = new JLabel("", SwingConstants.CENTER);
		lbl.setFont(new Font("Arial", Font.BOLD, 80));
		lbl.setForeground(Color.WHITE);
		dialog.getContentPane().setBackground(new Color(0, 0, 0, 180));
		dialog.add(lbl);
		dialog.setUndecorated(true);
		dialog.setSize(250, 200);
		dialog.setLocationRelativeTo(this);

		Timer t = new Timer(1000, null);
		final int[] count = 
			{ 
					seconds
			};
		lbl.setText(String.valueOf(count[0]));
		t.addActionListener(ev ->
		{
			count[0]--;
			if (count[0] >= 1)
			{
				lbl.setText(String.valueOf(count[0]));
			}
			else if (count[0] == 0)
			{
				lbl.setText("Go!");
			}
			else
			{
				t.stop();
				dialog.dispose();
				onComplete.run();
			}
		});
		t.start();
		dialog.setVisible(true);
	}

	private void loadNextQuestion()
	{
		importTextfiles();
		questionArea.setText(question);
		for (int i = 0; i < 4; i++)
		{
			answerButtons[i].setText(answerChoices[i]);
			answerButtons[i].setEnabled(true);
		}
		repaint();
	}

	private void handleAnswer(int index)
	{
		if (!timerStarted || gameEnded) return;

		for (JButton b : answerButtons)
			b.setEnabled(false);

		boolean isCorrect = false;
		if (answerChoices[index] != null && answerChoices[index].length() >= 4)
		{
			isCorrect = answerChoices[index].substring(3, 4).equalsIgnoreCase(answer.trim());
		}

		if (isCorrect)
		{
			correctSound.play();
			ropeOffset -= PULL_STEP;
		}
		else
		{
			incorrectSound.play();
			ropeOffset += BOT_PULL_STEP;
			tugArrayNum[tugArrayNumber++] = questionNumber;
		}

		checkWinLoss();

		Timer delayTimer = new Timer(500, e ->
		{
			if (!gameEnded)
			{
				loadNextQuestion();
			}
		});
		delayTimer.setRepeats(false);
		delayTimer.start();

		repaint();
	}

	private void startGameLoop()
	{
		startTime = System.currentTimeMillis(); // Record the start time
		gameTimer = new Timer(30, e ->
		{
			if (gameEnded) return;

			ropeOffset += botPullSpeed;
			checkWinLoss();
			repaint();
		});
		gameTimer.start();
	}

	private void checkWinLoss()
	{
		if (ropeOffset <= -WIN_THRESHOLD)
		{
			endGame("You Win!");
		}
		else if (ropeOffset >= WIN_THRESHOLD)
		{
			endGame("You Lose!");
		}
	}

	private void endGame(String msg)
	{
		Storer storer = new Storer();
		storer.setWrongFromGame2(Arrays.copyOf(tugArrayNum, tugArrayNumber));

		endTime = System.currentTimeMillis(); // Record the end time
		store.setTugTime((int) (endTime - startTime) / 1000);
		next.setVisible(true);
		startButton.setVisible(false);
		gameEnded = true;
		timerStarted = false;
		if (gameTimer != null) 
			gameTimer.stop();

		for (JButton b : answerButtons) b.setEnabled(false);

		questionArea.setText("");
		JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
	}

	private void importTextfiles()
	{
		Scanner in1 = null;
		Scanner in2 = null;
		try
		{
			in1 = new Scanner(new File("trigAnswerExplanations.txt"));
			in2 = new Scanner(new File("trigMultipleChoice.txt"));

			int temp;
			do
			{
				temp = (int) (Math.random() * 50 + 1);
			}
			while (questionNumber == temp);
			questionNumber = temp;

			while (in1.hasNextLine())
			{
				String line = in1.nextLine();
				if (line.startsWith(questionNumber + ")"))
				{
					answerExplanation = line;
				}
			}

			while (in2.hasNextLine())
			{
				String questionLine = in2.nextLine();
				if (questionLine.startsWith(questionNumber + ")"))
				{
					question = questionLine;
					for (int i = 0; i < 4; i++)
					{
						if (in2.hasNextLine())
						{
							answerChoices[i] = in2.nextLine();
						}
					}
					in2.next();
					if (in2.hasNext())
					{
						answer = in2.next();
					}
				}
			}
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
		finally
		{
			if (in1 != null) in1.close();
			if (in2 != null) in2.close();
		}
		System.out.println("Question: " + question);
		for (String choice : answerChoices)
		{
			System.out.println("Choice: " + choice);
		}
		System.out.println("Answer: " + answer);
		System.out.println("Explanation: " + answerExplanation);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		getImages();

		Graphics2D g2d = (Graphics2D) g;

		int w = getWidth();
		int h = getHeight();

		int ropeW = ropeImage.getWidth();
		int minOffset = -(w - ropeW) / 2;
		int maxOffset = (w - ropeW) / 2;
		ropeOffset = Math.max(minOffset, Math.min(maxOffset, ropeOffset));

		int centerX = w / 2;
		int userWinX = centerX - (int) WIN_THRESHOLD;
		int botWinX = centerX + (int) WIN_THRESHOLD;

		int userCarX = userWinX - CAR_WIDTH + (int) ropeOffset;
		int botCarX = botWinX + (int) ropeOffset;

		int carY = h / 2 - CAR_HEIGHT / 2;

		g2d.drawImage(userCarImage, userCarX, carY, CAR_WIDTH, CAR_HEIGHT, this);
		g2d.drawImage(botCarImage, botCarX, carY, CAR_WIDTH, CAR_HEIGHT, this);

		int ropeHeight = ropeImage.getHeight();
		int ropeY = carY + CAR_HEIGHT / 2 - ropeHeight / 2;
		int ropeX = userCarX + CAR_WIDTH;
		int ropeWidth = botCarX - ropeX;

		if (ropeWidth > 0)
		{
			g2d.drawImage(ropeImage, ropeX, ropeY, ropeWidth, ropeHeight, this);
		}

		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(userCarX + CAR_WIDTH, ropeY + ropeHeight / 2, botCarX, ropeY + ropeHeight / 2);

		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(w / 2 - (int) WIN_THRESHOLD, carY, w / 2 - (int) WIN_THRESHOLD, carY + CAR_HEIGHT);
		g2d.drawLine(w / 2 + (int) WIN_THRESHOLD, carY, w / 2 + (int) WIN_THRESHOLD, carY + CAR_HEIGHT);

		g2d.setColor(Color.YELLOW);
		g2d.drawLine(w / 2, carY, w / 2, carY + CAR_HEIGHT);
	}
}

class SoundPlayer
{
	private Clip clip;
	private FloatControl volumeControl;
	private static boolean isMuted = false; // Global mute flag

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

class LearningPanel extends JPanel 
{
	private JTextArea textArea;
	private Storer storer;

	public LearningPanel(JPanel parent, CardLayout layout) 
	{
		setLayout(new BorderLayout());
		setBackground(new Color(240, 245, 255)); // Soft bluish background

		storer = new Storer();

		// Cool-looking Title
		JLabel title = new JLabel("Learning Review", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
		title.setForeground(new Color(25, 50, 95));
		title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

		// Fancy Text Area
		textArea = new JTextArea();
		textArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 16));
		textArea.setForeground(new Color(44, 44, 55));
		textArea.setBackground(new Color(255, 255, 255));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(200, 210, 240)),
				BorderFactory.createEmptyBorder(16, 18, 16, 18)
				));

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		scrollPane.getViewport().setBackground(new Color(250, 250, 255));

		// Cool Glass-style Button
		JButton nextButton = new JButton("→ Next");
		nextButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
		nextButton.setBackground(new Color(0, 166, 126));
		nextButton.setForeground(Color.WHITE);
		nextButton.setFocusPainted(false);
		nextButton.setPreferredSize(new Dimension(140, 45));
		nextButton.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(0, 140, 110), 1, true),
				BorderFactory.createEmptyBorder(8, 18, 8, 18)
				));
		nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		nextButton.setOpaque(true);

		// Add hover effect (optional)
		nextButton.addMouseListener(new java.awt.event.MouseAdapter() 
		{
			public void mouseEntered(java.awt.event.MouseEvent evt) 
			{
				nextButton.setBackground(new Color(0, 150, 115));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) 
			{
				nextButton.setBackground(new Color(0, 166, 126));
			}
		});

		nextButton.addActionListener(e -> 
		{
			layout.show(parent, "HighScoresAfter");
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(240, 245, 255));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 25, 10));
		buttonPanel.add(nextButton);

		// Add subtle shadow panel around text area
		JPanel centerWrapper = new JPanel(new BorderLayout());
		centerWrapper.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 20, 10, 20),
				BorderFactory.createLineBorder(new Color(220, 225, 245), 2)
				));
		centerWrapper.setBackground(Color.WHITE);
		centerWrapper.add(scrollPane, BorderLayout.CENTER);

		add(title, BorderLayout.NORTH);
		add(centerWrapper, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}


	@Override
	public void setVisible(boolean visible) 
	{
		super.setVisible(visible);
		if (!visible) return;

		StringBuilder display = new StringBuilder();
		String name = storer.getName();
		int timeRace = storer.getRaceTime();
		int timeTug = storer.getTugTime();
		String opponent = storer.getOpponentCarImage().substring(0, storer.getOpponentCarImage().length()-4);
		int[] wrong1 = storer.getWrongFromGame1();
		int[] wrong2 = storer.getWrongFromGame2();

		display.append("Name: ").append(name).append("\n");
		display.append("Opponent's Car: ").append(opponent).append("\n");
		display.append("Time taken in RaceGame: ").append(timeRace).append(" seconds\n");
		display.append("Time taken in TugGame: ").append(timeTug).append(" seconds\n\n");

		boolean anyWrong = false;
		if (wrong1 != null && wrong1.length > 0) 
		{
			display.append("Wrong Answers - Game 1:\n");
			processWrongAnswers(wrong1, display);
			anyWrong = true;
		}

		if (wrong2 != null && wrong2.length > 0) 
		{
			display.append("\nWrong Answers - Game 2:\n");
			processWrongAnswers(wrong2, display);
			anyWrong = true;
		}

		if (!anyWrong) 
		{
			display.append("No wrong answers! Excellent job!\n");
		}

		textArea.setText(display.toString());
		textArea.setCaretPosition(0);
	}

	private void processWrongAnswers(int[] questionNumbers, StringBuilder display) 
	{
		for (int qNum : questionNumbers)
		{
			if (qNum == 0) continue; // skip uninitialized entries

			String question = "";
			String[] choices = new String[4];
			String answer = "";
			String explanation = "";

			try (Scanner qScan = new Scanner(new File("trigMultipleChoice.txt"));
					Scanner eScan = new Scanner(new File("trigAnswerExplanations.txt"))) 
			{

				// Read from question file
				while (qScan.hasNextLine()) 
				{
					String line = qScan.nextLine();
					if (line.startsWith(qNum + ")")) 
					{
						question = line;
						for (int i = 0; i < 4 && qScan.hasNextLine(); i++) 
						{
							choices[i] = qScan.nextLine();
						}
						if (qScan.hasNext()) qScan.next(); // skip blank
						if (qScan.hasNext()) answer = qScan.next();
						break;
					}
				}

				// Read from explanation file
				while (eScan.hasNextLine()) 
				{
					String line = eScan.nextLine();
					if (line.startsWith(qNum + ")")) 
					{
						explanation = line;
						break;
					}
				}

			} 
			catch (IOException e) 
			{
				display.append("Error reading files for question #").append(qNum).append("\n\n");
				continue;
			}

			display.append("Question #").append(qNum).append(":\n");
			display.append(question).append("\n");
			for (String choice : choices) 
			{
				if (choice != null) display.append(choice).append("\n");
			}
			display.append("Correct Answer: ").append(answer).append("\n");
			display.append("Explanation: ").append(explanation).append("\n\n");
		}
	}
}

class HighScorePanel extends JPanel 
{
	private JTextArea textArea;
	private JPanel parent;
	private CardLayout layout;
	private static final String HIGH_SCORE_FILE = "HighScores.txt";

	public HighScorePanel(JPanel parent, CardLayout layout) 
	{
		this.parent = parent;
		this.layout = layout;

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		JLabel title = new JLabel("High Scores by Opponent", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 26));
		title.setBorder(new EmptyBorder(15, 0, 10, 0));
		add(title, BorderLayout.NORTH);

		textArea = new JTextArea();
		textArea.setFont(new Font("Consolas", Font.PLAIN, 16));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(new Color(245, 245, 250));
		textArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(180,180,200), 1, true),
				new EmptyBorder(12,12,12,12)
				));

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(new EmptyBorder(10, 30, 10, 30));
		add(scroll, BorderLayout.CENTER);

		JButton backBtn = new JButton("Back");
		backBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
		backBtn.setBackground(new Color(200, 60, 60));
		backBtn.setForeground(Color.BLACK);
		backBtn.setFocusPainted(false);
		backBtn.setPreferredSize(new Dimension(120, 40));
		backBtn.addActionListener(e -> layout.show(parent, "Welcome"));
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(new EmptyBorder(10,10,20,10));
		btnPanel.add(backBtn);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void setVisible(boolean visible) 
	{
		super.setVisible(visible);
		if (!visible) return;

		// Append current result with opponent


		// Read and process high scores
		Map<String, List<Record>> map = readRecordsGrouped();

		StringBuilder sb = new StringBuilder();
		for (String opp : map.keySet()) 
		{
			sb.append("Opponent: ").append(opp).append("\n");
			List<Record> list = map.get(opp);
			// sort by raceTime and tugTime
			list.sort(Comparator.comparingInt(r -> r.raceTime));
			sb.append(" Lowest RaceGame Times:\n");
			for (int i = 0; i < Math.min(3, list.size()); i++) 
			{
				Record r = list.get(i);
				sb.append("  " + (i+1) + ". " + r.name + ": " + r.raceTime + "s\n");
			}
			list.sort(Comparator.comparingInt(r -> r.tugTime));
			sb.append(" Lowest TugGame Times:\n");
			for (int i = 0; i < Math.min(3, list.size()); i++) 
			{
				Record r = list.get(i);
				sb.append("  " + (i+1) + ". " + r.name + ": " + r.tugTime + "s\n");
			}
			sb.append("\n");
		}

		textArea.setText(sb.toString());
		textArea.setCaretPosition(0);
	}

	private Map<String, List<Record>> readRecordsGrouped() 
	{
		Map<String, List<Record>> map = new LinkedHashMap<>();
		File f = new File(HIGH_SCORE_FILE);
		if (!f.exists()) return map;

		try (BufferedReader br = new BufferedReader(new FileReader(f))) 
		{
			String line;
			while ((line = br.readLine()) != null) 
			{
				String[] parts = line.split("\\|");
				if (parts.length < 4) continue;
				String name = parts[0], opp = parts[1];
				int rt = Integer.parseInt(parts[2]);
				int tt = Integer.parseInt(parts[3]);
				Record r = new Record(name, rt, tt);
				map.computeIfAbsent(opp, k -> new ArrayList<>()).add(r);
			}
		} 
		catch (IOException e) 
		{
			// ignore
		}
		return map;
	}

	private static class Record 
	{
		String name;
		int raceTime;
		int tugTime;
		Record(String n, int r, int t)
		{
			name = n; raceTime = r; tugTime = t; 
		}
	}
}

class HighScorePanelAfter extends JPanel 
{
	private JTextArea textArea;
	private Storer storer;
	private JPanel parent;
	private CardLayout layout;
	private static final String HIGH_SCORE_FILE = "HighScores.txt";

	public HighScorePanelAfter(JPanel parent, CardLayout layout) 
	{
		this.parent = parent;
		this.layout = layout;
		this.storer = new Storer();

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		JLabel title = new JLabel("High Scores by Opponent", SwingConstants.CENTER);
		title.setFont(new Font("Segoe UI", Font.BOLD, 26));
		title.setBorder(new EmptyBorder(15, 0, 10, 0));
		add(title, BorderLayout.NORTH);

		textArea = new JTextArea();
		textArea.setFont(new Font("Consolas", Font.PLAIN, 16));
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(new Color(245, 245, 250));
		textArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(180,180,200), 1, true),
				new EmptyBorder(12,12,12,12)
				));

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(new EmptyBorder(10, 30, 10, 30));
		add(scroll, BorderLayout.CENTER);
		
		JButton finish = new JButton("Finish");
		finish.setFont(new Font("Segoe UI", Font.BOLD, 18));
		finish.setBackground(new Color(200, 60, 60));
		finish.setForeground(Color.BLACK);
		finish.setFocusPainted(false);
		finish.setPreferredSize(new Dimension(120, 40));
		finish.addActionListener(e -> layout.show(parent, "thank you"));

		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		btnPanel.setBorder(new EmptyBorder(10,10,20,10));
		btnPanel.add(finish);
		add(btnPanel, BorderLayout.SOUTH);
	}

	@Override
	public void setVisible(boolean visible) 
	{
		super.setVisible(visible);
		if (!visible) 
			return;

		// Append current result with opponent
		appendCurrentResult();

		// R ead and process high scores
		Map<String, List<Record>> map = readRecordsGrouped();

		StringBuilder sb = new StringBuilder();
		for (String opp : map.keySet()) 
		{
			sb.append("Opponent: ").append(opp).append("\n");
			List<Record> list = map.get(opp);
			// sort by raceTime and tugTime
			list.sort(Comparator.comparingInt(r -> r.raceTime));
			sb.append(" Lowest RaceGame Times:\n");
			for (int i = 0; i < Math.min(3, list.size()); i++) 
			{
				Record r = list.get(i);
				sb.append("  " + (i+1) + ". " + r.name + ": " + r.raceTime + "s\n");
			}
			list.sort(Comparator.comparingInt(r -> r.tugTime));
			sb.append(" Lowest TugGame Times:\n");
			for (int i = 0; i < Math.min(3, list.size()); i++) 
			{
				Record r = list.get(i);
				sb.append("  " + (i+1) + ". " + r.name + ": " + r.tugTime + "s\n");
			}
			sb.append("\n");
		}

		textArea.setText(sb.toString());
		textArea.setCaretPosition(0);
	}

	private void appendCurrentResult() 
	{
		String name = storer.getName();
		String oppFile = storer.getOpponentCarImage();
		int timeRace = storer.getRaceTime();
		int timeTug = storer.getTugTime();

		// Validate: skip if name or opponent is missing or times are 0 or negative
		if (name == null || name.trim().isEmpty() ||
				oppFile == null || timeRace <= 0 || timeTug <= 0)
		{
			return; // don't write invalid entry
		}

		String opponent = oppFile.replaceFirst("\\.png$", "");
		String entry = String.format("%s|%s|%d|%d", name.trim(), opponent, timeRace, timeTug);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE, true))) 
		{
			bw.newLine();
			bw.write(entry);
		} 
		catch (IOException e)
		{
			System.err.println("Failed to save high score: " + e.getMessage());
		}
	}


	private Map<String, List<Record>> readRecordsGrouped() 
	{
		Map<String, List<Record>> map = new LinkedHashMap<>();
		File f = new File(HIGH_SCORE_FILE);
		if (!f.exists()) return map;

		try (BufferedReader br = new BufferedReader(new FileReader(f))) 
		{
			String line;
			while ((line = br.readLine()) != null) 
			{
				String[] parts = line.split("\\|");
				if (parts.length < 4) continue;
				String name = parts[0], opp = parts[1];
				int rt = Integer.parseInt(parts[2]);
				int tt = Integer.parseInt(parts[3]);
				Record r = new Record(name, rt, tt);
				map.computeIfAbsent(opp, k -> new ArrayList<>()).add(r);
			}
		} 
		catch (IOException e) 
		{
			// ignore
		}
		return map;
	}

	private static class Record 
	{
		String name;
		int raceTime;
		int tugTime;
		Record(String n, int r, int t)
		{
			name = n; raceTime = r; tugTime = t; 
		}
	}
}class ThankYouScreenPanel extends JPanel implements ActionListener {
    private final java.util.List<Particle> particles = new ArrayList<>();
    private final Timer timer;
    private float rotationAngle = 0f;
    private float gradientOffset = 0;
    private float hue = 0f;
    private final GradientButton quitButton;

    public ThankYouScreenPanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        // Create and style a custom gradient button
        quitButton = new GradientButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 20));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBounds(1000, 10, 150, 50);
        quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);

        // Spawn initial particles
        for (int i = 0; i < 100; i++) {
            particles.add(new Particle(getWidth(), getHeight()));
        }

        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int w = getWidth(), h = getHeight();

        // Animate diagonal gradient and hue shift
        gradientOffset += 1;
        hue += 0.001f;
        if (hue > 1f) hue = 0f;
        Color c1 = Color.getHSBColor(hue, 1f, 0.6f);
        Color c2 = Color.getHSBColor((hue + 0.4f) % 1f, 1f, 0.9f);
        GradientPaint bg = new GradientPaint(
            (gradientOffset % w), 0, c1,
            0, (gradientOffset % h), c2
        );
        g2.setPaint(bg);
        g2.fillRect(0, 0, w, h);

        // Rotating logo
        AffineTransform old = g2.getTransform();
        g2.translate(w/2, h/3);
        g2.rotate(rotationAngle);
        g2.setColor(new Color(255, 220, 0, 200));
        g2.fillOval(-50, -50, 100, 100);
        g2.setTransform(old);

        // Particles
        for (Particle p : particles) p.draw(g2);

        // Text
        String msg = "Thank You for Playing!";
        Font font = new Font("Arial Black", Font.BOLD, 48);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int textW = fm.stringWidth(msg);
        int x = (w - textW) / 2;
        int y = h * 2 / 3;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(msg, x + 4, y + 4);

        // Animated text gradient (matching background hues)
        Color t1 = Color.getHSBColor((hue + 0.2f) % 1f, 1f, 1f);
        Color t2 = Color.getHSBColor((hue + 0.6f) % 1f, 1f, 1f);
        GradientPaint tg = new GradientPaint(x, y - fm.getAscent(), t1, x + textW, y, t2);
        g2.setPaint(tg);
        g2.drawString(msg, x, y);
        g2.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int w = getWidth(), h = getHeight();
        if (particles.size() < 150) particles.add(new Particle(w, h));
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.update();
            if (!p.isAlive()) it.remove();
        }
        rotationAngle += 0.01f;
        repaint();
    }

    /**
     * Custom JButton with rounded gradient background and hover effect
     */
    private static class GradientButton extends JButton {
        private boolean hover = false;
        GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            });
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth(), h = getHeight();
            float f = hover ? 1.1f : 1f;
            GradientPaint gp = new GradientPaint(0, 0,
                getBackground().brighter(), 0, h,
                getBackground().darker());
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, w, h, 20, 20);
            g2.setComposite(AlphaComposite.SrcOver);
            super.paintComponent(g2);
            g2.dispose();
        }
        @Override public void setBackground(Color bg) { super.setBackground(bg); }
        @Override public void setForeground(Color fg) { super.setForeground(fg); }
    }

    /** Basic particle class for sparkle effect. */
    static class Particle {
        float x, y, life, maxLife;
		double vy;
		double vx;
        Color color;
        Particle(int width, int height) {
            Random rnd = new Random();
            x = width/2f; y = height/3f;
            float ang = rnd.nextFloat()*2*(float)Math.PI;
            float spd = rnd.nextFloat()*4+1;
            vx = Math.cos(ang)*spd;
            vy = Math.sin(ang)*spd;
            maxLife = life = rnd.nextFloat()*60+30;
            color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1f);
        }
        void update() { x+=vx; y+=vy; life--; }
        boolean isAlive() { return life>0; }
        void draw(Graphics2D g2) {
            float a = life/maxLife;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,a));
            int s = (int)(a*8+2);
            g2.setColor(color);
            g2.fillOval((int)x,(int)y,s,s);
            g2.setComposite(AlphaComposite.SrcOver);
        }
    }
}
