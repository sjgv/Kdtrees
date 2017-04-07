import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTrees<Value> {
    private static final double negXinf = 0.0;
    private static final double posXinf = 1.0;
    private static final double negYinf = 0.0;
    private static final double posYinf = 1.0;

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
        root = put(root, p,val, negXinf, negYinf, posXinf, posYinf, 0);
    }
    
    public Value get(Point2D p){
    	return get(root,p,0);
    }

    public boolean contains(Point2D p) {
        return (contains(root, p, 0) != null);
    }

    private int cmp(Point2D fresh, Point2D old, int level) {
        if (level % 2 == 0) {
            //Compare x-coordinates
            int cmp = Double.compare(fresh.x(), old.x());
            if (cmp == 0) {
                return Double.compare(fresh.y(), old.y());
            } else {
                return cmp;
            }
        } else {
         //Compare y-coordinates
        	int cmp = Double.compare(fresh.y(), old.y());
            if (cmp == 0) {
                return Double.compare(fresh.x(),old.y());
            } else {
                return cmp;
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
    
    @SuppressWarnings("unchecked")
	private Value get(Node N, Point2D p, int level){
    	if (N == null) {
            return null;
        };
        int cmp = cmp(p, N.p, level);

        if (cmp < 0) {
        	Node nodel = (Node) get(N.left, p, level + 1);
            if (level % 2 == 0) {
                N.left = nodel;
            } else {
                N.left = nodel;
            }
        } else if (cmp > 0) {
            Node noder = (Node) get(N.right, p, level + 1);
			if (level % 2 == 0) {
                N.right = noder;
            } else {
                N.right = noder;
            }
        }

        return N.val;
    }

    private Point2D contains(Node N, Point2D p, int level) {
        while (N != null) {
        	//fresh,old, level
            int cmp = cmp(p, N.p, level);

            if (cmp < 0) {
                return contains(N.left, p, level + 1);
            } else if (cmp > 0) {
                return contains(N.right, p, level + 1);
            } else {
                return N.p;
            }
        }

        return null;
    }

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        rangeAdd(root, rect, q);
        return q;
    }

    private void rangeAdd(Node N, RectHV rect, Queue<Point2D> q) {
        if (N != null && rect.intersects(N.rect)) {
            if (rect.contains(N.p)) {
                q.enqueue(N.p);
            }
            rangeAdd(N.left, rect, q);
            rangeAdd(N.right, rect, q);
        }
    }

    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        } else {
            Point2D result = null;
            result = nearest(root, p, result);
            return result;
        }
    }

    private Point2D nearest(Node N, Point2D p, Point2D minP) {
        if (N != null) {
            if (minP == null) {
                minP = N.p;
            }

            // If the current min point is closer to query than the current point
            //it's either going to be greater than or equal to
            if (minP.distanceSquaredTo(p) >= N.rect.distanceSquaredTo(p)) {
                if (N.p.distanceSquaredTo(p) < minP.distanceSquaredTo(p)) {
                    minP = N.p;
                }

                // Check in which order should we iterate
                if (N.right != null && N.right.rect.contains(p)) {
                    minP = nearest(N.right, p, minP);
                    minP = nearest(N.left, p, minP);
                } else {
                    minP = nearest(N.left, p, minP);
                    minP = nearest(N.right, p, minP);
                }
            }
        }

        return minP;
    }
}


