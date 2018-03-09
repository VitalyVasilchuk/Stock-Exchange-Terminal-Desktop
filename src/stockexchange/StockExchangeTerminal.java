package stockexchange;

import java.awt.EventQueue;
import stockexchange.gui.PanelManager;

public class StockExchangeTerminal {

    public static void main(String[] args) {
	EventQueue.invokeLater(() -> {
	    PanelManager board = new PanelManager();
	    board.setVisible(true);
	});
    }

}
