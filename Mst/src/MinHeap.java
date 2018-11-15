import java.util.ArrayList;

// 201602082 진수연
public class MinHeap {

    private ArrayList<Node> minHeap;

    public MinHeap() { // 생성자
        minHeap = new ArrayList<Node>();
    }

    // S에 원소 x를 새로 넣는다.
    public void insert(MinHeap S, Node newNode) {
        S.minHeap.add(newNode);
        if (S.minHeap.size() == 1) { // 비교할 값이 없으므로 바로 리턴
            return;
        }

        // 부모 노드 위로 올라가면서 값을 비교 -> 부모 노드의 값보다 더 작은 곳에 위치
        int currentLoc = S.minHeap.size() - 1;
        int parentLoc = (currentLoc - 1) / 2;
        while ((currentLoc > 0) && (S.minHeap.get(currentLoc).getWeight() < S.minHeap.get(parentLoc).getWeight())) { // 부모노드의 값보다 삽입하는 값이 더 작으면
            swap(S, currentLoc, parentLoc);
            currentLoc = parentLoc;
            parentLoc = (currentLoc - 1) / 2; // 서로의 값을 변경 후, 비교 위치도 위로 올라감
        }
    }

    // 왼쪽, 오른쪽 노드와 값을 비교하여 크면 밑으로 내려가며 알맞은 위치에 삽입되어 min heap구조를 유지하게 한다.
    public void MIN_HEAPIFY(MinHeap S, int i, int n) {
        int j = 0;
        while (i < n / 2) {// 내부노드 0~n/2-1 그러므로 리프노드가 아니려면 n/2보단 작아야함.
            // 리프노드가 아닐때라는 조건이 있으므로 무조건 자식은 있다.
            int tempPriority = S.minHeap.get(i).getWeight(); // i인덱스의 weight 값으로 설정

            if (2 * i + 1 < S.minHeap.size() && 2 * i + 2 < S.minHeap.size()) { // 자식 모두 존재
                if (S.minHeap.get(2 * i + 1).getWeight() > S.minHeap.get(2 * i + 2).getWeight()) // 비교해서 j를 작은 자식으로 설정
                    j = 2 * i + 2;
                else
                    j = 2 * i + 1;
            } else if (2 * i + 1 < S.minHeap.size()) { // 왼쪽 자식만 존재할때
                j = 2 * i + 1; // 작은 자식은 왼쪽자식
            } else { // 오른쪽 자식만 존재할때
                j = 2 * i + 2; // 작은 자식은 오른쪽자식
            }

            if (S.minHeap.get(j).getWeight() < tempPriority) {
                swap(S, i, j); // 자리 바꾸기
                MIN_HEAPIFY(S, j, size()); // 자식들도 heapify해야함.
            } else
                break;
        }
    }

    // S에서 키 값이 최소인 원소를 제거한다.
    public Node EXTRACT_MIN(MinHeap S) {
        Node minNode = S.minHeap.get(0);
        int lastIndex = S.size()-1;
        S.minHeap.set(0, S.minHeap.get(lastIndex));
        S.minHeap.remove(S.minHeap.get(lastIndex));

        MIN_HEAPIFY(S, 0, S.size());

        return minNode;
    }

    // 서로의 자리를 변경한다.
    public void swap(MinHeap S, int i, int j) {
        Node temp = S.minHeap.get(i);
        S.minHeap.set(i, S.minHeap.get(j)); // j인덱스의 값을 i인덱스의 자리로 복사
        i = j; // i=j로 설정
        S.minHeap.set(i, temp); // temp를 i인덱스의 자리로 복사
    }

    // 사이즈를 반환한다.
    public int size() {
        return minHeap.size();
    }

    // 비어있는지 확인한다.
    public boolean isEmpty() {
        if(this.size() == 0){
            return true;
        }else{
            return false;
        }
    }

}