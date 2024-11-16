public class EX extends Stage {

    // stage registers
    public int rawInstructionIn;
    public int rawInstructionOut;
    public int accumulatorIn;
    public int accumulatorOut;
    public int resultOut;

    // control signal
    public boolean shouldHalt;
    public boolean shouldJump;

    private Instruction instruction;

    public EX(Memory memory) {
        super(memory);
        rawInstructionIn = 0xD00;
        rawInstructionOut = 0xD00;
        accumulatorIn = 0x000;
        accumulatorOut = 0x000;
        resultOut = 0x000;
    }

    @Override
    public void process() throws Exception {
        instruction = Instruction.fromInt(rawInstructionIn);
        rawInstructionOut = rawInstructionIn;
        accumulatorOut = accumulatorIn;
        
        shouldHalt = false;
        shouldJump = false;

        System.out.println("EX - " + instruction);
        if (instruction.isArithmetic()) {
            alu();
        } else if (instruction.isJump()) {
            branch();
        } else if (instruction.isMemory()) {
            this.resultOut = instruction.operand;
        } else if (instruction.operation == Instruction.OPERATION.HALT) {
            shouldHalt = true;
        } else {
            // noop - do nothing
        }

    }

    public void alu () throws Exception {
        int result = 0;
        if (instruction.operation == Instruction.OPERATION.ADD) {
            result =  accumulatorIn + as12bitInt(memory.readMemory(instruction.operand));
            result = result & 0xFFF;
        } else if (instruction.operation == Instruction.OPERATION.SUB) {
            result = accumulatorIn - as12bitInt(memory.readMemory(instruction.operand));
            result = result & 0xFFF;
        } else if (instruction.operation == Instruction.OPERATION.AND) {
            result = accumulatorIn & memory.readMemory(instruction.operand);
        } else if (instruction.operation == Instruction.OPERATION.OR) {
            result = accumulatorIn | memory.readMemory(instruction.operand);
        }
        this.resultOut = result;
    }

    public void branch () {
        if (instruction.operation == Instruction.OPERATION.JMP) {
            shouldJump = true;
        } else if (instruction.operation == Instruction.OPERATION.JN) {
            if (as12bitInt(accumulatorIn) < 0) {
                shouldJump = true;
            }
        } else if (instruction.operation == Instruction.OPERATION.JZ) {
            if (accumulatorIn == 0) {
                shouldJump = true;
            }
        }
        this.resultOut = instruction.operand;
    }

    private static int as12bitInt(int arg) {
        return (arg << (32-12))>>(32-12);
    }

}
