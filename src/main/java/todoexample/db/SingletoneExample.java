package todoexample.db;

public class SingletoneExample {
   private static SingletoneExample instance = new SingletoneExample();


   private SingletoneExample() {

   }

   public static SingletoneExample getInstance() {
      return instance;
   }
}