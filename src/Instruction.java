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

    public String toString() {
        return "Operation: " + operation.toString() + ", operand: 0x" + String.format("%02X",operand);
    }

}
