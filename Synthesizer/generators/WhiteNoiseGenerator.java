package generators;

import java.util.Random;

import common.Signal;
import common.SynthComponent;

public class WhiteNoiseGenerator extends WaveformGeneratorBase implements SynthComponent {

	private Signal buffer;
	

	public WhiteNoiseGenerator(int bitsPerSample, double sampleRate, double duration) {
		super(bitsPerSample, sampleRate, duration);
	
		int bytesPerSample = bitsPerSample / 8;
		buffer = new Signal(sampleRate, duration, bytesPerSample);
		generate();

	}

	private void generate() {
		
		Random random = new Random();
		

		int max = Integer.MAX_VALUE >> (32 - buffer.getBytesPerSample()*8);

		for (int i = 0; i < buffer.getSampleCount(); ++i) {
			int sample = random.nextInt(max);
			if (random.nextBoolean()) {
				sample = -sample;
			}
			buffer.setSample(i, sample);

		}
	}

	
	@Override
	public Signal getSignal() {
		return buffer;
	}

}