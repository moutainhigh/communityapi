package net.okdi.logistics;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 定时器执行的先后时间队列，遵循先进先出原则
 * */
public class TimerQueue<T> {
	private Queue<T> storage = new ConcurrentLinkedQueue<T>();

	/** 将指定的元素插入队尾 */
	public void offer(T v) {
		storage.add(v);
	}

	/** 检索，但是不移除此队列的头，如果队列为空，则返回 null */
	public T peek() {
		return storage.peek();
	}
	
	/** 检索并移除此队列的头，如果队列为空，则返回 null */
	public T poll() {
		return storage.poll();
	}

	/** 队列是否为空 */
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	/** 打印队列元素 */
	public String toString() {
		return storage.toString();
	}
	public synchronized int  getSize(){
		return	storage.size();
	};

}
