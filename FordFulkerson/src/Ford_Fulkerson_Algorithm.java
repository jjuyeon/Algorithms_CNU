import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 201602082 진수연
public class Ford_Fulkerson_Algorithm {
    private static final int INF = Integer.MAX_VALUE;
    private static int MAX_V = 6; // 사용자 입력 받기 전 임의 설정
    private static int flow[][];
    private static int capacity[][];

    public static void main(String args[]){
        capacity = new int[][] {{0,12,0,3,0,0},
                                {0,0,10,0,0,0},
                                {0,0,0,0,3,15},
                                {0,11,0,0,5,0},
                                {0,0,0,0,0,17},
                                {0,0,0,0,0,0}
        }; // 각 간선에 흐를 수 있는 최대 용량
        flow = new int[MAX_V][MAX_V]; // 각 간선에 흐르는 실제 유량

        int totalFlow = networkFlow(0,5);

        System.out.println("유량 네트워크 전체의 최대 유량 : "+totalFlow);
    }

    // 각 간선에 흐르는 유량을 구하여 최대 유량을 구한다.
    private static int networkFlow(int source, int sink){
        int totalFlow = 0;

        while(true){
            int parent[] = new int[MAX_V];
            Arrays.fill(parent,-1); // -1로 초기화
            Queue<Integer> q = new LinkedList<Integer>();
            parent[source] = source;
            q.add(source);

            while(!q.isEmpty() && parent[sink] == -1) {
                int here = q.remove();
                for (int there = 0; there < MAX_V; ++there) {
                    if (capacity[here][there] - flow[here][there] > 0 && parent[there] == -1) {
                        q.add(there);
                        parent[there] = here;
                    } // here에서 there까지 보낼 수 있는 잔여 유량이 남아있고, there의 parent가 정해지지 않았을 때 업데이트
                } // 큐가 비어있지 않고 sink의 parent가 정해지지 않았을 때까지 반복한다.
            }

            if(parent[sink] == -1){ // 종료조건(모든 경로를 다 찾았을 때)
                break;
            }

            StringBuilder path = new StringBuilder();
            int amount = INF;
            for(int p=sink; p!=source; p=parent[p]){ // 유량 계산
                amount = Math.min(capacity[parent[p]][p] - flow[parent[p]][p], amount);
            }

            for(int p=sink; p!=source; p=parent[p]){ // 유량 계산
                path.insert(0, parent[p] + " - "); // 경로 저장
                flow[parent[p]][p] += amount;
                flow[p][parent[p]] -= amount;
            }
            path.append(sink);

            System.out.print("경로 : "+path);
            System.out.println(" / 최대 유량: "+amount);
            totalFlow += amount; // 전체 유량 저장
        }
        return totalFlow;
    }
}