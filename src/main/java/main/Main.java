package main;

import admin.AdminPageServlet;
import frontend.SignInServlet;
import frontend.SignUpServlet;
import frontend.UserProfileServlet;
import interfaces.AccountService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import utils.AccountServiceImpl;

import javax.servlet.Servlet;

/**
 * @author alexey
 */
public class Main {
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("Use port as the first argument");
            System.exit(1);
        }

        final String portString = args[0];
        int port = Integer.valueOf(portString);

        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        AccountService accountService = new AccountServiceImpl();

        Servlet signIn = new SignInServlet(accountService);
        Servlet signUp = new SignUpServlet(accountService);
        Servlet profile = new UserProfileServlet(accountService);
        Servlet admin = new AdminPageServlet(accountService);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signIn), SignInServlet.signInPageURL);
        context.addServlet(new ServletHolder(signUp), SignUpServlet.signUpPageURL);
        context.addServlet(new ServletHolder(profile), UserProfileServlet.userProfilePageURL);
        context.addServlet(new ServletHolder(admin), AdminPageServlet.adminPageURL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}