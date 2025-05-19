package daiseek.sbb.controller;

import daiseek.sbb.dto.AnswerForm;
import daiseek.sbb.dto.QuestionForm;
import daiseek.sbb.entity.Question;
import daiseek.sbb.repository.QuestionRepository;
import daiseek.sbb.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 프리픽스가 모두 /question로 시작하므로, 컨트롤러 클래스 위에 @RequestMapping을 붙일 수 있다.
@RequestMapping(value = "/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final QuestionService questionService;


    @GetMapping("/list")
//    @ResponseBody
//    public String list(Model model) {

        // 1. 서비스 없이 레포지토리에서 데이터를 주고 받을때
//        List<Question> questions = this.questionService.getQuestions();

        // 2. 서비스에 레포지토리를 주입받아서 사용

        // 기존의 메서드 대신 page 기능을 구현한 메서드 사용
//        List<Question> questionList = this.questionService.getList();
//        model.addAttribute("questionList", questionList);
//        return "question_list";
//    }

    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }


    @GetMapping(value = "/detail/{id}") // URL 매핑만 하면, value 생략 가능
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(Model model) {
        model.addAttribute("questionForm", new QuestionForm());
        // 빈 객체를 모델에 추가
        return "question_form"; // 'http://'을 제거하고 절대 경로로 수정

    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        // TODO : 질문 저장
        return "redirect:/question/list";
    }

}

// Model : 자바 클래스와 템플릿 간의 연결고리 역할
// Model 객체는 따로 생성할 필요 없이 메서드에 매개변수로 넣어주면,
// 스프링 부트가 자동으로 모델 객체를 생성해준다.