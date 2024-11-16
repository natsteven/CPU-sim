public abstract class Stage {
    protected Memory memory;

    public Stage(Memory memory) {
        this.memory = memory;

    }

    public abstract void process() throws Exception;
}
