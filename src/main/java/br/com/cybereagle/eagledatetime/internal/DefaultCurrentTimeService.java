package br.com.cybereagle.eagledatetime.internal;

import br.com.cybereagle.eagledatetime.CurrentTimeService;

public class DefaultCurrentTimeService implements CurrentTimeService {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
