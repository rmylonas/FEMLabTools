package it.fem.labtools.csv

class RetentionTimeFilterer {
	
	def currentRt = null

	def filterRt(File inputFile, File outputFile, Double rtTol){
		if(!(inputFile.exists() && outputFile.exists())){
			throw new RuntimeException("Missing input or output file")
		}
		
		inputFile.eachLine{	line ->
			if(this.checkRt(line, rtTol)) outputFile.append(line + "\n")
		}
		
	}
	
	private Boolean checkRt(String line, Double rtTol){
		
		def entry = line.split(",")
		
		// let's just print the lines which are not csv
		if(entry.size() < 1) return true
		
		// check if there is a newRt to take
		if(entry[0].isNumber()) currentRt = entry[2] as Double
		
		// if we have a currentRt we check it
		if(currentRt && entry.size() >= 10){
			// rt is at 6th position
			def rt = entry[6]
			
			// print it if its no number
			if(! rt.isNumber()) return true
			
			// check if it's within the range
			rt = rt as Double
			
			if(rt <= (currentRt+rtTol) && rt >= (currentRt-rtTol)) return true
			
			// otherwise we don't print it
			return false
		}
		
		// and all before the first currentRt, we print
		return true		
	}
}
