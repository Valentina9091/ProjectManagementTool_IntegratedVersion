 <%@ include file="../inc/_taglibs.jsp"%>
 
 
 <%@ taglib prefix="s" uri="/struts-tags" %>
 
 
 
 <struct:htmlWrapper navi="settings">

<jsp:body>


<script type="text/javascript">
$(document).ready(function() {
  var controller = new AccessListController({
    element: $('#accessListElement'),
    iterationElement: $('#accessIterationListElement')
  });
});
</script>


<c:choose>
<c:when test="${currentUser.admin}">
  <div id="accessListElement" style="min-width: 750px"> </div>
  <div id="accessIterationListElement" style="min-width: 750px"> </div>
</c:when>
<c:otherwise>
  <h3></h3>
</c:otherwise>
</c:choose>
 
 
 <head>
 
 
 <div class="dynamictable ui-widget-content ui-corner-all">
 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Upload</title>




<title>Agilefant</title>
  <aef:css path="main" minify="true" />
  <!--[if IE 7]><aef:css path="IE7styles.css" /><![endif]-->
  <!--[if IE 8]><aef:css path="IE8styles.css" /><![endif]-->
  
  <link rel="shortcut icon" href="static/img/favicon.png" type="image/png" />

  <aef:javascript path="jquery" minify="true" />

  <style type="text/css">  
    #outerWrapper {
      background: url('static/img/login_gradient.png') repeat-x;
      height: 300px;
    }
  
    #loginWrapper {
      width: 28em;
      height: 15em;
      margin: -8em 0 0 -12em;
      position: absolute;
      top: 35%;
      left: 50%;
      border-width: 3px;
      -moz-border-radius: 10px;
      -webkit-border-radius: 10px;
      border-radius: 10px;
      z-index: 100;
      overflow: visible;      
    }
    
    .loginWrapperWithError {
      height: 16em !important;
    }
    
    #login {
      font-family: Verdana, Arial, Helvetica, sans-serif;
      margin: 1em auto;
      width: 25em;
    }
    
    #loginWrapper tr {
      line-height: 2em;
    }
    
    #disclaimer {
      position: relative;
      top: 10em;
      left: 50%;
      z-index: 101;
      
      width: 22em;
      /*height: 8em;*/
      margin: -4em 0 0 -12em;
      padding: 0 1em;
      
      border-width: 3px;
      -moz-border-radius: 10px;
      -webkit-border-radius: 10px;
      border-radius: 10px;
      
      font-size: 80%;
      color: #f00;
    }
    
    #disclaimer a {
      color: rgb(30, 94, 238) !important;
    }
    
    #disclaimer a:hover {
      color:  #e50 !important;
    }
    
    #agilefantText {
      margin: 0;
      position: absolute;
      bottom: 0;
      z-index: 50;
      font-size: 72pt;
      font-style: italic;
      font-weight: bold;
    }
    
    #agilefantText img {
      display: block;
      float: left;
    }
    
    #agilefantText span {
      display: block;
      margin: 0.5em 0 0 150px;
    }
    
    #footerWrapper {
      position: fixed;
      bottom: 0;
      width: 100%;
      padding: 0 0 1em 0;
      background-color: white;
      z-index: 200;
    }
    
    #footerText {
      margin: 1em 0 0 1em;
      white-space: nowrap;
    }

  </style>













</head>

<script>
            var sFileName ="";
            var _validFileExtensions = [".xlsx"];    
function Validate(oForm) {
	
	if(!document.getElementById("myfile").value ) {
		event.preventDefault();
		alert("Please, Choose a File");
        return false;
		}
	
	
    var arrInputs = oForm.getElementsByTagName("input");
    for (var i = 0; i < arrInputs.length; i++) {
        var oInput = arrInputs[i];
        if (oInput.type == "file") {
            sFileName = oInput.value;
            if (sFileName.length > 0) {
                var blnValid = false;
                for (var j = 0; j < _validFileExtensions.length; j++) {
                    var sCurExtension = _validFileExtensions[j];
                    if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                        blnValid = true;
                        break;
                    }
                }
                
                if (!blnValid) {
                    alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
                    return false;
                }
            }
        }
    }
  
    return true;
}
    
   </script> 




<body>






<h1>Select Your File</h1>
<s:form  action="userFile" method="post" enctype="multipart/form-data" onsubmit="return Validate(this);">

<h2> Select File Type :
  <s:radio id="radiobutton" name="type" title="User"  value="User" list="#{'1' : 'Agilefant Revisions'}" checked="true"/>
<s:radio id="radiobutton" name="type" title="Project" value="Project" list="#{ '2' : 'Project'}"/>

</h2>
<s:file name="fileUpload" id ="myfile" key="fullpath"></s:file>
<s:submit value="upload"></s:submit>
</s:form>
</body>
</html>




</jsp:body>
</struct:htmlWrapper>