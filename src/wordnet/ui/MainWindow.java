package wordnet.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.*;
import java.util.*;

//import javax.media.Manager;
//import javax.media.Player;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import wordnet.base.*;
import wordnet.listener.*;
import net.didion.jwnl.*;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.Dictionary;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 7935640868482365848L;
	public static MainWindow mw;
	// 按钮区
	private JButton forwardButton;
	private JButton backButton;
	private JButton searchButton;
	private JButton onnetButton;
	private JButton pronounceButton;

	// 文字区
	public JFormattedTextField inputTextField;
	private JTextArea title;
	public JTabbedPane tabbedPane;
	private TreePane treepane;

	// 播放路径
	// private String playPath;

	// 字体设置窗口
	private JFrame FontFrame;

	// 查询区
	public MyWord word;
	public String CurrentWord;

	// 历史区
	private List<String> History;
	private int ListIndex;

	// 风格区
	private String[] Fonts;
	private String[] Colors;
	private Color[] ColorOptions;

	private Font tagFont;
	private Font defaultfont;
	private Color defaultcolor;
	private Color defaultcolorb;

	// 标签区
	private ImageIcon[] doticon;
	private String[] partofspeach;
	private String[] suffix;

	// **********************************************************************
	// Luo Beier
	private Font menuFont = new Font("Century", Font.PLAIN, 15);
	private Font areaFont = new Font("Comic Sans MS", Font.PLAIN, 15);
	private JFrame jFrame;
	// --------------------REWRITEPART----------------------
	private JFrame help1Frame = new JFrame("Help On Using Our Browser");
	private JFrame help2Frame = new JFrame("Help On WordNet Terminoloty");
	private JFrame help3Frame = new JFrame("Display The WordNet Lisence");
	private JFrame wordFrame = new JFrame("My NoteBook");

	// private JTextArea wordArea = new JTextArea();
	// private JScrollPane wordScroll = new JScrollPane(wordArea);
	private JTextArea help1Area = new JTextArea();
	private JScrollPane help1Scroll = new JScrollPane(help1Area,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JTextArea help2Area = new JTextArea();
	private JScrollPane help2Scroll = new JScrollPane(help2Area,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	private JTextArea help3Area = new JTextArea();
	private JScrollPane help3Scroll = new JScrollPane(help3Area,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	// --------------------REWRITEPART------------------------

	private JMenuBar menuBar = new JMenuBar();

	private JMenu file = new JMenu("File");
	private JMenuItem outToFile;
	private JMenuItem addWord;
	private JMenuItem notebook;
	private JMenuItem exit;

	private JMenu edit = new JMenu("Edit");
	private JMenuItem changeFont;
	private JCheckBoxMenuItem autoPronounce;
	private JMenu changeSearchWeb;
	private JRadioButtonMenuItem google;
	private JRadioButtonMenuItem baidu;
	private JRadioButtonMenuItem haici;
	private JRadioButtonMenuItem youdao;
	private JMenuItem clearHistory;

	private JMenu help = new JMenu("Help");
	private JMenuItem using;
	private JMenuItem terminology;
	private JMenuItem license;
	private JMenuItem aboutUs;
	// **********************************************************************
	{// 初始化块
		word = null;
		CurrentWord = null;

		History = new ArrayList<String>();
		ListIndex = 0;

		Fonts = new String[] { "Serif", "Consolas", "Fixed", "Comic Sans MS" };
		Colors = new String[] { "Black", "Blue", "Cyan", "Gray", "Green",
				"Magenta", "Orange", "Pink", "Red", "White", "Yellow" };
		ColorOptions = new Color[] { Color.BLACK, Color.BLUE, Color.CYAN,
				Color.GRAY, Color.GREEN, Color.MAGENTA, Color.ORANGE,
				Color.PINK, Color.RED, Color.WHITE, Color.YELLOW };

		tagFont = new Font("Serif", Font.BOLD, 15);
		defaultfont = new Font("Consolas", 0, 13);
		defaultcolor = Color.BLACK;
		defaultcolorb = Color.WHITE;

		doticon = new ImageIcon[4];
		for (int i = 0; i < 4; i++) {
			doticon[i] = new ImageIcon(
					MainWindow.class.getResource("/resources/icons/dot"
							+ (1 + i) + ".png"));
		}

		partofspeach = new String[] { "Noun", "Adjective", "Verb", "Adverb" };
		suffix = new String[] { ".n", ".adj", ".v", ".adv" };

		inputTextField = new JFormattedTextField();

		inputTextField.addActionListener(new SearchButtonListener());
		inputTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				InputAreaSelected();
				// inputTextField.setText("");
			}

			public void focusLost(FocusEvent e) {
			}
		});
		inputTextField.setFont(new Font("Arial", 0, 20));
		inputTextField.setDragEnabled(true);
		inputTextField.addMouseListener(new MyMouseListener());
	}

	public MainWindow() throws ParseException {
		super("PowerSearch_3.0");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setIconImage(new ImageIcon(MainWindow.class
				.getResource("/resources/icons/wn.png")).getImage());
		this.setBounds(300, 300, 590, 150);
		this.setResizable(false);
		this.setLayout(null);

		// **********************************************************************
		// Luo Beier
		jFrame = this;
		// --------------------REWRITEPART------------------------
		// wordArea.setFont(areaFont);
		// wordArea.setEditable(false);

		wordFrame.setBounds(400, 150, 455, 470);

		help1Area.setFont(areaFont);
		help1Area.setEditable(false);
		help1Area.setLineWrap(true);
		help1Area.setWrapStyleWord(true);
		help1Frame.setBounds(370, 150, 600, 470);
		help1Frame.add(help1Scroll);
		help2Area.setFont(areaFont);
		help2Area.setEditable(false);
		help2Area.setLineWrap(true);
		help2Area.setWrapStyleWord(true);
		help2Frame.setBounds(370, 150, 600, 470);
		help2Frame.add(help2Scroll);
		help3Area.setFont(areaFont);
		help3Area.setEditable(false);
		help3Area.setLineWrap(true);
		help3Area.setWrapStyleWord(true);
		help3Frame.setBounds(370, 150, 600, 470);
		help3Frame.add(help3Scroll);

		outToFile = new JMenuItem("Save In File", new ImageIcon(
				MainWindow.class.getResource("/resources/icons/saveicon.png")));
		outToFile.setFont(menuFont);
		addWord = new JMenuItem("Add To My Notebook", new ImageIcon(
				MainWindow.class
						.getResource("/resources/icons/addwordicon.png")));
		addWord.setFont(menuFont);
		notebook = new JMenuItem("Open My Notebook",
				new ImageIcon(MainWindow.class
						.getResource("/resources/icons/write2icon.png")));

		notebook.setFont(menuFont);
		exit = new JMenuItem("Exit", new ImageIcon(
				MainWindow.class.getResource("/resources/icons/exiticon.png")));
		exit.setFont(menuFont);

		changeFont = new JMenuItem("Font", new ImageIcon(
				MainWindow.class.getResource("/resources/icons/fonticon.png")));
		changeFont.setFont(menuFont);
		autoPronounce = new JCheckBoxMenuItem("Auto-Pronounce", new ImageIcon(
				MainWindow.class.getResource("/resources/icons/soundicon.png")));
		autoPronounce.setFont(menuFont);
		autoPronounce.setSelected(true);

		changeSearchWeb = new JMenu("Change The Internet");
		changeSearchWeb.setFont(menuFont);
		google = new JRadioButtonMenuItem("google",
				new ImageIcon(MainWindow.class
						.getResource("/resources/icons/googleicon.png")), true);
		google.setFont(menuFont);
		baidu = new JRadioButtonMenuItem("baidu", new ImageIcon(
				MainWindow.class.getResource("/resources/icons/baiduicon.png")));
		baidu.setFont(menuFont);
		haici = new JRadioButtonMenuItem("Dict.cn", new ImageIcon(
				MainWindow.class.getResource("/resources/icons/haiciicon.png")));
		haici.setFont(menuFont);
		youdao = new JRadioButtonMenuItem("youdao",
				new ImageIcon(MainWindow.class
						.getResource("/resources/icons/youdaoicon.png")));
		youdao.setFont(menuFont);
		clearHistory = new JMenuItem("Clear History Record",
				new ImageIcon(MainWindow.class
						.getResource("/resources/icons/deleteicon.png")));
		clearHistory.setFont(menuFont);

		using = new JMenuItem("Help On Using The WordNet Browser",
				new ImageIcon(MainWindow.class
						.getResource("/resources/icons/questionicon.png")));
		using.setFont(menuFont);
		terminology = new JMenuItem("Help On WordNet Terminology",
				new ImageIcon(MainWindow.class
						.getResource("/resources/icons/questionicon.png")));
		terminology.setFont(menuFont);
		license = new JMenuItem("Display The WordNet License", new ImageIcon(
				MainWindow.class
						.getResource("/resources/icons/questionicon.png")));
		license.setFont(menuFont);
		aboutUs = new JMenuItem("About Us", new ImageIcon(
				MainWindow.class.getResource("/resources/icons/smallus.png")));
		aboutUs.setFont(menuFont);

		file.setMnemonic(KeyEvent.VK_F);

		outToFile.setMnemonic(KeyEvent.VK_S);
		outToFile.addActionListener(new outToFileListener());
		outToFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		file.add(outToFile);
		addWord.addActionListener(new addWordListener());
		addWord.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				ActionEvent.CTRL_MASK));
		file.add(addWord);
		notebook.addActionListener(new openBookListener());
		notebook.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		file.add(notebook);
		file.addSeparator();
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				ActionEvent.CTRL_MASK));
		file.add(exit);
		file.setFont(menuFont);
		file.setBorderPainted(true);
		menuBar.add(file);

		edit.setMnemonic(KeyEvent.VK_E);
		changeFont.addActionListener(new changeFontListener());
		edit.add(changeFont);
		edit.add(autoPronounce);
		ButtonGroup web = new ButtonGroup();
		web.add(google);
		web.add(baidu);
		web.add(youdao);
		web.add(haici);
		changeSearchWeb.add(google);
		changeSearchWeb.add(baidu);
		changeSearchWeb.add(youdao);
		changeSearchWeb.add(haici);
		edit.add(changeSearchWeb);
		clearHistory.addActionListener(new clearHistoryListener());
		edit.add(clearHistory);
		edit.setBorderPainted(true);
		edit.setFont(menuFont);
		menuBar.add(edit);

		help.setMnemonic(KeyEvent.VK_H);
		using.addActionListener(new help1Listener());
		help.add(using);
		terminology.addActionListener(new help2Listener());
		help.add(terminology);
		license.addActionListener(new help3Listener());
		help.add(license);
		help.addSeparator();
		aboutUs.addActionListener(new aboutUsListener());
		help.add(aboutUs);
		help.setBorderPainted(true);
		help.setFont(menuFont);
		menuBar.add(help);

		menuBar.setBounds(0, 0, 1200, 40);
		menuBar.setBorderPainted(true);

		this.add(menuBar);
		// **********************************************************************

		int Hight1 = 40;
		int Width1 = 5;

		// 后退按钮
		backButton = new JButton(
				new ImageIcon(MainWindow.class
						.getResource("/resources/icons/left_arrow.png")));
		backButton.setBounds(Width1 + 8, Hight1 + 4, 40, 24);
		backButton.setToolTipText("Last Word");
		backButton.setOpaque(false);
		backButton.setBackground(Color.BLACK);
		backButton.setBorder(null);
		backButton.setFocusPainted(false);
		backButton.setEnabled(false);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ListIndex > 0) {
					ListIndex--;
					forwardButton.setEnabled(true);
					if (ListIndex == 0)
						backButton.setEnabled(false);
					String s = new String(History.get(ListIndex));
					inputTextField.setText(s);
					SearchSucceeded(s);
				}
				InputAreaSelected();
				return;
			}
		});
		this.add(backButton);

		// 前进按钮
		forwardButton = new JButton(new ImageIcon(
				MainWindow.class
						.getResource("/resources/icons/right_arrow.png")));
		forwardButton.setBounds(Width1 + 55, Hight1 + 4, 40, 24);
		forwardButton.setToolTipText("Next Word");
		forwardButton.setOpaque(false);
		forwardButton.setBackground(Color.BLACK);
		forwardButton.setBorder(null);
		forwardButton.setFocusPainted(false);
		forwardButton.setEnabled(false);
		forwardButton.addActionListener(new forwardListener());
		this.add(forwardButton);

		// 输入区域
		inputTextField.setBounds(Width1 + 106, Hight1, 330, 33);
		this.add(inputTextField);

		// 搜索按钮
		searchButton = new JButton(new ImageIcon(
				MainWindow.class.getResource("/resources/icons/magnifier.png")));
		searchButton.setBounds(Width1 + 445, Hight1, 32, 32);
		searchButton.setToolTipText("Search");
		searchButton.setOpaque(false);
		searchButton.setBackground(Color.BLACK);
		searchButton.setBorder(null);
		searchButton.setFocusPainted(false);
		searchButton.addActionListener(new SearchButtonListener());
		this.add(searchButton);

		// 网络查词按钮
		onnetButton = new JButton(new ImageIcon(
				MainWindow.class.getResource("/resources/icons/internet.png")));
		onnetButton.setBounds(Width1 + 490, Hight1, 32, 32);
		onnetButton.setToolTipText("Search Online");
		onnetButton.setOpaque(false);
		onnetButton.setBackground(Color.BLACK);
		onnetButton.setBorder(null);
		onnetButton.setFocusPainted(false);
		onnetButton.addActionListener(new onnetListener());
		this.add(onnetButton);

		// 发音按钮（需要监听器）
		pronounceButton = new JButton(new ImageIcon(
				MainWindow.class.getResource("/resources/icons/sound.png")));
		pronounceButton.setBounds(Width1 + 537, Hight1 + 6, 20, 20);
		pronounceButton.setToolTipText("Pronounce");
		pronounceButton.setOpaque(false);
		pronounceButton.setBackground(Color.BLACK);
		pronounceButton.setBorder(null);
		pronounceButton.setFocusPainted(false);
		pronounceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// readWord();
			}
		});
		this.add(pronounceButton);

		int Hight2 = Hight1 + 30;
		// 单词标题
		title = new JTextArea();
		title.setBounds(Width1, Hight2, 580, 50);
		title.setEditable(false);
		title.setOpaque(false);
		title.setFont(new Font("Serif", 0, 30));
		this.add(title);

		int Hight3 = Hight2 + 40;
		// 词相关信息显示区域
		tabbedPane = new JTabbedPane(JTabbedPane.TOP,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setVisible(false);
		tabbedPane.setBounds(Width1, Hight3, 580, 567);
		this.add(tabbedPane);

		// 字体选择框初始化
		{
			FontFrame = new JFrame("Fonts And Colors");
			FontFrame.setBounds(300, 300, 367, 380);
			FontFrame.setIconImage(new ImageIcon(MainWindow.class
					.getResource("/resources/icons/font.png")).getImage());
			FontFrame.setResizable(false);
			FontFrame.setLayout(null);

			JTextArea FontTag = new JTextArea();
			JTextArea SizeTag = new JTextArea();
			JTextArea ForegroundTag = new JTextArea();
			JTextArea BackgroundTag = new JTextArea();
			JTextArea SampleTag = new JTextArea();

			final JComboBox ChooseFont = new JComboBox();
			final JComboBox ChooseSize = new JComboBox();
			final JComboBox ChooseForeground = new JComboBox();
			final JComboBox ChooseBackground = new JComboBox();
			final JTextArea FontSample = new JTextArea();
			JButton ConfirmButton = new JButton("Confirm");

			// "Font:"标签
			FontTag.setBounds(10, 10, 100, 25);
			FontTag.setFont(tagFont);
			FontTag.setText("Font:");
			FontTag.setEditable(false);
			FontTag.setOpaque(false);
			FontFrame.add(FontTag);
			// 字体选择框
			ChooseFont.setBounds(10, 30, 150, 25);
			ChooseFont.setEditable(false);
			ChooseFont.setBackground(Color.white);

			for (int i = 0; i < Fonts.length; i++)
				ChooseFont.addItem(Fonts[i]);
			ChooseFont.setSelectedIndex(1);
			ChooseFont.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					FontSample.setFont(new Font(Fonts[ChooseFont
							.getSelectedIndex()], 0, ChooseSize
							.getSelectedIndex() + 1));
				}
			});
			FontFrame.add(ChooseFont);

			// "Size:"标签
			SizeTag.setBounds(200, 10, 100, 25);
			SizeTag.setFont(tagFont);
			SizeTag.setText("Size:");
			SizeTag.setEditable(false);
			SizeTag.setOpaque(false);
			FontFrame.add(SizeTag);
			// 大小选择框
			ChooseSize.setBounds(200, 30, 150, 25);
			ChooseSize.setEditable(false);
			ChooseSize.setBackground(Color.WHITE);
			for (int i = 1; i <= 48; i++)
				ChooseSize.addItem("" + i);
			ChooseSize.setSelectedIndex(12);
			ChooseSize.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					FontSample.setFont(new Font(Fonts[ChooseFont
							.getSelectedIndex()], 0, ChooseSize
							.getSelectedIndex() + 1));
				}
			});
			FontFrame.add(ChooseSize);

			// "Foreground Color:"标签
			ForegroundTag.setBounds(10, 65, 150, 25);
			ForegroundTag.setFont(tagFont);
			ForegroundTag.setText("Foreground Color:");
			ForegroundTag.setEditable(false);
			ForegroundTag.setOpaque(false);
			FontFrame.add(ForegroundTag);
			// 前景色选择框
			ChooseForeground.setBounds(10, 85, 150, 25);
			ChooseForeground.setEditable(false);
			ChooseForeground.setBackground(Color.WHITE);
			for (int i = 0; i < Colors.length; i++)
				ChooseForeground.addItem(Colors[i]);
			ChooseForeground.setSelectedIndex(0);
			ChooseForeground.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					FontSample.setForeground(ColorOptions[ChooseForeground
							.getSelectedIndex()]);
				}
			});
			FontFrame.add(ChooseForeground);

			// "Background Color:"标签
			BackgroundTag.setBounds(200, 65, 150, 25);
			BackgroundTag.setFont(tagFont);
			BackgroundTag.setText("Background Color:");
			BackgroundTag.setEditable(false);
			BackgroundTag.setOpaque(false);
			FontFrame.add(BackgroundTag);
			// 背景色选择框
			ChooseBackground.setBounds(200, 85, 150, 25);
			ChooseBackground.setEditable(false);
			ChooseBackground.setBackground(Color.WHITE);
			for (int i = 0; i < Colors.length; i++)
				ChooseBackground.addItem(Colors[i]);
			ChooseBackground.setSelectedIndex(9);
			ChooseBackground.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					FontSample.setBackground(ColorOptions[ChooseBackground
							.getSelectedIndex()]);
				}
			});
			FontFrame.add(ChooseBackground);

			// "Sample:"标签
			SampleTag.setBounds(10, 120, 100, 25);
			SampleTag.setFont(tagFont);
			SampleTag.setText("Sample:");
			SampleTag.setEditable(false);
			SampleTag.setOpaque(false);
			FontFrame.add(SampleTag);
			// 示例区域
			FontSample.setBounds(10, 140, 340, 135);
			FontSample.setLineWrap(true);
			FontSample.setForeground(defaultcolor);
			FontSample.setBackground(defaultcolorb);
			FontSample.setFont(defaultfont);
			FontSample.setText("Welcome To Wordnet!");
			FontFrame.add(FontSample);

			// 确认按钮
			ConfirmButton.setBounds(140, 300, 87, 30);
			ConfirmButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					defaultfont = new Font(
							Fonts[ChooseFont.getSelectedIndex()], 0, ChooseSize
									.getSelectedIndex() + 1);
					defaultcolor = ColorOptions[ChooseForeground
							.getSelectedIndex()];
					defaultcolorb = ColorOptions[ChooseBackground
							.getSelectedIndex()];
					if (CurrentWord == null)
						return;
					SearchSucceeded(CurrentWord);
					FontFrame.setVisible(false);
				}
			});
			FontFrame.add(ConfirmButton);
		}
		inputTextField.requestFocus();
	}

	// 搜索按钮的监听器
	class SearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String s = new String(inputTextField.getText());
			searchStart(s, false);
		}
	}

	//
	class forwardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (ListIndex < History.size() - 1) {
				ListIndex++;
				backButton.setEnabled(true);
				if (ListIndex == History.size() - 1)
					forwardButton.setEnabled(false);
				String s = new String(History.get(ListIndex));
				inputTextField.setText(s);
				SearchSucceeded(s);
			}
			InputAreaSelected();
			return;
		}
	}

	// **********************************************************************
	// Luo Beier
	class changeFontListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			FontFrame.setVisible(true);
			FontFrame.requestFocus();
		}
	}

	/**
	 * 网络查词按钮监听器
	 * 
	 * @author Luo Beier
	 * 
	 */
	class onnetListener implements ActionListener {
		private String googleWeb = "http://translate.google.cn/translate_t?q=";
		private String baiduWeb = "http://dict.baidu.com/s?wd=";
		private String youdaoWeb = "http://dict.youdao.com/search?q=";
		private String haiciWeb = "http://dict.cn/";

		public void actionPerformed(ActionEvent e) {
			String website = googleWeb;
			if (baidu.isSelected())
				website = baiduWeb;
			else if (youdao.isSelected())
				website = youdaoWeb;
			else if (haici.isSelected())
				website = haiciWeb;
			runBroswer(website + CurrentWord);
		}

		private void runBroswer(String webSite) {
			try {
				Desktop desktop = Desktop.getDesktop();
				if (Desktop.isDesktopSupported()
						&& desktop.isSupported(Desktop.Action.BROWSE)) {
					URI uri = new URI(webSite);
					desktop.browse(uri);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (URISyntaxException ex) {
				ex.printStackTrace();
			}
		}
	}

	class addWordListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (!Find(CurrentWord))
					return;
			} catch (JWNLException exception) {
				exception.printStackTrace();
			}
			File myBookPath = new File("C://WordNet");
			if (!myBookPath.exists()) {
				boolean b = myBookPath.mkdir();
				if (!b) {
					System.out.println("can't create directory in c");
					JOptionPane.showMessageDialog(jFrame, "Can't Open File!");
				}
			}
			myBookPath = new File(myBookPath + "//notebook.txt");
			if (!myBookPath.exists()) {
				try {
					myBookPath.createNewFile();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			RandomAccessFile writer = null;
			FileReader reader = null;
			String tmp = "";
			String word = CurrentWord;
			try {
				reader = new FileReader(myBookPath);
				int hasRead = 0;
				char[] bbuf = new char[64];
				while ((hasRead = reader.read(bbuf)) > 0) {
					tmp += new String(bbuf, 0, hasRead);
				}
				if (tmp.contains(word)) {
					JOptionPane.showMessageDialog(jFrame,
							"The Word Is Already In Your Notebook");
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					}
					return;
				}
				writer = new RandomAccessFile(myBookPath, "rw");
				writer.seek(writer.length());
				writer.write((word + "%").getBytes());
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			}

		}
	}

	class openBookListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			File myBookPath = new File("C://WordNet");
			if (!myBookPath.exists()) {
				System.out.println("no such directory in c");
				JOptionPane.showMessageDialog(jFrame,
						"You haven't got a notebook yet");
				return;
			}
			myBookPath = new File(myBookPath + "//notebook.txt");
			if (!myBookPath.exists()) {
				System.out.println("no such file exist");
				JOptionPane.showMessageDialog(jFrame,
						"You haven't got a notebook yet");
				return;
			}
			FileReader reader = null;
			try {
				System.out.println("read notebook");
				reader = new FileReader(myBookPath);
				char[] buf = new char[32];
				int hasRead = 0;
				// wordArea.setText("");
				String wordList = "";
				while ((hasRead = reader.read(buf)) > 0) {
					String tmp = new String(buf, 0, hasRead);
					// wordArea.append(tmp);
					wordList += tmp;
				}
				String[] words = wordList.split("%", -1);
				Object[][] objectWord = new Object[words.length - 1][2];
				for (int i = 0; i < words.length - 1; i++) {
					objectWord[i][0] = i + 1 + "";
					objectWord[i][1] = (Object) words[i];
				}
				Object[] column = { "No.", "Word" };
				JTable wordTable = new JTable(objectWord, column);
				JScrollPane wordScroll = new JScrollPane(wordTable);
				wordTable.setFont(areaFont);
				// wordTable.setEnabled(false);
				wordTable.setRowSelectionAllowed(true);
				wordFrame.add(wordScroll);
				// wordArea.setCaretPosition(0);
				wordFrame.setState(JFrame.NORMAL);
				wordFrame.setVisible(true);
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			}
		}
	}

	/*
	 * class myData implements TableModel{ void
	 * addTableModelListener(TableModelListener l){
	 * 
	 * } Class }
	 */
	class clearHistoryListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int res = JOptionPane.showConfirmDialog(jFrame,
					"Are you sure to delete your history record?", "",
					JOptionPane.OK_CANCEL_OPTION);
			if (res == JOptionPane.YES_OPTION)
				ClearHistory();
		}
	}

	class help1Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (help1Frame.isVisible()) {
				help1Frame.setState(JFrame.NORMAL);
				help1Frame.requestFocus();
				return;
			}
			try {
				if (!help1Area.getText(0, 5).equals(null)) {
					help1Frame.setVisible(true);
					return;
				}
			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}
			System.out.println("read help1");
			FileReader reader = null;
			try {
				URL url = MainWindow.class
						.getResource("/resources/help/help1.txt");
				File file;
				try {
					file = new File(url.toURI());
					reader = new FileReader(file);
					char[] bbuf = new char[32];
					int hasRead = 0;
					help1Area.setText("");
					while ((hasRead = reader.read(bbuf)) > 0) {
						help1Area.append(new String(bbuf, 0, hasRead));
					}
					help1Area.setCaretPosition(0);
					help1Frame.setVisible(true);
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			}
		}
	}

	class help2Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (help2Frame.isVisible()) {
				help2Frame.setState(JFrame.NORMAL);
				help2Frame.requestFocus();
				return;
			}
			try {
				if (!help2Area.getText(0, 5).equals(null)) {
					help2Frame.setVisible(true);
					return;
				}
			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}
			FileReader reader = null;
			try {
				URL url = MainWindow.class
						.getResource("/resources/help/help2.txt");
				File file;
				try {
					file = new File(url.toURI());
					reader = new FileReader(file);
					char[] bbuf = new char[32];
					int hasRead = 0;
					help2Area.setText("");
					while ((hasRead = reader.read(bbuf)) > 0) {
						help2Area.append(new String(bbuf, 0, hasRead));
					}
					help2Area.setCaretPosition(0);
					help2Frame.setVisible(true);

				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			}
		}
	}

	class help3Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (help3Frame.isVisible()) {
				help3Frame.setState(JFrame.NORMAL);
				help3Frame.requestFocus();
				return;
			}
			try {
				if (!help3Area.getText(0, 5).equals(null)) {
					help3Frame.setVisible(true);
					return;
				}
			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}
			FileReader reader = null;
			try {
				URL url = MainWindow.class
						.getResource("/resources/help/help3.txt");
				File file;
				try {
					file = new File(url.toURI());
					reader = new FileReader(file);
					char[] bbuf = new char[32];
					int hasRead = 0;
					help3Area.setText("");
					while ((hasRead = reader.read(bbuf)) > 0) {
						help3Area.append(new String(bbuf, 0, hasRead));
					}
					help3Area.setCaretPosition(0);
					help3Frame.setVisible(true);
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			}
		}
	}

	class aboutUsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ImageIcon us = new ImageIcon(
					MainWindow.class.getResource("/resources/icons/usicon.png"));
			String discription = "About us:\r\nPowerSearch@pku.edu.cn";
			JOptionPane.showMessageDialog(jFrame, discription, "About Us",
					JOptionPane.INFORMATION_MESSAGE, us);
		}
	}

	// **********************************************************************

	private boolean Find(String myword) throws JWNLException {
		List<IndexWord> wordList = new ArrayList<IndexWord>();
		IndexWord word;
		word = Dictionary.getInstance().getIndexWord(POS.NOUN, myword);
		if (word != null)
			wordList.add(word);
		word = Dictionary.getInstance().getIndexWord(POS.ADJECTIVE, myword);
		if (word != null)
			wordList.add(word);
		word = Dictionary.getInstance().getIndexWord(POS.VERB, myword);
		if (word != null)
			wordList.add(word);
		word = Dictionary.getInstance().getIndexWord(POS.ADVERB, myword);
		if (word != null)
			wordList.add(word);
		if (wordList.size() == 0)
			return false;
		return true;
	}

	public void InputAreaSelected() {
		inputTextField.setSelectionStart(0);
		inputTextField.setSelectionEnd(inputTextField.getText().length());
		// inputTextField.requestFocus();
	}

	public void searchStart(String s, boolean bReady) {
		CurrentWord = s;
		InputAreaSelected();
		try {
			if (!bReady) {
				if (Find(s)) {
					pronounceButton.setEnabled(true);
					History.remove(s);
					History.add(s);
					ListIndex = History.size() - 1;
					if (ListIndex > 0)
						backButton.setEnabled(true);
					forwardButton.setEnabled(false);
					addWord.setEnabled(true);
					SearchSucceeded(s);
				}

				else {
					pronounceButton.setEnabled(false);
					ListIndex = History.size();
					title.setText("Not Found");
					addWord.setEnabled(false);
					if (inputTextField.getText() != null)
						inputTextField.setText(null);
				}
			} else {
				pronounceButton.setEnabled(true);
				History.add(s);
				ListIndex = History.size() - 1;
				if (ListIndex > 0)
					backButton.setEnabled(true);
				forwardButton.setEnabled(false);
				SearchSucceeded(s);
			}
		} catch (JWNLException e1) {
			e1.printStackTrace();
		}
	}

	public void SearchSucceeded(String s) {
		CurrentWord = s;
		word = new MyWord(s);

		// playPath = "E:\\WordPronounce\\"+s.charAt(0)+"\\"+s+".mp3";
		// if(autoPronounce.isSelected())
		// readWord();
		tabbedPane.removeAll();
		inputTextField.setText(s);
		InputAreaSelected();
		title.setText(s);

		inputTextField.requestFocus();

		PartOfSpeach[] pos = new PartOfSpeach[4];

		for (int i = 0; i < 4; i++) {
			pos[i] = word.getPOS(i);
			if (pos[i] != null)
				tabbedPane.addTab(partofspeach[i], doticon[0],
						ShowOnePos(pos[i]), suffix[i]);
		}
		tabbedPane.setVisible(true);

		JPanel spn = new JPanel();
		spn.setOpaque(false);
		spn.setLayout(null);
		// "Antonyms:"标签
		JTextArea tag1 = new JTextArea();
		tag1.setBounds(33, 33, 100, 20);
		tag1.setFont(tagFont);
		tag1.setText("Antonyms:");
		tag1.setEditable(false);
		tag1.setOpaque(false);
		spn.add(tag1);
		// 反义词显示区域
		JTextArea ant = new JTextArea();
		ant.setLineWrap(true);
		ant.setFont(defaultfont);
		List<String> stringlist;
		stringlist = word.getAntonym();
		if (stringlist.size() == 0)
			ant.setText("No Antonym");
		// 每个词性对应一个JTabbedPane选项
		for (int i = 0; i < stringlist.size(); i++) {
			ant.append("" + (i + 1) + '.' + stringlist.get(i) + "    ");
			if (i % 4 == 3)
				ant.append("\n");
		}
		ant.setEditable(false);
		ant.setBackground(defaultcolorb);
		ant.setForeground(defaultcolor);
		ant.setCaretPosition(0);
		ant.setDragEnabled(true);
		JScrollPane jp = new JScrollPane(ant);
		jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jp.setBorder(null);
		jp.setOpaque(false);
		jp.setBounds(44, 53, 400, 420);
		spn.add(jp);
		tabbedPane.addTab("Antonyms", doticon[1], spn, "Antonyms");
		if (treepane == null) {
			treepane = new TreePane(word);
			new Thread(treepane, "new").start();
		} else {
			treepane.setCenterWord(word);
			new Thread(treepane, "new").start();
		}
		this.add(treepane);
		inputTextField.setText(null);
		this.setBounds(30, 20, 1200, 710);
	}

	private JPanel ShowOnePos(PartOfSpeach pos) {
		JPanel pn = new JPanel();
		pn.setLayout(null);
		pn.setOpaque(false);

		JTabbedPane tp = new JTabbedPane(JTabbedPane.RIGHT,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		List<Sense> senselist = pos.getSense();
		// 每个词义对应一个JTabbedPane选项
		for (int i = 0; i < senselist.size(); i++) {
			if (i < 9)
				tp.addTab("Sense   " + (i + 1), doticon[2],
						ShowOneSense(senselist.get(i)), null);
			else
				tp.addTab("Sense " + (i + 1), doticon[2],
						ShowOneSense(senselist.get(i)), null);
		}
		tp.setBounds(10, 10, 555, 515);
		pn.add(tp);

		return pn;
	}

	/**
	 * sense的左标签页
	 * 
	 * @param sense
	 * @return
	 */
	private JPanel ShowOneSense(Sense sense) {
		JPanel pn;
		JTabbedPane tp;
		JPanel spn1;
		JTextArea tag1;
		JTextArea exp;
		JScrollPane jp1;
		JTextArea tag2;
		JTextArea sen;
		JScrollPane jp2;
		JTextArea tag3;
		JTextArea syn;
		JScrollPane jp3;
		final JPanel spn2;

		tp = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);

		List<String> stringlist;
		pn = new JPanel();
		pn.setLayout(null);
		pn.setOpaque(false);

		spn1 = new JPanel();
		spn1.setOpaque(false);
		spn1.setLayout(null);

		// "Explanation:"标签
		tag1 = new JTextArea();
		tag1.setBounds(10, 5, 100, 20);
		tag1.setEditable(false);
		tag1.setOpaque(false);
		tag1.setFont(tagFont);
		tag1.setText("Explanation:");
		spn1.add(tag1);
		// 解释内容显示区域
		exp = new JTextArea();
		exp.setEditable(false);
		exp.setLineWrap(true);
		exp.setFont(defaultfont);
		exp.setText(sense.getExpression());
		exp.setCaretPosition(0);
		exp.setBackground(defaultcolorb);
		exp.setForeground(defaultcolor);
		exp.setDragEnabled(true);
		jp1 = new JScrollPane(exp);
		jp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jp1.setBorder(null);
		jp1.setBounds(20, 25, 400, 120);
		jp1.setOpaque(false);
		spn1.add(jp1);

		// "Sample Sentenses:"标签
		tag2 = new JTextArea();
		tag2.setBounds(10, 155, 150, 20);
		tag2.setEditable(false);
		tag2.setOpaque(false);
		tag2.setFont(tagFont);
		tag2.setText("Sample Sentences:");
		spn1.add(tag2);
		// 例句内容显示区域
		sen = new JTextArea();
		sen.setEditable(false);
		sen.setLineWrap(true);
		sen.setFont(defaultfont);
		stringlist = sense.getSamples();
		if (stringlist.size() == 0)
			sen.setText("No sample sentence.");
		for (int i = 0; i < stringlist.size(); i++) {
			sen.append("" + (i + 1) + '.' + stringlist.get(i) + "\n");
		}
		sen.setCaretPosition(0);
		sen.setBackground(defaultcolorb);
		sen.setForeground(defaultcolor);
		sen.setDragEnabled(true);
		jp2 = new JScrollPane(sen);
		jp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jp2.setBounds(20, 175, 400, 140);
		jp2.setBorder(null);
		jp2.setOpaque(false);
		spn1.add(jp2);

		// "Synonyms:"标签
		tag3 = new JTextArea();
		tag3.setBounds(10, 325, 100, 20);
		tag3.setEditable(false);
		tag3.setOpaque(false);
		tag3.setFont(tagFont);
		tag3.setText("Synonyms:");
		spn1.add(tag3);
		// 同义词显示区域
		syn = new JTextArea();
		syn.setEditable(false);
		syn.setLineWrap(true);
		syn.setFont(defaultfont);
		stringlist = sense.getSynGroup();
		if (stringlist.size() == 0)
			syn.setText("No Synonym");
		for (int i = 0; i < stringlist.size(); i++) {
			syn.append("" + (i + 1) + '.' + stringlist.get(i) + "    ");
			if (i % 3 == 2)
				syn.append("\n");
		}
		syn.setCaretPosition(0);
		syn.setBackground(defaultcolorb);
		syn.setForeground(defaultcolor);
		syn.setDragEnabled(true);
		jp3 = new JScrollPane(syn);
		jp3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jp3.setBounds(20, 345, 400, 100);
		jp3.setBorder(null);
		jp3.setOpaque(false);
		spn1.add(jp3);

		tp.addTab("Explaination/Sample Sentences/Synonyms", null, spn1, null);

		/**
		 * @Attention 这里开始查找关系,导致程序堵塞,考虑多线程
		 */
		spn2 = new JPanel();
		spn2.setOpaque(false);
		spn2.setLayout(null);

		final Vector<RelatedSense> relations = new Vector<RelatedSense>();
		for (int i = 0; i < 28; i++) {
			if (sense.getRelated(i) != null)
				relations.add(sense.getRelated(i));
		}
		JTextArea ctag1;
		JTextArea ctag2;
		final JComboBox cb;
		// "Relations:"标签
		ctag1 = new JTextArea();
		ctag1.setBounds(10, 5, 100, 20);
		ctag1.setEditable(false);
		ctag1.setOpaque(false);
		ctag1.setFont(tagFont);
		ctag1.setText("Relations:");
		spn2.add(ctag1);
		// "Details:"标签
		ctag2 = new JTextArea();
		ctag2.setBounds(10, 55, 100, 20);
		ctag2.setEditable(false);
		ctag2.setOpaque(false);
		ctag2.setFont(tagFont);
		ctag2.setText("Details:");
		spn2.add(ctag2);

		// 关系选择框
		cb = new JComboBox();
		cb.setBounds(20, 25, 200, 20);
		cb.setBackground(Color.WHITE);
		cb.setEditable(false);
		for (int i = 0; i < relations.size(); i++)
			cb.addItem(relations.get(i).getType());
		// 每一个关系的内容
		final JTabbedPane sjtp = new JTabbedPane(JTabbedPane.LEFT,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		sjtp.setBounds(20, 85, 400, 360);
		sjtp.setVisible(false);

		spn2.add(sjtp);

		class comboboxListener implements ItemListener {
			public void itemStateChanged(ItemEvent e) {
				sjtp.removeAll();
				List<Sense> sl = relations.get(cb.getSelectedIndex())
						.getRelation();
				for (int i = 0; i < sl.size(); i++) {
					Sense sense = sl.get(i);

					JPanel spn1 = new JPanel();
					spn1.setOpaque(false);
					spn1.setLayout(null);

					JTextArea tag3 = new JTextArea();
					tag3.setEditable(false);
					tag3.setOpaque(false);
					tag3.setFont(tagFont);
					tag3.setBounds(10, 5, 100, 20);
					tag3.setText("Synonyms:");
					spn1.add(tag3);

					JTextArea syn = new JTextArea();
					syn.setEditable(false);
					syn.setLineWrap(true);
					// syn.setOpaque(false);
					syn.setBackground(defaultcolorb);
					syn.setForeground(defaultcolor);
					syn.setFont(defaultfont);
					List<String> stringlist;
					stringlist = sense.getSynGroup();
					if (stringlist.size() == 0)
						syn.setText("No Synonym");
					for (int j = 0; j < stringlist.size(); j++) {
						syn.append("" + (j + 1) + '.' + stringlist.get(j)
								+ "    ");
						if (j % 2 == 1)
							syn.append("\n");
					}
					syn.setCaretPosition(0);
					syn.setDragEnabled(true);
					JScrollPane jp3 = new JScrollPane(syn);
					jp3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					jp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					jp3.setBorder(null);
					jp3.setBounds(20, 25, 285, 180);
					jp3.setOpaque(false);
					spn1.add(jp3);

					JTextArea tag1 = new JTextArea();
					tag1.setEditable(false);
					tag1.setOpaque(false);
					tag1.setBounds(10, 215, 100, 20);
					tag1.setFont(tagFont);
					tag1.setText("Explanation:");
					spn1.add(tag1);

					JTextArea exp = new JTextArea();
					exp.setEditable(false);
					exp.setLineWrap(true);
					// exp.setOpaque(false);
					exp.setBackground(defaultcolorb);
					exp.setForeground(defaultcolor);
					exp.setFont(defaultfont);
					exp.setText(sense.getExpression());
					exp.setCaretPosition(0);
					exp.setDragEnabled(true);
					JScrollPane jp1 = new JScrollPane(exp);
					jp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					jp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					jp1.setBorder(null);
					jp1.setBounds(20, 235, 285, 100);
					jp1.setOpaque(false);
					spn1.add(jp1);

					if (i < 9)
						sjtp.addTab("Sense   " + (i + 1), doticon[3], spn1,
								null);
					else
						sjtp.addTab("Sense " + (i + 1), doticon[3], spn1, null);

					sjtp.setVisible(true);
				}
			}
		}
		cb.addItemListener(new comboboxListener());
		if (relations.size() > 0) {
			cb.setSelectedIndex(0);
			sjtp.removeAll();
			List<Sense> sl = relations.get(cb.getSelectedIndex()).getRelation();
			for (int i = 0; i < sl.size(); i++) {
				Sense fsense = sl.get(i);

				JPanel fspn1 = new JPanel();
				fspn1.setOpaque(false);
				fspn1.setLayout(null);

				JTextArea ftag3 = new JTextArea();
				ftag3.setEditable(false);
				ftag3.setOpaque(false);
				ftag3.setFont(tagFont);
				ftag3.setBounds(10, 5, 100, 20);
				ftag3.setText("Synonyms:");
				fspn1.add(ftag3);

				JTextArea fsyn = new JTextArea();
				fsyn.setEditable(false);
				fsyn.setLineWrap(true);
				fsyn.setBackground(defaultcolorb);
				fsyn.setForeground(defaultcolor);
				fsyn.setFont(defaultfont);
				List<String> fstringlist;
				fstringlist = fsense.getSynGroup();
				if (fstringlist.size() == 0)
					fsyn.setText("No Synonym");
				for (int j = 0; j < fstringlist.size(); j++) {
					fsyn.append("" + (j + 1) + '.' + fstringlist.get(j)
							+ "    ");
					if (j % 2 == 1)
						fsyn.append("\n");
				}
				fsyn.setCaretPosition(0);
				fsyn.setDragEnabled(true);
				JScrollPane fjp3 = new JScrollPane(fsyn);
				fjp3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				fjp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				fjp3.setBorder(null);
				fjp3.setBounds(20, 25, 285, 180);
				fjp3.setOpaque(false);
				fspn1.add(fjp3);

				JTextArea ftag1 = new JTextArea();
				ftag1.setEditable(false);
				ftag1.setOpaque(false);
				ftag1.setBounds(10, 215, 100, 20);
				ftag1.setFont(tagFont);
				ftag1.setText("Explanation:");
				fspn1.add(ftag1);

				JTextArea fexp = new JTextArea();
				fexp.setEditable(false);
				fexp.setLineWrap(true);
				fexp.setBackground(defaultcolorb);
				fexp.setForeground(defaultcolor);
				fexp.setFont(defaultfont);
				fexp.setText(fsense.getExpression());
				fexp.setCaretPosition(0);
				fexp.setDragEnabled(true);
				JScrollPane fjp1 = new JScrollPane(fexp);
				fjp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				fjp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				fjp1.setBorder(null);
				fjp1.setBounds(20, 235, 285, 100);
				fjp1.setOpaque(false);
				fspn1.add(fjp1);

				if (i < 9)
					sjtp.addTab("Sense   " + (i + 1), doticon[3], fspn1, null);
				else
					sjtp.addTab("Sense " + (i + 1), doticon[3], fspn1, null);

				sjtp.setVisible(true);
			}
		}
		spn2.add(cb);

		tp.addTab("Relations", null, spn2, null);
		tp.setBounds(10, 10, 440, 490);
		pn.add(tp);

		return pn;
	}

	/*
	 * public void readWord(){ try{ Player player = Manager.createPlayer(new
	 * File(playPath).toURI().toURL()); player.prefetch(); player.start(); }
	 * catch (Exception ioe){ ioe.printStackTrace(); } }
	 */
	public void ClearHistory() {
		History.clear();
		ListIndex = 0;
		CurrentWord = null;
		word = null;
		inputTextField.setText("");
		title.setText("");
		tabbedPane.removeAll();
		backButton.setEnabled(false);
		forwardButton.setEnabled(false);
	}
}