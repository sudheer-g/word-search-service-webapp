package com.work.servlets;

import com.work.wordSearchService.MultiThreadedWordSearchService;
import com.work.wordSearchService.Query;
import com.work.wordSearchService.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class WordSearchServlet extends HttpServlet{
    Logger logger = LogManager.getLogger();
    public void init() throws ServletException{
        System.out.println("Hit Servlet");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MultiThreadedWordSearchService wordSearchService = new MultiThreadedWordSearchService();
        String directoryPath = req.getParameter("directory-path");
        String pattern = req.getParameter("pattern");
        logger.info("{}, {}", directoryPath, pattern);
        List<Result> resultList = wordSearchService.search(new Query(directoryPath, pattern, true));
        PrintWriter out = resp.getWriter();
        out.println("<h1>TODO</h1>");
        for(Result result : resultList) {
            out.println("<p>" + result + "</p>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //doGet(req, resp);
    }

    public void destroy() {}
}
