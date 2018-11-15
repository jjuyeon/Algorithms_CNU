// 201602082 진수연
public class Prim {

    private int path[][];
    private MinHeap heap;
    private int key[];
    private char π[];
    private boolean visit[];
    private static int cost = 0;

    public static void main(String[] args){
        int adjacencyMatrix[][] = new int[][]{
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}};

        int startVertex = 0;
        Prim prim = new Prim(adjacencyMatrix);
        prim.calShortestPath(adjacencyMatrix, startVertex);

        System.out.println("\nw<MST> = "+cost); // 총 비용 출력
    }

    Prim(int[][] adjacencyMatrix){ // 생성자
        path = new int[adjacencyMatrix.length][adjacencyMatrix.length];
        heap = new MinHeap();
        visit = new boolean[adjacencyMatrix.length];
        key = new int[adjacencyMatrix.length];
        π = new char[adjacencyMatrix.length];
    }

    // Prim's Algorithm을 실행한다.
    public void calShortestPath(int[][] matrix, int start){
        // path matrix 초기화
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                if((matrix[i][j] == 0) && (i!=j)){
                    path[i][j] = Integer.MAX_VALUE;
                }else{
                    path[i][j] = matrix[i][j];
                }
            }
        }
        // 큐에 vertex들을 추가
        for(int v=0; v<matrix.length; v++){
            Node temp = new Node((char)('a'+v), path[start][v]);
            heap.insert(heap,temp);
            π[v] = ' '; // 부모 vertex 초기화
        }
        // key 초기화
        for(int i=0; i<matrix.length; i++){
            key[i] = Integer.MAX_VALUE;
        }
        // 시작 vertex만 0으로 초기화
        key[start] = 0;

        while(!heap.isEmpty()){
            Node u = heap.EXTRACT_MIN(heap);

            while(visit[u.getVertex()-97] && !heap.isEmpty() ){ // 이미 방문한 vertex이면
                u = heap.EXTRACT_MIN(heap); // 계속 추출
            }

            if(!visit[u.getVertex()-97]){ // 방문하지 않은 vertex라면
                System.out.println("w<" + π[u.getVertex()-97] + "," + u.getVertex() + "> = " + u.getWeight());
                cost += u.getWeight();
            }

            visit[u.getVertex()-97] = true; // 방문한 vertex 임을 체크해준다

            for(int v=0; v<matrix.length; v++){
                if((path[u.getVertex()-97][v] < key[v]) && (!visit[v])){ // 방문한 vertex에 이어진 거리들 < 이전에 설정된 거리 & 사이클 생성x
                    key[v] = path[u.getVertex()-97][v]; // 거리(key) 업데이트
                    π[v] = u.getVertex(); // 부모 vertex 업데이트
                    Node newNode = new Node((char)('a'+v), key[v]);
                    heap.insert(heap, newNode); // min heap에 삽입
                }
            }
        }

    }
}
