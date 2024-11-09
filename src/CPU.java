import java.util.Arrays;

public class CPU {
    // Stages
    private IF IF;
    private ID ID;
    private EX EX;
    private MEM MEM;
    private WB WB;

    private int cycle;
    private Memory memory;
    private int programCounter;
    private boolean haltFlag;

    public CPU() {
        memory = new Memory(1024);

        IF = new IF(memory);
        ID = new ID(memory);
        EX = new EX(memory);
        MEM = new MEM(memory);
        WB = new WB(memory);
        cycle = 0;
        programCounter = 0;
        haltFlag = false;
    }

    public int run() {
        try {
            while(!haltFlag) {
                clock();
            }
            return 0;
        } catch (Exception e) {
            System.out.println("Encountered Exception on line " + programCounter);
            e.printStackTrace();
            return 1;
        }
    }

    public void clock() throws Exception {
        System.out.println("-- Cycle: " + cycle + " -----------------");
        cycle++;
        IF.setProgramCounter(programCounter);
        IF.process();

        ID.setInstructionRaw(IF.getFetchedInstruction());
        ID.process();

        //EX.setInstruction(ID.getDecodedInstruction());

        // temp until EX stage can set the flag
        if (programCounter >= 15) {
            haltFlag = true;
        }
        else {
            programCounter++;
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