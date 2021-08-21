package me.yukun99.ip;

import me.yukun99.ip.commands.Command;
import me.yukun99.ip.core.Parser;
import me.yukun99.ip.core.TaskList;
import me.yukun99.ip.core.Ui;
import me.yukun99.ip.exceptions.HelpBotIllegalArgumentException;
import me.yukun99.ip.exceptions.HelpBotInvalidCommandException;
import me.yukun99.ip.exceptions.HelpBotInvalidTaskTypeException;

import java.util.Scanner;

public class HelpBot {
	// Constants/Variables related to the instance.
	private final Scanner scanner = new Scanner(System.in);
	private final Ui ui;
	private final Parser parser;

	// Whether to exit bot.
	private boolean exit = false;

	/**
	 * Constructor for a HelpBot instance.
	 *
	 * @param name Name of the bot to be created.
	 */
	public HelpBot(String name) {
		TaskList taskList = new TaskList();
		this.ui = new Ui(name, taskList);
		this.parser = new Parser(this, this.scanner, taskList, this.ui);
		this.ui.start();
		this.listen();
	}

	/**
	 * Method to listen for user inputs.
	 */
	private void listen() {
		while (this.scanner.hasNext()) {
			try {
				Command command = this.parser.parse();
				command.run();
			} catch (HelpBotIllegalArgumentException | HelpBotInvalidCommandException | HelpBotInvalidTaskTypeException e) {
				this.ui.error(e);
			}
			if (this.exit) {
				return;
			}
		}
	}

	public void exit() {
		this.exit = true;
		this.scanner.close();
		this.ui.exit();
	}
}