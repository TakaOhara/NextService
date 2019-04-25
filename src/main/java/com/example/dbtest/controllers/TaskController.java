package com.example.dbtest.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.dbtest.domain.entity.Task;
import com.example.dbtest.domain.entity.UserInfo;
import com.example.dbtest.domain.repositories.ProfileRepository;
import com.example.dbtest.domain.service.TaskService;
import com.example.dbtest.domain.service.UserInfoService;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
	private UserInfoService userInfoService;

    @Autowired
    public TaskController(TaskService taskService,UserInfoService userInfoService) {
        this.taskService = taskService;
        this.userInfoService = userInfoService;
    }

    //INDEX
    @GetMapping
    public String task(TaskForm taskForm, Model model) {
    	
    	System.out.println(userInfoService.findById(1).getPassword());
    	
        taskForm.setNewTask(true);
        List<Task> list = taskService.findAll();
        
        model.addAttribute("list", list);
        model.addAttribute("title", "タスク一覧");

        return "task";
    }

    //INSERT
    @PostMapping(name="new")
    public String insert(
    	@Valid @ModelAttribute TaskForm taskForm, //ヴァリデーションはフォームクラスに対して行う
        BindingResult result,
        Model model,
        Principal principal) {
    	
//    	int userId = 0;
//    	if(principal !=  null) {//認証前はnull
//        	Authentication auth = (Authentication)principal;
//            UserInfo userInfo = (UserInfo)auth.getPrincipal();
//            userId = userInfo.getId();
//        }
    	int userId = userInfoService.getId();
        Task task = makeTask(userId, taskForm);
        //redirect、失敗したらそのままHTML表示
        if (!result.hasErrors()) {
            taskService.save(task);
            return "redirect:/task?complete";
        } else {
            taskForm.setNewTask(true);
            model.addAttribute("taskForm", taskForm);
            List<Task> list = taskService.findAll();
            model.addAttribute("list", list);//sessionに格納
            model.addAttribute("title", "タスク一覧 afterInsert");
            return "task";
        }
    }

    
    //Before UPDATE
    @GetMapping("/{id}")//編集ページ
    public String showUpdate(
        @PathVariable Integer id,
        Model model) {

        Optional<TaskForm> form = taskService.getTaskForm(id);

        if (!form.isPresent()) {
            return "redirect:/task";
        }

        model.addAttribute("taskForm", form.get());
        List<Task> list = taskService.findAll();
        model.addAttribute("list", list);
        model.addAttribute("title", "更新フォーム");

        return "task";
    }
    
    /**
     * UPDATE
     * @param id
     * @param taskForm
     * @param model
     * @return
     */
    @PostMapping(params = "edit")
    public String update(
    	@Valid @ModelAttribute TaskForm taskForm,
    	BindingResult result,
    	Model model,
        Principal principal) {
    	
//    	int userId = 0;
//    	if(principal !=  null) {//認証前はnull
//        	Authentication auth = (Authentication)principal;
//            UserInfo userInfo = (UserInfo)auth.getPrincipal();
//            userId = userInfo.getId();
//        }
    	
    	//isNewTaskにfalseが代入される
        Optional<TaskForm> form = taskService.getTaskForm(taskForm.getId());

        if (!form.isPresent()) {
            return "redirect:/task";
        }
    	
        int userId = userInfoService.getId();
    	Task task = makeTask(userId, taskForm);
    	
        if (!result.hasErrors()) {
        	taskService.save(task);
            return "redirect:/task/" + taskForm.getId() + "?complete";
        } else {
            model.addAttribute("taskForm", taskForm);
            model.addAttribute("title", "タスク編集画面");
            return "task";
        }
        
        
    }

    /**
     * DELETE
     * @param id
     * @param mav
     * @return
     */
    @PostMapping("/delete/{id}")
    public String delete(
    	@PathVariable Integer id,
    	Model model) {
    	
        taskService.deleteById(id);
        return "redirect:/task";
    }

    private Task makeNewTask(int userId, TaskForm taskForm) {
        return new Task(userId, taskForm.getTypeId(), taskForm.getTitle(), taskForm.getDetail(), taskForm.getDeadline());
    }

    private Task makeTask(int userId, TaskForm taskForm) {
        return new Task(taskForm.getId(), userId, taskForm.getTypeId(), taskForm.getTitle(), taskForm.getDetail(), taskForm.getDeadline());
    }


}
