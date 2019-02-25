package cn.activiti;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Administrator
 * 2019/2/14
 */
public class Test1 {

    @Test
    public void test01() {
        ProcessEngineConfiguration configuration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
    }

    @Test
    public void test02() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }


    @Test
    public void test03() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deployment = repositoryService.createDeployment();
        deployment = deployment.addClasspathResource("diagrams/holiday.bpmn");
        deployment = deployment.addClasspathResource("diagrams/holiday.png");
        deployment = deployment.name("请假流程");
        Deployment deploy = deployment.deploy();

        System.out.println("流程ID： "+deploy.getId());
        System.out.println("流程名字： "+deploy.getName());

    }

    @Test
    public void test04(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("myHoliday:3:5004");
        //runtimeService.startProcessInstanceByKey("myHoliday");
        //4.输出实例的相关信息
        System.out.println("流程部署ID"+processInstance.getDeploymentId());//null
        System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());//myHoliday:3:5004
        System.out.println("流程实例ID"+processInstance.getId());//10001
        System.out.println("活动ID"+processInstance.getActivityId());//null
    }

    @Test
    public void test05(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery = taskQuery.processDefinitionId("myHoliday:3:5004");
        taskQuery = taskQuery.taskAssignee("lisi");
        List<Task> list = taskQuery.list();
        for(Task task:list){
            System.out.println(" 流 程 实 例 id ： " +task.getProcessInstanceId());//10001 //1001
            System.out.println("任务id：" + task.getId());//10005  //12502
            System.out.println("任务负责人：" + task.getAssignee());//zhangsan    //lisi
            System.out.println("任务名称：" + task.getName());//部门经理  //总经理
        }
    }

    @Test
    public void test06(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("12502");
    }

    @Test
    public void test07(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/holiday.bpmn");
        InputStream resourceAsStream2 = this.getClass().getClassLoader().getResourceAsStream("diagrams/holiday.png");
        DeploymentBuilder deployment = repositoryService.createDeployment();

        deployment = deployment.addInputStream("holiday.bpmn", resourceAsStream);
        deployment = deployment.addInputStream("holiday.png", resourceAsStream2);
        deployment = deployment.name("请假条2");
        Deployment deploy = deployment.deploy();
        System.out.println("流程部署id：" + deploy.getId());//20001
        System.out.println("流程部署名称：" + deploy.getName());//请假条2

    }


    @Test
    public void test08(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("diagrams/activiti.zip");
        ZipInputStream zipInputStream = new ZipInputStream(resourceAsStream);

        Deployment deployment = repositoryService.createDeployment().addZipInputStream(zipInputStream).name("请假条3").deploy();

        System.out.println("流程部署id：" + deployment.getId());//22501
        System.out.println("流程部署名称：" + deployment.getName());//请假条3

    }

    @Test
    public void test09(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
       //List<ProcessDefinition> list = processDefinitionQuery.processDefinitionId("myHoliday:3:5004").orderByProcessDefinitionId().desc().list();
        List<ProcessDefinition> list = processDefinitionQuery.processDefinitionKey("myHoliday").orderByProcessDefinitionId().desc().list();
        System.out.println(list.size());
        for(ProcessDefinition processDefinition:list){
            System.out.println("------------------------");
            System.out.println(" 流 程 部 署 id ： " + processDefinition.getDeploymentId());
            System.out.println("流程定义id：" + processDefinition.getId());
            System.out.println("流程定义名称：" + processDefinition.getName());
            System.out.println("流程定义key：" + processDefinition.getKey());
            System.out.println("流程定义版本：" + processDefinition.getVersion());
        }
    }

    @Test
    public void test10(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment("7501",true);

    }

    @Test
    @SuppressWarnings("all")
    public void test11() throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId("myHoliday:3:5004").singleResult();
        String resourceName = processDefinition.getResourceName();
        String diagramResourceName = processDefinition.getDiagramResourceName();
        System.out.println("processDefinition.getDeploymentId():  "+processDefinition.getDeploymentId());

        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        InputStream resourceAsStream2 = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), diagramResourceName);

        File file = new File("d:/purchasingflow01.bpmn");
        File file2 = new File("d:/purchasingflow01.png");

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileOutputStream fileOutputStream2 = new FileOutputStream(file2);

        byte[] b = new byte[1024];
        int a = -1;
        while ((a=resourceAsStream.read(b))!=-1){
            fileOutputStream.write(b,0,a);
        }

        byte[] b2 = new byte[1024];
        int a2 = -1;
        while ((a2=resourceAsStream2.read(b2))!=-1){
            fileOutputStream2.write(b2,0,a2);
        }
        resourceAsStream.close();
        fileOutputStream.close();
        resourceAsStream2.close();
        fileOutputStream2.close();

    }

    @Test
    @SuppressWarnings("all")
    public void test111() throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //RepositoryService repositoryService = processEngine.getRepositoryService();

        //流程部署id
        String deploymentId = "9001";
        // 通过流程引擎获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //读取资源名称
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId("myHoliday:3:5004").singleResult();
        System.out.println("processDefinition.getDeploymentId():  "+processDefinition.getDeploymentId());
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
        File exportFile = new File("d:/holiday2.png");
        FileOutputStream fileOutputStream = new FileOutputStream(exportFile);
        byte[] buffer = new byte[1024];
        int len = -1;
        //输出图片
        while((len = inputStream.read(buffer))!=-1){
            fileOutputStream.write(buffer, 0, len);
        }
        inputStream.close();
        fileOutputStream.close();
    }

    @Test
    public void test12() throws Exception{
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery query = historyService.createHistoricActivityInstanceQuery().processInstanceId("10001");
        List<HistoricActivityInstance> list = query.list();
        System.out.println(list.size());
        for (HistoricActivityInstance ai:list) {
            System.out.println(ai.getActivityId());
            System.out.println(ai.getActivityName());
            System.out.println(ai.getProcessDefinitionId());
            System.out.println(ai.getProcessInstanceId());
            System.out.println("==============================");

        }
    }


   /* public static void main(String[] args) {
        //1.创建ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //2.得到RepositoryService实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //3.进行部署
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("diagrams/holiday.bpmn")  //添加bpmn资源
                .addClasspathResource("diagrams/holiday.png")  //添加bpmn资源
                .name("请假申请单流程")
                .deploy();

        //4.输出部署的一些信息
        System.out.println(deployment.getName());
        System.out.println(deployment.getId());

    }*/

}
