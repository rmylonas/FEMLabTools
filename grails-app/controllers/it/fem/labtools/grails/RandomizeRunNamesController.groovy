package it.fem.labtools.grails

import it.fem.labtools.ms.instruments.RandomizePattern
import it.fem.labtools.ms.instruments.RandomizeRunNames

class RandomizeRunNamesController {

    def index() { }
	
	def randomize() {
		def sampleNames = params["sampleNames"]
		def prefix = params["prefix"]
		def suffix = params["suffix"]
		
		List<String> sampleList = sampleNames.split("\\n")
		
		// test pattern
		def pattern = new RandomizePattern()
		pattern.startPattern = '3.blank'
		pattern.repeatPattern = '1.STDmix-1.QC-2.sample'
		pattern.endPattern = '1.STDmix-1.QC-1.blank'
		
		// randomize
		def randomize = new RandomizeRunNames()
		def runNames = randomize.randomizeNames(prefix, suffix, sampleList, pattern)
		
		// and pass the list to exportCSV
		redirect(action: "exportCSV", params:[runNames: runNames.join("\n")])
		
	}
	
	def exportCSV(){
		// hand a csv file to the browser
		response.setHeader "Content-disposition", "attachment; filename=randomizedNames.csv"
		response.contentType = 'text/csv'
		response.outputStream << params.runNames
		response.outputStream.flush()
	}
	
}
