import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Labyrinth {
    private List<List<Character>> list = new ArrayList<>();
    private Point Start;
    private Point End;
    private List<Way> FullWays = new ArrayList<>();
    private List<Point> Crosses = new ArrayList<>();
    private final int CrossesCount = 24;

    Labyrinth(String fileName) throws IOException {
        List<String> k = Files.readAllLines(Paths.get(fileName));
        for (int i = 0; i < k.size(); i++) {
            char[] s = k.get(i).toCharArray();
            List<Character> l = new ArrayList<>();
            for (int j = 0; j < s.length; j++) {
                l.add(s[j]);
            }
            list.add(l);
        }
        Show();
        findingStartAndEnd();
        findingCrossing();
    }

    private void findingStartAndEnd() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (i == 0 || j == 0 || i == list.size() - 1 || j == list.get(i).size() - 1)
                    if (Start == null && list.get(i).get(j).equals(' ')) {
                        Start = new Point(i, j);
                    } else if (list.get(i).get(j).equals(' ')) {
                        End = new Point(i, j);
                    }
            }
        }
        Start.Show();
        End.Show();
    }

    private void findingCrossing() {
        for (int i = 1; i < list.size() - 1; i++) {
            for (int j = 1; j < list.get(i).size() - 1; j++) {
                int k = 0;
                if (!list.get(i).get(j).equals('*')) {
                    if (CheckUp(i, j)) k++;
                    if (CheckDown(i, j)) k++;
                    if (CheckLeft(i, j)) k++;
                    if (CheckRight(i, j)) k++;
                    if (k >= 3) Crosses.add(new Point(i, j));
                }
            }
        }
        System.out.println("Перекрестки :");
        for (int i = 0; i < Crosses.size(); i++) {
            Crosses.get(i).Show();
        }
        System.out.println("Кол-во перекрестков = " + Crosses.size());
        ShowCrossings();
    }

    public void findAWayOut() {
        Way bestWay = new Way();
        Obhod(Start, bestWay);
        bestWay = CheckMinimalWay();
        if (Crosses.size() > CrossesCount) System.out.println("Путь :");
         else {
            System.out.println("Различных путей найдено :" + FullWays.size());
            System.out.println("Кратчайший путь :");
        }
        bestWay.Show();
        fill1(bestWay);
        Show();
    }

    public double h(Point a, Point end) {
        double length = Math.sqrt(Math.pow(end.getI() - a.getI(), 2) + Math.pow(end.getJ() - a.getJ(), 2));
        return length;
    }

    public Way Obhod(Point start, Way way) {
        way.add(start);
        if (way.isInWay(End)) {
            FullWays.add(way);
            return way;
        }

        List<Point> points = CheckAllWays(start, way);
        points.sort((o1, o2) -> { if (h(o1, End) < h(o2, End)) return -1;return 1;});

        for (int i = 0; i < points.size(); i++) {
            if (Crosses.size() > CrossesCount) {
                list.get(points.get(i).getI()).remove(points.get(i).getJ());
                list.get(points.get(i).getI()).add(points.get(i).getJ(), '0');
            }
            Obhod(points.get(i), way.clone());
        }

        return new Way();
    }

    private List<Point> CheckAllWays(Point point, Way way) {
        List<Point> list1 = new ArrayList<>();
        if (CheckUp(point.getI(), point.getJ()) && !CheckPointInWay(way, new Point(point.getI() - 1, point.getJ()))) {
            if (!list.get(point.getI() - 1).get(point.getJ()).equals('0'))
                list1.add(new Point(point.getI() - 1, point.getJ()));
        }
        if (CheckDown(point.getI(), point.getJ()) && !CheckPointInWay(way, new Point(point.getI() + 1, point.getJ()))) {
            if (!list.get(point.getI() + 1).get(point.getJ()).equals('0'))
                list1.add(new Point(point.getI() + 1, point.getJ()));
        }
        if (CheckRight(point.getI(), point.getJ()) && !CheckPointInWay(way, new Point(point.getI(), point.getJ() + 1))) {
            if (!list.get(point.getI()).get(point.getJ() + 1).equals('0'))
                list1.add(new Point(point.getI(), point.getJ() + 1));
        }
        if (CheckLeft(point.getI(), point.getJ()) && !CheckPointInWay(way, new Point(point.getI(), point.getJ() - 1))) {
            if (!list.get(point.getI()).get(point.getJ() - 1).equals('0'))
                list1.add(new Point(point.getI(), point.getJ() - 1));
        }
        return list1;
    }

    private Way CheckMinimalWay() {
        Way min = FullWays.get(0);
        for (int i = 1; i < FullWays.size(); i++) {
            if (min.length > FullWays.get(i).length)
                min = FullWays.get(i);
        }
        return min;
    }

    private boolean CheckUp(int i, int j) {
        return (i >= 1)
                && (!list.get(i - 1).get(j).equals('*'));
    }

    private boolean CheckDown(int i, int j) {
        return (i <= list.size() - 2)
                && (!list.get(i + 1).get(j).equals('*'));
    }

    private boolean CheckLeft(int i, int j) {
        return (j >= 1)
                && (!list.get(i).get(j - 1).equals('*'));
    }

    private boolean CheckRight(int i, int j) {
        return (j <= list.get(i).size() - 2)
                && (!list.get(i).get(j + 1).equals('*'));
    }

    private static boolean CheckPointInWay(Way way, Point point) {
        return (way.isInWay(point));
    }

    public void fill1(Way way) {
        for (int i = 0; i < way.length; i++) {
            list.get(way.getList().get(i).getI()).remove(way.getList().get(i).getJ());
            list.get(way.getList().get(i).getI()).add(way.getList().get(i).getJ(), '1');
        }
    }

    private void ShowCrossings() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size() - 1; j++) {
                if (new Point(i, j).isInList(Crosses))
                    System.out.print('+');
                else
                    System.out.print(list.get(i).get(j));
            }
            if (new Point(i, list.get(i).size() - 1).isInList(Crosses))
                System.out.println('+');
            else
                System.out.println(list.get(i).get(list.get(i).size() - 1));
        }
    }

    public void Show() {
        System.out.println("Лабиринт :");
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size() - 1; j++) {
                if (list.get(i).get(j).equals('0')) {
                    System.out.print(' ');
                } else
                    System.out.print(list.get(i).get(j));
            }
            System.out.println(list.get(i).get(list.get(i).size() - 1));
        }
    }
}