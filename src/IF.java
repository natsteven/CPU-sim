public class IF extends Stage {
    
    private int fetchedInstruction;
    private int programCounter;

    public IF(Memory memory) {
        super(memory);
        fetchedInstruction = 0;
        programCounter = 0;
    }

    public void process() throws Exception {
        fetchedInstruction = memory.readMemory(programCounter);
        //System.out.println(String.format("0x%08X", 1));
        System.out.println("IF - 0x" + String.format("%03X",fetchedInstruction));
    }

    public void setProgramCounter(int addr) {
        programCounter = addr;
    }

    public int getFetchedInstruction() {
        return fetchedInstruction;
    }

}
