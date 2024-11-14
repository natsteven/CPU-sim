import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        CPU cpu  = new CPU();
        String program;
        try {
            program = Files.readString(Paths.get("programs/max5.txt"));
            cpu.loadMemory(program);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cpu.run();

    }
}
