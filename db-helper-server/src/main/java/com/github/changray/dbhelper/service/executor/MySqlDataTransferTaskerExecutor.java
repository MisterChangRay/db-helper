package com.github.changray.dbhelper.service.executor;

import com.github.changray.dbhelper.core.DataTransfer;
import com.github.changray.dbhelper.core.TaskRunnable;
import com.github.changray.dbhelper.pojo.mapper.TaskerMapper;
import com.github.changray.dbhelper.pojo.po.Tasker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Service()
public class MySqlDataTransferTaskerExecutor extends TaskRunnable {
    @Autowired
    private TaskerMapper taskerMapper;
    private DataTransfer pageer = new DataTransfer();

    @Override
    public void run() {
        Tasker tasker = this.getTasker();
        boolean res=  check(tasker);

        if(!res) {
            return;
        }
        try {
            copyTableIfNotExists(tasker);
            while (true) {
                copyData(tasker);
                Thread.sleep(0);
            }
        } catch (InterruptedException i) {
            // stop task

        } catch (Exception ae){
            ae.printStackTrace();
        }

    }


    private void copyTableIfNotExists(Tasker tasker) {

    }

    private void copyData(Tasker tasker) {


    }


    private boolean check(Tasker tasker) {
        return false;
    }

}
