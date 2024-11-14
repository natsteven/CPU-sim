public class EX extends Stage {

    private Instruction instruction;

    public EX(Memory memory, Control control) {
        super(memory, control);
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
    
    @Override
    public void process() throws Exception {
        System.out.println("EX - " + instruction);
        if (instruction.isArithmetic()) {
            alu();
            control.alu = true;
        } else if (instruction.isJump()) {
            branch();
        } else if (instruction.isMemory()) {
            memoryPrep();
            control.memAcc = true;
        } else if (instruction.operation == Instruction.OPERATION.HALT) {
            control.halt = true;
        } else {
            System.out.println("Instruction not recognized");
            System.exit(1);
        }
    }

    public void alu () throws Exception {
        int accumulator = control.accumulator;
        if (instruction.operation == Instruction.OPERATION.ADD) {
            accumulator += memory.readMemory(instruction.operand);
        }else if (instruction.operation == Instruction.OPERATION.SUB) {
            accumulator -= memory.readMemory(instruction.operand);
        } else if (instruction.operation == Instruction.OPERATION.AND) {
            accumulator &= memory.readMemory(instruction.operand);
        } else if (instruction.operation == Instruction.OPERATION.OR) {
            accumulator |= memory.readMemory(instruction.operand);
        }
        control.exRegister = accumulator;
    }

    public void branch () {
        int accumulator = control.accumulator;
        if (instruction.operation == Instruction.OPERATION.JMP) {
            control.jump = true;
        } else if (instruction.operation == Instruction.OPERATION.JN) {
            if (accumulator < 0) {
                control.jump = true;
            }
        } else if (instruction.operation == Instruction.OPERATION.JZ) {
            if (accumulator == 0) {
                control.jump = true;
            }
        }
        control.exRegister = instruction.operand;
    }

    public void memoryPrep() {
        control.exRegister = instruction.operand;
        if (instruction.operation == Instruction.OPERATION.LOAD || instruction.operation == Instruction.OPERATION.LOADI) {
            control.loadOrStore = true;
            control.direct = (instruction.operation == Instruction.OPERATION.LOAD);
        } else if (instruction.operation == Instruction.OPERATION.STORE || instruction.operation == Instruction.OPERATION.STOREI) {
            control.loadOrStore = false;
            control.direct = (instruction.operation == Instruction.OPERATION.STORE);
        }
    }

}
