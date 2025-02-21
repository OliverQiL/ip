public class oongaliegabangalie {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NEWLINE = System.lineSeparator();
    private static final String BOT_NAME = "Oongaliegabangalie";

    private static void printGreeting() {
        String greeting = DIVIDER + NEWLINE
                + "Hello! I'm " + BOT_NAME + NEWLINE
                + "What can I do for you?" + NEWLINE
                + DIVIDER;
        System.out.println(greeting);
    }

    private static void printGoodbye() {
        String goodbye = DIVIDER + NEWLINE
                + "Bye. Hope to see you again soon!" + NEWLINE
                + "Oongaliegabangalie is always watching..." + NEWLINE
                + DIVIDER;
        System.out.println(goodbye);
    }

    public static void main(String[] args) {
        printGreeting();
        printGoodbye();
    }
}