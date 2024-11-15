public class EX extends Stage {

    private Instruction instruction;

    public EX(Memory memory, Control control) {
        super(memory, control);
    }

    @Override
    public void process() throws Exception {
        stageControl = control.stages.get(2);

        if (this.inReg == null) {
            System.out.println("EX - No input");
            return;
        }
        if (control.isStalling()) {
            System.out.println("EX - Stalling");
            return;
        }

        instruction = Instruction.fromInt(this.inReg);

        if (instruction.isDataHazard()) {
            control.dataHazard = true;
        }
        if (instruction.isJump()) {
            control.controlHazard = true;
        }

        System.out.println("EX - " + instruction);
        if (instruction.isArithmetic()) {
            alu();
            stageControl.alu = true;
        } else if (instruction.isJump()) {
            branch();
        } else if (instruction.isMemory()) {
            memoryPrep();
            stageControl.memAcc = true;
        } else if (instruction.operation == Instruction.OPERATION.HALT) {
            stageControl.halt = true;
        } else {
            System.out.println("Instruction not recognized");
            System.exit(1);
        }

    }

    public void alu () throws Exception {
        int accumulator = control.accumulator;
        if (instruction.operation == Instruction.OPERATION.ADD) {
            accumulator += as12bitInt(memory.readMemory(instruction.operand));
            accumulator = accumulator & 0xFFF;
        }else if (instruction.operation == Instruction.OPERATION.SUB) {
            accumulator -= as12bitInt(memory.readMemory(instruction.operand));
            accumulator = accumulator & 0xFFF;
        } else if (instruction.operation == Instruction.OPERATION.AND) {
            accumulator &= memory.readMemory(instruction.operand);
        } else if (instruction.operation == Instruction.OPERATION.OR) {
            accumulator |= memory.readMemory(instruction.operand);
        }
        this.outReg = accumulator;
    }

    public void branch () {
        int accumulator = control.accumulator;
        if (instruction.operation == Instruction.OPERATION.JMP) {
            stageControl.jump = true;
        } else if (instruction.operation == Instruction.OPERATION.JN) {
            if (as12bitInt(accumulator) < 0) {
                stageControl.jump = true;
            }
        } else if (instruction.operation == Instruction.OPERATION.JZ) {
            if (accumulator == 0) {
                stageControl.jump = true;
            }
        }
        this.outReg = instruction.operand;
    }

    public void memoryPrep() {
        this.outReg = instruction.operand;
        if (instruction.operation == Instruction.OPERATION.LOAD || instruction.operation == Instruction.OPERATION.LOADI) {
            stageControl.loadOrStore = true;
            stageControl.direct = (instruction.operation == Instruction.OPERATION.LOAD);
        } else if (instruction.operation == Instruction.OPERATION.STORE || instruction.operation == Instruction.OPERATION.STOREI) {
            stageControl.loadOrStore = false;
            stageControl.direct = (instruction.operation == Instruction.OPERATION.STORE);
        }
    }

    private static int as12bitInt(int arg) {
        return (arg << (32-12))>>(32-12);
    }

}
