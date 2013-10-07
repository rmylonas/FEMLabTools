package it.fem.labtools.grails

import it.fem.labtools.parser.MassBankParserImpl

class ParseMassBankController {
	static scope = "session"
	
	def mbParser = new MassBankParserImpl()
	def parsedFiles = []
	
	def index(){
		flash.parsedFiles = parsedFiles
	}
	
	def upload() {
		def importFile
		def exportFile
		
		// upload the file
		def f = request.getFile('massBankFile')
		
		if (f.empty) {
			flash.error = 'MassBank result file is empty'
		}else{
			importFile = File.createTempFile("lt_mbimport_",".txt")
			exportFile = File.createTempFile("lt_mbexport_",".csv")
			f.transferTo(importFile)
			
			// and process it
			try{
				mbParser.parse(importFile, exportFile)
				flash.message = 'file was succesfully processed'
				
				parsedFiles.add(0, ['filepath': exportFile.toString(), 'name': f.getOriginalFilename()])
				
			}catch(e){
				e.printStackTrace()
				flash.error = 'sorry, your MassBank file could not be parsed'
			}
		}
		
		flash.parsedFiles = parsedFiles
		redirect(action: 'index')
	}
	
	
	def download(){
		
		FileInputStream csvFileStream = new FileInputStream(params.filepath)
		
		// hand a csv file to the browser
		response.setHeader "Content-disposition", "attachment; filename=${params.filename}.csv"
		response.contentType = 'text/csv'
		response.outputStream << csvFileStream
		response.outputStream.flush()	
	}
	
}
