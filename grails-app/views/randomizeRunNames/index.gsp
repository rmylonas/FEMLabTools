<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Randomize run names</title>    
</head>
<body>

 <div class="container">
    <h3>Randomize run names</h2>
    
    <h5>Enter a list of names (newline separated)</h5>
      
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
	
	<g:uploadForm action="randomize" class="form-horizontal">
		<div class="control-group">
    		<label class="control-label" for="inputSampleNames">Sample names</label>
		    <div class="controls">
		      <g:textArea name="sampleNames" rows="20" cols="100"/>
		    </div>
  		</div>
		<div class="control-group">
    		<label class="control-label" for="prefix">Two letter code</label>
		    <div class="controls">
				<g:textField name="prefix" value="AA"/>
			</div>
  		</div>
  		<div class="control-group">
    		<label class="control-label" for="prefix">Instrument setting</label>
    		<%! import it.fem.labtools.ms.instruments.InstrumentSettings %>
		    <div class="controls">
				<g:select name="instrument" from="${InstrumentSettings.values()}" optionKey="key"/>
			</div>
  		</div>
				
		<div>
			<input class="btn btn-primary" type="submit" value="Randomize">
		</div>
    </g:uploadForm>

</div> <!-- /container -->

</body>
</html>