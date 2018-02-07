package cs.mum.edu.carrental.command;

import cs.mum.edu.carrental.command.car.ReserveCommand;
import cs.mum.edu.carrental.command.car.SearchCarCommand;
import cs.mum.edu.carrental.command.order.*;
import cs.mum.edu.carrental.command.order.MyDataCommand;
import cs.mum.edu.carrental.command.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("index", new IndexCommand());
        commands.put("bad", new CommandTemplate());
        //User relevent begin
        commands.put("auth", new AuthCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("searchCar", new SearchCarCommand());
        //User relevent end

        //Customer relevent begin
        commands.put("myData", new MyDataCommand());
        commands.put("reserve", new ReserveCommand());
        //Customer relevent end

        //Administrator relevent begin
        commands.put("allData", new AllDataCommand());
        commands.put("order", new OrderDealCommand());
        commands.put("user", new UserInfoCommand());
        commands.put("approve_order", new OrderApproveCommand());
        commands.put("reject_order", new OrderRejectCommand());
        commands.put("add_penalty", new OrderPenaltyCommand());
        commands.put("close_order", new OrderCloseCommand());
        //Administrator relevent end
        

    }

    public static Command createCommand(HttpServletRequest request) {


        String value = request.getParameter("command");

        if (value != null) {
            return getCommandByParameter(value);
        }

        return getCommandByRequestPath(request);


    }

    private static Command getCommandByRequestPath(HttpServletRequest request) {
        String mainPath = buildPathForSearch(request);

        switch (mainPath) {
            case "searchcar":
                return commands.get("searchCar");
            case "searchcar/pages":
                return commands.get("searchCar");
            case "reserve":
                return commands.get("reserve");
            case "my_new_orders":
                return commands.get("myData");
            case "my_rejected_orders":
                return commands.get("myData");
            case "my_approved_orders":
                return commands.get("myData");
            case "my_closed_orders":
                return commands.get("myData");
            case "all_new_orders":
                return commands.get("allData");
            case "all_approved_orders":
                return commands.get("allData");
            case "all_rejected_orders":
                return commands.get("allData");
            case "all_closed_orders":
                return commands.get("allData");
            case "order":
                return commands.get("order");
            case "user":
                return commands.get("user");
            default:
                return commands.get("index");
        }
    }

    private static Command getCommandByParameter(String value) {
        switch (value.toLowerCase()) {
            case "register":
                return commands.get("registration");
            case "auth":
                return commands.get("auth");
            case "language":
                return commands.get("language");
            case "logout":
                return commands.get("logout");
            case "searchcar":
                return commands.get("searchCar");
            case "reserve":
                return commands.get("reserve");
            case "approve_order":
                return commands.get("approve_order");
            case "reject_order":
                return commands.get("reject_order");
            case "add_penalty":
                return commands.get("add_penalty");
            case "close_order":
                return commands.get("close_order");
            default:
                return commands.get("bad");
        }
    }

    private static String buildPathForSearch(HttpServletRequest request) {
        String[] path = request.getServletPath().split("/");
        if (path.length > 2) {
            return path[1] + "/" + path[2];
        }
        if (path.length == 2)
            return path[1];
        return "";
    }
}
