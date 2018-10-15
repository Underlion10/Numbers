package com.numbers.listener;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.numbers.pojo.Intento;
import com.numbers.servlet.AdivinarServlet;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

	/**
	 * Default constructor.
	 */
	public SessionListener() {
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent se) {
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		System.out.println(session.getAttribute("guessNumber"));
		AdivinarServlet srvlet = new AdivinarServlet();
		int num = srvlet.decryptNumber((byte[]) (session.getAttribute("guessNumber")));
		Connection conn = (Connection) se.getSession().getServletContext().getAttribute("dbConn");
		String query = "insert into partidas (nombre, intentos, tiempo, numero) values ('";
		query += session.getAttribute("nombre") + "', '";
		Intento attempt = (Intento) session.getAttribute("intento");
		System.out.println(attempt);
		if (attempt.getIntentoActual() > 0) {
			query += attempt.getIntentoActual() + "', '";
			query += attempt.getTotalTime(attempt.getTiempo()) + "','";
			query += num + "')";
			try {
				Statement stm = conn.createStatement();
				stm.executeUpdate(query);
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}
		}
	}

}
