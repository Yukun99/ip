package me.yukun99.ip.core;

import me.yukun99.ip.HelpBot;
import me.yukun99.ip.commands.AddCommand;
import me.yukun99.ip.commands.Command;
import me.yukun99.ip.commands.DeleteCommand;
import me.yukun99.ip.commands.DoneCommand;
import me.yukun99.ip.commands.ExitCommand;
import me.yukun99.ip.commands.HelpCommand;
import me.yukun99.ip.commands.ListCommand;
import me.yukun99.ip.commands.UpdateCommand;
import me.yukun99.ip.exceptions.HelpBotIllegalArgumentException;
import me.yukun99.ip.exceptions.HelpBotInvalidCommandException;

import java.util.Scanner;

import static me.yukun99.ip.tasks.Task.Type;

/**
 * Parser for HelpBot commands.
 */
public class Parser {
	// Current instance of HelpBot.
	private final HelpBot helpBot;
	// Scanner used to scan for user inputs.
	private final Scanner scanner;
	// TaskList instance from the current instance of HelpBot.
	private final TaskList taskList;
	// Ui instance from the current instance of HelpBot.
	private final Ui ui;
	// Storage instance from the current instance of HelpBot.
	private final Storage storage;

	/**
	 * Constructor for a Parser instance.
	 *
	 * @param helpBot HelpBot instance that this instance of Parser belongs to.
	 * @param scanner Scanner used for user input scanning.
	 * @param taskList TaskList instance from the current instance of HelpBot.
	 * @param ui Ui instance from the current instance of HelpBot.
	 * @param storage Storage instance from the current instance of HelpBot.
	 */
	public Parser(HelpBot helpBot, Scanner scanner, TaskList taskList, Ui ui, Storage storage) {
		this.helpBot = helpBot;
		this.scanner = scanner;
		this.taskList = taskList;
		this.ui = ui;
		this.storage = storage;
	}

	/**
	 * Parses the next command sent by the user.
	 *
	 * @return Next command sent to the HelpBot.
	 * @throws HelpBotIllegalArgumentException If arguments provided for the commands are missing or wrong.
	 * @throws HelpBotInvalidCommandException If command sent by the user is invalid.
	 */
	public final Command parse() throws HelpBotIllegalArgumentException, HelpBotInvalidCommandException {
		String message = this.scanner.nextLine();
		Command command = null;
		if (message.equals("list")) {
			command = new ListCommand(null, this.taskList, this.ui);
		}
		if (message.startsWith("list ")) {
			String date = message.replace("list ", "");
			String[] args = {date};
			command = new ListCommand(args, this.taskList, this.ui);
		}
		if (message.startsWith("todo ")) {
			String name = message.replaceFirst("todo ", "");
			String[] args = {name};
			command = new AddCommand(args, this.taskList, this.ui, Type.TODO, this.storage);
		}
		if (message.startsWith("deadline ")) {
			String args = message.replaceFirst("deadline ", "");
			if (args.contains(" /by ")) {
				String[] argsSplit = args.split(" /by ");
				command = new AddCommand(argsSplit, this.taskList, this.ui, Type.DEADLINE, this.storage);
			} else {
				throw new HelpBotIllegalArgumentException(args);
			}
		}
		if (message.startsWith("event ")) {
			String args = message.replaceFirst("event ", "");
			if (args.contains(" /at ")) {
				String[] argsSplit = args.split(" /at ");
				command = new AddCommand(argsSplit, this.taskList, this.ui, Type.EVENT, this.storage);
			} else {
				throw new HelpBotIllegalArgumentException(args);
			}
		}
		if (message.startsWith("update ")) {
			String args = message.replaceFirst("update ", "");
			if (args.contains(" /to ")) {
				String[] argsSplit = args.split(" /to ");
				command = new UpdateCommand(argsSplit, this.taskList, this.ui, this.storage);
			} else {
				throw new HelpBotIllegalArgumentException(args);
			}
		}
		if (message.startsWith("delete ")) {
			String strIndex = message.replaceFirst("delete ", "");
			String[] args = {strIndex};
			command = new DeleteCommand(args, this.taskList, this.ui, this.storage);
		}
		if (message.startsWith("done ")) {
			String strIndex = message.replaceFirst("done ", "");
			String[] args = {strIndex};
			command = new DoneCommand(args, this.taskList, this.ui);
		}
		if (message.equals("help")) {
			command = new HelpCommand(null, null, this.ui);
		}
		if (message.equals("bye")) {
			command = new ExitCommand(null, null, this.ui, this.helpBot);
		}
		if (command == null) {
			throw new HelpBotInvalidCommandException(message);
		}
		return command;
	}
}
