public class Main {
	// Fancy name for my bot
	private static final String LOGO = " ____        _     \n"
			+ "|  _ \\      | |    \n"
			+ "| |_) | ___ | |__  \n"
			+ "|  _ < / _ \\| '_ \\ \n"
			+ "| |_) | (_) | |_) |\n"
			+ "|____/ \\___/|_.__/ \n";

	/**
	 * Entry point for my help bot.
	 *
	 * @param args Command line arguments specified when running the program.
	 */
	public static void main(String[] args) {
		HelpBot duke = new HelpBot(LOGO);
		duke.start();
	}
}
