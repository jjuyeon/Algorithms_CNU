import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

//201602082 진수연
public class mergeSort {

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
        mergeSort(sort,0, sort.size()-1); // 인덱스 범위를 인자로 함
        stopTime = System.nanoTime();

        // 정렬 결과를 file 쓰기
        try{
            FileWriter writer = new FileWriter("hw01_00_201602082_merge.txt");
            for( int i = 0 ; i < sort.size() ; i++ ){
                writer.write(String.valueOf(sort.get(i)) + ",");
            }
            //merge 함수가 사용된 횟수를 file로 기록
            writer.write(String.valueOf(mergeCount));

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
        System.out.println("merge sort 실행 시간: "+finalTime);
    }

    // 합병 정렬 구현 (recursive function으로 구현되어야 한다)

    // 분할 : 배열의 크기가 1이 될 때까지 계속해서 배열을 둘로 나눈다 (recursive function으로 구현되어야 한다) - 실습pdf 참고
    public static void mergeSort(ArrayList<Integer> tempSort, int p, int r){
        // 배열의 크기가 1이 될 때까지 계속하여 배열을 둘로 나눈다
        if((r-p)<1){ // 배열의 크기가 1이 되면 끝냄
            return;
        }
        int q = (p+r)/2; // 가운데 인덱스 넘버 설정

        mergeSort(tempSort, p, q); // 가운데 인덱스를 기준으로 왼쪽 위치 배열
        mergeSort(tempSort,q+1, r); // 가운데 인덱스를 기준으로 오른쪽 위치 배열

        merge(tempSort,p, r);
    }

    // 정복 : 나눠진 데이터를 2개 배열씩 비교하여 재귀적으로 정렬한다
    // 결합 : 정렬된 두 개의 배열을 병합해 하나의 정렬된 배열로 만든다
    public static void merge(ArrayList<Integer> tempSort, int p, int r){
        mergeCount++; // merge함수를 호출했으므로 +1해줌

        ArrayList<Integer> resultSort = new ArrayList<Integer>();
        int mid = (p+r)/2;
        int leftIndex = p;
        int rightIndex = mid+1;

        // 나눠진 2개의 배열을 비교하여 정렬하는 단계
        do{
            if(tempSort.get(leftIndex) <= tempSort.get(rightIndex)){
                resultSort.add(tempSort.get(leftIndex));
                leftIndex++; // leftIndex에 위치하는 key값을 저장하였으므로 인덱스 +1 해줌
            }else{
                resultSort.add(tempSort.get(rightIndex));
                rightIndex++; // rightIndex에 위치하는 key값을 저장하였으므로 인덱스 +1 해줌
            }
        }while(leftIndex<=mid && rightIndex<=r);

        // 비교하고 남은 부분 저장
        for(int i = leftIndex; i<=mid; i++) {
            resultSort.add(tempSort.get(leftIndex));
            leftIndex++;
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

}