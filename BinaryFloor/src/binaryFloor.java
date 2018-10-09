import java.util.Scanner;

// 201602082 진수연
public class binaryFloor {

    // 이진 탐색을 이용하여 log_2⁡𝑛의 floor를 효율적으로 계산하는 프로그램 구현
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("n을 입력: ");
        long n = sc.nextLong();
        int e = binaryFloor(n);
        System.out.println("log_2n보다 크지 않은 최대 정수: "+e);
    }

    // 이진탐색을 이용하여 log_2⁡𝑛의 floor(log_2⁡𝑛보다 크지 않은 최대 정수)를 계산한다.
    public static int binaryFloor(long n){
        int e = 0;
        double k = 2;
        while(k<=n){ // k가 n보다 작거나 같을 동안 계속 반복
            e = check_e(k);
            k *= k;
        } // 아직 비교 범위가 n보다 작을때 범위를 증가시킴.

        int mid = binarySearch(e, e*2, n); // 이진탐색 이용하여 위치 탐색

        return mid;
    }

    // e의 값을 찾기 위한 메서드
    public static int check_e(double k){
        int e = 0;
        while(k>=2){
            k = k/2;
            e++;
        } //이전 k의 값과 현재 e의 값은 같으므로 이전 k값이 2로 나눠지는 횟수로 e를 구할 수 있음.
        return e;
    }

    // binarySearch 구현
    private static int binarySearch(int low, int high, long findKey) {
        int position = (low + high) / 2; // 범위 내에서 중간 값

        if ((long) Math.pow(2,position) == findKey) { // key를 찾으면 바로 리턴
            return position;
        }

        if(high-position == 1){ // 범위가 1 차이 나는 구간까지 좁혀진다면
            return position; // 해당 위치 바로 찾을 수 있음.
        }

        if (findKey < (long) Math.pow(2,position)) { // 중간 값이 더 크면
            return binarySearch(low, position, findKey); // 중간 값의 왼쪽 구간을 이진탐색
        }else{ //중간 값이 더 작으면
            return binarySearch(position, high, findKey); // 중간 값의 오른쪽 구간을 이진탐색
        }
    }

}
