package io.sponges.bot.dashboard.newdesign;

import io.sponges.bot.dashboard.newdesign.user.User;
import io.sponges.bot.dashboard.newdesign.user.UserManager;
import spark.Request;
import spark.Response;
import spark.Session;

import java.util.HashMap;
import java.util.Map;

public abstract class Page {

    protected static final Map<Integer, String> SESSIONS = new HashMap<>();

    private final String route;
    private final Method method;
    private final boolean template;
    private final boolean auth;

    private User user = null;

    public Page(String route, Method method, boolean template, boolean auth) {
        this.route = route;
        this.method = method;
        this.template = template;
        this.auth = auth;
    }

    public Object internalExecute(UserManager userManager, Request request, Response response, Model.Builder builder) {
        Session session = request.session();
        String alertKey = "alert";
        if (session.attributes().contains(alertKey)) {
            String alert = session.attribute(alertKey);
            builder.data(alertKey, alert);
            session.removeAttribute(alertKey);
        }
        boolean authorised = isAuthorised(session);
        if (auth && !authorised) {
            session.attribute(alertKey, "You must be logged in to do that!");
            String uri = request.uri();
            if (request.queryParams().size() > 0) {
                uri += "?" + request.queryString();
            }
            session.attribute("requested-url", uri);
            response.redirect("/account/login");
            return null;
        }
        builder.with("logged_in", authorised);
        if (authorised) {
            user = userManager.getOrLoadUser(session);
        }
        return execute(request, response, builder);
    }

    protected abstract Object execute(Request request, Response response, Model.Builder builder);

    protected boolean isAuthorised(Session session) {
        if (!session.attributes().contains("id") || !session.attributes().contains("email") || !session.attributes().contains("token")) {
            return false;
        }
        int id = session.attribute("id");
        String token = session.attribute("token");
        return SESSIONS.containsKey(id) && SESSIONS.get(id).equals(token);
    }

    public String getRoute() {
        return route;
    }

    public Method getMethod() {
        return method;
    }

    public boolean isTemplate() {
        return template;
    }

    protected User getUser() {
        return user;
    }

    public enum Method {
        GET, POST, PUT, PATCH, DELETE, OPTIONS
    }
}
