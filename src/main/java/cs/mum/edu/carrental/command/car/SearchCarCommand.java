package cs.mum.edu.carrental.command.car;

import cs.mum.edu.carrental.Model.Car;
import cs.mum.edu.carrental.Model.CarFilter;
import cs.mum.edu.carrental.command.CommandTemplate;
import cs.mum.edu.carrental.dao.DaoCar;
import cs.mum.edu.carrental.dao.DaoFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class SearchCarCommand extends CommandTemplate {

    private static final Logger logger = LoggerFactory.getLogger(SearchCarCommand.class);
    public static final int PAGE_LIMIT = 10;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String[] path = request.getServletPath().split("/");
        Integer pageNumber = getPageNumber(path);
        CarFilter carFilter = getCarFilter(request);
        try {
            createCarPage(request, pageNumber, carFilter);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/"+ path[1] +".tiles");
            requestDispatcher.forward(request, response);
        } catch (SQLException e) {
            infoRedirect(request, response, "DATABASE_PROBLEM" );
            logger.error("DBError", e);
        } catch (Exception e) {
            logger.error("Forward", e);
        }

    }

    private void createCarPage(HttpServletRequest request, Integer pageNumber, CarFilter carFilter) throws SQLException {
        DaoCar daoCar = DaoFactory.getDaoCar();
        List<Car> cars = daoCar.getFilteredPage(pageNumber, PAGE_LIMIT, carFilter);
        Integer carCount = carFilter.getResultQty();

        int totalPages = carCount/PAGE_LIMIT + 1;
        int current = pageNumber;
        int begin = Math.max(1, current - 2);
        int end = Math.min(begin + 5, totalPages);

        request.getSession().setAttribute("beginIndex", begin);
        request.getSession().setAttribute("endIndex", end);
        request.getSession().setAttribute("currentIndex", pageNumber);
        request.getSession().setAttribute("totalPages", totalPages);
        request.getSession().setAttribute("cars", cars);
        request.getSession().setAttribute("carFilter", carFilter);
    }

    private CarFilter getCarFilter(HttpServletRequest request) {
        CarFilter carFilter = new CarFilter();
        if (request.getMethod().toLowerCase().equals("post")) {
            try {
                BeanUtils.populate(carFilter, request.getParameterMap());
            } catch (ReflectiveOperationException e) {
                logger.error("BeanUtilsError", e);
            }
        } else {
            carFilter = (CarFilter) request.getSession().getAttribute("carFilter");
            if (carFilter == null) carFilter = new CarFilter();
        }
        return carFilter;
    }

    private Integer getPageNumber(String[] path) {
        return path.length > 3 ? Integer.parseInt(path[3]) : Integer.parseInt("1");
    }
}
