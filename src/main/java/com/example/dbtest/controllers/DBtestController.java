package com.example.dbtest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.dbtest.domain.entity.Instructor;
import com.example.dbtest.domain.entity.InstructorDetail;
import com.example.dbtest.domain.service.InstructorService;

@Controller
@RequestMapping("/test")
public class DBtestController {

    private final InstructorService instructorService;

    @Autowired
    public DBtestController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    //INDEX
    @GetMapping
    public ModelAndView test(
            //@ModelAttributeでInstructorFormは自動的にインスタンス化される　
            //htmlに反映させるにはmav.addObjectが必須
            @ModelAttribute("InstructorForm") InstructorForm instructorForm,
            ModelAndView mav) {

        instructorForm.setNewInstructor(true);
        mav.addObject("instructorForm", instructorForm);
        List<Instructor> list = instructorService.findAll();
        mav.addObject("list", list);
        mav.addObject("title", "メンバー一覧");

        mav.setViewName("test");
        return mav;
    }


    //Before UPDATE
    @GetMapping(value = "/{id}")//編集ページ
    public ModelAndView showUpdate(
            @ModelAttribute("InstructorForm") InstructorForm instructorForm,
            @PathVariable Integer id,
            ModelAndView mav) {
        Optional<InstructorForm> form = instructorService.getInstructorForm(id);

        if (!form.isPresent()) {
            mav.setViewName("redirect:/test");
            return mav;
            //return new ModelAndView("redirect:/test")
        }

        mav.addObject("instructorId", id);
        mav.addObject("instructorForm", form.get());
        List<Instructor> list = instructorService.findAll();
        mav.addObject("list", list);
        mav.addObject("title", "更新フォーム");

        mav.setViewName("test");
        return mav;
    }


    //INSERT
    @PostMapping
    @Transactional(readOnly = false)
    public ModelAndView insert2(
            @Validated InstructorForm instructorForm,//ヴァリデーションはフォームクラスに対して行う
            BindingResult result,
            ModelAndView mav) {
        Instructor instructor = makeInstructor(instructorForm);
        //redirect、失敗したらそのままHTML表示
        if (!result.hasErrors()) {
            instructorService.save(instructor);
            mav.setViewName("redirect:/test");
        } else {
            instructorForm.setNewInstructor(true);
            mav.addObject("instructorForm", instructorForm);
            List<Instructor> list = instructorService.findAll();
            mav.addObject("list", list);
            mav.addObject("title", "メンバー一覧2");
            mav.setViewName("test");
        }
        return mav;
    }

    //UPDATE
    @PostMapping("/update/{id}")
    @Transactional(readOnly = false)
    public ModelAndView update(
            @PathVariable Integer id,
            @ModelAttribute("InstructorForm") InstructorForm instructorForm,
            ModelAndView mav) {
        Instructor instructor = makeInstructor(id, instructorForm);
        instructorService.save(instructor);
        mav.setViewName("redirect:/test/" + id);
        return mav;
    }

    //DELETE
    @PostMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional(readOnly = false)//削除の場合必須
    public ModelAndView delete(
            @PathVariable Integer id,
            ModelAndView mav) {
        instructorService.deleteById(id);
        mav.setViewName("redirect:/test");
        return mav;
    }


    private Instructor makeInstructor(InstructorForm iForm) {
        InstructorDetail iD = new InstructorDetail(iForm.getYoutubeChannel(), iForm.getHobby());
        Instructor i = new Instructor(iForm.getFirstName(), iForm.getLastName(), iForm.getEmail());
        i.setInstructorDetail(iD);
        return i;
    }

    private Instructor makeInstructor(int id, InstructorForm iForm) {
        InstructorDetail iD = new InstructorDetail(iForm.getYoutubeChannel(), iForm.getHobby());
        Instructor i = new Instructor(id, iForm.getFirstName(), iForm.getLastName(), iForm.getEmail());
        i.setInstructorDetail(iD);
        return i;
    }


}
