import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

//201602082 진수연
public class threeWayMergeSort {

    final static String file = "data02.txt";
    static ArrayList<Integer> sort = new ArrayList<Integer>();
    static int mergeCount = 0;
    public static void main(String args[]) {
        long startTime;
        long stopTime;

        // 정렬되지 않은 file 읽어오기
        try{
            BufferedReader in = new BufferedReader(new FileReader(file)); // data02.txt불러옴
            String line = in.readLine(); // 한줄을 입력받아 읽음
            while (line != null) { // null이 아닐때까지 (파일의 끝까지 읽기)
                StringTokenizer parser = new StringTokenizer(line, ",");
                while(parser.hasMoreTokens()){
                    int num = Integer.parseInt(parser.nextToken()); //숫자를 받아옴
                    sort.add(num); //받아온 숫자를 int배열에 저장
                }
                line = in.readLine(); // 한줄을 입력받아 읽음
            }
            in.close(); // 파일 닫기
        } catch(FileNotFoundException e){ // 예외처리
            System.err.println(e);
            System.exit(1);
        } catch(IOException e){
            System.err.println(e);
            System.exit(1);
        }

        startTime = System.nanoTime();
        // 합병 정렬 실행
        threeWayMergeSort(sort,0, sort.size()-1); // 인덱스 범위를 인자로 함
        stopTime = System.nanoTime();

        // 정렬 결과를 file 쓰기
        try{
            FileWriter writer = new FileWriter("hw01_00_201602082_3way_merge.txt");
            for( int i = 0 ; i < sort.size()-1 ; i++ ){
                writer.write(String.valueOf(sort.get(i)) + ",");
            }
            writer.write(String.valueOf(sort.get(sort.size()-1)));

            writer.close();
        } catch(FileNotFoundException e){ // 예외처리
            System.err.println(e);
            System.exit(1);
        } catch(IOException e){
            System.err.println(e);
            System.exit(1);
        }

        // 실행 시간 출력
        long finalTime = stopTime-startTime;
        System.out.println("three way merge sort 실행 시간: "+finalTime);
        // 파일에 기록하지 않았으므로 출력해서 확인
        //System.out.println("merge() 함수 실행 횟수: "+mergeCount);
        }

    // 3-way 합병 정렬 구현 (recursive function으로 구현되어야 한다) : 3개의 서로 다른 데이터 그룹을 하나로 합치는 합병 정렬

    // 분할 : 배열의 크기가 1이 될 때까지 계속해서 배열을 셋으로 나눈다 (recursive function으로 구현되어야 한다) - 실습pdf 참고
    public static void threeWayMergeSort(ArrayList<Integer> tempSort, int p, int r){
        if((r-p+1)==3){ //만약 배열의 크기가 3이라면
            merge(tempSort, p, p,p+1, r); // 하나의 원소만을 가지는 배열이 3개가 되므로
            return;
        }
        // 배열의 크기가 1이 될 때까지 계속하여 배열을 셋으로 나눈다
        if((r-p)<1){ // 배열의 크기가 1이 되면 끝냄
            return;
        }
        int size = (r-p)+1; // 배열의 크기
        int q1 = p + size/3; // 3등분 중 중간 배열의 처음 인덱스
        int q2 = p + 2*(size/3); // 3등분 중 중간 배열의 마지막 인덱스

        threeWayMergeSort(tempSort, p, q1); // 왼쪽 위치 배열
        threeWayMergeSort(tempSort,q1+1, q2); // 중앙 위치 배열
        threeWayMergeSort(tempSort, q2+1, r); // 오른쪽 위치 배열

        merge(tempSort, p, q1, q2, r);
    }

    // 정복 : 나눠진 데이터를 2개 배열씩 비교하여 재귀적으로 정렬한다
    // 결합 : 정렬된 두 개의 배열을 병합해 하나의 정렬된 배열로 만든다
    public static void merge(ArrayList<Integer> tempSort, int p, int q1, int q2, int r) {
        mergeCount++; // merge함수를 호출했으므로 +1해줌
        ArrayList<Integer> resultSort = new ArrayList<Integer>();
        int leftIndex = p;
        int midIndex = q1+1;
        int rightIndex = q2+1;

        // 나눠진 3개의 배열을 비교하여 정렬하는 단계

        // 3가지 범위 다같이 비교(가장 작은 범위들끼리 비교)
        while(leftIndex<=q1 && midIndex<=q2 && rightIndex<=r){
            // leftIndex 값과 midIndex 값 비교 -> 작은 값과 rightIndex 값 비교
            if(tempSort.get(leftIndex) <= tempSort.get(midIndex)){
                // leftIndex 값이 midIndex보다 작으므로 rightIndex 값과 leftIndex 값 비교
                boolean leftIndexSave = compare(tempSort, leftIndex, rightIndex, resultSort);
                if(leftIndexSave)
                    leftIndex++; // leftIndex에 위치하는 key값을 저장하였으므로 인덱스 +1 해줌
                else
                    rightIndex++; // rightIndex에 위치하는 key값을 저장하였으므로 인덱스 +1 해줌
            }
            else{
                boolean midIndexSave = compare(tempSort, midIndex, rightIndex, resultSort);
                if(midIndexSave)
                    midIndex++;
                else
                    rightIndex++;
            }
        }

        // 3가지 범위에서 2개의 범위들끼리 비교
        while(leftIndex<=q1 && midIndex <=q2){ // leftIndex 값과 midIndex 값 비교
            boolean leftIndexSave = compare(tempSort, leftIndex, midIndex, resultSort);
            if(leftIndexSave)
                leftIndex++;
            else
                midIndex++;
        }

        while(leftIndex<=q1 && rightIndex <=r){ // leftIndex 값과 rightIndex 값 비교
            boolean leftIndexSave = compare(tempSort, leftIndex, rightIndex, resultSort);
            if(leftIndexSave)
                leftIndex++;
            else
                rightIndex++;
        }

        while(midIndex<=q2 && rightIndex <=r){ // midIndex 값과 rightIndex 값 비교
            boolean midIndexSave = compare(tempSort, midIndex, rightIndex, resultSort);
            if(midIndexSave)
                midIndex++;
            else
                rightIndex++;
        }

        // 비교하고 남은 부분 저장
        for(int i = leftIndex; i<=q1; i++) {
            resultSort.add(tempSort.get(leftIndex));
            leftIndex++;
        }
        for(int i = midIndex; i<=q2; i++) {
            resultSort.add(tempSort.get(midIndex));
            midIndex++;
        }
        for(int i = rightIndex; i<=r; i++) {
            resultSort.add(tempSort.get(rightIndex));
            rightIndex++;
        }

        int count=0;
        //정렬된 부분은 원래 arrayList에 저장
        for(int i=p; i<=r; i++){
            tempSort.set(i, resultSort.get(count++));
        }
    }

    // 2개의 key 값을 비교하는 메서드
    public static boolean compare(ArrayList<Integer> compareSort, int compare1, int compare2, ArrayList<Integer> saveSort){
        boolean compare1Save = false;
        if(compareSort.get(compare1) <= compareSort.get(compare2)){ // compare1 값이 작을 때
            saveSort.add(compareSort.get(compare1)); // compare1 값을 저장
            compare1Save = true;
        }else{ // 값이 더 크면
            saveSort.add(compareSort.get(compare2)); // compare2 값을 저장
        }
        return compare1Save;
    }

}