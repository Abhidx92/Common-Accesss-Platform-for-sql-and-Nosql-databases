package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AttributeValues;
import model.Results;
import nativeQuery.builder.QueryTranslator;
import query.parser.gettablecolumns;



/**
 * Servlet implementation class GetResultController
 */
public class GetResultController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QueryTranslator queryTranslator = new QueryTranslator(); 
		AttributeValues attributeValues =  new AttributeValues();
		gettablecolumns getTableColumns = new gettablecolumns();
		Results results = new Results();
		
		/* ---------------------------------------------------------------------------------------------------------------------------------*/
		/* Fetching values from home page of CAP*/
		/* ---------------------------------------------------------------------------------------------------------------------------------*/
		String query = request.getParameter("query").toString();
		String[] database = request.getParameterValues("database");
		
		/* ---------------------------------------------------------------------------------------------------------------------------------*/
		/* Parsing SQL query*/
		/* ---------------------------------------------------------------------------------------------------------------------------------*/
		String parsedString = getTableColumns.parse(query);
		
		/* ---------------------------------------------------------------------------------------------------------------------------------*/
		/* calling Query Translator*/
		/* ---------------------------------------------------------------------------------------------------------------------------------*/
		attributeValues = queryTranslator.fetchAllDetails(parsedString, query);
		
		/* ---------------------------------------------------------------------------------------------------------------------------------*/
		/* Fetching results from different drivers*/
		/* ---------------------------------------------------------------------------------------------------------------------------------*/
		results = queryTranslator.callDatabase(database, attributeValues,query);
		
		
		HttpSession session = request.getSession();
		session.setAttribute("alien",  results);
		
		response.sendRedirect("showResult.jsp");
	}

	

}
