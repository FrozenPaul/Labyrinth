import java.util.ArrayList;
import java.util.List;

public class Way {

    private List<Point> list = new ArrayList<>();
    public int length = 0;

    Way() {
    }

    public List<Point> getList() {
        return list;
    }

    public void add(Point a) {
        list.add(a);
        length++;
    }

    public void Show() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).Show();
        }
        System.out.println("Длина = " + length);
    }
}