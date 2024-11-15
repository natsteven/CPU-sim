public class WB extends Stage {
    
    public WB(Memory memory, Control control) {
        super(memory, control);
    }

    @Override
    public void process() {
        if (this.inReg == null) {
            System.out.println("WB - No input");
            return;
        }

        stageControl = control.stages.get(4);

        System.out.println("WB - 0x" + String.format("%03X",this.inReg) + ", jump: " + stageControl.jump + ", alu: " + stageControl.alu);
        if (stageControl.jump) {
            control.programCounter = this.inReg;
            control.controlHazard = false;
        }else if (stageControl.alu) {
            control.accumulator = this.inReg;
            control.dataHazard = false;
        } else if (stageControl.memAcc) {
            if (stageControl.loadOrStore) { // Load
                control.accumulator = this.inReg;
                control.dataHazard = false;
            }
        }
        if (stageControl.halt) {
           control.halt = true;
        }
    }
}
