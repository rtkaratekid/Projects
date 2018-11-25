package common;

import java.io.Serializable;

public class Signal implements Serializable{
	public Signal(double sampleRate, double duration, int bytesPerSample){
		this.sampleRate = sampleRate;
		this.duration = duration;
		this.bytesPerSample = bytesPerSample;
		this.data = new byte[getSampleCount()*bytesPerSample];
	}
	
	public Signal(Signal rhs){
		this.duration = rhs.duration;
		this.sampleRate = rhs.sampleRate;
		this.bytesPerSample = rhs.bytesPerSample;
		this.data = new byte[rhs.data.length];
		for(int i = 0; i < data.length; ++i){
			this.data[i] = rhs.data[i];
		}
	}
	
	public int getSample(int index){
		int sample = 0;
		for(int b = 0; b < bytesPerSample; ++b){
			int entry = data[index*bytesPerSample + b];
			sample |= ((int)(entry) & 0xFF) << b*8;
		}
		//sign extend
		boolean sign = (data[index*bytesPerSample + bytesPerSample -1] >> 7) != 0;
		if(sign){
			for(int b = bytesPerSample; b < 4; ++b){
				sample |= (int)(0xFF) << b*8;
			}
		}
		return sample;
	}
	
	public void setSample(int index, int value){
		for(int b = 0; b < bytesPerSample; ++b){
			data[index*bytesPerSample + b] = (byte)(value >>> 8*b);
		}
	}
	
	public boolean compatible(Signal other){
		return bytesPerSample == other.bytesPerSample && 
				duration == other.duration &&
				sampleRate == other.sampleRate;
	}
	
	
	public byte[] getData(){return data;}
	public int getSampleCount(){return (int)(duration*sampleRate);}
	public int getBytesPerSample(){return bytesPerSample;}
	
	public double getDuration(){ return duration;}
	public double getSampleRate(){return sampleRate;}
	
	private byte[] data;
	private double duration, sampleRate;
private int bytesPerSample;
}
