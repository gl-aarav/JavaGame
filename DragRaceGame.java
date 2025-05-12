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
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

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
	private boolean gifOrNo = true;
	private Image carBackground;

	private boolean leftButtonPressed;
	private boolean rightButtonPressed;
	private boolean leftButtonHovered;
	private boolean rightButtonHovered;

	private SoundPlayer buttonClickSound;
	private SoundPlayer carAccelerationSound;

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
		carAccelerationSound = new SoundPlayer("accelerate.wav");

		startGIF();

		muteButton = new JButton(new ImageIcon("unmute.jpg"));
		muteButton.setVisible(false);
		muteButton.setBounds(10, 10, 50, 50);
		muteButton.setBorderPainted(false);
		muteButton.setContentAreaFilled(false);
		muteButton.setFocusPainted(false);
		muteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
				carAccelerationSound.stop();
				if (timer != null && timer.isRunning())
				{
					carAccelerationSound.stop();
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
					carAccelerationSound.play();
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
						carAccelerationSound.stop();
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

		String[] lines = {
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
	private JLabel carStatsLabel;
	private String carStats = "No car selected"; // Placeholder for car stats
	private boolean opponentCarSelected = false;

	public CarChoosePanel(GameHolder gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;
		this.layout = layout;
		addMouseListener(this);
		addMouseMotionListener(this);
		setLayout(null);
		addComponents();

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
					carSelectSound.play(); // Play car selection sound
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
			ImageStorer imageStorer = new ImageStorer();
			try
			{
				opponentCarSelected = true; // Set to true when the slider is moved
				if (value < 20)
				{
					imageForOpponent = ImageIO.read(new File("Bicycle.png")); // Easy mode
					imageStorer.setOpponentCarImage("Bicycle.png");
				}
				else if (value < 40)
				{
					imageForOpponent = ImageIO.read(new File("Motorcycle.png"));
					imageStorer.setOpponentCarImage("Motorcycle.png");
				}
				else if (value < 60)
				{
					imageForOpponent = ImageIO.read(new File("CarNormal.png"));
					imageStorer.setOpponentCarImage("CarNormal.png");
				}
				else if (value < 80)
				{
					imageForOpponent = ImageIO.read(new File("CarSport.png"));
					imageStorer.setOpponentCarImage("CarSport.png");
				}
				else
				{
					imageForOpponent = ImageIO.read(new File("Rocket.png"));
					imageStorer.setOpponentCarImage("Rocket.png");
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
		ImageStorer imageStorer = new ImageStorer();

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
				imageStorer.setCarImage("Car1"); // Reset the image
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
				imageStorer.setCarImage("Car2"); // Reset the image
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
				imageStorer.setCarImage("Car3"); // Reset the image
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
				imageStorer.setCarImage("Car4"); // Reset the image
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
				imageStorer.setCarImage("Car5"); // Reset the image
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
				imageStorer.setCarImage("Car6"); // Reset the image
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
				imageStorer.setCarImage("Car7"); // Reset the image
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
				imageStorer.setCarImage("Car8"); // Reset the image
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
				imageStorer.setCarImage("Car9"); // Reset the image
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
				imageStorer.setCarImage("Car10"); // Reset the image
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
				imageStorer.setCarImage("Car11"); // Reset the image
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
				imageStorer.setCarImage("Car12"); // Reset the image
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
				imageStorer.setCarImage("Car13"); // Reset the image
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
				imageStorer.setCarImage("Car14"); // Reset the image
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
				imageStorer.setCarImage("Car15"); // Reset the image
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
				imageStorer.setCarImage("Car16"); // Reset the image
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
				imageStorer.setCarImage("Car17"); // Reset the image
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
				imageStorer.setCarImage("Car18"); // Reset the image
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
				imageStorer.setCarImage("Car19"); // Reset the image
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
				imageStorer.setCarImage("Car20"); // Reset the image
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
			carColors.put("490,130", "Red");
			carColors.put("490,254", "Maroon Red");
			carColors.put("490,376", "Red");
			carColors.put("490,500", "Dark Red");

			carColors.put("735,5", "Yellow");
			carColors.put("735,130", "Gold Yellow");
			carColors.put("735,254", "Gold Yellow");
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
			carTypes.put("490,130", "Sport");
			carTypes.put("490,254", "Coupe");
			carTypes.put("490,376", "Crossover");
			carTypes.put("490,500", "Luxury");

			carTypes.put("735,5", "Hybrid");
			carTypes.put("735,130", "Hyper");
			carTypes.put("735,254", "Coupe");
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
			carConditions.put("490,130", "Certified");
			carConditions.put("490,254", "New");
			carConditions.put("490,376", "6 Years Used");
			carConditions.put("490,500", "Refurbished");

			carConditions.put("735,5", "New");
			carConditions.put("735,130", "1 Year Used");
			carConditions.put("735,254", "Certified");
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

class ImageStorer {
	private static String carNumber; // Make this static
	private static String carOpponentString;

	public void setCarImage(String number) {
		carNumber = number; // Set the static field
	}

	public String getCarImage() {
		return carNumber; // Return the static field
	}

	public void setOpponentCarImage(String carType) {
		carOpponentString = carType; // Set the static field
	}

	public String getOpponentCarImage() {
		return carOpponentString; // Return the static field
	}
}

class GamePanel extends JPanel {
	private JPanel parent;
	private CardLayout layout;
	private Image trackImage, carsImage, opponentCarImage;
	private String carNumber, carOpponentString;

	private final int USER_CAR_X = 300;
	private final int FINISH_LINE = 26400;
	private final int TRACK_END = - 27200;

	private double car1LogicalPos = 0;
	private double car2LogicalPos = 0;
	private double trackPos = 0;

	private boolean timerStarted = false;
	private boolean gameEnded = false;

	private double userSpeedBoost = 0;
	private Timer gameTimer;

	public GamePanel(JPanel gameHolder, CardLayout layout) {
		this.parent = gameHolder;
		this.layout = layout;
		setLayout(new BorderLayout());

		// Title
		JLabel label = new JLabel("Racing Challenge!", SwingConstants.CENTER);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial", Font.BOLD, 32));
		add(label, BorderLayout.NORTH);

		// Bottom control buttons
		JPanel controlPanel = new JPanel(new GridLayout(1, 5, 10, 10));
		JButton startButton = new JButton("Start");
		JButton faster = new JButton("Go Faster");
		JButton slower = new JButton("Go Slower");
		JButton boost = new JButton("Small Boost");
		JButton slowdown = new JButton("Small Slowdown");

		startButton.addActionListener(e -> {
			if (!timerStarted) {
				startTimer();
				timerStarted = true;
				startButton.setText("Restart");
			} else {
				car1LogicalPos = 0;
				car2LogicalPos = 0;
				trackPos = 0;
				userSpeedBoost = 0;
				gameEnded = false;
				timerStarted = false;
				startButton.setText("Start");
				repaint();
			}
		});

		faster.addActionListener(e -> userSpeedBoost += 5);
		slower.addActionListener(e -> userSpeedBoost -= 5);
		boost.addActionListener(e -> {
			if (!gameEnded) {
				userSpeedBoost += 10; // Apply the boost
				Timer boostTimer = new Timer(2000, new ActionListener() { // 2000ms = 2 seconds
					@Override
					public void actionPerformed(ActionEvent e) {
						userSpeedBoost -= 10; // Revert the boost
						((Timer) e.getSource()).stop(); // Stop the timer
					}
				});
				boostTimer.setRepeats(false); // Ensure the timer only runs once
				boostTimer.start(); // Start the timer
			}
		});
		slowdown.addActionListener(e -> {
			if (!gameEnded) {
				userSpeedBoost -= 10; // Apply the slowdown
				Timer slowdownTimer = new Timer(2000, new ActionListener() { // 2000ms = 2 seconds
					@Override
					public void actionPerformed(ActionEvent e) {
						userSpeedBoost += 10; // Revert the slowdown
						((Timer) e.getSource()).stop(); // Stop the timer
					}
				});
				slowdownTimer.setRepeats(false); // Ensure the timer only runs once
				slowdownTimer.start(); // Start the timer
			}
		});


		controlPanel.add(startButton);
		controlPanel.add(faster);
		controlPanel.add(slower);
		controlPanel.add(boost);
		controlPanel.add(slowdown);
		add(controlPanel, BorderLayout.SOUTH);
	}

	public void getCar() {
		if (carsImage != null && opponentCarImage != null && trackImage != null) return;

		ImageStorer imageStorer = new ImageStorer();
		carNumber = imageStorer.getCarImage();
		carOpponentString = imageStorer.getOpponentCarImage();
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startTimer() {
		gameTimer = new Timer(16, e -> {
			if (gameEnded) return;

			double userSpeed = 10 + Math.random() * 5 + userSpeedBoost;
			double opponentSpeed = 18 + Math.random() * 3;

			car1LogicalPos += userSpeed;
			car2LogicalPos += opponentSpeed;

			if (trackPos > TRACK_END) {
				trackPos -= userSpeed;
			}

			if (car1LogicalPos >= FINISH_LINE) {
				gameEnded = true;
				gameTimer.stop();
				JOptionPane.showMessageDialog(GamePanel.this, "You Win!", "Race Result", JOptionPane.INFORMATION_MESSAGE);
			} else if (car2LogicalPos >= FINISH_LINE) {
				gameEnded = true;
				gameTimer.stop();
				JOptionPane.showMessageDialog(GamePanel.this, "You Lose! Opponent Passed You!", "Race Result", JOptionPane.ERROR_MESSAGE);
			}

			repaint();
		});

		gameTimer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		getCar();

		// Camera shake
		int shake = (int)(Math.random() * Math.max(0, userSpeedBoost / 2));
		int trackY = 100 + shake;

		// Draw track
		if (trackImage != null) {
			g.drawImage(trackImage, (int) trackPos, trackY, (int) (1166 * 25), 800, this);
		}

		// Draw player car
		if (carsImage != null) {
			g.drawImage(carsImage, USER_CAR_X, 170 + shake, 238 * 2, 121 * 2, this);
		}

		// Draw opponent car
		int opponentScreenX = USER_CAR_X + (int)(car2LogicalPos - car1LogicalPos);
		if (opponentCarImage != null) {
			if (gameEnded && car2LogicalPos >= FINISH_LINE) {
				g.drawImage(opponentCarImage, getWidth() + 100, 580, 238 * 2, 121 * 2, this);
			} else if (opponentScreenX < getWidth() && opponentScreenX > -400) {
				g.drawImage(opponentCarImage, opponentScreenX, 580, 238 * 2, 121 * 2, this);
			}
		}
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