package com.web.spring.controller;

import com.web.spring.dto.child.plan.PlanResponseDto;
import com.web.spring.dto.parent.ParentReportResponseDto;
import com.web.spring.entity.Child;
import com.web.spring.entity.Wish;
import com.web.spring.service.ChildService;
import com.web.spring.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parents")
public class ParentController {

    private final ChildService childService;
    private final ParentService parentService;

    /* 월간 리포트 */
    @GetMapping("/reports/{childNum}")
    public ResponseEntity<?> getChildReports(@PathVariable String childNum,
                                            @RequestParam String year,
                                            @RequestParam String month){

        //토큰까서 parentNum 넣었다 치고
        Child child = parentService.findParentChild(1L, Long.parseLong(childNum));

        ParentReportResponseDto reponse = parentService.getChildReport(child, year, month);
        return ResponseEntity.status(HttpStatus.OK).body(reponse);
    }

    /* 아이에게 한 마디 */
    @PostMapping("/notifications/{childNum}")
    public ResponseEntity<?> createNotification(@PathVariable String childNum) {


        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /* 용돈 계약서 */
    @GetMapping("/contracts/{childNum}")
    public ResponseEntity<?> getContracts(@PathVariable String childNum,
                                          @RequestParam String year,
                                          @RequestParam String month) throws Exception {

        //토큰까서 parentNum 넣었다 치고
        Child child = parentService.findParentChild(1L, Long.parseLong(childNum));

        PlanResponseDto  planResponseDto = childService.showPlan(year,month);

        //추후에 결제 내역 가져오기

        return ResponseEntity.status(HttpStatus.OK).body(planResponseDto);
    }

    /* 위시 리스트 */
    @GetMapping("/wishes/{childNum}")
    public ResponseEntity<?> getWishes(@PathVariable String childNum){

        //토큰까서 parentNum 넣었다 치고
        Child child = parentService.findParentChild(1L, Long.parseLong(childNum));

        List<Wish> wishes =childService.showActiveWishList(childNum);

        return ResponseEntity.status(HttpStatus.OK).body(wishes);
    }

}
