import java.util.ArrayList;
import java.util.List;

public class Way {

    private List<Point> list = new ArrayList<>();

    Way() {}

    public List<Point> getList() {
        return list;
    }

    public void add(Point a) {
        list.add(a);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}