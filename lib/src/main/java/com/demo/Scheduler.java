package com.demo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scheduler {
	final static Logger logger = LoggerFactory.getLogger(Scheduler.class);
	private int counter = 0;
	
	public void schedule() {
		Timer timer = new Timer();
		ExecutorService executor = Executors.newSingleThreadExecutor();

		TimerTask task = new TimerTask() {
			public void run() {
//				if (counter++ > 5) {
//					this.cancel();
//					logger.info("done.");
//					return;
//				}
				executor.submit(new LongRunningTask());
			}
		};
		//timer.schedule(task, 0, 60 * 60 * 1000);
		timer.schedule(task, 0, 15 * 60 * 1000);
	}

}
