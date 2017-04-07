import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTrees<Value> {
    private static final double XMIN = 0.0;
    private static final double XMAX = 1.0;
    private static final double YMIN = 0.0;
    private static final double YMAX = 1.0;

    private int size;

    private Node root;

    private class Node {
        private Point2D p;
        private RectHV rect;
        private Value val;

        private Node left;
        private Node right;

        private Node(Point2D p, RectHV inRect, Value val) {
            this.p = p;
            this.rect = inRect;
            this.val = val;

            this.left = null;
            this.right = null;
        }
    }

    public KdTrees() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void put(Point2D p, Value val) {
        root = put(root, p,val, XMIN, YMIN, XMAX, YMAX, 0);
    }
    
    public Value get(Point2D p){
    	return get(root,p,0);
    }

    public boolean contains(Point2D p) {
        return (contains(root, p, 0) != null);
    }

    private int cmp(Point2D a, Point2D b, int level) {
        if (level % 2 == 0) {
            // Compare x-coordinates
            int cmpResult = new Double(a.x()).compareTo(new Double(b.x()));
            if (cmpResult == 0) {
                return new Double(a.y()).compareTo(new Double(b.y()));
            } else {
                return cmpResult;
            }
        } else {
         // Compare y-coordinates
            int cmpResult = new Double(a.y()).compareTo(new Double(b.y()));

            if (cmpResult == 0) {
                return new Double(a.x()).compareTo(new Double(b.x()));
            } else {
                return cmpResult;
            }
        }
    }

    private Node put(Node N, Point2D p, Value v, double xmin, double ymin, double xmax, double ymax, int level) {
        if (N == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax),v);
        };

        int cmp = cmp(p, N.p, level);
        if (cmp < 0) {
            if (level % 2 == 0) {
                N.left = put(N.left, p, v, xmin, ymin, N.p.x(), ymax, level + 1);
            } else {
                N.left = put(N.left, p, v, xmin, ymin, xmax, N.p.y(), level + 1);
            }
        } else if (cmp > 0) {
            if (level % 2 == 0) {
                N.right = put(N.right, p, v, N.p.x(), ymin, xmax, ymax, level + 1);
            } else {
                N.right = put(N.right, p, v, xmin, N.p.y(), xmax, ymax, level + 1);
            }
        }

        return N;
    }
    private Value get(Node N, Point2D p, int level){
    	if (N == null) {
            return null;
        };
        int cmp = cmp(p, N.p, level);

        if (cmp < 0) {
            if (level % 2 == 0) {
                N.left = (Node) get(N.left, p, level + 1);
            } else {
                N.left = (Node) get(N.left, p, level + 1);
            }
        } else if (cmp > 0) {
            if (level % 2 == 0) {
                N.right = (Node) get(N.right, p, level + 1);
            } else {
                N.right = (Node) get(N.right, p, level + 1);
            }
        }

        return N.val;
    }

    private Point2D contains(Node x, Point2D point, int level) {
        while (x != null) {

            int cmp = cmp(point, x.p, level);

            if (cmp < 0) {
                return contains(x.left, point, level + 1);
            } else if (cmp > 0) {
                return contains(x.right, point, level + 1);
            } else {
                return x.p;
            }
        }

        return null;
    }

    public void draw() {
        StdDraw.clear();

       // drawLine(root, 0);
    }

   
}


