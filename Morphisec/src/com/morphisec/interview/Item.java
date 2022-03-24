package com.morphisec.interview;


import java.util.Date;
import java.util.Objects;

public final class Item {

	private final long m_uid;
	private final String m_tag;
	private final Date m_createDate;
	private Item m_snapshot;   
    
    
	public Item(long uid, String tag) {
		this(uid, tag, System.currentTimeMillis(), false);
	}

	private Item(long uid, String tag, long currentTime, boolean isSnapshot) {
		m_uid = uid;
		m_tag = tag;
		m_createDate = new Date(currentTime);
		takeSnapshot(isSnapshot, currentTime);
	}

	public long getUid() {
		return m_uid;
	}

	public String getTag() {
		return m_tag;
	}

	public  Date getDate() {
		return new Date(m_createDate.getTime());
	}

	public void verifyItem() throws Exception {
		if (m_snapshot == null) {
			throw new Exception(
					"Cannot verify item, snapshot is missing");
		}

		if (m_snapshot.equals(this)) {
			return;
		}

		throw new Exception("Item (" + getUid()
				+ ") is not valid, its state has changed");
	}

	private void takeSnapshot(boolean isSnapshot, long currentTime) {
		if (isSnapshot) {
			return;
		}

		m_snapshot = new Item(m_uid, m_tag, currentTime, true);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item)) return false;
		Item item = (Item) o;
		return m_uid == item.m_uid && m_tag.equals(item.m_tag) && m_createDate.equals(item.m_createDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(m_uid, m_tag, m_createDate);
	}

	@Override
	public String toString() {
		return m_tag + "(uid: " + m_uid + ")";
	}
	
}
