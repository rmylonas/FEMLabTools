package it.fem.labtools.ms.instruments

enum InstrumentSettings {
	SYNAPT_RP_POS("Synapt RP Pos", "RP_pos", "1.blank-1.STDmix-4.QC", "6.sample-1.QC", "1.STDmix-1.blank"),
	SYNAPT_RP_NEG("Synapt RP Neg", "RP_neg", "1.blank-1.STDmix-4.QC", "6.sample-1.QC", "1.STDmix-1.blank")
	
	private final String description 
	private final String tag 
	private final String startPattern
	private final String repeatPattern
	private final String endPattern
	
	InstrumentSettings(String description, String tag, String startPattern, String repeatPattern, String endPattern) {
		this.description = description
		this.tag = tag
		this.startPattern = startPattern
		this.repeatPattern = repeatPattern
		this.endPattern = endPattern
	}
	
	public String description(){return description}
	public String tag(){return tag}
	public String startPattern(){return startPattern}
	public String repeatPattern(){return repeatPattern}
	public String endPattern(){return endPattern}
	
	String toString(){ description }
	String getKey() { name() }
}
