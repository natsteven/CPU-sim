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
        return operation == OPERATION.LOAD || operation == OPERATION.STORE || operation == OPERATION.LOADI || operation == OPERATION.STOREI;
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
        int operationBits = (val >> 12) & 0xF;
        int operand = val & 0xFFF;
        OPERATION operation = OPERATION.values()[operationBits];
        return new Instruction(operation, operand);
    }

}
