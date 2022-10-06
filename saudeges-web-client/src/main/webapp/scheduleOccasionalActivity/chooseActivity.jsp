<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="model"
	class="presentation.web.model.ScheduleOccasionaActivityModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SaudeGes: Definir Especialidade</title>
</head>
<body>
	<form action="definirInstrutor" method="get">
		<input id="specialty" name="specialty" type="hidden" value="${model.specialty}">
		<fieldset>
			<legend>Definir Atividade</legend>
			<div class="mandatory_field">
				<h3>Atividades disponíveis:</h3>
				<ul>
					<c:forEach var="activity" items="${model.occasionalActivities}">
						<input type="radio" name="activity" value="${activity.name}">
						<label for="activity">${activity.name} | nº de sessões: ${activity.numSessions }</label></br>
	 				</c:forEach>
	 			</ul>
			</div>
			
			<label for="numDates">Definir aqui o número de datas (igual ao número de sessões da atividade!):</label>
				<input type="text" name="numDates" value="${model.numDates}">
				
				
			<div class="button">
				<input type="submit" value="Definir">
			</div>
		</fieldset>
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