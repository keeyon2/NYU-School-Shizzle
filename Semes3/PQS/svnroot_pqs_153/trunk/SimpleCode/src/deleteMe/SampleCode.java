package deleteMe;

public class SampleCode {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		long startTime = System.currentTimeMillis();
		Thread.sleep(1000);
		long endTime = System.currentTimeMillis();
		long duration = (endTime - startTime);
		System.out.println("Time is: " + duration);
		System.currentTimeMillis();
		String foo = "";
		System.out.println("foo is null: " + foo == null);
		System.out.println("foo is empty: " + foo.equals(""));

	}

}
