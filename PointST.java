import java.util.LinkedList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.ST;


public class PointST<Value> {
	ST<Point2D,Value> points = new ST<>();
	
	public PointST(){
		//this.points = null;
	}
	
	public boolean isEmpty(){
		return points.isEmpty();
	}
	public int size(){
		return points.size();
	}
	public void put(Point2D p, Value val){
		//System.out.println(p + " " + val);
		//System.out.println(points.size());
		points.put(p, val);
	}
	public Value get(Point2D p){
		return points.get(p);
	}
	public boolean contains(Point2D p){
		return points.contains(p);
	}
	
	public Iterable<Point2D> points(){
		LinkedList<Point2D> myList = new LinkedList<>();
		for(Point2D el : points){
			myList.add(el);
		}
		return myList;
	}
	
	public Iterable<Point2D> range(RectHV rect){
		LinkedList<Point2D> myList = new LinkedList<>();
		for(Point2D el : points){
		if(rect.contains(el)){
			myList.add(el);
		} 
		}
		return myList;
	}
	public Point2D nearest(Point2D p){
		Point2D closest = new Point2D(0,0);
		for(Point2D el : points){
			if(el.distanceTo(p) < closest.distanceTo(p)){
				closest = el;
			}
		}
		return closest;
	}
}
