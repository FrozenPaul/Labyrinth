import java.util.Objects;

public class Point {
    private int i;
    private int j;

    Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Point point = (Point) obj;
        return  ( i == point.getI() && j == point.getJ());
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "(" + i + "," + j + ")";
    }
}