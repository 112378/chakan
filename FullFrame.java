package picture;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

public class FullFrame extends JFrame {
	
	private JButton jButton1;
	private JButton jButton2;
	private JButton jButton3;
	private JButton jButton4;
	private JButton jButton5;
	private JButton jButton6;
	private JLabel jLabel1;
	private JScrollPane jScrollPane1;
	private JToolBar jToolBar1;

	ArrayList<File> ClickedFilePath = new ArrayList<File>();
	ArrayList<JTextField> ImagesNameTF = new ArrayList<JTextField>();
	ArrayList<JLabel> SmallLabels = new ArrayList<JLabel>();
	int SelectImage = 0;
	double CurrentMultiple = 1;
	int ImagesQuantity = 0;
	int flag = 0;
	Slide play;
	String OpenFilePath;

	public FullFrame(ArrayList<File> ClickedFilePath, ArrayList<JTextField> ImagesNameTF, int SelectImage,
			int ImagesQuantity, ArrayList<JLabel> SmallLabels, String OpenFilePath) {
		initComponents();
		setTitle("��ͼ����");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.ClickedFilePath = ClickedFilePath;
		this.ImagesNameTF = ImagesNameTF;
		this.SelectImage = SelectImage;
		this.ImagesQuantity = ImagesQuantity - 1;
		this.SmallLabels = SmallLabels;
		this.OpenFilePath = OpenFilePath;
		if (SelectImage == -1) {
			jButton2.setEnabled(false);
			jButton1.setEnabled(false);
			jButton5.setEnabled(false);
			jButton6.setEnabled(false);
		}
		if (SelectImage == 0) {
			jButton1.setEnabled(false);
			jButton1.setEnabled(false);

		}
		if (SelectImage == ImagesQuantity - 1) {
			jButton1.setEnabled(true);
			jButton2.setEnabled(false);
			jButton3.setEnabled(true);
			jButton4.setEnabled(true);
			jButton5.setEnabled(false);
			jButton6.setEnabled(false);
		}
		if (SelectImage != ImagesQuantity - 1 && SelectImage != 0 && SelectImage != -1) {
			jButton1.setEnabled(true);
			jButton2.setEnabled(true);
			jButton3.setEnabled(true);
			jButton4.setEnabled(true);
			jButton5.setEnabled(true);
			jButton6.setEnabled(true);
		}

	}

	public void Back() {
		if (SelectImage - 1 >= 0) {
			for (int i = 0; i < ClickedFilePath.size(); i++) {

				if (ClickedFilePath.get(i).getName().equals(ImagesNameTF.get(SelectImage - 1).getText())) {

					ImageIcon ic1 = new ImageIcon(ClickedFilePath.get(i).getAbsolutePath());
					Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(),
							Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);
				}
			}
			SelectImage = SelectImage - 1;
			if (SelectImage == 0) {
				getJButton1().setEnabled(false);
			} else {
				getJButton1().setEnabled(true);
				jButton2.setEnabled(true);
				jButton3.setEnabled(true);
				jButton4.setEnabled(true);
				jButton5.setEnabled(true);
				jButton6.setEnabled(true);
			}
		}
	}

	public void Forward() {
		if (SelectImage + 1 <= ImagesQuantity) {
			for (int i = 0; i < ClickedFilePath.size(); i++) {

				if (ClickedFilePath.get(i).getName().equals(ImagesNameTF.get(SelectImage + 1).getText())) {

					ImageIcon ic1 = new ImageIcon(ClickedFilePath.get(i).getAbsolutePath());
					Image im = ic1.getImage().getScaledInstance(ic1.getIconWidth(), ic1.getIconHeight(),
							Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);
				}
			}

			SelectImage = SelectImage + 1;
			if (SelectImage == ImagesQuantity) {
				jButton2.setEnabled(false);
				jButton5.setEnabled(false);
				jButton6.setEnabled(false);
				getJButton1().setEnabled(true);
				jButton3.setEnabled(true);
				jButton4.setEnabled(true);
			} else {
				jButton1.setEnabled(true);
			}
		}
	}

	public void Enlarge() {
		if (SelectImage == -1) {
			ImageIcon ic1 = new ImageIcon(OpenFilePath);

			double w = ic1.getIconWidth();
			double h = ic1.getIconHeight();
			Image im = ic1.getImage().getScaledInstance((int) (w * (CurrentMultiple + 0.25)),
					(int) (h * (CurrentMultiple + 0.25)), Image.SCALE_DEFAULT);
			ImageIcon ic2 = new ImageIcon(im);
			getJLabel1().setIcon(ic2);

		} else {
			if (SelectImage != flag) {
				CurrentMultiple = 1;
			}
			for (int i = 0; i < ClickedFilePath.size(); i++) {
				if (ClickedFilePath.get(i).getName().equals(ImagesNameTF.get(SelectImage).getText())) {
					ImageIcon ic1 = new ImageIcon(ClickedFilePath.get(i).getAbsolutePath());

					double w = ic1.getIconWidth();
					double h = ic1.getIconHeight();
					Image im = ic1.getImage().getScaledInstance((int) (w * (CurrentMultiple + 0.25)),
							(int) (h * (CurrentMultiple + 0.25)), Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);

				}
			}
		}
		CurrentMultiple = CurrentMultiple + 0.25;
		flag = SelectImage;
	}

	public void Decrease() {
		if (SelectImage == -1) {

			ImageIcon ic1 = new ImageIcon(OpenFilePath);

			double w = ic1.getIconWidth();
			double h = ic1.getIconHeight();
			Image im = ic1.getImage().getScaledInstance((int) (w * (CurrentMultiple - 0.25)),
					(int) (h * (CurrentMultiple - 0.25)), Image.SCALE_DEFAULT);
			ImageIcon ic2 = new ImageIcon(im);
			getJLabel1().setIcon(ic2);
		} else {
			if (SelectImage != flag) {
				CurrentMultiple = 1;
			}
			for (int i = 0; i < ClickedFilePath.size(); i++) {
				if (ClickedFilePath.get(i).getName().equals(ImagesNameTF.get(SelectImage).getText())) {
					ImageIcon ic1 = new ImageIcon(ClickedFilePath.get(i).getAbsolutePath());

					double w = ic1.getIconWidth();
					double h = ic1.getIconHeight();
					Image im = ic1.getImage().getScaledInstance((int) (w * (CurrentMultiple - 0.25)),
							(int) (h * (CurrentMultiple - 0.25)), Image.SCALE_DEFAULT);
					ImageIcon ic2 = new ImageIcon(im);
					getJLabel1().setIcon(ic2);
				}
			}
		}
		CurrentMultiple = CurrentMultiple - 0.25;
		flag = SelectImage;
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		jToolBar1 = new JToolBar();
		jButton1 = new JButton();
		jButton2 = new JButton();
		jButton3 = new JButton();
		jButton4 = new JButton();
		jButton5 = new JButton();
		jButton6 = new JButton();
		jScrollPane1 = new JScrollPane();
		jLabel1 = new JLabel();

		jToolBar1.setRollover(true);

		jButton1.setIcon(new ImageIcon(getClass().getResource("/images/2.JPG"))); 
		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton1);

		jButton2.setIcon(new ImageIcon(getClass().getResource("/images/1.jpg")));
		jButton2.setFocusable(false);
		jButton2.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton2.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton2);

		jButton3.setIcon(new ImageIcon(getClass().getResource("/images/3.jpg"))); // NOI18N
		jButton3.setFocusable(false);
		jButton3.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton3.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton3);

		jButton4.setIcon(new ImageIcon(getClass().getResource("/images/4.jpg"))); // NOI18N
		jButton4.setFocusable(false);
		jButton4.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton4.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton4);

		jButton5.setIcon(new ImageIcon(getClass().getResource("/images/5.jpg"))); // NOI18N
		jButton5.setFocusable(false);
		jButton5.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton5.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton5);

		jButton6.setIcon(new ImageIcon(getClass().getResource("/images/6.jpg"))); // NOI18N
		jButton6.setFocusable(false);
		jButton6.setHorizontalTextPosition(SwingConstants.CENTER);
		jButton6.setVerticalTextPosition(SwingConstants.BOTTOM);
		jButton6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton6ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton6);

		jScrollPane1.setViewportView(jLabel1);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, 382,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(278, Short.MAX_VALUE))
				.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)));

		pack();
	}

	@SuppressWarnings("empty-statement")
	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		Decrease();
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		Back();
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		Forward();
	}

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		Enlarge();
	}

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		jButton6.setEnabled(true);
		jButton2.setEnabled(false);
		getJButton1().setEnabled(false);
		jButton3.setEnabled(false);
		jButton4.setEnabled(false);
		jButton5.setEnabled(false);
		play = new Slide(this);
		play.start();
	}

	private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {

		play.stop();
		jButton2.setEnabled(true);
		getJButton1().setEnabled(true);
		jButton3.setEnabled(true);
		jButton4.setEnabled(true);
		jButton5.setEnabled(true);
	}

	public JLabel getJLabel1() {
		return jLabel1;
	}

	public void setJLabel1(JLabel jLabel1) {
		this.jLabel1 = jLabel1;
	}

	public JButton getJButton1() {
		return jButton1;
	}

	public void Forward1() {
		// TODO Auto-generated method stub
		
	}
}
