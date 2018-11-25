package common;

import java.io.Serializable;
import exceptions.IncompatibleSignalException;

public interface SynthComponent extends Serializable{
	Signal getSignal() throws IncompatibleSignalException;

	SynthInput[] getInputs();
}
