public class MEM extends Stage {

    public int rawInstructionIn;
    public int rawInstructionOut;
    public int accumulatorIn;
    public int resultIn;
    public int resultOut;

    public MEM(Memory memory) {
        super(memory);
        rawInstructionIn = 0xD00;
        rawInstructionOut = 0xD00;
        accumulatorIn = 0x000;
        resultIn = 0x000;
        resultOut = 0x000;

    }
    
    @Override
    public void process() throws Exception {
        Instruction instruction = Instruction.fromInt(rawInstructionIn);
        rawInstructionOut = rawInstructionIn;

        if (instruction.isMemory()) {
            switch (instruction.operation) {
                case Instruction.OPERATION.LOAD:
                    resultOut = memory.readMemory(resultIn);
                    System.out.println("MEM - " + instruction + " Load address 0x" + String.format("%03X",resultIn));
                    break;
                case Instruction.OPERATION.LOADI:
                    resultOut = memory.readMemory(memory.readMemory(resultIn));
                    System.out.println("MEM - " + instruction + " Load indirect address 0x" + String.format("%03X",resultIn));
                    break;
                case Instruction.OPERATION.STORE:
                    memory.load(resultIn, accumulatorIn);
                    System.out.println("MEM - " + instruction + " Store at address 0x" + String.format("%03X",resultIn) + " value : 0x" + String.format("%03X",accumulatorIn));
                    break;
                case Instruction.OPERATION.STOREI:
                    memory.load(memory.readMemory(resultIn), accumulatorIn);
                    System.out.println("MEM - " + instruction + " Store indirect address 0x" + String.format("%03X",resultIn) + " value : 0x" + String.format("%03X",accumulatorIn));
                    break;
                default:
            }
        }
        else {
            resultOut = resultIn;
            System.out.println("MEM - " + instruction + " nothing to do");
        }

    }


}
