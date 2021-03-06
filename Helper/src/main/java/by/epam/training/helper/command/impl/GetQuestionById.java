package by.epam.training.helper.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epam.training.helper.bean.Answer;
import by.epam.training.helper.bean.PageItem;
import by.epam.training.helper.bean.Question;
import by.epam.training.helper.command.Command;
import by.epam.training.helper.constant.ErrorMessage;
import by.epam.training.helper.constant.ParameterName;
import by.epam.training.helper.constant.Url;
import by.epam.training.helper.service.AnswerService;
import by.epam.training.helper.service.QuestionService;
import by.epam.training.helper.service.exception.ServiceException;
import by.epam.training.helper.service.factory.ServiceFactory;
import by.epam.training.helper.utils.StringUtils;
import by.epam.training.helper.utils.StringParser;

/**
 * Command to get the question by ID
 * @author Nikolaev Ilya
 * {@link Command}  invokes method execute() with the request , response  and return jsp question
 */
public class GetQuestionById implements Command{
	private static final Logger logger = LogManager.getLogger(GetQuestionById.class);
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = request.getParameter(ParameterName.MESSAGE);
		String pageIndex = request.getParameter(ParameterName.NUMBER_PAGE);
		String questionIdParametr = request.getParameter(ParameterName.QUESTION_ID);
		int pageNumber = 1;
		int	questionId = 0;
		Question question = null;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		QuestionService questionService = serviceFactory.getQuestionService();
		if(!StringUtils.isNullOrEmpty(pageIndex)){
			pageNumber = StringParser.parseString(pageIndex, pageNumber);
		}
		if(!StringUtils.isNullOrEmpty(questionIdParametr)){
			try {
				questionId = StringParser.parseString(questionIdParametr);
				question = questionService.getQuestionById(questionId);
				if(question != null){
					 AnswerService answerService = serviceFactory.getAnswerService();
					 PageItem<Answer> answerManager = null;
					 answerManager = answerService.getAnswerWithLimit(pageNumber, questionId);
					 request.setAttribute(ParameterName.QUESTION, question);
					 request.setAttribute(ParameterName.ANSWERS, answerManager.getItems());
					 request.setAttribute(ParameterName.CURRENT_PAGE, pageNumber);
					 if(!StringUtils.isNullOrEmpty(message)){
						 request.setAttribute(ParameterName.MESSAGE, message);
					 }
					 request.getRequestDispatcher(Url.QUESTION).forward(request, response);
				 }else{
					 logger.error(ErrorMessage.ERROR_QUESTION_NOT_FOUND);
					 request.getRequestDispatcher(Url.REDIRECT_HOME_PAGE_WITH_MESSAGE + ErrorMessage.ERROR_QUESTION_NOT_FOUND).forward(request, response); 
				 }
			} catch (ServiceException e) {
				logger.error(ErrorMessage.ERROR_QUESTION_NOT_FOUND);
				request.getRequestDispatcher(Url.REDIRECT_HOME_PAGE_WITH_MESSAGE + ErrorMessage.ERROR_QUESTION_NOT_FOUND).forward(request, response);
			} catch (NumberFormatException e) {
				logger.error(ErrorMessage.ERROR_DETERMINETED_QUESTION_ID);
				request.getRequestDispatcher(Url.REDIRECT_HOME_PAGE_WITH_MESSAGE + ErrorMessage.ERROR_QUESTION_NOT_FOUND).forward(request, response);
			}
		}else{
			 logger.error(ErrorMessage.ERROR_QUESTION_ID_EMPTY);
			 request.getRequestDispatcher(Url.REDIRECT_HOME_PAGE_WITH_MESSAGE + ErrorMessage.ERROR_QUESTION_NOT_FOUND).forward(request, response); 
		}
		
	}

}
