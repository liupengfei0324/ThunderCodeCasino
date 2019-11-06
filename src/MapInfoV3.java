public class MapInfoV3 {

    char[][] map = new char[15][15];

    //人物在map中的当前坐标
    int xOfLocation_x=0;//人物在
    int yOfLocation_y=0;

    //当前方向 w,s,a,d 对应 0,1,2,3
    int mCurrentDirection;

    //视野半径
    static int radius=2;

    public int charInMapToint(int x,int y){
        return Integer.parseInt(String.valueOf(map[x][y]));
    }
    public boolean isGost(int x,int y){
        return String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]).equals("G");
    }
    public boolean isWall(int x,int y){
        return String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]).equals("9");
    }
    public int getValue(int x,int y){
        return Integer.parseInt(String.valueOf(map[x][y]));
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

    public void print(){
        for(int i=0;i<15;i++){
            System.out.println(String.copyValueOf(map[i]));
        }
    }

    class ghost{
        private int xOfGhost;
        private int yofGhost;
    }
}
