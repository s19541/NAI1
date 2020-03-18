import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Algorithm {
    int k;
    List<Iris> train_set;
    Algorithm(String train_set,int k){
        this.k=k;
        this.train_set= loadSetFromFile(train_set);
    }
   private  List<Iris> loadSetFromFile(String set_fname){
        List<Iris> set=new ArrayList<>();
        try{
            BufferedReader bf=new BufferedReader(new FileReader(set_fname));
            String line="";
            while((line=bf.readLine())!=null){
                StringTokenizer tokenizer=new StringTokenizer(line,",");
                String token="";
                List<Double>attr=new ArrayList<>();
                while(tokenizer.hasMoreTokens()){
                    token=tokenizer.nextToken();
                    if(!token.startsWith("Iris")) {
                        attr.add(Double.parseDouble(token));
                    }
                    else
                        set.add(new Iris(attr,token));
                }
            }
            bf.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return set;
    }
    void findGroupForTestFile(String test_file_fname){
        List<Iris>test_set= loadSetFromFile(test_file_fname);
        int tmp=0;
        int accurecyCount=0;
        for(Iris iris:test_set){
            tmp++;
            System.out.println(tmp+"."+findName(findLowestDistances(iris.attributes)));
            if(findName(findLowestDistances(iris.attributes)).equals(iris.name))
                accurecyCount++;
        }
        System.out.println("Accuracy:"+accurecyCount+"/"+tmp);
    }
    String findGroup(double...args){
        if(args.length!=train_set.get(0).attributes.size())
            return "Podano nieprawidlowa ilosc argumentow";
        else{
            List<Double>list=new ArrayList<>();
            for (int i = 0; i <args.length ; i++) {
                list.add(args[i]);
            }
            return findName(findLowestDistances(list));
        }
    }
    private PotentialDistance[] findLowestDistances(List<Double>w){
        PotentialDistance[] lowestDistances=new PotentialDistance[k];
        for (int i = 0; i < train_set.size(); i++) {
            if(i<k){
                lowestDistances[i]=new PotentialDistance(calcDistance(w,train_set.get(i).attributes),train_set.get(i).name);
            }
            else{
                double tmp=calcDistance(w,train_set.get(i).attributes);
               if(tmp<findMax(lowestDistances)){
                   for (int j = 0; j <lowestDistances.length ; j++) {
                       if(lowestDistances[j].distance==findMax(lowestDistances)){
                           lowestDistances[j]=new PotentialDistance(tmp,train_set.get(i).name);
                           break;
                       }
                   }
               }
            }
    }
        return lowestDistances;
    }
    private String findName(PotentialDistance[]lowestDistances){
        Map<String,Integer> countNames=new HashMap<>();
        for (int i = 0; i < lowestDistances.length ; i++) {
            if(!countNames.containsKey(lowestDistances[i].name))
            countNames.put(lowestDistances[i].name,0);
            else
                countNames.put(lowestDistances[i].name,countNames.get(lowestDistances[i].name)+1);
        }
        String maxKey=null;
        for (Map.Entry<String, Integer> entry : countNames.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if(maxKey==null)
                maxKey=key;
            if(value>countNames.get(maxKey))
                maxKey=key;
            if(value==countNames.get(maxKey)){
                double minDistance=0;
                String minDistanceKey="";
                for (int i = 0; i < lowestDistances.length; i++) {
                    if(lowestDistances[i].name==key||lowestDistances[i].name==maxKey){
                        if(minDistance==0) {
                            minDistance = lowestDistances[i].distance;
                            minDistanceKey=lowestDistances[i].name;
                        }
                        else
                            if(lowestDistances[i].distance<minDistance) {
                                minDistance = lowestDistances[i].distance;
                                minDistanceKey=lowestDistances[i].name;
                            }
                    }
                }
                maxKey=minDistanceKey;
            }
        }
        return maxKey;
    }
   private double calcDistance(List<Double> w,List<Double> a){
        double sum=0;
        for (int i = 0; i <w.size() ; i++) {
            sum+=Math.pow(w.get(i)-a.get(i),2);
        }
        return Math.sqrt(sum);
    }
 private   double findMax(PotentialDistance[]potentialDistances){
        double tmp=potentialDistances[0].distance;
        for (int i = 0; i <potentialDistances.length ; i++) {
            if(tmp<=potentialDistances[i].distance)
                tmp=potentialDistances[i].distance;
        }
        return tmp;
    }
}
