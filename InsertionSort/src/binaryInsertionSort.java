import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

//201602082 진수연
public class binaryInsertionSort {

    final static String file = "data02.txt";
    static ArrayList<Integer> sort = new ArrayList<Integer>();

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
        binaryInsertionSort(sort); // 삽입 정렬 실행
        stopTime = System.nanoTime();

        // 정렬 결과를 file 쓰기
        try{
            FileWriter writer = new FileWriter("hw01_00_201602082_binary_insertion.txt");
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
        System.out.println("binary insertion sort 실행 시간: "+finalTime);
    }

    // binary 삽입 정렬 구현
    // binary insertion sort -> key 의 자리를 binary search를 이용해서 찾아서 정렬함.
    private static void binaryInsertionSort(ArrayList<Integer> tempSort) {
        for(int i=1; i<tempSort.size(); i++){
            int key = tempSort.get(i); // index i의 값을 임시 기억장소 temp에 저장

            // key의 자리 찾기(binary search 이용)
            int keyLocation = binarySearch(tempSort, 0, i, key); //이진 탐색을 통해 삽입될 위치를 탐색

            // 해당되는 자리에 삽입
            int changeIndex = i;
            while(changeIndex>keyLocation){
                tempSort.set(changeIndex, tempSort.get(changeIndex-1));
                changeIndex--;
            }
            tempSort.set(keyLocation, key);
        }
    }

    // binarySearch 구현
    private static int binarySearch(ArrayList<Integer> tempSort, int low, int high, int findKey){
        int position = (low+high)/2; // 범위 내에서 중간 값

        if(tempSort.get(position) == findKey){ // key를 찾으면 바로 리턴
            return position;
        }

        if(low>=high){ // 이진탐색을 하면서 변경된 low, high 범위가 넘어갈 경우
            if(findKey < tempSort.get(low)){ //찾는 키 값이 low보다도 작으면
                return low; // 제일 왼쪽 인덱스 위치
            }else{ //low보다는 크면
                return low+1; // 왼쪽 인덱스의 옆 위치
            }
        }

        if(findKey < tempSort.get(position)){ // 중간 값이 더 크면
            return binarySearch(tempSort, low, position-1, findKey); // 중간 값의 왼쪽 구간을 이진탐색
        }else{ // 중간 값이 더 작으면
            return binarySearch(tempSort, position+1, high, findKey); // 중간 값의 오른쪽 구간을 이진탐색
        }
    }

}