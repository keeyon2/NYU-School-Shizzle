import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;

public class JavaTest {
    public static void main (String[] args) {
        System.out.println("Hello, World");
        ArrayList<Integer> A = new ArrayList<Integer>();
        A.add(5);
        A.add(5);
        A.add(1);
        String finalResult = new String();
        for (int i = 0; i < A.size() - 1; i++)
        {
            finalResult += (A.get(i) + ", ");
        }
        finalResult += A.get(A.size() - 1); 
        System.out.println(finalResult);
        for (int i = 0; i < -1; i++)
        {
            System.out.println("no error no print");
        }
    }
}
