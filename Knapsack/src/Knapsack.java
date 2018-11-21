import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// 201602082 진수연
public class Knapsack {
    final static String file = "data11.txt";
    private static ArrayList<Integer> itemNum = new ArrayList<Integer>();
    private static ArrayList<Integer> value = new ArrayList<Integer>();
    private static ArrayList<Integer> weight = new ArrayList<Integer>();
    private static int[][] table;

    public static void main(String[] args){
        readFile();

        int bagSize;
        while(true){
            bagSize = setBagSize();
            if(bagSize>=0 && bagSize<=50){
                break;
            }
            System.out.println("Error : 범위에 맞게 배낭의 사이즈를 설정해주세요.\n");
        }

        table = new int[itemNum.size()+1][bagSize+1];
        makeTable(bagSize);
        printTable();
        printMax(itemNum.size(), bagSize);
        }


    // file을 읽어 각각에 맞는 배열리스트에 저장한다.
    private static void readFile(){
        try {
            value.add(0); weight.add(0);

            BufferedReader in = new BufferedReader(new FileReader(file)); // data11.txt 불러옴
            String line = in.readLine(); // 한줄을 입력받아 읽음
            while (line != null) { // null이 아닐때까지 (파일의 끝까지 읽기)
                StringTokenizer parser = new StringTokenizer(line, ",");
                itemNum.add(Integer.parseInt(parser.nextToken()));
                value.add(Integer.parseInt(parser.nextToken()));
                weight.add(Integer.parseInt(parser.nextToken()));
                line = in.readLine(); // 한줄을 입력받아 읽음
            }
            in.close(); // 파일 닫기
        } catch (FileNotFoundException e) { // 예외처리
            System.err.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    // 배낭의 사이즈를 입력받는다.
    private static int setBagSize() {
        Scanner scanner = new Scanner(System.in);
            System.out.print("배낭의 사이즈를 입력하세요(0~50) : ");
            int n = scanner.nextInt();
            return n;
    }

    // 배열(Table)에 OPT 값을 채운다.
    private static void makeTable(int bagSize){
        for(int j=0; j<=bagSize; j++){
            table[0][j] = 0;
        } // item이 아무것도 없을 때이므로 배낭에 들어갈 수 있는 아이템의 가치 합은 모두 0이다.

        for(int i=1; i<=itemNum.size(); i++){ // 아이템을 하나씩 추가
            for(int j=1; j<=bagSize; j++){ // 배낭의 크기를 하나씩 증가
                if(weight.get(i) > j){
                    table[i][j] = table[i-1][j];
                } // 만약 추가한 아이템의 크기가 배낭의 크기보다 크다면 아이템을 넣을 수 없다.
                else{
                    table[i][j] = max(table[i-1][j], value.get(i)+table[i-1][j-weight.get(i)]);
                } // 아이템의 크기가 더 작다면 넣기 전의 가치와 넣은 후의 가치를 비교하여 더 높은 가치를 저장한다.
            }
        }
    }

    // 최종적으로 배열(Table)에 저장된 OPT 값을 출력한다.
    private static void printTable(){
        for(int i=0; i<table.length; i++){
            for(int j=0; j<table[i].length; j++){
                System.out.print(table[i][j]+"\t");
            }
            System.out.println();
        }
    }

    // 가치(value) 총합이 가장 높은 item 구성 및 value 합을 출력한다.
    private static void printMax(int itemSize, int bagSize){
        System.out.println("max : "+table[itemSize][bagSize]);

        System.out.print("item : ");
        int sum = table[itemSize][bagSize];
        int tempItemSize = itemSize;
        int tempBagSize = bagSize;
        while(tempBagSize>=0 && tempItemSize>=0 && sum > 0){
            while(table[tempItemSize][tempBagSize] == table[tempItemSize-1][tempBagSize]){
                tempItemSize--;
            } // 배낭에 담은 가치가 앞줄과 같다면 앞줄에서 먼저 결정된 가치이므로 앞줄로 이동한다.
            System.out.print(itemNum.get(tempItemSize-1)+" "); // 해당 item 구성을 출력한다.
            sum -= value.get(tempItemSize); // 최종max에서 해당 value 제거
            tempBagSize -= weight.get(tempItemSize); // 최종weight에서 해당 weight 제거
            tempItemSize--;
        }
    }

    // 두 수를 비교하여 큰 수를 반환한다.
    private static int max(int a, int b){
        if(a>=b){
            return a;
        }else{
            return b;
        }
    }
}
