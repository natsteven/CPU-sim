public class IF extends Stage {
    
    private int fetchedInstruction;

    public IF(Memory memory, Control control) {
        super(memory, control);
        fetchedInstruction = 0;
    }

    public void process() throws Exception {
        fetchedInstruction = memory.readMemory(control.programCounter);
        //System.out.println(String.format("0x%08X", 1));
        System.out.println("IF - 0x" + String.format("%03X",fetchedInstruction));
    }

    public int getFetchedInstruction() {
        return fetchedInstruction;
    }

}
