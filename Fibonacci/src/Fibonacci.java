import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// 201602082 진수연
public class Fibonacci {
    private static ArrayList<Long> arr = new ArrayList<Long>(); // fibonacci_array & fibonacci_recursive_squaring 함수에 사용할 배열

    public static void main(String[] args){
        showRun(); // 프로그램의 기능을 설명 & 실행한다.
    }

    // 피보나치 수열을 recursion을 사용하여 구현.
    private static long fibonacci_recursion(long n){
        if(n<2){ //2보다 작으면
            return n;
        }
        return fibonacci_recursion(n-1) + fibonacci_recursion(n-2);
    }

    // 피보나치 수열을 array를 사용하여 구현.
    private static long fibonacci_array(ArrayList<Long> arrayList, long n){
        if(n<=1){ //0번째와 1번째 인덱스 설정
            arrayList.add(n);
            return n;
        }else{
            long temp = arrayList.get((int)n-1) + arrayList.get((int)n-2);
            arrayList.add(temp);
            return temp;
        }
    }

    // 피보나치 수열을 recursive squaring을 사용하여 구현.
    private static long fibonacci_recursive_squaring(ArrayList<Long> arrayList, long n){
        if(n<2){
            return n;
        }
        return pow(arrayList, n).get(2);
    }

    // divide & conquer를 사용하여 피보나치를 구한다.
    private static ArrayList<Long> pow(ArrayList<Long> arrayList, long n){
        if(n==1){
            return arrayList;
        }

        arrayList = pow(arrayList, n/2); // 반으로 나눠서 계산한다.
        arrayList = mul(arrayList, arrayList); // arrayList(N) = arrayList(N/2) * arrayList(N/2)

        if(n%2 == 1){ // n은 홀수일 때
            ArrayList<Long> initial = new ArrayList<Long>(Arrays.asList(1l,1l,1l,0l));
            arrayList = mul(arrayList, initial);
        } // {1,1,1,0}를 한번 더 곱해준다.

        return arrayList;
    }

    // arrayList1,2의 2*2 행렬 곱을 한다.
    private static ArrayList<Long> mul(ArrayList<Long> arrayList1, ArrayList<Long> arrayList2){
        ArrayList<Long> result = new ArrayList<Long>();

        result.add(arrayList1.get(0)*arrayList2.get(0) + arrayList1.get(1)*arrayList2.get(2));
        result.add(arrayList1.get(0)*arrayList2.get(1) + arrayList1.get(1)*arrayList2.get(3));
        result.add(arrayList1.get(2)*arrayList2.get(0) + arrayList1.get(3)*arrayList2.get(2));
        result.add(arrayList1.get(2)*arrayList2.get(1) + arrayList1.get(3)*arrayList2.get(3));

        return result;
    }

    // 기능에 따라 F(0)부터 F(n)까지 각각의 수행 시간을 측정한 후, 출력한다.
    private static void printRunningTime(int functionNumber, long n){
        double startTime = 0, stopTime = 0;
        arr.clear(); // arr 초기화

        System.out.println("------------------------------------------------------------------");
        for(int i=0; i<=n; i++) {
            if (functionNumber == 1) {
                startTime = System.nanoTime();
                System.out.print("F[" + i + "] = " + String.format("%-30s",fibonacci_recursion(i)));
                stopTime = System.nanoTime();
            } else if (functionNumber == 2) {
                startTime = System.nanoTime();
                System.out.print("F[" + i + "] = " + String.format("%-30s",fibonacci_array(arr, i)));
                stopTime = System.nanoTime();
            } else if (functionNumber == 3) {
                arr = new ArrayList<Long>(Arrays.asList(1l,1l,1l,0l)); // arr 초기값 세팅 {1,1,1,0}
                startTime = System.nanoTime();
                System.out.print("F[" + i + "] = " + String.format("%-30s",fibonacci_recursive_squaring(arr, i)));
                stopTime = System.nanoTime();
            }
            double sec = (stopTime-startTime)/1000000000;
            System.out.println(String.format("%.12f",sec) + " sec"); //sec(초)로 단위 변경

            if((i+1)%10 == 0){
                System.out.println("------------------------------------------------------------------");
            }
        }
    }

    // 프로그램의 기능을 출력한다.
    private static void printProgram(){
        System.out.println("**** 연산의 방법은 다음과 같습니다. ****");
        System.out.println("------------------------------------------------------------------");
        System.out.println("1 : Recursion");
        System.out.println("2 : Array");
        System.out.println("3 : Recursive squaring");
        System.out.println("4 : 종료");
        System.out.println("------------------------------------------------------------------");
        System.out.print("수행하고자 하는 연산을 선택해주세요: ");
    }

    // 기능을 실행한다.
    private static void showRun(){
        Scanner scanner = new Scanner(System.in);
        int input;

        while(true){
            try{
                printProgram();

                input = Integer.parseInt(scanner.nextLine());
                if(input == 1){ // recursion
                    System.out.print("정수 n을 입력해주세요: (범위를 90까지 입력해주세요.) ");
                    long n = Long.parseLong(scanner.nextLine());

                    System.out.println("**** 1 : Recursion 실행 ****");
                    printRunningTime(1,n);
                }else if(input == 2){ // array
                    System.out.print("정수 n을 입력해주세요: (범위를 90까지 입력해주세요.) ");
                    long n = Long.parseLong(scanner.nextLine());

                    System.out.println("**** 2 : Array 실행 ****");
                    printRunningTime(2,n);
                }else if(input == 3){ // recursive squaring
                    System.out.print("정수 n을 입력해주세요: (범위를 90까지 입력해주세요.) ");
                    long n = Long.parseLong(scanner.nextLine());

                    System.out.println("**** 3 : Recursive squaring 실행 ****");
                    printRunningTime(3,n);
                }else if(input == 4){
                    System.out.println("**** 프로그램을 종료합니다. ****");
                    return;
                }else{
                    throw new Exception();
                }
            }catch (Exception e) { // 에러처리
                System.out.println("**** Error 잘못 입력하셨습니다. ****");
                System.out.println("초기 화면으로 되돌아갑니다");
            }
            System.out.println("\n");
        }
    }

}

