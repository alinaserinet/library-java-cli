package util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileService {
    private final RandomAccessFile file;

    public FileService(RandomAccessFile file) {
        this.file = file;
    }

    public void write(int value) throws IOException {
        file.writeInt(value);
    }
    public void write(String value, int stringLen) throws IOException {
        file.writeChars(fixStringLen(value, stringLen));
    }
    public void write(double value) throws IOException {
        file.writeDouble(value);
    }

    public void write(long value) throws IOException {
        file.writeLong(value);
    }

    public int readInt(long seek) throws IOException {
        file.seek(seek);
        return file.readInt();
    }
    public long readLong(long seek) throws IOException {
        file.seek(seek);
        return file.readLong();
    }
    public double readDouble(long seek) throws IOException {
        file.seek(seek);
        return file.readDouble();
    }
    public char readChar(long seek) throws IOException {
        file.seek(seek);
        return file.readChar();
    }
    public String readString(long seek, int stringLen) throws IOException {
        StringBuilder string = new StringBuilder();
        file.seek(seek);
        for(int i = 0; i < stringLen; i++)
            string.append(file.readChar());
        return string.toString().trim();
    }

    private String fixStringLen(String string, int stringLen) {
        if(stringLen <= string.length())
            return string.substring(0, stringLen);
        StringBuilder stringBuilder = new StringBuilder(string);
        for(int i = 0; i < stringLen - string.length(); i++)
            stringBuilder.append(" ");
        return stringBuilder.toString();
    }

    public long length() throws IOException {
        return file.length();
    }

    public void seek(long seek) throws IOException {
        file.seek(seek);
    }
}
