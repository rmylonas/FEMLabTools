package it.fem.labtools.ms.instruments


/**
 * @author mylonasr
 *
 *	Generate randomized samples with all the Pre- and Suffixes
 *	Add QC, STDmix and blanks according to rules defined by {@link RandomizeSetting}
 *
 *	following entities are allowed: QC, STDmix, blank, sample
 *	put number of entity followed by point and entity
 *	seperate entities by - 
 *
 *	example:
 *
 *	startPattern = "3.blank"
 *	repeatPatter = "1.STDmix-1.QC-2.sample"
 *	endPattern = "1.STDmix-1.QC-1.blank"
 *
 */
class RandomizeRunNames{ 
	
	
	List<String> randomizeNames(String prefix, String suffix, List<String> sampleIds, RandomizePattern pattern){
		
		def runList = []
		
		// randomize the sample names
		Collections.shuffle(sampleIds, new Random())
		
		// fill in the start part
		runList = this.createPatternSet(pattern.startPattern)
		
		// fill in the sample part
		while(sampleIds.size > 0){
			runList = runList + this.createPatternSet(pattern.repeatPattern, sampleIds)
		}
		
		// fill in the end part
		runList = runList + this.createPatternSet(pattern.endPattern)
		
		// add prefix, suffix and numeration
		runList = this.addInformation(prefix, suffix, runList)
		
		return runList
	}
	
	
	private List<String> addInformation(String prefix, String suffix, List<String> runList){
		
		for(i in 1..runList.size){
			def name = prefix + sprintf('%03d', i) + "_" + runList[i-1].trim() + "_" + suffix
			runList[i-1] = name 
		}
		
		return runList
		
	}
	
	
	private List<String> createPatternSet(String pattern, List<String> sampleIds){
		List runList = createPatternSet(pattern)
		List returnList = []		
		
		// consume the sampleIds
		for(i in 0..(runList.size-1)){
			if(runList[i] == 'sample'){
				if(sampleIds.size > 0){
					returnList << sampleIds[0]
					sampleIds.remove(0)
				}
			}else{
				returnList << runList[i]
			}
		}
		
		return returnList
	}
	
	
	private List<String> createPatternSet(String pattern){
		def runList = []
		
		for(def pair: this.parsePatternString(pattern)){
			for(i in 1..(pair[0].toInteger())){
				runList.add(pair[1])
			}
		}
		
		return runList
	}
	
	
	private List parsePatternString(String pattern){
		List returnPattern = []
		
		for(String p: pattern.split("-")){ 
			def pair = p.split("\\.")
			returnPattern.add(pair)
		}
		
		return returnPattern
	}
	
}
