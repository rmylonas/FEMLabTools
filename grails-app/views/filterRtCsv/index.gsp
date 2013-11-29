<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Filter CSV file on retention times</title>    
</head>
<body>

 <div class="container">
    <h3>Retention time filter</h3>
    
    <h5>Upload your CSV file</h5>
      
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
	    	<label class="control-label" for="csvInput">CSV file</label>
	    	<div class="controls">
				<input name="csvFile" type="file" style="display:none">
				<div class="input-append">
					<input id="csvInput" class="input-large" type="text">
					<a class="btn" onclick="$('input[name=csvFile]').click();">Browse</a>
				</div>
			</div>
		</div>
			
		<div class="control-group">
	    	<label class="control-label" for="rtThreshold">Retention time threshold</label>
			<div class="controls">
				<g:textField name="rtThreshold" value="${(cookie(name:'rtThreshold')?cookie(name:'rtThreshold'):0.1)}" size="8"/>
			</div>
		</div>
		
		<div class="control-group">
			<div class="controls">
				<input class="btn btn-primary" type="submit" value="Upload">
			</div>
		</div>
		
    </g:uploadForm>

</div> <!-- /container -->


    <script type="text/javascript">
		$('input[name=csvFile]').change(function() {
		$('#csvInput').val($(this).val());
		});
	</script>

</body>
</html>