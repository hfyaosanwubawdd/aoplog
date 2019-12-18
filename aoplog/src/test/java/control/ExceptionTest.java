package control;

public class ExceptionTest {
	public static void main(String[] args) {
		try {
			
			int i = 10/0;
		} catch (Exception e) {
			System.out.println(e.getClass().getSimpleName());
		}
			
		
	}
}
