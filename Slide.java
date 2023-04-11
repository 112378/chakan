package picture;

import java.util.Timer;
import java.util.TimerTask;


public class Slide {
	private int second;
	FullFrame frame;
	private final Timer timer = new Timer();

	public Slide(FullFrame fullFrame) {
		this.frame = fullFrame;
		second = 2;
	}

	public void start() {
		timer.schedule(new TimerTask() {
			public void run() {
				frame.Forward();
				frame.getJButton1().setEnabled(false);
			}
		}, second * 1000, second * 1000);
	}

	public void stop() {
		timer.cancel();
	}
}
