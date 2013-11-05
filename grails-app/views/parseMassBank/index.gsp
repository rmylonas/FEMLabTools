<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>MassBank parser</title>    
</head>
<body>

 <div class="container">
    <h3>MassBank parser</h2>
    
    <h5>Upload a MassBank result file (in .txt format)</h5>
      
    <!-- Show errors -->
    <g:if test="${flash.error}">
  		<div class="alert alert-block alert-error">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>Error: </strong>${flash.error}
  		</div>
	</g:if>
	
	<!-- Show messages -->
	<g:if test="${flash.message}">
  		<div class="alert alert-block alert-success">
  			<button type="button" class="close" data-dismiss="alert">&times;</button>
  			<strong>OK: </strong>${flash.message}
  		</div>
	</g:if>
	
	<g:uploadForm action="upload" class="form-horizontal">
	
		<div class="control-group">
	    	<label class="control-label" for="massBankInput">MassBank file</label>
	    	<div class="controls">
				<input name="massBankFile" type="file" style="display:none">
				<div class="input-append">
					<input id="massBankInput" class="input-large" type="text">
					<a class="btn" onclick="$('input[name=massBankFile]').click();">Browse</a>
				</div>
			</div>
		</div>
			
		<div class="control-group">
	    	<label class="control-label" for="scoreThreshold">Score threshold</label>
			<div class="controls">
				<g:textField name="scoreThreshold" value="${(cookie(name:'scoreThreshold')?cookie(name:'scoreThreshold'):0.1)}" size="8"/>
			</div>
		</div>
		
		<div class="control-group">
			<div class="controls">
				<input class="btn btn-primary" type="submit" value="Upload">
			</div>
		</div>
		
    </g:uploadForm>
    
    <g:if test="${flash.parsedFiles}">
  		<h5>Processed files:</h5>
	</g:if>
    
    <ul>
	    <g:each in="${flash.parsedFiles}">
	    	<li><a href="download?filepath=${it.filepath}&filename=${it.name}">${it.name}</a></li>
		</g:each>
	</ul>

</div> <!-- /container -->


    <script type="text/javascript">
		$('input[name=massBankFile]').change(function() {
		$('#massBankInput').val($(this).val());
		});
	</script>

</body>
</html>