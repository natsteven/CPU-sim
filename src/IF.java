public class IF extends Stage {
    
    public int fetchedInstructionOut;
    public int programCounterIn;

    public IF(Memory memory) {
        super(memory);
        fetchedInstructionOut = 0xD00;
        programCounterIn = 0x00;
    }

    public void process() throws Exception {
        fetchedInstructionOut = memory.readMemory(programCounterIn);
        System.out.println("IF - 0x" + String.format("%03X",fetchedInstructionOut));
    }

}
