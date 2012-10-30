package org.zooper.becuz.restmote.serverui;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import org.zooper.becuz.restmote.serverui.panels.PanelStatus;

public class MainWindow extends JFrame {

	private JPanel panelStatus;
	private JPanel panelOptions;
	private JPanel panelFiles;
	private JPanel panelApps;
	private JPanel panelAbout;
	
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public MainWindow() {
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Tab Status", UIConstants.createImageIcon("resources/tabStatus.png", "buh", 32, 32, false), getPanelStatus(), "Does nothing");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tabbedPane.addTab("Tab Options", UIConstants.createImageIcon("resources/tabOptions.png", "buh", 32, 32, false), makeTextPanel("Panel #2"), "Does twice as much nothing");
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		tabbedPane.addTab("Tab Files", UIConstants.createImageIcon("resources/tabFiles.png", "buh", 32, 32, false), makeTextPanel("Panel #3"), "Still does nothing");
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

//		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab Apps", UIConstants.createImageIcon("resources/tabApps.png", "buh", 32, 32, false), makeTextPanel("Panel #2"), "Does nothing at all");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		tabbedPane.addTab("Tab About", UIConstants.createImageIcon("resources/tabAbout.png", "buh", 32, 32, false), makeTextPanel("Panel #2"), "Does nothing at all");
		tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
		
		getContentPane().add(tabbedPane);
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	private JPanel getPanelStatus() {
		if (panelStatus == null){
			panelStatus = new PanelStatus();
		}
		return panelStatus;
	}

	private void setPanelStatus(JPanel panelStatus) {
		this.panelStatus = panelStatus;
	}

	private JPanel getPanelOptions() {
		return panelOptions;
	}

	private void setPanelOptions(JPanel panelOptions) {
		this.panelOptions = panelOptions;
	}

	private JPanel getPanelFiles() {
		return panelFiles;
	}

	private void setPanelFiles(JPanel panelFiles) {
		this.panelFiles = panelFiles;
	}

	private JPanel getPanelApps() {
		return panelApps;
	}

	private void setPanelApps(JPanel panelApps) {
		this.panelApps = panelApps;
	}

	private JPanel getPanelAbout() {
		return panelAbout;
	}

	private void setPanelAbout(JPanel panelAbout) {
		this.panelAbout = panelAbout;
	}

}