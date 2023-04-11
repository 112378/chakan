package picture;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class MyFrame extends JFrame {

	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	private JComboBox jComboBox1;
	private JComboBox jComboBox2;
	private JMenu jMenu1;
	private JMenu jMenu2;
	private JMenu jMenu3;
	private JMenuBar jMenuBar1;
	private JMenuItem jMenuItem1;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem3;
	private JMenuItem jMenuItem4;
	private JScrollPane jScrollPane1;
	private JTabbedPane jTabbedPane1;
	private JToolBar jToolBar1;
	private JTree jTree1;
	
	ArrayList<File> ImageFiles = new ArrayList<File>();
	ArrayList<JPanel> SmallPanels = new ArrayList<JPanel>(); 
	ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>(); 
	ArrayList<JTextField> SmallTextFields = new ArrayList<JTextField>(); 
	JScrollPane BigScrollPane;
	ArrayList<File> ClickedFilePath = new ArrayList<File>();
	int ImagesQuantity; 
	int SelectImage = -1; 
	JFrame IntroduceFrame = new JFrame(); 
	JTextArea IntroduceTextArea = new JTextArea(); 
	JPopupMenu PopupMenu = new JPopupMenu();
	JMenuItem Copy = new JMenuItem(" 42 "); 
	JMenuItem Delete = new JMenuItem(" 725 "); 
	JMenuItem Cut = new JMenuItem(" 45 "); 
	JMenuItem Rename = new JMenuItem(" 25"); 
	JPanel ImagePanel = new JPanel();
	String FilePath;
	MouseEvent E;
	File TemporaryFile;
	ImageIcon TemporaryIcon;
	String OldName;
	JPopupMenu OutPopupMenu = new JPopupMenu();
	JMenuItem Refresh = new JMenuItem("==");
	JMenuItem Paste = new JMenuItem("//");
	JMenuItem BatchRename = new JMenuItem("--");
	ArrayList<BufferedInputStream> SourceFile = new ArrayList<BufferedInputStream>();
	ArrayList<FileOutputStream> NewFile = new ArrayList<FileOutputStream>();
	ArrayList<TreePath> TreePaths = new ArrayList<TreePath>();
	String SourceFileName = null;
	int CopyNum = 0;
	int PasteNumber = 0;
	String CopyName;
	String CopyPath;
	int CutFlag = 0;
	public MyFrame() {
		initComponents();
	}

	public void InitIntroduction() {
		IntroduceFrame.setVisible(false);
		IntroduceFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		IntroduceFrame.setSize(220, 130);
		IntroduceFrame.setLocationRelativeTo(null); 
		IntroduceFrame.add(IntroduceTextArea);
		IntroduceTextArea.setEditable(false); 
	}


	public void Init() {

		InitIntroduction();
		BigScrollPane = new JScrollPane(ImagePanel); 
		ImagePanel.setLayout(null); 
		jTabbedPane1.add(BigScrollPane); 
		PopupMenu.add(Copy); 
		// jPopupMenu.addSeparator(); 
		PopupMenu.add(Cut); 
		PopupMenu.add(Delete); 
		PopupMenu.add(Rename); 
		OutPopupMenu.add(Refresh);
		OutPopupMenu.add(Paste);
		OutPopupMenu.add(BatchRename);
		jComboBox1.addPopupMenuListener(new PopupMenuListener() {

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex()));
				ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex()), 1);
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
				System.out.println("3");
			}
		});

		ImagePanel.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseReleased(MouseEvent evt) {
				OutPopupMenu(evt);
			}
		});

		BatchRename.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				BatchRename();
			}
		});

		Delete.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Delete();
			}
		});

		Rename.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Rename();
			}
		});

		Copy.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Copy();
				} catch (IOException ex) {
					Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});

		Cut.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					Cut();
				} catch (IOException ex) {
					Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});

		Refresh.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ShowImages(E, new TreePath(0), 0);
			}
		});

		Paste.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Paste();
				} catch (FileNotFoundException ex) {
					Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});

	}

	public void Back() {
		jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex() - 1));
		ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex() - 1), 1);
		jScrollPane1.getVerticalScrollBar().setValue((int) (jTree1.getRowHeight() * jTree1.getMaxSelectionRow()));
	}

	public void Next() {
		jTree1.setSelectionPath(TreePaths.get(jComboBox1.getSelectedIndex() + 1));
		ShowImages(E, TreePaths.get(jComboBox1.getSelectedIndex() + 1), 1);
		jScrollPane1.getVerticalScrollBar().setValue((int) (jTree1.getRowHeight() * jTree1.getMaxSelectionRow()));
	}

	public void Up() {
		if (jTree1.getSelectionPath().getParentPath() != null) {
			jTree1.setSelectionPath(jTree1.getSelectionPath().getParentPath());
			System.out.println(jTree1.getMaxSelectionRow());
			for (int i = jTree1.getRowCount(); i >= jTree1.getMaxSelectionRow(); i--) {
				jTree1.collapseRow(i);
			}
		}
	}

	public void BatchRename() {
		// ImagePanel.removeAll();
		int num = 0;
		File file = new File(FilePath);
		File[] files = file.listFiles();
		ArrayList<File> filesA = new ArrayList<File>();
		ArrayList<ImageIcon> ImageIcons = new ArrayList<ImageIcon>();
		for (int i = 0; i < files.length; i++) {
			String ext = files[i].getName().substring(files[i].getName().lastIndexOf("."), files[i].getName().length())
					.toLowerCase();
			if (ext.equals(".jpg") || ext.equals(".gif") || ext.equals(".bmp")) {
				filesA.add(files[i]);
			}
		}
		for (int i = 0; i < filesA.size(); i++) {
			if (!filesA.get(i).renameTo(filesA.get(i))) {
				// ImageIcons.add(GetImageIcon(new ImageIcon(filesA.get(i).getAbsolutePath())));
				SmallLabels.get(i).setIcon(null);
			}
		}

		String string = JOptionPane.showInputDialog(null, ")", "(", JOptionPane.INFORMATION_MESSAGE);

		for (int i = 0; i < filesA.size(); i++) {
			String axt = filesA.get(i).getName()
					.substring(filesA.get(i).getName().lastIndexOf("."), filesA.get(i).getName().length())
					.toLowerCase();
			System.out.println(filesA.get(i));
			int j = i + 1;
			if (SmallLabels.get(i).getIcon() == null) {
				filesA.get(i).renameTo(new File(FilePath + File.separator + string + "(" + j + ")" + axt));
				ImageIcons.add(GetImageIcon(new ImageIcon(FilePath + File.separator + string + "(" + j + ")" + axt)));
				SmallTextFields.get(i).setText(string + "(" + j + ")" + axt);
				ClickedFilePath.add(new File(FilePath + File.separator + string + "(" + j + ")" + axt));
			} else {
				filesA.get(i).renameTo(new File(FilePath + File.separator + string + "(" + j + ")" + axt));
				SmallTextFields.get(i).setText(string + "(" + j + ")" + axt);
				ClickedFilePath.add(new File(FilePath + File.separator + string + "(" + j + ")" + axt));
			}
		}
		for (int i = 0; i < filesA.size(); i++) {
			if (SmallLabels.get(i).getIcon() == null) {
				SmallLabels.get(i).setIcon(ImageIcons.get(num));
				num++;
			}
		}

	}

	public void Paste() throws FileNotFoundException {
		int flag = 0;
		int PasteNum = 0;
		int FlagName = 0;
		if (CutFlag == 1) {
			for (int i = 0; i < ImageFiles.size(); i++) {
				if (SmallTextFields.get(i).getText().equals(SourceFileName)) {
					FlagName = 1;
				}

			}
			if (FlagName == 1) {
				JOptionPane.showMessageDialog(null, "..!", "ERROR",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				if (CopyPath == FilePath) {
					JOptionPane.showMessageDialog(null, "??", "ERROR",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					NewFile.add(new FileOutputStream(new File(FilePath + File.separator + SourceFileName)));
					byte[] content = new byte[1000];
					int size = 0;

					try {
						while ((size = SourceFile.get(CopyNum - 1).read(content)) != -1) {
							NewFile.get(PasteNumber).write(content, 0, size);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						NewFile.get(PasteNumber).close();
						SourceFile.get(CopyNum - 1).close();
					} catch (IOException ex) {
					}
					PasteNumber++;
					ImageIcon ic1 = new ImageIcon(FilePath + File.separator + SourceFileName);
					double h1 = ic1.getIconHeight();
					double w1 = ic1.getIconWidth();
					if (h1 < 77 && w1 < 100) {
						Image im = ic1.getImage().getScaledInstance((int) w1, (int) h1, Image.SCALE_DEFAULT);
						ImageIcon ic2 = new ImageIcon(im);
						SmallLabels.add(new JLabel());
						SmallTextFields.add(new JTextField());
						SmallLabels.get(SmallLabels.size() - 1).setIcon(ic2);
						SmallTextFields.get(SmallLabels.size() - 1).setText(SourceFileName);

					} else {
						if (h1 * 180 > w1 * 142) {
							Image im = ic1.getImage().getScaledInstance((int) (w1 / (h1 / 81)), 81,
									Image.SCALE_DEFAULT);
							ImageIcon ic2 = new ImageIcon(im);
							SmallLabels.add(new JLabel());
							SmallTextFields.add(new JTextField());
							SmallLabels.get(SmallLabels.size() - 1).setIcon(ic2);
							SmallTextFields.get(SmallLabels.size() - 1).setText(SourceFileName);
						} else {
							Image im = ic1.getImage().getScaledInstance(105, (int) (h1 / (w1 / 105)),
									Image.SCALE_DEFAULT);
							ImageIcon ic2 = new ImageIcon(im);
							SmallLabels.add(new JLabel());
							SmallTextFields.add(new JTextField());
							SmallLabels.get(SmallLabels.size() - 1).setIcon(ic2);
							SmallTextFields.get(SmallLabels.size() - 1).setText(SourceFileName);
						}
						SmallPanels.add(new JPanel());
						int j = SmallLabels.size() - 1;
						ImagePanel.add(SmallPanels.get(j));
						ImageFiles.add(new File(FilePath + File.separator + SourceFileName));

						if (ImageFiles.size() > 20) {
							SmallPanels.get(j).setBounds(j % 5 * 131, 1 + (j / 5 * 125), 120, 110);
						} else {
							SmallPanels.get(j).setBounds(j % 5 * 135, 1 + (j / 5 * 125), 120, 110);
						}
						SmallPanels.get(j).setLayout(new java.awt.BorderLayout(0, 0));
						SmallPanels.get(j).add(SmallLabels.get(j), java.awt.BorderLayout.CENTER);
						SmallPanels.get(j).add(SmallTextFields.get(j), java.awt.BorderLayout.PAGE_END);
						SmallTextFields.get(j).setBorder(null);
						SmallTextFields.get(j).setHorizontalAlignment(SwingConstants.CENTER);
						SmallTextFields.get(j).setEditable(false);
						if (j == 0) {
							SmallLabels.get(0).setDisplayedMnemonic(501);
						} else {
							SmallLabels.get(j).setDisplayedMnemonic(j);
						}
						SmallLabels.get(j).setHorizontalAlignment(SwingConstants.CENTER);
						SmallLabels.get(j).setOpaque(true);
						SmallLabels.get(j).setBackground(new java.awt.Color(244, 244, 244));
						if (new File(CopyPath + File.separator + SourceFileName).delete()) {
							JOptionPane.showMessageDialog(null, "?!!!", "ERROR",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "!!!", "ERROR",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		} else {
			String SourceFileNameFront = SourceFileName.substring(0, SourceFileName.indexOf(".")).toLowerCase();

			if (SourceFileName.indexOf("(") != -1) {
				SourceFileNameFront = SourceFileName.substring(0, SourceFileName.indexOf("(")).toLowerCase();
			}
			System.out.println((String) (SourceFileNameFront) + "aabcdefg");

			String SourceFileNameLast = SourceFileName
					.substring(SourceFileName.lastIndexOf("."), SourceFileName.length()).toLowerCase();
			for (int size = 0; size < SmallTextFields.size(); size++) {
				if (SourceFileNameFront.equals(SmallTextFields.get(size).getText()
						.substring(0, SmallTextFields.get(size).getText().indexOf(".")).toLowerCase())) {
					PasteNum = 2;

				}

			}
			for (int size = 0; size < SmallTextFields.size(); size++) {

				if (new String(SourceFileNameFront + "(" + PasteNum + ")").equals(SmallTextFields.get(size).getText()
						.substring(0, SmallTextFields.get(size).getText().indexOf(".")).toLowerCase())) {
					PasteNum++;
					size = 0;
				}
			}

			if (PasteNum != 0) {
				NewFile.add(new FileOutputStream(new File(
						FilePath + File.separator + SourceFileNameFront + "(" + PasteNum + ")" + SourceFileNameLast)));
			} else {
				NewFile.add(new FileOutputStream(
						new File(FilePath + File.separator + SourceFileNameFront + SourceFileNameLast)));
			}

			try {
				CopyTwo();
			} catch (IOException e) {
			}
			byte[] content = new byte[1000];
			int size = 0;

			try {
				while ((size = SourceFile.get(CopyNum - 1).read(content)) != -1) {
					NewFile.get(PasteNumber).write(content, 0, size);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			ShowImages(E, new TreePath(0), 0);
			try {
				NewFile.get(PasteNumber).close();
			} catch (IOException ex) {
			}
			PasteNumber++;

		}

	}

	public void CopyTwo() throws IOException {
		if (CopyNum != 0) {
			try {
				SourceFile.get(CopyNum - 1).close();
			} catch (IOException ex) {
			}
		}

		try {
			SourceFile.add(new BufferedInputStream(new FileInputStream(CopyPath + File.separator + CopyName)));

			SourceFileName = CopyName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CopyNum++;

	}

	public void Copy() throws IOException {
		CutFlag = 0;
		if (CopyNum != 0) {
			try {
				SourceFile.get(CopyNum - 1).close();
			} catch (IOException ex) {
			}
		}
		CopyName = SmallTextFields.get(SelectImage).getText();
		CopyPath = FilePath;
		try {
			SourceFile.add(new BufferedInputStream(
					new FileInputStream(FilePath + File.separator + SmallTextFields.get(SelectImage).getText())));

			SourceFileName = SmallTextFields.get(SelectImage).getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CopyNum++;

	}

	public void Cut() throws IOException {
		if (CopyNum != 0) {
			try {
				SourceFile.get(CopyNum - 1).close();
			} catch (IOException ex) {
			}
		}
		CopyPath = FilePath;
		try {
			SourceFile.add(new BufferedInputStream(
					new FileInputStream(FilePath + File.separator + SmallTextFields.get(SelectImage).getText())));
			SourceFileName = SmallTextFields.get(SelectImage).getText();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CopyNum++;
		CutFlag = 1;
	}

	public void OutPopupMenu(MouseEvent evt) {
		if (evt.isPopupTrigger()) { 
			OutPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY()); 
		}
	}

	public void Delete() {
		SmallLabels.get(SelectImage).setIcon(null);
		if (JOptionPane.showConfirmDialog(null,
				"shanchu" + SmallTextFields.get(SelectImage).getText() + "?") == JOptionPane.YES_OPTION) {
			if (new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText()).delete()) {
				if (ImagesQuantity - 1 > 20) {
					SmallPanels.get(SelectImage).setBounds(3000, 1, 0, 0);
					SmallPanels.remove(SelectImage);
					SmallTextFields.remove(SelectImage);
					SmallLabels.remove(SelectImage);
					SmallPanels.trimToSize();
					SmallLabels.trimToSize();
					SmallTextFields.trimToSize();
					for (int i = SelectImage; i < SmallPanels.size(); i++) {
						System.out.println("size" + SmallPanels.size());
						SmallPanels.get(i).setBounds((i) % 5 * 135, 1 + ((i) / 5 * 125), 120, 110);
					}
				} else {
					SmallPanels.get(SelectImage).setBounds(3000, 1, 0, 0);
					SmallPanels.remove(SelectImage);
					SmallTextFields.remove(SelectImage);
					SmallLabels.remove(SelectImage);
					SmallPanels.trimToSize();
					SmallLabels.trimToSize();
					SmallTextFields.trimToSize();
					for (int i = SelectImage; i < SmallPanels.size(); i++) {
						System.out.println("size" + SmallPanels.size());
						SmallPanels.get(i).setBounds((i) % 5 * 135, 1 + ((i) / 5 * 125), 120, 110);
					}
				}
				ImagesQuantity--;
			} else {
				JOptionPane.showMessageDialog(null, "!!!", "ERROR",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void Rename() {
		Robot mRobot = null;
		try {
			mRobot = new Robot();
		} catch (java.awt.AWTException awe) {
			System.err.println("new   Robot   error");
		}
		TemporaryFile = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());
		Point point = new Point();
		point = SmallTextFields.get(SelectImage).getLocationOnScreen();
		mRobot.mouseMove(point.x + 50, point.y + 5);
		SmallTextFields.get(SelectImage).setEditable(true);
		OldName = (String) SmallTextFields.get(SelectImage).getText();
		SmallTextFields.get(SelectImage).setBackground(Color.white);
		TemporaryIcon = (ImageIcon) SmallLabels.get(SelectImage).getIcon();
		if (!TemporaryFile.renameTo(TemporaryFile)) {
			SmallLabels.get(SelectImage).setIcon(null);
		}
		SmallTextFields.get(SelectImage).addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					RenameText();
				} catch (IOException ex) {
					Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		});
	}

	public void RenameText() throws IOException {
		File filetwo = new File(FilePath + File.separator + SmallTextFields.get(SelectImage).getText());

		if (TemporaryFile.renameTo(filetwo)) {
			JOptionPane.showMessageDialog(null, "[[", "]]", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, "??", "???", JOptionPane.INFORMATION_MESSAGE);

		}
		SmallTextFields.get(SelectImage).setBackground(null);
		SmallTextFields.get(SelectImage).setEditable(false);
		SmallLabels.get(SelectImage).setIcon(GetImageIcon(new ImageIcon(filetwo.getAbsolutePath())));
		SmallLabels.get(SelectImage).setBackground(new java.awt.Color(244, 244, 244));
	}

	public ImageIcon GetImageIcon(ImageIcon imageicon) {
		double h1 = imageicon.getIconHeight();
		double w1 = imageicon.getIconWidth();
		if (h1 < 77 && w1 < 100) {
			Image image = imageicon.getImage().getScaledInstance((int) w1, (int) h1, Image.SCALE_DEFAULT);
			ImageIcon Finalii = new ImageIcon(image);
			return Finalii;

		} else {
			if (h1 * 180 > w1 * 142) {
				Image image = imageicon.getImage().getScaledInstance((int) (w1 / (h1 / 81)), 81, Image.SCALE_DEFAULT);
				ImageIcon Finalii = new ImageIcon(image);
				return Finalii;
			} else {
				Image image = imageicon.getImage().getScaledInstance(105, (int) (h1 / (w1 / 105)), Image.SCALE_DEFAULT);
				ImageIcon Finalii = new ImageIcon(image);
				return Finalii;
			}
		}
	}

	public void PopupMenu(MouseEvent evt) {
		if (evt.isPopupTrigger()) { 
			JLabel SelectLabel = new JLabel(); 
			SelectLabel = (JLabel) evt.getSource(); 
			PopupMenu.show(evt.getComponent(), evt.getX(), evt.getY()); 

			for (int b = 0; b < SmallLabels.size(); b++) {
				SmallLabels.get(b).setBackground(new java.awt.Color(244, 244, 244));
			}
			JLabel CurrentLabel = new JLabel();
			CurrentLabel = (JLabel) evt.getSource();
			CurrentLabel.setBackground(new java.awt.Color(194, 194, 194));
			for (int y = 0; y < SmallLabels.size(); y++) {
				if (SmallLabels.get(y).getDisplayedMnemonic() == CurrentLabel.getDisplayedMnemonic()) {
					SelectImage = y;
				}
			}
		}
	}

	public void InitLabelListener() {
		for (int i = 0; i < SmallLabels.size(); i++) {
			SmallLabels.get(i).setBorder(null);
			SmallLabels.get(i).addMouseListener(new java.awt.event.MouseAdapter() {

				public void mouseReleased(MouseEvent evt) {
					PopupMenu(evt);
				}

				public void mousePressed(java.awt.event.MouseEvent evt) {
					for (int b = 0; b < SmallLabels.size(); b++) {
						SmallLabels.get(b).setBackground(new java.awt.Color(244, 244, 244));
					}
					JLabel CurrentLabel = new JLabel();
					CurrentLabel = (JLabel) evt.getSource();
					CurrentLabel.setBackground(new java.awt.Color(194, 194, 194));
					int clickTimes = evt.getClickCount();

					for (int y = 0; y < SmallLabels.size(); y++) {
						if (SmallLabels.get(y).getDisplayedMnemonic() == CurrentLabel.getDisplayedMnemonic()) {
							SelectImage = y;
						}
					}
					if (clickTimes == 2) {
						ImageIcon ic2 = null;

						for (int t = 0; t < ClickedFilePath.size(); t++) {

							if (ClickedFilePath.get(t).getName().equals(SmallTextFields.get(SelectImage).getText())) {
								System.out.println("D\\" + ClickedFilePath.get(t).getAbsolutePath());
								ImageIcon TemporaryIcon = new ImageIcon(ClickedFilePath.get(t).getAbsolutePath());
								Image TemporaryImage = TemporaryIcon.getImage().getScaledInstance(
										TemporaryIcon.getIconWidth(), TemporaryIcon.getIconHeight(),
										Image.SCALE_DEFAULT);
								ic2 = new ImageIcon(TemporaryImage);
							}
						}
						FullFrame jj = new FullFrame(ClickedFilePath, SmallTextFields, SelectImage, ImagesQuantity,
								SmallLabels, "123");
						jj.setVisible(true);
						jj.getJLabel1().setIcon(ic2);
						jj.getJLabel1().setHorizontalAlignment(SwingConstants.CENTER);
					}
				}
			});
		}
	}

	public void Open() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			SelectImage = -1;
			FullFrame jj = new FullFrame(ClickedFilePath, SmallTextFields, SelectImage, ImagesQuantity, SmallLabels,
					chooser.getSelectedFile().getAbsolutePath());
			ImageIcon ic1 = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
			Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(), Image.SCALE_DEFAULT);
			ImageIcon ic2 = new ImageIcon(im);
			jj.getJLabel1().setIcon(ic2);
			jj.setVisible(true);
			jj.getJLabel1().setHorizontalAlignment(SwingConstants.CENTER);

		}
	}

	public void RunTree(final JTree jTree1) {
		File[] roots = (new PFileSystemView()).getRoots();//电脑磁盘根路径
		MyNode nod = null;
		nod = new MyNode(roots[0]);
		nod.explore();
		jTree1.setModel(new DefaultTreeModel(nod)); // 设置将提供数据的 TreeModel
		jTree1.addTreeExpansionListener(new TreeExpansionListener() {  //监听器

			public void treeExpanded(TreeExpansionEvent event) {  //节点展开
				TreePath path = event.getPath();
				MyNode node = (MyNode) path.getLastPathComponent();
				if (!node.isExplored()) {
					DefaultTreeModel model = ((DefaultTreeModel) jTree1.getModel());
					node.explore();
					model.nodeStructureChanged(node);
				}
			}

			public void treeCollapsed(TreeExpansionEvent event) {
			}
		});

		jTree1.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				ShowImages(e, new TreePath(0), 0);
				E = e;

			}
		});
	}

	public void ShowImages(MouseEvent e, TreePath path, int FlagTree) {
		try {

			Locale systime = Locale.CHINA;
			SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss", systime);
			String temptime = timeformat.format(new Date());
			System.out.println("C\\:" + temptime);
			SmallPanels.clear();
			SmallLabels.clear();
			SmallTextFields.clear();
			ImagePanel.removeAll();
			ImagePanel.repaint();
			ImageFiles.clear();
			int flag = 0;
			ImagePanel.setLayout(null);
			String filepath = null;
			ImagesQuantity = 0;

			JTree tree = (JTree) e.getSource();
			int row = tree.getRowForLocation(e.getX(), e.getY());
			if (row == -1) {
				return;
			}
			if (FlagTree == 0) {
				path = tree.getPathForRow(row);
			}

			if (path == null) {
				return;
			}
			MyNode node = (MyNode) path.getLastPathComponent();
			if (node == null) {
				return;
			}
			try {
				filepath = node.getWorR1();
				FilePath = node.getWorR1();
				System.out.println("node=" + path);
			} catch (IOException ex) {
				Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
			System.out.println(filepath);

			for (int i = 0; i < jComboBox1.getItemCount(); i++) {
				if (filepath.equals(jComboBox1.getItemAt(i))) {
					flag = 1;
				}
			}
			if (flag == 0) {
				jComboBox1.addItem(filepath);
				TreePaths.add(path);
			}
			jComboBox1.setSelectedItem(filepath);
			File[] files = node.getWorR().listFiles();
			ClickedFilePath.clear();
			for (int FilesQuantity = 0; FilesQuantity < files.length; FilesQuantity++) {
				ClickedFilePath.add(files[FilesQuantity]);
			}

			int PictureNumber = 0;
			for (int CIN = 0; CIN < files.length; CIN++) {
				String ext = files[CIN].getName()
						.substring(files[CIN].getName().lastIndexOf("."), files[CIN].getName().length()).toLowerCase();
				if (ext.equals(".jpg") || ext.equals(".gif") || ext.equals(".bmp")) {
					PictureNumber++;
					ImageFiles.add(files[CIN]);
				}
			}

			for (int mm = 0; mm < ImageFiles.size(); mm++) {
				SmallLabels.add(new JLabel());
				SmallPanels.add(new JPanel());
				SmallTextFields.add(new JTextField());
			}
			int i = ImageFiles.size();
			Runnable threadimages1 = new ThreadImages(ImageFiles, 0, i / 6, SmallLabels, SmallTextFields, SmallPanels,
					ImagePanel);
			Runnable threadimages2 = new ThreadImages(ImageFiles, i / 6, i / 6 * 2, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Runnable threadimages3 = new ThreadImages(ImageFiles, i / 6 * 2, i / 6 * 3, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Runnable threadimages4 = new ThreadImages(ImageFiles, i / 6 * 3, i / 6 * 4, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Runnable threadimages5 = new ThreadImages(ImageFiles, i / 6 * 4, i / 6 * 5, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Runnable threadimages6 = new ThreadImages(ImageFiles, i / 6 * 5, i, SmallLabels, SmallTextFields,
					SmallPanels, ImagePanel);
			Thread t1 = new Thread(threadimages1);
			Thread t2 = new Thread(threadimages2);
			Thread t3 = new Thread(threadimages3);
			Thread t4 = new Thread(threadimages4);
			Thread t5 = new Thread(threadimages5);
			Thread t6 = new Thread(threadimages6);
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
			ImagesQuantity = ImageFiles.size();
			System.out.println("D\\:" + ImagesQuantity);
			InitLabelListener();

			if (ImagesQuantity > 20) {
				BigScrollPane.getVerticalScrollBar().setVisible(true);
				if (ImagesQuantity % 5 == 0) {
					ImagePanel.setPreferredSize(new Dimension(632, 125 * (ImagesQuantity / 5)));
				} else {
					ImagePanel.setPreferredSize(new Dimension(632, 125 * (ImagesQuantity / 5 + 1)));
				}
				BigScrollPane.getVerticalScrollBar().setValue(0);
			} else {
				ImagePanel.setPreferredSize(new Dimension(632, 399));
			}

		} catch (StringIndexOutOfBoundsException ex) {
			ImagePanel.setPreferredSize(new Dimension(632, 399));
		}

	}

	
	@SuppressWarnings("unchecked")
	private void initComponents() {

		jToolBar1 = new JToolBar();
		jButton1 = new JButton();
		jButton2 = new JButton();
		jButton3 = new JButton();
		jButton4 = new JButton();
		jButton5 = new JButton();
		jScrollPane1 = new JScrollPane();
		jTree1 = new JTree();
		jComboBox1 = new JComboBox();
		jTabbedPane1 = new JTabbedPane();
		jComboBox2 = new JComboBox();
		jMenuBar1 = new JMenuBar();
		jMenu1 = new JMenu();
		jMenuItem1 = new JMenuItem();
		jMenuItem2 = new JMenuItem();
		jMenu2 = new JMenu();
		jMenuItem3 = new JMenuItem();
		jMenu3 = new JMenu();
		jMenuItem4 = new JMenuItem();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		jToolBar1.setRollover(true);

		jButton1.setIcon(new ImageIcon(getClass().getResource("/images/1.jpg"))); 
		jButton1.setText("??");
		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton1);

		jButton2.setIcon(new ImageIcon(getClass().getResource("/images/2.JPG"))); 
		jButton2.setText("2又是什么");
		jButton2.setFocusable(false);
		jButton2.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton2.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton2);

		jButton3.setIcon(new ImageIcon(getClass().getResource("/images/3.jpg"))); 
		jButton3.setText("3是什么");
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton3.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton3);

		jButton4.setIcon(new ImageIcon(getClass().getResource("/images/2.j4g"))); 
		jButton4.setText("什么东西");
		jButton4.setFocusable(false);
		jButton4.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton4.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton4);

		jButton5.setIcon(new ImageIcon(getClass().getResource("/images/5.jpg")));
		jButton5.setText("按钮");
		jButton5.setFocusable(false);
		jButton5.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton5.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton5);

		jScrollPane1.setViewportView(jTree1);   //滚动条
		RunTree(jTree1);

		jComboBox1.setPreferredSize(new java.awt.Dimension(62, 14));

		jComboBox2.setModel(new DefaultComboBoxModel(new String[] { "what", " ", " ", " " }));

		jMenuBar1.setMaximumSize(new java.awt.Dimension(1000, 32769));

		jMenu1.setText("miea");

		jMenuItem1.setText("sjb");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem2.setText("ddssm");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("zmnl");

		jMenuItem3.setText("nmsb");
		jMenu2.add(jMenuItem3);

		jMenuBar1.add(jMenu2);

		jMenu3.setText("ghh");

		jMenuItem4.setText("ghd");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem4ActionPerformed(evt);
			}
		});
		jMenu3.add(jMenuItem4);

		jMenuBar1.add(jMenu3);

		setJMenuBar(jMenuBar1);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(jComboBox2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
								layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(jTabbedPane1, GroupLayout.DEFAULT_SIZE, 685,
												Short.MAX_VALUE)
										.addComponent(jComboBox1, 0, 685, Short.MAX_VALUE)))
				.addComponent(jToolBar1, GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
								.addComponent(jToolBar1, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 20,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jTabbedPane1, GroupLayout.DEFAULT_SIZE, 540,
														Short.MAX_VALUE)
												.addGap(2, 2, 2))
										.addGroup(layout.createSequentialGroup()
												.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 536,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addGap(5, 5, 5)))
								.addContainerGap()));

		pack();
	}

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
		Open();
	}

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
		System.exit(0);
	}

	private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
		IntroduceFrame.setVisible(true);
		IntroduceTextArea.setText("\n     。\n" + "      QQ:3079118617\n" + "     ？\n");
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		if (jComboBox1.getSelectedIndex() > 0) {
			Back();
		}
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		if (jComboBox1.getSelectedIndex() + 1 < TreePaths.size()) {
			Next();
		}
	}

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		Up();
	}

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		ShowImages(E, new TreePath(0), 0);
	}

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		Delete();
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("错误");
		}

		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				MyFrame mainFrame = new MyFrame();
				mainFrame.Init();
				mainFrame.setTitle("美图看看");
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
			}
		});
	}

	
}
