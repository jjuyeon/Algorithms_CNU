// 201602082 진수연
public class Node{
    private char vertex;
    private int weight;

    public Node(char vertex, int weight){
        this.vertex = vertex;
        this.weight = weight;
    }

    public void setVertex(char vertex){
        this.vertex = vertex;
    }

    public char getVertex(){
        return this.vertex;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getWeight(){
        return this.weight;
    }
}