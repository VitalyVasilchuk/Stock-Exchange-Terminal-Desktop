package stockexchange.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class KeyDialog extends JDialog implements ActionListener {

    // набор констант для позиционирования контролов на форме
    private static final int PAD_X = 4;
    private static final int PAD_Y = 4;

    private static final int LBL_WIDTH = 120;
    private static final int TXT_WIDTH = 360;
    private static final int BTN_WIDTH = 80;
    private static final int CMP_HEIGHT = 24;
    
    private static final int FORM_WIDTH = PAD_X + LBL_WIDTH + PAD_X * 2 + TXT_WIDTH + PAD_X;

    // набор контролов, располагаемых на форме
    private JButton bCancel;
    private JButton bSend;
    private JLabel lPrivateKey;
    private JLabel lPublicKey;
    private JTextPane tPublicKey;
    private JTextPane tPrivateKey;
    
    private final String title;

    // Заголовки кнопок
    private static final String SAVE = "Save";
    private static final String CANCEL = "Cancel";

    // Надо ли записывать изменения после закрытия диалога
    private boolean save = false;

    public KeyDialog(String title) {
	this.title = title;
	
	initComponents();
    }

    private void initComponents() {
	setLayout(null);
	
	lPublicKey = new JLabel("Public-key: ");
	lPrivateKey = new JLabel("Private-key: ");

	
	tPublicKey = new JTextPane();
	tPrivateKey = new JTextPane();
	
	bCancel = new JButton("Cancel");
	bSend = new JButton(SAVE);

	placeComponents(lPublicKey, tPublicKey, 0);
	placeComponents(lPrivateKey, tPrivateKey, 1);
	
	placeButtons(2);
	
	pack();
	
 	setTitle(title);
	setModal(true);
	setResizable(false);
	setBounds(300, 300, FORM_WIDTH, PAD_Y + (PAD_Y + CMP_HEIGHT) * 4);
	
   }
    
    // размещаем метки и поля ввода на форме
    private void placeComponents(JLabel l, JTextPane t, int row) {
	l.setHorizontalAlignment(SwingConstants.RIGHT);
	l.setBounds(new Rectangle(PAD_X, PAD_Y + row * (CMP_HEIGHT + PAD_Y), LBL_WIDTH, CMP_HEIGHT));
	add(l);
	
	t.setBounds(new Rectangle(PAD_X + LBL_WIDTH + PAD_X, PAD_Y + row * (CMP_HEIGHT + PAD_Y), TXT_WIDTH, CMP_HEIGHT));
	t.setBorder(BorderFactory.createEtchedBorder());
	add(t);
    }

    // размещаем кнопки на форме
    private void placeButtons(int row) {

	bSend.setOpaque(true);
	bSend.setBackground(Color.LIGHT_GRAY);
	bSend.setActionCommand(SAVE);
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
	save = SAVE.equals(action);
	// Закрываем форму
	setVisible(false);
    }

    // надо ли отправлять данные
    public boolean isSend() {
	return save;
    }

}
