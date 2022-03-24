package com.morphisec.interview;

import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

	private Logger m_logger = Logger.getLogger(Consumer.class.getName());
	private final Vector<Item> m_items;
	private final Vector<Item> m_processedItems;
	private final Vector<Item> m_screenedItems;

	public Consumer(Vector<Item> items) {
		m_items = items;
		m_processedItems = new Vector<Item>();
		m_screenedItems = new Vector<Item>();
	}

	@Override
	public void run() {
		while (true) {
			try {
				consume();
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				m_logger.log(Level.SEVERE, null, ex);
			}

		}
	}

	private void consume() throws InterruptedException {

		while (m_items.isEmpty()) {
			synchronized (m_items) {
				m_logger.log(Level.INFO,
						"Queue is empty {0} is waiting , size: {1}",
						new Object[] { Thread.currentThread().getName(),
								m_items.size() });
				m_items.wait();
			}
		}

		synchronized (m_items) {
			Item item = m_items.remove(0);
			m_items.notifyAll();
			processItem(item);
		}
	}

	private void processItem(Item item) {
		long uid = item.getUid();
		String tag = item.getTag();
		Date date = item.getDate();

		long hash = generateHash(uid, tag, date);
		if (hash % 3 == 0) {
			m_screenedItems.add(item);
			m_logger.log(Level.INFO, "{0} was screened out", item);
		} else {
			m_processedItems.add(item);
			m_logger.log(Level.INFO, "{0} was processed and logged", item);
		}

		try {
			item.verifyItem();
		} catch (Exception e) {
			m_logger.log(Level.SEVERE, "processing failed", e);
		}
	}

	private long generateHash(long uid, String tag,  Date date) {
		
		if (uid > 0) {
			date.setTime(date.getTime() + uid);
		}

		return date.getTime() + tag.hashCode();
	}
}
