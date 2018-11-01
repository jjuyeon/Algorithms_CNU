
// 201602082 진수연
public class Dijkstra {
    static int path[][];
    static boolean visit[];
    static int dist[];
    static MinHeap minHeap;

    public static void main(String[] args){
        int adjacencyMatrix[][] = new int[][]{
                {0, 10, 3, 0, 0},
                {0, 0, 1, 2, 0},
                {0, 4, 0, 8, 2},
                {0, 0, 0, 0, 7},
                {0, 0, 0, 9, 0}};

        System.out.println("dijkstra's algorithm으로 계산한 결과는 다음과 같습니다.");

        int startVertex = 0;
        Dijkstra dijkstra = new Dijkstra(adjacencyMatrix);
        dijkstra.calShortestPath(adjacencyMatrix, startVertex);
    }

    public Dijkstra(int[][] adjacencyMatrix){ // 생성자
        path = new int[adjacencyMatrix.length][adjacencyMatrix[0].length];
        visit = new boolean[adjacencyMatrix.length];
        dist = new int[2*adjacencyMatrix.length-1];
        minHeap = new MinHeap(2*adjacencyMatrix.length);
    }

    // Dijkstra Algorithm 실행한다.
    private static void calShortestPath(int[][] matrix, int start){
        // path matrix 초기화
        for(int i=0; i<path.length; i++){
            for(int j=0; j<path[i].length; j++){
                if(matrix[i][j] == 0){
                    path[i][j] = Integer.MAX_VALUE;
                }else{
                    path[i][j] = matrix[i][j];
                }
            }
        }
        // visit 초기화
        for (int i = 0; i< visit.length; i++) {
            visit[i] = false;
        }
        // dist 초기화
        for( int i = 0 ; i < dist.length; i++ ){
            dist[i] = Integer.MAX_VALUE;
        }

        dist[start] = 0;
        Node vertex = new Node((char)(65+start), 0, start);
        minHeap.insert(vertex);
        visit[start] = true;

        //시작 vertex 초기화
        Node visitVertex = minHeap.EXTRACT_MIN();
        int s_index = visitVertex.getIndex(); // S 인덱스
        int q_index = 0; // Q 인덱스

        System.out.println("\n=============================================");
        System.out.println("S["+visitVertex.getIndex()+"] : d["+visitVertex.getVertex()+"] = "+visitVertex.getWeight());
        s_index++;
        System.out.println("---------------------------------------------");

        do{
            visit[visitVertex.getIndex()] = true;
            int edgeCost = -1;
            int newCost = -1;
            for(int i = 0 ; i < visit.length ; i++){
                if(!visit[i]){
                    System.out.print("Q[" + q_index + "] : d[" + (char)(65+i) + "] = " + dist[i]);
                    q_index++;
                } // 현재 방문하고있는 vertex와 방문하지 않은 vertex들 간의 거리 출력
                if(path[visitVertex.getIndex()][i] != Integer.MAX_VALUE){ // 이웃하면
                    edgeCost = path[visitVertex.getIndex()][i];
                    newCost = dist[visitVertex.getIndex()] + edgeCost;
                    if(newCost < dist[i]){ // 방문하여 거처 가는 거리가 시작지점에서 직결로 가는 거리보다 작으면
                        dist[i] = newCost;
                        System.out.println(" -> d[" + (char)(65+i) + "] = " + dist[i]);
                    }
                    minHeap.insert(new Node((char)(65+i), dist[i], i)); // 이웃한 노드를 스택에 삽입
                }
                else if(!visit[i]){
                    System.out.println();
                }
            }
            visitVertex = minHeap.EXTRACT_MIN(); // 스택 제일 위의 원소 추출
            while(visit[visitVertex.getIndex()] && !minHeap.isEmpty()){ // 추출된 원소의 vertexr가 이미 방문된 vertex이면
                visitVertex = minHeap.EXTRACT_MIN();
            }
            if(s_index < visit.length){
                System.out.println("\n\n=============================================");
                System.out.println("S[" + s_index + "] : d[" + visitVertex.getVertex() + "] = " + visitVertex.getWeight());
                System.out.println("---------------------------------------------");
            }
            s_index++;
            q_index = 0;
        }while(!minHeap.isEmpty());
    }

}
