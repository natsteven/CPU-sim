import java.util.Arrays;

public class CPU {
    // Stages
    private final IF IF;
    private final ID ID;
    private final EX EX;
    private final MEM MEM;
    private final WB WB;

    private int cycle;
    private final Memory memory;
    private final Control control;

    public CPU() {
        memory = new Memory(1024);
        control = new Control();

        IF = new IF(memory, control);
        ID = new ID(memory, control);
        EX = new EX(memory, control);
        MEM = new MEM(memory, control);
        WB = new WB(memory, control);
        cycle = 1;
    }

    public int run() {
        try {
            while(!control.halt) {
                singleStage();
            }
            return 0;
        } catch (Exception e) {
            System.out.println("Encountered Exception on line " + control.programCounter);
            e.printStackTrace();
            return 1;
        }
    }

    public void singleStage() throws Exception {
        System.out.println("-- Cycle: " + cycle + " -----------------");
        cycle++;
        // program counter is set on initialization of memory
        IF.process();

        System.out.println("-- Cycle: " + cycle + " -----------------");
        cycle++;
        ID.setInstructionRaw(IF.getFetchedInstruction());
        ID.process();

        System.out.println("-- Cycle: " + cycle + " -----------------");
        cycle++;
        EX.setInstruction(ID.getDecodedInstruction());
        EX.process();

        System.out.println("-- Cycle: " + cycle + " -----------------");
        cycle++;
        MEM.process();

        System.out.println("-- Cycle: " + cycle + " -----------------");
        cycle++;
        WB.process();

        if (cycle > 99999) {
            control.halt = true;
            System.out.println("Probably an issue :)");
        }

    }

    public void loadMemory(String program) {
        String[] lines = program.split("\n");
        int programLength = lines.length;
        int[] result = new int[programLength];
        for (int i = 0; i < programLength; i++) {
            String hexLine = lines[i].substring(0,3).trim();
            result[i] = Integer.parseInt(hexLine, 16) & 0xFFF;
        }
        System.out.println(Arrays.toString(result));
        try {
            memory.loadSection(0,programLength,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}