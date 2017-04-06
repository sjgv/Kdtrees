import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;


public class KdTrees<Value> {
	private Node root;
	private int size;
	
	private class Node{
		private Point2D p;
		private RectHV rect;
		private Node leftN;
		private Node rightN;
		private Value val;
		
		
		public Node(Point2D p, RectHV r){	
			this.p = p;
			this.rect = r;
		}
		
	}
	
	public KdTrees(){
		this.size = 0;
		this.root = null;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public int size(){
		return size;
	}
	
	public void put(Point2D p, Value val){
		root = put(root,p,val, 0.0,0.0,1.0,1.0,true);
	}
	public boolean contains(Point2D p){
		return contains(root,p,true);
	}
	public void draw(){
		draw(root,true);
	}
	public Iterable<Point2D> range(RectHV rect){
		Queue<Point2D> q = new Queue<Point2D>();
		range(root,rect,q);
	}
	public Point2D nearest(Point2D p){
		if(root == null) return null;
		return nearest(root, p, root.p, true);
	}
	//traverse tree until null, insert there
	private Node put(Node N, Point2D p, Value v, double x0, double y0, double x1, double y1, boolean isVertical){
		if(N == null){
			size++;
			RectHV r = new RectHV(x0,y0,x1,y1);
			return new Node(p,r);
		}
		else if(N.p.x() == p.x() && N.p.y() == p.y()) return N;
		if(isVertical){
			double cmp = p.x() - N.p.x();
			if (cmp < 0) N.leftN = put(N,p,v,x0,y0,x1,y1,!isVertical);
			else N.rightN = put(N,p,v,x0,y0,x1,y1,!isVertical);
			}
		else{
			double cmp = p.y() - N.p.y();
			if (cmp < 0) N.leftN = put(N,p,v,x0,y0,x1,y1,!isVertical);
			else N.rightN = put(N,p,v,x0,y0,x1,y1,!isVertical);
		}
		return N;
	}
	//get points
	private Value get(Point2D p, ){
		if;
	}
}