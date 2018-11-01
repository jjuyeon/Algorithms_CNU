
// 201602082 진수연
public class Node{
    private char vertex;
    private int weight;
    private int index;

    public Node(char vertex, int weight, int index){
        this.vertex = vertex;
        this.weight = weight;
        this.index = index;
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

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }
}
