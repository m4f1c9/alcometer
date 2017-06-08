package alcometer_server.statistics;

import alcometer_server.util.Mocks;
import alcometer_server.util.exceptions.DAOExceptions;
import com.alibaba.fastjson.JSON;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class StatisticsServlet extends HttpServlet {

    Logger logger;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = (String) request.getAttribute("userID");
        String period = request.getParameter("period");

        try {
            StatisticsDAO dao = Mocks.createStatisticsDAO();
            Statistic statistic = dao.getStatistic(userID, period);
            String answer = JSON.toJSONString(statistic);

            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(answer);

        } catch (DAOExceptions e) {
            response.sendError(400, "Incorrect period");
            logger.debug("Incorrect period - " + period);            
        }
    }

    @Override
    public void init() throws ServletException {
        logger = Logger.getLogger(StatisticsServlet.class);
        logger.debug("Initialized StatisticsServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
