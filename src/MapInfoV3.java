public class MapInfoV3 {

	char[][] map = new char[15][15];

	private final static int VALUEOFWALL = -1;
	private final static int VALUEOFGHOST = -1;
	private final static int VALUEOFOUT = -1;
	// 人物在map中的当前坐标
	int xOfLocation_x = 0;// 人物在
	int yOfLocation_y = 0;

	// 当前方向 w,s,a,d 对应 0,1,2,3
	int mCurrentDirection;

	// 视野半径
	static int radius = 2;

	public int charInMapToInt(int x, int y) {
		return Integer.parseInt(String.valueOf(map[x][y]));
	}

	public boolean isGhost(int x, int y) {
		return String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("G");
	}

	public boolean isWall(int x, int y) {
		return String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("9");
	}

	public int getValueOfGhost(int distance) {
		return -100;
	}

	public boolean isOutOfBounds(int x, int y) {
		if (x < 0 || x > 14 || y < 0 || y > 14) {
			return true;
		}
		return false;
	}

	// 从字符串中创建map
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
	}

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
		return "[wait]";
	}

	public int getNextDirection() {
		int direction = 1;
		return direction;
	}

	private int[][] weight = new int[2 * radius + 1][2 * radius + 1];
	// 系数方阵
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

	public void print() {
		for (int i = 0; i < 15; i++) {
			System.out.println(String.copyValueOf(map[i]));
		}
	}

	// 鬼的位置构造类
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

	// 鬼距离人的位置
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

	private void specailWall() {
		// 右边出现缺口墙
		if (String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("9")
				&& String.valueOf(map[xOfLocation_x][yOfLocation_y + 2]).equals("9")
				&& String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("9")) {
			/**
			 * 不能向右边走
			 */
		}
		// 左边出现缺口墙
		if (String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]).equals("9")
				&& String.valueOf(map[xOfLocation_x][yOfLocation_y - 2]).equals("9")
				&& String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]).equals("9")) {
			/**
			 * 不能向左边走
			 */
		}
		// 上面出现缺口墙
		if (String.valueOf(map[xOfLocation_x - 1][yOfLocation_y - 1]).equals("9")
				&& String.valueOf(map[xOfLocation_x - 2][yOfLocation_y]).equals("9")
				&& String.valueOf(map[xOfLocation_x - 1][yOfLocation_y + 1]).equals("9")) {
			/**
			 * 不能向上边走
			 */
		}
		// 下面出现缺口墙
		if (String.valueOf(map[xOfLocation_x + 1][yOfLocation_y - 1]).equals("9")
				&& String.valueOf(map[xOfLocation_x + 2][yOfLocation_y]).equals("9")
				&& String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("9")) {
			/**
			 * 不能向下边走
			 */
		}
	}

	private int specailGhost() {
		// 被幽灵右侧包围
		if (String.valueOf(map[xOfLocation_x][yOfLocation_y - 1]).equals("G")
				&& String.valueOf(map[xOfLocation_x - 1][yOfLocation_y]).equals("G")
				&& String.valueOf(map[xOfLocation_x + 1][yOfLocation_y]).equals("G")) {
			return 3;
		}
		// 被幽灵上侧包围
		if (String.valueOf(map[xOfLocation_x][yOfLocation_y - 1]).equals("G")
				&& String.valueOf(map[xOfLocation_x - 1][yOfLocation_y]).equals("G")
				&& String.valueOf(map[xOfLocation_x + 1][yOfLocation_y + 1]).equals("G")) {
			return 1;
		}
		// 被幽灵右侧包围
		if (String.valueOf(map[xOfLocation_x - 1][yOfLocation_y]).equals("G")
				&& String.valueOf(map[xOfLocation_x][yOfLocation_y + 1]).equals("G")
				&& String.valueOf(map[xOfLocation_x + 1][yOfLocation_y]).equals("G")) {
			return 2;
		}
		// 被幽灵下侧包围
		if (String.valueOf(map[xOfLocation_x][yOfLocation_y - 1]).equals("G")
				&& String.valueOf(map[xOfLocation_x + 1][yOfLocation_y]).equals("G")
				&& String.valueOf(map[xOfLocation_x][yOfLocation_y + 1]).equals("G")) {
			return 0;
		}else {
			/**
			 * 其他情况
			 */
			return 0;
		}
	}
}
