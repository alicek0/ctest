import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class OneCardWriter extends JPanel {

	private OneCardGame cardGame;
	private static int window_width = 1500;
	private static int window_height = 800;
	private String username = "USER";
	// contains number of cards for each computer 
	private int[] com_cards = new int[3];
	// player card hand
	private Card[] player_cards = new Card[16];
	private CardButton[] player_cards_buttons = new CardButton[16];
	
	private int player_card_count = 0;
	private int turn_count = 0;
	private final Color transparent = new Color(0,0,0,0);
	
	private JPanel playerPanel = new JPanel();
	private JPanel deckPanel = new JPanel();
	private JPanel com1Panel = new JPanel();
	private JPanel tempPanel2 = new JPanel(new GridBagLayout());
	private JPanel com2Panel = new JPanel();
	private JPanel tempPanel3 = new JPanel(new GridBagLayout());
	private JPanel com3Panel = new JPanel();
	private JPanel tempPanel1;
	private JPanel statusPane;
	
	private Card putCard;
	private CardImage putCardImg;
	private JLabel putCardLabel;
	JLabel turnLabel;
	
	private Image card_back_rotated = null;
	private Image card_back = null;
	
	public OneCardWriter(OneCardGame cg) {
		setLayout(new BorderLayout());
		JPanel gamePanel = new JPanel(new BorderLayout());
		
		// f.setTitle("OneCard");
		cardGame = cg;
		com_cards = cg.numberOfComCards();
		//player_cards = cg.playerCards();
		for (int i =0; i<16; i++) {
			player_cards[i] = cg.playerCards()[i];
		}
		putCard = cg.topCard();
		putCard = new Card("diamonds", 12);
		player_card_count = cg.playerCardNumber();
		
		// import images
		try {
			card_back = ImageIO.read(new FileInputStream("img/back.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//resize image
		int n = 6; // image scaler ratio int
		int width = card_back.getWidth(getFocusCycleRootAncestor())/n;
		int height = card_back.getHeight(getFocusCycleRootAncestor())/n;
		card_back = card_back.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		try {
			card_back_rotated = ImageIO.read(new FileInputStream("img/back_rotated.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		// resize image
		width = card_back_rotated.getWidth(getFocusCycleRootAncestor())/n;
		height = card_back_rotated.getHeight(getFocusCycleRootAncestor())/n;
		card_back_rotated = card_back_rotated.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		setLayout(new BorderLayout());
		player_card_count = 16;
		// initialize player_cards_buttons
		for (int i =0; i<player_card_count; i++) {
			player_cards_buttons[i]=new CardButton(player_cards[i].suit(), player_cards[i].rank(), this);
		}
		
		// Player Panel
		gamePanel.add(new JLabel("PlayerCards", SwingConstants.CENTER), BorderLayout.SOUTH); // placeholder
		playerPanel.setLayout(new FlowLayout());
		//f.pack();
		for (int i =0; i<player_card_count; i++) {
			playerPanel.add(player_cards_buttons[i]);
		}
		playerPanel.setBackground(transparent); // sets background transparent
		//playerPanel.setPreferredSize(new Dimension(0,250));
//		else playerPanel.setPreferredSize(new Dimension(0,150));
		gamePanel.add(playerPanel, BorderLayout.SOUTH);
		
		// Center Deck Panel
		gamePanel.add(new JLabel("Test", SwingConstants.CENTER), BorderLayout.CENTER); // placeholder
		deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.X_AXIS));
		deckPanel.add(Box.createHorizontalGlue());

		// JLabel backCard = new JLabel(new ImageIcon(card_back)); //save image to jlabel
		//deckPanel.add(backCard);
		putCardImg = new CardImage(putCard.suit(), putCard.rank());
		putCardLabel = new JLabel(new ImageIcon(putCardImg.img()));
		deckPanel.add(putCardLabel); // add jlabel to panel
		deckPanel.add(Box.createRigidArea(new Dimension(8,0)));
		//deckPanel.add(new JLabel(new ImageIcon(card_back)));
		deckPanel.add(new CardDeckButton(this));
		deckPanel.add(Box.createHorizontalGlue());
		deckPanel.setBackground(transparent); // sets background transparent
		gamePanel.add(deckPanel, BorderLayout.CENTER);
		
		// Computer1 Panel
		gamePanel.add(new JLabel("Computer1"), BorderLayout.WEST);
		//tempPanel1 = new JPanel(new GridBagLayout());
		tempPanel1 = new JPanel(new BorderLayout());
		tempPanel1.setBackground(new Color(0,0,0,0));
		com1Panel.setLayout(new OverlapLayout(new Point(0,20)));
		// add cards by number of cards of com1
		for (int i=0; i<com_cards[0]; i++) {
			com1Panel.add(new JLabel(new ImageIcon(card_back_rotated)));
		}
		com1Panel.setBackground(transparent); // sets background transparent
		// add(com1Panel, BorderLayout.WEST);
		//tempPanel1.add(com1Panel, new GridBagConstraints());
		tempPanel1.add(com1Panel, BorderLayout.CENTER);
		gamePanel.add(tempPanel1, BorderLayout.WEST);
		
		// Computer2 Panel
		gamePanel.add(new JLabel("Computer2", SwingConstants.CENTER), BorderLayout.NORTH);
		tempPanel2.setBackground(new Color(0,0,0,0));
		tempPanel2.setLayout(new FlowLayout());
		com2Panel.setLayout(new OverlapLayout(new Point(20, 0)));
		for (int i=0; i<com_cards[1]; i++) {
			com2Panel.add(new JLabel(new ImageIcon(card_back)));
		}
		com2Panel.setBackground(new Color(0,0,0,0)); // sets background transparent
		//add(com2Panel, BorderLayout.NORTH);
		tempPanel2.add(com2Panel);
		gamePanel.add(tempPanel2, BorderLayout.NORTH);
		
		// Computer3 Panel
		gamePanel.add(new JLabel("Computer3"), BorderLayout.EAST);
		// if solved cards cut off issue & panel not readjusting size issue
		// change to GridBagLayout() to center cards.
		tempPanel3.setLayout(new BorderLayout()); 
		tempPanel3.setBackground(transparent);
		com3Panel.setLayout(new OverlapLayout(new Point(0,20)));
		for (int i=0; i<com_cards[2]; i++) {
			JLabel l = new JLabel(new ImageIcon(card_back_rotated));
			com3Panel.add(l);
			//com3Panel.add(new JLabel(new ImageIcon(card_back_rotated)));
		}
		com3Panel.setBackground(transparent); // sets background transparent
		tempPanel3.add(com3Panel);
		gamePanel.add(tempPanel3, BorderLayout.EAST);
		
		// Status Pane
		statusPane = new JPanel();
		statusPane.setLayout(new FlowLayout(FlowLayout.TRAILING));
		JLabel userLabel = new JLabel(username);
		statusPane.add(userLabel);
		turnLabel = new JLabel("TURN: "+ turn_count);
		statusPane.add(turnLabel);
		int chips_count = 1; // replace with cardGame.getChips();
		JLabel chips = new JLabel("CHIPS: " + chips_count);
		statusPane.add(chips);
		statusPane.setBackground(new Color(235, 235, 235));
		
		add(statusPane, BorderLayout.NORTH);
		add(gamePanel, BorderLayout.CENTER);
		gamePanel.setBackground(transparent);
	}
	
	// BACKGROUND
	public void paintComponent(Graphics g) {
		// fill background with white
		g.setColor(new Color(235, 235, 235)); // light grey
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// draw grid lines (as background)
		g.setColor(new Color(200, 200, 200)); // light grey + a bit darker
		
		// thicken line
		//Graphics2D g2 = (Graphics2D) g;
		//g2.setStroke(new BasicStroke(3));
		
		// vertical lines
		int vertical_line_count = 7;
		for (int i =window_width/vertical_line_count; i < window_width+300; i+=window_width/vertical_line_count) {
			g.drawLine(i, 0, i, window_height*2);
		}
		// horizontal lines
		int horizontal_line_count = 4;
		for (int i =window_height/horizontal_line_count; i < window_height+300; i+=window_height/horizontal_line_count) {
			g.drawLine(0, i, window_width*2, i);
		}
		
		// rounded rectangle background for player hand
		// g.fillRoundRect(150, window_height/2+150, player_card_bg_w, 100, 15, 15);	
		// is it possible that it readjusts according to the panel?
		// do i have to make a specified panel and set graphics for it? 
		// are coordinates specific to a panel? 
		
	}

	// deal player card
	public void putCard(String s, int r) {
		// System.out.println(s+ " " + Integer.toString(r)); debug line
		if (cardGame.putCard(s, r)) {
			System.out.println("Put card");
			for (int i =0; i<player_card_count; i++) {
				if (player_cards_buttons[i] != null && player_cards_buttons[i].suit().equals(s) && player_cards_buttons[i].rank() == r) {
					playerPanel.remove(player_cards_buttons[i]);
					for (int j = i ; j<player_card_count; j++) {
						if (j == player_card_count-1) player_cards_buttons[j] = null;
						else player_cards_buttons[j] = player_cards_buttons[j+1];
					}
				}
			}
			// decrement player's card count
			player_card_count -=1;
			playerPanel.validate();
			repaint();
			
			// increment turn count at the end of player turn 
			// will be useful if dynamic number of players 
			turn_count += 4; // increase by the number of players
			turnLabel.setText("TURN: " + turn_count);
		}
	}
	
	// end turn and pass turn to next player
	public void endTurn() {
		// ????????? ????????? 
		// ?????? ?????? ????????? ????????? 
		System.out.println("END TURN");
		refresh();
	}

	public static void main(String[] args) {
		OneCardGame cardGame = new OneCardGame();
		OneCardWriter gameWriter = new OneCardWriter(cardGame);
		JFrame f = new JFrame("OneCard");
		f.setContentPane(gameWriter);
		f.setSize(window_width, window_height);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//gameWriter.refresh();
	}

	
	private void updateComp(JPanel panel, int actual, int n) {
		int current = com_cards[n];
		Image temp = card_back_rotated;
		if (n == 1) temp = card_back;
		
		if (current != actual) {
			panel.removeAll(); 
			for (int i = 0; i < actual; i++) {
				panel.add(new JLabel(new ImageIcon(temp)));
			}
			panel.validate();
			if (panel == com2Panel) {
				panel.setSize(400, panel.getHeight());
				tempPanel2.add(panel);
			}
			repaint();
		}
	}
	
	private void updatePlayer(JPanel panel, Card[] player_cards, int player_card_count) {
		panel.removeAll();
		//playerPanel.setLayout(new FlowLayout());
		
		for (int i =0; i<player_card_count; i++) {
			player_cards_buttons[i]=new CardButton(player_cards[i].suit(), player_cards[i].rank(), this);
			panel.add(player_cards_buttons[i]);
		}
		
		if (player_card_count>11) panel.setPreferredSize(new Dimension(this.getWidth(), 280));
		else panel.setPreferredSize(new Dimension(this.getWidth(), 180));
//		else panel.setSize(new Dimension(this.getWidth(),150));
		panel.validate();
		repaint();
	}
	
	private void refresh(){
		// computer cards??? ????????? ????????? ?????? ?????????
		// updateComp(com#Panel, updated num of com cards, com number (0/1/2))

		// updateComp(com1Panel, cardGame.numberOfComCards()[0], 0);
		// updateComp(com2Panel, cardGame.numberOfComCards()[1], 1);
		// updateComp(com1Panel, cardGame.numberOfComCards()[2], 2);
		updateComp(com1Panel, 16, 0);
		updateComp(com2Panel, 16, 1);
		updateComp(com3Panel, 16, 2);
		
		//updateComp(16, 0);
		setTopCard();

		// player cards array??? ????????? ?????? ????????? 
		Arrays.fill(player_cards, null);
		// Card[] temp_cards =  cardGame.playerCards();
		Card[] temp_cards = {new Card("diamonds", 1), new Card ("spades", 2), new Card("clubs", 3),
				new Card("diamonds", 4), new Card ("spades", 5), new Card("clubs", 6),
				new Card("diamonds", 7), new Card ("spades", 8), new Card("clubs", 9),
				new Card("diamonds", 10), new Card ("spades", 11), new Card("clubs", 12),
				new Card("diamonds", 13), new Card ("spades", 1), new Card("clubs", 1)}; // cardGame.playerCards();
		int temp_num_of_cards = 15; // cardGame.playerCardNumber()
		for (int i =0; i< temp_num_of_cards; i++) {
			if (temp_cards[i] != null) player_cards[i] = temp_cards[i];
		}
		
		updatePlayer(playerPanel, temp_cards, temp_num_of_cards);
		
		
		// check if player lose/win
		// case = cg.cases();
		// switch (case):
		//     case 0: continue;
		//     case 1: player lose, break;
		//     case 2: player win
		//     default: pass;
	}
	
	private void setTopCard() {
		putCard = cardGame.topCard();
		putCard = new Card("spades", 1);
		putCardImg = new CardImage(putCard.suit(), putCard.rank());
		putCardLabel.setIcon(new ImageIcon(putCardImg.img()));
	}
	
	public void setUsername(String text) {
		// cardGame.setUsername(text); 
	}
}

/// delay / wait / timer ?????? ???????
//try {
//System.out.println("sleeping");
//for (int i =0; i<10; i++) {
//Thread.sleep(100);
//this.repaint();}
//System.out.println("done sleeping");
//} catch(InterruptedException e) {}