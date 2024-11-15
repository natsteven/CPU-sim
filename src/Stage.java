public abstract class Stage {
    private boolean locked = false;
    protected Memory memory;
    protected Control control;
    protected Integer inReg;
    protected Integer outReg;
    protected Control.StageControl stageControl;

    public Stage(Memory memory, Control control) {
        this.memory = memory;
        this.control = control;
        this.inReg = null;
        this.outReg = null;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public abstract void process() throws Exception;
}
