package com.yura.repair.command.user;

import com.yura.repair.command.Command;
import com.yura.repair.command.MultipleMethodCommand;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand extends MultipleMethodCommand {

    @Override
    protected String executeGet(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            request.getSession().removeAttribute("user");
        }
        return "redirect:/";
    }

    @Override
    protected String executePost(HttpServletRequest request) {
        return null;
    }
}
