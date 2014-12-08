package main;

import interfaces.services.AccountService;
import interfaces.services.DBService;
import interfaces.services.WebSocketService;
import services.ResourceFactory;
import servlets.AdminPageServletImpl;
import services.WebSocketServiceImpl;
import services.DBServiceImpl;
import interfaces.servlets.AdminPageServlet;
import interfaces.servlets.LogOutServlet;
import interfaces.servlets.SignInServlet;
import interfaces.servlets.SignUpServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;
import services.AccountServiceImpl;

import javax.servlet.Servlet;

class Main {
    public static void main(String[] args) throws Exception {
        ResourceFactory.instance().setResource(ResourceFactory.serverSettingsFilename);
        ServerSettings serverSettings = (ServerSettings) ResourceFactory.instance().getResource(ResourceFactory.serverSettingsFilename);
        final int port = serverSettings.getPort();
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        DBService dbService = new DBServiceImpl();
        AccountService accountService = new AccountServiceImpl(dbService);
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

// TODO selenium - тест первой страницы