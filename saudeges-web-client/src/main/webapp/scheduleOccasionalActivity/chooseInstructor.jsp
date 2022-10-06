<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.ScheduleOccasionaActivityModel" scope="request"/>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SaudeGes: Escolher instrutor</title>
</head>
<body>

	<h3>Datas das Sessões:</h3>
	<ul>
		<c:forEach var="date" items="${model.dates}">
			<li>${date}</li>
		</c:forEach>
	</ul>

	<form action="agendarSessoes">
		<input id="specialty" name="specialty" type="hidden" value="${model.specialty}">
		<input id="activity" name="activity" type="hidden" value="${model.activity}">
		<input id="numDates" name="numDates" type="hidden" value="${model.numDates}">
		
		<h3>Instrutores disponíveis:</h3>
		<ul>
			<c:forEach var="instructor" items="${model.instructors}">
				<input type="radio" name="instructor" value="${instructor.id}">
				<label for="instructor">ID: ${instructor.id} | Nome: ${instructor.name}</label></br>
		 	</c:forEach>
		</ul>
	
		<label for="email">Email:</label>
		<input type="text" name="email" value="${model.email}">
	
		<div class="button">
   			<input type="submit" value="Escolher Instrutor">
	   </div>
	</form>
	
	<form action="voltarIndex">
		<div class="button">
   			<input type="submit" value="Voltar ao Menu Principal">
	   </div>
	</form>
	
	<c:if test="${model.hasMessages}">
		<p>Mensagens</p>
		<ul>
			<c:forEach var="mensagem" items="${model.messages}">
				<li>${mensagem} 
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>