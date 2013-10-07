package it.fem.labtools.parser

import groovy.util.GroovyTestCase

class MassBankParserImplTest extends GroovyTestCase{
	
	private static final String TEST_ROOT = "test/data/it/fem/labtools/parser/massbank/"
	
	
	void testFileParserSmall() {
		File massBankResultFile = new File( TEST_ROOT + "small.txt" )
		assertTrue(massBankResultFile.exists())
		
		def mbParser = new MassBankParserImpl()
		def csvFile = File.createTempFile("test_small_massbank_parser_out",".csv")
		mbParser.parse(massBankResultFile, csvFile)
		
		assertTrue(csvFile.size() > 0)
	}
	
	
	void testFileParserDiet() {
		File massBankResultFile = new File( TEST_ROOT + "test_MS3_diet_results.txt" )
		assertTrue(massBankResultFile.exists())
		
		def mbParser = new MassBankParserImpl()
		def csvFile = File.createTempFile("test_diet_massbank_parser_out",".csv")
		mbParser.parse(massBankResultFile, csvFile)
		
		assertTrue(csvFile.size() > 0)
	}
	
	
	void testFileParserInvalid() {
		File massBankResultFile = new File( TEST_ROOT + "invalid.txt" )
		assertTrue(massBankResultFile.exists())
		
		def mbParser = new MassBankParserImpl()
		def csvFile = File.createTempFile("test_invalid_massbank_parser_out",".csv")
		
		def hasFailed = false
		try{
			mbParser.parse(massBankResultFile, csvFile)
		}catch(e){
			hasFailed = true
		}
		
		assertTrue(hasFailed)
					
	}

	
}
