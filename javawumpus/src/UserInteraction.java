import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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

	public int readInt() {
		String line = "";
		BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		try {
			line = is.readLine();
		} catch (IOException e) {
			return 0;
		}
		return Integer.parseInt(line);
	}

}
