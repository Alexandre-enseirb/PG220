package utils;

public interface IReader {
    public String read();
    public String localName();
    public int readInt();
    public boolean hasNext();

    public int[3] readDate();

}
