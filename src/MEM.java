public class MEM extends Stage {

    public MEM(Memory memory, Control control) {
        super(memory, control);
    }
    
    @Override
    public void process() throws Exception {
        System.out.println("MEM");
        if (control.memAcc) {
            System.out.println("Memory access!");
            if (control.loadOrStore) { // Load
                if (control.direct) {
                    control.memRegister = (memory.readMemory(control.exRegister)); // directly load operand
                } else {
                    int address = memory.readMemory(control.exRegister);
                    control.memRegister = (memory.readMemory(address)); // get value from pointer to memory
                }
            } else { // Store
                if (control.direct) {
                    memory.load(control.exRegister, control.accumulator);
                } else {
                    memory.load(memory.readMemory(control.exRegister), control.accumulator);
                }
            }
        }
        // else no memory access
    }


}
