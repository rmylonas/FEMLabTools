package it.fem.labtools.parser;

import java.io.File;

public class MassBankParserImpl implements MassBankParser{

//	def static headerList = ['Query', 'Scan No.', 'Retention time [min]', 'Nr of hits', 'Accession', 'Title', 'Formula', 'Mass', 'Score', 'Hit']
	def static headerList = ['Mass', 'Retention time [min]', 'Accession', 'Title', 'Formula', 'Score']
	def currentEntry = [:]
	boolean inQuery = false
	def nrLines = 0
	def scoreThreshold = 0.0
	
	
	@Override
	public void parse(File inputFile, File outputFile) {
		
		if(!(inputFile.exists() && outputFile.exists())){
			throw new RuntimeException("Missing input or output file")
		}
		
		// write the header to the csv-file
		outputFile.write(headerList.join(",") + "\n")
		
		inputFile.eachLine{	line -> 
			boolean check = parseLine(line.trim(), outputFile)
			if(!check) throw new RuntimeException("Could not parse MassBank result file [" + inputFile.toString() + "]")
		}
		
		// if no results were parsed we throw an excpetion
		if(nrLines == 0) throw new RuntimeException("No results parsed from MassBank result file")
	}
	
	private boolean parseLine(String line, File outputFile){
		
		// look for ### Query
		if(!inQuery){
			if(line.contains("### Query")){
				def matcher = line =~ /.*Query\s+([\d|,]+).*/
				if(matcher.getCount() != 1) return false
				currentEntry.queryNr = matcher[0][1]
				inQuery = true
				return true
			}else{	
				// skip this line if we're not already in a Query block
				return true
			}
		}
		
		// look for name
		if(line ==~ /^#\s+Name:.*/){
			def matcher = line =~ /.*Scan No\.\s+(\d+) at ([\.|\d]+).*/
			if(matcher.getCount() != 1) return false
			currentEntry.scanNr = matcher[0][1]
			currentEntry.rT = matcher[0][2]
			return true
		}
		
		// and for Hit
		if(line ==~ /^# Hit.*/){
			def matcher = line =~ /.*Hit\:\s+(\d+).*/
			if(matcher.getCount() != 1) return false
			currentEntry.nrOfHits = matcher[0][1]
			if(currentEntry.nrOfHits == '0') resetVariables()
			return true
		}
		
		// and for headers
		if(line ==~ /^Accession.*/){
			currentEntry.headers = line.split("\\s+")
			currentEntry.hits = []
			return true
		}
		
		// and we parse the entries
		if(currentEntry.headers && line){
			// println line
			currentEntry.hits << line
			return true
		}
		
		// if we have entries and an empty line, we're done
		if(currentEntry?.hits && !line){
			writeEntry(outputFile)
			return true
		}
		
		return true
		
	}
	
	private void resetVariables(){
		inQuery = false
		currentEntry = [:]
	}
	
	private void writeEntry(File outputFile){
		currentEntry.hits.each { oneHit ->
			def infoArray = oneHit.split("\\t")
			
			def score = infoArray[4].replace(",", ".")
			def mass = infoArray[3].replace(",", "")
			def accession = infoArray[0].replaceAll(";|:|,", "")
			def title = infoArray[1].replaceAll(";|:|,", "")
			def formula = infoArray[2].replaceAll(";|:|,", "")
			
			def infoString = mass + "," +
							 currentEntry.rT + "," +
							 accession + "," +
							 title + "," +
							 formula + "," +
							 score
							 
			// append if score bigger than threshold
			if(score.toDouble() >= scoreThreshold) outputFile.append infoString + "\n"				
			nrLines++
		}
		resetVariables();
	}
	
}
