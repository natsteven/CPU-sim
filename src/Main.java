import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        CPU cpu  = new CPU();
        if(args.length >= 2) {
            int addr1 = Integer.parseInt(args[0],16);
            int addr2 = Integer.parseInt(args[1],16);
            System.out.println("Watching memory at " + String.format("%02X",addr1) + " to " + String.format("%02X",addr2));
            cpu.watchMem(addr1,addr2);
        }
        String program = null;
        try {
            program = Files.readString(Paths.get("programs/max5.txt"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        if(program == null) {
            try {
                program = Files.readString(Paths.get("src/programs","max5.txt"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(program != null) {
            cpu.loadMemory(program);
            cpu.run();
        }
        else {
            System.out.println("Failed to load program");
        }
    

    }
}