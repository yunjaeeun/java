package com.ohgiraffers.chap03.section01.gragh_search;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Application2 {

    public static BufferedReader toBufferedReader(String str) {
        InputStream is = new ByteArrayInputStream(str.getBytes());
        return new BufferedReader(new InputStreamReader(is));
    }

    static Queue<Node> q = new LinkedList<>();

    /* 설명. 상하좌우 개념의 좌표 배열들 */
    static int dirX[] = {0, 0, -1, 1};    // x와 y의 0번 인덱스 = 하, 1번 인덱스 = 상, 2번 인덱스 = 좌, 3번 인덱스 = 우
    static int dirY[] = {-1, 1, 0, 0};

    /* 설명. 배추밭 */
    static int map[][];                   // 표를 저장할 2차원 배열

    /* 설명. 방문한 배추 좌표 배열 */
    static boolean visit[][];

    /* 설명. 현재 위치에 대한 좌표 */
    static int cur_x, cur_y;

    /* 설명. 배추밭의 크기와 심어진 배추의 갯수 */
    static int N, M, K;

    /* 설명. 필요한 배추흰지렁이 수 */
    static int count;


    /* 설명. x와 y좌표를 인지하는 static 내부 클래스 */
    static class Node {
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int solution(String input) throws IOException {
        BufferedReader br = toBufferedReader(input);
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        M = Integer.valueOf(st.nextToken());
        N = Integer.valueOf(st.nextToken());
        K = Integer.valueOf(st.nextToken());

        map = new int[N][M];
        visit = new boolean[N][M];

        /* 설명. 배추심기 */
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.valueOf(st.nextToken());
            int y = Integer.valueOf(st.nextToken());

            /* 설명. 입력 받은 x, y 좌표와 배열을 만들 때의 순서(행, 열)는 반대의 개념이다. */
            map[y][x] = 1;
        }

        count = 0;
        for (int i = 0; i < N; i++) {           // 행
            for (int j = 0; j < M; j++) {       // 열
                if (visit[i][j] == false && map[i][j] == 1) {   // 방문하지 않았으면서 배추가 심어져있으면
                    count++;                                    // 배추를 심음
                    bfs(j, i);                                  // bfs의 인자는 x좌표, y좌표 순으로 받을 것이므로 i와 j를 확인하고 넘겨준다
                }
            }

        }
        return count;
    }

    /* 설명. 상하좌우에 배추가 심어져 있으면 한번씩 동작(기본적으로는 이렇고 방문 배열에 대해서는 다른 으미) */
    private static void bfs(int x, int y) {
         q.offer(new Node(x,y));
         visit[y][x] = true;

         /* 설명. 연속적으로 상하좌우에 배추가 심어져 있다면 반복한다. */
         while (!q.isEmpty()) {
             Node node = q.poll();

             /* 설명. 상하좌우 살펴보기 */
             for (int i = 0; i < 4; i++) {
                 cur_x = node.x + dirX[i];
                 cur_y = node.y + dirY[i];

                 /* 설명. 지금 보는 방향이 좌표로써 존재하면서 방문한 적이 없고 배추가 심어져 있다면 */
                 if(range_check() && visit[cur_y][cur_x] == false && map[cur_y][cur_x] == 1){
                     q.offer(new Node(cur_x, cur_y));       // 붙어있는 배추의 위치가 쌓이는 곳
                     visit[cur_y][cur_x] = true;
                 }
             }
         }
    }

    private static boolean range_check() {
        return cur_x >= 0 && cur_x < M && cur_y >= 0 && cur_y < N;      // 좌표가 벗어나면 실행되지 않게.
    }

}
