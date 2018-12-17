# Algorithms 14주차 과제
1. Dynamic programming을 이용하여 Ford Fulkerson Algorithm을 수행하는 프로그램 구현.
	- 모든 가능한 경로에 대해 경로 및 최대 유량 출력
	- 최대 용량 테이블은 컴파일 전에 미리 임의설정
	
2. Ford Fulkerson Algorithm 설명
	- Capacity : 각 간선에 흐를 수 있는 최대 유량
	- Flow : 각 간선에 흐르는 실제 유량
	- Residual Capacity : Capacity에서 Flow를 뺀 잔여랑
	- Source : 출발 지점
	- Sink : 도착 지점
	- Ford Fulkerson Algorithm의 규칙
		* 용량 제한 속성: f(u,v) <= c(u,v)
		* 유량의 대칭성 : f(u,v) = -f(v,u)
		* 유량의 보존 : 각 정점에 들어오는 유량과 나가는 유량은 항상 같음

3. Ford Fulkerson Algorithm의 과정
	1. 시작 노드와 도착 노드를 연결하는 Path 탐색
	2. Path에서 흐를 수 있는 최대 유량 도출
	3. Path의 각 Edge에 유량 적용
	4. Residual graph로 변환
	5. 1~5 과정 반복
	6. 더 이상 Path가 없을 경우 지금까지 얻은 모든 Path에서의 최대 유량 값을 더해서 전체 그래프에 대한 최대 유량 계산

