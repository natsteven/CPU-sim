import java.util.ArrayList;

public class ID extends Stage {

    public int rawInstructionIn;
    public int rawInstructionOut;
    public int programCounterIn;
    public boolean isStalled;

    private ArrayList<Instruction> pipelineQueue;

    public ID(Memory memory) {
        super(memory);
        flushPipelineQueue();
        rawInstructionIn = 0xD00;
        rawInstructionOut = 0xD00;
        programCounterIn = 0x000;
        isStalled = false;
    }

    @Override
    public void process() {
        pipelineQueue.removeLast();
        Instruction decodedInstruction = Instruction.fromInt(rawInstructionIn);
        System.out.println("pipeline queue");
        for (Instruction instruction : pipelineQueue) {
            System.out.println(instruction);
        }
        if (dataHazard()) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            System.out.println("ID - " + decodedInstruction + " - Stalling due to DATA HAZARD");
        }
        else if (controlHazard()) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            System.out.println("ID - " + decodedInstruction + " - Stalling due to CONTROL HAZARD");
        } else if (halt(decodedInstruction)) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            System.out.println("ID - " + decodedInstruction + " - Stalling due to HALT");
        }
        else {
            pipelineQueue.addFirst(decodedInstruction);
            rawInstructionOut = rawInstructionIn;
            isStalled = false;
            System.out.println("ID - " + decodedInstruction);
        }
    }

    private boolean dataHazard() {
        for (Instruction instruction : pipelineQueue) {
            if(instruction.isDataHazard()) {
                return true;
            }
        }
        return false;
    }

    private boolean controlHazard() { // since we are doing forwarding after EX we can just check the first instruction
//        for (Instruction instruction : pipelineQueue) {
//            if(instruction.isControlHazard()) {
//                return true;
//            }
//        }
//        return false;
        return pipelineQueue.getFirst().isControlHazard();
    }

    private boolean halt(Instruction currentInstruction) {
        if (currentInstruction.isHalt()) {
            System.out.println("isHalt");
            for (Instruction instruction : pipelineQueue) {
                if(!instruction.isNop()) {
                    System.out.println("should stall");
                    return true;
                }
            }
        }
        return false;
    }

    public void flushPipelineQueue() {
        pipelineQueue = new ArrayList<Instruction>();
        pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
        pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
        pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
//        pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
    }

}
