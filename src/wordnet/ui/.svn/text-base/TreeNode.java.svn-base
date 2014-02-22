package wordnet.ui;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 * 
 * @author GeniusLJR
 * 构造动态节点图所需要用的每个节点的数据结构
 * 每个节点根据自己的受力情况进行移动以便TreePane来进行重绘
 * 
 */
public class TreeNode {
	/**
	 * 动态节点图中绘制节点的宽度
	 */
	public static final int WIDTH = 10;
	/**
	 * 动态节点图中绘制节点的高度
	 */
	public static final int HEIGHT = 10;
	
	private String name;
	private TreeNode parent;
	private List<TreeNode> children;
	private int childNum;
	/**
	 * 节点的横坐标x
	 */
	private double x;
	/**
	 * 节点的纵坐标
	 */
	private double y;

	//节点质量
	public double mass;
	/**
	 * 节点所带电荷量
	 */
	public double equantity;
	/**
	 * 节点在X坐标轴上的速度分量
	 */
	private double velocityX = 0;
	/**
	 * 节点在y坐标轴上的速度分量
	 */
	private double velocityY = 0;
	
	//节点所受力分别在X和Y方向上的分量
	private double forceX;
	private double forceY;
	//节点的加速度分别在X和Y方向上的分量
	public double accelerationX = 0;
	public double accelerationY = 0;
	//节点在绘制中所需要的颜色，根据各自属性(state)的值来确定，
	//state = 0 为中心节点，颜色为CenterColor
	//state = 1为最外周子节点，颜色为VertexColor
	//state = 2为表示同义词类的根节点，其父节点为中心节点，颜色为SynonymColor
	//state = 3为表示反义词类的根节点，其父节点为中心节点，颜色为AntonymColor

	private Color color;
	private int state;
	//各个节点的颜色 
	private final Color CenterColor = Color.BLACK;
	private final Color SynonymColor = new Color(112, 193, 65);
	private final Color AntonymColor = new Color(246, 233, 31);
	private final Color VertexColor = new Color(127, 211, 239);
	private final Color NewColor = new Color(245, 207, 186);
	private final Color ShowColor = new Color(113, 199, 216);
	//private final Color ShowColor2 = new Color(255, 255, 255);
	
	private final Color[] colors = new Color[]{CenterColor, VertexColor, SynonymColor, AntonymColor, NewColor, ShowColor, ShowColor};
	
	/**
	 * 空构造函数
	 */
	public TreeNode() {
	}
	/**
	 * 根据一个单词的字符串来构造
	 * @param _name 节点所表示单词的字符串
	 */
	public TreeNode(String _name) {
		name = _name;
		parent = null;
		mass = Double.POSITIVE_INFINITY;
		equantity = 200;
	}
	/**
	 * 根据一个单词的字符串和其父节点来构造
	 * @param _name 节点所表示单词的字符串
	 * @param _parent 节点的父节点
	 */
	public TreeNode(String _name, TreeNode _parent) {
		name = _name;
		parent = _parent;
		parent.addChild(this);
		//如果该节点是中间层节点
		if (parent.parent == null) {
			mass = 5;
			equantity = 3;
		}
		//如果该节点是最外层子节点
		else {
			mass = 10;
			equantity = 9;
		}
	}
	/**
	 * 返回该节点所代表单词的字符串
	 * @return 单词字符串
	 */
	public String getName() {
		return name;
	}
	/**
	 * 返回该节点的子节点个数
	 * @return 子节点个数
	 */
	public int getChildNum() {
		return childNum;
	}
	/**
	 * 添加一个子节点
	 * @param child 子节点
	 */
	public void addChild(TreeNode child) {
		if(children == null){
			//如果是第一个子节点
			children = new ArrayList<TreeNode>();
		}
		children.add(child);
		childNum++;
	}
	/**
	 * 得到指定索引的子节点
	 * @param index 子节点的索引
	 * @return 指定索引指向的子节点
	 */
	public TreeNode getChild(int index) {
		return children.get(index);
	}
	/**
	 * 得到该节点的父节点
	 * @return 父节点
	 */
	public TreeNode getParent() {
		return parent;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	/**
	 * 设置节点坐标
	 * @param _x 横坐标
	 * @param _y 纵坐标
	 */
	public void setLocation(double _x, double _y) {
		this.x = _x;
		this.y = _y;
	}
	/**
	 * 得到该节点横坐标
	 * @return 横坐标
	 */
	public double getX() {
		return x;
	}
	/**
	 * 得到该节点纵坐标
	 * @return 纵坐标
	 */
	public double getY() {
		return y;
	}
	/**
	 * 得到该节点X轴方向的速度
	 * @return X方向速度
	 */
	public double getVx() {
		return this.velocityX;
	}
	/**
	 * 得到该节点y轴方向的速度
	 * @return Y方向速度
	 */
	public double getVy() {
		return this.velocityY;
	}

	/**
	 * 得到包围该节点的矩形
	 * @return 包围该节点的矩形
	 */
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
	}
	/**
	 * 设置该节点的属性，用来区分中心节点，中间层节点，最外层节点等
	 * @param _state 节点属性
	 */
	public void setState (int _state) {
		state = _state;
		color = colors[state];
		if (state == 4) {
			mass = Double.POSITIVE_INFINITY;
		}
	}
	/**
	 * 返回节点的属性值
	 * @return state标记是中心节点，中间层节点，最外层节点等
	 */
	public int getState () {
		return state;
	}
	/**
	 * 返回该节点的颜色
	 * @return 颜色
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * 给节点添加一个力
	 * @param _forceX X轴方向的力
	 * @param _forceY Y轴方向的力
	 */
	public void addForce(double _forceX, double _forceY) {
		forceX += _forceX;
		forceY += _forceY;
	}
	/**
	 * 使节点的力，速度，加速度都为0
	 */
	public void clearForceAndV() {
		forceX = forceY = 0;
		this.velocityX = this.velocityY = 0;
		this.accelerationX = this.accelerationY = 0;
	}
	/**
	 * 使节点移动
	 * @param time 节点移动的时间间隔
	 */
	public void move(double time) {
		//限定速度最大值
		final double VelocityMax = 0.5;
		
		//牛顿第二定律求加速度
		this.accelerationX = forceX / this.mass;
		this.accelerationY = forceY / this.mass;
		
		//新的速度
		double newVx = this.velocityX + this.accelerationX * time;
		double newVy = this.velocityY + this.accelerationY * time;
		//新的坐标
		x = (x + (newVx + this.velocityX)/2 * time);
		y = (y + (newVy + this.velocityY)/2 * time);
		//节点如果超出边界则直接反弹
		if (x > TreePane.FRAME_SIZE) x = 2*TreePane.FRAME_SIZE - x;
		if (x < 0) x = -x;
		if (y > TreePane.FRAME_SIZE) y = 2*TreePane.FRAME_SIZE - y;
		if (y < 0) y = -y;
		//更新速度值
		this.velocityX = newVx;
		this.velocityY = newVy;
		//如果速度绝对值超过限制，则令其取最大值
		if (this.velocityX > VelocityMax) this.velocityX = VelocityMax;
		if (this.velocityX < -VelocityMax) this.velocityX = -VelocityMax;
		if (this.velocityY > VelocityMax) this.velocityY = VelocityMax;
		if (this.velocityY < -VelocityMax) this.velocityY = -VelocityMax;
		
	}
}
