package main;

import admin.AdminPageServletImpl;
import base.Port;
import frontend.*;
import interfaces.*;
import base.WebSocketServiceImpl;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import utils.AccountServiceImpl;
import utils.ReadXMLFileSAX;

import javax.servlet.Servlet;

/**
 * @author alexey
 */
class Main {
    public static void main(String[] args) throws Exception {
        Port portObj = (Port) ReadXMLFileSAX.readXML("port.xml");
        final int port = portObj.getPort();
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        AccountService accountService = new AccountServiceImpl();
        WebSocketService webSocketService = new WebSocketServiceImpl();

        Servlet signIn = new SignInServletImpl(accountService);
        Servlet signUp = new SignUpServletImpl(accountService);
        Servlet logOut = new LogOutServletImpl(accountService);
        Servlet admin  = new AdminPageServletImpl(accountService);
        WebSocketGameServlet webSocketGameServlet = new WebSocketGameServlet(webSocketService, accountService);
        Servlet frontendServlet = new FrontendServlet(accountService);

        context.addServlet(new ServletHolder(signIn), SignInServlet.signInPageURL);
        context.addServlet(new ServletHolder(signUp), SignUpServlet.signUpPageURL);
        context.addServlet(new ServletHolder(logOut), LogOutServlet.logOutPageUrl);
        context.addServlet(new ServletHolder(admin),  AdminPageServlet.adminPageURL);
        context.addServlet(new ServletHolder(webSocketGameServlet), WebSocketGameServlet.gamePageURL);
        context.addServlet(new ServletHolder(frontendServlet), FrontendServlet.frontendUrl);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
    }
}
