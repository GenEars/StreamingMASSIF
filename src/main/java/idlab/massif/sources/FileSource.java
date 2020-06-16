package idlab.massif.sources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import idlab.massif.core.PipeLine;
import idlab.massif.interfaces.core.ListenerInf;
import idlab.massif.interfaces.core.SourceInf;

/***
 * Reads a provided file line by line and streams each line to the pipeline.
 * 
 * @author psbonte
 *
 */
public class FileSource implements SourceInf {

	private String fileName;
	private PipeLine pipeline;
	private long timeout;
	private ListenerInf listener;

	public FileSource(String fileName, long timeout) {
		this.fileName = fileName;
		this.timeout = timeout;
	}

	public void registerPipeline(PipeLine pipeline) {
		this.pipeline = pipeline;
	}

	public void stream() {

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			while ((line = br.readLine()) != null) {
				if (listener != null) {
					listener.notify(0, line);
				}
				if (pipeline != null) {
					pipeline.addEvent(line);
				}
				try {
					Thread.sleep(timeout);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean addEvent(String event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addListener(ListenerInf listener) {
		this.listener = listener;
		return true;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
