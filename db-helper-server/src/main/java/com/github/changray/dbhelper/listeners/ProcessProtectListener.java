package com.github.changray.dbhelper.listeners;

import com.github.changray.dbhelper.events.ConfigChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


/**
 * 进程保护服务
 *
 * 这里主要是使用远程SSH 方式进行进程保护
 */
@Service
public class ProcessProtectListener {
    static Logger logger = LoggerFactory.getLogger(ProcessProtectListener.class.getName());



    @EventListener(value = {ConfigChangedEvent.class})
    public void configChanges() {
    }

}
