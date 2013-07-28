import java.io.IOException;


public class UserInteraction {

	public void print(int data) {
		System.out.print(data);
	}

	public void println(int data) {
		System.out.println(data);
	}

	public void println(String data) {
		System.out.println(data);
	}

	public void print(String data) {
		System.out.print(data);
	}

	public int readChar() throws IOException {
		return System.in.read();
	}

}
