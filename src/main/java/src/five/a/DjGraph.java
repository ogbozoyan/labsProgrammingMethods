package src.five.a;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static java.lang.Math.min;
import static java.util.Arrays.fill;

/**
 * @author ogbozoyan
 * @date 15.03.2023
 */
@Data
public class DjGraph {
    private final Integer MAX_VERTS = 20;
    private final Integer INFINITY = Integer.MAX_VALUE - 1000;
    private DjVertex[] vertexList;
    private Integer[][] adjMat;
    private Integer nVerts;
    private Integer nTree;
    private DistPar[] sPath;
    private Integer currentVert;
    private Integer startToCurrent;

    public DjGraph() {
        vertexList = new DjVertex[MAX_VERTS];
        // Матрица смежности
        adjMat = new Integer[MAX_VERTS][MAX_VERTS];
        nVerts = 0;
        nTree = 0;
        for (Integer j = 0; j < MAX_VERTS; j++) {    // Матрица смежности
            for (Integer k = 0; k < MAX_VERTS; k++) {
                adjMat[j][k] = INFINITY;
            }
        }
        sPath = new DistPar[MAX_VERTS];
    }

    public void addVertex(Character lab) {
        vertexList[nVerts++] = new DjVertex(lab);
    }

    public void addEdge(Integer start, Integer end, Integer weight) {
        adjMat[start][end] = weight;
    }

    public void path() {
        Integer startTree = 0;
        vertexList[startTree].setInTree(true);
        nTree = 1;
        for (Integer j = 0; j < nVerts; j++) {
            Integer tempDist = adjMat[startTree][j];
            sPath[j] = new DistPar(startTree, tempDist);
        }
        while (nTree < nVerts) {
            Integer indexMin = getMin();
            Integer minDist = sPath[indexMin].getDistance();
            if (Objects.equals(minDist, INFINITY)) {
                System.out.println("Недостежимые вершины");
                break;
            } else {                        // Возврат currentVert
                currentVert = indexMin;  // к ближайшей вершине
                startToCurrent = sPath[indexMin].getDistance();
                // Минимальное расстояние от startTree
                // до currentVert равно startToCurrent
            }
            vertexList[currentVert].setInTree(true);
            nTree++;
            adjust_sPath();
        }
        displayPaths();
        nTree = 0;
        for (Integer j = 0; j < nVerts; j++)
            vertexList[j].setInTree(false);
    }

    public Integer getMin() {
        Integer minDist = Integer.MAX_VALUE;
        Integer indexMin = 0;
        for (Integer j = 1; j < nVerts; j++) {
            if (!vertexList[j].isInTree() &&
                    sPath[j].getDistance() < minDist)
                minDist = sPath[j].getDistance();
            {
                indexMin = j;
            }
        }
        return indexMin;
    }

    public void adjust_sPath() {
        // Обновление данных в массиве кратчайших путей sPath
        Integer column = 1;                // Начальная вершина пропускается
        while (column < nVerts)         // Перебор столбцов
        {
            // Если вершина column уже включена в дерево, она пропускается
            if (vertexList[column].isInTree()) {
                column++;
                continue;
            }
            // Вычисление расстояния для одного элемента sPath
            // Получение ребра от currentVert к column
            Integer currentToFringe = adjMat[currentVert][column];
            // Суммирование расстояний
            Integer startToFringe = startToCurrent + currentToFringe;
            // Определение расстояния текущего элемента sPath
            Integer sPathDist = sPath[column].getDistance();
            // Сравнение расстояния от начальной вершины с элементом sPath
            if (startToFringe < sPathDist)   // Если меньше,
            {                            // данные sPath обновляются
                sPath[column].setParentVert(currentVert);
                sPath[column].setDistance(startToFringe);
            }
            column++;
        }
    }

    public void displayPaths() {
        for (Integer j = 0; j < nVerts; j++) // display contents of sPath[]
        {
            System.out.print(vertexList[j].getName() + "=");
            if (Objects.equals(sPath[j].getDistance(), INFINITY))
                System.out.print("inf");
            else
                System.out.print(sPath[j].getDistance());
            char parent = vertexList[sPath[j].getParentVert()].getName();
            System.out.print("(" + parent + ") ");
        }
        System.out.println("");
    }

    public Integer[][] floydWarshall() {
        Integer[][] dist = new Integer[nVerts][nVerts]; // dist[i][j] = минимальное_расстояние(i, j)
        for (int i = 0; i < nVerts; i++) System.arraycopy(adjMat[i], 0, dist[i], 0, nVerts);
        for (int k = 0; k < nVerts; k++)
            for (int i = 0; i < nVerts; i++)
                for (int j = 0; j < nVerts; j++)
                    dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]);
        return dist;
    }


}

@Data
@NoArgsConstructor
class DjVertex {
    private Character name;
    private boolean isInTree;

    public DjVertex(Character lab) {
        name = lab;
        isInTree = false;
    }

}

@Data
@NoArgsConstructor
class DistPar {
    // Класс хранит растояния между точками
    private Integer distance;
    private Integer parentVert;

    public DistPar(Integer pv, Integer d) {
        distance = d;
        parentVert = pv;
    }
}
