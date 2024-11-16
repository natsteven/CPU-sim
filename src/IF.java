public class IF extends Stage {
    
    public int fetchedInstructionOut;
    public int programCounterIn;

    public IF(Memory memory) {
        super(memory);
        fetchedInstructionOut = 0xD00;
        programCounterIn = 0x00;
    }

    public void process() throws Exception {
        // if (control.isStalling()) {
        //     System.out.println("IF - Stalling - 0x" + String.format("%03X",fetchedInstruction));
        //     return;
        // }

        fetchedInstructionOut = memory.readMemory(programCounterIn);
        //System.out.println(String.format("0x%08X", 1));
        System.out.println("IF - 0x" + String.format("%03X",fetchedInstructionOut));
    }

}
