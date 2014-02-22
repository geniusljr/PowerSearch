package wordnet.ui;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import wordnet.base.*;

public class TreePane extends JPanel implements Runnable {

	private static final long serialVersionUID = -5900447289566704451L;
	// ������С
	public static int FRAME_SIZE = 590;
	// �ڵ�����ߵ���ɫ
	private final Color LineColor = Color.LIGHT_GRAY;
	// ��ѡ�нڵ����ɫ
	private final Color SelectedColor = new Color(222, 74, 74);
	// ������ɫ
	private final Color FontColor = Color.BLACK;
	// ������ɫ
	private final Color BackGroundColor = Color.white;/* new Color(238, 238, 238) */;

	// ��Ⱦ������ʱ����
	private final int RenderTime = 10;
	// �ڵ��ƶ���ʱ����
	private final double MoveTime = 0.3;
	// �洢���нڵ�
	private List<TreeNode> nodes;
	// �洢���б�ѡ�нڵ�
	private List<Integer> selected;
	// ���Ľڵ�
	private TreeNode centerNode;
	// ���Ľڵ�ĵ���
	private String centerWord;
	// ��������
	private final static int CENTER_X = TreePane.FRAME_SIZE / 2;
	private final static int CENTER_Y = TreePane.FRAME_SIZE / 2;
	// �������������нڵ�ľ���뾶����
	private final static int CENTER_R = TreePane.FRAME_SIZE / 3;

	// �������
	private int mouseX;
	private int mouseY;
	// �����������
	private int tmpX;
	private int tmpY;
	// ��ʱ��
	private static Timer timer;
	// ���Ľڵ㵥�ʣ����ݽṹMyWord
	private MyWord myword;

	private int count = 0;
	private int tmpcount = 0;

	ImageIcon[] icon = new ImageIcon[7];

	// ���ƻ�������Ⱦ
	ActionListener taskPerformer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			reLocate();
			repaint();
			count++;
			if (count > 5000)
				count = 0;
		}
	};
	// ����϶�������
	MouseMotionListener mousedrag = new MouseMotionAdapter() {
		public void mouseDragged(MouseEvent e) {
			// ��ǰ���λ��
			mouseX = e.getX();
			mouseY = e.getY();
			for (int i = 0; i < selected.size(); i++) {
				// �϶����б�ѡ�нڵ�
				TreeNode temp = nodes.get(selected.get(i));
				temp.setLocation(temp.getX() + mouseX - tmpX, temp.getY()
						+ mouseY - tmpY);
			}
			// �������λ��
			tmpX = mouseX;
			tmpY = mouseY;
			// �ػ�ͼ��
			repaint();
		}
	};
	// �����������
	MouseListener mousepress = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			setFocus();
			tmpX = e.getX();
			tmpY = e.getY();
			boolean chosen = false;
			int totalnum = nodes.size();
			for (int i = 0; i < totalnum; i++) {
				TreeNode tmp = nodes.get(i);
				// ����������ĳ���ڵ㷶Χ�ڷ���
				if (tmp.getBounds().contains(tmpX, tmpY)) {
					chosen = true;
					// ctrl+������������ѡ�нڵ�
					if (e.isControlDown()) {
						if (!selected.contains(i))
							selected.add(i);
						else
							selected.remove(i);
					} else {
						System.out.println(tmp.getState());
						selected = new ArrayList<Integer>();
						selected.add(i);
					}
					// ˫������ѵ���Ľڵ���Ϊ���Ľڵ�չ��
					if (e.getClickCount() == 2
							&& e.getButton() == MouseEvent.BUTTON1) {
						if (tmp == centerNode)
							return;
						if (tmp.getState() != 5 && tmp.getState() != 6) {
							MainWindow.mw.searchStart(tmp.getName(), false);
							return;
						}
						init();
					}
					// �Ҽ���������Ѹýڵ������ͬ��ʽڵ�չ�������ӵ�������
					else if (Math.abs(count - tmpcount) > 50
							&& e.getButton() == MouseEvent.BUTTON3) {
						if (tmp.getState() == 0 || tmp.getState() == 2)
							return;
						addNode(tmp);
						tmpcount = count;
						return;
					} else if (e.getButton() == MouseEvent.BUTTON1
							&& e.getClickCount() == 1) {
						// ������м����˼�ڵ㷶Χ�ڣ�������������Ϊ5������ʹ����˼�ַ������Ա�����
						if (tmp.getState() == 2) {
							tmp.setState(5);
							repaint();
							return;
						}
						if (tmp.getState() == 3) {
							tmp.setState(6);
							repaint();
							return;
						}
					}
					// �ػ�ͼ��
					repaint();
				}
				if (!chosen)
					selected = new ArrayList<Integer>();
			}
		}

		public void mouseReleased(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int totalnum = nodes.size();
			for (int i = 0; i < totalnum; i++) {
				TreeNode tmp = nodes.get(i);
				if (!tmp.getBounds().contains(x, y) && tmp.getState() == 5)
					tmp.setState(2);
				else if (!tmp.getBounds().contains(x, y) && tmp.getState() == 6)
					tmp.setState(3);
			}
		}
	};

	// ��ȡ����
	private void setFocus() {
		this.grabFocus();
	}

	/**
	 * ��_mywordΪ���Ľڵ㣬����ͼ��
	 */
	public TreePane(MyWord _myword) {
		myword = _myword;
		for (int i = 0; i < 6; i++) {
			icon[i] = new ImageIcon(
					MainWindow.class.getResource("/resources/icons/ball" + i
							+ ".png"));
		}
		this.setOpaque(false);
		setFocus();
		run();
	}

	/**
	 * �������Ľڵ�
	 */
	public void setCenterWord(MyWord _word) {
		myword = _word;
		run();
	}

	/**
	 * �������Ľڵ㣬��ʼ�����нڵ����飬ѡ�нڵ����飬�������Ľڵ����� ����ǰ���Ľڵ����ӵ�������չ��
	 */
	private void init() {
		this.setSize(TreePane.FRAME_SIZE, TreePane.FRAME_SIZE);
		centerWord = myword.getName();
		centerNode = new TreeNode(centerWord);
		centerNode.setState(0);
		selected = new ArrayList<Integer>();
		nodes = new ArrayList<TreeNode>();
		centerNode.setLocation(CENTER_X, CENTER_Y);
		// ��centerNode��Ϊ���Ľڵ�չ��
		addNode(centerNode);
	}

	/**
	 * չ���ڵ�treenode ����ͬ�����������ӽڵ㶼���ӵ���������
	 */
	private void addNode(TreeNode treenode) {
		// ���浱ǰ�ڵ������ֱ���ӽڵ�
		List<TreeNode> tmpcenterChildren = new ArrayList<TreeNode>();
		// ����չ��treenode����Ҫ�����нڵ�
		List<TreeNode> tmpnodes = new ArrayList<TreeNode>();
		// չ���½ڵ��Ҫ��ʼ����ѡ�нڵ����飬���
		selected = new ArrayList<Integer>();

		// ��չ��treenode�ĵ�������
		String tmpcenterWord = treenode.getName();
		// ��ǰ�ڵ��MyWord��
		MyWord newword;

		if (!treenode.equals(centerNode)) {
			// ����ýڵ㲻�����Ľڵ㣬����Ҫ���²�ѯ�õ��ʵ�����ͬ��ʷ������
			// ������������Ϊ��չ���ڵ�4
			treenode.setState(4);
			newword = new MyWord(tmpcenterWord);
		} else {
			// �����Ľڵ㣬��������Ϊ���Ľڵ�0
			treenode.setState(0);
			newword = myword;
		}

		tmpnodes.add(treenode);

		// ͬ���
		PartOfSpeach[] pos = new PartOfSpeach[4];
		for (int i = 0; i < 4; i++) {
			pos[i] = newword.getPOS(i);
			if (pos[i] != null) {
				int tempnum = pos[i].getSense().size();
				for (int j = 0; j < tempnum; j++) {

					// �м��ڵ㣬���ʾ���ַ������丸�ڵ㵥�ʵ�һ����˼
					TreeNode tmp = new TreeNode("Sense: "
							+ pos[i].getSense().get(j).getExpression()
									.substring(2), treenode);
					// ����������Ϊ�м����˼�ڵ�
					tmp.setState(2);
					List<String> tempchildren = pos[i].getSense().get(j)
							.getSynGroup();
					int childnum = tempchildren.size();
					if (true) {
						// �������˼��ֻ���Լ�����û������ͬ��ʣ���չ����synset
						for (int k = 0; k < childnum; k++) {
							if (!tempchildren.get(k).equals(treenode.getName())) {
								// չ������˼�µ�����ͬ��ʣ����������Լ�
								TreeNode tmpChild = new TreeNode(
										tempchildren.get(k), tmp);
								// ��������Ϊ����㵥�ʽڵ�
								tmpChild.setState(1);
								tmpnodes.add(tmpChild);
							}
						}
						tmpcenterChildren.add(tmp);
						// System.out.println(tmp.getName());
					}
				}
			}
		}
		tmpnodes.addAll(tmpcenterChildren);

		// �����
		// ��������Ϊ����㵥�ʽڵ�
		TreeNode tmpcenterAntonym = new TreeNode("", treenode);
		// ����������Ϊ����ʽڵ�
		tmpcenterAntonym.setState(3);

		// �洢���з���ʵ���
		List<String> antonymChildren = newword.getAntonym();

		int antonymNum = antonymChildren.size();
		if (antonymNum != 0) {
			tmpnodes.add(tmpcenterAntonym);
			tmpcenterChildren.add(tmpcenterAntonym);

			// ��Set��ɾ�����з��������ͬ�ĵ���
			Set<String> antonymSet = new HashSet<String>();
			for (int i = 0; i < antonymNum; i++) {
				antonymSet.add(antonymChildren.get(i));
			}
			for (String str : antonymSet) {
				// �÷����������������ӽڵ�
				TreeNode tmpChild = new TreeNode(str, tmpcenterAntonym);
				// ����������Ϊ����㵫�˽ڵ�1
				tmpChild.setState(1);
				tmpnodes.add(tmpChild);
			}
		}

		// �����ʼ�����нڵ��λ��
		int tmpcenterChildNum = tmpcenterChildren.size();
		for (int i = 0; i < tmpcenterChildNum; i++) {
			// ��ʼ���м��ڵ��λ��
			double tempR = (Math.random() * CENTER_R / 10);
			double tempX = (treenode.getX() + Math.cos(2 * Math.PI * i
					/ tmpcenterChildNum)
					* tempR);
			double tempy = (treenode.getY() + Math.sin(2 * Math.PI * i
					/ tmpcenterChildNum)
					* tempR);
			TreeNode tempChild = tmpcenterChildren.get(i);
			tempChild.setLocation(tempX, tempy);
			int tempChildNum = tmpcenterChildren.get(i).getChildNum();
			for (int j = 0; j < tempChildNum; j++) {
				// ��ʼ�������ڵ��λ��
				double tmpR = (Math.random() * CENTER_R / 15);
				double tmpX = (tempX + Math.cos(2 * Math.PI * j / tempChildNum)
						* tmpR);
				double tmpY = (tempy + Math.sin(2 * Math.PI * j / tempChildNum)
						* tmpR);
				tempChild.getChild(j).setLocation(tmpX, tmpY);
			}
		}
		// �����нڵ����ӵ�nodes������
		nodes.addAll(tmpnodes);
		// ��սڵ������ٶȣ����ٶ�
		for (TreeNode x : nodes) {
			x.clearForceAndV();
		}
		// �ػ�ͼ��
		repaint();
	}

	/**
	 * �����������������·ֲ����нڵ�
	 */
	private void reLocate() {
		// �ȼ������нڵ���������
		calculateForce();
		// ʹ���нڵ��ƶ����µ�����λ��
		int totalnum = nodes.size();
		for (int i = 0; i < totalnum; i++)
			nodes.get(i).move(MoveTime);
	}

	/**
	 * �ػ�ͼ��
	 */
	public void paint(Graphics g) {
		super.paint(g);
		// ���ñ�����ɫ
		this.setBackground(BackGroundColor);
		int totalnum = nodes.size();
		for (int i = 1; i < totalnum; i++) {
			TreeNode tmp = nodes.get(i);
			String str = tmp.getName();
			TreeNode tmpParent = tmp.getParent();
			if (tmpParent != null) {
				// ����������ɫ�һ�������
				g.setColor(LineColor);
				g.drawLine((int) (tmp.getX()) + TreeNode.WIDTH / 2,
						(int) (tmp.getY()) + TreeNode.HEIGHT / 2,
						(int) (tmpParent.getX()) + TreeNode.WIDTH / 2,
						(int) (tmpParent.getY()) + TreeNode.HEIGHT / 2);
			}
			// ����������ɫ
			if (selected.contains(i))
				g.setColor(SelectedColor);
			else
				g.setColor(FontColor);
			// �����ַ���
			if (tmp.getState() != 2 && tmp.getState() != 3) {
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
				if (tmp.getState() == 5 || tmp.getState() == 6) {
					g.setColor(Color.RED);
					g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
				}
				g.drawString(str, (int) (tmp.getX()) + 10,
						(int) (tmp.getY()) + 10);
				if (tmp.getState() == 5)
					g.drawString("Relation: synonym of "
							+ tmp.getParent().getName(),
							(int) (tmp.getX() + 10), (int) (tmp.getY() - 10));
				if (tmp.getState() == 6)
					g.drawString("Relation: antonym of "
							+ tmp.getParent().getName(),
							(int) (tmp.getX() + 10), (int) (tmp.getY() - 10));
			}
			/*
			 * //���ýڵ���ɫ if (selected.contains(i)) g.setColor(SelectedColor);
			 * else { g.setColor(tmp.getColor()); } //���ƽڵ�
			 * g.fillOval((int)(tmp.getX()), (int)(tmp.getY()), TreeNode.WIDTH,
			 * TreeNode.HEIGHT);
			 */
			g.drawImage(icon[tmp.getState()].getImage(), (int) (tmp.getX()),
					(int) (tmp.getY()), this);
		}
		if (selected.contains(0))
			g.setColor(SelectedColor);
		else
			g.setColor(centerNode.getColor());
		// �������Ľڵ�
		// g.fillOval((int)(centerNode.getX()), (int)(centerNode.getY()),
		// TreeNode.WIDTH, TreeNode.HEIGHT);
		g.drawImage(icon[0].getImage(), (int) (centerNode.getX()),
				(int) (centerNode.getY()), this);
		g.setFont(new Font("Centaur", Font.BOLD, 25));
		g.drawString(centerWord, (int) centerNode.getX(),
				(int) centerNode.getY());

	}

	/**
	 * �����нڵ���������� ÿ���ڵ��൱��һ���������ӣ��ڵ��������൱�ڵ���
	 * ÿ���ڵ��ܵ������ڵ������ĳ��������ɵĵ������˶�������������Ħ������ǽ�ڸ��ĳ���
	 * ���ݿ��ض��ɣ����˶��ɵ������������ÿ�����յ���������Ȼ���������
	 */
	private void calculateForce() {
		// ��ɳ���ϵ�����൱�ڿ��ض����е�K
		double repulsion_factor = 3.0;
		// ���ɵ�ƽ�ⳤ��
		double springlength = 100.0;
		// ���ɵĵ���ϵ��
		double spring_factor = 5;
		// Ħ��ϵ��
		double friction_factor = 30;
		// ǽ��������ϵ��
		double wall_factor = 0.25;

		int totalnum = nodes.size();
		for (int i = 1; i < totalnum; i++) {

			// ��ɳ���
			double repulsionX = 0.0;
			double repulsionY = 0.0;
			TreeNode self = nodes.get(i);
			double selfx = self.getX();
			double selfy = self.getY();
			for (int j = 0; j < totalnum; j++) {
				if (j == i)
					continue;
				double dx = selfx - nodes.get(j).getX();
				double dy = selfy - nodes.get(j).getY();
				double r2 = dx * dx + dy * dy;
				if (r2 == 0) {
					dx = dy = 1;
					r2 = 2;
				}
				TreeNode tempparent = self.getParent();
				// ��Сͬһ�����ڵ��ϵ��ӽڵ���໥������
				if (tempparent != centerNode
						&& tempparent == nodes.get(j).getParent())
					repulsion_factor = 1.1;
				else if (tempparent == centerNode
						&& nodes.get(j).getParent() == centerNode)
					repulsion_factor = 4.0;
				else
					repulsion_factor = 2.0;
				// F = K*Q1*Q2/r^2;
				double repulsion_forcex = repulsion_factor * self.equantity
						* nodes.get(j).equantity * (dx) / r2;
				double repulsion_forcey = repulsion_factor * self.equantity
						* nodes.get(j).equantity * (dy) / r2;
				// �޶�ÿ�����ľ���ֵ����
				if (repulsion_forcex > 100)
					repulsion_forcex = 100;
				if (repulsion_forcey > 100)
					repulsion_forcey = 100;
				if (repulsion_forcex < -100)
					repulsion_forcex = -100;
				if (repulsion_forcey < -100)
					repulsion_forcey = -100;
				repulsionX += repulsion_forcex;
				repulsionY += repulsion_forcey;
			}
			// ���������ܴ��ʱ�򣬽����С
			double d = Math.sqrt(repulsionX * repulsionX + repulsionY
					* repulsionY);
			if (d > 100) {
				repulsionX *= 100 / d;
				repulsionY *= 100 / d;
			}

			// ��������
			double tensionX = 0.0;
			double tensionY = 0.0;
			// �洢�ýڵ�ĸ��ڵ�����ӽڵ������
			// ֻ����Щ�ڵ���ýڵ�֮�����һ������
			List<TreeNode> tempnodes = new ArrayList<TreeNode>();
			if (self.getParent() != null)
				tempnodes.add(self.getParent());
			for (int j = 0; j < self.getChildNum(); j++)
				tempnodes.add(self.getChild(j));
			int tempnodesnum = tempnodes.size();
			for (int j = 0; j < tempnodesnum; j++) {
				double dx = tempnodes.get(j).getX() - selfx;
				double dy = tempnodes.get(j).getY() - selfy;
				double r = Math.sqrt(dx * dx + dy * dy);
				if (r == 0) {
					dx = dy = 1;
					r = 1.414;
				}
				if (self.getParent() == centerNode
						&& tempnodes.get(j) == centerNode)
					springlength = 150.0;
				else
					springlength = 70.0;
				// �õ���ֻ�е����ȳ���springlength��ʱ����������
				// ���Ǻ̵ܶ�ʱ�򲻻��������
				// F = k * (r -L);
				if (r > springlength) {
					double tension_forcex = dx / r;
					double tension_forcey = dy / r;
					tension_forcex *= spring_factor * (r - springlength);
					tension_forcey *= spring_factor * (r - springlength);
					tensionX += tension_forcex;
					tensionY += tension_forcey;
				}
			}
			// ���������ܴ��ʱ�򣬽����С
			double s = Math.sqrt(tensionX * tensionX + tensionY * tensionY);
			if (s > 100) {
				tensionX *= 100 / s;
				tensionY *= 100 / s;
			}

			// Ħ����
			// F = -k * v
			double frictionX = -friction_factor * self.getVx();
			double frictionY = -friction_factor * self.getVy();

			// ǽ�����������ڵ�ƫ������ԽԶ����������Խ��
			double wallX = -(self.getX() - TreePane.FRAME_SIZE / 2)
					* wall_factor;
			double wallY = -(self.getY() - TreePane.FRAME_SIZE / 2)
					* wall_factor;

			// ����
			double forceX = repulsionX + frictionX + tensionX + wallX;
			double forceY = repulsionY + frictionY + tensionY + wallY;

			self.addForce(forceX, forceY);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		if (timer != null)
			timer.stop();
		timer = new Timer(RenderTime, taskPerformer);
		timer.start();
		this.setBounds(590, 40, 600, 600);
		this.addMouseListener(mousepress);
		this.addMouseMotionListener(mousedrag);
	}
}