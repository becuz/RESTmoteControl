package org.zooper.becuz.restmote.ui.widgets;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.zooper.becuz.restmote.ui.MainWindow;
import org.zooper.becuz.restmote.utils.Utils;

public class IntTextField extends JTextField implements DocumentListener {
	
	private static final long serialVersionUID = 1L;

	private MainWindow mainWindow;
	
    public IntTextField(MainWindow mainWindow) {
		super();
		this.mainWindow = mainWindow;
	}
        
//	public IntTextField(int defval, int size) {
//		super("" + defval, size);
//	}

	protected Document createDefaultModel() {
		Document doc = new IntTextDocument();
		doc.addDocumentListener(this);
		return doc;
	}

	public boolean isValid() {
		try {
			if (Utils.isEmpty(getText())){
				return true;
			}
			Integer.parseInt(getText());
			return true;		
		} catch (NumberFormatException e) {
		} catch (NullPointerException e) {}
		return false;
	}

	public int getValue() {
		try {
			return Integer.parseInt(getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	class IntTextDocument extends PlainDocument {
		
		public void insertString(int offs, String str, AttributeSet a)throws BadLocationException {
			if (str == null)
				return;
			String oldString = getText(0, getLength());
			String newString = oldString.substring(0, offs) + str
					+ oldString.substring(offs);
			try {
				Integer.parseInt(newString + "0");
				super.insertString(offs, str, a);
			} catch (NumberFormatException e) {
			}
		}
		
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		mainWindow.refreshLblServerUrl();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		mainWindow.refreshLblServerUrl();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		mainWindow.refreshLblServerUrl();		
	}

}