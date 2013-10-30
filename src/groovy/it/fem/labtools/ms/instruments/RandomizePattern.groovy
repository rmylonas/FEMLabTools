package it.fem.labtools.ms.instruments

/**
 * @author mylonasr
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
class RandomizePattern {

	String startPattern
	String repeatPattern
	String endPattern
	
}
