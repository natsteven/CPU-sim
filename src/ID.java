import java.util.ArrayList;

public class ID extends Stage {

    public int rawInstructionIn;
    public int rawInstructionOut;
    public boolean isStalled;
    public int stallCounter;

    private Instruction decodedInstruction;
    private ArrayList<Instruction> pipelineQueue;

    public ID(Memory memory) {
        super(memory);
        flushPipelineQueue();
        rawInstructionIn = 0xD00;
        rawInstructionOut = 0xD00;
        isStalled = false;
        stallCounter = 0;
    }

    @Override
    public void process() {
        pipelineQueue.removeLast();
        decodedInstruction = Instruction.fromInt(rawInstructionIn);
        System.out.println("\tpipeline queue");
        for (Instruction instruction : pipelineQueue) {
            System.out.println("\t" + instruction);
        }
        if (dataHazard()) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            stallCounter++;
            System.out.println("ID - " + decodedInstruction + " - Stalling due to DATA HAZARD");
        }
        else if (controlHazard()) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            stallCounter++;
            System.out.println("ID - " + decodedInstruction + " - Stalling due to CONTROL HAZARD");
        } else if (halt(decodedInstruction)) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            stallCounter++;
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
            for (Instruction instruction : pipelineQueue) {
                if(!instruction.isNop()) {
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
        //pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
    }

    public void forwardProcess() {
        pipelineQueue.removeLast();
        decodedInstruction = Instruction.fromInt(rawInstructionIn);
        System.out.println("\tpipeline queue");
        for (Instruction instruction : pipelineQueue) {
            System.out.println("\t" + instruction);
        }
        if (hasLoad()) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            stallCounter++;
            System.out.println("ID - " + decodedInstruction + " - Stalling due to DATA HAZARD");
        }
        else if (controlHazard()) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            stallCounter++;
            System.out.println("ID - " + decodedInstruction + " - Stalling due to CONTROL HAZARD");
        } else if (halt(decodedInstruction)) {
            pipelineQueue.addFirst(new Instruction(Instruction.OPERATION.STALL, 0x00));
            rawInstructionOut = 0xC00;
            isStalled = true;
            stallCounter++;
            System.out.println("ID - " + decodedInstruction + " - Stalling due to HALT");
        }
        else {
            pipelineQueue.addFirst(decodedInstruction);
            rawInstructionOut = rawInstructionIn;
            isStalled = false;
            System.out.println("ID - " + decodedInstruction);
        }
    }

    public boolean shouldForward() {
        for (int i =1 ; i < pipelineQueue.size(); i++) { // we skip the 'current' instruction
            if(pipelineQueue.get(i).isArithmetic()) {
                System.out.println("FORWARDING FOR DATA HAZARD");
                return true;
            }
        }
        return false;
    }

    public boolean hasLoad() {
        for (Instruction instruction : pipelineQueue) {
            if(instruction.isLoad()) {
                return true;
            }
        }
        return false;
    }


}
