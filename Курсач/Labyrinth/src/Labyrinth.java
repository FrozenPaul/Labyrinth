import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Labyrinth {
    private List<List<String>> list = new ArrayList<>();
    private Point Start;
    private Point End;
    private Point SpPoint;

    Labyrinth(String fileName) throws IOException {
        List<String> k = Files.readAllLines(Paths.get(fileName));
        for (int i = 0; i < k.size(); i++) {
            char[] s = k.get(i).toCharArray();
            List<String> l = new ArrayList<>();
            for (int j = 0; j < s.length; j++) {
                l.add(String.valueOf(s[j]));
            }
            list.add(l);
        }
        Show();
        findingStartAndEnd();
    }

    private void findingStartAndEnd() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (i == 0 || j == 0 || i == list.size() - 1 || j == list.get(i).size() - 1)
                    if (Start == null && list.get(i).get(j).equals(" ")) {
                        Start = new Point(i, j);
                    } else if (list.get(i).get(j).equals(" ")) {
                        End = new Point(i, j);
                    }
            }
        }
        Start.Show();
        End.Show();
    }

    public void findAWayOut() {
        List<Point> points = new ArrayList<>();
        points.add(Start);
        Zapoln(points, 0);
        Way bestWay = wayReconstruction();
        System.out.println("Кратчайший путь :");
        bestWay.Show();
        fill1(bestWay);
        Show();
    }

    public void Zapoln(List<Point> points, int i) {
        while (list.get(End.getI()).get(End.getJ()).equals(" ")) {
            if (i == 0) {
                list.get(Start.getI()).remove(Start.getJ());
                list.get(Start.getI()).add(Start.getJ(), "0");
                i++;
            }
            List<Point> secondPoints = new ArrayList<>();
            for (int j = 0; j < points.size(); j++) {
                secondPoints.addAll(CheckAllPoints(points.get(j)));
            }
            for (int j = 0; j < secondPoints.size(); j++) {
                list.get(secondPoints.get(j).getI()).remove(secondPoints.get(j).getJ());
                list.get(secondPoints.get(j).getI()).add(secondPoints.get(j).getJ(), "" + i + "");
            }
            i++;
            points = secondPoints;
        }
    }

    private Way wayReconstruction() {
        Way way = new Way();
        way.add(End);
        SpPoint = End;
        int EndNumber = Integer.parseInt(list.get(End.getI()).get(End.getJ())) - 1;
        for (int i = EndNumber; i >= 0; i--) {
            way.add(findPoint(i));
        }
        return way;
    }

    private Point findPoint(int i) {
        List<Point> points = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            for (int k = 0; k < list.get(j).size(); k++) {
                if (list.get(j).get(k).equals("" + i + "") && h(new Point(j, k), SpPoint) == 1)
                    points.add(new Point(j, k));
            }
        }
        points.sort((o1, o2) -> {
            if (h(o1, Start) < h(o2, Start)) return -1;
            return 1;
        });
        Point point = points.get(0);
        SpPoint = point;
        return point;
    }

    public double h(Point a, Point b) {
        double length = Math.sqrt(Math.pow(b.getI() - a.getI(), 2) + Math.pow(b.getJ() - a.getJ(), 2));
        return length;
    }

    private List<Point> CheckAllPoints(Point start) {
        List<Point> list = new ArrayList<>();
        if (CheckUp(start.getI(), start.getJ())) list.add(new Point(start.getI() - 1, start.getJ()));
        if (CheckDown(start.getI(), start.getJ())) list.add(new Point(start.getI() + 1, start.getJ()));
        if (CheckLeft(start.getI(), start.getJ())) list.add(new Point(start.getI(), start.getJ() - 1));
        if (CheckRight(start.getI(), start.getJ())) list.add(new Point(start.getI(), start.getJ() + 1));
        return list;
    }

    private boolean CheckUp(int i, int j) {
        return (i >= 1)
                && (!list.get(i - 1).get(j).equals("*"))
                && (list.get(i - 1).get(j).equals(" "));
    }

    private boolean CheckDown(int i, int j) {
        return (i <= list.size() - 2)
                && (!list.get(i + 1).get(j).equals("*"))
                && (list.get(i + 1).get(j).equals(" "));
    }

    private boolean CheckLeft(int i, int j) {
        return (j >= 1)
                && (!list.get(i).get(j - 1).equals("*"))
                && (list.get(i).get(j - 1).equals(" "));
    }

    private boolean CheckRight(int i, int j) {
        return (j <= list.get(i).size() - 2)
                && (!list.get(i).get(j + 1).equals("*"))
                && (list.get(i).get(j + 1).equals(" "));
    }

    public void fill1(Way way) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (!list.get(i).get(j).equals("*") && !list.get(i).get(j).equals(" ")) {
                    list.get(i).remove(j);
                    list.get(i).add(j, " ");
                }
            }
        }
        for (int i = 0; i < way.length; i++) {
            list.get(way.getList().get(i).getI()).remove(way.getList().get(i).getJ());
            list.get(way.getList().get(i).getI()).add(way.getList().get(i).getJ(), "1");
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