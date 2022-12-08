import java.awt.event.ActionEvent;

public class ThrownDeckButton extends CardButton {
	private OneCardWriter oneCardWriter;

	public ThrownDeckButton(String s, int r, OneCardWriter ocw) {
		super(s, r, ocw);
		oneCardWriter = ocw;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("top card clicked");
		
	}
}
