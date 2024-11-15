public class Memory {

    private final int[] memory;
    private final int memorySize;
    private boolean locked;

    public Memory(int size) {
        memory = new int[size];
        memorySize = size;
        locked = false;
    }

    /**
     * loads 12 bit value into memory array at address
     *
     * @param address address to load into
     * @param value   value to load
     */
    public void load(int address, int value) throws Exception {
        locked = true;
        if ((address > memorySize - 1) || (address < 0)) {
            throw new Exception("Attempt to load into memory outside of bounds at address: " + address);
        }
        memory[address] = value;
        locked = false;
    }

    /**
     * loads array of 12 bit values into memory at startAddress
     *
     * @param startAddress address to start loading into
     * @param size         size of section to load
     * @param values       array of 12 bit values
     */
    public void loadSection(int startAddress, int size, int[] values) throws Exception {
        locked = true;
        for (int i = 0; i < size; i++) {
            load(startAddress + i, values[i]);
        }
        locked = false;
    }

    /**
     * reads single memory address
     *
     * @param address
     * @return
     * @throws Exception
     */
    public int readMemory(int address) throws Exception {
        locked = true;
        if (isOutOfBounds(address, 1)) {
            throw new Exception("Attempt to read memory outside of bounds at address: " + address);
        }
        locked = false;
        return memory[address];
    }

    /**
     * @param address
     * @param length
     * @throws Exception
     */
    public int[] readMemory(int address, int length) throws Exception {
        if (isOutOfBounds(address, length)) {
            throw new Exception("Attempt to read memory outside of bounds at address: " + address + " and length: " + length);
        }
        locked = true;
        int[] ret = new int[length];
        System.arraycopy(memory, address, ret, 0, length);
        locked = false;
        return ret;
    }

    private boolean isOutOfBounds(int startAddress, int size) {
        return (startAddress < 0) ||
                (startAddress > memorySize - 1) ||
                (startAddress + size > memorySize);
    }

    public boolean isLocked() {
        return locked;
    }
}