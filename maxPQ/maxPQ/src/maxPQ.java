import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// 201602082 ������
public class maxPQ {

	final static String file = "data03.txt";
	ArrayList<Integer> priority;
	ArrayList<String> explanation;

	public maxPQ() {
		priority = new ArrayList<Integer>();
		explanation = new ArrayList<String>();
	}

	// maxPQ��ü�� �켱 ����_priority(�۾� ����_explanation) �迭�� ����� �����Ѵ�.
	public static int size(maxPQ S) {
		return S.priority.size();
	}

	public static void main(String[] args) {

		maxPQ maxPriorityQueue = new maxPQ();

		// ���ĵ��� ���� file �о����
		try {
			int priorityCount = 0;
			BufferedReader in = new BufferedReader(new FileReader(file)); // data03.txt �ҷ���
			String line = in.readLine(); // ������ �Է¹޾� ����
			while (line != null) { // null�� �ƴҶ����� (������ ������ �б�)
				StringTokenizer parser = new StringTokenizer(line, ",");
				while (parser.hasMoreTokens()) {
					priorityCount++;
					if ((priorityCount % 2) == 0) { // ������� �޾ƿ�
						String sub = parser.nextToken();
						maxPriorityQueue.explanation.add(sub);
					} else { // �켱������ �޾ƿ�
						int num = Integer.parseInt(parser.nextToken());
						maxPriorityQueue.priority.add(num);
					}
				}
				line = in.readLine(); // ������ �Է¹޾� ����
			}
			in.close(); // ���� �ݱ�
		} catch (FileNotFoundException e) { // ����ó��
			System.err.println(e);
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}

		// Max Priority Queue�� �����Ѵ�.
		BUILD_MAX_HEAP(maxPriorityQueue, size(maxPriorityQueue));

		// ���α׷��� ����� ���� & �����Ѵ�.
		showRun(maxPriorityQueue);
	}

	// Max Priority Queue�� �����Ѵ�.
	public static void BUILD_MAX_HEAP(maxPQ S, int n) {
		for (int i = n / 2 - 1; i >= 0; i--) { // ������� �� ������ ���γ����� (����������) �ҷ���
			MAX_HEAPIFY(S, i, n);
		}
	}

	public static void MAX_HEAPIFY(maxPQ S, int i, int n) {
		int j = 0;
		while (i < n / 2) {// ���γ�� 0~n/2-1 �׷��Ƿ� ������尡 �ƴϷ��� n/2���� �۾ƾ���.
			// ������尡 �ƴҶ���� ������ �����Ƿ� ������ �ڽ��� �ִ�.
			int tempPriority = S.priority.get(i); // i�ε����� priority ������ ����

			if (2 * i + 1 < S.priority.size() && 2 * i + 2 < S.priority.size()) { // �ڽ� ��� ����
				if (S.priority.get(2 * i + 1) < S.priority.get(2 * i + 2)) // ���ؼ� j�� ū �ڽ����� ����
					j = 2 * i + 2;
				else
					j = 2 * i + 1;
			} else if (2 * i + 1 < S.priority.size()) { // ���� �ڽĸ� �����Ҷ�
				j = 2 * i + 1; // ū �ڽ��� �����ڽ�
			} else { // ������ �ڽĸ� �����Ҷ�
				j = 2 * i + 2; // ū �ڽ��� �������ڽ�
			}

			if (S.priority.get(j) > tempPriority) {
				swap(S, i, j); // �ڸ� �ٲٱ�
				MAX_HEAPIFY(S, j, size(S)); // �ڽĵ鵵 heapify�ؾ���.
			} else
				break;
		}
	}

	// S�� ���� x�� ���� �ִ´�.
	public static void insert(maxPQ S, int number, String name) {
		S.priority.add(number);
		S.explanation.add(name);

		if (S.priority.size() == 1) { // ���� ���� �����Ƿ� �ٷ� ����
			return;
		}

		// �θ� ��� ���� �ö󰡸鼭 ���� �� -> �θ� ����� ������ �� ū ���� ��ġ
		int currentLoc = S.priority.size() - 1;
		int parentLoc = (currentLoc - 1) / 2;
		while ((currentLoc > 0) && (S.priority.get(currentLoc) > S.priority.get(parentLoc))) { // �θ����� ������ �����ϴ� ���� �� ũ��
			swap(S, currentLoc, parentLoc);
			currentLoc = parentLoc;
			parentLoc = (currentLoc - 1) / 2; // ������ ���� ���� ��, �� ��ġ�� ���� �ö�
		}
	}

	// S���� Ű ���� �ִ��� ���Ҹ� �����Ѵ�.
	public static String max(maxPQ S) {
		return S.priority.get(0) + "," + S.explanation.get(0);
	}

	// S���� Ű ���� �ִ��� ���Ҹ� �����Ѵ�.
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

	// ���� x�� Ű ���� k�� ������Ų��. �̶� k�� x�� ���� Ű ������ �۾����� �ʴ´ٰ� �����Ѵ�.
	public static void increase_key(maxPQ S, int x, int k) {
		S.priority.set(x, k);
		BUILD_MAX_HEAP(S, size(S));
	}

	// S���� ��� x�� �����Ѵ�. ���� �� Max heap�� �����Ѵ�.
	// ������ ��ġ�������� �Ʒ��� �ڽĳ���� �񱳸� ���� ��� ���� ������ ���� �����ϸ� max heap������ �����Ѵ�.
	public static String delete(maxPQ S, int x) {
		String removeNode = S.priority.get(x) + "," + S.explanation.get(x);
		int lastIndex_p = S.priority.size() - 1;
		int lastIndex_e = S.explanation.size() - 1;

		S.priority.set(x, S.priority.get(lastIndex_p));
		S.priority.remove(lastIndex_p);
		S.explanation.set(x, S.explanation.get(lastIndex_e));
		S.explanation.remove(lastIndex_e);

		int tempIndex = x;
		// ���� �ڽĳ�尡 ���� ������ �ݺ��Ѵ�.
		while (tempIndex * 2 + 1 < S.priority.size()) {
			int leftChildIndex = tempIndex * 2 + 1;
			int rightChildIndex = tempIndex * 2 + 2;

			if (rightChildIndex < S.priority.size()) { // �ڽ� ��� ����
				if (S.priority.get(leftChildIndex) < S.priority.get(tempIndex)
						&& S.priority.get(rightChildIndex) < S.priority.get(tempIndex)) { // tempIndex�� Ŭ ��
					break;
				} else if (S.priority.get(rightChildIndex) < S.priority.get(leftChildIndex)) { // leftIndex�� Ŭ ��
					swap(S, tempIndex, leftChildIndex);
					tempIndex = tempIndex * 2 + 1;
				} else { // rightIndex�� Ŭ ��
					swap(S, tempIndex, rightChildIndex);
					tempIndex = tempIndex * 2 + 2;
				}
			} else { // ���� �ڽĸ� ������ ��
				if (S.priority.get(leftChildIndex) < S.priority.get(tempIndex)) { // tempIndex�� Ŭ ��
					break;
				} else { // leftIndex�� Ŭ ��
					swap(S, tempIndex, leftChildIndex);
					tempIndex = tempIndex * 2 + 1;
				}
			}
		}
		return removeNode;
	}

	// ������ �ڸ��� �����Ѵ�.
	public static void swap(maxPQ S, int i, int j) {
		int temp1 = S.priority.get(i);
		String temp2 = S.explanation.get(i);
		S.priority.set(i, S.priority.get(j)); // j�ε����� ���� i�ε����� �ڸ��� ����
		S.explanation.set(i, S.explanation.get(j));
		i = j; // i=j�� ����
		S.priority.set(i, temp1); // temp�� i�ε����� �ڸ��� ����
		S.explanation.set(i, temp2);
	}

	// ���α׷��� ����� ����Ѵ�.
	public static void print(maxPQ S) {
		System.out.println("**** ���� �켱 ���� ť�� ����Ǿ� �ִ� �۾� ��� ����� ������ �����ϴ� ****");
		for (int i = 0; i < S.priority.size(); i++) {
			System.out.println("index[" + i + "] " + S.priority.get(i) + "," + S.explanation.get(i));
		}
		System.out.println("---------------------------------------");
		System.out.println("1. �۾� �߰�   2. �ִ밪   3. �ִ� �켱���� �۾� ó��");
		System.out.println("4. ���� Ű�� ����             5. �۾� ����       6. ����");
		System.out.println("---------------------------------------");
	}

	// ����� �����Ѵ�.
	public static void showRun(maxPQ S) {
		Scanner scanner = new Scanner(System.in);
		int input;

		// '����' ����� ���� ������ �ݺ��Ѵ�.
		while (true) {
			try {
				print(S);

				input = Integer.parseInt(scanner.nextLine());
				if (input == 1) { // �۾� �߰�
					System.out.println("�߰��� �۾� ���� �Է��ϼ��� :");
					String name = " " + scanner.nextLine();
					System.out.println("�߰��� �۾��� �켱 ������ �Է��ϼ��� :");
					int number = Integer.parseInt(scanner.nextLine());
					insert(S, number, name);
					System.out.println("���ο� �� [" + number + "," + name + "]��(��) �߰��߽��ϴ�");
				} else if (input == 2) { // �ִ밪
					System.out.println("�ִ밪�� [" + max(S) + "] �Դϴ�");
				} else if (input == 3) { // �ִ� �켱���� �۾� ó��(�ִ밪 ����)
					String removeMax = extract_max(S);
					System.out.println("�ִ밪 [" + removeMax + "]��(��) �����߽��ϴ�");
				} else if (input == 4) { // ���� Ű�� ����
					if (S.priority.size() == 0) { // ����ó��
						throw new Exception();
					}
					System.out.println("���� �۾� ��� ��Ͽ� �ִ� ���� �� �����ϰ��� �ϴ� ���� �ε��� ���� �Է��ϼ��� :");
					int findIndex = Integer.parseInt(scanner.nextLine());
					int originalKey = S.priority.get(findIndex);
					String originalExplanation = S.explanation.get(findIndex);
					System.out.println("������ ������ Ű ���� �����ϼ��� (���� Ű ������ �۾����� �ʾƾ��մϴ�) :");
					int fixKey = Integer.parseInt(scanner.nextLine());
					if (fixKey <= originalKey) { // ����ó��
						System.out.println("**** ������ Ű ���� ���� Ű ������ �۾����� �ʾƾ��մϴ� ****");
						throw new Exception();
					}
					increase_key(S, findIndex, fixKey);
					System.out.println("������ �� [" + originalKey + "," + originalExplanation + "] -> ���� key: " + fixKey);
				} else if (input == 5) { // �۾� ����
					if (S.priority.size() == 0) { // ����ó��
						throw new Exception();
					}
					System.out.println("���� �۾� ��� ��Ͽ� �ִ� ���� �� �����ϰ��� �ϴ� ���� �ε��� ���� �Է��ϼ��� :");
					int findIndex = Integer.parseInt(scanner.nextLine());
					String removeNode = delete(S, findIndex);
					System.out.println("[" + removeNode + "]��(��) �����߽��ϴ�");
				} else if (input == 6) { // ����
					System.out.println("**** ���α׷��� �����մϴ� ****");
					return;
				} else { // ����ó��
					throw new Exception();
				}
			} catch (Exception e) { // ����ó��
				if (S.priority.size() == 0) {
					System.out.println("**** Error ���Ұ� �������� �ʽ��ϴ� ****");
				} else {
					System.out.println("**** Error �߸� �Է��ϼ̽��ϴ�  ****");
				}
				System.out.println("�ʱ� ȭ������ �ǵ��ư��ϴ�");
			}
			System.out.println("\n");
		}
	}

}