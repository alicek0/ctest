import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class LoginPanel extends JPanel implements ActionListener{
	
	private List<ActionListener> buttonActionListeners= new ArrayList<>();
	
	JTextField username;
	OneCardWriter oneCardWriter;
	JLabel err_label;
	
	public LoginPanel() {
		//oneCardWriter = ocw;
		
		// import font
		File font_file = new File("img/SanMarinoBeach-2OOLW.ttf");
		Font font = null;
		// Get Font
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, font_file);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Font font2 = null;
		font_file = new File("img/Bebas-Regular.ttf");
		try { font2 = Font.createFont(Font.PLAIN, font_file); }
		catch (Exception e) { e.printStackTrace(); }
		
		// Initialize
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		// Title Text
		JLabel title = new JLabel("One Card");
		title.setFont(font2.deriveFont(Font.PLAIN, 72f));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		add(title, c);
		
		// Username text
		JLabel text = new JLabel();
		text.setText("USERNAME: ");
		c.insets = new Insets(15,0,0,0); // top padding
		c.gridwidth = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0.5;
		add(text, c);
		
		
		
		// Username input
		username = new JTextField(12);
		c.gridx = 1;
		c.anchor = GridBagConstraints.LINE_START;
		add(username, c);
		username.addActionListener(this);
		c.weightx = 0.0;
		
		c.anchor = GridBagConstraints.CENTER;
		
		// Error message
		err_label = new JLabel();
		err_label.setText("Please enter your username.");
		c.gridy = 3;
		c.gridx = 0;
		c.gridwidth = 2;
		add(err_label, c);
		
		JButton start = new JButton("Start");
		start.setFont(font2.deriveFont(Font.PLAIN, 24f));
		start.addActionListener(this);
		
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 4;
		c.ipady = 40;
		c.ipadx = 100;
		c.insets = new Insets(15,0,0,0); // top padding
		add(start, c);
		setPreferredSize(new Dimension(300,300));
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Login");
		f.add(new LoginPanel());
		f.setSize(330,500);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}


	public void actionPerformed(ActionEvent e) {
		// if (e.getActionCommand() == "Start") System.out.println("start");
		
		String text = username.getText();
		if (text!="null" && ! text.trim().isEmpty()) {
			text = text.strip();
			text = text.replaceAll("[\\s]+", "");
			if (!text.matches("[\\d\\w]+") )  // [] matches any pattern in the brackets digits words more than once 
				err_label.setText("Username must only contain letters and numbers.");
			else {
				System.out.println(text);
				// oneCardWriter.setUsername(text);
				err_label.setText("Welcome "+text+"!");
			}
		}
		
		else 
			err_label.setText("Username cannot be empty.");
		
		username.selectAll();
		username.setText("");
		
		for(ActionListener listener: buttonActionListeners){
			System.out.println("pressed");
		}
		
	}
	
	public void addMyButtonActionListener(ActionListener a){
        if(!buttonActionListeners.contains(a))
            buttonActionListeners.add(a);
    }
}
