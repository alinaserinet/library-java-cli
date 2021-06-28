package util;

public class TableDrawer {
    private static char tl = 9484, bl = 9492, tr = 9488, br = 9496;
    private static char l = 9500, r = 9500, t = 9516, b = 9524;
    private static char vl = 9472, hl = 9474, i = 9532;

    // draw row for preview user
    public static void memberPreview(long id, String fullName, String role, String booksCount) {
        String format = hl + " %-6s" + hl + " %-29s" + hl + " %-19s" + hl + " %-19s" + hl + "\n";
        System.out.format(format, id, fullName, role, booksCount);
    }

    // draw head for users table
    public static void memberHead() {
        System.out.printf("%c", tl);
        drawVerticalLine(7);
        System.out.printf("%c", t);
        drawVerticalLine(30);
        System.out.printf("%c", t);
        drawVerticalLine(20);
        System.out.printf("%c", t);
        drawVerticalLine(20);
        System.out.printf("%c\n", tr);
        String format = hl + " %-6s" + hl + " %-29s" + hl + " %-19s" + hl + " %-19s" + hl + "\n";
        System.out.format(format, "ID", "full name", "role", "books");
    }

    // draw footer for users table
    public static void memberFooter() {
        System.out.printf("%c", bl);
        drawVerticalLine(7);
        System.out.printf("%c", b);
        drawVerticalLine(30);
        System.out.printf("%c", b);
        drawVerticalLine(20);
        System.out.printf("%c", b);
        drawVerticalLine(20);
        System.out.printf("%c\n", br);
    }

    public static void bookPreview(long id, String name, String authors, long availableCount){
        String format = hl + " %-9s" + hl + " %-29s" + hl + " %-54s" + hl + " %-9s" + hl + "\n";
        System.out.format(format, id, name, authors, availableCount);
    }

    public static void booksHead(){
        System.out.printf("%c", tl);
        drawVerticalLine(10);
        System.out.printf("%c", t);
        drawVerticalLine(30);
        System.out.printf("%c", t);
        drawVerticalLine(55);
        System.out.printf("%c", t);
        drawVerticalLine(10);
        System.out.printf("%c\n", tr);
        String format = hl + " %-9s" + hl + " %-29s" + hl + " %-54s" + hl + " %-9s" + hl + "\n";
        System.out.format(format, "book id", "name", "authors", "available");
    }

    public static void booksFooter() {
        System.out.printf("%c", bl);
        drawVerticalLine(10);
        System.out.printf("%c", b);
        drawVerticalLine(30);
        System.out.printf("%c", b);
        drawVerticalLine(55);
        System.out.printf("%c", b);
        drawVerticalLine(10);
        System.out.printf("%c\n", br);
    }

    // draw a vertical line for table
    public static void drawVerticalLine(int length) {
        for (int i = 1; i <= length; i++) {
            System.out.printf("%c", vl);
        }
    }
}