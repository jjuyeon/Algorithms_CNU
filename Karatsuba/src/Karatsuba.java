import java.util.Scanner;
import java.util.StringTokenizer;

// 201602082 진수연
public class Karatsuba {

    private static int Threshold = 3; // Threshold는 3자리 수보다 작은 곱셈은 일반 곱셈으로 하기 위해 설정한다.

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);

        while(true){
            try{
                System.out.println("=============================================================================");
                System.out.println("*** 같은 자릿수를 가진 두 수 x,y의 곱을 하는 Karatsuba Algorithm ***");
                System.out.println("같은 자릿수를 가진 숫자 x,y를 입력해주세요. 입력형태는 long x,long y 입니다.\n");
                String line = scanner.nextLine();
                StringTokenizer parser = new StringTokenizer(line, ",");

                long x = Long.parseLong(parser.nextToken());
                long y = Long.parseLong(parser.nextToken());

                if(String.valueOf(x).length() != String.valueOf(y).length()){
                    throw new Exception();
                }

                System.out.print("\nOutput Data : ");
                System.out.println(karatsuba(x,y));
                System.out.println("=============================================================================");
                return;
            }catch(Exception e){
                System.out.println("\n*** 입력 Error : 처음화면으로 되돌아갑니다. ***\n");
            }
        }
    }

    private static long karatsuba(long x, long y){
        // long x, y의 size 저장
        int xSize = String.valueOf(x).length();
        int ySize = String.valueOf(y).length();

        // Threshold(3)보다 자릿수가 작으면 일반적인 곱셈 연산을 한다.
        if(xSize <= Threshold || ySize <= Threshold){
            return x*y;
        }

        // 주어진 숫자를 자릿수가 절반이 되도록 나눈다.
        long x1 = divideLong(x, 0, xSize/2);
        long x0 = divideLong(x, xSize/2, xSize);
        long y1 = divideLong(y, 0, ySize/2);
        long y0 = divideLong(y, ySize/2, ySize);

        // 자릿수에 따른 정수 계산을 한다.
        long z0 = karatsuba(x0,y0);
        long z2 = karatsuba(x1,y1);
        long z1 = karatsuba(x1+x0, y1+y0) -z2 - z0;

        // 위에서 얻은 정수를 결과식에 맞춰 대입하여 결과를 계산한다.
        if(xSize % 2 == 1){
            xSize++;
        }
        long result = z2 * (long)Math.pow(10, xSize) + z1 * (long)Math.pow(10, xSize/2) + z0;
        return result;
    }

    // 전달받은 start에서 end까지 number를 잘라 반환하는 함수이다.
    private static long divideLong(long number, int start, int end){
        String changeStr = String.valueOf(number);
        String temp = "";
        for(int i = start; i<end; i++){
            temp+=changeStr.charAt(i);
        }
        return Long.parseLong(temp);
    }
}
