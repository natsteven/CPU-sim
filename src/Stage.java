public abstract class Stage {
    private boolean locked = false;
    protected Memory memory;
    protected Control control;

    public Stage(Memory memory, Control control) {
        this.memory = memory;
        this.control = control;
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    public abstract void process() throws Exception;
}
