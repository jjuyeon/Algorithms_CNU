import java.util.Scanner;

// 201602082 진수연
public class Fibonacci {
    public static void main(String[] args){
        showRun(); // 프로그램의 기능을 설명 & 실행한다.
    }

    // 피보나치 수열을 recursion을 사용하여 구현.
    public static long fibonacci_recursion(long n){
        if(n<2){
            return n;
        }
        return fibonacci_recursion(n-1) + fibonacci_recursion(n-2);
    }
    // 피보나치 수열을 array를 사용하여 구현.
    public static void fibonacci_array(long n){

    }
    // 피보나치 수열을 recursive squaring을 사용하여 구현.
    public static void fibonacci_recursive_sqaring(long n){

    }

    // 기능에 따라 F(0)부터 F(n)까지 각각의 수행 시간을 측정한 후, 출력한다.
    private static void printRunningTime(int functionNumber, long n){
        long startTime = 0, stopTime = 0;

        System.out.println("---------------------------------------");
        for(int i=0; i<=n; i++) {
            if (functionNumber == 1) {
                startTime = System.nanoTime();
                System.out.print("F[" + i + "] = " + fibonacci_recursion(i));
                stopTime = System.nanoTime();

            } else if (functionNumber == 2) {

            } else if (functionNumber == 3) {

            }

            double sec = (double)(stopTime-startTime)/1000000000;
            System.out.println("\t\t" + String.format("%.12f",sec) + " sec"); //sec(초)로 단위 변경

            if((i+1)%10 == 0){
                System.out.println("---------------------------------------");
            }
        }
    }

    // 프로그램의 기능을 출력한다.
    private static void printProgram(){
        System.out.println("**** 연산의 방법은 다음과 같습니다. ****");
        System.out.println("---------------------------------------");
        System.out.println("1 : Recursion");
        System.out.println("2 : Array");
        System.out.println("3 : Recursive squaring");
        System.out.println("4 : 종료");
        System.out.println("---------------------------------------");
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
                    System.out.print("정수 n을 입력해주세요: ");
                    long n = Long.parseLong(scanner.nextLine());

                    System.out.println("**** 1 : Recursion 실행 ****");
                    printRunningTime(1,n);
                }else if(input == 2){ // array
                    System.out.print("정수 n을 입력해주세요: ");
                    long n = Long.parseLong(scanner.nextLine());

                    System.out.println("**** 2 : Array 실행 ****");
                    printRunningTime(2,n);
                }else if(input == 3){ // recursive squaring
                    System.out.print("정수 n을 입력해주세요: ");
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

