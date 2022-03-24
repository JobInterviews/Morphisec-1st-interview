package com.morphisec.interview;

import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {
	private Logger m_logger = Logger.getLogger(Producer.class.getName());
	private final Vector<Item> m_items;
	private final int m_size;

	public Producer(Vector<Item> items, int size) {
		this.m_items = items;
		this.m_size = size;
	}

	@Override
	public void run() {
		for (int index = 0; index < 100; index++) {
			try {
				produce(index);
			} catch (InterruptedException ex) {
				m_logger.log(Level.SEVERE, null, ex);
			}

		}
	}

	private void produce(int index) throws InterruptedException {

		while (m_items.size() == m_size) {
			synchronized (m_items) {
				m_logger.log(Level.INFO,
						"Queue is full {0} is waiting , size: {1}",
						new Object[] { Thread.currentThread().getName(),
								m_items.size() });

				m_items.wait();
			}
		}

		synchronized (m_items) {
			Item item = getNextItem(index);
			m_items.add(item);
			m_logger.log(Level.INFO, "{0} was produced", item);
			m_items.notifyAll();
		}
	}

	private Item getNextItem(int index) {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new Item(new Random().nextLong(), "item #" + index);
	}
}
