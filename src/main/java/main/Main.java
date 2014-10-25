package main;

import admin.AdminPageServlet;
import base.Port;
import frontend.*;
import interfaces.*;
import base.WebSocketServiceImpl;
import base.GameMechanicsImpl;
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
public class Main {
    public static void main(String[] args) throws Exception {
        Port portObj = (Port) ReadXMLFileSAX.readXML("port.xml");
        final int port = portObj.getPort();
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        AccountService accountService = new AccountServiceImpl();
        WebSocketService webSocketService = new WebSocketServiceImpl();

        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

        Servlet signIn = new SignInServlet(accountService);
        Servlet signUp = new SignUpServlet(accountService);
        Servlet profile = new UserProfileServlet(accountService);
        Servlet admin = new AdminPageServlet(accountService);
        WebSocketGameServlet webSocketGameServlet = new WebSocketGameServlet(gameMechanics,
                webSocketService, accountService);
        Servlet frontendServlet = new FrontendServlet(accountService);

        context.addServlet(new ServletHolder(signIn), SignInServlet.signInPageURL);
        context.addServlet(new ServletHolder(signUp), SignUpServlet.signUpPageURL);
        context.addServlet(new ServletHolder(profile), UserProfileServlet.userProfilePageURL);
        context.addServlet(new ServletHolder(admin), AdminPageServlet.adminPageURL);
        context.addServlet(new ServletHolder(webSocketGameServlet), WebSocketGameServlet.gamePageURL);
        context.addServlet(new ServletHolder(frontendServlet), "/game.html");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("static");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        gameMechanics.run();
    }
}

/*
    TODO
    игра закончилась -> F5 -> косяки
    синхронизировать с фронтендом необходимые переменные
 */