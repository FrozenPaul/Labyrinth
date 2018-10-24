import java.util.List;

public class Point {
    private int i;
    private int j;

    Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object obj) {
        Point point = (Point) obj;
        if (i == point.getI() && j == point.getJ())
            return true;
        return false;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isInList(List<Point> list) {
        if (list.contains(this)) return true;
        return false;
    }

    public void Show() {
        System.out.println("(" + i + "," + j + ")");
    }
}