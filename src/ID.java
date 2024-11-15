public class ID extends Stage {

    private int rawInstruction;
    private Instruction decodedInstruction;

    public ID(Memory memory, Control control) {
        super(memory, control);
        rawInstruction = 0;
    }

    @Override
    public void process() {
        if (this.inReg == null) {
            System.out.println("ID - No input");
            return;
        }
        if (control.isStalling()) {
            System.out.println("ID - Stalling - " + decodedInstruction);
            return;
        }
        rawInstruction = this.inReg;
        int instructionBits = (rawInstruction >> 8) & 0xF;
        int operand = rawInstruction & 0x0FF;
        Instruction.OPERATION operation = switch (instructionBits) {
            case 0x0 -> Instruction.OPERATION.JMP;
            case 0x1 -> Instruction.OPERATION.JN;
            case 0x2 -> Instruction.OPERATION.JZ;
            case 0x4 -> Instruction.OPERATION.LOAD;
            case 0x5 -> Instruction.OPERATION.STORE;
            case 0x6 -> Instruction.OPERATION.LOADI;
            case 0x7 -> Instruction.OPERATION.STOREI;
            case 0x8 -> Instruction.OPERATION.AND;
            case 0x9 -> Instruction.OPERATION.OR;
            case 0xA -> Instruction.OPERATION.ADD;
            case 0xB -> Instruction.OPERATION.SUB;
            case 0xF -> Instruction.OPERATION.HALT;
            default ->
                // noop
                    Instruction.OPERATION.NOOP;
        };
        decodedInstruction = new Instruction(operation, operand);
        System.out.println("ID - " + decodedInstruction);
        this.outReg = decodedInstruction.toInt();
    }
}
