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
        Zapoln(points, 1);
        Way bestWay = wayReconstruction();
        System.out.println("Кратчайший путь :");
        bestWay.Show();
        fill1(bestWay);
        Show();
    }

    public void Zapoln(List<Point> points, int i) {
        list.get(Start.getI()).set(Start.getJ(), "0");

        while (list.get(End.getI()).get(End.getJ()).equals(" ")) {
            List<Point> secondPoints = new ArrayList<>();
            for (int j = 0; j < points.size(); j++) {
                List<Point> pointList = CheckAllPoints(points.get(j), " ");
                for (int k = 0; k < pointList.size(); k++) {
                    if (!secondPoints.contains(pointList.get(k)))
                        secondPoints.add(pointList.get(k));
                }
            }
            for (int j = 0; j < secondPoints.size(); j++) {
                list.get(secondPoints.get(j).getI()).set(secondPoints.get(j).getJ(), "" + i + "");
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
            way.add(findingPoint(SpPoint, i));
        }
        return way;
    }

    private Point findingPoint(Point start, int number) {
        List<Point> points = CheckAllPoints(start, "" + number + "");
        points.sort((o1, o2) -> {
            if (h(o1, Start) < h(o2, Start)) return -1;
            return 1;
        });
        SpPoint = points.get(0);
        return SpPoint;
    }

    private double h(Point a, Point b) {
        double length = Math.sqrt(Math.pow(b.getI() - a.getI(), 2) + Math.pow(b.getJ() - a.getJ(), 2));
        return length;
    }

    private List<Point> CheckAllPoints(Point start, String s) {
        List<Point> list = new ArrayList<>();
        if (CheckUp(start.getI(), start.getJ(), s)) list.add(new Point(start.getI() - 1, start.getJ()));
        if (CheckDown(start.getI(), start.getJ(), s)) list.add(new Point(start.getI() + 1, start.getJ()));
        if (CheckLeft(start.getI(), start.getJ(), s)) list.add(new Point(start.getI(), start.getJ() - 1));
        if (CheckRight(start.getI(), start.getJ(), s)) list.add(new Point(start.getI(), start.getJ() + 1));
        return list;
    }

    private boolean CheckUp(int i, int j, String s) {
        return (i >= 1)
                && (!list.get(i - 1).get(j).equals("*"))
                && (list.get(i - 1).get(j).equals(s));
    }

    private boolean CheckDown(int i, int j, String s) {
        return (i <= list.size() - 2)
                && (!list.get(i + 1).get(j).equals("*"))
                && (list.get(i + 1).get(j).equals(s));
    }

    private boolean CheckLeft(int i, int j, String s) {
        return (j >= 1)
                && (!list.get(i).get(j - 1).equals("*"))
                && (list.get(i).get(j - 1).equals(s));
    }

    private boolean CheckRight(int i, int j, String s) {
        return (j <= list.get(i).size() - 2)
                && (!list.get(i).get(j + 1).equals("*"))
                && (list.get(i).get(j + 1).equals(s));
    }

    private void fill1(Way way) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                if (!list.get(i).get(j).equals("*") && !list.get(i).get(j).equals(" ")) {
                    list.get(i).set(j, " ");
                }
            }
        }
        for (int i = 0; i < way.length; i++) {
            list.get(way.getList().get(i).getI()).set(way.getList().get(i).getJ(), "1");
        }
    }

    public void Show() {
        System.out.println("Лабиринт :");
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size() - 1; j++) {
                System.out.print(list.get(i).get(j));
            }
            System.out.println(list.get(i).get(list.get(i).size() - 1));
        }
    }
}