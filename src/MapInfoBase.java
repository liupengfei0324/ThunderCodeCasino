public abstract class MapInfoBase {

    char[][] map = new char[15][15];

    //当前吃豆人位置坐标
    int xOfLocation_x = 0;
    int yOfLocation_y = 0;

    // 当前方向 w,s,a,d 对应 0,1,2,3
    int mCurrentDirection;
    // 当前走了多少步
    int mStepNum = 0;

    /**
     * 地图数据转换成二维
     *
     * @param str 初始地图字符串
     */
    public void createFromString(String str) {
//        System.out.println("createFromString");
        String string = str.substring(1, 226);

        char[] chars = string.toCharArray();
        // System.out.println(chars);
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

                i++;
            }
        }
//        print();
        //当前步数是否为奇偶
        if (mStepNum == 0) {
            mStepNum = 1;
        } else {
            mStepNum = 0;
        }
        System.out.println("mStepNum: " + mStepNum);
        //print();
        //System.out.println();
        updateMap();
        print();
    }

    /**
     * 更新地图数据 - 添加”墙“
     * <p>
     * ? :  顺序遍历，可能会对部分 度为2 的方块产生影响。
     */
    public void updateMap() {
        int flagOfEachNode = 0;
        for (int j = 0; j < 15; j++) {
            for (int k = 0; k < 15; k++) {
                flagOfEachNode = 0;
                if (j != xOfLocation_x || k != yOfLocation_y) {
                    if (!(isOutOfBounds(j - 1, k) || isWall(j - 1, k))) {
                        flagOfEachNode++;
                    }
                    if (!(isOutOfBounds(j + 1, k) || isWall(j + 1, k))) {
                        flagOfEachNode++;
                    }
                    if (!(isOutOfBounds(j, k - 1) || isWall(j, k - 1))) {
                        flagOfEachNode++;
                    }
                    if (!(isOutOfBounds(j, k + 1) || isWall(j, k + 1))) {
                        flagOfEachNode++;
                    }
                    if (flagOfEachNode == 1) {
//                        System.out.println(map[j][k] + "--> , j=" + j + ",k=" + k + " | flagNode :" + flagOfEachNode);
                        map[j][k] = "9".charAt(0);
//                        System.out.println(map[j][k] + "");
                    }
                }

            }
        }
    }

    /**
     * 获取下一步的指令
     *
     * @return
     */
    public String getNextCommand() {

        int direction = getNextDirection();
        switch (direction) {
            case 0:
                // xOfLocation_x=xOfLocation_x-1;
                Client.OLD_COMMAND = "[w]";
                return "[w]";
            case 1:
                // xOfLocation_x=xOfLocation_x+1;
                Client.OLD_COMMAND = "[s]";
                return "[s]";
            case 2:
                // yOfLocation_y=yOfLocation_y-1;
                Client.OLD_COMMAND = "[a]";
                return "[a]";
            case 3:
                // yOfLocation_y=yOfLocation_y+1;
                Client.OLD_COMMAND = "[d]";
                return "[d]";
            default:
        }
        return "[w]";
    }

    /**
     * 获取下次行动的方向
     * 为子类留重写方法的接口
     *
     * @return
     */
    public abstract int getNextDirection();

    /**
     * 打印二维数组地图
     */
    public void print() {
        for (int i = 0; i < 15; i++) {
            System.out.println(String.copyValueOf(map[i]));
        }
    }

    /**
     * 将char转为int
     *
     * @param x
     * @param y
     * @return
     */
    public int charInMapToInt(int x, int y) {
        return Integer.parseInt(String.valueOf(map[x][y]));
    }

    public boolean isGhost(int x, int y) {
        return String.valueOf(map[x][y]).equals("G");
    }

    public boolean isWall(int x, int y) {
        return String.valueOf(map[x][y]).equals("9");
    }

    public int getValue(int x, int y) {
        return Integer.parseInt(String.valueOf(map[x][y]));
    }

    public boolean isOne(int x,int y) {
        return String.valueOf(map[x][y]).equals("1");
    }
    
    public boolean isTwo(int x,int y) {
        return String.valueOf(map[x][y]).equals("2");
    }
    
    public boolean isThree(int x,int y) {
        return String.valueOf(map[x][y]).equals("3");
    }
    
    public boolean isFour(int x,int y) {
        return String.valueOf(map[x][y]).equals("4");
    }
    
    public boolean isEmpty(int x,int y) {
        return String.valueOf(map[x][y]).equals("0");
    }
    
    public boolean isFive(int x,int y) {
        return String.valueOf(map[x][y]).equals("5");
    }
    /**
     * 判断幽灵是否即将移动
     *
     * @return
     */
    public boolean ghostWillMove() {
        if (mStepNum == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 坐标是否越界
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isOutOfBounds(int x, int y) {
        if (x < 0 || x > 14 || y < 0 || y > 14) {
            return true;
        }
        return false;
    }

}
