package src.five.a;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ogbozoyan
 * @date 13.03.2023
 */
@Data
@NoArgsConstructor
public class Vertex {

    private char name;
    private boolean wasVisited;

    public Vertex(char lab) {
        name = lab;
        wasVisited = false;
    }
}

