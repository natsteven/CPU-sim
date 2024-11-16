public class CPU {
    // Stages
    private final IF IF;
    private final ID ID;
    private final EX EX;
    private final MEM MEM;
    private final WB WB;

    public int cycle;
    private final Memory memory;
    private int programCounter;
    private int accumulator;

    private boolean halt;

    // private final Control control;

    //debug values
    private int memWatchStart = -1;
    private int memWatchEnd = -1;

    public CPU() {
        memory = new Memory(1024);
        // control = new Control();

        IF = new IF(memory);
        ID = new ID(memory);
        EX = new EX(memory);
        MEM = new MEM(memory);
        WB = new WB(memory);
        halt = false;
        cycle = 1;
    }

    public int run() {
        try {
            while (!halt) {
                singleStage();
            }
            return 0;
        } catch (Exception e) {
            System.out.println("Encountered Exception on line " + programCounter);
            e.printStackTrace();
            return 1;
        }
    }

    public void singleStage() throws Exception {
        System.out.println("-- Cycle: " + cycle++ + " -----------------");
        clockCycle();
        
        System.out.println("Program Counter: 0x" + String.format("%02X", programCounter));
        System.out.println("Accumulator: 0x" + String.format("%03X", accumulator));
        if (memWatchEnd >= 0) {
            int[] memdump = memory.readMemory(memWatchStart, memWatchEnd - memWatchStart + 1);
            System.out.println("Memdump:");
            System.out.println(arrayToString(memdump));
        }
        
        IF.process();
        System.out.println("-- Cycle: " + cycle++ + " -----------------");
        clockCycle();
        ID.process();
        System.out.println("-- Cycle: " + cycle++ + " -----------------");
        clockCycle();
        EX.process();
        System.out.println("-- Cycle: " + cycle++ + " -----------------");
        clockCycle();
        MEM.process();
        System.out.println("-- Cycle: " + cycle++ + " -----------------");
        clockCycle();
        WB.process();
        programCounter++;
        ID.flushPipelineQueue();
        if (EX.shouldHalt) {
            halt = true;
        }

        if (cycle > 500) {
            halt = true;
            System.out.println("Probably an issue :)");
        }
    }

    public void pipelineNaive() {
        try {
            while (!halt) {
                System.out.println("-- Cycle: " + cycle++ + " -----------------");
                clockCycle();
                System.out.println("Program Counter: 0x" + String.format("%02X", programCounter));
                System.out.println("Accumulator: 0x" + String.format("%03X", accumulator));
                if (memWatchEnd >= 0) {
                    int[] memdump = memory.readMemory(memWatchStart, memWatchEnd - memWatchStart + 1);
                    System.out.println("Memdump:");
                    System.out.println(arrayToString(memdump));
                }
                
                IF.process();
                ID.process();
                EX.process();
                MEM.process();
                WB.process();
                
                if (EX.shouldHalt) {
                    halt = true;
                }

                if(!ID.isStalled) {
                    programCounter++;
                }

                if (cycle > 500) {
                    halt = true;
                    System.out.println("Probably an issue :)");
                }
            }
        } catch (Exception e) {
            System.out.println("Encountered Exception on line " + programCounter);
            e.printStackTrace();
        }
    }

    public void loadMemory(String program) {
        String[] lines = program.split("\n");
        int programLength = lines.length;
        int[] result = new int[programLength];
        for (int i = 0; i < programLength; i++) {
            String hexLine = lines[i].substring(0, 3).trim();
            result[i] = Integer.parseInt(hexLine, 16) & 0xFFF;
        }
        System.out.println("Loading Program: " + arrayToString(result));
        try {
            memory.loadSection(0, programLength, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int j : arr) {
            sb.append("0x" + String.format("%03X", j));
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    public void watchMem(int start, int end) {
        memWatchStart = start;
        memWatchEnd = end;
    }

    public void clockCycle() {
        IF.programCounterIn = programCounter;

        if(!ID.isStalled) {
            ID.rawInstructionIn = IF.fetchedInstructionOut;
        }
        
        EX.accumulatorIn = accumulator;
        EX.rawInstructionIn = ID.rawInstructionOut;

        MEM.accumulatorIn = EX.accumulatorOut;
        MEM.rawInstructionIn = EX.rawInstructionOut;
        MEM.resultIn = EX.resultOut;

        WB.rawInstructionIn = MEM.rawInstructionOut;
        WB.resultIn = MEM.resultOut;

        accumulator = WB.accumulatorOut;

        if (EX.shouldJump) {
            programCounter = EX.resultOut;
        }
    }

}