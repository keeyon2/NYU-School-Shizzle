import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;

public class Part1 {

    public Part1()
    {
    }
   
    public class ComparableList<T extends Comparable<T>> extends ArrayList<T> implements Comparable<ComparableList<T>>
    {
      private ArrayList<T> al;
      
      public ComparableList(ArrayList<T> al)
      {
          this.al = al;
      }

      public int compareTo(Part1.ComparableList<T> other)
      {
          //First bullet
          //We will iterate through, and if same, we continue
          //If we have item at same index different, return certain value
          int totalIterations = Math.min(this.al.size(), other.al.size());
          for (int i = 0; i < totalIterations; i++)
          {
              int compareValue = this.al.get(i).compareTo (other.al.get(i));
              if (compareValue != 0)
              {
                  return compareValue;
              }
          }
          // For all comparable elements are equal, now test size 
          if (this.al.size() < other.al.size())
          {
              return -1;
          }
          else if (other.al.size() > this.al.size())
          {
              return 1;
          }

          return 0;
      } 

      public String toString()
      {
          String finalResult = new String();
          for (int i = 0; i < this.al.size() - 1; i++)
          {
              finalResult += (this.al.get(i) + ", ");
          }
          return finalResult += this.al.get(this.al.size() - 1); 
      }
    }

    public class A implements Comparable<A>
    {
        public Integer x;
        public A(Integer x)
        {
            this.x = x;
        }
        public int compareTo(A other)
        {
            return this.getTotalData().compareTo(other.getTotalData());
        }
        public Integer getTotalData()
        {
            return x;
        }
        public String toString()
        {
            return "A<" + this.x + ">";
        }
    }

    public class B extends A
    {
        public Integer y;
        public B(Integer x, Integer y)
        {
            super(x);
            this.y = y;
        }

        public int compareTo(A other)
        {
            return this.getTotalData().compareTo(other.getTotalData());
        }
        
        public Integer getTotalData()
        {
            return this.x + this.y;
        }

        public String toString()
        {
            return "B<" + this.x + "," + this.y + ">";
        }
    }

    public <T extends Comparable<T>> void addtoCList(T z, ComparableList<T> L)
    {
        L.add(z);
    }
    
    public static void main(String []args)
    {
        //Part1 p = new Part1();
        ////p.test();
        //A a1 = p.new A(6);
        //A a2 = p.new A(7);
        //A a3 = p.new A(6);
        //B b1 = p.new B(2, 4);
        //B b2 = p.new B(3, 5);
        //System.out.println("a1.compareTo(a2) should be -1: " + a1.compareTo(a2));
        //System.out.println("a2.compareTo(a1) should be 1: " + a2.compareTo(a1));
        //System.out.println("a1.compareTo(a3) should be 0: " + a1.compareTo(a3));
        //System.out.println();
        //System.out.println("a1.compareTo(b1) should be 0: " + a1.compareTo(b1));
        //System.out.println("a1.compareTo(b2) should be -1: " + a1.compareTo(b2));
        //System.out.println("b1.compareTo(a1) should be 0: " + b1.compareTo(a1));
        //System.out.println("b2.compareTo(a1) should be 1: " + b2.compareTo(a1));
        //System.out.println("b1.compareTo(b2) should be -1: " + b1.compareTo(b2));
    }
    
    static void test() {
        ComparableList<A> c1 = new ComparableList<A>();
        ComparableList<A> c2 = new ComparableList<A>();
        for(int i = 0; i < 10; i++) {
            addToCList(new A(i), c1);
            addToCList(new A(i), c2);
        }
        
        addToCList(new A(12), c1);
        addToCList(new B(6,6), c2);
        
        addToCList(new B(7,11), c1);
        addToCList(new A(13), c2);
    
        System.out.print("c1: ");
        System.out.println(c1);
        
        System.out.print("c2: ");
        System.out.println(c2);
    
        switch (c1.compareTo(c2)) {
        case -1: 
            System.out.println("c1 < c2");
            break;
        case 0:
            System.out.println("c1 = c2");
            break;
        case 1:
            System.out.println("c1 > c2");
            break;
        default:
            System.out.println("Uh Oh");
            break;
        }
    
    }
   // static void test() {
   //     A a1 = new A(6);
   //     A a2 = new A(7);
   // }
}
