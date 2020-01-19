package trending;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cluster {

    int noOfCities;
    int K=4;
    List<Integer> placeCluster;
    List<Integer> cities;
    ArrayList<ArrayList<Integer>> means;
    int [][] userList=new int[][] {{0,5},{1,5},{2,5},{3,5},{4,3}};
    ArrayList<Integer> userCluster;

    Cluster(int noOfCities){
        this.noOfCities=noOfCities;
        placeCluster=new ArrayList<>();
        cities=new ArrayList<>();
        means= new ArrayList<>();
        userCluster=new ArrayList<>();
    }

    public void cluster(){
        Random random=new Random();
        for(int i =0;i<noOfCities;i++){
            cities.add(i);
            placeCluster.add(0);
        }
        for(int i=0;i<K;i++){
            means.get(i).add((random.nextInt(noOfCities)));
            means.get(i).add(0);
        }

        for(int u=0;u<userList.length;u++){

            int [] users=userList[u];
            for(int i=0;i<10;i++){
                for(int j=0;j<u+1;j++){
                    int minimumDist=1000;
                    int minIndex=-1;
                    for(int k=0;k<K;k++){
                        double dist= Math.sqrt(Math.pow((userList[j][0]-means.get(k).get(0)),2)+Math.pow(userList[j][1]-means.get(k).get(1),2));
                        if(minimumDist>dist){
                            minimumDist= (int) dist;
                            minIndex=k;
                        }
                    }
                    userCluster.add(minIndex);
                    placeCluster.add(userList[j][i],minIndex);

                }
                ArrayList<ArrayList<Integer>> tempMean=new ArrayList<>();
                for(int k=0;k<K;k++){
                    int count=0;
                    int sum1=0,sum2=0;
                    for(int j=0;j<u+1;j++){
                        if(userCluster.get(j)==k){
                            sum1+=userList[j][0];
                            sum2+=userList[j][1];
                            count++;
                        }
                    }
                    if(count!=0){
                        tempMean.get(k).add(sum1/count);
                        tempMean.get(k).add(sum2/count);
                    }else{
                        tempMean.get(k).add(random.nextInt(noOfCities));
                        tempMean.get(k).add(0);
                    }

                }
                for(int i1=0;i1<K;i1++){
                    means.add(i1,tempMean.get(i));
                }

            }
        }
        int userIndex=1;
        for(int i=0;i<noOfCities;i++){
            if(placeCluster.get(i)==userCluster.get(userIndex)){
                //show the recommended cities
            }
        }

    }


}
