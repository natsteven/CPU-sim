public class Instruction {
    static public enum OPERATION {
        JMP,
        JN,
        JZ,
        LOAD,
        STORE,
        LOADI,
        STOREI,
        AND,
        OR,
        ADD,
        SUB,
        HALT,
        STALL,
        NOOP
    };

    public Instruction.OPERATION operation;
    public Integer operand;

    public Instruction(Instruction.OPERATION operation, Integer operand) {
        this.operation = operation;
        this.operand = operand;
    }

    public boolean isArithmetic() {
        return operation == OPERATION.ADD || operation == OPERATION.SUB || operation == OPERATION.AND || operation == OPERATION.OR;
    }

    public boolean isJump() {
        return operation == OPERATION.JMP || operation == OPERATION.JN || operation == OPERATION.JZ;
    }

    public boolean isMemory() {
        return isLoad() || isStore();
    }

    public boolean isLoad() {
        return operation == OPERATION.LOAD || operation == OPERATION.LOADI;
    }

    public boolean isStore() {
        return operation == OPERATION.STORE || operation == OPERATION.STOREI;
    }

    public boolean isHalt() {
        return operation == OPERATION.HALT;
    }

    public boolean isNop() {
        return !isHalt() && !isStore() && !isLoad() && !isMemory() && !isJump() && !isArithmetic();
    }

    public boolean isDataHazard() {
        return isArithmetic() || operation == OPERATION.LOAD || operation == OPERATION.LOADI;
    }

    public String toString() {
        return "Operation: " + operation.toString() + ", operand: 0x" + String.format("%02X",operand);
    }

    public int toInt() {
        return (operation.ordinal() << 12) | (operand & 0xFFF);
    }

    public static Instruction fromInt(int val) {
        int operationBits = (val >> 8) & 0xF;
        int operand = val & 0xFF;
        Instruction.OPERATION operation = switch (operationBits) {
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
            case 0xC -> Instruction.OPERATION.STALL;
            case 0xF -> Instruction.OPERATION.HALT;
            default ->
                // noop
                    Instruction.OPERATION.NOOP;
        };
        return new Instruction(operation, operand);
    }

}
