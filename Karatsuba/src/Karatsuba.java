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

        int aSize = String.valueOf(x).length();
        int bSize = String.valueOf(y).length();

        if(aSize <= Threshold || bSize <= Threshold){
            return x*y;
        }

        long x1 = divideLong(x, 0, aSize/2);
        long x0 = divideLong(x, aSize/2, aSize);
        long y1 = divideLong(y, 0, bSize/2);
        long y0 = divideLong(y, bSize/2, bSize);

        long z0 = karatsuba(x0,y0);
        long z2 = karatsuba(x1,y1);
        long z1 = karatsuba(x1+x0, y1+y0) -z2 - z0;

        int resultSize = aSize/2;
        long result = z2 * (long)Math.pow(10, resultSize*2) + z1 * (long)Math.pow(10, resultSize) + z0;
        return result;
    }

    private static long divideLong(long number, int start, int end){
        String changeStr = String.valueOf(number);
        String temp = "";
        for(int i = start; i<end; i++){
            temp+=changeStr.charAt(i);
        }
        return Long.parseLong(temp);
    }
}
