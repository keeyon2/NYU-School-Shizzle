import java.lang.Math;
import java.util.ArrayList;
import java.util.Comparator;

public class Part1 {

    public Part1()
    {
    }
 
    public static void main(String []args)
    {
        test();
    }

    public static <T extends Comparable<T>> void addToCList(T z, ComparableList<T> L)
    {
        L.al.add(z);
    }   
    
    static void test() 
    {
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
}

    class ComparableList<T extends Comparable<T>> extends ArrayList<T> implements Comparable<ComparableList<T>>
    {
      public ArrayList<T> al;
     
      public ComparableList()
      {
          al = new ArrayList<T>();
      } 

      public ComparableList(ArrayList<T> al)
      {
          al = new ArrayList<T>();
          this.al = al;
      }

      public int compareTo(ComparableList<T> other)
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
          return al.toString();
      }
    }

    class A implements Comparable<A>
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
    
    class B extends A
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

