package com.se;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


@WebServlet("/history")
public class History extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try{
            //Getting data from database
            Connection connection = DatabaseConnection.getConnection();
            ResultSet resultset = connection.createStatement().executeQuery("select  date from history group by date order by date desc;");

            LinkedHashMap<String, ArrayList<HistoryResult>> results = new LinkedHashMap<String, ArrayList<HistoryResult>>();
            while(resultset.next()) {
                results.put(resultset.getString("date"), new ArrayList<HistoryResult>());
            }

            for(String key: results.keySet()) {
                ArrayList<HistoryResult> list = new ArrayList<HistoryResult>();
                ResultSet hisResults = connection.createStatement().executeQuery("select * from history where date='"+key+"' order by time desc;");
                while(hisResults.next()) {
                    HistoryResult historyResult = new HistoryResult();
                    historyResult.setTime(String.valueOf(hisResults.getTime("time")));
                    historyResult.setTitle(hisResults.getString("title"));
                    historyResult.setLink(hisResults.getString("link"));

                    list.add(historyResult);
                }

                results.put(key, list);
            }

            request.setAttribute("results", results);
            request.getRequestDispatcher("history.jsp").forward(request, response);
        }
        catch (IOException ioException) {
            System.out.println(ioException);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}

