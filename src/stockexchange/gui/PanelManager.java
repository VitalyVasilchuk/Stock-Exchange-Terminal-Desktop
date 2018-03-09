package stockexchange.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import stockexchange.SessionManager;

public class PanelManager extends JFrame implements ActionListener {

    private final String mode;
    private final SessionManager sessionManager;

    private AccountPanel accountPanel;
    private BuyPanel buyPanel;
    private SellPanel sellPanel;
    private OrderPanel orderPanel;
    private DealPanel dealPanel;
    
    private final int DELAY = 60*1000;
    private Timer timer;

    public PanelManager() {
	this.mode = "POST";
	this.sessionManager = new SessionManager("btc_uah");
	sessionManager.auth();

	init();
	
	timer = new Timer(DELAY, this);
	timer.start();
    }

    private void init() {
	// настройка параметров внешнего представления основной формы GUI
	setBounds(0, 0, 1280, 720);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setTitle("Stock Exchange (" + sessionManager.getCurrencyPair().toUpperCase().replace('_', '/') + ")");
	setLocationRelativeTo(null);

	// создание панелей со списками данных
	accountPanel = new AccountPanel(mode, sessionManager);
	buyPanel = new BuyPanel(mode, sessionManager);
	sellPanel = new SellPanel(mode, sessionManager);
	orderPanel = new OrderPanel(mode, sessionManager);
	dealPanel = new DealPanel(mode, sessionManager);

	JButton bSwitch = new JButton("Сменить валюту");
	bSwitch.setActionCommand("SWITCH");
	bSwitch.addActionListener(this);
	bSwitch.setBackground(Color.LIGHT_GRAY);

	// размещение панелей данных на форме
	setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.insets = new Insets(1, 1, 1, 1);
	c.fill = GridBagConstraints.BOTH;
	c.weightx = 0.25;
	c.weighty = 1.0;
	c.gridwidth = 1;
	c.gridheight = 2;
	add(accountPanel, c);

	c.fill = GridBagConstraints.BOTH;
	c.gridheight = 1;
	c.weightx = 1.0;
	c.weighty = 0.4;
	add(buyPanel, c);

	c.gridwidth = GridBagConstraints.REMAINDER;
	c.weighty = 0.4;
	add(sellPanel, c);

	c.weighty = 0.2;
	add(orderPanel, c);

	c.fill = GridBagConstraints.HORIZONTAL;
	c.anchor = GridBagConstraints.NORTH;
	c.weightx = 0.25;
	c.weighty = 0.0;
	c.gridwidth = 1;
	c.gridheight = 1;
	add(bSwitch, c);
	
	c.fill = GridBagConstraints.BOTH;
	c.gridwidth = GridBagConstraints.REMAINDER;
	c.gridheight = 1;
	c.weightx = 1.0;
	c.weighty = 0.4;
	add(dealPanel, c);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
	// если пришла команда, обрабатываем как нажатие кнопки на форме
	if (ae.getActionCommand() != null) {
	    switch (ae.getActionCommand()) {
		case "SWITCH":
		    String currencySelected = accountPanel.getSelected();
		    if (currencySelected.equals("")) {
			JOptionPane.showMessageDialog(this, "Необходимо выделить строку c наименованием требуемой валюты.");
		    } else {
			sessionManager.setCurrencyPair(currencySelected.toLowerCase() + "_uah");
			setTitle("Stock Exchange (" + sessionManager.getCurrencyPair().toUpperCase().replace('_', '/') + ")");
			ReloadAllPanels();
		    }

		    break;
	    }
	}
	// иначе считаем событием от таймера
	else {
	    ReloadAllPanels();
	}
    }
    
    private void ReloadAllPanels(){
	accountPanel.getData();
	accountPanel.reloadTable();
	buyPanel.getData();
	buyPanel.reloadTable();
	sellPanel.getData();
	sellPanel.reloadTable();
	orderPanel.getData();
	orderPanel.reloadTable();
	dealPanel.getData();
	dealPanel.reloadTable();
    }
}
