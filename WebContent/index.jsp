<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.time.ZoneOffset"%>
<%@page import="java.time.Duration"%>
<%@page import="java.time.Instant"%>
<%@page import="java.time.Period"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.numbers.pojo.Intento"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Guess the number</title>
	<link rel="stylesheet" href="css/index.css"/>
</head>
<body>
<div id="scoreboard">
	<h1>Tabla de puntuaciones</h1>
	<% try {ArrayList<ArrayList<Object>> partidas = (ArrayList<ArrayList<Object>>) session.getAttribute("partidas");
	for(int i = 0; i < partidas.size(); i++){
		ArrayList<Object> partida = partidas.get(i);
		out.print("<p>" + partida.get(0) + " - " + partida.get(1) + " - " + partida.get(2) + " - " + partida.get(3) + "</p>");
	}} catch(NullPointerException e){
		out.print("No hay resultados disponibles");
	}%>
</div>
<div>
	<h1>Adivina el número</h1>
	<% 
	if(session.getAttribute("min-value") == null) {
	out.print("<form action=\"adivinar\" method=\"post\">" +
		"<h3>Introduce los números entre los que deseas adivinar</h3>" +
		"<input type=\"number\" maxlength=\"3\" name=\"min-value\"/>" +
		"<input type=\"number\" maxlength=\"3\" name=\"max-value\"/>" +
		"<input type=\"submit\" value=\"Empezar\"/>" +
	"</form>");
	} else {
		Intento intento = (Intento) session.getAttribute("intento");
		if(request.getAttribute("won") != null) {
			out.print("<h2>" + intento.getMensaje() + "</h2>");
			out.print("<form action=\"rejugar\" method=\"get\">" +
			"<input type=\"text\" name=\"nombre\" placeholder=\"Introduce tu nombre\"/>" +
			"<input type=\"submit\" value=\"Jugar de nuevo\"/>" +
			"</form>");
		} else {
			out.print("<h2>Introduce un número entre el " + session.getAttribute("min-value") +
					" y el " + session.getAttribute("max-value") + "</h2>" +
					"<form autocomplete=\"off\" action=\"adivinar\" method=\"post\">" +
					"<input type=\"text\" name=\"number\" placeholder=\"Introduce un número entre el "
					+ session.getAttribute("min-value") + " y el " + session.getAttribute("max-value") + "\"/>" +
					"<input type=\"submit\" value=\"Jugar\"/>" +
				"</form>" + 
				"<form action=\"rejugar\" method=\"get\"><input type=\"submit\" value=\"Reiniciar\"/></form>");
			out.print("<p>" + intento.getMensaje() + "</p>");
		}
		if(intento.getIntentos().size() > 0) {
			out.print("<p>Intentos realizados</p>");
			out.print("<ul>");
			for(int i = 0; i < intento.getIntentos().size(); i++){
				out.print("<li>Intento " + (i+1) + " : " + intento.getIntentos().get(i) + "</li>");
			}
			out.print("</ul>");
		}
	    if(request.getAttribute("won") != null) {
	    	out.print("Tiempo total de juego: " + intento.getTotalTime(intento.getTiempo()));
	    }
	}
	%>
	</div>
	<div id="footer">
		<ul>
			<li>Lionel Hernández Delgado - </li>
			<li>DSW</li>
			<li> - 2018/2019</li>
		</ul>
	</div>
</body>
</html>