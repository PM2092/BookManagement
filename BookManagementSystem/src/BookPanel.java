import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class BookPanel extends JPanel implements ActionListener, DocumentListener, KeyListener {

	private JButton addButton, updateButton, deleteButton, exitButton,
	firstButton, prevButton, nextButton, lastButton;
	private JLabel codeLabel, titleLabel, priceLabel;
	private JTextField codeField, titleField, priceField;
	public static boolean addFlag = false;
	public static NumberFormat currency = NumberFormat.getCurrencyInstance();
	Book currentBook = null;

	public BookPanel() {
		createContainer();
		openDatabase();
		showBookDetails();
		enableButtons(true);
	}

	/**
	 * Creates the frame container.
	 */
	private void createContainer () {
		codeLabel = new JLabel(Constants.JLABEL_CODE);
		codeField = new JTextField("", 7);
		titleLabel = new JLabel(Constants.JLABEL_TITLE);
		titleField = new JTextField("", 26);
		priceLabel = new JLabel(Constants.JLABEL_PRICE);
		priceField = new JTextField("", 7);
		
		codeField.addKeyListener(this);
		titleField.addKeyListener(this);
		priceField.addKeyListener(this);
		codeField.getDocument().addDocumentListener(this);
		titleField.getDocument().addDocumentListener(this);
		priceField.getDocument().addDocumentListener(this);

		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.weightx = 100;
		gbConstraints.weighty = 100;
		gbConstraints.ipadx = 5;

		gbConstraints.anchor = GridBagConstraints.EAST;
		gbConstraints = getConstraints(gbConstraints, 1, 1, 1, 1);
		add(codeLabel, gbConstraints);
		gbConstraints = getConstraints(gbConstraints, 1, 2, 1, 1);
		add(titleLabel, gbConstraints);
		gbConstraints = getConstraints(gbConstraints, 1, 3, 1, 1);
		add(priceLabel, gbConstraints);

		gbConstraints.anchor = GridBagConstraints.WEST;
		gbConstraints = getConstraints(gbConstraints, 2, 1, 3, 1);
		add(codeField, gbConstraints);
		gbConstraints = getConstraints(gbConstraints, 2, 2, 3, 1);
		add(titleField, gbConstraints);
		gbConstraints = getConstraints(gbConstraints, 2, 3, 3, 1);
		add(priceField, gbConstraints);

		gbConstraints.anchor = GridBagConstraints.CENTER;
		gbConstraints = getConstraints(gbConstraints, 1, 4, 4, 1);
		add(createDataUpdatePanel(), gbConstraints);
		gbConstraints = getConstraints(gbConstraints, 1, 5, 4, 1);
		add(createNavigationPanel(), gbConstraints);
	}
	
	
	/**
	 * Create panel with buttons to update data such as add, update, delete, exit.
	 */
	private JPanel createDataUpdatePanel() {
		JPanel dataUpdatePanel = new JPanel();
		addButton = new JButton(Constants.JBUTTON_ADD);
		updateButton = new JButton(Constants.JBUTTON_UPDATE);
		deleteButton = new JButton(Constants.JBUTTON_DELETE);
		exitButton = new JButton(Constants.JBUTTON_EXIT);
		dataUpdatePanel.add(addButton);
		dataUpdatePanel.add(updateButton);
		dataUpdatePanel.add(deleteButton);
		dataUpdatePanel.add(exitButton);

		addButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		exitButton.addActionListener(this);
		return dataUpdatePanel;
	}
	
	/**
	 * Create panel with buttons to navigate through data such as next, previous, first, last.
	 */
	private JPanel createNavigationPanel() {
		JPanel navigationPanel = new JPanel();
		firstButton = new JButton(Constants.JBUTTON_FIRST);
		prevButton = new JButton(Constants.JBUTTON_PREV);
		nextButton = new JButton(Constants.JBUTTON_NEXT);
		lastButton = new JButton(Constants.JBUTTON_LAST);
		navigationPanel.add(firstButton);
		navigationPanel.add(prevButton);
		navigationPanel.add(nextButton);
		navigationPanel.add(lastButton);
		
		firstButton.addActionListener(this);
		prevButton.addActionListener(this);
		nextButton.addActionListener(this);
		lastButton.addActionListener(this);
		return navigationPanel;
	}
	
	
	private GridBagConstraints getConstraints(GridBagConstraints c, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		return c;
	}
	
	/**
	 * Exception handling for database connection.
	 */
	private void openDatabase() {
		try{
			BookDB.loadDB();
			BookDB.open();
			currentBook = BookDB.moveFirst();

		}
		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(1);
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	
	/**
	 * Code to display the data in the frame container.
	 */
	private void showBookDetails() {
		codeField.setText(currentBook.getCode());
		titleField.setText(currentBook.getTitle());
		priceField.setText(currency.format(currentBook.getPrice()));
	}

	/**
	 * @param flag1
	 * code to enable buttons according to requirement.
	 */
	private void enableButtons(boolean flag1) {
		boolean flag2 = false;
		if (flag1 == false)
			flag2 = true;
		updateButton.setEnabled(flag2);
		addButton.setEnabled(flag1);
		deleteButton.setEnabled(flag1);
		firstButton.setEnabled(flag1);
		nextButton.setEnabled(flag1);
		prevButton.setEnabled(flag1);
		lastButton.setEnabled(flag1);
	}

	/**
	 * @param currencyString
	 * @return
	 */
	private double formatCurrency(String currencyString) {
		if (currencyString.charAt(0) == '$')
			currencyString = currencyString.substring(1);
		return Double.parseDouble(currencyString);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			Object source = e.getSource();
			if (source == exitButton) {
				BookDB.close();
				System.exit(0);
			}
			else if (source == firstButton) {
				currentBook = BookDB.moveFirst();
				showBookDetails();
				enableButtons(true);
			}
			else if (source == prevButton) {
				currentBook = BookDB.movePrevious();
				showBookDetails();
				enableButtons(true);
			}
			else if (source == nextButton) { 
				currentBook = BookDB.moveNext();
				showBookDetails();
				enableButtons(true);
			}
			else if (source == lastButton) {
				currentBook = BookDB.moveLast();
				BookDB.findOnCode("12");
				showBookDetails();
				enableButtons(true);
			}
			else if (source == addButton) {
				codeField.requestFocus();
				enableButtons(false);
				codeField.setText("");
				titleField.setText("");
				priceField.setText("");
				addFlag = true;
			}
			else if (source == updateButton) {
				double price = formatCurrency(priceField.getText());
				Book book = new Book(codeField.getText(),
						titleField.getText(),
						price);
				if (addFlag == false) {
					BookDB.saveDB(book);
					currentBook = book;
				}
				if (addFlag == true) {
					BookDB.addRecord(book);
					currentBook = BookDB.moveFirst();
					addFlag = false;
				}
				currentBook = book;
				showBookDetails();
				enableButtons(true);
			}
			else if(source == deleteButton) {
				BookDB.deleteRecord(currentBook.getCode());
				firstButton.requestFocus();
				firstButton.doClick();
				showBookDetails();
				enableButtons(true);
			}
		}
		catch (NumberFormatException numberFormatExe) {
			JOptionPane.showMessageDialog(this, numberFormatExe.getMessage());
		}
		catch (SQLException SQLExe) {
			JOptionPane.showMessageDialog(this, SQLExe.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ESCAPE) {
			showBookDetails();
			enableButtons(true);
			codeField.requestFocus();
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		enableButtons(false); 
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		enableButtons(false);
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
	}
}