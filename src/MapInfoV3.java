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

    private int[] mValuesOfBlocks=new int[10];

    public int charInMapToInt(int x, int y) {
        return Integer.parseInt(String.valueOf(map[x][y]));
    }
    public boolean isGhost(int x, int y) {
        return String.valueOf(map[x][y]).equals("G");
    }
    public boolean isWall(int x,int y){
        return String.valueOf(map[x][y]).equals("9");
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

        int direction ;
        calculateWeightOfDirections();
        direction=getPreciseDirection();
        return direction;
    }

    //private int[][] weight = new int[2 * radius + 1][2 * radius + 1];
    //系数方阵
    private int[][] coefficients = new int[2 * radius + 1][2 * radius + 1];


    /**
     * 获取以 吃豆人 为中心的 半径为 radius 的方阵，存放在 tmpMatrix 中；
     */
    public void calculateWeightOfDirections() {
        int[][] tmpMatrix = new int[2 * radius + 1][2 * radius + 1];
        
        for (int i = xOfLocation_x - radius , m=0; i <= xOfLocation_x + radius && m<5 ; i++ , m++) {
            for (int j = xOfLocation_x - radius , n=0; j <= xOfLocation_x + radius && n<5; j++ , n++) {

                //System.out.println("i_j: " + i +" / "+ j);

                if (i == xOfLocation_x && j == yOfLocation_y) {
                    tmpMatrix[m][n] = 0;
                } else if (isOutOfBounds(i, j)) {
                    //判断 人物 四周的路是否可行
                    if((m==2 && n==1) || (m==2 && n==3) || (m==1 && n==2) || (m==3 && n==2)){
                        tmpMatrix[m][n] = -1000;
                    }else {
                        tmpMatrix[m][n] = VALUEOFOUT;
                    }

                } else if (isGhost(i, j)) {
                    if((m==2 && n==1) || (m==2 && n==3) || (m==1 && n==2) || (m==3 && n==2)){
                        tmpMatrix[m][n] = -1000;
                    }else {
                        tmpMatrix[m][n] = VALUEOFGHOST;
                    }
                } else if (isWall(i, j)) {
                    if((m==2 && n==1) || (m==2 && n==3) || (m==1 && n==2) || (m==3 && n==2)){
                        tmpMatrix[m][n] = -1000;
                    }else {
                        tmpMatrix[m][n] = VALUEOFWALL;
                    }
                } else {
                    tmpMatrix[m][n] = charInMapToInt(i, j);
                }

            }
        }

//        for(int i=0;i<5;i++){
//            System.out.println(String.);
//        }
        calculate(tmpMatrix);

        calculateEveryBlock(tmpMatrix);
    }

    /**
     *计算 8 个块的总值；
     * @param matrix 以 吃豆人 为中心的方阵；
     */
    public void calculateEveryBlock(int[][] matrix){
        mValuesOfBlocks[1]= matrix[0][0]+matrix[0][1]+matrix[1][0]+matrix[1][1];
        mValuesOfBlocks[2]= matrix[0][2]+matrix[1][2];
        mValuesOfBlocks[3]= matrix[0][3]+matrix[0][4]+matrix[1][3]+matrix[1][4];
        mValuesOfBlocks[4]= matrix[2][0]+matrix[2][1];
        mValuesOfBlocks[5]= 0;
        mValuesOfBlocks[6]= matrix[2][3]+matrix[2][4];
        mValuesOfBlocks[7]= matrix[3][0]+matrix[3][1]+matrix[4][0]+matrix[4][1];
        mValuesOfBlocks[8]= matrix[3][2]+matrix[4][2];
        mValuesOfBlocks[9]= matrix[3][3]+matrix[3][4]+matrix[4][3]+matrix[4][4];
    }

    /**
     * 获取下一步的精确方向
     * @return
     */
    public int getPreciseDirection(){
//        int w=0;int s=0; int a=0; int d=0;
        int wTMP=0;int sTMP=0; int aTMP=0; int dTMP=0;
        int flag_ws; int flag_ad; int bigerInws; int bigerInad;
        wTMP=mValuesOfBlocks[1]+mValuesOfBlocks[2]+mValuesOfBlocks[3];
        sTMP=mValuesOfBlocks[7]+mValuesOfBlocks[8]+mValuesOfBlocks[9];
        aTMP=mValuesOfBlocks[1]+mValuesOfBlocks[4]+mValuesOfBlocks[7];
        dTMP=mValuesOfBlocks[3]+mValuesOfBlocks[6]+mValuesOfBlocks[9];
        System.out.println("wTMP="+wTMP+", sTMP="+sTMP+", aTMP="+aTMP+", dTMP="+dTMP);
        if(wTMP>sTMP){
            flag_ws=0;
            bigerInws=wTMP;
        }else {
            flag_ws=1;
            bigerInws=sTMP;
        }
        if(aTMP>dTMP){
            flag_ad=2;
            bigerInad=aTMP;
        }else {
            flag_ad=3;
            bigerInad=dTMP;
        }

        if(bigerInad>bigerInws){
            return flag_ad;
        }else {
            return flag_ws;
        }

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
