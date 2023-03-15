package src.five.a;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Graph {
    private final Integer INF = Integer.MAX_VALUE - 1010010;
    private Integer MAX_VERTS;
    private Vertex[] vertexList;
    private Integer[][] adjMat;      // Матрица смежности
    private Integer countVerts;
    private Stack stack;
    private Queue queue;

    public Graph(Integer max) {
        MAX_VERTS = max;
        vertexList = new Vertex[MAX_VERTS];
        // Матрица смежности
        adjMat = new Integer[MAX_VERTS][MAX_VERTS];
        countVerts = 0;
        for (int j = 0; j < MAX_VERTS; j++)      // Матрица смежности
            for (int k = 0; k < MAX_VERTS; k++)   // заполняется нулями
                adjMat[j][k] = 0;
        stack = new Stack(max);
        queue = new Queue();
    }

    public void addVertex(char name) {
        vertexList[countVerts++] = new Vertex(name);
    }

    public void addEdge(int start, int end) {
        adjMat[start][end] = 1;
        adjMat[end][start] = 1;
    }

    public void displayVertex(int v) {
        System.out.print(vertexList[v].getName());
    }

    private int getAdjUnvisitedVertex(int v) {
        for (int j = 0; j < countVerts; j++)
            if (adjMat[v][j] == 1 && !vertexList[j].isWasVisited())
                return j;               // Возвращает первую найденную вершину
        return -1;                    // Таких вершин нет
    }

    //deep search
    public void DFS() {
        vertexList[0].setWasVisited(true);
        displayVertex(0);
        stack.push(0);
        while (!stack.isEmpty()) {

            int v = getAdjUnvisitedVertex(stack.peek());
            if (v == -1)
                stack.pop();
            else {
                vertexList[v].setWasVisited(true);
                displayVertex(v);
                stack.push(v);
            }
        }
        for (int j = 0; j < countVerts; j++)
            vertexList[j].setWasVisited(false);
    }

    //в ширину
    public void BFS() {
        vertexList[0].setWasVisited(true);
        displayVertex(0);
        queue.insert(0);
        int v2;
        while (!queue.isEmpty()) {
            int v1 = queue.remove();
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1) {
                vertexList[v2].setWasVisited(true);
                displayVertex(v2);
                queue.insert(v2);
            }
        }
        for (int j = 0; j < countVerts; j++)
            vertexList[j].setWasVisited(false);
    }
}

@Data
@NoArgsConstructor
class Stack {
    private Integer SIZE;
    private Integer[] st;
    private Integer top;

    public Stack(Integer size) {
        SIZE = size;
        st = new Integer[SIZE];
        top = -1;
    }

    public void push(Integer j) {
        st[++top] = j;
    }

    public Integer pop() {
        return st[top--];
    }

    public Integer peek() {
        return st[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }
}

@Data
class Queue {
    private final int SIZE = 20;
    private int[] queArray;
    private int front;
    private int rear;

    public Queue() {
        queArray = new int[SIZE];
        front = 0;
        rear = -1;
    }

    public void insert(int j) {
        if (rear == SIZE - 1)
            rear = -1;
        queArray[++rear] = j;
    }

    public int remove() {
        int temp = queArray[front++];
        if (front == SIZE)
            front = 0;
        return temp;
    }

    public boolean isEmpty() {
        return (rear + 1 == front || (front + SIZE - 1 == rear));
    }
}
