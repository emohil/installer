package com.company.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自带超时功能的Map,当存入的key超过指定的时间后，会自动从当前Map中移除.
 * 
 * @author lihome
 *
 * @param <K>
 * @param <V>
 * 
 * @see java.util.HashMap
 */
public class ExpireMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 1L;

    private static final long INTERVAL = 60 * 1000;

    /** 默认的超时时间(秒) */
    private static final long DEFAULT_TIMEOUT = 7200;

    private long timeout = DEFAULT_TIMEOUT * 1000;

    private Map<K, Long> keyTime = New.hashMap();

    /** 使用默认的超时时间 */
    public ExpireMap() {
        this(DEFAULT_TIMEOUT);
    }

    /**
     * 使用自定义的超时时间
     * @param timeout
     *            in seconds before keys timeout
     */
    public ExpireMap(long timeout) {
        this.timeout = timeout * 1000;

        Timer timer = new Timer();
        timer.schedule(new CleanTimerTask(timer), INTERVAL);
    }

    @Override
    public V put(K key, V value) {
        keyTime.put(key, System.currentTimeMillis());
        return super.put(key, value);
    }

    @Override
    public V remove(Object key) {
        keyTime.remove(key);
        return super.remove(key);
    }

    private void cleanup() {

        List<K> timeoutKeys = New.list();

        for (java.util.Map.Entry<K, Long> e : keyTime.entrySet()) {
            if (System.currentTimeMillis() - e.getValue() >= timeout) {
                timeoutKeys.add(e.getKey());
            }
        }
        for (K k : timeoutKeys) {
            this.remove(k);
        }
    }

    public class CleanTimerTask extends TimerTask {
        // Reference to containing Timer.
        private final Timer _timer;

        // Constructor takes reference to containing
        // Timer so it can reset itself after execution.
        CleanTimerTask(Timer _timer) {
            this._timer = _timer;
        }

        // Clean cache.
        public void run() {

            cleanup();

            _timer.schedule(new CleanTimerTask(_timer), INTERVAL);
        }
    }
}