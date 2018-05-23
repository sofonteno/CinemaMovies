package fr.me.interfaces.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "welcome-page", urlPatterns = "/welcome")
public class WelcomeServlet extends HttpServlet {
  private static final String VUE_ACCUEIL = "/WEB-INF/welcome.jsp";

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    /* Récupération de la session */
    HttpSession session = request.getSession();

    /*
     * A la reception d'une requete GET, simple affichage de la page d'accueil
     * de l'outil
     */
    this.getServletContext().getRequestDispatcher(VUE_ACCUEIL).forward(request, response);

  }

}
