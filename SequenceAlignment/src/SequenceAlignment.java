import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// 201602082 진수연
public class SequenceAlignment {
    private final static String file = "data12.txt";
    private static String x;
    private static String y;
    private static String[][] matrix;
    private static ArrayList<String> result;
    public static void main(String[] args){
        int mismatch_penalty = 2;
        int gap = 2;

        readFile();
        alignment(mismatch_penalty,gap);
        getOptimalCombination(gap);
    }

    // file을 읽어 비교할 두 문자열을 입력받는다.
    private static void readFile(){
        try {
            BufferedReader in = new BufferedReader(new FileReader(file)); // data11.txt 불러옴
            String line = in.readLine(); // 한줄을 입력받아 읽음
            int count = 0;
            while (line != null) { // null이 아닐때까지 (파일의 끝까지 읽기)
                if(count%2==0){
                    x  = line;
                }else{
                    y = line;
                }
                count++;
                line = in.readLine(); // 한줄을 입력받아 읽음
            }
            in.close(); // 파일 닫기
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    // 두 문자열의 같은 곳과 다른 곳을 찾아 같은 길이로 만드며 대응하는 데 드는 비용 중 가장 최적의 비용을 설정하여 이차원 배열을 만든다.
    private static void alignment(int mismatch_penalty, int gap_penalty){
        matrix = new String[y.length()+1][x.length()+1];
        // matrix 초기화
        for(int i=0; i<=y.length(); i++){
            for(int j=0; j<=x.length(); j++){
                matrix[i][j] = "-";
            }
        }
        // 첫 줄, 맨 왼쪽 줄 초기화
        for(int j=0; j<=x.length(); j++) {
            matrix[0][j] = String.valueOf(-(gap_penalty * j));
        }
        for(int i=0; i<=y.length(); i++){
            matrix[i][0] = String.valueOf(-(gap_penalty * i));
        }
        printMatrix(); // 초기 matrix 값 출력

        for(int i=1; i<=y.length(); i++){ // 최적의 비용 저장
            for(int j=1; j<=x.length(); j++){
                matrix[i][j] = max(Integer.parseInt(matrix[i][j-1])-mismatch_penalty, Integer.parseInt(matrix[i-1][j-1])+sameChar_check(j-1,i-1), Integer.parseInt(matrix[i-1][j])-mismatch_penalty);
                printMatrix(); // matrix를 만들어가는 과정 출력
            } // 왼쪽, 위쪽, 대각선에서 계산된 값 중 가장 큰 값을 matrix에 저장
        }
    }

    // matrix에 저장된 값을 출력한다.
    private static void printMatrix(){
        System.out.print("       X∥");
        System.out.printf("%5c",' ');
        for( int i = 0 ; i < x.length() ; i++){
            System.out.printf("%5c",x.charAt(i));
        }
        System.out.println();
        System.out.print("Y  Index∥");
        for(int i=0; i<=x.length(); i++){
            System.out.printf("%5s",i);
        }
        System.out.println();
        for(int i=0; i< x.length(); i++){
            System.out.print("--------");
        }
        System.out.println();

        int index = 0;
        System.out.print("       "+index+"∥");
        for( int i = 0 ; i <= y.length() ; i++ ){
            for( int j = 0 ; j <= x.length() ; j++ ){
                System.out.printf( "%5s",matrix[i][j]);
            }
            System.out.println();

            if( i != y.length() ){
                System.out.print(y.charAt(index) + "  ");
                System.out.printf("%5d",(index+1));
                System.out.print("∥");
                index++;
            }
        }

        System.out.println("\n");
    }

    // 최적의 조합을 계산하여 출력한다.
    private static void getOptimalCombination(int gap_penalty) {
        int i, j;
        StringBuilder alignmentX = new StringBuilder();
        StringBuilder alignmentY = new StringBuilder();

        for(i=y.length(), j=x.length(); i>0 && j>0; ){
            if (Integer.parseInt(matrix[i - 1][j - 1]) + sameChar_check(j - 1, i - 1) == Integer.parseInt(matrix[i][j])) {
                matrix[i][j] = '*'+matrix[i][j];
                alignmentX.insert(0, x.charAt(j - 1));
                alignmentY.insert(0, y.charAt(i - 1));
                i--; j--;
            } // 대각선에서 계산된 값이 최적의 값인지 확인 true-> 모두 정렬된 경우
            else if (Integer.parseInt(matrix[i - 1][j]) - gap_penalty == Integer.parseInt(matrix[i][j])) {
                matrix[i][j] = '*'+matrix[i][j];
                alignmentY.insert(0, y.charAt(i - 1));
                alignmentX.insert(0, "-");
                i--;
            } // 위쪽에서 계산된 값이 최적의 값인지 확인 true-> x에 gap이 있을 경우
            else if (Integer.parseInt(matrix[i][j - 1]) - gap_penalty == Integer.parseInt(matrix[i][j])) {
                matrix[i][j] = '*'+matrix[i][j];
                alignmentX.insert(0, x.charAt(j - 1));
                alignmentY.insert(0, "-");
                j--;
            } // 왼쪽에서 계산된 값이 최적의 값인지 확인 true-> y에 gap이 있을 경우
        }
        matrix[i][j] = '*'+matrix[i][j];

        // 남은 x,y값을 더해준다.
        while(i>0){
            alignmentY.insert(0, y.charAt(i - 1));
            i--;
        }
        while(j>0) {
            alignmentX.insert(0, x.charAt(j - 1));
            j--;
        }
        // x와 y 길이가 같도록 만들어준다.
        while(alignmentX.length() > alignmentY.length()) {
            alignmentY.insert(0, "-");
        }
        while(alignmentY.length() > alignmentX.length()) {
            alignmentX.insert(0, "-");
        }

        System.out.println("=======================================================");
        System.out.println("\n[ 최종 행렬 출력 (기호 *이 있는 값의 위치는 최적의 조합을 찾은 과정을 나타냅니다) ]");
        printMatrix(); // 최종 matrix 출력

        System.out.println("[ 최적의 조합 출력 ]");
        System.out.println(alignmentX);
        System.out.print(alignmentY + "       ");
        System.out.println("점수 : " + matrix[y.length()][x.length()].charAt(1));
    }

    // 3개의 숫자 중 가장 큰 숫자를 반환한다.
    private static String max(int a, int b, int c){
        int max = a;
        if(max<b){
            max=b;
        }
        if(max<c){
            max=c;
        }
        return String.valueOf(max);
    }

    // x의 i인덱스 문자와 y의 j인덱스 문자가 같은지 비교하여 값을 반환한다.
    private static int sameChar_check(int i, int j){
        if(x.charAt(i) == y.charAt(j)){
            return 1;
        }else{
            return -1;
        }
    }
}