package stockexchange.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import stockexchange.entity.account.AccountManager;
import stockexchange.entity.account.AccountModel;
import stockexchange.SessionManager;

public class AccountPanel extends JPanel implements ActionListener {

    private final List dataList;
    private final AccountManager dataManager;
    private SessionManager sessionManager;
    private final JTable dataTable = new JTable();

    public AccountPanel(String mode, SessionManager sessionManager) {
	this.dataList = new ArrayList();
	this.dataManager = new AccountManager(mode, sessionManager);
	this.dataList.addAll(dataManager.getList());
	this.sessionManager = sessionManager;

	init();
    }

    private void init() {
	JLabel label = new JLabel(" Остатки на счетах");
	label.setOpaque(true);
	label.setBackground(Color.LIGHT_GRAY);

	dataTable.setBackground(new Color(221, 217, 195));
	AccountModel dm = new AccountModel(dataList);
	TableRowSorter<AccountModel> sorter = new TableRowSorter<>(dm);
	dataTable.setRowSorter(sorter);

	JScrollPane scroll = new JScrollPane(dataTable);

	JButton bReload = new JButton("Reload");
	bReload.setActionCommand("RELOAD");
	bReload.addActionListener(this);
	bReload.setBackground(Color.LIGHT_GRAY);

	JLabel filterLabel = new JLabel("Filter");

	JTextField filterText = new JTextField();
	filterText.addCaretListener((CaretEvent e) -> {
	    String text = filterText.getText();
	    if (text.length() == 0) {
		sorter.setRowFilter(null);
	    } else {
		try {
		    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
		} catch (PatternSyntaxException pse) {
		    System.err.println("Bad regex pattern");
		}
	    }
	});

	this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();

	c.fill = GridBagConstraints.BOTH;
	c.insets = new Insets(1, 1, 1, 1);
	c.weightx = 1.0;
	c.gridwidth = GridBagConstraints.REMAINDER;
	c.ipady = 5;
	this.add(label, c);

	c.ipady = 1;
	c.gridwidth = 1;
	c.weightx = 0.0;
	//this.add(new JLabel("Фильтр: "), c);
	c.weightx = 1.0;
	this.add(filterText, c);
	c.weightx = 0.0;
	c.gridwidth = GridBagConstraints.REMAINDER;
	this.add(bReload, c);

	c.weightx = 1.0;
	c.weighty = 1.0;
	this.add(scroll, c);

	reloadTable();
    }

    // загружаем внешние данные в список сделок (List)
    public void getData() {
	dataManager.getData();
	dataList.clear();
	dataList.addAll(dataManager.getList());
    }

    // перезагружаем список сделок (List) в таблице (JTable)
    public void reloadTable() {
	AccountModel dm = new AccountModel(dataList);
	dataTable.setModel(dm);
	tableRender(dataTable);
    }

    // опредение и применение рендера для раскрашивания строк сделок/заявок/ордеров
    private void tableRender(JTable table) {
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		super.setHorizontalAlignment(SwingConstants.RIGHT);
		String val = table.getValueAt(row, table.getColumnModel().getColumnIndex("валюта")).toString();
		if (val.equals("UAH") || sessionManager.getCurrencyPair().startsWith(val.toLowerCase())) {
		    super.setFont(new Font("", Font.BOLD, 12));
		}

		return cell;
	    }
	};

	for (int i = 0; i < table.getColumnCount(); i++) {
	    table.getColumnModel().getColumn(i).setCellRenderer(renderer);
	}
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
	switch (ae.getActionCommand()) {
	    case "RELOAD":
		getData();
		reloadTable();
		break;
	}
    }

    public String getSelected() {
	String currency = "";
	int sr = dataTable.getSelectedRow();
	if (sr != -1) {
	    currency = dataTable.getModel().getValueAt(sr, 0).toString();
	} else {
	}
	
	return currency;
    }

}
