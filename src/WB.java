public class WB extends Stage {

    public int rawInstructionIn;
    public int resultIn;
    public int accumulatorOut;
    
    public WB(Memory memory) {
        super(memory);
        rawInstructionIn = 0xD00;
        resultIn = 0x000;
        accumulatorOut = 0x000;
    }

    @Override
    public void process() {
        Instruction instruction = Instruction.fromInt(rawInstructionIn);

        if (instruction.isArithmetic()) {
            accumulatorOut = resultIn;
            System.out.println("WB - " + instruction + " ALU writing to accumulator value 0x" + String.format("%03X",resultIn));
        }
        else if (instruction.isLoad()) {
            accumulatorOut = resultIn;
            System.out.println("WB - " + instruction + " Load writing to accumulator value 0x" + String.format("%03X",resultIn));
        }
        else {
            System.out.println("WB - " + instruction + " nothing to do");

        }

    }
}
