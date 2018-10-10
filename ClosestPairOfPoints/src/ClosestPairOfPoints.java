import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 201602082 진수연
public class ClosestPairOfPoints {

    final static String FILE = "data05_closest.txt";
    static ArrayList<Double> X = new ArrayList<Double>();
    static ArrayList<Double> Y = new ArrayList<Double>();

    public static void main(String[] args){
        // 정렬되지 않은 file 읽어오기
        try{
            BufferedReader in = new BufferedReader(new FileReader(FILE)); // data05_closest.txt불러옴
            String line = in.readLine(); // 한줄을 입력받아 읽음
            while (line != null) { // null이 아닐때까지 (파일의 끝까지 읽기)
                StringTokenizer parser = new StringTokenizer(line, ",");
                int count = 0;
                while(parser.hasMoreTokens()){
                    double num = Double.parseDouble(parser.nextToken()); //숫자를 받아옴
                    if(count % 2 == 0){
                        X.add(num);
                        count++;
                    }else {
                        Y.add(num);
                    }
                } // 받아온 숫자를 int배열에 저장
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

        System.out.println(closestPair(X, Y, 0, X.size()));
    }

    // 함수들을 적절한 순서로 불러 모든 좌표에서 가장 최소 거리를 구한다.
    private static double closestPair(ArrayList<Double> x, ArrayList<Double> y, int p, int q){
        double result;

        sortByStandard(x, y);
        if( q-p <= 3){
            result = bruteForce(x, y, p, q-1);
            return result;
        }

        double minOfLeft = closestPair(x, y, 0, x.size()/2);
        double minOfRight = closestPair(x, y, x.size()/2, x.size());

        double delta = min(minOfLeft, minOfRight);
        delete( x, y, delta);

        sortByStandard(y, x);
        result = bruteForce(x, y, 0, x.size()-1 );

        result = Math.round(result * 1000d) / 1000d; // 소수점 3자리까지 출력
        return result;
    }

    // 주어진 배열 sort_standard를 기준으로 정렬한다. (insertion sort 사용)
    private static void sortByStandard(ArrayList<Double> sort_standard, ArrayList<Double> sort){
        for(int i=1; i<sort_standard.size(); i++){
            double temp_standard = sort_standard.get(i); // index i의 값을 임시 기억장소 temp에 저장
            double temp_sort = sort.get(i);
            int tempLocation = i;

            // temp 값을 좌측 배열에 저장된 수 중 가장 마지막(오른쪽)배열에 저장된 값과 비교 && index범위 제한
            while(tempLocation > 0 && sort_standard.get(tempLocation-1) > temp_standard){ // temp의 값이 더 작을 때
                sort_standard.set(tempLocation, sort_standard.get(tempLocation-1)); // 비교한 배열의 값을 오른쪽 인덱스에 저장
                sort.set(tempLocation, sort.get(tempLocation-1));
                tempLocation--; // 계속 왼쪽 값과 비교하기 위함
            }

            // 비교가 종료되면
            sort_standard.set(tempLocation, temp_standard); // 비교했던 위치의 바로 오른쪽 인덱스에 temp 값 저장
            sort.set(tempLocation, temp_sort); // 비교했던 위치의 바로 오른쪽 인덱스에 temp 값 저장
        }
    }

    // Brute Force란? 문제를 해결하기 위해서, 가능한 모든 경우에 대해 모두 직접 해 보는 방법
    private static double bruteForce(ArrayList<Double> x, ArrayList<Double> y, int p, int q){
        double min = 10000;

        // 주어진 x,y 배열의 값을 통해 주어진 범위 p에서 r까지 모든 좌표의 짝끼리의 길이를 구해 가장 작은 길이를 계산한다.
        for(int i=p; i<=q; i++){
            for(int j=i+1; j<=q; j++){
                double distance = Math.pow(x.get(j) - x.get(i), 2) + Math.pow(y.get(j) - y.get(i), 2);
                if(distance < min){
                    min = distance;
                }
            }
        }
        return Math.sqrt(min);
    }

    // 주어진 길이에서 더 작은 길이를 반환한다.
    private static double min(double left, double right){
        if(left < right){
            return left;
        }
        else{
            return right;
        }
    }

    // 주어진 x배열에서 x[N/2]를 기준으로 양 옆으로 delta만큼 떨어진 좌표들은 배열에서 삭제한다.
    private static void delete(ArrayList<Double> x, ArrayList<Double> y, double delta){
        double base_left = x.get(x.size()/2) - delta;
        double base_right = x.get(x.size()/2) + delta;
        int baseIndex = x.size()/2;

        ArrayList<Double> temp_x = new ArrayList<Double>();
        ArrayList<Double> temp_y = new ArrayList<Double>();
        for(int i=0; i<x.size(); i++){
            temp_x.add(x.get(i));
            temp_y.add(y.get(i));
        }

        for(int i=0; i<baseIndex; i++){
            if(base_left > x.get(i)){ // 왼쪽 부분에서 N/2에서 델타보다 더 멀리 떨어진 점들을 찾아 제거
                temp_x.remove(i);
                temp_y.remove(i);
            }
        }

        for(int i= baseIndex; i<x.size(); i++){
            if(base_right < x.get(i)){ // 오른쪽 부분에서 N/2에서 델타보다 더 멀리 떨어진 점들을 찾아 제거
                temp_x.remove(i);
                temp_y.remove(i);
            }
        }

        x.clear(); y.clear();
        for(int i=0; i<temp_x.size(); i++){
            x.add(temp_x.get(i));
            y.add(temp_y.get(i));
        }
    }

}
