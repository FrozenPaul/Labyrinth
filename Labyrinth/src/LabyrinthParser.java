import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LabyrinthParser {

    LabyrinthParser(){}

    public int[][] parse(String fileName) throws IOException {
        List<String> k = Files.readAllLines(Paths.get(fileName));
        int[][] mas = new int[k.size()][];
        for (int i = 0; i < mas.length; i++) {
            char[] c = k.get(i).toCharArray();
            mas[i] = new int[c.length];
            for (int j = 0; j < mas[i].length; j++) {
                if (String.valueOf(c[j]).equals("*"))
                    mas[i][j] = -1;
            }
        }
        return mas;
    }

    public Point[] findingStartAndEnd(int[][] list) {
        Point[] points = new Point[2];
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                if (i == 0 || j == 0 || i == list.length - 1 || j == list[i].length - 1)
                    if (points[0] == null && list[i][j] == 0) {
                        points[0] = new Point(i, j);
                    } else if (list[i][j] == 0) {
                        points[1] = new Point(i, j);
                    }
            }
        }
        return points;
    }

    public int[][] fillTheWayOut(int[][] mas, Way way){
        int[][] list = mas.clone();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                if (list[i][j] != -1 && list[i][j] != 0) {
                    list[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < way.getList().size(); i++) {
            list[way.getList().get(i).getI()][way.getList().get(i).getJ()] = 1;
        }
        return list;
    }

}
