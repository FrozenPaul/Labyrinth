import java.util.List;

public class Point {
    private int i;
    private int j;

    Point(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI(){
        return i;
    }

    public int getJ(){
        return j;
    }

    public boolean isInList(List<Point> list){
        for (int k = 0; k < list.size(); k++) {
            if (i == list.get(k).getI() && j==list.get(k).getJ())
                return true;
        }
        return false;
    }

    public void Show() {
        System.out.println("(" + i + "," + j + ")");
    }
}