import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 201602082 진수연
public class Inversion {

    final static String file = "data07_inversion.txt";
    static ArrayList<Integer> sort = new ArrayList<Integer>();
    static int inversionCount = 0;
    static ArrayList<InversionSet> inversionSet = new ArrayList<InversionSet>();

    public static void main(String[] args){

        // 정렬되지 않은 file 읽어오기
        try{
            BufferedReader in = new BufferedReader(new FileReader(file)); // data07_inversion.txt불러옴
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

       // 입력된 데이터 출력
        System.out.print("Input Data : ");
        for( int i = 0; i< sort.size(); i++){
            if(i==sort.size()-1){
                System.out.println(sort.get(i));
            }else{
                System.out.print(sort.get(i) + ",");
            }
        }

        divideInversionCount(sort,0, sort.size()-1); // 인덱스 범위를 인자로 함.

        // inversion 개수와 해당 inversion set 출력
        System.out.println("Inversion Count : "+ inversionCount);
        System.out.print("Inversion Set : ");
        for(int i=0; i< inversionSet.size(); i++){
            System.out.print("("+inversionSet.get(i).leftComponent+","+inversionSet.get(i).rightComponent+") ");
        }

        // 정렬 결과 출력
        System.out.print("\nSorted Data : ");
        for( int i = 0; i< sort.size(); i++){
            if(i==sort.size()-1){
                System.out.println(sort.get(i));
            }else{
                System.out.print(sort.get(i) + ",");
            }
        }
    }


    // 분할 : 배열의 크기가 1이 될 때까지 계속해서 배열을 둘로 나눈다
    public static void divideInversionCount(ArrayList<Integer> tempSort, int p, int r){
        // 배열의 크기가 1이 될 때까지 계속하여 배열을 둘로 나눈다
        if((r-p)<1){ // 배열의 크기가 1이 되면 끝냄
            return;
        }
        int q = (p+r)/2; // 가운데 인덱스 넘버 설정

        divideInversionCount(tempSort, p, q); // 가운데 인덱스를 기준으로 왼쪽 위치 배열
        divideInversionCount(tempSort,q+1, r); // 가운데 인덱스를 기준으로 오른쪽 위치 배열

        mergeInversionCount(tempSort,p, r);
    }

    // 정복 : 나눠진 데이터를 2개 배열씩 비교하여 재귀적으로 정렬한다
    // 결합 : 정렬된 두 개의 배열을 병합해 하나의 정렬된 배열로 만든다
    private static void mergeInversionCount(ArrayList<Integer> tempSort, int p, int r){
        ArrayList<Integer> resultSort = new ArrayList<Integer>();
        int mid = (p+r)/2;
        int leftIndex = p;
        int rightIndex = mid+1;

        // 나눠진 2개의 배열을 비교하여 정렬하는 단계
        while(leftIndex<=mid && rightIndex<=r){
            boolean leftIndexSave = compare(tempSort, leftIndex, rightIndex, resultSort);
            if(leftIndexSave) { // 왼쪽 인덱스의 값이 오른쪽 인덱스의 값보다 작을 때
                leftIndex++;
            }
            else { // 오른쪽 인덱스의 값이 왼쪽 인덱스의 값보다 작을 때
                for (int i = leftIndex; i <= mid; i++) {
                    inversionCount++;
                    inversionSet.add(new InversionSet(tempSort.get(i), tempSort.get(rightIndex)));
                } // 왼쪽 배열의 현재 인덱스부터 남은 원소의 개수를 세어 inversion을 누적한다.
                rightIndex++;
            }
        }

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

    // 2개의 key 값을 비교하는 메서드
    private static boolean compare(ArrayList<Integer> compareSort, int compare1, int compare2, ArrayList<Integer> saveSort){
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

// inversion set의 정보를 저장하는 클래스
class InversionSet{
    int leftComponent;
    int rightComponent;

    public InversionSet(int leftComponent, int rightComponent){
        this.leftComponent = leftComponent;
        this.rightComponent = rightComponent;
    }
}