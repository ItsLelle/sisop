package PCSemafori;

public abstract class Buffer {
    public Buffer(int dimensione) {
    }
    protected int[] buffer;
    protected int in = 0;
    protected int out = 0;
}
