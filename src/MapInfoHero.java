import java.util.ArrayList;
import java.util.List;

public class MapInfoHero {


    char[][] map = new char[15][15];

    //人物在map中的当前坐标
    int xOfLocation_x = 0;//人物在
    int yOfLocation_y = 0;

    //当前方向 w,s,a,d 对应 0,1,2,3
    int mCurrentDirection;

    List<Ghost> ghostList = new ArrayList<>();
    List<GhostDistance> ghostDistanceList = new ArrayList<>();
    List<GhostNextMove> ghostNextMoveList = new ArrayList<>();

    //从字符串中创建map
    public void createFromString(String str) {
        System.out.println("createFromString");
        String string = str.substring(1, 226);

        char[] chars = string.toCharArray();
        //System.out.println(chars);
        int i = 0;
        for (int j = 0; j < 15; j++) {
            for (int k = 0; k < 15; k++) {
                map[j][k] = chars[i];
                if (String.valueOf(chars[i]).equals("s")) {
                    xOfLocation_x = j;
                    yOfLocation_y = k;
                    mCurrentDirection = 1;
                }
                if (String.valueOf(chars[i]).equals("a")) {
                    xOfLocation_x = j;
                    yOfLocation_y = k;
                    mCurrentDirection = 2;
                }
                if (String.valueOf(chars[i]).equals("d")) {
                    xOfLocation_x = j;
                    yOfLocation_y = k;
                    mCurrentDirection = 3;
                }
                if (String.valueOf(chars[i]).equals("w")) {
                    xOfLocation_x = j;
                    yOfLocation_y = k;
                    mCurrentDirection = 0;
                }
                if (String.valueOf(chars[i]).equals("G")) {
                    ghostDistanceList.add(new GhostDistance(new Ghost(j, k)));
                }
                i++;
            }
        }
        //print();
    }

    public String getNextCommand() {

        int direction = getNextDirection();
        switch (direction) {
            case 0:
                //xOfLocation_x=xOfLocation_x-1;
                Client.OLD_COMMAND = "[w]";
                return "[w]";
            case 1:
                //xOfLocation_x=xOfLocation_x+1;
                Client.OLD_COMMAND = "[s]";
                return "[s]";
            case 2:
                //yOfLocation_y=yOfLocation_y-1;
                Client.OLD_COMMAND = "[a]";
                return "[a]";
            case 3:
                //yOfLocation_y=yOfLocation_y+1;
                Client.OLD_COMMAND = "[d]";
                return "[d]";
            default:
        }
        return "[wait]";
    }

    public int getNextDirection() {

        return 0;
    }

    public void setGhostNextMove() {
        for (int i = 0; i < 3; i++) {
            if ((xOfLocation_x - ghostList.get(0).getxOfGhost() > 0)) {

                if ((yOfLocation_y - ghostList.get(0).getyOfGhost() > 0)) {
                    //鬼位于小人左上角
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost() - 1, ghostList.get(0).getyOfGhost()));//预测鬼向下走的坐标
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost(), ghostList.get(0).getyOfGhost() +1)); //预测向右走的坐标
                } else if ((yOfLocation_y - ghostList.get(0).getyOfGhost() < 0)) {
                    //鬼位于小人右上角
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost() - 1, ghostList.get(0).getyOfGhost()));//预测鬼向下走的坐标
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost(), ghostList.get(0).getyOfGhost() -1));//预测鬼向左走的坐标
                } else {
                    //鬼位于正上方
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost() - 1, ghostList.get(0).getyOfGhost()));//预测鬼向下走的坐标
                }
            } else if ((xOfLocation_x - ghostList.get(0).getxOfGhost() < 0)) {
                if ((yOfLocation_y - ghostList.get(0).getyOfGhost() > 0)) {
                    //鬼位于小人左上角
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost() + 1, ghostList.get(0).getyOfGhost()));//预测鬼向上走的坐标
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost(), ghostList.get(0).getyOfGhost() +1)); //预测鬼向右的坐标
                } else if ((yOfLocation_y - ghostList.get(0).getyOfGhost() < 0)) {
                    //鬼位于小人右上角
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost() + 1, ghostList.get(0).getyOfGhost()));//预测鬼向上走的坐标
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost(), ghostList.get(0).getyOfGhost() -1));//预测鬼向左走的坐标
                } else {
                    //鬼位于正下方
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost() + 1, ghostList.get(0).getyOfGhost()));//预测鬼向上走的坐标
                }
            } else {
                if ((yOfLocation_y - ghostList.get(0).getyOfGhost() > 0)) {
                    //鬼位于正左方
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost(), ghostList.get(0).getyOfGhost() +1)); //预测鬼向右的坐标
                } else if ((yOfLocation_y - ghostList.get(0).getyOfGhost() < 0)) {
                    //鬼位于正右方
                    ghostNextMoveList.add(new GhostNextMove(ghostList.get(0).getxOfGhost(), ghostList.get(0).getyOfGhost() -1));//预测鬼向左走的坐标
                }
            }

        }

    }

    //鬼
    class Ghost {
        private int xOfGhost;
        private int yOfGhost;

        public Ghost(int xOfGhost, int yOfGhost) {
            this.xOfGhost = xOfGhost;
            this.yOfGhost = yOfGhost;
        }

        public int getxOfGhost() {
            return xOfGhost;
        }

        public void setxOfGhost(int xOfGhost) {
            this.xOfGhost = xOfGhost;
        }

        public int getyOfGhost() {
            return yOfGhost;
        }

        public void setyOfGhost(int yOfGhost) {
            this.yOfGhost = yOfGhost;
        }
    }

    class GhostNextMove {
        private int xOfGhostNextMove;
        private int yOfGhostNextMove;

        public GhostNextMove(int xOfGhostNextMove, int yOfGhostNextMove) {
            this.xOfGhostNextMove = xOfGhostNextMove;
            this.yOfGhostNextMove = yOfGhostNextMove;
        }

        public int getxOfGhostNextMove() {
            return xOfGhostNextMove;
        }

        public void setxOfGhostNextMove(int xOfGhostNextMove) {
            this.xOfGhostNextMove = xOfGhostNextMove;
        }

        public int getyOfGhostNextMove() {
            return yOfGhostNextMove;
        }

        public void setyOfGhostNextMove(int yOfGhostNextMove) {
            this.yOfGhostNextMove = yOfGhostNextMove;
        }
    }

    //鬼距离人的位置
    class GhostDistance {
        private int xOfGhostDistance;
        private int yOfGhostDistance;
        private Ghost ghost;

        public GhostDistance(Ghost ghost) {
            this.ghost = ghost;
            this.xOfGhostDistance = Math.abs(ghost.getxOfGhost() - xOfLocation_x);
            this.yOfGhostDistance = Math.abs(ghost.getyOfGhost() - yOfLocation_y);
        }

        public int getxOfGhostDistance() {
            return xOfGhostDistance;
        }

        public void setxOfGhostDistance(int xOfGhostDistance) {
            this.xOfGhostDistance = xOfGhostDistance;
        }

        public int getyOfGhostDistance() {
            return yOfGhostDistance;
        }

        public void setyOfGhostDistance(int yOfGhostDistance) {
            this.yOfGhostDistance = yOfGhostDistance;
        }

        public Ghost getGhost() {
            return ghost;
        }

        public void setGhost(Ghost ghost) {
            this.ghost = ghost;
        }
    }

}
