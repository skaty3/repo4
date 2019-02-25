package cn.activiti;

import cn.activiti.cn.activiti.bean.Holiday;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Administrator
 * 2019/2/20
 */
public class Test3 {

    @Test
    public void test1() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deploy = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("diagrams/holiday2.bpmn")
                .name("qingjiatiao")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getKey());
        System.out.println(deploy.getName());
    }

    @Test
    public void test2() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myHoliday2").singleResult();
        System.out.println(processDefinition.getId());
        System.out.println(processDefinition.getKey());
        System.out.println(processDefinition.getName());

    }

    @Test
    public void test3() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        Holiday holiday = new Holiday();
        holiday.setXiaozong("xiaoZ");
        holiday.setNum(2);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("holiday",holiday);
        ProcessInstance myHoliday2 = processEngine.getRuntimeService().startProcessInstanceByKey("myHoliday2",map);
        System.out.println(myHoliday2.getProcessInstanceId());
        System.out.println(myHoliday2.getProcessDefinitionId());
        System.out.println(myHoliday2.getProcessDefinitionKey());
        System.out.println(myHoliday2.getProcessDefinitionName());
    }

    @Test
    public void test4() {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        List<Task> xiao1 = taskService.createTaskQuery().taskCandidateOrAssigned("xiao1").list();
        System.out.println(xiao1.size());
        for(Task task:xiao1){
            System.out.println(task.getId());
            System.out.println(task.getAssignee());
            System.out.println(task.getName());
            taskService.claim(task.getId(),"xiao1");
        }

    }

    @Test
    public void test5() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("10007");

    }




}
