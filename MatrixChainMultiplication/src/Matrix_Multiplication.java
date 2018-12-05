import java.util.ArrayList;
import java.util.Scanner;

// 201602082 진수연
public class Matrix_Multiplication {
    private static ArrayList<Integer> matrixSize=new ArrayList<Integer>();
    private static int m[][];
    private static int s[][];

    public static void main(String args[]){
        input_matrixSize();
        matrix_chain_order(matrixSize);
        printMatrixSize(matrixSize);
        printAll_matrix(m);
        printAll_matrix(s);

        System.out.println("Optimal solution : "+m[0][m.length-1]);
        System.out.print("Optimal parens : ");
        print_optimal_parens(s,0,s.length-1);
    }

    // 사용자로부터 행렬을 입력받는다.
    private static void input_matrixSize(){
        Scanner scanner = new Scanner(System.in);
        int index = 1;
        while(true) {
            System.out.println("*** "+index+"번째 행렬의 크기를 입력하세요(0을 입력하면 끝납니다.)***");
            System.out.print("행의 크기: ");
            int x = Integer.parseInt(scanner.nextLine());
            if(x == 0){ // 0을 입력받으면 행렬 입력 끝
                break;
            }
            System.out.print("열의 크기: ");
            int y = Integer.parseInt(scanner.nextLine());
            if(y == 0){ // 0을 입력받으면 행렬 입력 끝
                break;
            }
            if(matrixSize.size()==0) {
                matrixSize.add(x);
                matrixSize.add(y);
            }else{
                matrixSize.add(y);
            }
            index++;
        }
        System.out.println();
        System.out.println("*** "+(index-1)+"개의 행렬이 입력되었습니다. ***");
    }

    // 이차원 배열 m과 s안에 들어갈 값을 계산하여 배열을 완성한다.
    private static void matrix_chain_order(ArrayList<Integer> p){
        int n = p.size()-1;

        m = new int[n][n];
        s = new int[n][n];

        // 이차원 배열 m, s 초기화
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                m[i][j] = -1;
                s[i][j] = -1;
            }
        }

        // i=j일 경우 m[i][j]는 0
        for(int i=0; i<n; i++){
            m[i][i] = 0;
        }

        for(int l=1; l<n; l++){
            for(int i=0; i<n-l; i++){
                int j = i+l;
                m[i][j] = Integer.MAX_VALUE;
                for(int k=i; k<j; k++){ // m과 s에 들어갈 값을 게산한다.
                    int mul = p.get(i)*p.get(k+1)*p.get(j+1);
                    int q  = m[i][k]+m[k+1][j] + mul;

                    if(q<m[i][j]){
                        m[i][j] = q;
                        s[i][j] = k+1;
                    }
                }
            }
        }
    }

    // 행렬곱 연산 수행 시 최적의 경우의 연산 순서를 출력한다.
    private static void print_optimal_parens(int[][] s, int i, int j){
        if(i==j){
            System.out.print("A"+(i+1));
        }else{
            System.out.print("(");
            print_optimal_parens(s,i,s[i][j]-1);
            print_optimal_parens(s,s[i][j],j);
            System.out.print(")");
        }
    }

    // 사용자로부터 입력받은 행렬의 사이즈를 출력한다.
    private static void printMatrixSize(ArrayList<Integer> p){
        for(int i=1; i<p.size(); i++){
            System.out.println("A("+i+") = "+p.get(i-1)+" x "+p.get(i));
        }
        System.out.println();
    }

    // 파라미터로 받은 행렬에 저장된 모든 값들을 출력한다.
    private static void printAll_matrix(int[][] matrix){
        System.out.println("===========================================================================");
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                System.out.print(String.format("%10d",matrix[i][j]));
            }
            System.out.println();
        }
        System.out.println("===========================================================================");
        System.out.println();
    }
}