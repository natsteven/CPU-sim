public class ID extends Stage {

    private int rawInstruction;
    private Instruction decodedInstruction;

    public ID(Memory memory) {
        super(memory);
        rawInstruction = 0;
    }

    @Override
    public void process() {
        int instructionBits = rawInstruction >> 8;
        int operand = rawInstruction & 0x0FF;
        Instruction.OPERATION operation;
        switch (instructionBits) {
            case 0x0:
                operation = Instruction.OPERATION.JMP;
                break;
            case 0x1:
                operation = Instruction.OPERATION.JN;
                break;
            case 0x2:
                operation = Instruction.OPERATION.JZ;
                break;
            case 0x4:
                operation = Instruction.OPERATION.LOAD;
                break;
            case 0x5:
                operation = Instruction.OPERATION.STORE;
                break;
            case 0x6:
                operation = Instruction.OPERATION.LOADI;
                break;
            case 0x7:
                operation = Instruction.OPERATION.STOREI;
                break;
            case 0x8:
                operation = Instruction.OPERATION.AND;
                break;
            case 0x9:
                operation = Instruction.OPERATION.OR;
                break;
            case 0xA:
                operation = Instruction.OPERATION.ADD;
                break;
            case 0xB:
                operation = Instruction.OPERATION.SUB;
                break;
            case 0xF:
                operation = Instruction.OPERATION.HALT;
                break;
            default:
                // noop
                operation = Instruction.OPERATION.NOOP;
                break;
        }
        decodedInstruction = new Instruction(operation, operand);
        System.out.println("ID - " + decodedInstruction.toString());
    }

    public void setInstructionRaw(int instruction) {
        rawInstruction = instruction;
    }

    public Instruction getDecodedInstruction() {
        return decodedInstruction;
    }
}
