import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

//201602082 진수연
public class insertionSort {

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
        insertionSort(sort, sort.size()); // 삽입 정렬 실행
        stopTime = System.nanoTime();

        // 정렬 결과를 file 쓰기
        try{
            FileWriter writer = new FileWriter("hw01_00_201602082_insertion.txt");
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
        System.out.println("insertion sort 실행 시간: "+finalTime);
    }

    // 삽입 정렬 구현 , index(i)=1에서 size-1까지 올라가면서 정렬
    private static void insertionSort(ArrayList<Integer> tempSort, int size) {
        for(int i=1; i<size; i++){
            int temp = tempSort.get(i); // index i의 값을 임시 기억장소 temp에 저장
            int tempLocation = i;

            // temp 값을 좌측 배열에 저장된 수 중 가장 마지막(오른쪽)배열에 저장된 값과 비교 && index범위 제한
            while(tempSort.get(tempLocation-1) > temp && tempLocation > 0){ // temp의 값이 더 작을 때
                tempSort.set(tempLocation, tempSort.get(tempLocation-1)); // 비교한 배열의 값을 오른쪽 인덱스에 저장
                tempLocation--; // 계속 왼쪽 값과 비교하기 위함
            }

            // 비교가 종료되면
            tempSort.set(tempLocation, temp); // 비교했던 위치의 바로 오른쪽 인덱스에 temp 값 저장
        }
    }

}