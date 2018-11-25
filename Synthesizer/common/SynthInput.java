package common;

public interface SynthInput {
	
	String getName();
	void connect(SynthComponent input);
	void disconnect(SynthComponent input);
	
}
