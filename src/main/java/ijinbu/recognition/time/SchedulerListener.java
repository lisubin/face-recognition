package ijinbu.recognition.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    public TaskManager taskManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

}
