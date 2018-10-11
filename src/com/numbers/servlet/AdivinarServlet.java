package com.numbers.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.numbers.pojo.Intento;

/**
 * Servlet implementation class AdivinarServlet
 */
@WebServlet("/AdivinarServlet")
public class AdivinarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdivinarServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext sc = getServletContext();
		HttpSession session = request.getSession();
		RequestDispatcher rd = sc.getRequestDispatcher("/");
		if (request.getSession().getAttribute("won") == null) {
			if (request.getSession().getAttribute("guessNumber") == null) {
				try {
					int min = Integer.parseInt(request.getParameter("min-value"));
					int max = Integer.parseInt(request.getParameter("max-value"));
					int value = (int) (Math.random() * (max - min) + min);
					session.setAttribute("min-value", min);
					session.setAttribute("max-value", max);
					session.setAttribute("guessNumber", encryptNumber(value));
					session.setAttribute("intento", new Intento(new ArrayList<String>(), new Date(), 0, ""));
				} catch (NumberFormatException e) {
					rd.forward(request, response);
				}
			} else {
				Intento attempt = (Intento) session.getAttribute("intento");
				try {
					byte[] encryptGuess = (byte[]) request.getSession().getAttribute("guessNumber");
					int numberGuess = decryptNumber(encryptGuess);
					int numberAttempt = Integer.parseInt(request.getParameter("number"));
					if (numberAttempt == numberGuess) {
						attempt.setIntentoActual(attempt.getIntentoActual() + 1);
						attempt.getIntentos().add("Has ganado!");
						attempt.setMensaje("Has ganado!, el número era el " + numberGuess);
						request.setAttribute("won", true);
					} else if (numberAttempt < numberGuess) {
						attempt.setIntentoActual(attempt.getIntentoActual() + 1);
						attempt.getIntentos().add("Debe introducir un número mayor que " + numberAttempt);
						attempt.setMensaje("Debe introducir un número mayor que " + numberAttempt);
					} else {
						attempt.setIntentoActual(attempt.getIntentoActual() + 1);
						attempt.getIntentos().add("Debe introducir un número menor que " + numberAttempt);
						attempt.setMensaje("Debe introducir un número menor que " + numberAttempt);
					}
					attempt.getIntentos().get(attempt.getIntentos().size() - 1)
							.concat(", Tiempo de partida: " + attempt.getTotalTime(attempt.getTiempo()));
				} catch (NumberFormatException e) {
					attempt.setMensaje("Debe introducir un valor númerico correcto");
				}
			}
		}
		rd.forward(request, response);
	}
	
	public byte[] encryptNumber(int number) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		DataOutputStream dos = new DataOutputStream (baos);
		try {
			dos.writeInt (number);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		return data;
	}
	
	public int decryptNumber(byte[] data) {
		ByteArrayInputStream bais = new ByteArrayInputStream (data);
		DataInputStream dis = new DataInputStream (bais);
		int j = 0;
		try {
			j = dis.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return j;
	}

}
