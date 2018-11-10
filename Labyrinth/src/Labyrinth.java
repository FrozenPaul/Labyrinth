import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Labyrinth {
    private int[][] list;
    private Point Start;
    private Point End;
    private Point SpPoint;

    Labyrinth(String fileName) throws IOException {
        LabyrinthParser labyrinthParser = new LabyrinthParser();
        list = labyrinthParser.parse(fileName);
        Point[] StartAndEnd = labyrinthParser.findingStartAndEnd(list);
        Start = StartAndEnd[0];
        End = StartAndEnd[1];
        System.out.println(this.toString());
        System.out.println(Start);
        System.out.println(End);
    }

    public void findAWayOut() {
        List<Point> points = new ArrayList<>();
        points.add(Start);
        Zapoln(points, 2);
        Way bestWay = wayReconstruction();
        System.out.println("Кратчайший путь :");
        System.out.println(bestWay);
        fill1(bestWay);
        System.out.println(this.toString());
    }

    public void Zapoln(List<Point> points, int i) {
        list[Start.getI()][Start.getJ()] = 1;

        while (list[End.getI()][End.getJ()] == 0) {
            List<Point> secondPoints = new ArrayList<>();
            for (int j = 0; j < points.size(); j++) {
                List<Point> pointList = CheckAllPoints(points.get(j), 0);
                for (int k = 0; k < pointList.size(); k++) {
                    if (!secondPoints.contains(pointList.get(k)))
                        secondPoints.add(pointList.get(k));
                }
            }
            for (int j = 0; j < secondPoints.size(); j++) {
                list[secondPoints.get(j).getI()][secondPoints.get(j).getJ()] = i;
            }
            i++;
            points = secondPoints;
        }
    }

    private Way wayReconstruction() {
        Way way = new Way();
        way.add(End);
        SpPoint = End;
        int EndNumber = list[End.getI()][End.getJ()] - 1;
        for (int i = EndNumber; i >= 1; i--) {
            way.add(findingPoint(SpPoint, i));
        }
        return way;
    }

    private Point findingPoint(Point start, int number) {
        List<Point> points = CheckAllPoints(start, number);
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

    private List<Point> CheckAllPoints(Point start, int s) {
        List<Point> list = new ArrayList<>();
        if (CheckUp(start.getI(), start.getJ(), s)) list.add(new Point(start.getI() - 1, start.getJ()));
        if (CheckDown(start.getI(), start.getJ(), s)) list.add(new Point(start.getI() + 1, start.getJ()));
        if (CheckLeft(start.getI(), start.getJ(), s)) list.add(new Point(start.getI(), start.getJ() - 1));
        if (CheckRight(start.getI(), start.getJ(), s)) list.add(new Point(start.getI(), start.getJ() + 1));
        return list;
    }

    private boolean CheckUp(int i, int j, int s) {
        return (i >= 1)
                && (list[i - 1][j] != -1)
                && (list[i - 1][j] == s);
    }

    private boolean CheckDown(int i, int j, int s) {
        return (i <= list.length - 2)
                && (list[i + 1][j] != -1)
                && (list[i + 1][j] == s);
    }

    private boolean CheckLeft(int i, int j, int s) {
        return (j >= 1)
                && (list[i][j - 1] != -1)
                && (list[i][j - 1] == s);
    }

    private boolean CheckRight(int i, int j, int s) {
        return (j <= list[i].length - 2)
                && (list[i][j + 1] != -1)
                && (list[i][j + 1] == s);
    }

    private void fill1(Way way) {
        LabyrinthParser parser = new LabyrinthParser();
        list = parser.fillTheWayOut(list,way);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                switch (list[i][j]) {
                    case -1:
                        s.append("*");
                        break;
                    case 0:
                        s.append(" ");
                        break;
                    case 1:
                        s.append("1");
                        break;
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
}