public class WB extends Stage {
    
    public WB(Memory memory, Control control) {
        super(memory, control);
    }

    @Override
    public void process() {
        System.out.println("WB");
        control.programCounter++;
        if (control.jump) {
            control.programCounter = control.exRegister;
            control.jump = false;
        }else if (control.alu) {
            control.accumulator = control.exRegister;
            control.alu = false;
        } else if (control.memAcc) {
            if (control.loadOrStore) { // Load
                control.accumulator = control.memRegister;
            }
            control.memAcc = false;
        }
    }
}
