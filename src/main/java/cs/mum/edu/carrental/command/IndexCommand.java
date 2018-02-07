package cs.mum.edu.carrental.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexCommand extends CommandTemplate {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletPath();
        if (path.equals("/"))
            path = "/index";
        dispatcherForward(request, response, request.getRequestDispatcher(path + ".tiles"));
    }
}
