package app.example.phanmembanhoa.Common;

import app.example.phanmembanhoa.Model.User;

public class Common {
    public static User currentUser;

    public static String DELETE = "Xóa";

    public static String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else if (status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }
}
