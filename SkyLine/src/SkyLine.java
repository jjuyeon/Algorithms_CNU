import java.util.*;

// 201602082 진수연
public class SkyLine {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("빌딩의 수 n을 입력하세요 : ");
        int n = Integer.parseInt(scanner.nextLine());

        Building[] building = new Building[n];
        for(int i=0; i<n; i++){
            String line = scanner.nextLine();

            StringTokenizer parser = new StringTokenizer(line, ",");
            int left = Integer.parseInt(parser.nextToken());
            int height = Integer.parseInt(parser.nextToken());
            int right = Integer.parseInt(parser.nextToken());

            building[i] = new Building(left, height, right); // building 정보를 가진 객체를 배열에 저장한다.
        }

        List<Skyline> result = findSkyLine(building, 0, n-1);
        for(int i=0; i<result.size()-1; i++){ // 결과를 출력한다.
            System.out.print(result.get(i).left+","+result.get(i).height+",");
        }
        System.out.println(result.get(result.size()-1).left+","+result.get(result.size()-1).height);
    }

    // divide & conquer를 사용하여 skyline을 찾는다.
    private static List<Skyline> findSkyLine(Building[] buildings, int s, int e){
        if(s==e){
            List<Skyline> skylines = new ArrayList<>(2);
            skylines.add(new Skyline(buildings[s].left, buildings[s].height));
            skylines.add(new Skyline(buildings[e].right, 0));
            return skylines;
        }

        int mid = (s+e)/2;

        List<Skyline> sky1 = findSkyLine(buildings, s, mid);
        List<Skyline> sky2 = findSkyLine(buildings, mid+1, e);

        return mergeSkyLine(sky1, sky2);
    }

    // max height를 구하여 max height 이하의 높이를 갖는, 감추어진 선은 제외시키는 기능을 한다.
    private static List<Skyline> mergeSkyLine(List<Skyline> skylines1, List<Skyline> skylines2){
        int currentH1 =0, currentH2 =0;
        List<Skyline> result = new ArrayList<Skyline>();

        while(skylines1.size()>0 && skylines2.size()>0){
            if(skylines1.get(0).left < skylines2.get(0).left){ // skylines1의 left가 더 작을 때
                int currentX = skylines1.get(0).left;
                currentH1 = skylines1.get(0).height;
                int maxH = currentH1;

                if(currentH2>maxH){ // 만약 skylines2의 height가 더 크다면
                    maxH = currentH2; // maxH 변경
                }

                int size = skylines1.size();
                result = append(currentX, maxH,result,size);
                skylines1.remove(0);

            }else{ // skylines2의 left가 더 작을 때
                int currentX = skylines2.get(0).left;
                currentH2 = skylines2.get(0).height;
                int maxH = currentH2;

                if(currentH1>maxH){ // 만약 skylines1의 height가 더 크다면
                    maxH = currentH1; // maxH 변경
                }

                int size = skylines2.size();
                result = append(currentX, maxH,result,size);
                skylines2.remove(0);
            }
        }

        while(skylines1.size() > 0){ // 나머지 line 정보를 저장한다.
            int size = skylines1.size();
            result = append(skylines1.get(0).left, skylines1.get(0).height,result,size);
            skylines1.remove(0);
        }
        while(skylines2.size() > 0){
            int size = skylines2.size();
            result = append(skylines2.get(0).left, skylines2.get(0).height,result,size);
            skylines2.remove(0);
        }
        return result;
    }

    // 결과 배열리스트(result)에 skyline을 담는 기능을 한다.
    private static List<Skyline> append(int currentX, int maxH, List<Skyline> result, int arrayListSize){
        if(result.size() == 0){
            result.add(new Skyline(currentX, maxH));
        }else{ // result 배열의 사이즈가 0이 아닐때부터 비교

            if(maxH == result.get(result.size()-1).height){ // 현재 max height와 이전 max height가 같다면
                return result; // skyline이 아니다.
            }

            if(result.get(result.size()-1).left == currentX){ // 이전left와 현재 left가 같다면 높이 측정
                Skyline lastSkyline = result.get(result.size()-1);
                result.remove(lastSkyline); // result 배열의 마지막 인덱스의 원소를 pop하여 마지막 원소인지 검사한다.
                if(arrayListSize == 1){ // 마지막 원소라면 height는 항상 0이다.
                    lastSkyline.height = 0;
                }
                else{ // 마지막 원소가 아니라면 height를 max height로 업데이트하기 위해 비교한다.
                    if(lastSkyline.height>maxH){
                        maxH = lastSkyline.height;
                    }
                    lastSkyline.height = maxH;
                }
                result.add(lastSkyline);
                }else{
                result.add(new Skyline(currentX, maxH));
            }
        }
        return result;
    }

    private static class Building{ // 빌딩의 정보
        int left;
        int right;
        int height;

        public Building(int left, int height, int right){
            this.left = left;
            this.height = height;
            this.right = right;
        }
    }

    private static class Skyline{ // skyLine 정보
        int left;
        int height;

        public Skyline(int left, int height){
            this.left = left;
            this.height = height;
        }
    }

}
