package cn.activiti;

import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * Administrator
 * 2019/2/19
 */
@SuppressWarnings("all")
public class Test2 {

    @Test
    public void test() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myHoliday");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("myHoliday:2:2503","110110110");
        //runtimeService.startProcessInstanceByKey("myHoliday");
        //4.输出实例的相关信息
        System.out.println("流程部署ID"+processInstance.getDeploymentId());//null
        System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());//myHoliday:3:5004
        System.out.println("流程实例ID"+processInstance.getId());//10001
        System.out.println("活动ID"+processInstance.getActivityId());//null
    }

    @Test
    public void test1() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> myHoliday = runtimeService.createProcessInstanceQuery().processDefinitionKey("myHoliday").list();
        System.out.println("list: "+myHoliday.size());
        for (ProcessInstance processInstance : myHoliday){
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + processInstance.getProcessInstanceId());
            System.out.println("所属流程定义id：" + processInstance.getProcessDefinitionId());
            System.out.println("是否执行完成：" + processInstance.isEnded());
            System.out.println("是否暂停：" + processInstance.isSuspended());
            System.out.println(" 当 前 活 动 标 识 ： " + processInstance.getActivityId());
            System.out.println(" 当 前 businessId ： " + processInstance.getBusinessKey());
        }
    }

    @Test
    public void test2() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> myHoliday = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myHoliday").list();
        System.out.println("myHoliday: "+myHoliday.size());
        for(ProcessDefinition processDefinition:myHoliday){
            boolean suspended = processDefinition.isSuspended();
            System.out.println("processDefinition.getDeploymentId():"+processDefinition.getDeploymentId());
            System.out.println("suspended: "+ suspended);

        }
        for(ProcessDefinition processDefinition:myHoliday){
            if(processDefinition.isSuspended()){
                repositoryService.activateProcessDefinitionByKey("myHoliday",true,null);
                break;
            }else {
                repositoryService.suspendProcessDefinitionByKey("myHoliday",true,null);
                break;
            }
        }
    }

    @Test
    public void test22() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceId("32501").list();
        for (ProcessInstance processInstance:list){
            System.out.println("processInstance.isSuspended()"+ processInstance.isSuspended());
            System.out.println("processInstance.getProcessDefinitionId()"+ processInstance.getProcessDefinitionId());
            if(processInstance!=null){
                if(processInstance.isSuspended()){
                    runtimeService.activateProcessInstanceById(processInstance.getProcessInstanceId());
                    break;
                }else {
                    runtimeService.suspendProcessInstanceById(processInstance.getProcessInstanceId());
                    break;
                }
            }
        }

    }

    @Test
    public void test3() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment("1");
    }

    @Test
    public void test4() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //32502
        taskService.complete("32505");
        /*List<Task> list = taskService.createTaskQuery().processDefinitionKey("myHoliday").taskAssignee("zhangsan").list();
        System.out.println(list.size());
        for (Task task:list){
            System.out.println(task.getAssignee());
            System.out.println(task.getId());
            taskService.complete(task.getId());
        }*/
    }


}
