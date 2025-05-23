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

// =============================
// AWT (Abstract Window Toolkit)
// =============================
import java.awt.AlphaComposite;         // Enables setting transparency for drawing
import java.awt.BasicStroke;           // Allows customizing stroke width for shapes
import java.awt.BorderLayout;          // Layout manager for arranging components in five regions
import java.awt.CardLayout;            // Layout manager for switching between multiple panels
import java.awt.Color;                 // Represents colors
import java.awt.Cursor;                // Allows customizing the mouse cursor
import java.awt.Dialog.ModalityType;  // Defines modality type for dialogs
import java.awt.Dimension;            // Represents width and height dimensions
import java.awt.FlowLayout;           // Simple layout manager that arranges components in a line
import java.awt.Font;                 // Defines text font properties
import java.awt.FontMetrics;          // Helps in measuring font sizes
import java.awt.GradientPaint;        // Paints a gradient color fill
import java.awt.Graphics;             // Base class for drawing on components
import java.awt.Graphics2D;           // Advanced 2D drawing features
import java.awt.GridLayout;           // Layout manager for arranging components in a grid
import java.awt.Image;                // Represents graphical images
import java.awt.RenderingHints;       // Controls rendering quality (e.g., antialiasing)
import java.awt.Toolkit;              // Provides access to platform-specific resources (like screen size)

// ===============
// Event Handling
// ===============
import java.awt.event.ActionEvent;         // Represents an action event (e.g., button click)
import java.awt.event.ActionListener;      // Interface for receiving action events
import java.awt.event.ComponentAdapter;    // Adapter class for component events
import java.awt.event.ComponentEvent;      // Event when a component is resized, moved, etc.
import java.awt.event.MouseAdapter;        // Adapter class for mouse events
import java.awt.event.MouseEvent;          // Represents mouse-related events
import java.awt.event.MouseListener;       // Interface for receiving mouse events
import java.awt.event.MouseMotionListener; // Interface for receiving mouse motion events

// ====================
// Graphics & Imaging
// ====================
import java.awt.geom.AffineTransform;  // Used for 2D transformations like scaling and rotating
import java.awt.image.BufferedImage;   // Represents an image with an accessible buffer of pixels

// ====================
// File I/O and Streams
// ====================
import java.io.BufferedReader;         // For reading text efficiently
import java.io.BufferedWriter;         // For writing text efficiently
import java.io.File;                   // Represents file and directory pathnames
import java.io.FileNotFoundException;  // Exception when a file is not found
import java.io.FileReader;             // For reading character files
import java.io.FileWriter;             // For writing character files
import java.io.IOException;            // Signals I/O errors

// ============
// Collections
// ============
import java.util.ArrayList;           // Resizable array implementation
import java.util.Arrays;              // Utility methods for arrays
import java.util.Comparator;          // Interface for comparing objects
import java.util.HashMap;             // Hash table implementation of the Map interface
import java.util.Hashtable;           // Legacy hash table
import java.util.Iterator;            // Iterator to traverse collections
import java.util.LinkedHashMap;       // Maintains insertion order in a hash map
import java.util.List;                // Interface for ordered collections
import java.util.Map;                 // Interface for key-value mappings
import java.util.Random;              // Generates random numbers

// ===============
// Image & Sound
// ===============
import javax.imageio.ImageIO;         // Reads and writes image files
import javax.sound.sampled.AudioInputStream;         // Stream for audio data
import javax.sound.sampled.AudioSystem;              // Manages audio playback
import javax.sound.sampled.Clip;                     // Plays audio clips
import javax.sound.sampled.FloatControl;             // Adjusts audio properties like volume
import javax.sound.sampled.LineUnavailableException; // Exception when audio line can't be opened
import javax.sound.sampled.UnsupportedAudioFileException; // Exception for unsupported audio formats

// =================
// Swing Components
// =================
import javax.swing.BorderFactory;     // Creates borders for components
import javax.swing.ImageIcon;         // Icon implementation using images
import javax.swing.JButton;           // Push button component
import javax.swing.JCheckBox;         // Check box component
import javax.swing.JDialog;           // Pop-up dialog window
import javax.swing.JFrame;            // Top-level application window
import javax.swing.JLabel;            // Displays a short string or image icon
import javax.swing.JOptionPane;       // Provides standard pop-up dialog boxes
import javax.swing.JPanel;            // Generic container for components
import javax.swing.JProgressBar;      // Visual progress indicator
import javax.swing.JScrollPane;       // Adds scrollable view to another component
import javax.swing.JSlider;           // Component for selecting a value from a range
import javax.swing.JTextArea;         // Multi-line area to display/edit text
import javax.swing.JTextField;        // Single-line text field
import javax.swing.ScrollPaneConstants; // Constants used with JScrollPane
import javax.swing.SwingConstants;    // Constants for alignment and orientation
import javax.swing.SwingUtilities;    // Utility class for Swing event handling
import javax.swing.Timer;             // Schedules action events at regular intervals
import javax.swing.border.EmptyBorder;// Border with empty space around components

// ===============
// Other Utilities
// ===============
import java.util.Scanner;             // Reads input from input streams
import java.util.function.BiConsumer; // Functional interface for two-argument operations

public class DragRaceGame 
{
	// Main method to start the game
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("Drag Race!"); // Create a JFrame with title "Drag Race!"
		frame.setSize(1166, 1003); // Set window size to 1166 x 1003 pixels
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application when window closes
		frame.setResizable(true); // Allow window to be resizable by the user
		frame.getContentPane().add(new GameHolder()); // Add GameHolder panel to frame's content pane

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get screen size of the display
		int x = (screenSize.width - frame.getWidth()) / 2; // Calculate x coordinate to center frame horizontally
		int y = (screenSize.height - frame.getHeight()) / 2 - 5; // Calculate y coordinate to center frame vertically (slightly adjusted)
		frame.setLocation(x, y); // Set frame location to center of screen

		frame.setVisible(true); // Make the frame visible on screen
	}
}


class GameHolder extends JPanel
{
	CardLayout layout; // CardLayout to switch between different panels

	public GameHolder()
	{
		layout = new CardLayout(); // Initialize CardLayout for panel switching
		setLayout(layout); // Set this JPanel's layout to CardLayout

		WelcomePagePanel welcomePanel = new WelcomePagePanel(this, layout); // Create welcome panel

		InstructionPanel instructionsPanel = new InstructionPanel(this, layout); // Create instructions panel

		HighScorePanel highScoresPanel = new HighScorePanel(this, layout); // Create high scores panel

		HighScorePanelAfter highScoresPanelAfter = new HighScorePanelAfter(this, layout); // Create post-game high scores panel

		GamePanel gamePanel = new GamePanel(this, layout); // Create main game panel

		CarChoosePanel carChoose = new CarChoosePanel(this, layout); // Create car selection panel

		TugOfWarPanel tow = new TugOfWarPanel(this, layout); // Create tug-of-war mini-game panel

		LearningPanel lp = new LearningPanel(this, layout); // Create learning/information panel

		ThankYouScreenPanel ty = new ThankYouScreenPanel(this, layout); // Create thank you/credits screen panel

		add(welcomePanel, "Welcome"); // Add welcome panel with name "Welcome"
		add(instructionsPanel, "Instructions"); // Add instructions panel with name "Instructions"
		add(highScoresPanel, "HighScores"); // Add high scores panel with name "HighScores"
		add(carChoose, "ChooseCar"); // Add car choose panel with name "ChooseCar"
		add(gamePanel, "Game"); // Add main game panel with name "Game"
		add(tow, "Tug"); // Add tug-of-war panel with name "Tug"
		add(lp, "Learn"); // Add learning panel with name "Learn"
		add(highScoresPanelAfter, "HighScoresAfter"); // Add post-game highscores with name "HighScoresAfter"
		add(ty, "thank you"); // Add thank you screen panel with name "thank you"
	}
}

//This class represents the welcome screen panel of the game.
//It includes animation transitions, intro sounds, skip/mute buttons, and custom mouse interactions.
class WelcomePagePanel extends JPanel implements MouseListener, MouseMotionListener
{
	private JPanel parent; // Parent container for card switching
	private CardLayout layout; // CardLayout manager for switching panels

	private float alpha = 1.0f; // Opacity value for fade effect (1 = fully opaque)

	private Timer timer; // Timer used for fade animations

	private Image gifImage; // Animated GIF intro image

	private boolean gifOrNo = true; // Flag to toggle between GIF and static car background

	private Image carBackground; // Background image shown after GIF

	private boolean leftButtonPressed; // Tracks if left button is pressed
	private boolean rightButtonPressed; // Tracks if right button is pressed

	private boolean leftButtonHovered; // Tracks if mouse is hovering left button
	private boolean rightButtonHovered; // Tracks if mouse is hovering right button

	private SoundPlayer buttonClickSound; // Sound for button clicks
	private SoundPlayer IntroSound; // Intro music sound player

	private JButton muteButton; // Button to mute/unmute intro sound
	private boolean isMuted = false; // Mute toggle state

	private JButton skipButton; // Button to skip intro animation

	private boolean fadeCompleted = false; // Flag to indicate fade animation finished

	// Constructor initializes panel, listeners, sounds, and UI components
	public WelcomePagePanel(JPanel gameHolder, CardLayout layout)
	{
		setDoubleBuffered(true); // Enable double buffering for smooth animation

		leftButtonPressed = false; // Initialize button press states
		rightButtonPressed = false;
		leftButtonHovered = false; // Initialize hover states
		rightButtonHovered = false;
		repaint();

		this.parent = gameHolder; // Store reference to parent panel container
		this.layout = layout; // Store reference to card layout manager

		addMouseListener(this); // Add mouse listener for clicks
		addMouseMotionListener(this); // Add mouse motion listener for hover

		buttonClickSound = new SoundPlayer("buttonClick.wav"); // Load click sound
		IntroSound = new SoundPlayer("Intro.wav"); // Load intro music sound

		startGIF(); // Begin GIF intro animation

		muteButton = new JButton(new ImageIcon("unmute.jpg")); // Create mute button with unmute icon
		muteButton.setVisible(false); // Initially hidden
		muteButton.setBounds(10, 10, 50, 50); // Position at top-left
		muteButton.setBorderPainted(false); // No border
		muteButton.setContentAreaFilled(false); // Transparent background
		muteButton.setFocusPainted(false); // No focus outline

		// Toggle mute state and update button icon and sound playback
		muteButton.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				isMuted = !isMuted; // Toggle mute state
				SoundPlayer.setMuted(isMuted); // Apply mute globally

				if (isMuted)
				{
					IntroSound.stop(); // Stop intro music
					muteButton.setIcon(new ImageIcon("mute.jpg")); // Show mute icon
				} 
				else 
				{
					IntroSound.play(); // Resume intro music
					muteButton.setIcon(new ImageIcon("unmute.jpg")); // Show unmute icon
				}
			}
		});

		setLayout(null); // Use absolute positioning for buttons
		add(muteButton); // Add mute button to panel

		skipButton = new JButton("Skip"); // Create skip button
		skipButton.setBounds(1000, 10, 100, 50); // Position top-right
		skipButton.setBorderPainted(true); // Show button border
		skipButton.setContentAreaFilled(false); // Transparent background
		skipButton.setFocusPainted(false); // No focus outline
		skipButton.setForeground(Color.BLACK); // Text color black
		skipButton.setBackground(Color.WHITE); // Background white

		// Skip button action transitions immediately to car selection
		skipButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				fadeCompleted = true; // Mark fade as done
				if (timer != null && timer.isRunning()) // Stop ongoing fade timer if any
				{
					timer.stop();
				}
				alpha = 0.0f; // Set opacity to transparent
				gifOrNo = false; // Switch from GIF to background
				startFadeIn(40); // Start fade-in animation quickly
				repaint();
			}
		});
		add(skipButton); // Add skip button to panel

		// Delay before starting intro music playback to sync with GIF
		Timer soundDelay = new Timer(1000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!fadeCompleted) // Play music only if fade not done
				{
					IntroSound.play();
					IntroSound.loop(); // Loop intro music
				}
				else
				{
					fadeCompleted = false; // Reset flag if fade was completed before
				}
			}
		});
		soundDelay.setRepeats(false); // Run only once
		soundDelay.start(); // Start the delay timer
	}

	// Starts the intro GIF and schedules fade out after delay
	public void startGIF()
	{
		setBackground(Color.BLACK); // Set background to black during intro
		gifImage = new ImageIcon("Start.gif").getImage(); // Load animated GIF

		try
		{
			carBackground = ImageIO.read(new File("CarBackground.jpeg")); // Load fallback background image
		}
		catch (IOException e)
		{
			e.printStackTrace(); // Print error if image not found
		}

		// Delay 7.5 seconds then start fade out to transition
		Timer delay = new Timer(7500, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				startFadeOut(100); // Begin fade out animation
			}
		});
		delay.setRepeats(false);
		delay.start();
	}

	// Fade out effect by decreasing alpha value over time
	public void startFadeOut(int time)
	{
		if (timer != null && timer.isRunning())
		{
			timer.stop(); // Stop any existing timer
		}
		if (!fadeCompleted)
		{
			timer = new Timer(time, new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					alpha = Math.max(0.0f, alpha - 0.05f); // Decrease opacity

					if (alpha <= 0.0f)
					{
						alpha = 0.0f; // Clamp to 0
						timer.stop(); // Stop timer
						gifOrNo = false; // Switch to background image
						startFadeIn(100); // Begin fade in animation
					}
					repaint(); // Redraw panel with new alpha
				}
			});
			timer.start();
		}
	}

	// Fade in effect by increasing alpha value over time
	public void startFadeIn(int time)
	{
		skipButton.setVisible(false); // Hide skip button during fade in

		if (timer != null && timer.isRunning())
		{
			timer.stop(); // Stop existing timer
		}

		alpha = 0.0f; // Start from fully transparent

		timer = new Timer(time, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				alpha = Math.min(1.0f, alpha + 0.05f); // Increase opacity

				if (alpha >= 1.0f)
				{
					alpha = 1.0f; // Clamp to fully opaque
					timer.stop(); // Stop timer
					muteButton.setVisible(true); // Show mute button
					fadeCompleted = true; // Mark fade completed
				}
				repaint(); // Redraw with updated alpha
			}
		});
		timer.start();
	}

	// Paints the GIF or background image and button hover/press overlays
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		if (gifOrNo && gifImage != null)
		{
			// Draw centered GIF with current alpha
			int finalWidth = 1166;
			int finalHeight = 975;
			int x = (getWidth() - finalWidth) / 2;
			int y = (getHeight() - finalHeight) / 2;

			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // Set transparency
			g2d.drawImage(gifImage, x, y, finalWidth, finalHeight, this); // Draw GIF
		}
		else if (carBackground != null)
		{
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); // Apply alpha to background
			g2d.drawImage(carBackground, 0, 0, 1166, 972, this); // Draw static background

			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); // Reset alpha for overlays

			// Draw dark overlays to indicate button press or hover states
			if (leftButtonPressed)
			{
				g2d.setColor(new Color(0, 0, 0, 180)); // Dark overlay
				g2d.fillRect(225, 855, 189, 53);
			}
			else if (rightButtonPressed)
			{
				g2d.setColor(new Color(0, 0, 0, 180));
				g2d.fillRect(586, 853, 376, 53);
			}
			else if (leftButtonHovered)
			{
				g2d.setColor(new Color(0, 0, 0, 80)); // Lighter overlay
				g2d.fillRect(224, 855, 188, 53);
			}
			else if (rightButtonHovered)
			{
				g2d.setColor(new Color(0, 0, 0, 80));
				g2d.fillRect(586, 853, 376, 53);
			}
		}
		g2d.dispose(); // Dispose graphics context
	}

	// Mouse pressed handles right-click shortcut and button press states
	@Override
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3) // Right-click skips intro
		{
			IntroSound.fadeOut(2000); // Fade out intro music
			layout.show(parent, "ChooseCar"); // Switch to car selection screen
		}

		int x = e.getX();
		int y = e.getY();

		if (!gifOrNo) // Only when not showing GIF
		{
			if (y > 855 && y < 896 && x > 232 && x < 412) // Left button area
			{
				leftButtonPressed = true;
				repaint();
				buttonClickSound.play(); // Play click sound
			}
			else if (y > 855 && y < 896 && x > 587 && x < 964) // Right button area
			{
				rightButtonPressed = true;
				repaint();
				buttonClickSound.play();
			}
		}
	}

	// Mouse released triggers panel switch based on pressed button
	@Override
	public void mouseReleased(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		if (!gifOrNo)
		{
			if (leftButtonPressed && y > 855 && y < 896 && x > 232 && x < 412) // Left button released inside area
			{
				IntroSound.fadeOut(3000); // Fade out intro music slowly
				layout.show(parent, "Instructions"); // Show instructions panel
			}
			else if (rightButtonPressed && y > 855 && y < 896 && x > 587 && x < 964) // Right button released inside area
			{
				IntroSound.fadeOut(3000);
				layout.show(parent, "HighScores"); // Show high scores panel
			}
		}

		leftButtonPressed = false; // Reset button press states
		rightButtonPressed = false;
		repaint();
	}

	@Override public void mouseClicked(MouseEvent e) {} // Unused
	@Override public void mouseEntered(MouseEvent e) {} // Unused

	// On mouse exit, clear hover and press states
	@Override
	public void mouseExited(MouseEvent e)
	{
		leftButtonHovered = false;
		rightButtonHovered = false;
		leftButtonPressed = false;
		rightButtonPressed = false;
	}

	@Override public void mouseDragged(MouseEvent e) {} // Unused

	// Detects mouse movement for hover effects and cursor changes
	@Override
	public void mouseMoved(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();

		if (!gifOrNo)
		{
			if (y > 855 && y < 896 && x > 232 && x < 412) // Hover over left button
			{
				leftButtonHovered = true;
				rightButtonHovered = false;
				setCursor(new Cursor(Cursor.HAND_CURSOR)); // Show hand cursor
			}
			else if (y > 856 && y < 895 && x > 587 && x < 964) // Hover over right button
			{
				rightButtonHovered = true;
				leftButtonHovered = false;
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			else
			{
				leftButtonHovered = false;
				rightButtonHovered = false;
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Default cursor
			}
			repaint(); // Update hover visual feedback
		}
	}
}
class InstructionPanel extends JPanel 
{
	private SoundPlayer buttonClickSound, NotificationSound; // Sounds for button clicks and notifications
	private JPanel parent; // Reference to the parent container to switch cards
	private CardLayout layout; // CardLayout for changing screens
	private boolean agreementChecked; // Tracks if user checked the agreement box
	private Image gifImage; // Background GIF image for the panel

	JCheckBox agreementCheckBox = new JCheckBox("I agree to the terms and conditions"); // Agreement checkbox

	private float hue = 0f; // Hue value for cycling gradient color effect

	// Timer to update hue and repaint every 40ms (~25 FPS) for smooth color cycling
	private final Timer textTimer = new Timer(40, e -> 
	{
		hue += 0.002f; // Increment hue slightly each tick
		if (hue > 1f) hue = 0f; // Reset hue when it exceeds 1 (HSB wraparound)
		repaint(); // Trigger repaint to update gradient text color
	});

	// Constructor initializes panel with parent references and starts animation timer
	public InstructionPanel(GameHolder gameHolder, CardLayout layout)
	{
		this.parent = gameHolder; // Save parent panel for layout switching
		this.layout = layout; // Save CardLayout manager

		textTimer.start(); // Start cycling gradient color timer

		setLayout(new BorderLayout()); // Use BorderLayout for component placement
		setBackground(Color.BLACK); // Set background to black for contrast

		showObjects(); // Add UI components to the panel

		buttonClickSound = new SoundPlayer("buttonClick.wav"); // Load button click sound
		NotificationSound = new SoundPlayer("Notification.wav"); // Load notification sound
	}

	// Method to set up and add UI components
	public void showObjects()
	{
		gifImage = new ImageIcon("Background.gif").getImage(); // Load background GIF image

		// Back button to return to Welcome screen
		JButton back = new JButton("   Back   ");
		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				layout.show(parent, "Welcome"); // Switch card to Welcome panel
				buttonClickSound.play(); // Play click sound
			}
		});
		back.setBackground(Color.BLACK); // Button styling - background black
		back.setForeground(Color.WHITE); // Text color white
		back.setOpaque(true); // Make background opaque
		back.setBorderPainted(false); // Remove border for flat look
		add(back, BorderLayout.WEST); // Add to left side

		// Next button to proceed only if agreement checked
		JButton next = new JButton("   Next   ");
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!agreementChecked)
				{
					NotificationSound.play(); // Play notification sound
					// Show message dialog if agreement not accepted
					javax.swing.JOptionPane.showMessageDialog(parent, "Please agree to the terms and conditions to proceed.", "Agreement Required", javax.swing.JOptionPane.INFORMATION_MESSAGE);
					buttonClickSound.play(); // Also play click sound
				}
				else if (agreementChecked)
				{
					buttonClickSound.play(); // Play click sound
					layout.show(parent, "ChooseCar"); // Move to ChooseCar panel
					agreementChecked = false; // Reset agreement for next visit
				}
			}
		});
		next.setBackground(Color.BLACK);
		next.setBorderPainted(false);
		next.setForeground(Color.WHITE);
		next.setOpaque(true);
		add(next, BorderLayout.EAST); // Add to right side

		// Configure the agreement checkbox
		agreementCheckBox.setForeground(Color.WHITE); // White text
		agreementCheckBox.setBackground(Color.BLACK); // Black background
		agreementCheckBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				buttonClickSound.play(); // Play click sound on toggle

				// Update agreementChecked flag based on checkbox state
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
		add(agreementCheckBox, BorderLayout.SOUTH); // Add to bottom

		// Add an instructions image at the top
		ImageIcon icon = new ImageIcon("Instructions.png");
		JLabel label = new JLabel(icon);
		add(label, BorderLayout.NORTH);
	}

	// Override setVisible to reset checkbox state when panel shown
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
		if (visible)
		{
			agreementCheckBox.setSelected(false); // Clear checkbox selection
			agreementChecked = false; // Reset agreement flag
		}
	}

	// Paint custom components including the GIF background and gradient text
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g); // Paint background

		// Draw background GIF at specified position and size
		g.drawImage(gifImage, 101, 312, 962, 626, this);

		// Prepare Graphics2D for gradient text drawing
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setFont(new Font("Arial", Font.BOLD, 18)); // Set font
		FontMetrics fm = g2d.getFontMetrics(); // Get font metrics for line height
		int lineHeight = fm.getHeight();

		// Compute two colors cycling through hue for gradient effect
		Color c1 = Color.getHSBColor(hue, 1f, 1f);
		Color c2 = Color.getHSBColor((hue + 0.3f) % 1f, 1f, 1f);

		// Text lines to display as instructions
		String[] lines =
			{
					"Game Setup",
					"You control a car that competes against a bot car.",
					"Answer correctly for speed boosts; wrong answers slow you down.",
					"",
					"How to Play",
					"Enter your name, pick your car, and choose difficulty,",
					"and press Start Game to begin.",
					"Answer questions using the buttons that appear.",
					"",
					"Learning Features",
					"At the end, review your mistakes,",
					"see correct answers, and get helpful explanations.",
					"",
					"Winning the Game",
					"Beat the bot to the finish line!",
					"Track your progress with the progress bar, and aim for a high score!"
			};

		int x = 300; // X start for text
		int yStart = 461; // Y start for text
		int totalHeight = lines.length * lineHeight; // Calculate total height for gradient

		// Create a vertical gradient paint from c1 to c2 over the text block height
		GradientPaint textGrad = new GradientPaint(
				x, yStart, c1,
				x, yStart + totalHeight, c2
				);
		g2d.setPaint(textGrad); // Apply gradient paint to Graphics2D

		// Draw each line of instruction text at calculated positions
		for (int i = 0; i < lines.length; i++) 
		{
			g2d.drawString(lines[i], x, yStart + i * lineHeight);
		}

		g2d.dispose(); // Dispose graphics context to free resources
	}
}


class CarChoosePanel extends JPanel implements MouseListener, MouseMotionListener
{
	// This class represents the car selection panel where players can choose their car and enter their name.
	private Image carOptions;                 // Image for car options with background
	private Image carOptionsNoBackgroundOriginal; // Car options image without background (original)
	private Image EmptyCar;                   // Placeholder image for empty car slot
	private Image imageForOpponent;           // Image representing the opponent’s car
	int x, y;                                // User’s car coordinates
	int xHover, yHover;                      // Mouse hover coordinates
	int xClick, yClick;                      // Mouse click coordinates
	int opponentX, opponentY;                // Opponent car coordinates
	private JPanel parent;                   // Parent JPanel container
	private CardLayout layout;              // CardLayout for switching UI panels
	private String name = "";               // Player or car name
	private boolean carSelected = false;   // Flag: has user selected a car?
	private boolean nameEntered = false;   // Flag: has user entered their name?
	private SoundPlayer carSelectSound;    // Sound for car selection
	private SoundPlayer buttonClickSound; // Sound for button clicks
	private SoundPlayer notificationSound; // Sound for notifications
	private JLabel carStatsLabel;          // Label displaying car stats
	private String carStats = "No car selected"; // Current car stats text
	private boolean opponentCarSelected = false; // Flag: opponent car selected?
	Storer Storer = new Storer();           // Storage handler instance

	public CarChoosePanel(GameHolder gameHolder, CardLayout layout)
	{
		this.parent = gameHolder;               // Reference to parent container (GameHolder)
		this.layout = layout;                   // CardLayout for switching UI panels
		addMouseListener(this);                 // Add mouse click listener
		addMouseMotionListener(this);           // Add mouse movement listener
		setLayout(null);                       // Use absolute positioning (no layout manager)
		addComponents();                       // Initialize and add UI components
		Storer.setCarImage(null);              // Clear stored car image

		carSelectSound = new SoundPlayer("carSelect.wav");    // Load car selection sound
		buttonClickSound = new SoundPlayer("buttonClick.wav"); // Load button click sound
		notificationSound = new SoundPlayer("Notification.wav"); // Load notification sound
		imageForOpponent = null;               // Initialize opponent image to null
	}

	public void addComponents()
	{
		try
		{
			carOptions = ImageIO.read(new File("CarOptions.png"));                    // Load car options image with background
			EmptyCar = ImageIO.read(new File("EmptyCar.jpg"));                        // Load empty car placeholder image
			carOptionsNoBackgroundOriginal = ImageIO.read(new File("CarOptionsClearBackground.png")); // Load car options image without background
		}
		catch (IOException e)
		{
			e.printStackTrace();                                                      // Print error if image loading fails
		}

		JButton back = new JButton("Back");                                          // Back button to return to instructions
		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				buttonClickSound.play();                                              // Play click sound
				layout.show(parent, "Instructions");                                 // Show instructions card
			}
		});
		back.setBounds(912, 900, 100, 38);                                           // Set button position and size
		add(back);

		JButton next = new JButton("Next");                                          // Next button to proceed to game
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				buttonClickSound.play();                                              // Play click sound
				if (carSelected && opponentCarSelected && nameEntered)                // Check if all selections are made
				{
					layout.show(parent, "Game");                                      // Show game card if ready
				}
				else
				{
					notificationSound.play();                                         // Play notification sound
					javax.swing.JOptionPane.showMessageDialog(parent, 
							"Please select enter the nessesary information to proceed.", 
							"Information Required", 
							javax.swing.JOptionPane.INFORMATION_MESSAGE);                // Show info dialog
					buttonClickSound.play();                                          // Play click sound again
				}
			}
		});
		next.setBounds(1037, 900, 100, 38);                                          // Set button position and size
		add(next);

		JTextField nameField = new JTextField("Enter Your Name");                    // Text field for player to enter name
		nameField.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (nameField.getText().equals("Enter Your Name"))
				{
					nameField.setText("");                                            // Clear placeholder on click
				}
			}
			@Override public void mousePressed(MouseEvent e) {}
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});

		nameField.addFocusListener(new java.awt.event.FocusListener()
		{
			@Override
			public void focusGained(java.awt.event.FocusEvent e)
			{
				buttonClickSound.play();                                              // Play click sound on focus
				if (nameField.getText().equals("Enter Your Name"))
				{
					nameField.setText("");                                            // Clear placeholder on focus gain
				}
			}
			@Override
			public void focusLost(java.awt.event.FocusEvent e)
			{
				if (nameField.getText().isEmpty())
				{
					nameField.setText("Enter Your Name");                             // Reset placeholder if empty
				}
			}
		});

		nameField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Storer.setName(nameField.getText());                                  // Store the entered name
				if (nameField.getText().isEmpty() || nameField.getText().equals("Enter Your Name"))
				{
					nameField.setText("Enter Your Name");                             // Reset if empty or placeholder
					nameEntered = false;                                              // Mark name as not entered
					repaint();                                                      
				}
				else if (nameField.getText().length() > 13)
				{
					nameField.setText(nameField.getText().substring(0, 13));         // Limit name length to 13
				}
				else
				{
					name = nameField.getText() + ", ";                               // Save name with comma
					nameEntered = true;                                               // Mark name as entered
					repaint();
				}
			}
		});
		add(nameField);
		nameField.setBounds(643, 737, 500, 38);                                     // Set position and size
		nameField.setEditable(true);                                                // Make text field editable

		JSlider difficultySlider = new JSlider(0, 100, 50);                          // Slider for difficulty (0-100, start 50)
		difficultySlider.setBounds(643, 812, 500, 62);                              // Set slider position and size

		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();                   // Labels for slider positions
		labelTable.put(0, new JLabel("Easy"));
		labelTable.put(50, new JLabel("Medium"));
		labelTable.put(100, new JLabel("Hard"));
		difficultySlider.setLabelTable(labelTable);                                 // Assign labels

		difficultySlider.setMajorTickSpacing(50);                                   // Major ticks at 0,50,100
		difficultySlider.setMinorTickSpacing(10);                                   // Minor ticks every 10
		difficultySlider.setPaintTicks(true);                                       // Show tick marks
		difficultySlider.setPaintLabels(true);                                      // Show labels
		difficultySlider.setSnapToTicks(true);                                      // Snap to ticks when sliding
		difficultySlider.setToolTipText("Adjust Difficulty");                       // Tooltip text

		Font labelFont = new Font("Arial", Font.BOLD, 16);                          // Font for labels
		for (JLabel label : labelTable.values()) 
		{
			label.setFont(labelFont);                                               // Set label font
			label.setForeground(new Color(40, 40, 40));                            // Set label color
		}

		difficultySlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(difficultySlider) 
		{
			@Override
			public void paintThumb(Graphics g) 
			{
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(new Color(50, 150, 255));                              // Blue thumb color
				g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
				g2d.dispose();
			}
			@Override
			public void paintTrack(Graphics g) 
			{
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int cy = trackRect.y + (trackRect.height / 2) - 3;
				int trackHeight = 6;

				int startX = trackRect.x;
				int endX = trackRect.x + trackRect.width;
				int thumbX = thumbRect.x + thumbRect.width / 2;

				g2d.setColor(new Color(200, 0, 0));                                 // Left side track color (red)
				g2d.fillRoundRect(startX, cy, thumbX - startX, trackHeight, 10, 10);

				g2d.setColor(new Color(0, 120, 255));                               // Right side track color (blue)
				g2d.fillRoundRect(thumbX, cy, endX - thumbX, trackHeight, 10, 10);

				g2d.dispose();
			}
		});

		difficultySlider.setBackground(new Color(245, 245, 245));                    // Slider background color
		difficultySlider.setForeground(Color.DARK_GRAY);                            // Slider foreground color
		difficultySlider.setOpaque(true);                                           // Make slider opaque

		difficultySlider.addChangeListener(e ->
		{
			int value = difficultySlider.getValue();
			Storer.setDifficultyLevel(value);                                       // Save difficulty level
			try
			{
				opponentCarSelected = true;                                         // Mark opponent car selected on slider change
				if (value < 20)
				{
					imageForOpponent = ImageIO.read(new File("Bicycle.png"));       // Very Easy: Bicycle
					Storer.setOpponentCarImage("Bicycle.png");
				}
				else if (value < 40)
				{
					imageForOpponent = ImageIO.read(new File("Motorcycle.png"));    // Easy: Motorcycle
					Storer.setOpponentCarImage("Motorcycle.png");
				}
				else if (value < 60)
				{
					imageForOpponent = ImageIO.read(new File("CarNormal.png"));     // Medium: Normal Car
					Storer.setOpponentCarImage("CarNormal.png");
				}
				else if (value < 80)
				{
					imageForOpponent = ImageIO.read(new File("CarSport.png"));      // Hard: Sports Car
					Storer.setOpponentCarImage("CarSport.png");
				}
				else
				{
					imageForOpponent = ImageIO.read(new File("Rocket.png"));        // Very Hard: Rocket
					Storer.setOpponentCarImage("Rocket.png");
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();                                                // Print error if image loading fails
			}
			repaint();                                                              // Repaint panel to update opponent car image
		});

		add(difficultySlider);

		JLabel difficultyLabel = new JLabel("Difficulty Level");                     // Label for difficulty slider
		difficultyLabel.setBounds(625, 787, 500, 25);                               // Position and size
		difficultyLabel.setForeground(Color.BLACK);                                 // Text color
		difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 15));                 // Font style and size
		add(difficultyLabel);

		carStatsLabel = new JLabel(carStats);                                       // Label to display car stats text
		carStatsLabel.setBounds(675, 375, 375, 188);                                // Position and size
		carStatsLabel.setFont(new Font("Arial", Font.BOLD, 16));                    // Font style and size
		carStatsLabel.setForeground(Color.BLUE);                                   // Text color
		carStatsLabel.setVerticalAlignment(JLabel.TOP);                            // Align text to top
		carStatsLabel.setHorizontalAlignment(JLabel.LEFT);                         // Align text to left
		add(carStatsLabel);
	}


	@Override
	public void paintComponent(Graphics g)
	{
		int carWidth = 121; // Width of the car image
		int carHeight = 238; // Height of the car image

		super.paintComponent(g); 
		g.drawImage(carOptions, 0, 0, 625 , 980, this); // Draw the car options image
		Graphics2D g2d = (Graphics2D) g; // Create a Graphics2D object for advanced rendering

		// Draw the car options
		if (!opponentCarSelected)
		{
			g.drawImage(EmptyCar, 944, 131, carWidth, carHeight, this); // Draw the empty car image
			java.awt.Stroke oldStroke = g2d.getStroke(); // Add a border to the empty car
			g2d.setStroke(new BasicStroke(2)); // Set the stroke width
			g.drawRect(944, 131, carWidth, carHeight); // Draw the border
			g2d.setStroke(oldStroke); // Reset the stroke
		}
		else
		{
			g.drawImage(imageForOpponent, 944, 131, carWidth, carHeight, this); // Draw the opponent's car image
		}

		// Draw gray box if clicked
		if (xClick != 0)
		{
			carSelected = true; // Set carSelected to true if a car is clicked
			g.setColor(new Color (0, 0, 0, 80)); // Set the color to a semi-transparent black
			g.fillRect(xClick, yClick, carWidth, carHeight); // Draw the gray box
			// Draw cropped section from carOptions into "Your Car" area
			g.drawImage(carOptionsNoBackgroundOriginal, 662, 131, 662 + carWidth, 131 + carHeight, xClick, yClick, xClick + carWidth, yClick + carHeight, this); // Draw the cropped image
		}
		else
		{
			carStats = "No car selected"; // Reset the car stats
			carStatsLabel.setText(carStats); // Update the label text
			carSelected = false; // Reset carSelected if no car is clicked
			g.drawImage(EmptyCar, 675, 131, carWidth, carHeight, this);
			//add border to the empty car
			java.awt.Stroke oldStroke = g2d.getStroke(); // Add a border to the empty car
			g2d.setStroke(new BasicStroke(2)); // Set the stroke width
			g.drawRect(675, 131, carWidth, carHeight); // Draw the border
			g2d.setStroke(oldStroke); // Reset the stroke
		}

		// Draw blue hover rectangle if hovering
		if (xHover != 0)
		{
			g.setColor(Color.BLUE); // Set the color to blue
			java.awt.Stroke oldStroke = g2d.getStroke(); // Add a border to the hovered car
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
	// Static field to store the selected car image identifier
	private static String carNumber; 

	// Static field to store the opponent's car image identifier
	private static String carOpponentString;

	// Instance field for difficulty level (not static, so per instance)
	private int difficultyLevel;

	// Static field to store the race game time
	private static int timeRace;

	// Static field to store the tug game time
	private static int timeTug;

	// Static field to store the player's name
	private static String name;

	// Static arrays to store wrong answers for two different games
	private static int[] wrongGame1;
	private static int[] wrongGame2;

	// Sets the selected car image identifier (static)
	public void setCarImage(String number) 
	{
		carNumber = number; // Assign value to static carNumber field
	}

	// Gets the selected car image identifier (static)
	public String getCarImage() 
	{
		return carNumber; // Return static carNumber field
	}

	// Sets the opponent's car image identifier (static)
	public void setOpponentCarImage(String carType) 
	{
		carOpponentString = carType; // Assign value to static carOpponentString field
	}

	// Gets the opponent's car image identifier (static)
	public String getOpponentCarImage() 
	{
		return carOpponentString; // Return static carOpponentString field
	}

	// Sets the difficulty level (instance field)
	public void setDifficultyLevel(int difficultyLevel) 
	{
		this.difficultyLevel = difficultyLevel;
	}

	// Gets the difficulty level (instance field)
	public int getDifficultyLevel() 
	{
		return difficultyLevel;
	}

	// Sets the race game time (static)
	public void setRaceGameTime(int timeRaceIn)
	{
		timeRace = timeRaceIn;
	}

	// Gets the race game time (static)
	public int getRaceTime()
	{
		return timeRace;
	}

	// Sets the tug game time (static)
	public void setTugTime(int tugTimeIn)
	{
		timeTug = tugTimeIn;
	}

	// Gets the tug game time (static)
	public int getTugTime()
	{
		return timeTug;
	}

	// Sets the player's name (static)
	public void setName(String nameIn)
	{
		name = nameIn;
	}

	// Gets the player's name (static)
	public String getName()
	{
		return name;
	}

	// Sets the array of wrong answers from game 1 (static)
	public void setWrongFromGame1(int[] wrongIn) 
	{
		wrongGame1 = wrongIn;
	}

	// Sets the array of wrong answers from game 2 (static)
	public void setWrongFromGame2(int[] wrongIn) 
	{
		wrongGame2 = wrongIn;
	}

	// Gets the array of wrong answers from game 1 (static)
	public int[] getWrongFromGame1()
	{
		return wrongGame1;
	}

	// Gets the array of wrong answers from game 2 (static)
	public int[] getWrongFromGame2() 
	{
		return wrongGame2;
	}
}


class GamePanel extends JPanel
{
	private JPanel parent;                 // Reference to the parent container panel
	private CardLayout layout;             // CardLayout managing multiple views/panels
	private Image trackImage, carsImage, opponentCarImage; // Images for track, user car, opponent car
	private String carNumber, carOpponentString;           // Strings to identify selected cars

	// Constants for car position and track limits
	private final int USER_CAR_X = 300;   // Fixed x-position for the user's car on screen
	private final int FINISH_LINE = 54000; // Logical position of the finish line on the track
	private final int TRACK_END = -56000;  // Logical end position of the track (off limits)

	// Logical positions for cars and track offset
	private double car1LogicalPos = 0;    // Logical position of user's car on track
	private double car2LogicalPos = 0;    // Logical position of opponent's car on track
	private double trackPos = 0;          // Current position offset of the track for scrolling

	private boolean timerStarted = false; // Flag to indicate if the game timer is running
	private boolean gameEnded = false;    // Flag to indicate if the game has ended

	private double userSpeedBoost = 0;   // Additional speed boost applied to user's car
	private Timer gameTimer;              // Swing Timer controlling the game loop/ticks
	private double opponentSpeed;         // Current speed of opponent's car

	private Storer storer = new Storer(); // Instance of Storer class for shared game state

	private int questionNumber = 0;       // Current question index in the quiz/game
	private String question = "";         // Current question text
	private String answerExplanation = "";// Explanation text for the current question's answer
	private String[] answerChoices = new String[4]; // Array holding the multiple-choice answers
	private String answer = "";           // Correct answer string for the current question
	boolean showButtons = true;           // Controls visibility of answer buttons

	JTextArea questionArea = new JTextArea(); // Text area UI component to display questions

	// Sound players for feedback and music
	private SoundPlayer correct;          // Sound played on correct answer
	private SoundPlayer incorrect;        // Sound played on incorrect answer
	private JProgressBar progressBar;     // Progress bar to show game progress visually
	private SoundPlayer raceMusic;        // Background music during race gameplay

	private JButton start;                // Start button for the game
	private JButton next;                 // Next button to proceed in the game/questions

	private int arrayNumber = 0;          // Index or count used in managing answer arrays
	private int[] arrayNum = new int[100]; // Array possibly tracking question or answer indices

	private double userSpeed = 20 + userSpeedBoost; // User car speed combining base speed and boost

	GamePanel(JPanel gameHolder, CardLayout layout) 
	{
		importTextfiles();  // Load questions and other text resources from files

		// Initialize sound players for correct, incorrect answers and race background music
		correct = new SoundPlayer("Correct.wav");
		incorrect = new SoundPlayer("InCorrect.wav");
		raceMusic = new SoundPlayer("raceGameSound.wav");

		this.parent = gameHolder; // Store reference to parent container panel
		this.layout = layout;      // Store reference to CardLayout controlling screen changes

		setLayout(new BorderLayout()); // Set layout manager for this panel

		// Play race music when this panel becomes visible
		addComponentListener(new ComponentAdapter() 
		{
			@Override
			public void componentShown(ComponentEvent e) 
			{
				raceMusic.play();
				raceMusic.loop();
			}
		});

		// Set panel background color (light pastel blue)
		setBackground(new Color(240, 248, 255));

		// Create and style the title label
		JLabel label = new JLabel("Racing Challenge!", SwingConstants.CENTER);
		label.setForeground(new Color(25, 25, 112)); // midnight blue text color
		label.setFont(new Font("Verdana", Font.BOLD, 36));

		// Initialize progress bar with track length range and starting value
		progressBar = new JProgressBar(0, 54000);
		progressBar.setValue((int)trackPos);

		// Header panel containing title label, question area, and progress bar
		JPanel header = new JPanel(new GridLayout(3,1));
		header.setBackground(new Color(240, 248, 255));

		// Control panel holding the answer buttons with spacing and background color
		JPanel controlPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		controlPanel.setBackground(new Color(230, 240, 255)); // slightly deeper blue

		// Create answer buttons for multiple choice questions
		JButton questionOneButton = new JButton("");
		JButton questionTwoButton = new JButton("");
		JButton questionThreeButton = new JButton("");
		JButton questionFourButton = new JButton("");

		// Start and Next buttons to control game flow
		start = new JButton("  Start  ");
		next = new JButton("Next");
		next.setVisible(false); // Hide Next button initially

		// Define button colors for styling
		Color startRestartColor = new Color(0, 122, 204);  // blue for start/restart
		Color nextColor = new Color(0, 166, 126);          // green for next

		// Set preferred sizes for Start and Next buttons
		start.setPreferredSize(new Dimension(130, 35));
		next.setPreferredSize(new Dimension(130, 35));

		// Shared font and color styles for buttons
		Font customFont = new Font("Segoe UI", Font.BOLD, 16);
		Color primaryColor     = new Color(0, 166, 126);
		Color secondaryColor   = new Color(220, 220, 220);
		Color hoverShadow      = new Color(0, 0, 0, 30);

		// Helper method to apply flat style and hover shadow to buttons
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

			// Change border on mouse press and release for button press effect
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

		// Apply styles to all answer buttons and start/next buttons
		styleButton.accept(questionOneButton, primaryColor);
		styleButton.accept(questionTwoButton, primaryColor);
		styleButton.accept(questionThreeButton, primaryColor);
		styleButton.accept(questionFourButton, primaryColor);
		styleButton.accept(start, startRestartColor);
		styleButton.accept(next, nextColor);

		// Set preferred size for answer buttons
		Dimension buttonSize = new Dimension(190, 60);
		for (JButton b : new JButton[]
				{ 
						questionOneButton, questionTwoButton, questionThreeButton, questionFourButton
				})
		{
			b.setPreferredSize(buttonSize);
		}

		// Start button action logic: start timer and initialize questions or restart game
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
					// Set text of answer buttons to current choices
					questionOneButton.setText(answerChoices[0]);
					questionTwoButton.setText(answerChoices[1]);
					questionThreeButton.setText(answerChoices[2]);
					questionFourButton.setText(answerChoices[3]);
					// Display current question text
					questionArea.setText(question);
					updateProgressBar();
				});
			}
			else
			{
				// Restart game: reset buttons, stop timer, reset variables and progress bar
				questionOneButton.setText("");
				questionTwoButton.setText("");
				questionThreeButton.setText("");
				questionFourButton.setText("");
				questionArea.setText("");
				if (gameTimer != null) 
					gameTimer.stop();
				car1LogicalPos = car2LogicalPos = trackPos = userSpeedBoost = 0;
				next.setVisible(false);
				gameEnded = timerStarted = false;
				start.setText("  Start  ");
				progressBar.setValue(0);
				repaint();
			}
		});

		// Next button action: switch to "Tug" panel, reset variables and UI
		next.addActionListener(e->
		{
			layout.show(parent, "Tug");
			questionOneButton.setText("");
			questionTwoButton.setText("");
			questionThreeButton.setText("");
			questionFourButton.setText("");
			questionArea.setText("");
			if (gameTimer != null) 
				gameTimer.stop();
			car1LogicalPos = car2LogicalPos = trackPos = userSpeedBoost = 0;
			next.setVisible(false);
			gameEnded = timerStarted = false;
			start.setText("  Start  ");
			start.setEnabled(true);
			start.setVisible(true);
			progressBar.setValue(0);
			userSpeedBoost = 0;
			userSpeed = 20 + userSpeedBoost;
			repaint();
		});

		// Panel to hold Start and Next buttons with padding and transparent background
		JPanel controlButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		controlButtonPanel.setBackground(new Color(240, 248, 255, 0));
		controlButtonPanel.add(start);
		controlButtonPanel.add(next);

		// Add action listeners to answer buttons to handle answer selection logic
		questionOneButton.addActionListener(e -> handleAnswer(0, questionOneButton, questionTwoButton, questionThreeButton, questionFourButton));
		questionTwoButton.addActionListener(e -> handleAnswer(1, questionOneButton, questionTwoButton, questionThreeButton, questionFourButton));
		questionThreeButton.addActionListener(e -> handleAnswer(2, questionOneButton, questionTwoButton, questionThreeButton, questionFourButton));
		questionFourButton.addActionListener(e -> handleAnswer(3, questionOneButton, questionTwoButton, questionThreeButton, questionFourButton));

		// Set font for answer buttons
		Font font = new Font("Arial", Font.CENTER_BASELINE, 15);
		questionOneButton.setFont(font);
		questionTwoButton.setFont(font);
		questionThreeButton.setFont(font);
		questionFourButton.setFont(font);

		// Style the question text area
		questionArea.setLineWrap(true);
		questionArea.setWrapStyleWord(true);
		questionArea.setEditable(false);
		questionArea.setFont(new Font("Serif", Font.PLAIN, 20));
		questionArea.setBackground(new Color(245, 245, 245));
		questionArea.setForeground(Color.BLACK);

		// Add components to header panel
		header.add(label);
		header.add(questionArea);
		header.add(progressBar);
		add(header, BorderLayout.NORTH);

		// Add answer buttons to control panel and add it at the bottom
		controlPanel.add(questionOneButton);
		controlPanel.add(questionTwoButton);
		controlPanel.add(questionThreeButton);
		controlPanel.add(questionFourButton);
		add(controlPanel, BorderLayout.SOUTH);

		// Add the control button panel containing Start and Next buttons in the center
		add(controlButtonPanel, BorderLayout.CENTER);
	}

	// Play race music if visible and loop it continuously
	public void ifVisible()
	{
		raceMusic.play();
		raceMusic.loop();
	}

	// Update the progress bar to current car1 logical position capped at finish line
	private void updateProgressBar()
	{
		int progress = (int) car1LogicalPos;
		progressBar.setValue(Math.min(progress, FINISH_LINE));
	}

	// Run a countdown from the specified seconds, then run onComplete when done
	private void runCountdown(int seconds, Runnable onComplete)
	{
		Timer countdownTimer = new Timer(1000, null);
		final int[] count = { seconds };
		start.setText(String.valueOf(count[0]));

		countdownTimer.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				count[0]--;
				if (count[0] > 0)
				{
					// Update countdown label text each second
					start.setText(String.valueOf(count[0]));	
				}
				else
				{
					// Stop timer and run completion callback when countdown reaches zero
					countdownTimer.stop();
					onComplete.run();
				}
			}
		}); 

		countdownTimer.start();
	}

	// Load question and answer data from text files and pick a random new question
	private void importTextfiles()
	{
		Scanner in1 = null;
		Scanner in2 = null;
		File trigAnswerFile = new File("trigAnswerExplanations.txt");
		File trigQuestionFile = new File("trigMultipleChoice.txt");

		try
		{
			// Open files for reading explanations and questions
			in1 = new Scanner(trigAnswerFile);
			in2 = new Scanner(trigQuestionFile);
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File not found: " + e.getMessage());
		}

		// Pick a new random question number different from last
		int temp = 0;
		do
		{
			temp = (int) (Math.random() * 50 + 1);
		} while (questionNumber == temp);

		questionNumber = temp;
		System.out.println("Question Number: " + questionNumber);

		// Find matching explanation line for the question number
		while (in1.hasNextLine())
		{
			String line = in1.nextLine();
			if (line.startsWith(questionNumber + ")"))
			{
				answerExplanation = line;
			}
		}

		// Find matching question and its four answer choices and the correct answer
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
				// Skip a token (likely a blank line or separator)
				in2.next();
				if (in2.hasNext())
				{
					// Read the correct answer character
					answer = in2.next();
				}
			}
		}

		// Print loaded question data for debugging
		System.out.println("Question: " + question);
		for (String choice : answerChoices)
		{
			System.out.println("Choice: " + choice);
		}
		System.out.println("Answer: " + answer);
		System.out.println("Explanation: " + answerExplanation);
	}

	// Load car images and set opponent speed based on opponent car type and difficulty level
	public void getCar()
	{
		// If images already loaded, do nothing
		if (carsImage != null && opponentCarImage != null && trackImage != null)
			return;

		// Get user car and opponent car image filenames from storer
		carNumber = storer.getCarImage();
		carOpponentString = storer.getOpponentCarImage();
		int level = storer.getDifficultyLevel();

		// Set opponent speed based on car type and difficulty level
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
			// Load and rotate opponent car image by 90 degrees for correct orientation
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

			// Load user car and track images
			carsImage = ImageIO.read(new File(carNumber + ".png"));
			trackImage = ImageIO.read(new File("Track.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Start the main game timer that updates car positions, track scrolling, and checks for race end conditions
	public void startTimer()
	{
		long startTime = System.currentTimeMillis(); // Record start time of the race

		gameTimer = new Timer(16, e -> 
		{	
			if (gameEnded)
				return;

			// Calculate user speed with current speed boost applied
			userSpeed = 20 + 2 * userSpeedBoost;

			// Update logical positions of user car and opponent car
			car1LogicalPos += userSpeed;
			car2LogicalPos += opponentSpeed;

			// Scroll track left if beyond track end limit
			if (trackPos > TRACK_END) 
			{
				trackPos -= userSpeed;
			}

			// Update progress bar with current user progress
			updateProgressBar();

			// Check if user wins by reaching finish line first
			if (car1LogicalPos >= FINISH_LINE && car2LogicalPos < FINISH_LINE)
			{
				storer.setWrongFromGame1(Arrays.copyOf(arrayNum, arrayNumber));
				long endTime = System.currentTimeMillis(); // Record end time
				storer.setRaceGameTime((int) (endTime/1000 - startTime/1000));
				gameEnded = true;
				gameTimer.stop();
				raceMusic.fadeOut(3000); // Fade out music over 3 seconds
				ImageIcon win = new ImageIcon("Win.png");
				JOptionPane.showMessageDialog(GamePanel.this, "You Win!", "Race Result", JOptionPane.INFORMATION_MESSAGE, win);
				next.setVisible(true);
				start.setVisible(false);
			} 
			// Check if opponent wins or both finish simultaneously
			else if (car2LogicalPos >= FINISH_LINE && car1LogicalPos >= FINISH_LINE) 
			{
				storer.setWrongFromGame1(Arrays.copyOf(arrayNum, arrayNumber));
				long endTime = System.currentTimeMillis(); // Record end time
				storer.setRaceGameTime((int) (endTime/1000 - startTime/1000));
				gameEnded = true;
				gameTimer.stop();
				raceMusic.fadeOut(3000);
				ImageIcon lose = new ImageIcon("Lose.png");
				JOptionPane.showMessageDialog(GamePanel.this, "You Lose!", "Race Result", JOptionPane.INFORMATION_MESSAGE, lose);
				next.setVisible(true);
				start.setVisible(false);
			}

			repaint(); // Repaint game panel each tick to update visuals
		});

		gameTimer.start();
	}

	// Paint the game panel, including track and cars, with random shaking effect based on speed boost
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		getCar(); // Ensure car images are loaded

		int shake = (int)(Math.random() * Math.max(0, userSpeedBoost / 2)); // Random shake amount for visual effect
		int trackY = 150 + shake;

		// Draw scrolling track background if loaded
		if (trackImage != null)
		{
			g.drawImage(trackImage, (int) trackPos, trackY, 1166 * 50, 750, this);
		}

		// Draw user car with shake effect
		if (carsImage != null)
		{
			g.drawImage(carsImage, USER_CAR_X, 170 + shake, 476, 242, this);
		}

		// Calculate opponent car screen position relative to user car
		int opponentScreenX = USER_CAR_X + (int)(car2LogicalPos - car1LogicalPos);
		if (opponentCarImage != null)
		{
			if (car2LogicalPos >= FINISH_LINE)
			{
				// Draw opponent car at finish line if finished
				g.drawImage(opponentCarImage, USER_CAR_X + (int)(FINISH_LINE - car1LogicalPos), 610, 476, 242, this);
			}
			else if (opponentScreenX < getWidth() && opponentScreenX > -400)
			{
				// Draw opponent car only if within visible screen area
				g.drawImage(opponentCarImage, opponentScreenX, 610, 476, 242, this);
			}
		}
	}

	// Handle player's answer selection, apply speed boost or slowdown, update questions, and manage UI feedback
	public void handleAnswer(int selectedIndex, JButton b1, JButton b2, JButton b3, JButton b4)
	{
		if (timerStarted)
		{
			// Check if selected answer is correct by comparing answer character
			boolean isCorrect = answerChoices[selectedIndex].substring(3, 4).equals(answer);
			System.out.println(isCorrect + "\n");
			System.out.println("Entered: " + answerChoices[selectedIndex].substring(3,4) + "\tRight: " + answer);

			if (!gameEnded)
			{
				if (isCorrect)
				{
					// Play correct sound effect
					correct.stop();
					correct.play();

					// Increase user speed boost temporarily
					userSpeedBoost += 10;

					// Load new question and update answer button text
					importTextfiles();
					b1.setText(answerChoices[0]);
					b2.setText(answerChoices[1]);
					b3.setText(answerChoices[2]);
					b4.setText(answerChoices[3]);
					questionArea.setText(question);

					// After 2 seconds, remove speed boost
					Timer boostTimer = new Timer(2000, new ActionListener() 
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							userSpeedBoost -= 10;
							((Timer) e.getSource()).stop();
						}
					});
					boostTimer.setRepeats(false);
					boostTimer.start();
				}
				else
				{
					// Add current question number to wrong answers array
					arrayNum[arrayNumber++] = questionNumber;

					// Play incorrect sound effect
					incorrect.stop();
					incorrect.play();

					// Decrease user speed boost temporarily to slow down user
					userSpeedBoost -= 10;

					// Load new question and update answer buttons
					importTextfiles();
					b1.setText(answerChoices[0]);
					b2.setText(answerChoices[1]);
					b3.setText(answerChoices[2]);
					b4.setText(answerChoices[3]);

					// Hide answer buttons and clear question area to indicate penalty
					b1.setVisible(false);
					b2.setVisible(false);
					b3.setVisible(false);
					b4.setVisible(false);
					questionArea.setText("");

					// After 2 seconds, revert slowdown and restore UI
					Timer slowdownTimer = new Timer(2000, new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							userSpeedBoost += 10;
							((Timer) e.getSource()).stop();
							b1.setVisible(true);
							b2.setVisible(true);
							b3.setVisible(true);
							b4.setVisible(true);
							questionArea.setText(question);
						}
					});
					slowdownTimer.setRepeats(false);
					slowdownTimer.start();
				}
			}
		}
	}
}

class TugOfWarPanel extends JPanel
{
	// Reference to the parent container panel and layout manager
	private JPanel parent;
	private CardLayout layout;

	// Images for the user car, bot car, and rope
	private BufferedImage userCarImage;
	private BufferedImage botCarImage;
	private BufferedImage ropeImage;

	// Game mechanics constants and variables
	private final double PULL_STEP = 50;        // Amount user pulls rope on correct answer
	private final double BOT_PULL_STEP = 20;    // Amount bot pulls rope on wrong answer
	private double botPullSpeed = 0;            // Bot's automatic pull speed per tick
	private final double WIN_THRESHOLD = 300;   // Distance needed to win or lose

	// Car image dimensions
	private final int CAR_WIDTH = 238;
	private final int CAR_HEIGHT = 131;

	// Rope and game state variables
	private double ropeOffset = 0;              // Current rope offset from center
	private boolean timerStarted = false;       // Is the game timer running
	private boolean gameEnded = false;          // Has the game ended

	private Timer gameTimer;                    // Main game loop timer

	// Question and answer state
	private int questionNumber = 0;             // Current question number
	private String question = "";               // Current question text
	private String[] answerChoices = new String[4]; // Multiple choice answers
	private String answer = "";                 // Correct answer
	private String answerExplanation = "";      // Explanation for the answer

	// Sound effects and music
	private SoundPlayer correctSound;           // Sound for correct answer
	private SoundPlayer incorrectSound;         // Sound for incorrect answer
	private SoundPlayer tugMusic;               // Background music

	// UI components for questions and answers
	private JTextArea questionArea = new JTextArea();
	private JButton[] answerButtons = new JButton[4];
	private JButton startButton = new JButton("Start");
	private JButton next = new JButton("Next");

	// Shared state and tracking
	private Storer store = new Storer();        // Shared game state
	private long startTime;                     // Game start time
	private long endTime;                       // Game end time
	private int tugArrayNumber = 0;             // Number of wrong answers
	private int[] tugArrayNum = new int[100];   // Array of wrong question numbers

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
		tugMusic = new SoundPlayer("tugGameSound.wav");

		addComponentListener(new ComponentAdapter() 
		{
			@Override
			public void componentShown(ComponentEvent e) 
			{
				tugMusic.play();
				tugMusic.loop();
			}
		});

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
			tugMusic.fadeOut(3000);
			layout.show(parent, "Learn");
			// Reset game variables
			ropeOffset = 0;
			gameEnded = false;
			timerStarted = false;

			// Stop any running timers
			if (gameTimer != null) 
			{
				gameTimer.stop();
			}

			// Reset UI components
			questionArea.setText("");
			for (JButton button : answerButtons) 
			{
				button.setText("");
				button.setEnabled(false);
			}

			// Reset start button visibility
			startButton.setVisible(true);
			startButton.setEnabled(true);
			startButton.setText("Start");

			// Hide the next button
			next.setVisible(false);

			// Reset any other game-specific logic
			tugArrayNumber = 0;
			Arrays.fill(tugArrayNum, 0);

			// Repaint the panel to reflect changes
			repaint();
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
			ImageIcon win = new ImageIcon("Win.png");
			endGame("You Win!", win);
		}
		else if (ropeOffset >= WIN_THRESHOLD)
		{
			ImageIcon lose = new ImageIcon("Lose.png");
			endGame("You Lose!", lose);
		}
	}

	private void endGame(String msg, ImageIcon icon)
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

		for (JButton b : answerButtons) 
			b.setEnabled(false);

		questionArea.setText("");
		JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE, icon);
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

		int ropeHeight = 50;
		int ropeY = carY + CAR_HEIGHT / 2 - ropeHeight / 2;
		int ropeX = userCarX + CAR_WIDTH;
		int ropeWidth = botCarX - ropeX;

		if (ropeWidth > 0)
		{
			g2d.drawImage(ropeImage, ropeX, ropeY, ropeWidth, ropeHeight, this);
		}

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

	public void fadeOut(int durationMs)
	{
		if (clip == null || volumeControl == null) return;
		final int steps = 10;
		final float initial = volumeControl.getValue();
		final float min = volumeControl.getMinimum();
		final float delta = (initial - min) / steps;
		Timer timer = new Timer(durationMs / steps, null);
		timer.addActionListener(new ActionListener() 
		{
			int count = 0;
			public void actionPerformed(ActionEvent e) 
			{
				if (count < steps) 
				{
					volumeControl.setValue(initial - delta * count);
					count++;
				}
				else 
				{
					timer.stop();
					stop();
				}
			}
		});
		timer.start();
	}


	// Stop the sound
	public void stop()
	{
		if (clip != null)
		{
			clip.stop();
		}
	}

	public void loop()
	{
		if (clip != null && !isMuted)
		{ // Check if not muted
			clip.loop(Clip.LOOP_CONTINUOUSLY);
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
}

//Panel shown at the end of the game, with animation and buttons for replay or quitting
class ThankYouScreenPanel extends JPanel implements ActionListener 
{
	private JPanel parent;             // Reference to parent panel for switching cards
	private CardLayout layout;        // Layout manager to switch between screens
	private final java.util.List<Particle> particles = new ArrayList<>();  // List of animated particles
	private final Timer timer;        // Timer for animation updates
	private float rotationAngle = 0f; // Angle for rotating logo
	private float gradientOffset = 0; // Offset used to animate the background gradient
	private float hue = 0f;           // Hue used to shift colors over time
	private final GradientButton quitButton;   // Button to quit the game
	private final GradientButton replayButton; // Button to replay the game

	public ThankYouScreenPanel(JPanel parent, CardLayout layout) 
	{
		this.parent = parent;
		this.layout = layout;
		setLayout(null);               // Use absolute layout
		setBackground(Color.BLACK);    // Set background color

		// Create and style the "Quit" button
		quitButton = new GradientButton("Quit");
		quitButton.setFont(new Font("Arial", Font.BOLD, 20));
		quitButton.setForeground(Color.WHITE);
		quitButton.setBounds(1000, 10, 150, 50);
		quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		quitButton.addActionListener(e -> 
		{
			System.exit(0);           // Exit the program when clicked
		});
		add(quitButton);

		// Create and style the "Replay" button
		replayButton = new GradientButton("Replay");
		replayButton.setFont(new Font("Arial", Font.BOLD, 20));
		replayButton.setForeground(Color.WHITE);
		replayButton.setBounds(1000, 70, 150, 50);
		replayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		replayButton.addActionListener(e -> 
		{
			layout.show(parent, "Game");  // Switch back to the game screen
		});
		add(replayButton);

		// Spawn initial particles at startup
		for (int i = 0; i < 100; i++)
		{
			particles.add(new Particle(getWidth(), getHeight()));
		}

		// Set up the animation timer (fires every ~16ms for ~60 FPS)
		timer = new Timer(16, this);
		timer.start();
	}

	// Custom drawing for the panel
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		int w = getWidth(), h = getHeight();

		// Animate a moving diagonal gradient background
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

		// Draw a rotating circular logo in the center top area
		AffineTransform old = g2.getTransform();
		g2.translate(w / 2, h / 3);
		g2.rotate(rotationAngle);
		g2.setColor(new Color(255, 220, 0, 200));
		g2.fillOval(-50, -50, 100, 100);
		g2.setTransform(old);

		// Draw all particles (sparkles)
		for (Particle p : particles) 
			p.draw(g2);

		// Draw "Thank You" text with shadow and animated gradient
		String msg = "Thank You for Playing!";
		Font font = new Font("Arial Black", Font.BOLD, 48);
		g2.setFont(font);
		FontMetrics fm = g2.getFontMetrics();
		int textW = fm.stringWidth(msg);
		int x = (w - textW) / 2;
		int y = h * 2 / 3;

		// Text shadow
		g2.setColor(new Color(0, 0, 0, 150));
		g2.drawString(msg, x + 4, y + 4);

		// Text with gradient fill that animates with hue
		Color t1 = Color.getHSBColor((hue + 0.2f) % 1f, 1f, 1f);
		Color t2 = Color.getHSBColor((hue + 0.6f) % 1f, 1f, 1f);
		GradientPaint tg = new GradientPaint(x, y - fm.getAscent(), t1, x + textW, y, t2);
		g2.setPaint(tg);
		g2.drawString(msg, x, y);

		g2.dispose();
	}

	// Timer update: update particles and rotation angle
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int w = getWidth(), h = getHeight();

		// Occasionally add more particles to keep the effect going
		if (particles.size() < 150) 
			particles.add(new Particle(w, h));

		// Update particle positions and remove dead ones
		Iterator<Particle> it = particles.iterator();
		while (it.hasNext())
		{
			Particle p = it.next();
			p.update();
			if (!p.isAlive()) it.remove();
		}

		// Rotate the logo slightly each frame
		rotationAngle += 0.01f;

		repaint(); // Request redraw
	}
	
	// Custom JButton with a rounded gradient background and hover glow
	private static class GradientButton extends JButton 
	{
		private boolean hover = false;

		GradientButton(String text) 
		{
			super(text);
			setContentAreaFilled(false);  // No default fill
			setBorderPainted(false);
			setFocusPainted(false);
			
			// Detect hover state
			addMouseListener(new MouseAdapter() 
			{
				public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
				public void mouseExited(MouseEvent e) { hover = false; repaint(); }
			});
		}

		// Custom paint for button with rounded gradient background
		@Override 
		protected void paintComponent(Graphics g) 
		{
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

	// Represents a single particle for the sparkle effect
	static class Particle
	{
		float x, y, life, maxLife;
		double vy, vx;
		Color color;

		// Initialize particle with random direction, speed, and color
		Particle(int width, int height) 
		{
			Random rnd = new Random();
			x = width / 2f; 
			y = height / 3f;

			float ang = rnd.nextFloat() * 2 * (float)Math.PI;
			float spd = rnd.nextFloat() * 4 + 1;
			vx = Math.cos(ang) * spd;
			vy = Math.sin(ang) * spd;

			maxLife = life = rnd.nextFloat() * 60 + 30;
			color = new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), 1f);
		}

		// Move particle based on velocity and reduce life
		void update() 
		{
			x += vx; 
			y += vy; 
			life--; 
		}

		// Check if the particle should still be displayed
		boolean isAlive() 
		{ 
			return life > 0; 
		}

		// Draw particle as a fading circle
		void draw(Graphics2D g2) 
		{
			float a = life / maxLife;
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, a));
			int s = (int)(a * 8 + 2);  // Size shrinks over time
			g2.setColor(color);
			g2.fillOval((int)x, (int)y, s, s);
			g2.setComposite(AlphaComposite.SrcOver);
		}
	}
}

