
public class Card implements CardInterface{
	private String suit;
	private int rank;
	
	public Card(String s, int r) {
		suit = s;
		rank = r;
	}
	
	public String suit() {
		return suit;
	}

	@Override
	public int rank() {
		// TODO Auto-generated method stub
		return rank;
	}
}
