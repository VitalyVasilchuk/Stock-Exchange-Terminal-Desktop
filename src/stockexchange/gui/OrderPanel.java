package stockexchange.gui;

import java.awt.Color;
import java.awt.Component;
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
import stockexchange.entity.order.OrderManager;
import stockexchange.entity.order.OrderModel;
import stockexchange.SessionManager;

public class OrderPanel extends JPanel implements ActionListener {

    private final List dataList;
    private final JTable dataTable = new JTable();
    private final OrderManager dataManager;

    public OrderPanel(String mode, SessionManager sessionManager) {
	dataList = new ArrayList();
	dataManager = new OrderManager(mode, sessionManager);
	dataList.addAll(dataManager.getList());
	
	init();
    }

    private void init() {
	JLabel label = new JLabel(" Выставленные ордера (BUY&SELL)");
	label.setOpaque(true);
	label.setBackground(Color.LIGHT_GRAY);

	dataTable.setBackground(new Color(253, 233, 217));
	OrderModel dm = new OrderModel(dataList);
	TableRowSorter<OrderModel> sorter = new TableRowSorter<>(dm);
	dataTable.setRowSorter(sorter);

	JScrollPane scroll = new JScrollPane(dataTable);

	JButton bReload = new JButton("Reload");
	bReload.setActionCommand("RELOAD");
	bReload.addActionListener(this);
	bReload.setBackground(Color.LIGHT_GRAY);

	JButton bDelete = new JButton("Отменить");
	bDelete.setActionCommand("DELETE");
	bDelete.addActionListener(this);
	bDelete.setBackground(Color.LIGHT_GRAY);

	JButton bBuy = new JButton("Купить");
	bBuy.setActionCommand("BUY");
	bBuy.addActionListener(this);
	bBuy.setBackground(Color.LIGHT_GRAY);

	JButton bSell = new JButton("Продать");
	bSell.setActionCommand("SELL");
	bSell.addActionListener(this);
	bSell.setBackground(Color.LIGHT_GRAY);

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
	
	// задаем Layout, располагаем компоненты
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
	this.add(bBuy, c);
	this.add(bSell, c);
	this.add(bDelete, c);

	c.gridwidth = GridBagConstraints.REMAINDER;
	this.add(bReload, c);

	c.weightx = 1.0;
	c.weighty = 1.0;
	c.insets = new Insets(1, 1, 0, 1);
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
	OrderModel dm = new OrderModel(dataList);
	dataTable.setModel(dm);
	tableRender(dataTable);
    }

    private void deleteOrder() {
	int sr = dataTable.getSelectedRow();
	if (sr != -1) {
	    Long id = Long.parseLong(dataTable.getModel().getValueAt(sr, 1).toString());
	    dataManager.delete(id);
	    getData();
	    reloadTable();
	} else {
	    JOptionPane.showMessageDialog(this, "Необходимо выделить строку для удаления.");
	}
    }
    
    private void addOrder(String operation, String params){
	dataManager.add(operation, params);
    }

    // опредение и применение рендера для раскрашивания строк сделок/заявок/ордеров
    private void tableRender(JTable table) {
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		super.setHorizontalAlignment(SwingConstants.RIGHT);
		if (table.getColumnName(0).equals("type")) {
		    String val = table.getValueAt(row, table.getColumnModel().getColumnIndex("type")).toString();
		    if (val.equals("sell")) {
			//cell.setBackground(new Color(242, 220, 219));
			cell.setForeground(new Color(128, 0, 0));
		    } else {
			//cell.setBackground(new Color(218, 248, 243));
			cell.setForeground(new Color(0, 0, 128));
		    }
		}
		return cell;
	    }
	};

	for (int i = 0; i < table.getColumnCount(); i++) {
	    table.getColumnModel().getColumn(0).setCellRenderer(renderer);
	}
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
	switch (ae.getActionCommand()) {
	    case "RELOAD":
		getData();
		reloadTable();
		break;

	    case "DELETE":
		deleteOrder();
		break;

	    case "BUY":
		OrderDialog bo = new OrderDialog("Buy order", "Buy");
		bo.setLocationRelativeTo(this);
		bo.setVisible(true);
		if (bo.isSend()) {
		    System.out.println(bo.getParams());
		    addOrder("BUY", bo.getParams());
		}
		break;

	    case "SELL":
		//Order o = new Order("sell", "0.225705", "5716.728013", "1290.294096174"); 
		OrderDialog so = new OrderDialog("Sell order", "Sell");
		so.setLocationRelativeTo(this);
		so.setVisible(true);
		if (so.isSend()) {
		    System.out.println(so.getParams());
		    addOrder("SELL", so.getParams());
		}
		break;
	}
    }
}
