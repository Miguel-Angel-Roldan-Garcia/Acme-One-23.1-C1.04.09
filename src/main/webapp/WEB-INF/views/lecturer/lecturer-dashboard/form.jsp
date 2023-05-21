

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<h2>${x.maxValue}</h2>
<h2>
	<acme:message code="lecturer.dashboard.form.title.courses-indicators"/>
</h2>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.average-elt-courses"/>
		</th>
		<td>
			<acme:print value="${learningTimeInCourses.averageValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.deviation-elt-courses"/>
		</th>
		<td>
			<acme:print value="${learningTimeInCourses.deviationValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.min-elt-courses"/>
		</th>
		<td>
			<acme:print value="${learningTimeInCourses.minValue}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.max-elt-courses"/>
		</th>
		<td>
			<acme:print value="${learningTimeInCourses.maxValue}"/>
		</td>
	</tr>	
</table>
<h2>
	<acme:message code="lecturer.dashboard.form.title.lectures-indicators"/>
</h2>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.average-elt-lectures"/>
		</th>
		<td>
			<acme:print value="${learningTimeInLectures.averageValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.deviation-elt-lectures"/>
		</th>
		<td>
			<acme:print value="${learningTimeInLectures.deviationValue}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.min-elt-lectures"/>
		</th>
		<td>
			<acme:print value="${learningTimeInLectures.minValue}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.max-elt-lectures"/>
		</th>
		<td>
			<acme:print value="${learningTimeInLectures.maxValue}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="lecturer.dashboard.form.title.lectures-nature"/>
</h2>

<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"THEORETICAL", "HANDS ON"
			],
			datasets : [
				{
					backgroundColor: "rgba(20,255,55,0.5)",
					data : [
						<jstl:out value="${theoreticalLecturesCount}"/>, 
						<jstl:out value="${handsonLecturesCount}"/>, 
					]
				}
			]
		};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>

