import java.util.ArrayList;
import java.util.List;

public class Way {

    private List<Point> list = new ArrayList<>();
    public int length = 0;

    Way(){}

    public List<Point> getList() {
        return list;
    }

    public void add(Point a){
        list.add(a);
        length++;
    }

    public Way clone(){
        Way result = new Way();
        for (int i = 0; i < list.size(); i++) {
            result.list.add(list.get(i));
        }
        result.length = result.list.size();
        return result;
    }

    public boolean isInWay(Point a){
        for (int i = 0; i < list.size(); i++) {
            if (a.getI() == list.get(i).getI() && a.getJ() == list.get(i).getJ())
                return true;
        }
        return false;
    }

    public void Show(){
        for (int i = 0; i < list.size(); i++) {
            list.get(i).Show();
        }
        System.out.println("Длина = "+ length);
    }
}
