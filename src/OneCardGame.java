
public class OneCardGame implements OneCardGameInterface{
	Dealer dealer;
	HumanPlayer hand_player = new HumanPlayer();
	ComputerPlayer hand_com1;
	ComputerPlayer hand_com2;
	ComputerPlayer hand_com3;
	
	public boolean isPlayerTurn() {
		return true;
	}

	// writer에서 받은 카드 (즉 플레이어의 카드)를 dealer보고 놓으라고 한다
	public boolean putCard(String suit, int rank) {
		// s
		return true;
	}
	
	public Card topCard() {
		// test 용도, 지워도 됌
		Card tmp = new Card("hearts", 1);
		return tmp;
	}
	
	public Card[] playerCards() {
		//test 용도, 지워도 됌
		Card[] hand2 = new Card[16];
		for (int i =0; i<13; i++) {
			hand2[i] = new Card("spades", i+1);
		}
		hand2[13] = new Card("hearts", 1);
		hand2[14] = new Card("hearts", 2);
		hand2[15] = new Card("hearts", 13);
		
		return hand2;
		//return hand_player.hand();
	}
	
	// player hand에 있는 카드 수를 return 한다 
	public int playerCardNumber() {
		return 10;
	}
	
	
	// computer cards 각각 몇개 있는지 
	public int[] numberOfComCards() {
		//test 용도 
		int[] comCards = {4, 8, 10};
		return comCards;
	}
	
}
