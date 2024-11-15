public class CPU {
    // Stages
    private final IF IF;
    private final ID ID;
    private final EX EX;
    private final MEM MEM;
    private final WB WB;

    public int cycle;
    private final Memory memory;
    private final Control control;

    //debug values
    private int memWatchStart = -1;
    private int memWatchEnd = -1;

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
            while (!control.halt) {
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
        System.out.println(" ------------------------- ");
        System.out.println("Program Counter: 0x" + String.format("%02X", control.programCounter));
        System.out.println("Accumulator: 0x" + String.format("%03X", control.accumulator));
        if (memWatchEnd >= 0) {
            int[] memdump = memory.readMemory(memWatchStart, memWatchEnd - memWatchStart + 1);
            System.out.println("Memdump:");
            System.out.println(arrayToString(memdump));
        }

        IF.process();
        clockCycle();
        ID.process();
        clockCycle();
        EX.process();
        clockCycle();
        MEM.process();
        clockCycle();
        WB.process();
        clockCycle();
        if (cycle > 500) {
            control.halt = true;
            System.out.println("Probably an issue :)");
        }

    }

    public void pipelineNaive() {
        try {
            while (!control.halt) {
                System.out.println(" ------------------------------ ");
                System.out.println("Program Counter: 0x" + String.format("%02X", control.programCounter));
                System.out.println("Accumulator: 0x" + String.format("%03X", control.accumulator));
                if (memWatchEnd >= 0) {
                    int[] memdump = memory.readMemory(memWatchStart, memWatchEnd - memWatchStart + 1);
                    System.out.println("Memdump:");
                    System.out.println(arrayToString(memdump));
                }
                clockCycle();
                IF.process();
                ID.process();
                EX.process();
                MEM.process();
                WB.process();

                if (cycle > 500) {
                    control.halt = true;
                    System.out.println("Probably an issue :)");
                }
            }
        } catch (Exception e) {
            System.out.println("Encountered Exception on line " + control.programCounter);
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
        System.out.println("-- Cycle: " + cycle++ + " -----------------");
        control.cycleStages();
        ID.inReg = IF.outReg;
        EX.inReg = ID.outReg;
        MEM.inReg = EX.outReg;
        WB.inReg = MEM.outReg;
        if (control.isStalling()) {
            EX.outReg = null;
            MEM.outReg = null;
        }
    }

}