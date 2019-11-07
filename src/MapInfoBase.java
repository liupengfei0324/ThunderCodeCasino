public class MapInfoBase {

	char[][] map = new char[15][15];
	
	//幽灵危险等级，从高到低：1——>3
	private final int GHOST_1 = -25;
	private final int GHOST_2 = -16;
	private final int GHOST_3 = -10;

	//墙的危险等级，从高到低：1——>2
	private final int WALL_1 = -4;
	private final int WALL_2 = -2;
	
	//当前吃豆人位置坐标
	int xOfLocation_x = 0;
	int yOfLocation_y = 0;

	// 当前方向 w,s,a,d 对应 0,1,2,3
	int mCurrentDirection;

	//地图数据转换成二维
	public void createFromString(String str) {
		System.out.println("createFromString");
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
		print();
		System.out.println();
		updateMap();
		print();
	}
	
	/**
	 * 更新地图数据 - 添加”墙“
	 *
	 * ? :  顺序遍历，可能会对部分 度为2 的方块产生影响。
	 */
	public void updateMap() {
	    int flagOfEachNode=0;
	    for (int j = 0; j < 15; j++) {
	        for (int k = 0; k < 15; k++) {
	        	flagOfEachNode=0;
	            if(j!=xOfLocation_x || k!=yOfLocation_y){
	                if(!(isOutOfBounds(j-1,k) || isWall(j-1,k))){
	                    flagOfEachNode++;
	                }
	                if(!(isOutOfBounds(j+1,k) || isWall(j+1,k))){
	                    flagOfEachNode++;
	                }
	                if(!(isOutOfBounds(j,k-1) || isWall(j,k-1))){
	                    flagOfEachNode++;
	                }
	                if(!(isOutOfBounds(j,k+1) || isWall(j,k+1))){
	                    flagOfEachNode++;
	                }
	                if(flagOfEachNode==1){
	                    System.out.println(map[j][k]+"--> , j="+j+",k="+k+" | flagNode :"+flagOfEachNode);
	                    map[j][k]="9".charAt(0);
	                    System.out.println(map[j][k]+"");
	                }
	            }
	            
	        }
	    }
	}
	
	public boolean isOutOfBounds(int x, int y) {
		if (x < 0 || x > 14 || y < 0 || y > 14) {
			return true;
		}
		return false;
	}

	//获取下一步的指令
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

	// w,s,a,d duiyingxiabiao 0,1,2,3
	//
	public int getNextDirection() {
		int a, w, s, d = 0;
		int[] valuesFlag = new int[4];
		int LastDirection = 2;
		for (int i = 0; i < 4; i++) {
			valuesFlag[i] = -1000;
		}
		
		
		
		if (xOfLocation_x - 1 >= 0 && map[xOfLocation_x - 1][yOfLocation_y] != "9".charAt(0)
				&& map[xOfLocation_x - 1][yOfLocation_y] != "G".charAt(0)) {
			w = -1;
			valuesFlag[0] = 0;
		}
		if (xOfLocation_x + 1 <= 14 && map[xOfLocation_x + 1][yOfLocation_y] != "9".charAt(0)
				&& map[xOfLocation_x + 1][yOfLocation_y] != "G".charAt(0)) {
			s = -1;
			valuesFlag[1] = 0;
		}
		if (yOfLocation_y - 1 >= 0 && map[xOfLocation_x][yOfLocation_y - 1] != "9".charAt(0)
				&& map[xOfLocation_x][yOfLocation_y - 1] != "G".charAt(0)) {
			a = -1;
			valuesFlag[2] = 0;
		}
		if (yOfLocation_y + 1 <= 14 && map[xOfLocation_x][yOfLocation_y + 1] != "9".charAt(0)
				&& map[xOfLocation_x][yOfLocation_y + 1] != "G".charAt(0)) {
			d = -1;
			valuesFlag[3] = 0;
		}
		
		// 幽灵在四周对角的情况规避
		if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGost(xOfLocation_x - 1, yOfLocation_y - 1)) {
			valuesFlag[0] += -500;
			valuesFlag[2] += -500;
		}
		if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGost(xOfLocation_x - 1, yOfLocation_y + 1)) {
			valuesFlag[0] += -500;
			valuesFlag[3] += -500;
		}
		if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1) && isGost(xOfLocation_x + 1, yOfLocation_y - 1)) {
			valuesFlag[1] += -500;
			valuesFlag[2] += -500;
		}
		if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1) && isGost(xOfLocation_x + 1, yOfLocation_y + 1)) {
			valuesFlag[3] += -500;
			valuesFlag[1] += -500;
		}
		if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGost(xOfLocation_x - 1, yOfLocation_y - 1)
				&& !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1)
				&& isGost(xOfLocation_x + 1, yOfLocation_y + 1)) {
			return mCurrentDirection;
		}
		if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGost(xOfLocation_x - 1, yOfLocation_y + 1)
				&& !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1)
				&& isGost(xOfLocation_x + 1, yOfLocation_y - 1)) {
			return mCurrentDirection;
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
		if (charInMapToint(xOfLocation_x - 1, yOfLocation_y) >= 4) {
			value += 15;
		} else {
			if (charInMapToint(xOfLocation_x - 1, yOfLocation_y) == 0) {
				value += -1;
			}
			if (charInMapToint(xOfLocation_x - 1, yOfLocation_y) >= 1) {
				value += 8;
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
		if (yOfLocation_y - 2 >= 0 && isGost(xOfLocation_x - 1, yOfLocation_y - 2)) {
			value += GHOST_2;
		}
		if (yOfLocation_y - 2 >= 0 && !isGost(xOfLocation_x - 1, yOfLocation_y - 2)) {
			if (9 > charInMapToint(xOfLocation_x - 1, yOfLocation_y - 2)
					&& charInMapToint(xOfLocation_x - 1, yOfLocation_y - 2) >= 0) {
				value += charInMapToint(xOfLocation_x - 1, yOfLocation_y - 2);
			}
		}

		// (x-2,y-1)
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && isWall(xOfLocation_x - 2, yOfLocation_y - 1)) {
			value += WALL_1;
		}
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && isGost(xOfLocation_x - 2, yOfLocation_y - 1)) {
			value += GHOST_2;
		}
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && !isGost(xOfLocation_x - 2, yOfLocation_y - 1)) {
			if (9 > charInMapToint(xOfLocation_x - 2, yOfLocation_y - 1)
					&& charInMapToint(xOfLocation_x - 2, yOfLocation_y - 1) >= 0) {
				value += charInMapToint(xOfLocation_x - 2, yOfLocation_y - 1);
			}
		}

		// (x-3,y)
		if (xOfLocation_x - 3 >= 0 && yOfLocation_y >= 0 && isGost(xOfLocation_x - 3, yOfLocation_y)) {
			value += GHOST_3;
		}
		if (xOfLocation_x - 3 >= 0 && yOfLocation_y >= 0 && !isGost(xOfLocation_x - 3, yOfLocation_y)) {
			if (9 > charInMapToint(xOfLocation_x - 3, yOfLocation_y)
					&& charInMapToint(xOfLocation_x - 3, yOfLocation_y) >= 0) {
				value += charInMapToint(xOfLocation_x - 3, yOfLocation_y);
			}
		}

		// (x-2,y+1)
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && isWall(xOfLocation_x - 2, yOfLocation_y + 1)) {
			value += WALL_1;
		}
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && isGost(xOfLocation_x - 2, yOfLocation_y + 1)) {
			value += GHOST_2;
		}
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && !isGost(xOfLocation_x - 2, yOfLocation_y + 1)) {
			if (9 > charInMapToint(xOfLocation_x - 2, yOfLocation_y + 1)
					&& charInMapToint(xOfLocation_x - 2, yOfLocation_y + 1) >= 0) {
				value += charInMapToint(xOfLocation_x - 2, yOfLocation_y + 1);
			}
		}

		// (x-1,y+2)
		if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && isGost(xOfLocation_x - 1, yOfLocation_y + 2)) {
			value += GHOST_2;
		}
		if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && !isGost(xOfLocation_x - 1, yOfLocation_y + 2)) {
			if (9 > charInMapToint(xOfLocation_x - 1, yOfLocation_y + 2)
					&& charInMapToint(xOfLocation_x - 1, yOfLocation_y + 2) >= 0) {
				value += charInMapToint(xOfLocation_x - 1, yOfLocation_y + 2);
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
		if (charInMapToint(xOfLocation_x + 1, yOfLocation_y) >= 4) {
			value += 15;
		} else {
			if (charInMapToint(xOfLocation_x + 1, yOfLocation_y) == 0) {
				value += -1;
			}
			if (charInMapToint(xOfLocation_x + 1, yOfLocation_y) >= 1) {
				value += 8;
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
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && isGost(xOfLocation_x + 1, yOfLocation_y - 2)) {
			value += GHOST_2;
		}
		if (xOfLocation_x + 1 >= 0 && yOfLocation_y - 2 >= 0 && !isGost(xOfLocation_x + 1, yOfLocation_y - 2)) {
			if (9 > charInMapToint(xOfLocation_x + 1, yOfLocation_y - 2)
					&& charInMapToint(xOfLocation_x + 1, yOfLocation_y - 2) >= 0) {
				value += charInMapToint(xOfLocation_x + 1, yOfLocation_y - 2);
			}
		}

		// (x+2,y-1)
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y - 1 >= 0 && isGost(xOfLocation_x + 2, yOfLocation_y - 1)) {
			value += GHOST_2;
		}
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y - 1 >= 0 && !isGost(xOfLocation_x + 2, yOfLocation_y - 1)) {
			if (9 > charInMapToint(xOfLocation_x + 2, yOfLocation_y - 1)
					&& charInMapToint(xOfLocation_x + 2, yOfLocation_y - 1) >= 0) {
				value += charInMapToint(xOfLocation_x + 2, yOfLocation_y - 1);
			}
		}

		// (x+4,y)
//					if (xOfLocation_x + 4 <= 14 && yOfLocation_y >= 0 && isGost(xOfLocation_x + 4, yOfLocation_y)) {
//						value += -6;
//					}
		if (xOfLocation_x + 4 <= 14 && yOfLocation_y >= 0 && !isGost(xOfLocation_x + 4, yOfLocation_y)) {
			if (9 > charInMapToint(xOfLocation_x + 4, yOfLocation_y)
					&& charInMapToint(xOfLocation_x + 4, yOfLocation_y) >= 0) {
				value += charInMapToint(xOfLocation_x + 4, yOfLocation_y);
			}
		}

		// (x+2,y+1)
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && isWall(xOfLocation_x + 2, yOfLocation_y + 1)) {
			value += WALL_1;
		}
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && isGost(xOfLocation_x + 2, yOfLocation_y + 1)) {
			value += GHOST_2;
		}
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && !isGost(xOfLocation_x + 2, yOfLocation_y + 1)) {
			if (9 > charInMapToint(xOfLocation_x + 2, yOfLocation_y + 1)
					&& charInMapToint(xOfLocation_x + 2, yOfLocation_y + 1) >= 0) {
				value += charInMapToint(xOfLocation_x + 2, yOfLocation_y + 1);
			}
		}

		// (x+1,y+2)
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && isGost(xOfLocation_x + 1, yOfLocation_y + 2)) {
			value += GHOST_2;
		}
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && !isGost(xOfLocation_x + 1, yOfLocation_y + 2)) {
			if (9 > charInMapToint(xOfLocation_x + 1, yOfLocation_y + 2)
					&& charInMapToint(xOfLocation_x + 1, yOfLocation_y + 2) >= 0) {
				value += charInMapToint(xOfLocation_x + 1, yOfLocation_y + 2);
			}
		}
		return value;
	}
	
	
	private int leftCommand(int value) {
		// (x,y-1)
		if (charInMapToint(xOfLocation_x, yOfLocation_y - 1) >= 4) {
			value += 15;
		} else {
			if (yOfLocation_y - 2 >= 0 && charInMapToint(xOfLocation_x, yOfLocation_y - 1) == 0) {
				value += -1;
			}
			if (charInMapToint(xOfLocation_x, yOfLocation_y - 1) >= 1) {
				value += 8;
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
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y - 1 >= 0 && isGost(xOfLocation_x + 2, yOfLocation_y - 1)) {
			value += GHOST_2;
		}
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y - 1 >= 0 && !isGost(xOfLocation_x + 2, yOfLocation_y - 1)) {
			if (9 > charInMapToint(xOfLocation_x + 2, yOfLocation_y - 1)
					&& charInMapToint(xOfLocation_x + 2, yOfLocation_y - 1) >= 0) {
				value += charInMapToint(xOfLocation_x + 2, yOfLocation_y - 1);
			}
		}

		// (x+1,y-2)
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && isWall(xOfLocation_x + 1, yOfLocation_y - 2)) {
			value += WALL_1;
		}
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && isGost(xOfLocation_x + 1, yOfLocation_y - 2)) {
			value += GHOST_2;
		}
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y - 2 >= 0 && !isGost(xOfLocation_x + 1, yOfLocation_y - 2)) {
			if (9 > charInMapToint(xOfLocation_x + 1, yOfLocation_y - 2)
					&& charInMapToint(xOfLocation_x + 1, yOfLocation_y - 2) >= 0) {
				value += charInMapToint(xOfLocation_x + 1, yOfLocation_y - 2);
			}
		}

		// (x,y-4)
//					if (xOfLocation_x >= 0 && yOfLocation_y - 4 >= 0 && isGost(xOfLocation_x, yOfLocation_y - 4)) {
//						value += -6;
//					}
		if (xOfLocation_x >= 0 && yOfLocation_y - 4 >= 0 && !isGost(xOfLocation_x, yOfLocation_y - 4)) {
			if (9 > charInMapToint(xOfLocation_x, yOfLocation_y - 4)
					&& charInMapToint(xOfLocation_x, yOfLocation_y - 4) >= 0) {
				value += charInMapToint(xOfLocation_x, yOfLocation_y - 4);
			}
		}

		// (x-1,y-2)
		if (yOfLocation_y - 2 >= 0 && xOfLocation_x - 1 >= 0 && isWall(xOfLocation_x - 1, yOfLocation_y - 2)) {
			value += WALL_1;
		}
		if (yOfLocation_y - 2 >= 0 && xOfLocation_x - 1 >= 0 && isGost(xOfLocation_x - 1, yOfLocation_y - 2)) {
			value += GHOST_2;
		}
		if (yOfLocation_y - 2 >= 0 && xOfLocation_x - 1 >= 0 && !isGost(xOfLocation_x - 1, yOfLocation_y - 2)) {
			if (9 > charInMapToint(xOfLocation_x - 1, yOfLocation_y - 2)
					&& charInMapToint(xOfLocation_x - 1, yOfLocation_y - 2) >= 0) {
				value += charInMapToint(xOfLocation_x - 1, yOfLocation_y - 2);
			}
		}

		// (x-2,y-1)
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && isGost(xOfLocation_x - 2, yOfLocation_y - 1)) {
			value += GHOST_2;
		}
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y - 1 >= 0 && !isGost(xOfLocation_x - 2, yOfLocation_y - 1)) {
			if (9 > charInMapToint(xOfLocation_x - 2, yOfLocation_y - 1)
					&& charInMapToint(xOfLocation_x - 2, yOfLocation_y - 1) >= 0) {
				value += charInMapToint(xOfLocation_x - 2, yOfLocation_y - 1);
			}
		}
		return value;
	}
	
	
	private int rightCommand(int value) {
		// (x,y+1)
		if (charInMapToint(xOfLocation_x, yOfLocation_y + 1) >= 4) {
			value += 15;
		} else {
			if (charInMapToint(xOfLocation_x, yOfLocation_y + 1) == 0) {
				value += -1;
			}
			if (charInMapToint(xOfLocation_x, yOfLocation_y + 1) >= 1) {
				value += 8;
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
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && isGost(xOfLocation_x - 2, yOfLocation_y + 1)) {
			value += GHOST_2;
		}
		if (xOfLocation_x - 2 >= 0 && yOfLocation_y + 1 <= 14 && !isGost(xOfLocation_x - 2, yOfLocation_y + 1)) {
			if (9 > charInMapToint(xOfLocation_x - 2, yOfLocation_y + 1)
					&& charInMapToint(xOfLocation_x - 2, yOfLocation_y + 1) >= 0) {
				value += charInMapToint(xOfLocation_x - 2, yOfLocation_y + 1);
			}
		}

		// (x-1,y+2)
		if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && isWall(xOfLocation_x - 1, yOfLocation_y + 2)) {
			value += WALL_1;
		}
		if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && isGost(xOfLocation_x - 1, yOfLocation_y + 2)) {
			value += GHOST_2;
		}
		if (xOfLocation_x - 1 >= 0 && yOfLocation_y + 2 <= 14 && !isGost(xOfLocation_x - 1, yOfLocation_y + 2)) {
			if (9 > charInMapToint(xOfLocation_x - 1, yOfLocation_y + 2)
					&& charInMapToint(xOfLocation_x - 1, yOfLocation_y + 2) >= 0) {
				value += charInMapToint(xOfLocation_x - 1, yOfLocation_y + 2);
			}
		}

		// (x,y+4)
//					if (xOfLocation_x >= 0 && yOfLocation_y + 4 <= 14 && isGost(xOfLocation_x, yOfLocation_y + 4)) {
//						value += -6;
//					}
		if (xOfLocation_x >= 0 && yOfLocation_y + 4 <= 14 && !isGost(xOfLocation_x, yOfLocation_y + 4)) {
			if (9 > charInMapToint(xOfLocation_x, yOfLocation_y + 4)
					&& charInMapToint(xOfLocation_x, yOfLocation_y + 4) >= 0) {
				value += charInMapToint(xOfLocation_x, yOfLocation_y + 4);
			}
		}

		// (x+1,y+2)
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && isWall(xOfLocation_x + 1, yOfLocation_y + 2)) {
			value += WALL_1;
		}
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && isGost(xOfLocation_x + 1, yOfLocation_y + 2)) {
			value += GHOST_2;
		}
		if (xOfLocation_x + 1 <= 14 && yOfLocation_y + 2 <= 14 && !isGost(xOfLocation_x + 1, yOfLocation_y + 2)) {
			if (9 > charInMapToint(xOfLocation_x + 1, yOfLocation_y + 2)
					&& charInMapToint(xOfLocation_x + 1, yOfLocation_y + 2) >= 0) {
				value += charInMapToint(xOfLocation_x + 1, yOfLocation_y + 2);
			}
		}

		// (x+2,y+1)
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && isGost(xOfLocation_x + 2, yOfLocation_y + 1)) {
			value += GHOST_2;
		}
		if (xOfLocation_x + 2 <= 14 && yOfLocation_y + 1 <= 14 && !isGost(xOfLocation_x + 2, yOfLocation_y + 1)) {
			if (9 > charInMapToint(xOfLocation_x + 2, yOfLocation_y + 1)
					&& charInMapToint(xOfLocation_x + 2, yOfLocation_y + 1) >= 0) {
				value += charInMapToint(xOfLocation_x + 2, yOfLocation_y + 1);
			}
		}
		return value;
	}

	public int charInMapToint(int x, int y) {
		return Integer.parseInt(String.valueOf(map[x][y]));
	}

	public boolean isGost(int x, int y) {
		return String.valueOf(map[x][y]).equals("G");
	}

	public boolean isWall(int x, int y) {
		return String.valueOf(map[x][y]).equals("9");
	}

	public int getValue(int x, int y) {
		return Integer.parseInt(String.valueOf(map[x][y]));
	}

	public void print() {
		for (int i = 0; i < 15; i++) {
			System.out.println(String.copyValueOf(map[i]));
		}
	}

}