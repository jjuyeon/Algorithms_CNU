import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// 201602082 진수연
public class maxPQ {

	final static String file = "data03.txt";
	ArrayList<Integer> priority;
	ArrayList<String> explanation;

	public maxPQ() {
		priority = new ArrayList<Integer>();
		explanation = new ArrayList<String>();
	}

	// maxPQ객체의 우선 순위_priority(작업 설명_explanation) 배열의 사이즈를 리턴한다.
	public static int size(maxPQ S) {
		return S.priority.size();
	}

	public static void main(String[] args) {

		maxPQ maxPriorityQueue = new maxPQ();

		// 정렬되지 않은 file 읽어오기
		try {
			int priorityCount = 0;
			BufferedReader in = new BufferedReader(new FileReader(file)); // data03.txt 불러옴
			String line = in.readLine(); // 한줄을 입력받아 읽음
			while (line != null) { // null이 아닐때까지 (파일의 끝까지 읽기)
				StringTokenizer parser = new StringTokenizer(line, ",");
				while (parser.hasMoreTokens()) {
					priorityCount++;
					if ((priorityCount % 2) == 0) { // 과목명을 받아옴
						String sub = parser.nextToken();
						maxPriorityQueue.explanation.add(sub);
					} else { // 우선순위를 받아옴
						int num = Integer.parseInt(parser.nextToken());
						maxPriorityQueue.priority.add(num);
					}
				}
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

		// Max Priority Queue를 생성한다.
		BUILD_MAX_HEAP(maxPriorityQueue, size(maxPriorityQueue));

		// 프로그램의 기능을 설명 & 실행한다.
		showRun(maxPriorityQueue);
	}

	// Max Priority Queue를 구축한다.
	public static void BUILD_MAX_HEAP(maxPQ S, int n) {
		for (int i = n / 2 - 1; i >= 0; i--) { // 리프노드 전 마지막 내부노드부터 (끝에서부터) 불러옴
			MAX_HEAPIFY(S, i, n);
		}
	}

	public static void MAX_HEAPIFY(maxPQ S, int i, int n) {
		int j = 0;
		while (i < n / 2) {// 내부노드 0~n/2-1 그러므로 리프노드가 아니려면 n/2보단 작아야함.
			// 리프노드가 아닐때라는 조건이 있으므로 무조건 자식은 있다.
			int tempPriority = S.priority.get(i); // i인덱스의 priority 값으로 설정

			if (2 * i + 1 < S.priority.size() && 2 * i + 2 < S.priority.size()) { // 자식 모두 존재
				if (S.priority.get(2 * i + 1) < S.priority.get(2 * i + 2)) // 비교해서 j를 큰 자식으로 설정
					j = 2 * i + 2;
				else
					j = 2 * i + 1;
			} else if (2 * i + 1 < S.priority.size()) { // 왼쪽 자식만 존재할때
				j = 2 * i + 1; // 큰 자식은 왼쪽자식
			} else { // 오른쪽 자식만 존재할때
				j = 2 * i + 2; // 큰 자식은 오른쪽자식
			}

			if (S.priority.get(j) > tempPriority) {
				swap(S, i, j); // 자리 바꾸기
				MAX_HEAPIFY(S, j, size(S)); // 자식들도 heapify해야함.
			} else
				break;
		}
	}

	// S에 원소 x를 새로 넣는다.
	public static void insert(maxPQ S, int number, String name) {
		S.priority.add(number);
		S.explanation.add(name);

		if (S.priority.size() == 1) { // 비교할 값이 없으므로 바로 리턴
			return;
		}

		// 부모 노드 위로 올라가면서 값을 비교 -> 부모 노드의 값보다 더 큰 곳에 위치
		int currentLoc = S.priority.size() - 1;
		int parentLoc = (currentLoc - 1) / 2;
		while ((currentLoc > 0) && (S.priority.get(currentLoc) > S.priority.get(parentLoc))) { // 부모노드의 값보다 삽입하는 값이 더 크면
			swap(S, currentLoc, parentLoc);
			currentLoc = parentLoc;
			parentLoc = (currentLoc - 1) / 2; // 서로의 값을 변경 후, 비교 위치도 위로 올라감
		}
	}

	// S에서 키 값이 최대인 원소를 리턴한다.
	public static String max(maxPQ S) {
		return S.priority.get(0) + "," + S.explanation.get(0);
	}

	// S에서 키 값이 최대인 원소를 제거한다.
	public static String extract_max(maxPQ S) {
		String removeMax = S.priority.get(0) + "," + S.explanation.get(0);
		int lastIndex_p = S.priority.size() - 1;
		int lastIndex_e = S.explanation.size() - 1;

		S.priority.set(0, S.priority.get(lastIndex_p));
		S.priority.remove(lastIndex_p);
		S.explanation.set(0, S.explanation.get(lastIndex_e));
		S.explanation.remove(lastIndex_e);

		MAX_HEAPIFY(S, 0, size(S));
		return removeMax;
	}

	// 원소 x의 키 값을 k로 증가시킨다. 이때 k는 x의 현재 키 값보다 작아지지 않는다고 가정한다.
	public static void increase_key(maxPQ S, int x, int k) {
		S.priority.set(x, k);
		BUILD_MAX_HEAP(S, size(S));
	}

	// S에서 노드 x를 제거한다. 제거 후 Max heap을 유지한다.
	// 삭제될 위치에서부터 아래로 자식노드들과 비교를 통해 노드 제일 마지막 값을 삽입하며 max heap구조를 정렬한다.
	public static String delete(maxPQ S, int x) {
		String removeNode = S.priority.get(x) + "," + S.explanation.get(x);
		int lastIndex_p = S.priority.size() - 1;
		int lastIndex_e = S.explanation.size() - 1;

		S.priority.set(x, S.priority.get(lastIndex_p));
		S.priority.remove(lastIndex_p);
		S.explanation.set(x, S.explanation.get(lastIndex_e));
		S.explanation.remove(lastIndex_e);

		int tempIndex = x;
		// 비교할 자식노드가 있을 때까지 반복한다.
		while (tempIndex * 2 + 1 < S.priority.size()) {
			int leftChildIndex = tempIndex * 2 + 1;
			int rightChildIndex = tempIndex * 2 + 2;

			if (rightChildIndex < S.priority.size()) { // 자식 모두 존재
				if (S.priority.get(leftChildIndex) < S.priority.get(tempIndex)
						&& S.priority.get(rightChildIndex) < S.priority.get(tempIndex)) { // tempIndex가 클 때
					break;
				} else if (S.priority.get(rightChildIndex) < S.priority.get(leftChildIndex)) { // leftIndex가 클 때
					swap(S, tempIndex, leftChildIndex);
					tempIndex = tempIndex * 2 + 1;
				} else { // rightIndex가 클 때
					swap(S, tempIndex, rightChildIndex);
					tempIndex = tempIndex * 2 + 2;
				}
			} else { // 왼쪽 자식만 존재할 때
				if (S.priority.get(leftChildIndex) < S.priority.get(tempIndex)) { // tempIndex가 클 때
					break;
				} else { // leftIndex가 클 때
					swap(S, tempIndex, leftChildIndex);
					tempIndex = tempIndex * 2 + 1;
				}
			}
		}
		return removeNode;
	}

	// 서로의 자리를 변경한다.
	public static void swap(maxPQ S, int i, int j) {
		int temp1 = S.priority.get(i);
		String temp2 = S.explanation.get(i);
		S.priority.set(i, S.priority.get(j)); // j인덱스의 값을 i인덱스의 자리로 복사
		S.explanation.set(i, S.explanation.get(j));
		i = j; // i=j로 설정
		S.priority.set(i, temp1); // temp를 i인덱스의 자리로 복사
		S.explanation.set(i, temp2);
	}

	// 프로그램의 기능을 출력한다.
	public static void print(maxPQ S) {
		System.out.println("**** 현재 우선 순위 큐에 저장되어 있는 작업 대기 목록은 다음과 같습니다 ****");
		for (int i = 0; i < S.priority.size(); i++) {
			System.out.println("index[" + i + "] " + S.priority.get(i) + "," + S.explanation.get(i));
		}
		System.out.println("---------------------------------------");
		System.out.println("1. 작업 추가   2. 최대값   3. 최대 우선순위 작업 처리");
		System.out.println("4. 원소 키값 증가             5. 작업 제거       6. 종료");
		System.out.println("---------------------------------------");
	}

	// 기능을 실행한다.
	public static void showRun(maxPQ S) {
		Scanner scanner = new Scanner(System.in);
		int input;

		// '종료' 기능이 나올 때까지 반복한다.
		while (true) {
			try {
				print(S);

				input = Integer.parseInt(scanner.nextLine());
				if (input == 1) { // 작업 추가
					System.out.println("추가할 작업 명을 입력하세요 :");
					String name = " " + scanner.nextLine();
					System.out.println("추가할 작업의 우선 순위를 입력하세요 :");
					int number = Integer.parseInt(scanner.nextLine());
					insert(S, number, name);
					System.out.println("새로운 값 [" + number + "," + name + "]을(를) 추가했습니다");
				} else if (input == 2) { // 최대값
					System.out.println("최대값은 [" + max(S) + "] 입니다");
				} else if (input == 3) { // 최대 우선순위 작업 처리(최대값 삭제)
					String removeMax = extract_max(S);
					System.out.println("최대값 [" + removeMax + "]을(를) 삭제했습니다");
				} else if (input == 4) { // 원소 키값 증가
					if (S.priority.size() == 0) { // 에러처리
						throw new Exception();
					}
					System.out.println("현재 작업 대기 목록에 있는 원소 중 수정하고자 하는 원소 인덱스 값을 입력하세요 :");
					int findIndex = Integer.parseInt(scanner.nextLine());
					int originalKey = S.priority.get(findIndex);
					String originalExplanation = S.explanation.get(findIndex);
					System.out.println("선택한 원소의 키 값을 수정하세요 (현재 키 값보다 작아지지 않아야합니다) :");
					int fixKey = Integer.parseInt(scanner.nextLine());
					if (fixKey <= originalKey) { // 에러처리
						System.out.println("**** 수정한 키 값이 현재 키 값보다 작아지지 않아야합니다 ****");
						throw new Exception();
					}
					increase_key(S, findIndex, fixKey);
					System.out.println("선택한 값 [" + originalKey + "," + originalExplanation + "] -> 수정 key: " + fixKey);
				} else if (input == 5) { // 작업 제거
					if (S.priority.size() == 0) { // 에러처리
						throw new Exception();
					}
					System.out.println("현재 작업 대기 목록에 있는 원소 중 삭제하고자 하는 원소 인덱스 값을 입력하세요 :");
					int findIndex = Integer.parseInt(scanner.nextLine());
					String removeNode = delete(S, findIndex);
					System.out.println("[" + removeNode + "]을(를) 삭제했습니다");
				} else if (input == 6) { // 종료
					System.out.println("**** 프로그램을 종료합니다 ****");
					return;
				} else { // 에러처리
					throw new Exception();
				}
			} catch (Exception e) { // 에러처리
				if (S.priority.size() == 0) {
					System.out.println("**** Error 원소가 존재하지 않습니다 ****");
				} else {
					System.out.println("**** Error 잘못 입력하셨습니다  ****");
				}
				System.out.println("초기 화면으로 되돌아갑니다");
			}
			System.out.println("\n");
		}
	}

}