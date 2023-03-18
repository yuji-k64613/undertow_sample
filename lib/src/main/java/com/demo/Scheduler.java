package com.demo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scheduler {
	final static Logger logger = LoggerFactory.getLogger(Scheduler.class);
	private int period = 0;
	
	public Scheduler() {
		period = Integer.parseInt(System.getenv("PERIOD")) * 60 * 1000;
	}
	
	public void schedule() {
		Timer timer = new Timer();
		ExecutorService executor = Executors.newSingleThreadExecutor();

		TimerTask task = new TimerTask() {
			public void run() {
				executor.submit(new LongRunningTask());
			}
		};
		timer.schedule(task, period / 2, period);
	}

}
