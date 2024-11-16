import java.rmi.server.Operation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ID extends Stage {

    public int rawInstructionIn;
    public int rawInstructionOut;
    public int programCounterIn;
    public boolean isStalled;
    private Instruction decodedInstruction;

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
        // if (this.inReg == null) {
        //     System.out.println("ID - No input");
        //     return;
        // }
        // if (control.isStalling()) {
        //     System.out.println("ID - Stalling - " + decodedInstruction);
        //     return;
        // }
        pipelineQueue.removeLast();
        decodedInstruction = Instruction.fromInt(rawInstructionIn);
        System.out.println("pipeline queue");
        for (Instruction instruction : pipelineQueue) {
            System.out.println(instruction);
        }
        if (dataHazard(decodedInstruction)) {
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
        }
        else {
            pipelineQueue.addFirst(decodedInstruction);
            rawInstructionOut = rawInstructionIn;
            isStalled = false;
            System.out.println("ID - " + decodedInstruction);
        }
    }

    private boolean dataHazard(Instruction currentInstruction) {
        if (currentInstruction.isHalt()) {
            System.out.println("isHalt");
            for (int i = 0; i < 3; i++) {
                Instruction instruction = pipelineQueue.get(i);
                if(!instruction.isNop()) {
                    System.out.println("should stall");
                    return true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            Instruction instruction = pipelineQueue.get(i);
            if(instruction.isDataHazard()) {
                return true;
            }
        }
        return false;
    }

    private boolean controlHazard() {
        return pipelineQueue.get(0).isJump();
    }

    public void flushPipelineQueue() {
        pipelineQueue = new ArrayList<Instruction>();
        pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
        pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
        pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
        pipelineQueue.add(new Instruction(Instruction.OPERATION.NOOP,0x00));
    }
}
