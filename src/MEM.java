public class MEM extends Stage {

    public MEM(Memory memory, Control control) {
        super(memory, control);
    }
    
    @Override
    public void process() throws Exception {
        if (this.inReg == null) {
            System.out.println("MEM - No input");
            return;
        }

        stageControl = control.stages.get(3);

        System.out.println("MEM - 0x" + String.format("%03X",this.inReg) + ", memAcc: " + stageControl.memAcc + ", load/store: " + stageControl.loadOrStore + ", direct: " + stageControl.direct);
        if (stageControl.memAcc) {
            if (stageControl.loadOrStore) { // Load
                if (stageControl.direct) {
                    this.outReg = (memory.readMemory(this.inReg)); // directly load operand
                } else {
                    int address = memory.readMemory(this.inReg);
                    this.outReg = (memory.readMemory(address)); // get value from pointer to memory
                }
            } else { // Store
                this.outReg = this.inReg; //for readability/debugging
                if (stageControl.direct) {
                    memory.load(this.inReg, control.accumulator);
                } else {
                    memory.load(memory.readMemory(this.inReg), control.accumulator);
                }
            }
        } else {
            this.outReg = this.inReg;
        }
    }


}
