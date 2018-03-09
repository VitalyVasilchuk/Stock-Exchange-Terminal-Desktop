package stockexchange.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class OrderDialog extends JDialog implements ActionListener {

    // набор констант для позиционирования контролов на форме
    private static final int PAD_X = 4;
    private static final int PAD_Y = 4;

    private static final int LBL_WIDTH = 60;
    private static final int TXT_WIDTH = 240;
    private static final int BTN_WIDTH = 80;
    private static final int CMP_HEIGHT = 24;

    private static final int FORM_WIDTH = PAD_X + LBL_WIDTH + PAD_X * 2 + TXT_WIDTH + PAD_X;

    // набор контролов, располагаемых на форме
    private JButton bCancel;
    private JButton bSend;
    private JLabel lAmount;
    private JLabel lPrice;
    private JLabel lType;
    private JLabel lVolume;
    private JTextPane tType;
    private JTextPane tAmount;
    private JTextPane tPrice;
    private JTextPane tVolume;

    private final String title;
    private final String type;

    // Заголовки кнопок
    private static final String SEND = "Send";
    private static final String CANCEL = "Cancel";

    // Надо ли записывать изменения после закрытия диалога
    private boolean send = false;

    public OrderDialog(String title, String type) {
	this.title = title;
	this.type = type;

	initComponents();
	placeButtons();
    }

    private void initComponents() {
	setLayout(null);

	lType = new JLabel("Type: ");
	lPrice = new JLabel("Price: ");
	lVolume = new JLabel("Volume: ");
	lAmount = new JLabel("Amount: ");

	tType = new JTextPane();
	tType.setEnabled(false);
	tType.setText(type);
	tPrice = new JTextPane();
	tVolume = new JTextPane();
	tAmount = new JTextPane();

	bCancel = new JButton("Cancel");
	bSend = new JButton(type);

	placeComponents(lType, tType, 0);
	placeComponents(lPrice, tPrice, 1);
	placeComponents(lVolume, tVolume, 2);
	placeComponents(lAmount, tAmount, 3);
	
	pack();

	setTitle(title);
	setModal(true);
	setResizable(false);
	setBounds(300, 300, FORM_WIDTH, PAD_Y + (PAD_Y + CMP_HEIGHT) * 6);

    }

    // размещаем метки и поля ввода на форме
    private void placeComponents(JLabel l, JTextPane t, int row) {
	// размещаем лейбу
	l.setHorizontalAlignment(SwingConstants.RIGHT);
	l.setBounds(new Rectangle(PAD_X, PAD_Y + row * (CMP_HEIGHT + PAD_Y), LBL_WIDTH, CMP_HEIGHT));
	add(l);
	
	// применяем DocumentFilter
	AbstractDocument document = (AbstractDocument) t.getDocument();
	document.setDocumentFilter(new DigitFilter());

	// размещаем поле
	t.setBounds(new Rectangle(PAD_X + LBL_WIDTH + PAD_X, PAD_Y + row * (CMP_HEIGHT + PAD_Y), TXT_WIDTH, CMP_HEIGHT));
	t.setBorder(BorderFactory.createEtchedBorder());
	add(t);
    }

    // размещаем кнопки на форме
    private void placeButtons() {
	int row = 4;

	bSend.setOpaque(true);
	bSend.setBackground(Color.LIGHT_GRAY);
	bSend.setActionCommand(SEND);
	bSend.addActionListener(this);
	bSend.setBounds(new Rectangle(FORM_WIDTH - PAD_X - (BTN_WIDTH + PAD_X) * 2, PAD_Y + row * (CMP_HEIGHT + PAD_Y), BTN_WIDTH, CMP_HEIGHT));
	add(bSend);

	bCancel.setOpaque(true);
	bCancel.setBackground(Color.LIGHT_GRAY);
	bCancel.setActionCommand(CANCEL);
	bCancel.addActionListener(this);
	bCancel.setBounds(new Rectangle(FORM_WIDTH - PAD_X - (BTN_WIDTH + PAD_X) * 1, PAD_Y + row * (CMP_HEIGHT + PAD_Y), BTN_WIDTH, CMP_HEIGHT));
	add(bCancel);
    }

    @Override
    // Обработка нажатий кнопок
    public void actionPerformed(ActionEvent ae) {
	String action = ae.getActionCommand();
	// если нажали кнопку SEND (передать данные) - запоминаем
	send = SEND.equals(action);
	// Закрываем форму
	setVisible(false);
    }

    // надо ли отправлять данные
    public boolean isSend() {
	return send;
    }

    // формируем параметры для запроса
    public String getParams() {
	String params
		= "count=" + tVolume.getText()
		+ "&price=" + tPrice.getText()
		+ "&currency1=UAH"
		+ "&currency=DOGE";

	return params;
    }

    // для фильтрации ввода символов в полях с типом double
    class DigitFilter extends DocumentFilter {

	final int MAX_CHAR = 20;

	@Override
	public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {

	    String text = fb.getDocument().getText(0,
		    fb.getDocument().getLength());
	    text += str;
	    if ((fb.getDocument().getLength() + str.length() - length) <= MAX_CHAR
		    && text.matches("^[0-9]+[.]?[0-9]{0,10}$")) {
		super.replace(fb, offs, length, str, a);
	    } else {
		Toolkit.getDefaultToolkit().beep();
	    }
	}

	@Override
	public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {

	    String text = fb.getDocument().getText(0, fb.getDocument().getLength());
	    text += str;
	    if ((fb.getDocument().getLength() + str.length()) <= MAX_CHAR
		    && text.matches("^[0-9]+[.]?[0-9]{0,10}$")) {
		super.insertString(fb, offs, str, a);
	    } else {
		Toolkit.getDefaultToolkit().beep();
	    }
	}
    }

}
