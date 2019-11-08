public class MapInfoTheBestPath extends MapInfoBase {
	int[] emptyCount = new int[4];// 四个方向连续空地的数量

	@Override
	public int getNextDirection() {
		int[] valuesFlag = new int[4];// 四个方向距离最近砖块或者幽灵的距离
		double[] valuesFruit = new double[4];// 四个方向水果的分值
//		int[] emptyCount = new int[4];// 四个方向连续空地的数量
		for (int i = 0; i < emptyCount.length; i++) {
			emptyCount[i]=0;
		}
		boolean[] isEmptyCount = new boolean[4];
		for (int i = 0; i < isEmptyCount.length; i++) {
			isEmptyCount[i] = true;
		}
		for (int i = 0; i < emptyCount.length; i++) {
			emptyCount[i] = 0;
		}
		for (int i = 0; i < 4; i++) {
			valuesFruit[i] = 1;
		}
		for (int i = xOfLocation_x - 1; i >= -1; i--) {
			if (!isOutOfBounds(i, yOfLocation_y) && !isWall(i, yOfLocation_y) && !isGhost(i, yOfLocation_y)) {
				valuesFruit[0] += charInMapToInt(i, yOfLocation_y);
			}
			if (isOutOfBounds(i, yOfLocation_y) || isWall(i, yOfLocation_y) || isGhost(i, yOfLocation_y)) {

				valuesFlag[0] = xOfLocation_x - i - 1;// 算出上方向最近砖块离吃豆人的距离
				break;
			}
			if (isOutOfBounds(i, yOfLocation_y) || isOne(i, yOfLocation_y) || isTwo(i, yOfLocation_y)
					|| isThree(i, yOfLocation_y) || isFour(i, yOfLocation_y) || isFive(i, yOfLocation_y)
					|| isWall(i, yOfLocation_y) || isGhost(i, yOfLocation_y)) {
				isEmptyCount[0] = false;
			}
			if (isEmptyCount[0]&& isEmpty(i, yOfLocation_y)) {
				emptyCount[0]++;// 算出上方空地数量
			}
		}

		for (int i = xOfLocation_x + 1; i <= 15; i++) {
			if (!isOutOfBounds(i, yOfLocation_y) && !isWall(i, yOfLocation_y) && !isGhost(i, yOfLocation_y)) {
				valuesFruit[1] += charInMapToInt(i, yOfLocation_y);
			}
			if (isOutOfBounds(i, yOfLocation_y) || isWall(i, yOfLocation_y) || isGhost(i, yOfLocation_y)) {

				valuesFlag[1] = i - xOfLocation_x - 1;// 算出下方向最近砖块离吃豆人的距离
				break;
			}
			if (isOutOfBounds(i, yOfLocation_y) || isOne(i, yOfLocation_y) || isTwo(i, yOfLocation_y)
					|| isThree(i, yOfLocation_y) || isFour(i, yOfLocation_y) || isFive(i, yOfLocation_y)
					|| isWall(i, yOfLocation_y) || isGhost(i, yOfLocation_y)) {
				isEmptyCount[1] = false;
			}
			if (isEmptyCount[1] && isEmpty(i, yOfLocation_y)) {
				emptyCount[1]++;// 算出下方空地数量
			}
		}

		for (int i = yOfLocation_y - 1; i >= -1; i--) {
			if (!isOutOfBounds(xOfLocation_x, i) && !isWall(xOfLocation_x, i) && !isGhost(xOfLocation_x, i)) {
				valuesFruit[2] += charInMapToInt(xOfLocation_x, i);
			}
			if (isOutOfBounds(xOfLocation_x, i) || isWall(xOfLocation_x, i) || isGhost(xOfLocation_x, i)) {

				valuesFlag[2] = yOfLocation_y - i - 1;// 算出左方向最近砖块离吃豆人的距离
				break;
			}
			if (isOutOfBounds(xOfLocation_x, i) || isOne(xOfLocation_x, i) || isTwo(xOfLocation_x, i)
					|| isThree(xOfLocation_x, i) || isFour(xOfLocation_x, i) || isFive(xOfLocation_x, i)
					|| isWall(xOfLocation_x, i) || isGhost(xOfLocation_x, i)) {
				isEmptyCount[2] = false;
			}
			if (isEmptyCount[2] && isEmpty(xOfLocation_x, i)) {
				emptyCount[2]++;// 算出左方空地数量
			}
		}

		for (int i = yOfLocation_y + 1; i <= 15; i++) {
			if (!isOutOfBounds(xOfLocation_x, i) && !isWall(xOfLocation_x, i) && !isGhost(xOfLocation_x, i)) {
				valuesFruit[3] += charInMapToInt(xOfLocation_x, i);
			}
			if (isOutOfBounds(xOfLocation_x, i) || isWall(xOfLocation_x, i) || isGhost(xOfLocation_x, i)) {

				valuesFlag[3] = i - yOfLocation_y - 1;// 算出右方向最近砖块离吃豆人的距离
				break;
			}
			if (isOutOfBounds(xOfLocation_x, i) || isOne(xOfLocation_x, i) || isTwo(xOfLocation_x, i)
					|| isThree(xOfLocation_x, i) || isFour(xOfLocation_x, i) || isFive(xOfLocation_x, i)
					|| isWall(xOfLocation_x, i) || isGhost(xOfLocation_x, i)) {
				isEmptyCount[3] = false;
			}
			if (isEmptyCount[3] && isEmpty(xOfLocation_x, i)) {
				emptyCount[3]++;// 算出右方空地数量
			}
		}

		System.out.print("上方空地数量：" + emptyCount[0] + "  " + "下方空地数量：" + emptyCount[1] + "\n");
		System.out.print("左方空地数量：" + emptyCount[2] + "  " + "右方空地数量：" + emptyCount[3] + "\n");

		System.out.print("上方距离：" + valuesFlag[0] + "  " + "下方距离：" + valuesFlag[1] + "\n");
		System.out.print("左方距离：" + valuesFlag[2] + "  " + "右方距离：" + valuesFlag[3] + "\n");

		System.out.print("上方分值：" + valuesFruit[0] + "  " + "下方分值：" + valuesFruit[1] + "\n");
		System.out.print("左方分值：" + valuesFruit[2] + "  " + "右方分值：" + valuesFruit[3] + "\n");

		if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGhost(xOfLocation_x - 1, yOfLocation_y - 1)
				&& !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1)
				&& isGhost(xOfLocation_x + 1, yOfLocation_y + 1)) {
			return mCurrentDirection;
		}
		if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGhost(xOfLocation_x - 1, yOfLocation_y + 1)
				&& !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1)
				&& isGhost(xOfLocation_x + 1, yOfLocation_y - 1)) {
			return mCurrentDirection;
		}

		for (int i = 0; i < valuesFruit.length; i++) {
			if (valuesFlag[i] != 0) {
				valuesFruit[i] = (valuesFruit[i] - 3) / valuesFlag[i];
			} else {
				valuesFruit[i] = 0;
			}
		}

		return compareDistance(valuesFruit);
	}

	private int compareDistance(double[] valueFlag) {
		System.out.print("上方权值：" + valueFlag[0] + "  " + "下方权值：" + valueFlag[1] + "\n");
		System.out.print("左方权值：" + valueFlag[2] + "  " + "右方权值：" + valueFlag[3] + "\n");
		double temp0 = 0;
		double temp1 = 0;
		double temp2 = 0;
		double temp3 = 0;
		int tempa;
		int flaga;
		int tempb;
		int flagb;
		if (valueFlag[0] > valueFlag[1]) {
			temp0 = valueFlag[0];
		} else {
			temp1 = valueFlag[1];
		}

		if (valueFlag[2] > valueFlag[3]) {
			temp2 = valueFlag[2];
		} else {
			temp3 = valueFlag[3];
		}

		if (temp0 == 0 && (!isOutOfBounds(xOfLocation_x, yOfLocation_y-1)&&(isGhost(xOfLocation_x, yOfLocation_y-1))||
				(!isOutOfBounds(xOfLocation_x, yOfLocation_y+1)&&(isGhost(xOfLocation_x, yOfLocation_y+1))
						||(!isOutOfBounds(xOfLocation_x-1, yOfLocation_y)&&(isGhost(xOfLocation_x-1, yOfLocation_y))
								||(!isOutOfBounds(xOfLocation_x+1, yOfLocation_y)&&(isGhost(xOfLocation_x+1, yOfLocation_y))))))) {
			return mCurrentDirection;
		}
		if (temp1 == 0 && (!isOutOfBounds(xOfLocation_x, yOfLocation_y-1)&&(isGhost(xOfLocation_x, yOfLocation_y-1))||
				(!isOutOfBounds(xOfLocation_x, yOfLocation_y+1)&&(isGhost(xOfLocation_x, yOfLocation_y+1))
						||(!isOutOfBounds(xOfLocation_x-1, yOfLocation_y)&&(isGhost(xOfLocation_x-1, yOfLocation_y))
								||(!isOutOfBounds(xOfLocation_x+1, yOfLocation_y)&&(isGhost(xOfLocation_x+1, yOfLocation_y))))))) {
			return mCurrentDirection;
		}
		if (temp2 == 0 && (!isOutOfBounds(xOfLocation_x, yOfLocation_y-1)&&(isGhost(xOfLocation_x, yOfLocation_y-1))||
				(!isOutOfBounds(xOfLocation_x, yOfLocation_y+1)&&(isGhost(xOfLocation_x, yOfLocation_y+1))
						||(!isOutOfBounds(xOfLocation_x-1, yOfLocation_y)&&(isGhost(xOfLocation_x-1, yOfLocation_y))
								||(!isOutOfBounds(xOfLocation_x+1, yOfLocation_y)&&(isGhost(xOfLocation_x+1, yOfLocation_y))))))) {
			return mCurrentDirection;
		}
		if (temp3 == 0 && (!isOutOfBounds(xOfLocation_x, yOfLocation_y-1)&&(isGhost(xOfLocation_x, yOfLocation_y-1))||
				(!isOutOfBounds(xOfLocation_x, yOfLocation_y+1)&&(isGhost(xOfLocation_x, yOfLocation_y+1))
						||(!isOutOfBounds(xOfLocation_x-1, yOfLocation_y)&&(isGhost(xOfLocation_x-1, yOfLocation_y))
								||(!isOutOfBounds(xOfLocation_x+1, yOfLocation_y)&&(isGhost(xOfLocation_x+1, yOfLocation_y))))))) {
			return mCurrentDirection;
		}
		if (temp0 > temp1 && temp0 > temp2 && temp0 > temp3) {// 上方向距离最大
			if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGhost(xOfLocation_x - 1, yOfLocation_y - 1)) {
				if (mCurrentDirection == 0 || mCurrentDirection == 2) {
					return mCurrentDirection;
				} else {
					if (valueFlag[1] > valueFlag[3]) {
						return 1;
					} else {
						return 3;
					}
				}
			}
			if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGhost(xOfLocation_x - 1, yOfLocation_y + 1)) {
				if (mCurrentDirection == 0 || mCurrentDirection == 3) {
					return mCurrentDirection;
				} else {
					if (valueFlag[2] > valueFlag[1]) {
						return 2;
					} else {
						return 1;
					}
				}
			}
			else {
				if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGhost(xOfLocation_x - 1, yOfLocation_y - 1) && !isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGhost(xOfLocation_x - 1, yOfLocation_y + 1)) {
					return mCurrentDirection;
				}else {
					return 0;
				}
			}
		}

		else if (temp1 > temp0 && temp1 > temp2 && temp1 > temp3) {// 下方向距离最大
			if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1) && isGhost(xOfLocation_x + 1, yOfLocation_y - 1)) {
				if (mCurrentDirection == 1 || mCurrentDirection == 2) {
					return mCurrentDirection;
				} else {
					if (valueFlag[0] > valueFlag[3]) {
						return 0;
					} else {
						return 3;
					}
				}
			}
			if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1) && isGhost(xOfLocation_x + 1, yOfLocation_y + 1)) {
				if (mCurrentDirection == 1 || mCurrentDirection == 3) {
					return mCurrentDirection;
				} else {
					if (valueFlag[0] > valueFlag[2]) {
						return 0;
					} else {
						return 2;
					}
				}
			}
			else {
				if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1) && isGhost(xOfLocation_x + 1, yOfLocation_y - 1) && !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1) && isGhost(xOfLocation_x + 1, yOfLocation_y + 1)) {
					return mCurrentDirection;
				}else {
					return 1;
				}
			}
		}

		else if (temp2 > temp0 && temp2 > temp1 && temp2 > temp3) {// 左方向距离最大
			if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGhost(xOfLocation_x - 1, yOfLocation_y - 1)) {
				if (mCurrentDirection == 0 || mCurrentDirection == 2) {
					return mCurrentDirection;
				} else {
					if (valueFlag[1] > valueFlag[3]) {
						return 1;
					} else {
						return 3;
					}
				}
			} else if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1)
					&& isGhost(xOfLocation_x + 1, yOfLocation_y - 1)) {
				if (mCurrentDirection == 1 || mCurrentDirection == 2) {
					return mCurrentDirection;
				} else {
					if (valueFlag[0] > valueFlag[3]) {
						return 0;
					} else {
						return 3;
					}
				}
			} else {
				if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGhost(xOfLocation_x - 1, yOfLocation_y - 1) && !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1)
						&& isGhost(xOfLocation_x + 1, yOfLocation_y - 1)) {
					return mCurrentDirection;
				}else {
					return 2;
				}
			}
		}

		else if (temp3 > temp0 && temp3 > temp1 && temp3 > temp2) {// 右方向距离最大
			if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGhost(xOfLocation_x - 1, yOfLocation_y + 1)) {
				if (mCurrentDirection == 0 || mCurrentDirection == 3) {
					return mCurrentDirection;
				} else {
					if (valueFlag[2] > valueFlag[1]) {
						return 2;
					} else {
						return 1;
					}
				}
			} else if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1)
					&& isGhost(xOfLocation_x + 1, yOfLocation_y + 1)) {
				if (mCurrentDirection == 1 || mCurrentDirection == 3) {
					return mCurrentDirection;
				} else {
					if (valueFlag[0] > valueFlag[2]) {
						return 0;
					} else {
						return 2;
					}
				}
			} else {
				if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGhost(xOfLocation_x - 1, yOfLocation_y + 1)
						&& !isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1)
						&& isGhost(xOfLocation_x + 1, yOfLocation_y + 1)) {
					return mCurrentDirection;
				} else {
					return 3;
				}
			}
		}
		
		
		else {
			if (emptyCount[0]>emptyCount[1]) {
				tempa = emptyCount[1];
				flaga = 1;
			}else {
				tempa = emptyCount[0];
				flaga = 0;
			}
			if (emptyCount[2]>emptyCount[3]) {
				tempb = emptyCount[3];
				flagb = 3;
			}else {
				tempb = emptyCount[2];
				flagb = 2;
			}
			if (tempa>tempb) {
				return flaga;
			}else {
				return flagb;
			}
		}
	}

//	private double[] arroundGhost(double[] valuesFlag) {
//		// 幽灵在四周对角的情况规避
//		if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y - 1) && isGost(xOfLocation_x - 1, yOfLocation_y - 1)) {
//			valuesFlag[0] += -50;
//			valuesFlag[2] += -50;
//		}
//		if (!isOutOfBounds(xOfLocation_x - 1, yOfLocation_y + 1) && isGost(xOfLocation_x - 1, yOfLocation_y + 1)) {
//			valuesFlag[0] += -50;
//			valuesFlag[3] += -50;
//		}
//		if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y - 1) && isGost(xOfLocation_x + 1, yOfLocation_y - 1)) {
//			valuesFlag[1] += -50;
//			valuesFlag[2] += -50;
//		}
//		if (!isOutOfBounds(xOfLocation_x + 1, yOfLocation_y + 1) && isGost(xOfLocation_x + 1, yOfLocation_y + 1)) {
//			valuesFlag[3] += -50;
//			valuesFlag[1] += -50;
//		}
//		return valuesFlag;
//	}
}
