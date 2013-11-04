package it.fem.labtools.ms.instruments

import groovy.util.GroovyTestCase

class RandomizeRunNamesTest extends GroovyTestCase{

	
	void testRandomizeSynaptRF() {
		List sampleIds = ['sample01', 'sample02', 'sample03', 'sample04', 'sample05', 'sample06', 'sample07']
		String prefix = 'AA'
		String suffix = 'RP_pos'
		
		// pattern setting for Synapt
		def pattern = new RandomizePattern()
		pattern.startPattern = '3.blank'
		pattern.repeatPattern = '1.STDmix-1.QC-2.sample'
		pattern.endPattern = '1.STDmix-1.QC-1.blank'
		
		// randomize and add info
		def randomize = new RandomizeRunNames()
		def runNames = randomize.randomizeNames(prefix, suffix, sampleIds, pattern)
		
		assertEquals(21, runNames.size)
		assertEquals("AA003_blank_RP_pos", runNames[2])
		assertEquals("AA008_STDmix_RP_pos", runNames[7])
		assertEquals("AA020_QC_RP_pos", runNames[19])
		assertTrue(runNames[17].contains("AA018_sample"))
		
	}
	
	
	void testSynaptPosSetting(){
		List sampleIds = ['sample01', 'sample02', 'sample03', 'sample04', 'sample05', 'sample06', 'sample07']
		String prefix = 'AB'
		
		// randomize
		def randomize = new RandomizeRunNames()		
		def runNames = randomize.randomizeNames(prefix, InstrumentSettings.SYNAPT_RP_POS, sampleIds)

		assertEquals(17, runNames.size())
		assertTrue(runNames[8].contains("AB009_sample"))
		
	}
	
	
}
