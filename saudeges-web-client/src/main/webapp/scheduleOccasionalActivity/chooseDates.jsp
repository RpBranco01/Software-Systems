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
<title>SaudeGes: Escolher Datas</title>
</head>
<body>
	<form action="definirDatas">
	<input id="specialty" name="specialty" type="hidden" value="${model.specialty}">
	<input id="activity" name="activity" type="hidden" value="${model.activity}">
	<input id="numDates" name="numDates" type="hidden" value="${model.numDates}">
		<div class="dates">
			<c:forEach var="i" begin="1" end="${model.numDates}">
				<label for="date">Data da sessão ${i}:</label> 
				<input type="date" name="date${i}" required pattern="\d{2}/\d{2}/\d{4} \d{2}:\d{2}" value="${model.dates[i]}"/>
				<span class ="validity"></span> </br>
			</c:forEach>
		</div>
		
		<div class="button">
   			<input type="submit" value="Definir Datas">
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