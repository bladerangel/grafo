package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import utils.ImagePath;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final ImageIcon ImageTest = ImagePath.setImage("/test.png");
	private static final ImageIcon ImageGraphicDisplay = ImagePath.setImage("/graphicDisplay.png");
	private static final ImageIcon ImageLog = ImagePath.setImage("/log.png");
	private static final ImageIcon ImageAdd = ImagePath.setImage("/add.png");
	private static final ImageIcon ImageRemove = ImagePath.setImage("/remove.png");
	private static final ImageIcon ImageCleanLog = ImagePath.setImage("/cleanLog.png");
	private static final ImageIcon ImageImport = ImagePath.setImage("/import.png");
	private static final ImageIcon ImageExport = ImagePath.setImage("/export.png");
	private static final ImageIcon ImageNew = ImagePath.setImage("/new.png");

	private JPanel contentPane;
	private JPanel panelGraph;
	private JPanel panelTitle;
	private JPanel panelButtons;
	private JScrollPane scrollPaneLog;
	private JTextArea textAreaLog;
	private JButton btnAddState;
	private JButton btnAddEvent;
	private JLabel lblTitleGraph;
	private JLabel lblTitleLog;
	private JButton btnTest;
	private JButton btnRemoveState;
	private JButton btnRemoveEvent;
	private JLabel lblStateActions;
	private JLabel lblEventActions;
	private JButton btnCleanLog;
	private JMenuBar menuBar;
	private JMenu mnMenu;
	private JMenuItem mntmImport;
	private JMenuItem mntmExport;
	private JMenuItem mntmNew;

	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 843, 649);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnMenu = new JMenu("File");
		mnMenu.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuBar.add(mnMenu);

		mntmNew = new JMenuItem("New");
		mntmNew.setForeground(Color.BLUE);
		mntmNew.setFont(new Font("Tahoma", Font.BOLD, 12));
		mntmNew.setIcon(ImageNew);
		mnMenu.add(mntmNew);

		mntmImport = new JMenuItem("Import");
		mntmImport.setFont(new Font("Tahoma", Font.BOLD, 12));
		mntmImport.setForeground(Color.BLUE);
		mntmImport.setIcon(ImageImport);
		mnMenu.add(mntmImport);

		mntmExport = new JMenuItem("Export");
		mntmExport.setForeground(Color.BLUE);
		mntmExport.setFont(new Font("Tahoma", Font.BOLD, 12));
		mntmExport.setIcon(ImageExport);
		mnMenu.add(mntmExport);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelGraph = new JPanel();
		panelGraph.setBorder(UIManager.getBorder("InternalFrame.border"));
		panelGraph.setBounds(43, 113, 453, 370);
		contentPane.add(panelGraph);

		panelTitle = new JPanel();
		panelTitle.setBounds(43, 11, 757, 91);
		contentPane.add(panelTitle);
		panelTitle.setLayout(null);

		lblTitleGraph = new JLabel("");
		lblTitleGraph.setBounds(57, 11, 311, 63);
		lblTitleGraph.setIcon(ImageGraphicDisplay);
		panelTitle.add(lblTitleGraph);

		lblTitleLog = new JLabel("");
		lblTitleLog.setBounds(524, 11, 210, 63);
		lblTitleLog.setIcon(ImageLog);
		panelTitle.add(lblTitleLog);

		panelButtons = new JPanel();
		panelButtons.setBounds(31, 494, 475, 116);
		contentPane.add(panelButtons);
		panelButtons.setLayout(null);

		btnAddState = new JButton("");
		btnAddState.setBounds(26, 36, 40, 40);
		btnAddState.setIcon(ImageAdd);
		panelButtons.add(btnAddState);

		btnAddEvent = new JButton("");
		btnAddEvent.setBounds(337, 36, 40, 40);
		btnAddEvent.setIcon(ImageAdd);
		panelButtons.add(btnAddEvent);

		btnTest = new JButton("");
		btnTest.setBounds(169, 36, 137, 40);
		btnTest.setIcon(ImageTest);
		panelButtons.add(btnTest);

		btnRemoveState = new JButton("");
		btnRemoveState.setBounds(93, 36, 40, 40);
		btnRemoveState.setIcon(ImageRemove);
		panelButtons.add(btnRemoveState);

		btnRemoveEvent = new JButton("");
		btnRemoveEvent.setBounds(403, 36, 40, 40);
		btnRemoveEvent.setIcon(ImageRemove);
		panelButtons.add(btnRemoveEvent);

		lblStateActions = new JLabel("State Actions");
		lblStateActions.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStateActions.setBounds(37, 11, 107, 14);
		panelButtons.add(lblStateActions);

		lblEventActions = new JLabel("Event Actions");
		lblEventActions.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEventActions.setBounds(347, 11, 107, 14);
		panelButtons.add(lblEventActions);

		scrollPaneLog = new JScrollPane();
		scrollPaneLog.setBounds(533, 113, 267, 316);
		contentPane.add(scrollPaneLog);

		textAreaLog = new JTextArea();
		textAreaLog.setEditable(false);
		scrollPaneLog.setViewportView(textAreaLog);

		btnCleanLog = new JButton("");
		btnCleanLog.setBounds(593, 443, 137, 40);
		btnCleanLog.setIcon(ImageCleanLog);
		contentPane.add(btnCleanLog);

	}

	public void actionListeners(ActionListener actionListener) {
		btnAddState.setActionCommand("addState");
		btnRemoveState.setActionCommand("removeState");
		btnAddEvent.setActionCommand("addEvent");
		btnRemoveEvent.setActionCommand("removeEvent");
		btnTest.setActionCommand("test");
		mntmImport.setActionCommand("import");
		mntmExport.setActionCommand("export");
		mntmNew.setActionCommand("new");

		btnCleanLog.setActionCommand("cleanLog");
		btnAddState.addActionListener(actionListener);
		btnAddEvent.addActionListener(actionListener);
		btnTest.addActionListener(actionListener);
		btnRemoveState.addActionListener(actionListener);
		btnRemoveEvent.addActionListener(actionListener);
		btnCleanLog.addActionListener(actionListener);
		mntmImport.addActionListener(actionListener);
		mntmExport.addActionListener(actionListener);
		mntmNew.addActionListener(actionListener);
	}

	public JPanel getPanelGraph() {
		return panelGraph;
	}

	public JTextArea getTextAreaLog() {
		return textAreaLog;
	}
}