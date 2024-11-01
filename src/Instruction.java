public class Instruction {
    public String opcode;
    public Integer operand1;
    public Integer operand2;

    public Instruction(String opcode, Integer operand1, Integer operand2) {
        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }
}
