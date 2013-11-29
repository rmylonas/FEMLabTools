package it.fem.labtools.csv

import groovy.util.GroovyTestCase

class RetentionTimeFiltererTest extends GroovyTestCase{

	private static final String TEST_ROOT = "test/data/it/fem/labtools/rtfilter/"
	
	public void testFilterRt() {
		
		def filterer = new RetentionTimeFilterer()
		
		File inputFile = new File(TEST_ROOT + "test.csv")
		File outputFile = File.createTempFile("test_rtfilter",".csv")
		Double rtTol = 0.5
		
		filterer.filterRt(inputFile, outputFile, rtTol)
		
		assert outputFile.size() > 0
		assert inputFile.size() > outputFile.size()
		
	}

}
