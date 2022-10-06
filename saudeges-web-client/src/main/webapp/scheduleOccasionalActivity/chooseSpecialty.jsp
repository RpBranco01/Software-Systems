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
	<form action="escolherEspecialidade" method="get">
		<fieldset>
			<legend>Procurar Atividades Ocasionais</legend>
			<div class="mandatory_field">
				<label for="specialty">Designa��o da Especialidade:</label> <input type="text"
					name="specialty" value="${model.specialty}" /> <br />
			</div>
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