package by.epam.training.helper.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epam.training.helper.command.exception.CommandNotFoundException;
import by.epam.training.helper.command.impl.AddAnswer;
import by.epam.training.helper.command.impl.AssignModerator;
import by.epam.training.helper.command.impl.ChangePassword;
import by.epam.training.helper.command.impl.CreateNews;
import by.epam.training.helper.command.impl.CreateQuestion;
import by.epam.training.helper.command.impl.EditAnswer;
import by.epam.training.helper.command.impl.EditNews;
import by.epam.training.helper.command.impl.EditProfile;
import by.epam.training.helper.command.impl.EditQuestion;
import by.epam.training.helper.command.impl.EvaluateAnswer;
import by.epam.training.helper.command.impl.GetAllUsers;
import by.epam.training.helper.command.impl.GetHomePage;
import by.epam.training.helper.command.impl.GetPageEditAnswer;
import by.epam.training.helper.command.impl.GetPageEditQuestion;
import by.epam.training.helper.command.impl.GetQuestionById;
import by.epam.training.helper.command.impl.GetSignInPage;
import by.epam.training.helper.command.impl.GetSignUpPage;
import by.epam.training.helper.command.impl.GetUserProfile;
import by.epam.training.helper.command.impl.LockUser;
import by.epam.training.helper.command.impl.SetLocale;
import by.epam.training.helper.command.impl.ShowNews;
import by.epam.training.helper.command.impl.ShowNewsById;
import by.epam.training.helper.command.impl.SignIn;
import by.epam.training.helper.command.impl.SignOut;
import by.epam.training.helper.command.impl.SignUp;
import by.epam.training.helper.command.impl.UnassignModerator;
import by.epam.training.helper.command.impl.UnlockUser;
import by.epam.training.helper.constant.CommandName;
import by.epam.training.helper.constant.ErrorMessage;

/**
 * Full list of commands
 * @author Nikolaev Ilya
 */
public class CommandHelper {
	private static final Logger logger = LogManager.getLogger(CommandHelper.class);
	private static CommandHelper instance;
	private Map<String, Command> commands = new HashMap<>();

	private CommandHelper() {
		commands.put(CommandName.HOME, new GetHomePage());
		commands.put(CommandName.SET_LOCALE, new SetLocale());
		commands.put(CommandName.SIGN_UP, new SignUp());
		commands.put(CommandName.SIGN_IN, new SignIn());
		commands.put(CommandName.SIGN_UP_PAGE, new GetSignUpPage());
		commands.put(CommandName.SIGN_OUT, new SignOut());
		commands.put(CommandName.USER_PROFILE, new GetUserProfile());
		commands.put(CommandName.CREATE_QUESTION, new CreateQuestion());
		commands.put(CommandName.CHANGE_PASSWORD, new ChangePassword());
		commands.put(CommandName.EDIT_PROFILE, new EditProfile());
		commands.put(CommandName.SHOW_QUESTION_BY_ID, new GetQuestionById());
		commands.put(CommandName.ADD_ANSWER, new AddAnswer());
		commands.put(CommandName.SHOW_USERS, new GetAllUsers());
		commands.put(CommandName.LOCK_USER, new LockUser());
		commands.put(CommandName.UNLOCK_USER, new UnlockUser());
		commands.put(CommandName.GET_PAGE_EDIT_ANSWER, new GetPageEditAnswer());
		commands.put(CommandName.GET_PAGE_EDIT_QUESTION, new GetPageEditQuestion());
		commands.put(CommandName.EDIT_ANSWER, new EditAnswer());
		commands.put(CommandName.EDIT_QUESTION, new EditQuestion());
		commands.put(CommandName.EVALUATE_ANSWER, new EvaluateAnswer());
		commands.put(CommandName.CREATE_NEWS, new CreateNews());
		commands.put(CommandName.SHOW_NEWS, new ShowNews());
		commands.put(CommandName.SIGN_IN_PAGE, new GetSignInPage());
		commands.put(CommandName.ASSIGN_MODERATOR, new AssignModerator());
		commands.put(CommandName.UNASSIGN_MODERATOR, new UnassignModerator());
		commands.put(CommandName.SHOW_NEWS_BY_ID, new ShowNewsById());
		commands.put(CommandName.EDIT_NEWS, new EditNews());
	}
	
	public static CommandHelper getInstance() {
		if (instance == null) {
			instance = new CommandHelper();
		}
		return instance;
	}
	
	public Command getCommand(String commandName) throws CommandNotFoundException{
		Command command;
		command = commands.get(commandName);
		if(command != null){
			return command;
		}else{
			System.out.println(command);
			logger.error(ErrorMessage.ERROR_COMMAND_NOT_FOUND + commandName);
			throw new CommandNotFoundException();
		}	
	}

}
