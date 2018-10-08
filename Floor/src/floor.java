import java.util.Scanner;

// 201602082 진수연
public class floor {

    // 정수 n을 입력 받아 log_2⁡𝑛의 floor(log_2⁡𝑛보다 크지 않은 최대 정수)를 계산하는 프로그램 구현
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("정수 n을 입력: ");
        int n = sc.nextInt();

        int e = floor(n);
        System.out.println("log_2n보다 크지 않은 최대 정수: "+e);
    }

    // log_2⁡𝑛의 floor(log_2⁡𝑛보다 크지 않은 최대 정수)를 계산한다.
    public static int floor(int n){
        int e = -1;
        int k = 1;
        while(k<=n){ // k가 n보다 작거나 같을 동안 계속 반복
            e += 1;
            k *= 2;
        } // 아직 비교 범위가 n보다 작을때 범위를 증가시킴.

        return e;
    }
}
