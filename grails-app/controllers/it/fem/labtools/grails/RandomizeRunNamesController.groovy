package it.fem.labtools.grails

import it.fem.labtools.ms.instruments.InstrumentSettings;
import it.fem.labtools.ms.instruments.RandomizePattern
import it.fem.labtools.ms.instruments.RandomizeRunNames

class RandomizeRunNamesController {

    def index() { 
	}
	
	def randomize() {
		def sampleNames = params["sampleNames"]
		def prefix = params["prefix"]
		def instrument = params["instrument"]
		
		def instrumentSetting = instrument as InstrumentSettings		
		List<String> sampleList = sampleNames.split("\\n")
		
		// randomize
		def randomize = new RandomizeRunNames()
		def runNames = randomize.randomizeNames(prefix, instrumentSetting, sampleList)
		
		// and pass the list to exportCSV
		//redirect(action: "exportCSV", params:[runNames: runNames.join("\n")])
		response.setHeader "Content-disposition", "attachment; filename=randomizedNames.csv"
		response.contentType = 'text/csv'
		response.outputStream << runNames.join("\n")
		response.outputStream.flush()
		
		
	}
	
//	def exportCSV(){
//		// hand a csv file to the browser
//		response.setHeader "Content-disposition", "attachment; filename=randomizedNames.csv"
//		response.contentType = 'text/csv'
//		response.outputStream << params.runNames
//		response.outputStream.flush()
//	}
	
}
