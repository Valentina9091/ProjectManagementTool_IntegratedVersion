<%@ include file="./inc/_taglibs.jsp"%>

<struct:htmlWrapper navi="advanceReport">

<jsp:body>

<h2><i>Financial Reports</i></h2>
<ww:form method="post" action="getAdvanceReports">
<div id="defaultreport" style="display: block">
<h4>For latest report please check period</h4>
<input type="radio" name="myRadio" value="weekly" checked="checked"> Weekly<BR>
<input type="radio" name="myRadio" value="monthly"> Monthly<BR>
<input type="radio" name="myRadio" value="quarterly"> Quarterly<BR>
			<BR>
			<BR>
<ww:submit value="Export to Excel" action="getAdvanceReports" />&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" onclick="javascript:yesnoCheck();" name="yesno"
				id="yesCheck" value="Advance Search" />
			<BR>
			<BR>
 </div>
</ww:form>
<ww:form method="post" action="getSearchReports">
			
 <div id="ifYes" style="display: none">
 		<sql:setDataSource var="productname" driver="com.mysql.jdbc.Driver"
					url="jdbc:mysql://localhost/agilefant" user="root" password="root" />
		<sql:query var="select" dataSource="${productname}">select distinct name from products;</sql:query>
		Product name &nbsp; &nbsp; &nbsp;
		<select name="productName">
			<option>select</option>
  			<c:forEach var="result" items="${select.rows}"> 
  				<option>${result.name}</option>
  			</c:forEach>
  		</select>   <BR><BR> 
  		
  		<sql:setDataSource var="projectname" driver="com.mysql.jdbc.Driver"
					url="jdbc:mysql://localhost/agilefant" user="root" password="root" />
		<sql:query var="select" dataSource="${projectname}">select distinct name from projects;</sql:query>
		Project name &nbsp; &nbsp; &nbsp; &nbsp;
		<select name="projectName">
			<option>select</option>
  			<c:forEach var="result" items="${select.rows}"> 
  				<option>${result.name}</option>
  			</c:forEach>
  		</select>   <BR><BR> 
             
        Start Date <BR>
			<aef:datepicker value="${startDate}" id="effStartDate"
					name="startDate" format="yyyy-MM-dd HH:mm" />
			<input class="dateSelectRow" style="display: none;"><BR><BR>
			End Date<BR>
			<aef:datepicker value="${endDate}" id="effEndDate" name="endDate"
					format="yyyy-MM-dd" />
		    <input class="dateSelectRow" style="display: none;">
		    <BR><BR><BR>
		    <input type="button" onclick="javascript:back();" name="yesno"
					id="yesBack" value="Back" />
		    <ww:submit value="Search" action="getSearchReports" />
    </div>
</ww:form>


<script type="text/javascript">
	function yesnoCheck() {
		if (document.getElementById('yesCheck').value = "Advance Search") {
			document.getElementById('ifYes').style.display = 'block';
			document.getElementById('defaultreport').style.display = 'none';
		} else
			document.getElementById('ifYes').style.display = 'none';

	}
</script>

<script type="text/javascript">
	function back() {
		if (document.getElementById('yesBack').value = "Back") {
			document.getElementById('defaultreport').style.display = 'block';
			document.getElementById('ifYes').style.display = 'none';
		} else
			document.getElementById('defaultreport').style.display = 'none';

	}
</script>

</jsp:body>
</struct:htmlWrapper>