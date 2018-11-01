
// 201602082 진수연
public class MinHeap {

    private Node[] minHeap;
    private int size = 0;

    public MinHeap(int number) { // 생성자
        minHeap = new Node[number];
    }

    // 전달받은 노드의 값을 부모 노드의 값과 비교하여 알맞은 위치에 삽입한다.
    public void insert(Node newNode) {
        if (this.size() == 0) { // 힙에 노드가 없으면
            this.size++;
            minHeap[this.size()] = newNode;
        } else {
            this.size++;
            int i = this.size();
            int parent = i / 2;
            while ((parent != 0) && (minHeap[parent].getWeight() > newNode.getWeight())) { // 부모 노드의 값과 비교
                minHeap[i] = minHeap[parent];
                i = parent;
                parent = i / 2;
            }
            minHeap[i] = newNode;
        }
    }

    // 왼쪽, 오른쪽 노드와 값을 비교하여 크면 밑으로 내려가며 알맞은 위치에 삽입되어 min heap구조를 유지하게 한다.
    public void MIN_HEAPIFY(int n){
        int left = n*2;
        int right = n*2 + 1;
        int minIndex;

        if( right <= this.size() ){
            if( minHeap[left].getWeight() < minHeap[right].getWeight() ){
                minIndex = left;
            }
            else{
                minIndex = right;
            }
            if( minHeap[n].getWeight() > minHeap[minIndex].getWeight() ){
                Node temp = minHeap[minIndex];
                minHeap[minIndex] = minHeap[n];
                minHeap[n] = temp;
                MIN_HEAPIFY(minIndex);
            }
        }
        else if((left == this.size()) && minHeap[n].getWeight() > minHeap[left].getWeight()){
            Node temp = minHeap[left];
            minHeap[left] = minHeap[n];
            minHeap[n] = temp;
        }
    }

    // heap에 들어있는 가장 작은 값을 가진 노드를 추출한다.
    public Node EXTRACT_MIN(){
        if( this.size() != 0 ){
            Node minNode = minHeap[1]; // 힙의 제일 처음 노드 저장(삭제)
            minHeap[1] = minHeap[this.size()]; // 힙의 가장 마지막 노드를 처음으로 삽입
            this.size--;
            MIN_HEAPIFY(1); // min heap 구축
            return minNode;
        }
        return null;
    }

    // 사이즈를 반환한다.
    public int size() {
        return this.size;
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