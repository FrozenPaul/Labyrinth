public class Point {
    private int i;
    private int j;

    Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void Show() {
        System.out.println("(" + i + "," + j + ")");
    }
}