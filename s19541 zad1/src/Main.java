import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Algorithm alg=new Algorithm(args[1],Integer.parseInt(args[0]));
        //System.out.println(alg.findGroup(5.4,3.0,4.5,1.5));
        alg.findGroupForTestFile(args[2]);
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Podaj wektory oddzielone spacja");
            String vectors=scanner.nextLine();
            StringTokenizer tokenizer=new StringTokenizer(vectors," ");
            double[]vectorsT = new double[tokenizer.countTokens()];
            int tmp=0;
            while(tokenizer.hasMoreTokens()){
                vectorsT[tmp]=Double.parseDouble(tokenizer.nextToken());
                tmp++;
            }
            System.out.println(alg.findGroup(vectorsT));
        }
    }
}
