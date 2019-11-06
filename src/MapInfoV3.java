public class MapInfoV3 {

    char[][] map = new char[15][15];

    private final static int VALUEOFWALL = -1;
    private final static int VALUEOFGHOST = -1;
    private final static int VALUEOFOUT = -1;
    //人物在map中的当前坐标
    int xOfLocation_x=0;//人物在
    int yOfLocation_y=0;

    //当前方向 w,s,a,d 对应 0,1,2,3
    int mCurrentDirection;

    //视野半径
    static int radius=2;

    public int charInMapToInt(int x, int y) {
        return Integer.parseInt(String.valueOf(map[x][y]));
    }
    public boolean isGhost(int x, int y) {
        return String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("G");
    }
    public boolean isWall(int x,int y){
        return String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]).equals("9");
    }
    //    public int getValue(int x,int y){
//        return Integer.parseInt(String.valueOf(map[x][y]));
//    }
    public int getValueOfGhost(int distance) {
        return -100;
    }

    public boolean isOutOfBounds(int x, int y) {
        if (x < 0 || x > 14 || y < 0 || y > 14) {
            return true;
        }
        return false;
    }

    //从字符串中创建map
    public void createFromString(String str){
        System.out.println("createFromString");
        String string = str.substring(1,226);

        char[] chars = string.toCharArray();
        //System.out.println(chars);
        int i=0;
        for(int j=0;j<15;j++){
            for (int k=0;k<15;k++){
                map[j][k]=chars[i];
                if(String.valueOf(chars[i]).equals("s")){
                    xOfLocation_x=j;yOfLocation_y=k;
                    mCurrentDirection=1;
                }
                if(String.valueOf(chars[i]).equals("a")){
                    xOfLocation_x=j;yOfLocation_y=k;
                    mCurrentDirection=2;
                }
                if(String.valueOf(chars[i]).equals("d")){
                    xOfLocation_x=j;yOfLocation_y=k;
                    mCurrentDirection=3;
                }
                if(String.valueOf(chars[i]).equals("w")){
                    xOfLocation_x=j;yOfLocation_y=k;
                    mCurrentDirection=0;
                }

                i++;
            }
        }
        print();
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
        int direction = 111;
        return direction;
    }

    private int[][] weight = new int[2 * radius + 1][2 * radius + 1];
    //系数方阵
    private int[][] coefficients = new int[2 * radius + 1][2 * radius + 1];


    public void calculateWeightOfDirections(int direction) {
        int[][] tmpMatrix = new int[2 * radius + 1][2 * radius + 1];
        for (int i = xOfLocation_x - radius; i <= xOfLocation_x + radius; i++) {
            for (int j = xOfLocation_x - radius; j <= xOfLocation_x + radius; j++) {
                if (isOutOfBounds(i, j)) {
                    tmpMatrix[i][j] = VALUEOFOUT;
                } else if (isGhost(i, j)) {
                    tmpMatrix[i][j] = VALUEOFGHOST;
                } else if (isWall(i, j)) {
                    tmpMatrix[i][j] = VALUEOFWALL;
                } else if (i == xOfLocation_x && j == yOfLocation_y) {
                    tmpMatrix[i][j] = 0;
                } else {
                    tmpMatrix[i][j] = charInMapToInt(i, j);
                }
            }
        }
        calculate(tmpMatrix);

    }

    public void calculate(int[][] tmpMatrix) {

    }

    public void print(){
        for(int i=0;i<15;i++){
            System.out.println(String.copyValueOf(map[i]));
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

    /**
     * 获取value
     * @param s  为null时解析第二参数
     * @param i
     * @return
     */
    private int getMapValue(String s , int i) {

        if (s != null) {

            switch (s) {
                case "0":
                    return 0;
                case "1":
                    return 1;
                case "2":
                    return 2;
                case "3":
                    return 3;
                case "4":
                    return 4;
                case "5":
                    return 5;
            }
        }

        if (i != -1) {

            switch (i) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
            }
        }
        return 0;
    }
}
