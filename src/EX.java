public class EX extends Stage{

    private Instruction instruction;

    public EX(Memory memory) {
        super(memory);
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
    
    @Override
    public void process() {
        System.out.println("EX - " + instruction);
        if (instruction.isArithmetic()) {
            alu();
            memory.setALU(true);
        } else if (instruction.isJump()) {
            branch();
        } else if (instruction.isMemory()) {
            memory();
            memory.setMemAcc(true);
        } else if (instruction.operation == Instruction.OPERATION.HALT) {
            memory.setHalt(true);
        } else {
            System.out.println("Instruction not recognized");
            System.exit(1);
        }
    }

    public void alu () {
        int accumulator = memory.getAccumulator();
        if (instruction.operation == Instruction.OPERATION.ADD) {
            accumulator += instruction.operand;
        }else if (instruction.operation == Instruction.OPERATION.SUB) {
            accumulator -= instruction.operand;
        } else if (instruction.operation == Instruction.OPERATION.AND) {
            accumulator &= instruction.operand;
        } else if (instruction.operation == Instruction.OPERATION.OR) {
            accumulator |= instruction.operand;
        }
        memory.setExRegister(accumulator);
    }

    public void branch () {
        int accumulator = memory.getAccumulator();
        if (instruction.operation == Instruction.OPERATION.JMP) {
            memory.setJump(true);
        } else if (instruction.operation == Instruction.OPERATION.JN) {
            if (accumulator < 0) {
                memory.setJump(true);
            }
        } else if (instruction.operation == Instruction.OPERATION.JZ) {
            if (accumulator == 0) {
                memory.setJump(true);
            }
        }
        memory.setExRegister(instruction.operand);
    }

    public void memory () {
        memory.setExRegister(instruction.operand);
        if (instruction.operation == Instruction.OPERATION.LOAD || instruction.operation == Instruction.OPERATION.LOADI) {
            memory.setLoadOrStore(true);
            memory.setDirect(instruction.operation == Instruction.OPERATION.LOAD);
        } else if (instruction.operation == Instruction.OPERATION.STORE || instruction.operation == Instruction.OPERATION.STOREI) {
            memory.setLoadOrStore(false);
            memory.setDirect(instruction.operation == Instruction.OPERATION.STORE);
        }
    }

}
