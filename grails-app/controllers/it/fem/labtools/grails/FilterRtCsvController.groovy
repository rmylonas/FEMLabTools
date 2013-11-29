package it.fem.labtools.grails

import it.fem.labtools.csv.RetentionTimeFilterer
import javax.servlet.http.Cookie

class FilterRtCsvController {

	def filterer = new RetentionTimeFilterer()
	
    def index() { }
	
	def upload() {
		def importFile
		def exportFile
		
		// rtThreshold
		def rtThreshold = params['rtThreshold']
		
		// save a cookie (for 100 days)
		Cookie cookie = new Cookie("rtThreshold", rtThreshold)
		cookie.maxAge = 100 * 24 * 60 * 60
		response.addCookie(cookie)
		
		// upload the file
		def f = request.getFile('csvFile')
		def fileName = this.parseFileName(f.getOriginalFilename())
		
		if (f.empty) {
			flash.error = 'CSV file is empty'
		}else{
			importFile = File.createTempFile("lt_mbimport_",".txt")
			exportFile = File.createTempFile("lt_mbexport_",".csv")
			f.transferTo(importFile)
			
			// and process it
			try{
				rtThreshold = rtThreshold as Double
				filterer.filterRt(importFile, exportFile, rtThreshold)
			}catch(e){
				e.printStackTrace()
				flash.error = 'sorry, your MassBank file could not be parsed'
				redirect(action: 'index')
				return
			}
		}
		
		FileInputStream csvFileStream = new FileInputStream(exportFile)
		
		// hand a csv file to the browser
		response.setHeader "Content-disposition", "attachment; filename=${fileName}.csv"
		response.contentType = 'text/csv'
		response.outputStream << csvFileStream
		response.outputStream.flush()
		
	}
	
	private def parseFileName(String origName){
		def a = origName.split("\\.")
		def until = (a.size() >= 2) ? (a.size()-2) : 0
		return a[0..until].join(".") + "_filtered"
	}
	
}
