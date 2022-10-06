<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.BuyMonthlyParticipationModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SaudeGes: comprar participacao mensal</title>
</head>
<body>
<h2>Comprar Participação Mensal:</h2>


<form action="comprarParticipacaoMensal" method="post">

	<input id="activityName" name="activityName" type="hidden" value="${model.activityName}">
	<p>Nome da Atividade: ${model.activityName}</p>
	
	<ul>
		<c:forEach var="schedule" items="${model.schedules}">
			<input type="radio" name="schedule" value="${schedule.id}">
			<label for="schedule">ID: ${schedule.id} | Duração: ${schedule.duration} | Data de início: ${schedule.date}</label>
	 	</c:forEach>
	</ul>
  
    <div class="mandatory_field">
		<label for="date">Data de início:</label> 
		<input type="date" name="date" required pattern="\d{2}/\d{2}/\d{4}" value="${model.date}"/>
		<span class ="validity"></span>
    </div>
   <div class="mandatory_field">
   		<label for="duration">Duração da participação:</label>
   		<input type="text" name="duration" value="${model.duration}"/>
   </div>
   <div class="mandatory_field">
		<label for="email">Email associado:</label> 
		<input type="text" name="email" value="${model.email}"/>
    </div>

   <div class="button">
   		<input type="submit" value="Criar Reserva">
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