package by.epam.training.helper.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import by.epam.training.helper.bean.News;
import by.epam.training.helper.command.Command;
import by.epam.training.helper.command.exception.CommandException;
import by.epam.training.helper.constant.ParameterName;
import by.epam.training.helper.constant.Url;
import by.epam.training.helper.service.NewsService;
import by.epam.training.helper.service.exception.ServiceException;
import by.epam.training.helper.service.factory.ServiceFactory;
import by.epam.training.helper.tools.ItemManager;
import by.epam.training.helper.tools.NullOrEmpty;
import by.epam.training.helper.tools.StringInNumber;

public class ShowNews implements Command {
	private static final Logger logger = LogManager.getLogger(ShowNews.class);
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, CommandException {
		String pageIndex = request.getParameter(ParameterName.NUMBER_PAGE);
		int pageNumber = 1;
		ItemManager<News> itemManager = null;
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		NewsService newsService = serviceFactory.getNewsService();
		if(!NullOrEmpty.isNullOrEmpty(pageIndex)){
			pageNumber = StringInNumber.parseString(pageIndex, pageNumber);
		}
		try {
			itemManager = newsService.getNewsPage(pageNumber);
			request.setAttribute(ParameterName.NEWS, itemManager.getItems());
			request.setAttribute(ParameterName.AMONT_PAGE, itemManager.getPageCount());
			request.setAttribute(ParameterName.MESSAGE, request.getParameter(ParameterName.MESSAGE));
			request.getRequestDispatcher(Url.NEWS).forward(request, response);
		} catch (ServiceException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

}