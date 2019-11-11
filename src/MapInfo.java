public class MapInfo extends MapInfoBase {

    //幽灵危险等级，从高到低：1——>3
    private final int GHOST_1 = -30;
    private final int GHOST_2 = -20;
    private final int GHOST_3 = -12;

    //墙的危险等级，从高到低：1——>2
    private final int WALL_1 = -8;
    private final int WALL_2 = -4;

    /**
     * 获取下次行动的方向
     *
     * @return
     */
    @Override
    public int getNextDirection() {
        int a, w, s, d = 0;
        int[] valuesFlag = new int[4];
        int LastDirection = 2;
        for (int i = 0; i < 4; i++) {
            valuesFlag[i] = -1000;
        }

        //正上方
        if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y) && isGhost(xOfLocation_x - 1, yOfLocation_y)) {
            if ((mCurrentDirection == 1 || mCurrentDirection == 2 || mCurrentDirection ==3) ){
                return mCurrentDirection;
            }
        }
        if (xOfLocation_x - 1 >= 0 && map[xOfLocation_x - 1][yOfLocation_y] != "9".charAt(0)
                && map[xOfLocation_x - 1][yOfLocation_y] != "G".charAt(0)) {
            w = -1;
            valuesFlag[0] = 0;
        }

        //正下方
        if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y) && isGhost(xOfLocation_x + 1, yOfLocation_y)) {
            if ((mCurrentDirection == 0 || mCurrentDirection == 2 || mCurrentDirection ==3)){
                return mCurrentDirection;
            }
        }
        if (xOfLocation_x + 1 <= 14 && map[xOfLocation_x + 1][yOfLocation_y] != "9".charAt(0)
                && map[xOfLocation_x + 1][yOfLocation_y] != "G".charAt(0)) {
            s = -1;
            valuesFlag[1] = 0;
        }

        //正左方
        if (!isOutOfBounds(xOfLocation_x, yOfLocation_y - 1) && isGhost(xOfLocation_x, yOfLocation_y - 1)) {
            if ((mCurrentDirection == 0 || mCurrentDirection == 1 || mCurrentDirection ==3) ){
                return mCurrentDirection;
            }
        }
        if (yOfLocation_y - 1 >= 0 && map[xOfLocation_x][yOfLocation_y - 1] != "9".charAt(0)
                && map[xOfLocation_x][yOfLocation_y - 1] != "G".charAt(0)) {
            a = -1;
            valuesFlag[2] = 0;
        }

        //正右方
        if (!isOutOfBounds(xOfLocation_x, yOfLocation_y + 1) && isGhost(xOfLocation_x, yOfLocation_y + 1)) {
            if ((mCurrentDirection == 0 || mCurrentDirection == 1 || mCurrentDirection ==2)  ){
                return mCurrentDirection;
            }
        }
        if (yOfLocation_y + 1 <= 14 && map[xOfLocation_x][yOfLocation_y + 1] != "9".charAt(0)
                && map[xOfLocation_x][yOfLocation_y + 1] != "G".charAt(0)) {
            d = -1;
            valuesFlag[3] = 0;
        }

        // 幽灵在四周对角的情况规避
        if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGhost(xOfLocation_x - 1, yOfLocation_y - 1)
                && !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1)
                && isGhost(xOfLocation_x + 1, yOfLocation_y + 1)&&!ghostWillMove()) {
            return mCurrentDirection;
        }
        if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGhost(xOfLocation_x - 1, yOfLocation_y + 1)
                && !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1)
                && isGhost(xOfLocation_x + 1, yOfLocation_y - 1)&&!ghostWillMove()) {
            return mCurrentDirection;
        }
        //幽灵在四周对角点
        if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGhost(xOfLocation_x - 1, yOfLocation_y - 1)) {
            if ((mCurrentDirection == 0 || mCurrentDirection == 2) && !ghostWillMove()) {
                return mCurrentDirection;
            } else {
                valuesFlag[0] += -500;
                valuesFlag[2] += -500;
            }

        }
        if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGhost(xOfLocation_x - 1, yOfLocation_y + 1)) {
            if ((mCurrentDirection == 0 || mCurrentDirection == 3) && !ghostWillMove()) {
                return mCurrentDirection;
            } else {
                valuesFlag[0] += -500;
                valuesFlag[3] += -500;
            }
        }
        if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1) && isGhost(xOfLocation_x + 1, yOfLocation_y - 1)) {
            if ((mCurrentDirection == 1 || mCurrentDirection == 2) && !ghostWillMove()) {
                return mCurrentDirection;
            } else {
                valuesFlag[1] += -500;
                valuesFlag[2] += -500;
            }
        }
        if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1) && isGhost(xOfLocation_x + 1, yOfLocation_y + 1)) {
            if ((mCurrentDirection == 1 || mCurrentDirection == 3) && !ghostWillMove()) {
                return mCurrentDirection;
            } else {
                valuesFlag[1] += -500;
                valuesFlag[3] += -500;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (valuesFlag[i] == 0) {
                // System.out.println("new i="+i);
                valuesFlag[i] = getValuesForDirections(i);
            }
        }

        int valueTemp, flag;
        int valueTemp2, flag2;
        if (valuesFlag[0] > valuesFlag[1]) {
            valueTemp = valuesFlag[0];
            flag = 0;
        } else if (valuesFlag[0] < valuesFlag[1]) {
            valueTemp = valuesFlag[1];
            flag = 1;
        } else {
            valueTemp = valuesFlag[1];
            if (LastDirection == 0) {
                flag = 0;
            } else if (LastDirection == 1) {
                flag = 1;
            } else {
                flag = 1;
            }
        }
        System.out.println("valuesFlag[0]=" + valuesFlag[0] + ", valuesFlag[1]=" + valuesFlag[1]);
        if (valuesFlag[2] > valuesFlag[3]) {
            valueTemp2 = valuesFlag[2];
            flag2 = 2;
        } else if (valuesFlag[2] < valuesFlag[3]) {
            valueTemp2 = valuesFlag[3];
            flag2 = 3;
        } else {
            valueTemp2 = valuesFlag[3];
            if (LastDirection == 2) {
                flag2 = 2;
            } else if (LastDirection == 3) {
                flag2 = 3;
            } else {
                flag2 = 3;
            }
        }
        System.out.println("valuesFlag[2]=" + valuesFlag[2] + ", valuesFlag[3]=" + valuesFlag[3]);
        if (valueTemp > valueTemp2) {
            LastDirection = flag;
            return flag;
        } else if (valueTemp < valueTemp2) {
            LastDirection = flag2;
            return flag2;
        } else {
            if (LastDirection == flag) {
                return flag;
            } else if (LastDirection == flag2) {
                return flag2;
            } else {
                LastDirection = flag;
                return flag;
            }
        }
    }

    //主要逻辑处理
    public int getValuesForDirections(int i) {
        int value = 0;

        switch (i) {
            case 0:
                value = upCommad(value);
                break;
            case 1:
                value = downCommand(value);
                break;
            case 2:
                value = leftCommand(value);
                break;
            case 3:
                value = rightCommand(value);
                break;
        }

        return value;
    }

    private int upCommad(int value) {
        // (x-1,y)
        if (!isOutOfBounds(xOfLocation_x-1, yOfLocation_y)&&!isGhost(xOfLocation_x-1, yOfLocation_y)&&!isWall(xOfLocation_x-1, yOfLocation_y)&&charInMapToInt(xOfLocation_x - 1, yOfLocation_y) >= 1&&
        		!isOutOfBounds(xOfLocation_x-2, yOfLocation_y)&&!isGhost(xOfLocation_x-2, yOfLocation_y)&&!isWall(xOfLocation_x-2, yOfLocation_y)&&charInMapToInt(xOfLocation_x-2 , yOfLocation_y) >= 1&&
        		!isOutOfBounds(xOfLocation_x-3, yOfLocation_y)&&!isGhost(xOfLocation_x-3, yOfLocation_y)&&!isWall(xOfLocation_x-3, yOfLocation_y)&&charInMapToInt(xOfLocation_x-3 , yOfLocation_y) >= 1&&
        		!isOutOfBounds(xOfLocation_x-4, yOfLocation_y)&&!isGhost(xOfLocation_x-4, yOfLocation_y)&&!isWall(xOfLocation_x-4, yOfLocation_y)&&charInMapToInt(xOfLocation_x-4 , yOfLocation_y) >= 1) {
            value += 35;
        } else {
            
            if (charInMapToInt(xOfLocation_x - 1, yOfLocation_y) >= 1) {
                value += 8;//优先吃身边1,2,3
            }
            value += Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y]));
        }

        // (x-2,y)
        if (xOfLocation_x - 2 >= 0 && String.valueOf(map[xOfLocation_x - 2][yOfLocation_y]).equals("9")) {
            value += WALL_1;
        }
        if (xOfLocation_x - 2 >= 0 && String.valueOf(map[xOfLocation_x - 2][yOfLocation_y]).equals("G")) {
            value += GHOST_1;
        }
        if (xOfLocation_x - 2 >= 0 && !String.valueOf(map[xOfLocation_x - 2][yOfLocation_y]).equals("G")) {
            if (9 > Integer.parseInt(String.valueOf(map[xOfLocation_x - 2][yOfLocation_y]))
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x - 2][yOfLocation_y])) >= 0) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x - 2][yOfLocation_y]));
            }
        }

        // (x-1,y-2)
        if (yOfLocation_y - 2 >= 0 && isWall(xOfLocation_x - 1, yOfLocation_y - 2)) {
            value += WALL_1;
        }
        if (yOfLocation_y - 2 >= 0 && isGhost(xOfLocation_x - 1, yOfLocation_y - 2)) {
            value += GHOST_2;
        }
        if (yOfLocation_y - 2 >= 0 && !isGhost(xOfLocation_x - 1, yOfLocation_y - 2)) {
            if (9 > charInMapToInt(xOfLocation_x - 1, yOfLocation_y - 2)
                    && charInMapToInt(xOfLocation_x - 1, yOfLocation_y - 2) >= 0) {
                value += charInMapToInt(xOfLocation_x - 1, yOfLocation_y - 2);
            }
        }

        // (x-2,y-1)
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && isWall(xOfLocation_x - 2, yOfLocation_y - 1)) {
            value += WALL_1;
        }
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && isGhost(xOfLocation_x - 2, yOfLocation_y - 1)) {
            value += GHOST_2;
        }
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && !isGhost(xOfLocation_x - 2, yOfLocation_y - 1)) {
            if (9 > charInMapToInt(xOfLocation_x - 2, yOfLocation_y - 1)
                    && charInMapToInt(xOfLocation_x - 2, yOfLocation_y - 1) >= 0) {
                value += charInMapToInt(xOfLocation_x - 2, yOfLocation_y - 1);
            }
        }

        // (x-3,y)
        if (xOfLocation_x - 3 >= 0 && yOfLocation_y >= 0 && isGhost(xOfLocation_x - 3, yOfLocation_y)) {
            value += GHOST_3;
        }
        if (xOfLocation_x - 3 >= 0 && yOfLocation_y >= 0 && !isGhost(xOfLocation_x - 3, yOfLocation_y)) {
            if (9 > charInMapToInt(xOfLocation_x - 3, yOfLocation_y)
                    && charInMapToInt(xOfLocation_x - 3, yOfLocation_y) >= 0) {
                value += charInMapToInt(xOfLocation_x - 3, yOfLocation_y);
            }
        }

        // (x-2,y+1)
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && isWall(xOfLocation_x - 2, yOfLocation_y + 1)) {
            value += WALL_1;
        }
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && isGhost(xOfLocation_x - 2, yOfLocation_y + 1)) {
            value += GHOST_2;
        }
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && !isGhost(xOfLocation_x - 2, yOfLocation_y + 1)) {
            if (9 > charInMapToInt(xOfLocation_x - 2, yOfLocation_y + 1)
                    && charInMapToInt(xOfLocation_x - 2, yOfLocation_y + 1) >= 0) {
                value += charInMapToInt(xOfLocation_x - 2, yOfLocation_y + 1);
            }
        }

        // (x-1,y+2)
        if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && isGhost(xOfLocation_x - 1, yOfLocation_y + 2)) {
            value += GHOST_2;
        }
        if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && !isGhost(xOfLocation_x - 1, yOfLocation_y + 2)) {
            if (9 > charInMapToInt(xOfLocation_x - 1, yOfLocation_y + 2)
                    && charInMapToInt(xOfLocation_x - 1, yOfLocation_y + 2) >= 0) {
                value += charInMapToInt(xOfLocation_x - 1, yOfLocation_y + 2);
            }
        }

        // (x-4,y)
//					if (xOfLocation_x - 4 >= 0 && String.valueOf(map[xOfLocation_x - 4][yOfLocation_y]).equals("G")) {
//						value += -;
//					}
        if (xOfLocation_x - 4 >= 0 && !String.valueOf(map[xOfLocation_x - 4][yOfLocation_y]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x - 4][yOfLocation_y])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x - 4][yOfLocation_y])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x - 4][yOfLocation_y]));
            }
        }

        // (x-1,y-1)
        if (yOfLocation_y - 1 >= 0 && xOfLocation_x - 1 >= 0
                && String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]).equals("9")) {
            value += WALL_1;
        }
//		if (yOfLocation_y - 1 >= 0 && xOfLocation_x - 1 >= 0
//				&& String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]).equals("G")) {
//			value += GHOST_1;
//		}
        if (yOfLocation_y - 1 >= 0 && !String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]));
            }
        }

        // (x-1,y+1)
        if (yOfLocation_y + 1 <= 14 && String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("9")) {
            value += WALL_1;
        }
//		if (yOfLocation_y + 1 <= 14 && String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("G")) {
//			value += GHOST_1;
//		}
        if (yOfLocation_y + 1 <= 14 && !String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]));
            }
        }
        return value;
    }


    private int downCommand(int value) {
        // (x+1,y)
    	if (!isOutOfBounds(xOfLocation_x+1, yOfLocation_y)&&!isWall(xOfLocation_x+1, yOfLocation_y)&&!isGhost(xOfLocation_x+1, yOfLocation_y)&&charInMapToInt(xOfLocation_x + 1, yOfLocation_y) >= 1&&
        		!isOutOfBounds(xOfLocation_x+2, yOfLocation_y)&&!isWall(xOfLocation_x+2, yOfLocation_y)&&!isGhost(xOfLocation_x+2, yOfLocation_y)&&charInMapToInt(xOfLocation_x + 2, yOfLocation_y) >= 1&&
        		!isOutOfBounds(xOfLocation_x+3, yOfLocation_y)&&!isWall(xOfLocation_x+3, yOfLocation_y)&&!isGhost(xOfLocation_x+3, yOfLocation_y)&&charInMapToInt(xOfLocation_x + 3, yOfLocation_y) >= 1&&
        		!isOutOfBounds(xOfLocation_x+4, yOfLocation_y)&&!isWall(xOfLocation_x+4, yOfLocation_y)&&!isGhost(xOfLocation_x+4, yOfLocation_y)&&charInMapToInt(xOfLocation_x + 4, yOfLocation_y) >= 1) {
            value += 35;
        } else {
            
            if (charInMapToInt(xOfLocation_x + 1, yOfLocation_y) >= 1) {
                value += 8;//优先吃身边1,2,3
            }
            value += Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y]));

        }

        // (x+2,y)
        if (xOfLocation_x + 2 <= 14 && String.valueOf(map[xOfLocation_x + 2][yOfLocation_y]).equals("9")) {
            value += WALL_1;
        }
        if (xOfLocation_x + 2 <= 14 && String.valueOf(map[xOfLocation_x + 2][yOfLocation_y]).equals("G")) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 2 <= 14 && !String.valueOf(map[xOfLocation_x + 2][yOfLocation_y]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x + 2][yOfLocation_y])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x + 2][yOfLocation_y])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x + 2][yOfLocation_y]));
            }
        }

        // (x+3,y)
        if (xOfLocation_x + 3 < 14 && String.valueOf(map[xOfLocation_x + 3][yOfLocation_y]).equals("G")) {
            value += GHOST_3;
        }
        if (xOfLocation_x + 3 < 14 && !String.valueOf(map[xOfLocation_x + 3][yOfLocation_y]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x + 3][yOfLocation_y])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x + 3][yOfLocation_y])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x + 3][yOfLocation_y]));
            }
        }

        // (x+1,y-1)
        if (yOfLocation_y - 1 >= 0 && String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]).equals("9")) {
            value += WALL_1;
        }
//		if (yOfLocation_y - 1 >= 0 && String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]).equals("G")) {
//			value += GHOST_1;
//		}
        if (yOfLocation_y - 1 >= 0 && !String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]));
            }
        }

        // (x+1,y+1)
        if (yOfLocation_y + 1 <= 14 && String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("9")) {
            value += WALL_1;
        }
//		if (yOfLocation_y + 1 <= 14 && String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("G")) {
//			value += GHOST_1;
//		}
        if (yOfLocation_y + 1 <= 14 && !String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y] + 1)) > 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]));
            }
        }

        // (x+1,y-2)
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && isWall(xOfLocation_x + 1, yOfLocation_y - 2)) {
            value += WALL_1;
        }
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && isGhost(xOfLocation_x + 1, yOfLocation_y - 2)) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 1 >= 0 && yOfLocation_y - 2 >= 0 && !isGhost(xOfLocation_x + 1, yOfLocation_y - 2)) {
            if (9 > charInMapToInt(xOfLocation_x + 1, yOfLocation_y - 2)
                    && charInMapToInt(xOfLocation_x + 1, yOfLocation_y - 2) >= 0) {
                value += charInMapToInt(xOfLocation_x + 1, yOfLocation_y - 2);
            }
        }

        // (x+2,y-1)
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y - 1 >= 0 && isGhost(xOfLocation_x + 2, yOfLocation_y - 1)) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y - 1 >= 0 && !isGhost(xOfLocation_x + 2, yOfLocation_y - 1)) {
            if (9 > charInMapToInt(xOfLocation_x + 2, yOfLocation_y - 1)
                    && charInMapToInt(xOfLocation_x + 2, yOfLocation_y - 1) >= 0) {
                value += charInMapToInt(xOfLocation_x + 2, yOfLocation_y - 1);
            }
        }

        // (x+4,y)
//					if (xOfLocation_x + 4 <= 14 && yOfLocation_y >= 0 && isGhost(xOfLocation_x + 4, yOfLocation_y)) {
//						value += -6;
//					}
        if (xOfLocation_x + 4 <= 14 && yOfLocation_y >= 0 && !isGhost(xOfLocation_x + 4, yOfLocation_y)) {
            if (9 > charInMapToInt(xOfLocation_x + 4, yOfLocation_y)
                    && charInMapToInt(xOfLocation_x + 4, yOfLocation_y) >= 0) {
                value += charInMapToInt(xOfLocation_x + 4, yOfLocation_y);
            }
        }

        // (x+2,y+1)
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && isWall(xOfLocation_x + 2, yOfLocation_y + 1)) {
            value += WALL_1;
        }
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && isGhost(xOfLocation_x + 2, yOfLocation_y + 1)) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && !isGhost(xOfLocation_x + 2, yOfLocation_y + 1)) {
            if (9 > charInMapToInt(xOfLocation_x + 2, yOfLocation_y + 1)
                    && charInMapToInt(xOfLocation_x + 2, yOfLocation_y + 1) >= 0) {
                value += charInMapToInt(xOfLocation_x + 2, yOfLocation_y + 1);
            }
        }

        // (x+1,y+2)
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && isGhost(xOfLocation_x + 1, yOfLocation_y + 2)) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && !isGhost(xOfLocation_x + 1, yOfLocation_y + 2)) {
            if (9 > charInMapToInt(xOfLocation_x + 1, yOfLocation_y + 2)
                    && charInMapToInt(xOfLocation_x + 1, yOfLocation_y + 2) >= 0) {
                value += charInMapToInt(xOfLocation_x + 1, yOfLocation_y + 2);
            }
        }
        return value;
    }


    private int leftCommand(int value) {
        // (x,y-1)
    	if (!isOutOfBounds(xOfLocation_x, yOfLocation_y-1)&&!isWall(xOfLocation_x, yOfLocation_y-1)&&!isGhost(xOfLocation_x, yOfLocation_y-1)&&charInMapToInt(xOfLocation_x, yOfLocation_y-1) >= 1&&
        		!isOutOfBounds(xOfLocation_x, yOfLocation_y-2)&&!isWall(xOfLocation_x, yOfLocation_y-2)&&!isGhost(xOfLocation_x, yOfLocation_y-2)&&charInMapToInt(xOfLocation_x , yOfLocation_y-2) >= 1&&
        		!isOutOfBounds(xOfLocation_x, yOfLocation_y-3)&&!isWall(xOfLocation_x, yOfLocation_y-3)&&!isGhost(xOfLocation_x, yOfLocation_y-3)&&charInMapToInt(xOfLocation_x, yOfLocation_y-3) >= 1&&
        		!isOutOfBounds(xOfLocation_x, yOfLocation_y-4)&&!isWall(xOfLocation_x, yOfLocation_y-4)&&!isGhost(xOfLocation_x, yOfLocation_y-4)&&charInMapToInt(xOfLocation_x, yOfLocation_y-4) >= 1) {
            value += 35;
        } else {
            
            if (charInMapToInt(xOfLocation_x, yOfLocation_y - 1) >= 1) {
                value += 8;//优先吃身边1,2,3
            }
            value += Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y - 1]));

        }

        // (x,y-2)
        if (yOfLocation_y - 2 >= 0 && String.valueOf(map[xOfLocation_x][yOfLocation_y - 2]).equals("9")) {
            value += WALL_1;
        }
        if (yOfLocation_y - 2 >= 0 && String.valueOf(map[xOfLocation_x][yOfLocation_y - 2]).equals("G")) {
            value += GHOST_1;
        }
        if (yOfLocation_y - 2 >= 0 && !String.valueOf(map[xOfLocation_x][yOfLocation_y - 2]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y - 2])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y - 2])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y - 2]));
            }
        }

        // (x,y-3)
        if (yOfLocation_y - 3 >= 0 && String.valueOf(map[xOfLocation_x][yOfLocation_y - 3]).equals("G")) {
            value += GHOST_3;
        }
        if (yOfLocation_y - 3 >= 0 && !String.valueOf(map[xOfLocation_x][yOfLocation_y - 3]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y - 3])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y - 3])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y - 3]));
            }
        }

        // (x+1,y-1)
        if (xOfLocation_x + 1 <= 14 && String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]).equals("9")) {
            value += WALL_1;
        }
//		if (xOfLocation_x + 1 <= 14 && String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]).equals("G")) {
//			value += GHOST_1;
//		}
        if (xOfLocation_x + 1 <= 14 && !String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]));
            }
        }

        // (x-1,y-1)
        if (xOfLocation_x - 1 >= 0 && String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]).equals("9")) {
            value += WALL_1;
        }
//		if (xOfLocation_x - 1 >= 0 && String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]).equals("G")) {
//			value += GHOST_1;
//		}
        if (xOfLocation_x - 1 >= 0 && !String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1])) > 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]));
            }
        }

        // (x+2,y-1)
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y - 1 >= 0 && isGhost(xOfLocation_x + 2, yOfLocation_y - 1)) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y - 1 >= 0 && !isGhost(xOfLocation_x + 2, yOfLocation_y - 1)) {
            if (9 > charInMapToInt(xOfLocation_x + 2, yOfLocation_y - 1)
                    && charInMapToInt(xOfLocation_x + 2, yOfLocation_y - 1) >= 0) {
                value += charInMapToInt(xOfLocation_x + 2, yOfLocation_y - 1);
            }
        }

        // (x+1,y-2)
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && isWall(xOfLocation_x + 1, yOfLocation_y - 2)) {
            value += WALL_1;
        }
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && isGhost(xOfLocation_x + 1, yOfLocation_y - 2)) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && !isGhost(xOfLocation_x + 1, yOfLocation_y - 2)) {
            if (9 > charInMapToInt(xOfLocation_x + 1, yOfLocation_y - 2)
                    && charInMapToInt(xOfLocation_x + 1, yOfLocation_y - 2) >= 0) {
                value += charInMapToInt(xOfLocation_x + 1, yOfLocation_y - 2);
            }
        }

        // (x,y-4)
//					if (xOfLocation_x >= 0 && yOfLocation_y - 4 >= 0 && isGhost(xOfLocation_x, yOfLocation_y - 4)) {
//						value += -6;
//					}
        if (xOfLocation_x >= 0 && yOfLocation_y - 4 >= 0 && !isGhost(xOfLocation_x, yOfLocation_y - 4)) {
            if (9 > charInMapToInt(xOfLocation_x, yOfLocation_y - 4)
                    && charInMapToInt(xOfLocation_x, yOfLocation_y - 4) >= 0) {
                value += charInMapToInt(xOfLocation_x, yOfLocation_y - 4);
            }
        }

        // (x-1,y-2)
        if (yOfLocation_y - 2 >= 0 && xOfLocation_x - 1 >= 0 && isWall(xOfLocation_x - 1, yOfLocation_y - 2)) {
            value += WALL_1;
        }
        if (yOfLocation_y - 2 >= 0 && xOfLocation_x - 1 >= 0 && isGhost(xOfLocation_x - 1, yOfLocation_y - 2)) {
            value += GHOST_2;
        }
        if (yOfLocation_y - 2 >= 0 && xOfLocation_x - 1 >= 0 && !isGhost(xOfLocation_x - 1, yOfLocation_y - 2)) {
            if (9 > charInMapToInt(xOfLocation_x - 1, yOfLocation_y - 2)
                    && charInMapToInt(xOfLocation_x - 1, yOfLocation_y - 2) >= 0) {
                value += charInMapToInt(xOfLocation_x - 1, yOfLocation_y - 2);
            }
        }

        // (x-2,y-1)
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && isGhost(xOfLocation_x - 2, yOfLocation_y - 1)) {
            value += GHOST_2;
        }
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && !isGhost(xOfLocation_x - 2, yOfLocation_y - 1)) {
            if (9 > charInMapToInt(xOfLocation_x - 2, yOfLocation_y - 1)
                    && charInMapToInt(xOfLocation_x - 2, yOfLocation_y - 1) >= 0) {
                value += charInMapToInt(xOfLocation_x - 2, yOfLocation_y - 1);
            }
        }
        return value;
    }


    private int rightCommand(int value) {
        // (x,y+1)
    	if (!isOutOfBounds(xOfLocation_x, yOfLocation_y+1)&&!isWall(xOfLocation_x, yOfLocation_y+1)&&!isGhost(xOfLocation_x, yOfLocation_y+1)&&charInMapToInt(xOfLocation_x, yOfLocation_y+1) >= 1&&
        		!isOutOfBounds(xOfLocation_x, yOfLocation_y+2)&&!isWall(xOfLocation_x, yOfLocation_y+2)&&!isGhost(xOfLocation_x, yOfLocation_y+2)&&charInMapToInt(xOfLocation_x , yOfLocation_y+2) >= 1&&
        		!isOutOfBounds(xOfLocation_x, yOfLocation_y+3)&&!isWall(xOfLocation_x, yOfLocation_y+3)&&!isGhost(xOfLocation_x, yOfLocation_y+3)&&charInMapToInt(xOfLocation_x, yOfLocation_y+3) >= 1&&
        		!isOutOfBounds(xOfLocation_x, yOfLocation_y+4)&&!isWall(xOfLocation_x, yOfLocation_y+4)&&!isGhost(xOfLocation_x, yOfLocation_y+4)&&charInMapToInt(xOfLocation_x, yOfLocation_y+4) >= 1) {
            value += 35;
        } else {
            
            if (charInMapToInt(xOfLocation_x, yOfLocation_y + 1) >= 1) {
                value += 8;//优先吃身边1,2,3
            }
            value += Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y + 1]));

        }

        // (x,y+2)
        if (yOfLocation_y + 2 <= 14 && String.valueOf(map[xOfLocation_x][yOfLocation_y + 2]).equals("9")) {
            value += WALL_1;
        }
        if (yOfLocation_y + 2 <= 14 && String.valueOf(map[xOfLocation_x][yOfLocation_y + 2]).equals("G")) {
            value += GHOST_1;
        }
        if (yOfLocation_y + 2 <= 14 && !String.valueOf(map[xOfLocation_x][yOfLocation_y + 2]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y + 2])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y + 2])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y + 2]));
            }
        }

        // (x,y+3)
        if (yOfLocation_y + 3 <= 14 && String.valueOf(map[xOfLocation_x][yOfLocation_y + 3]).equals("G")) {
            value += GHOST_3;
        }
        if (yOfLocation_y + 3 <= 14 && !String.valueOf(map[xOfLocation_x][yOfLocation_y + 3]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y + 3])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y + 3])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y + 3]));
            }
        }

        // (x+1,y+1)
        if (xOfLocation_x + 1 <= 14 && String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("9")) {
            value += WALL_1;
        }
//		if (xOfLocation_x + 1 <= 14 && String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("G")) {
//			value += GHOST_1;
//		}
        if (xOfLocation_x + 1 <= 14 && !String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1])) >= 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]));
            }
        }

        // (x-1,y+1)
        if (xOfLocation_x - 1 >= 0 && String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("9")) {
            value += WALL_1;
        }
//		if (xOfLocation_x - 1 >= 0 && String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("G")) {
//			value += GHOST_1;
//		}
        if (xOfLocation_x - 1 >= 0 && !String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("G")) {
            if (Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1])) > 0
                    && Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1])) < 9) {
                value += Integer.parseInt(String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]));
            }
        }

        // (x-2,y+1)
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && isGhost(xOfLocation_x - 2, yOfLocation_y + 1)) {
            value += GHOST_2;
        }
        if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && !isGhost(xOfLocation_x - 2, yOfLocation_y + 1)) {
            if (9 > charInMapToInt(xOfLocation_x - 2, yOfLocation_y + 1)
                    && charInMapToInt(xOfLocation_x - 2, yOfLocation_y + 1) >= 0) {
                value += charInMapToInt(xOfLocation_x - 2, yOfLocation_y + 1);
            }
        }

        // (x-1,y+2)
        if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && isWall(xOfLocation_x - 1, yOfLocation_y + 2)) {
            value += WALL_1;
        }
        if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && isGhost(xOfLocation_x - 1, yOfLocation_y + 2)) {
            value += GHOST_2;
        }
        if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && !isGhost(xOfLocation_x - 1, yOfLocation_y + 2)) {
            if (9 > charInMapToInt(xOfLocation_x - 1, yOfLocation_y + 2)
                    && charInMapToInt(xOfLocation_x - 1, yOfLocation_y + 2) >= 0) {
                value += charInMapToInt(xOfLocation_x - 1, yOfLocation_y + 2);
            }
        }

        // (x,y+4)
//					if (xOfLocation_x >= 0 && yOfLocation_y + 4 <= 14 && isGhost(xOfLocation_x, yOfLocation_y + 4)) {
//						value += -6;
//					}
        if (xOfLocation_x >= 0 && yOfLocation_y + 4 <= 14 && !isGhost(xOfLocation_x, yOfLocation_y + 4)) {
            if (9 > charInMapToInt(xOfLocation_x, yOfLocation_y + 4)
                    && charInMapToInt(xOfLocation_x, yOfLocation_y + 4) >= 0) {
                value += charInMapToInt(xOfLocation_x, yOfLocation_y + 4);
            }
        }

        // (x+1,y+2)
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && isWall(xOfLocation_x + 1, yOfLocation_y + 2)) {
            value += WALL_1;
        }
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && isGhost(xOfLocation_x + 1, yOfLocation_y + 2)) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && !isGhost(xOfLocation_x + 1, yOfLocation_y + 2)) {
            if (9 > charInMapToInt(xOfLocation_x + 1, yOfLocation_y + 2)
                    && charInMapToInt(xOfLocation_x + 1, yOfLocation_y + 2) >= 0) {
                value += charInMapToInt(xOfLocation_x + 1, yOfLocation_y + 2);
            }
        }

        // (x+2,y+1)
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && isGhost(xOfLocation_x + 2, yOfLocation_y + 1)) {
            value += GHOST_2;
        }
        if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && !isGhost(xOfLocation_x + 2, yOfLocation_y + 1)) {
            if (9 > charInMapToInt(xOfLocation_x + 2, yOfLocation_y + 1)
                    && charInMapToInt(xOfLocation_x + 2, yOfLocation_y + 1) >= 0) {
                value += charInMapToInt(xOfLocation_x + 2, yOfLocation_y + 1);
            }
        }
        return value;
    }


}
