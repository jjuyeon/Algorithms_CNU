import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 201602082 진수연
public class quick {

    final static String file = "data04.txt";
    static ArrayList<Integer> sort;

    public static void main(String args[]) {
        long startTime;
        long stopTime;

        // 기본적인 Partition 알고리즘을 이용한 Quick Sort 실행
        readFile();
        startTime = System.nanoTime();
        quickSort(sort,0, sort.size()-1);
        stopTime = System.nanoTime();
        writeFile("hw03_00_201602082_quick.txt");
        //long finalTime = stopTime - startTime; // 실행 시간 출력
        //System.out.println("quick sort 실행 시간: " + finalTime);


        // 랜덤 pivot 값을 이용한 Quick Sort 실행
        readFile();
        startTime = System.nanoTime();
        quickSort_withRandom(sort,0, sort.size()-1);
        stopTime = System.nanoTime();
        writeFile("hw03_00_201602082_quickRandom.txt");
        //finalTime = stopTime - startTime; // 실행 시간 출력
        //System.out.println("quick sort - Random 실행 시간: " + finalTime);
    }

    // 정렬되지 않은 file 읽어오기(,를 기준으로 split하여 arrayList에 삽입)
    public static void readFile(){
        try {
            sort = new ArrayList<Integer>();
            BufferedReader in = new BufferedReader(new FileReader(file)); // data04.txt불러옴
            String line = in.readLine(); // 한줄을 입력받아 읽음
            while (line != null) { // null이 아닐때까지 (파일의 끝까지 읽기)
                StringTokenizer parser = new StringTokenizer(line, ",");
                while (parser.hasMoreTokens()) {
                    int num = Integer.parseInt(parser.nextToken()); // 숫자를 받아옴
                    sort.add(num); // 받아온 숫자를 int배열에 저장
                }
                line = in.readLine(); // 한줄을 입력받아 읽음
            }
            in.close(); // 파일 닫기
        } catch (FileNotFoundException e) { // 예외처리
            System.err.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    // 정렬 결과를 file 쓰기(출력)
    public static void writeFile(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int i = 0; i < sort.size() - 1; i++) {
                writer.write(String.valueOf(sort.get(i)) + ",");
            }
            writer.write(String.valueOf(sort.get(sort.size() - 1)));

            writer.close();
        } catch (FileNotFoundException e) { // 예외처리
            System.err.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    // i 인덱스의 값과 j 인덱스의 값을 바꿔준다.
    public static void swap(ArrayList<Integer> A, int i, int j){
        int temp = A.get(i);
        A.set(i, A.get(j));
        A.set(j, temp);
    }

    // 기본적인 Partition 알고리즘을 이용한 Quick Sort 구현

    // 전달받은 제일 오른쪽 값과 그를 제외한 부분으로 나누어 값을 비교해 정렬한다.
    public static int partition(ArrayList<Integer> A, int p, int r){
        int pivot = A.get(r);
        int left = p-1;
        int right = p;
        for( ; right<r; right++){
            if(A.get(right) <= pivot){
                left++;
                swap(A, left, right);
            }
        }
        left++;
        swap(A, left, right);

        return left;
    }

    // 재귀적으로 파티션을 통해 퀵정렬을 한다.
    public static void quickSort(ArrayList<Integer> A, int p, int r){
        if(p < r){
            int q = partition(A, p, r);
            quickSort(A, p, q-1);
            quickSort(A, q+1, r);
        }
    }


    // 랜덤 pivot 값을 이용한 Quick Sort 구현

    // pivot을 정할 때 제일 오른쪽 값이 아니라 랜덤하게 정한다.
    public static int randomizedPartition(ArrayList<Integer> A, int p, int r){
        int random = RANDOM(p, r);
        // 제일 오른쪽 값과 랜덤하게 정해진 값을 바꿈.
        swap(A, r, random);
        return partition(A, p, r);
    }

    // 재귀적으로 파티션을 통해 퀵정렬을 한다.
    public static void quickSort_withRandom(ArrayList<Integer> A, int p, int r){
        if(p < r){
            int q = randomizedPartition(A, p, r);
            quickSort_withRandom(A, p, q-1);
            quickSort_withRandom(A, q+1, r);
        }
    }

    // 범위 내의 인덱스 중에서 랜덤으로 인덱스 하나를 정한다.
    public static int RANDOM(int first, int last){
        int range = last-first+1;
        int random = (int)(Math.random() * range);
        return first + random;
    }

}