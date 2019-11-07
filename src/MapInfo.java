public class MapInfo {

    char[][] map = new char[15][15];


    int xOfLocation_x=0;
    int yOfLocation_y=0;

    //当前方向 w,s,a,d 对应 0,1,2,3
    int mCurrentDirection;

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

    public String getNextCommand(){

        int direction=getNextDirection();
        switch (direction){
            case 0:
                //xOfLocation_x=xOfLocation_x-1;
                Client.OLD_COMMAND="[w]";
                return "[w]";
            case 1:
                //xOfLocation_x=xOfLocation_x+1;
                Client.OLD_COMMAND="[s]";
                return "[s]";
            case 2:
                //yOfLocation_y=yOfLocation_y-1;
                Client.OLD_COMMAND="[a]";
                return "[a]";
            case 3:
                //yOfLocation_y=yOfLocation_y+1;
                Client.OLD_COMMAND="[d]";
                return "[d]";
            default:
        }
        return "[w]";
    }

    //w,s,a,d duiyingxiabiao 0,1,2,3
    //
    public int getNextDirection(){
        int a,w,s,d=0;
        int[] valuesFlag=new int[4];
        //int LastDirection=2;
        for(int i=0;i<4;i++){
            valuesFlag[i]=-1;
        }
        if(xOfLocation_x-1>=0 && map[xOfLocation_x-1][yOfLocation_y]!="9".charAt(0) && map[xOfLocation_x-1][yOfLocation_y]!="G".charAt(0)){
            w=-1;valuesFlag[0]=0;
        }
        if(xOfLocation_x+1<=14 && map[xOfLocation_x+1][yOfLocation_y]!="9".charAt(0) && map[xOfLocation_x+1][yOfLocation_y]!="G".charAt(0)){
            s=-1;valuesFlag[1]=0;
        }
        if(yOfLocation_y-1>=0 && map[xOfLocation_x][yOfLocation_y-1]!="9".charAt(0) && map[xOfLocation_x][yOfLocation_y-1]!="G".charAt(0)){
            a=-1;valuesFlag[2]=0;
        }
        if(yOfLocation_y+1<=14 && map[xOfLocation_x][yOfLocation_y+1]!="9".charAt(0) && map[xOfLocation_x][yOfLocation_y+1]!="G".charAt(0)){
            d=-1;valuesFlag[3]=0;
        }
        for(int i=0;i<4;i++){
            if(valuesFlag[i]==0){
                //System.out.println("new i="+i);
                valuesFlag[i]=getValuesForDirections(i);
            }
        }



        int valueTemp,flag;
        int valueTemp2,flag2;
        if(valuesFlag[0]>valuesFlag[1]){
            valueTemp=valuesFlag[0];
            flag=0;
        }else if(valuesFlag[0]<valuesFlag[1]){
            valueTemp=valuesFlag[1];
            flag=1;
        }else {
            if(mCurrentDirection==0){
                valueTemp=valuesFlag[1];
                flag=0;
            }else {
                valueTemp=valuesFlag[1];
                flag=1;
            }
        }
            //valueTemp=valuesFlag[1];
//            if(LastDirection==0){
//                flag=0;
//            }else if(LastDirection==1){
//                flag=1;
//            }else {
//                flag=1;
//            }

        System.out.println("valuesFlag[0]="+valuesFlag[0]+", valuesFlag[1]="+valuesFlag[1]);
        if(valuesFlag[2]>valuesFlag[3]){
            valueTemp2=valuesFlag[2];flag2=2;
        }else if(valuesFlag[2]<valuesFlag[3]){
            valueTemp2 = valuesFlag[3];
            flag2 = 3;
        }else {
            if(mCurrentDirection==2){
                valueTemp2 = valuesFlag[3];
                flag2=2;
            }else {
                valueTemp2 = valuesFlag[3];
                flag2=3;
            }
        }
//        }else {
//            valueTemp2=valuesFlag[3];
//            if(LastDirection==2){
//                flag2=2;
//            }else if(LastDirection==3){
//                flag2=3;
//            }else {
//                flag2=3;
//            }
//        }
        System.out.println("valuesFlag[2]="+valuesFlag[2]+", valuesFlag[3]="+valuesFlag[3]);
        if(valueTemp>valueTemp2){
            //LastDirection=flag;
            return flag;
        }else {
            //LastDirection = flag2;
            return flag2;
        }
//        }else {
//            if(LastDirection==flag){
//                return flag;
//            }else if(LastDirection==flag2){
//                return flag2;
//            }else {
//                LastDirection=flag;
//                return flag;
//            }
//        }
    }


    /**
     * 获取某个方向的总值
     * @param i ： 方向
     *          w,s,a,d 对应 0,1,2,3
     * @return
     */
    public int getValuesForDirections(int i){
        int value=0;
        
        switch(i){
            case 0:
                value+=Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y]));
                if(xOfLocation_x-2>=0 && !String.valueOf(map[xOfLocation_x-2][yOfLocation_y]).equals("G")){
                    if(9>Integer.parseInt(String.valueOf(map[xOfLocation_x-2][yOfLocation_y])) &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x-2][yOfLocation_y]))>=0){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x-2][yOfLocation_y]));
                    }
                }
                if(xOfLocation_x-3>=0 && !String.valueOf(map[xOfLocation_x-3][yOfLocation_y]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x-3][yOfLocation_y]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x-3][yOfLocation_y]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x-3][yOfLocation_y]));
                    }
                }
                if(yOfLocation_y-1>=0 && !String.valueOf(map[xOfLocation_x-1][yOfLocation_y-1]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y-1]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y-1]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y-1]));
                    }
                }
                if(yOfLocation_y+1<=14 && !String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]));
                    }
                }
                break;
            case 1:
                value+=Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y]));
                if(xOfLocation_x+2<=14 && !String.valueOf(map[xOfLocation_x+2][yOfLocation_y]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x+2][yOfLocation_y]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x+2][yOfLocation_y]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x+2][yOfLocation_y]));
                    }
                }
                if(xOfLocation_x+3<14 && !String.valueOf(map[xOfLocation_x+3][yOfLocation_y]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x+3][yOfLocation_y]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x+3][yOfLocation_y]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x+3][yOfLocation_y]));
                    }
                }
                if(yOfLocation_y-1>=0 && !String.valueOf(map[xOfLocation_x+1][yOfLocation_y-1]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y-1]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y-1]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y-1]));
                    }
                }
                if(yOfLocation_y+1<=14 && !String.valueOf(map[xOfLocation_x+1][yOfLocation_y+1]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y]+1))>0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y+1]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y+1]));
                    }
                }
                break;
            case 2:
                value+=Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y-1]));
                if(yOfLocation_y-2>=0 && !String.valueOf(map[xOfLocation_x][yOfLocation_y-2]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y-2]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y-2]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y-2]));
                    }
                }
                if(yOfLocation_y-3>=0 && !String.valueOf(map[xOfLocation_x][yOfLocation_y-3]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y-3]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y-3]))<9 ){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y-3]));
                    }
                }
                if(xOfLocation_x+1<=14 && !String.valueOf(map[xOfLocation_x+1][yOfLocation_y-1]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y-1]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y-1]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y-1]));
                    }
                }
                if(xOfLocation_x-1>=0 && !String.valueOf(map[xOfLocation_x-1][yOfLocation_y-1]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y-1]))>0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y-1]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y-1]));
                    }
                }
                break;
            case 3:

                value+=Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y+1]));

                if(yOfLocation_y+2<=14 && !String.valueOf(map[xOfLocation_x][yOfLocation_y+2]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y+2]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y+2]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y+2]));
                    }
                }

                if(yOfLocation_y+3<=14 && !String.valueOf(map[xOfLocation_x][yOfLocation_y+3]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y+3]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y+3]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x][yOfLocation_y+3]));
                    }
                }

                if(xOfLocation_x+1<=14 && !String.valueOf(map[xOfLocation_x+1][yOfLocation_y+1]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y+1]))>=0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y+1]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x+1][yOfLocation_y+1]));
                    }
                }

                if(xOfLocation_x-1>=0 && !String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]).equals("G")){
                    if(Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]))>0 &&
                            Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]))<9){
                        value+=Integer.parseInt(String.valueOf(map[xOfLocation_x-1][yOfLocation_y+1]));
                    }
                }

                break;
        }

        return value;
    }

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


    public void print(){
        for(int i=0;i<15;i++){
            System.out.println(String.copyValueOf(map[i]));
        }
    }

//    class Gost{
//        int x;
//        int y;
//    }
//    class Score5{
//
//    }
//    class Score4{
//
//    }
//    class Score3{
//
//    }
//    class Score2{
//
//    }
//    class Score1{
//
//    }
//    class Score0{
//
//    }
//    class Wall{
//
//    }
}
