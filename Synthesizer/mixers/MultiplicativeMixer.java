package mixers;

import java.util.ArrayList;

import common.Signal;
import common.SynthComponent;
import common.SynthComponentBase;
import common.SynthInput;
import exceptions.IncompatibleSignalException;

public class MultiplicativeMixer extends SynthComponentBase {
	
ArrayList<SynthComponent> sources;
	
	public MultiplicativeMixer(){
		sources = new ArrayList<>();
		setInputs( new SynthInput[] {new Sources()});
	}
	
	@Override
	public Signal getSignal() throws IncompatibleSignalException {
		int nSources = sources.size();
		ArrayList<Signal> signals = new ArrayList<>(nSources);
		signals.add(sources.get(0).getSignal());
		Signal output = new Signal(signals.get(0));
		for(int i = 1; i < nSources; ++i){
			signals.add(sources.get(i).getSignal());
			if(!signals.get(0).compatible(signals.get(i))){
				throw new IncompatibleSignalException("signal " + i + " is incompatible");
			}
			for(int j = 0; j < output.getSampleCount(); ++j){
				output.setSample(j, output.getSample(j) * signals.get(i).getSample(j));
			}
		}
		return output;
		
		
	}

	private class Sources implements SynthInput{
		@Override
		public void connect(SynthComponent source) {
			sources.add(source);
			
		}

		@Override
		public void disconnect(SynthComponent source) {
			sources.remove(source);
		}

		@Override
		public String getName() {
			return "Sources";
		}
	}
}