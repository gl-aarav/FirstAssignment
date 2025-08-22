/* 
 * Aarav Goyal
 * 3/26/2025
 * ControlPanel.java
 */ 

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JFrame;  
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlPanel
{
	public static void main(String[] args) 
	{
		ControlPanel ce = new ControlPanel();
		ce.run();
	}

	public void run() 
	{
		JFrame frame = new JFrame("Control Panel for Picture");
		frame.setSize(800, 600);
		frame.setLocation(10, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CpPanelHolder cph = new CpPanelHolder();

		frame.getContentPane().add(cph);
		frame.setVisible(true);
	}
}

class CpPanelHolder extends JPanel
{
	private int selected;                     // the index for the picture selected to draw
	private JTextArea tAComponentInfo;        // text area in the PictPanel, but changed in RightControlPanel2
	private JLabel welcome;                   // label in the PictPanel, but changed in RightControlPanel2
	private Font font;                        // most fonts are the same, so there is one
	private int val;                          // value of the slider to change the picture size
	private int width;
	private int height;
	private int [] widthOfImages;            // stores the width of each image
	private int [] heightOfImages;           // stores the height of each image

	public CpPanelHolder()
	{        
		font = new Font("Serif", Font.BOLD, 15);
		setLayout(new BorderLayout());
		PictPanel pict = new PictPanel();
		add(pict, BorderLayout.CENTER);    

		RightControlPanel rcp = new RightControlPanel(pict);
		add(rcp, BorderLayout.EAST);
	}

	class PictPanel extends JPanel
	{
		private String[] names;                // the names of the pictures
		private Image[] images;                // array of images to be drawn

		public PictPanel()
		{
			setLayout(new BorderLayout()); // Set layout before adding components

			names = new String[] {"mountains.jpg", "shanghai.jpg", "trees.jpg", "water.jpg"};
			images = new Image[names.length];
			widthOfImages = new int[names.length];
			heightOfImages = new int [names.length];

			for(int i = 0; i < names.length; i++)
			{
				images[i] = getMyImage("pictures/" + names[i]);
				if (images[i] != null) 
				{  
					widthOfImages[i] = images[i].getWidth(this);
					heightOfImages[i] = images[i].getHeight(this);
				}
				else 
				{
					System.err.println("Failed to load image: " + names[i]);
				}
			}

			welcome = new JLabel("Welcome!", JLabel.CENTER);
			welcome.setFont(font);
			welcome.setForeground(Color.BLACK);
			add(welcome, BorderLayout.NORTH); // Add welcome label after setting the layout

			JPanel textArea = new JPanel(new BorderLayout());
			tAComponentInfo = new JTextArea("What the component has changed will show up here", 20, 1);
			tAComponentInfo.setLineWrap(true);
			tAComponentInfo.setWrapStyleWord(true);
			tAComponentInfo.setEditable(false); // Optional: Prevent user from editing if needed

			JScrollPane scrollPane = new JScrollPane(tAComponentInfo);
			scrollPane.setPreferredSize(new Dimension(0, 175)); // Set fixed size to prevent expansion
			textArea.add(scrollPane, BorderLayout.SOUTH);
			add(textArea, BorderLayout.SOUTH);

		}

		public Image getMyImage(String pictName) 
		{
			Image picture = null;
			File pictFile = new File(pictName);
			try 
			{
				picture = ImageIO.read(pictFile);    
			}

			catch(IOException e)
			{
				System.err.print("\n\n\n" + pictName + " can't be found.\n\n\n");
				e.printStackTrace();
			}

			return picture;
		}

		public void paintComponent(Graphics g) 
		{
		    super.paintComponent(g);

		    if (images[selected] != null) 
		    {
		        int originalWidth = widthOfImages[selected];
		        int originalHeight = heightOfImages[selected];

 		        int newWidthTemp = (originalWidth + 6 * val)/4;
		        int newHeightTemp = (originalHeight + 6 * val)/4;

		        int maxWidth = getWidth() - 20;  
		        int maxHeight = getHeight() - 20;

		        int newWidth = newWidthTemp;
		        int newHeight = newHeightTemp;

		        if (newWidth > maxWidth) 
		        {
		            double scaleRatio = (double) maxWidth / newWidthTemp;
		            newWidth = maxWidth;
		            newHeight = (int) (newHeightTemp * scaleRatio);
		        }
		       
		        else if (newHeight > maxHeight) 
		        {
		            double scaleRatio = (double) maxHeight / newHeightTemp;
		            newHeight = maxHeight;
		            newWidth = (int) (newWidthTemp * scaleRatio);
		        }

		        g.drawImage(images[selected], 20, 20, newWidth, newHeight, this);
		    }
		}



		public void setSelected(int index) 
		{
			selected = index;
			repaint();
		}

		public void setSliderValue(int value) 
		{
			val = value;
			repaint();
		}

		public void updateWelcomeText(String name, Color color) 
		{
			String colorText = new String ("");
			if (!name.equals("Enter Your Name:"))
			{
				welcome.setText("Welcome, " + name + "!");
				tAComponentInfo.setText("The welcome sign is now to \"" + welcome.getText() + "\"");
			}
			welcome.setForeground(color);
		}
	}


	class RightControlPanel extends JPanel
	{
		private JTextField tfName;                         // text field for user to type in their name
		private ButtonGroup bg;                            // to select the color so only one is selected
		private JRadioButton color1, color2, color3;       // color choices
		private JSlider sSize;                             // slider for changing the size of the picture
		private PictPanel pictPanel;

		public RightControlPanel(PictPanel pictPanel)
		{    
			this.pictPanel = pictPanel;
			setBackground(Color.CYAN);
			setLayout(new BorderLayout());

			JPanel labelAndText = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
			labelAndText.setBackground(Color.CYAN);
			labelAndText.setPreferredSize(new Dimension(200, 60)); // Ensure space for JTextField

			JLabel l = new JLabel("     Control Panel     "); 
			l.setFont(font);
			labelAndText.add(l);

			// Create and assign the text field
			tfName = new JTextField("Enter Your Name:", 15);
			tfName.setPreferredSize(new Dimension(180, 25));
			tfName.addActionListener(new TextFieldHandler());

			labelAndText.add(tfName); // Ensure JTextField is properly added
			add(labelAndText, BorderLayout.NORTH);

			JPanel menu = new JPanel(new BorderLayout());
			menu.setBackground(Color.CYAN);
			JMenuBar pictureBar = makePictureMenuBar();
			menu.add(pictureBar, BorderLayout.NORTH);    
			add(menu, BorderLayout.WEST);

			JPanel slider = new JPanel(new GridLayout(2, 1));
			slider.setBackground(Color.CYAN);
			makeSlider();
			slider.add(sSize);
			add(slider, BorderLayout.SOUTH);

			JPanel rButtonPanel = makeRB();
			rButtonPanel.setBackground(Color.CYAN);
			add(rButtonPanel, BorderLayout.EAST);
		}


		public JTextField makeText() 
		{
			JTextField textField = new JTextField("Enter Your Name:");
			textField.setPreferredSize(new Dimension(180, 25));  
			textField.addActionListener(new TextFieldHandler());
			return textField;  
		}

		class TextFieldHandler implements ActionListener 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String userName = tfName.getText(); 
				if (!userName.isEmpty() && !userName.equals("Enter Your Name:")) 
				{
					Color selectedColor = getSelectedColor(); 
					pictPanel.updateWelcomeText(userName, selectedColor);
				}
			}
		}

		public JMenuBar makePictureMenuBar()
		{
			JMenuBar bar = new JMenuBar();
			JMenu picture = new JMenu("Pictures");

			JMenuItem water = new JMenuItem("water");
			JMenuItem mountains = new JMenuItem("mountains");
			JMenuItem shanghai = new JMenuItem("shanghai");
			JMenuItem trees = new JMenuItem("trees");

			PictureMenuHandler cmh = new PictureMenuHandler();        
			water.addActionListener(cmh);
			mountains.addActionListener(cmh);
			shanghai.addActionListener(cmh);
			trees.addActionListener(cmh);

			picture.add(water);
			picture.add(mountains);
			picture.add(shanghai);
			picture.add(trees);

			bar.add(picture);

			return bar;
		}

		class PictureMenuHandler implements ActionListener 
		{
			public void actionPerformed(ActionEvent evt) 
			{
				String command = evt.getActionCommand();    

				if(command.equals("mountains")) 
				{
					tAComponentInfo.setText(tAComponentInfo.getText() + "\n" + "The picture color was changed to \"Mountains.\"");
					pictPanel.setSelected(0);    
				}

				else if(command.equals("shanghai"))   
				{
					tAComponentInfo.setText(tAComponentInfo.getText() + "\n" + "The picture color was changed to \"Shangai.\"");
					pictPanel.setSelected(1);        
				}

				else if(command.equals("trees"))
				{
					tAComponentInfo.setText(tAComponentInfo.getText() + "\n" + "The picture color was changed to \"Trees.\"");
					pictPanel.setSelected(2);
				}
				else if(command.equals("water"))
				{
					tAComponentInfo.setText(tAComponentInfo.getText() + "\n" + "The picture color was changed to \"Water.\"");
					pictPanel.setSelected(3);    
				}
			}
		}

		public void makeSlider()
		{
			setPreferredSize(new Dimension(300, 30));
			sSize = new JSlider(0, 200, 5);
			sSize.setMajorTickSpacing(20);    
			sSize.setPaintTicks(true);
			sSize.setLabelTable(sSize.createStandardLabels(20)); 
			sSize.setPaintLabels(true);
			sSize.setOrientation(JSlider.HORIZONTAL);
			SliderListener slistener1 = new SliderListener();
			sSize.addChangeListener(slistener1);
		}

		class SliderListener implements ChangeListener 
		{
			public void stateChanged(ChangeEvent evt) 
			{
				int value = sSize.getValue();
				if (!sSize.getValueIsAdjusting()) 
				{ 
					tAComponentInfo.setText("The slider value was changed to " + value + ".");
					pictPanel.setSliderValue(value);
				}
				pictPanel.setSliderValue(value);
				repaint();
			}
		}    

		public JPanel makeRB()
		{
			JPanel rbPanel = new JPanel(new GridLayout(8, 1));
			JLabel color = new JLabel("Select a color of the label:  ");
			rbPanel.add(color);

			color1 = new JRadioButton("Red");
			color2 = new JRadioButton("Blue");
			color3 = new JRadioButton("Magenta");

			ButtonGroup bg = new ButtonGroup();
			bg.add(color1);
			bg.add(color2);
			bg.add(color3);

			color1.addActionListener(new RButtonHandler());
			color2.addActionListener(new RButtonHandler());
			color3.addActionListener(new RButtonHandler());

			rbPanel.add(color1);
			rbPanel.add(color2);
			rbPanel.add(color3);

			add(rbPanel);

			return rbPanel;
		}

		class RButtonHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent evt) 
			{ 
				String userName = tfName.getText();
				Color selectedColor = getSelectedColor();
				pictPanel.updateWelcomeText(userName, selectedColor);
				if (getSelectedColor() == Color.RED)
					tAComponentInfo.setText("The welcome sign color was changed to red.");
				else if (getSelectedColor() == Color.BLUE)
					tAComponentInfo.setText("The welcome sign color was changed to blue.");
				else  if (getSelectedColor() == Color.MAGENTA)
					tAComponentInfo.setText("The welcome sign color was changed to magenta.");
			}
		}

		private Color getSelectedColor() 
		{
			if (color1.isSelected())
			{
				return Color.RED;
			}
			else if (color2.isSelected())
			{
				return Color.BLUE;
			}
			else if (color3.isSelected())
			{
				return Color.MAGENTA;
			}

			return Color.BLACK;  // Default color
		}
	}
}
