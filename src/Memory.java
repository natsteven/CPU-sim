import java.util.Arrays;

public class Memory {

    int[] memory;
    int memorySize;

    public Memory(int size) {
        memory = new int[size];
        memorySize = size;
    }

    /**
     * loads 12 bit value into memory array at address
     * @param address   address to load into
     * @param value     value to load
     * @throws Exception
     * 
     */
    public void load(int address, int value) throws Exception {
        if((address > memorySize - 1) || (address < 0)) {
            throw new Exception("Attempt to load into memory outside of bounds at address: " + address);
        }
        memory[address] = value;
    }

    /**
     * loads array of 12 bit values into memory at startAddress
     * @param startAddress  address to start loading into
     * @param size          size of section to load
     * @param values        array of 12 bit values
     * @throws Exception
     */
    public void loadSection(int startAddress, int size, int[] values) throws Exception {
        for (int i=0; i < size; i++) {
            load(startAddress + i, values[i]);
        }
    }

    /**
     * reads single memory address
     * @param address
     * @return
     * @throws Exception
     */
    public int readMemory(int address) throws Exception {
        if(isOutOfBounds(address, 1)) {
            throw new Exception("Attempt to read memory outside of bounds at address: " + address);
        }
        return memory[address];
    }

    /**
     * 
     * @param address
     * @param length
     * @throws Exception
     */
    public int[] readMemory(int address, int length) throws Exception {
        if(isOutOfBounds(address, length)) {
            throw new Exception("Attempt to read memory outside of bounds at address: " + address + " and length: " + length);
        }
    
        return Arrays.copyOfRange(memory, address, address + length - 1);
    }

    private boolean isOutOfBounds(int startAddress, int size) {
        return (startAddress < 0) ||
                (startAddress > memorySize - 1) ||
                (startAddress + size > memorySize);
    }
}